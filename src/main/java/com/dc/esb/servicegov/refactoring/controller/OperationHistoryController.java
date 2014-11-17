package com.dc.esb.servicegov.refactoring.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.refactoring.entity.OperationHistory;
import com.dc.esb.servicegov.refactoring.entity.SDAHistory;
import com.dc.esb.servicegov.refactoring.entity.SLAHistory;
import com.dc.esb.servicegov.refactoring.service.OLAManager;
import com.dc.esb.servicegov.refactoring.service.OperationHistoryManager;
import com.dc.esb.servicegov.refactoring.service.OperationManager;
import com.dc.esb.servicegov.refactoring.service.SDAManager;
import com.dc.esb.servicegov.refactoring.service.SLAManager;
import com.dc.esb.servicegov.refactoring.vo.SDAHistoryVO;

@Controller
@RequestMapping("/operationHistory")
public class OperationHistoryController {	
	private static final Log log = LogFactory.getLog(OperationHistoryController.class);
	@Autowired
	private OperationHistoryManager operationManager;
	List<SDAHistory> sdaList = new ArrayList<SDAHistory>();
	private String indentSpace = "";
	@Autowired
	private OperationManager opManager;
	@Autowired
	private SDAManager sdaManager;
	@Autowired
	private SLAManager slaManager;
	@Autowired
	private OLAManager olaManager;
	
	@RequestMapping(method = RequestMethod.GET, value = "/allHistory/{operationandservice}", headers = "Accept=application/json")
	public @ResponseBody List<OperationHistory> getAllHistoryVersion(@PathVariable String operationandservice){
    	String operationId = operationandservice.substring(10);
    	String serviceId = operationandservice.substring(0,10);
		return operationManager.getAllHistoryOperation(operationId, serviceId);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/getOperation", headers = "Accept=application/json")
	public @ResponseBody OperationHistory getOperation(@RequestBody String[] params){
		return operationManager.getOperation(params[0], params[1],params[2]).get(0);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/getSDA", headers = "Accept=application/json")
	public @ResponseBody
	List<SDAHistory> getSDAInfoByOperationId(@RequestBody String[] params) {
		try {
			sdaList = new ArrayList<SDAHistory>();
			SDAHistoryVO sdaVO = operationManager.getSDA(params[0], params[1],params[2]);
			renderSDAVO(sdaVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdaList;
	}
    private void renderSDAVO(SDAHistoryVO sda) {
    	SDAHistory node = sda.getValue();
		node.setStructId("|--" + node.getStructId());
		node.setStructId(this.indentSpace + node.getStructId());
		sdaList.add(node);
		if (sda.getChildNode() != null) {
			List<SDAHistoryVO> childSda = sda.getChildNode();
			String tmpIndent = this.indentSpace;
			this.indentSpace += "&nbsp;&nbsp;&nbsp;&nbsp;";
			for (SDAHistoryVO a : childSda) {
				renderSDAVO(a);
			}
			this.indentSpace = tmpIndent;
		}
	}
	@RequestMapping(method = RequestMethod.POST, value = "/getSLA", headers = "Accept=application/json")
	public @ResponseBody List<SLAHistory> getSLA(@RequestBody String[] params){
		return operationManager.getSLA(params[0], params[1],params[2]);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/getOLA", headers = "Accept=application/json")
	public @ResponseBody List<SLAHistory> getOLA(@RequestBody String[] params){
		return operationManager.getSLA(params[0], params[1],params[2]);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/backOperation", headers = "Accept=application/json")
	public @ResponseBody boolean backOperation(@RequestBody String[] params){
		boolean sdaFlag = sdaManager.deleteSDA(params[0], params[2]);
		boolean slaFlag = slaManager.deleteSLA(params[0], params[2]);
		boolean olaFlag = olaManager.deleteOLA(params[0], params[2]);
		boolean operationFlag = opManager.deleteOperation(params[0], params[2]);
		boolean flag = operationFlag && sdaFlag && slaFlag && olaFlag;
		if(flag){
			return operationManager.backOperation(params[0], params[1], params[2]);
		}else{
			return false;
		}
	}
}
