package com.dc.esb.servicegov.refactoring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.Service;
import com.dc.esb.servicegov.refactoring.entity.ServiceCategory;
import com.dc.esb.servicegov.refactoring.service.impl.OperationManagerImpl;
import com.dc.esb.servicegov.refactoring.service.impl.ServiceCategoryManagerImpl;
import com.dc.esb.servicegov.refactoring.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.refactoring.vo.ServiceNode;

@Controller
@RequestMapping("/serviceInfo")
public class ServiceInfoController {	
	private Log log = LogFactory.getLog(getClass());
	@Autowired
	private ServiceManagerImpl serviceManager;
	@Autowired
	private OperationManagerImpl operationManager;
	@Autowired
	private ServiceCategoryManagerImpl ServiceCategoryManager;
	@RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
	public @ResponseBody List<com.dc.esb.servicegov.refactoring.entity.Service> getAllOperationInfo(){
		
		return serviceManager.getAllServices();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getServiceById/{id}", headers = "Accept=application/json")
	public @ResponseBody com.dc.esb.servicegov.refactoring.entity.Service getServiceById
	(@PathVariable String id){
		
		return serviceManager.getServiceById(id);
	}
	
	/**
     * 
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/insertOrUpdate", headers = "Accept=application/json")
     public
     @ResponseBody
     boolean insertOrUpdate(HttpServletRequest request,
 			HttpServletResponse response,@RequestBody com.dc.esb.servicegov.refactoring.entity.Service service) {
    	boolean flag = false;
    	try{
//    		
//    		request.setCharacterEncoding("UTF-8");
//			response.setCharacterEncoding("UTF-8");
//			params = new String(params.getBytes("iso8859-1"),"UTF-8");
//    	    ObjectMapper mapper = new ObjectMapper();
//		    mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
//		    // json Object convert to Metadata
//		    com.dc.esb.servicegov.refactoring.entity.Service service = 
//			mapper.readValue(params, com.dc.esb.servicegov.refactoring.entity.Service.class);
		    flag = serviceManager.insertOrUpdateService(service);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return flag;
    }
    
    /**
     * 根据ID删除服务
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/delByServiceId/{id}", headers = "Accept=application/json")
     public
     @ResponseBody
     boolean delete(@PathVariable String id) {
    	boolean flag = true;
    	try{
    		if(id.contains(",")){
    			String[] idArr = id.split(",");
    			int i =0;
    			while(i<idArr.length){
    				serviceManager.delServiceById(idArr[i]);
    				i++;
    			}
    		}
    		else{
    			serviceManager.delServiceById(id);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		flag = false;
    	}
    	return flag;
    }
    
    /**
     * 根据ID获取所有操作信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getOperationsById/{id}", headers = "Accept=application/json")
     public
     @ResponseBody
     List<Operation> getOperationsById(@PathVariable String id) {
    	return serviceManager.getOperationsByServiceId(id);
    }
    
    /**
     * check service has operation?
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/checkExistOperation/{id}", headers = "Accept=application/json")
     public
     @ResponseBody
     String checkExistOperation(@PathVariable String id) {
    	String idArr[] = id.split(",");
    	for(String serid:idArr){
    	List<Operation> list = serviceManager.getOperationsByServiceId(serid);
    	   if(list != null && list.size()>0){
    		   return serid;
    	   }
    	}
    	return null;
    }
    
    /**
     * 发布服务
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/deploy/{id}", headers = "Accept=application/json")
     public
     @ResponseBody
     boolean deploy(@PathVariable String id) {
    	boolean flag = true;
    	try{
    		if(id.contains(",")){
    			String[] idArr = id.split(",");
    			int i =0;
    			while(i<idArr.length){
    				serviceManager.deployService(idArr[i]);
    				i++;
    			}
    		}
    		else{
    			serviceManager.deployService(id);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		flag = false;
    	}
    	return flag;
    }
    
    /**
     * 重定义服务
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/redef/{id}", headers = "Accept=application/json")
     public
     @ResponseBody
     boolean redef(@PathVariable String id) {
    	boolean flag = true;
    	try{
    		if(id.contains(",")){
    			String[] idArr = id.split(",");
    			int i =0;
    			while(i<idArr.length){
    				serviceManager.redefService(idArr[i]);
    				i++;
    			}
    		}
    		else{
    			serviceManager.redefService(id);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		flag = false;
    	}
    	return flag;
    }
    
    /**
     * 上线服务
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/publish/{id}", headers = "Accept=application/json")
     public
     @ResponseBody
     boolean publish(@PathVariable String id) {
    	boolean flag = true;
    	try{
    		if(id.contains(",")){
    			String[] idArr = id.split(",");
    			int i =0;
    			while(i<idArr.length){
    				serviceManager.publishService(idArr[i]);
    				i++;
    			}
    		}
    		else{
    			serviceManager.publishService(id);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		flag = false;
    	}
    	return flag;
    }
    @RequestMapping(method = RequestMethod.GET, value = "/AllService", headers = "Accept=application/json")
    public @ResponseBody List<ServiceNode> getAllServiceNode() {
    	List<ServiceNode> serviceNodeList = new ArrayList<ServiceNode>();
    	ServiceNode serviceNode = new ServiceNode();
    	serviceNode.setNodeId("MSMGroup");
    	serviceNode.setNodeName("金融服务库");
    	serviceNode.setNodeValue("金融服务库");
    	serviceNode.setParentNodeId("/");
    	serviceNodeList.add(serviceNode);
    	List<ServiceCategory> firstServiceCategoryList = ServiceCategoryManager.getFirstLevelInfo();
    	for(ServiceCategory serviceCategory:firstServiceCategoryList){
    		if(!"MSMGroup".equals(serviceCategory.getCategoryId())){
    	    	ServiceNode firstNode = new ServiceNode();
    	    	firstNode.setNodeId(serviceCategory.getCategoryId());
    	    	firstNode.setNodeName(serviceCategory.getCategoryId());
    	    	firstNode.setNodeValue(serviceCategory.getCategoryName());
    	    	firstNode.setParentNodeId(serviceCategory.getParentId());
    	    	serviceNodeList.add(firstNode);
    		}
    	}
    	List<ServiceCategory> secondServiceCategoryList = ServiceCategoryManager.getSecondLevelInfo();
    	for(ServiceCategory serviceCategory:secondServiceCategoryList){
    	    ServiceNode secondNode = new ServiceNode();
    	    secondNode.setNodeId(serviceCategory.getCategoryId());
    	    secondNode.setNodeName(serviceCategory.getCategoryId());
    	    secondNode.setNodeValue(serviceCategory.getCategoryName());
    	    secondNode.setParentNodeId(serviceCategory.getParentId());
    	    serviceNodeList.add(secondNode);
    	    List<Service> servieList = serviceManager.getServicesByCategory(serviceCategory.getCategoryId());
    	    for(Service service:servieList){
        	    ServiceNode thirdNode = new ServiceNode();
        	    thirdNode.setNodeId(service.getServiceId());
        	    thirdNode.setNodeName(service.getServiceId());
        	    thirdNode.setNodeValue(service.getServiceName());
        	    thirdNode.setParentNodeId(serviceCategory.getCategoryId());
        	    serviceNodeList.add(thirdNode);
        	    List<Operation> operationList = serviceManager.getOperationsByServiceId(service.getServiceId());
        	    for(Operation operation:operationList){
            	    ServiceNode fourthNode = new ServiceNode();
            	    fourthNode.setNodeId(operation.getOperationId());
            	    fourthNode.setNodeName(operation.getOperationId());
            	    fourthNode.setNodeValue(operation.getOperationName());
            	    fourthNode.setParentNodeId(service.getServiceId());
            	    serviceNodeList.add(fourthNode);        	    	
        	    }
    	    }
    	}
    	return serviceNodeList;
   	}
}
