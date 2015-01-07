package com.dc.esb.servicegov.refactoring.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.entity.SDANode;
import com.dc.esb.servicegov.refactoring.entity.OLA;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.SDA;
import com.dc.esb.servicegov.refactoring.entity.SLA;
import com.dc.esb.servicegov.refactoring.service.InvokeInfoManager;
import com.dc.esb.servicegov.refactoring.service.OLAManager;
import com.dc.esb.servicegov.refactoring.service.OperationManager;
import com.dc.esb.servicegov.refactoring.service.SDAManager;
import com.dc.esb.servicegov.refactoring.service.SLAManager;
import com.dc.esb.servicegov.refactoring.vo.OperationInfoVO;
import com.dc.esb.servicegov.refactoring.vo.SDAVO;

@Controller
@RequestMapping("/operationInfo")
public class OperationInfoController {	
	private static final Log log = LogFactory.getLog(OperationInfoController.class);
	@Autowired
	private OperationManager operationManager;
	@Autowired
	private SDAManager sdaManager;
	@Autowired
	private SLAManager slaManager;
	@Autowired
	private OLAManager olaManager;
	@Autowired
	private InvokeInfoManager invokeManager;
	
	List<SDA> sdaList = new ArrayList<SDA>();
	
	List<SDANode> lstSDA = new ArrayList<SDANode>();
	
	private String indentSpace = "";
	
	@RequestMapping(method = RequestMethod.GET, value = "/alloperations", headers = "Accept=application/json")
	public @ResponseBody List<OperationInfoVO> getAllOperationInfo(){
		List<OperationInfoVO> operationInfoVOInfos = null;
		operationInfoVOInfos = operationManager.getAllOperations();
		return operationInfoVOInfos;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/getOperation/{operationandservice}", headers = "Accept=application/json")
    public
    @ResponseBody Operation getOperation(@PathVariable String operationandservice) {	
    	String operationId = operationandservice.substring(10);
    	String serviceId = operationandservice.substring(0,10);
    	return operationManager.getOperation(operationId,serviceId);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/getSDAInfoByOperationId/{operationandservice}", headers = "Accept=application/json")
	public @ResponseBody
	List<SDA> getSDAInfoByOperationId(@PathVariable String operationandservice) {
    	String operationId = operationandservice.substring(10);
    	String serviceId = operationandservice.substring(0,10);
		try {
			sdaList = new ArrayList<SDA>();
			SDAVO sdaVO = operationManager.getSDAByOperation(operationId,serviceId);
			renderSDAVO(sdaVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdaList;
	}
    private void renderSDAVO(SDAVO sda) {
		SDA node = sda.getValue();
		node.setStructId("|--" + node.getStructId());
		node.setStructId(this.indentSpace + node.getStructId());
		sdaList.add(node);
		if (sda.getChildNode() != null) {
			List<SDAVO> childSda = sda.getChildNode();
			String tmpIndent = this.indentSpace;
			this.indentSpace += "&nbsp;&nbsp;&nbsp;&nbsp;";
			for (SDAVO a : childSda) {
				renderSDAVO(a);
			}
			this.indentSpace = tmpIndent;
		}
	}
    @RequestMapping(method = RequestMethod.GET, value = "/getSLAByOperationId/{operationandservice}", headers = "Accept=application/json")
    public @ResponseBody List<SLA> getSLAByOperationId(@PathVariable String operationandservice){
    	String operationId = operationandservice.substring(10);
    	String serviceId = operationandservice.substring(0,10);
    	return operationManager.getSLAByOperationId(operationId,serviceId);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/getOLAByOperationId/{operationandservice}", headers = "Accept=application/json")
    public @ResponseBody List<OLA> getOLAByOperationId(@PathVariable String operationandservice){
    	String operationId = operationandservice.substring(10);
    	String serviceId = operationandservice.substring(0,10);
    	return operationManager.getOLAByOperationId(operationId,serviceId);
    }   
    @RequestMapping(method = RequestMethod.GET, value = "/deleteOperation/{operationandservice}", headers = "Accept=application/json")
    public @ResponseBody boolean deleteOperation(@PathVariable String operationandservice){
    	String operationId = operationandservice.substring(10);
    	String serviceId = operationandservice.substring(0,10); 	
    	boolean sdaSuccess = sdaManager.deleteSDA(operationId, serviceId);
    	boolean slaSuccess = slaManager.deleteSLA(operationId, serviceId);
    	boolean olaSuccess = olaManager.deleteOLA(operationId, serviceId);
    	boolean invokeSuccess = invokeManager.deleteInvokeInfo(operationId, serviceId);
    	boolean operationSuccess = operationManager.deleteOperation(operationId, serviceId);
    	boolean isSuccess = sdaSuccess && slaSuccess && olaSuccess && operationSuccess && invokeSuccess;
    	return isSuccess;
    }      
    @RequestMapping(method = RequestMethod.POST, value = "/addOperation", headers = "Accept=application/json")
    public @ResponseBody boolean addOperation(@RequestBody Operation operation){
    	boolean operationSuccess = operationManager.saveOperation(operation);
    	String operationId = operation.getOperationId();
    	String serviceId = operation.getServiceId();
    	boolean flag = false;
    	if(operationSuccess){
    		flag = initSDA(operationId,serviceId);
    	}
    	return operationSuccess&&flag;
    }
    @RequestMapping(method = RequestMethod.POST, value = "/addSDA", headers = "Accept=application/json")
    public @ResponseBody boolean addSDA(@RequestBody SDA[] sdaArray){
    	String operationId = sdaArray[0].getOperationId();
    	String serviceId = sdaArray[0].getServiceId();
    	boolean updateFlag = sdaManager.updateOperationVersion(operationId, serviceId);
    	if(updateFlag){
        	boolean isDeleteSuccess = sdaManager.deleteSDA(operationId, serviceId);
        	List<SDA> sdaList = new ArrayList<SDA>();
        	for (int i = 0; i < sdaArray.length; i++) {
        		sdaArray[i].setStructId(sdaArray[i].getStructId()
        				.substring(sdaArray[i].getStructId().lastIndexOf("|--")+3));
        		sdaList.add(sdaArray[i]);
    		}
        	if(isDeleteSuccess){
        		boolean sdaSuccess = sdaManager.saveSDA(sdaList);
        		log.error("sda save is success :"+sdaSuccess);
        		return sdaSuccess;
        	}else{
        		return false;
        	}     		
    	}else{
    		log.error("error in update operation version ");
    		return false;
    	}
     	
    }    
    @RequestMapping(method = RequestMethod.POST, value = "/addSLA", headers = "Accept=application/json")
    public @ResponseBody boolean addSLA(@RequestBody SLA[] slaArray){ 	
    	String serviceId = slaArray[0].getServiceId();
    	String operationId = slaArray[0].getOperationId();
    	boolean deleteFlag = slaManager.deleteSLA(operationId, serviceId);
    	List<SLA> slaList = new ArrayList<SLA>();
    	for (int i = 0; i < slaArray.length; i++) {
    		slaList.add(slaArray[i]);
		}
    	if(deleteFlag){
    		boolean saveFlag = slaManager.saveSLA(slaList);
    		return saveFlag;
    	}else{
    		return false;
    	}  	
    }   
    @RequestMapping(method = RequestMethod.POST, value = "/addOLA", headers = "Accept=application/json")
    public @ResponseBody boolean addOLA(@RequestBody OLA[] olaArray){
    	String operationId = olaArray[0].getOperationId();
    	String serviceId = olaArray[0].getServiceId();
    	boolean isDeleteSuccess = olaManager.deleteOLA(operationId, serviceId);
    	List<OLA> olaList = new ArrayList<OLA>();
    	for (int i = 0; i < olaArray.length; i++) {
    		olaList.add(olaArray[i]);
		}
    	if(isDeleteSuccess){
        	boolean slaSuccess = olaManager.saveOLA(olaList);
        	return slaSuccess;
    	}else{
    		return false;
    	}     	

    }
    public boolean initSDA(String operationId,String serviceId){
    	int sdaNum = operationManager.getSDAByOperationId(operationId, serviceId).size();
    	List<SDA> sdaList = new ArrayList<SDA>();
    	if(sdaNum<=0){
    		SDA rootSda = new SDA();
    		String rootId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    		rootSda.setId(rootId);
    		rootSda.setStructId("");
    		rootSda.setMetadataId("");
    		rootSda.setType("");
    		rootSda.setSeq(1);
    		rootSda.setStructId(operationId);
    		rootSda.setOperationId(operationId);
    		rootSda.setServiceId(serviceId);
    		rootSda.setParentId("/");
    		sdaList.add(rootSda);
    		
    		SDA requestSda = new SDA();
    		String requestId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    		requestSda.setId(requestId);
    		requestSda.setSeq(2);
    		requestSda.setStructId("");
    		requestSda.setMetadataId("");
    		requestSda.setType("");
    		requestSda.setStructId("request");
    		requestSda.setOperationId(operationId);
    		requestSda.setServiceId(serviceId);
    		requestSda.setParentId(rootId);
    		sdaList.add(requestSda);
    		
    		SDA svcBodyRequestSda = new SDA();
    		String svcBodyRequestId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    		svcBodyRequestSda.setId(svcBodyRequestId);
    		svcBodyRequestSda.setSeq(3);
    		svcBodyRequestSda.setStructId("");
    		svcBodyRequestSda.setMetadataId("");
    		svcBodyRequestSda.setType("");
    		svcBodyRequestSda.setStructId("SvcBody");
    		svcBodyRequestSda.setOperationId(operationId);
    		svcBodyRequestSda.setServiceId(serviceId);
    		svcBodyRequestSda.setParentId(requestId);
    		sdaList.add(svcBodyRequestSda);
    		
    		SDA responseSda = new SDA();
    		String responseId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    		responseSda.setId(responseId);
    		responseSda.setSeq(4);
    		responseSda.setStructId("");
    		responseSda.setMetadataId("");
    		responseSda.setType("");
    		responseSda.setStructId("response");
    		responseSda.setOperationId(operationId);
    		responseSda.setServiceId(serviceId);
    		responseSda.setParentId(rootId);
    		sdaList.add(responseSda);
    		
    		SDA svcBodyResponseSda = new SDA();
    		String svcBodyResponseId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    		svcBodyResponseSda.setId(svcBodyResponseId);
    		svcBodyResponseSda.setSeq(5);
    		svcBodyResponseSda.setStructId("");
    		svcBodyResponseSda.setMetadataId("");
    		svcBodyResponseSda.setType("");
    		svcBodyResponseSda.setStructId("SvcBody");
    		svcBodyResponseSda.setOperationId(operationId);
    		svcBodyResponseSda.setServiceId(serviceId);
    		svcBodyResponseSda.setParentId(responseId);    
    		sdaList.add(svcBodyResponseSda);
    	}
    	return sdaManager.saveSDA(sdaList);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/deployOperation/{operationandservice}", headers = "Accept=application/json")
    public @ResponseBody boolean deployOperation(@PathVariable String operationandservice){
    	log.error("*************************************"+operationandservice);
    	String operationId = operationandservice.substring(10);
    	String serviceId = operationandservice.substring(0,10); 
    	return operationManager.deployOperation(operationId, serviceId);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/redefOperation/{operationandservice}", headers = "Accept=application/json")
    public @ResponseBody boolean redefOperation(@PathVariable String operationandservice){
    	log.error("*************************************"+operationandservice);
    	String operationId = operationandservice.substring(10);
    	String serviceId = operationandservice.substring(0,10); 
    	return operationManager.redefOperation(operationId, serviceId);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/publishOperation/{operationandservice}", headers = "Accept=application/json")
    public @ResponseBody boolean publishOperation(@PathVariable String operationandservice){
    	log.error("*************************************"+operationandservice);
    	String operationId = operationandservice.substring(10);
    	String serviceId = operationandservice.substring(0,10); 
    	return operationManager.publishOperation(operationId, serviceId);
    }
}
