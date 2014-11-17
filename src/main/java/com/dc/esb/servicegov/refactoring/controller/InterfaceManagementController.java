package com.dc.esb.servicegov.refactoring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.refactoring.entity.IDA;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.Service;
import com.dc.esb.servicegov.refactoring.service.impl.InterfaceManagerImpl;
import com.dc.esb.servicegov.refactoring.service.impl.OperationManagerImpl;
import com.dc.esb.servicegov.refactoring.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.refactoring.vo.IDAVO;
import com.dc.esb.servicegov.refactoring.vo.InterfaceListVO;
import com.dc.esb.servicegov.refactoring.vo.InterfaceVO;

/**
 * 
 * @author G
 *
 */
@Controller
@RequestMapping("/interfaceManagement")
public class InterfaceManagementController {
	
	@Autowired
	private InterfaceManagerImpl interfaceManager;
	
	@Autowired
	private ServiceManagerImpl serviceManager;
	
	@Autowired
	private OperationManagerImpl operationManager;
	
//	@Autowired
//	private InvokeInfoManagerImpl invokeManager;
	
	private List<IDA> idaList = null;
	
	private String indentSpace = "";

	/**
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getAllList", headers = "Accept=application/json")
	public @ResponseBody List<InterfaceListVO> getInterfaceManagermentInfo(){
		return interfaceManager.getInterfaceManagementInfo();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getAllServices", headers = "Accept=application/json")
	public @ResponseBody List<Service> getAllServices(){
		return serviceManager.getAllServices();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getOperationOfService/{params}", headers = "Accept=application/json")
	public @ResponseBody List<Operation> getOperationOfService(@PathVariable String params){
		return operationManager.getOperationsOfService(params.trim());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/update/{params}", headers = "Accept=application/json")
	public @ResponseBody InterfaceListVO udpate(@PathVariable String params){
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getVO/{params}", headers = "Accept=application/json")
	public @ResponseBody InterfaceListVO getVO(@PathVariable String params){
		return interfaceManager.getInterfaceVO(params);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/insert", headers = "Accept=application/json")
	public @ResponseBody List<IDA> insert(@RequestBody InterfaceVO vo){
		
		interfaceManager.insertInterfaceInfo(vo);
		return getIDAs(vo.getInterfaceId());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getIDAs/{params}", headers = "Accept=application/json")
	public @ResponseBody List<IDA> getIDAs(@PathVariable String params){
		IDAVO root;
		try {
			idaList = new ArrayList<IDA>();
			root = interfaceManager.getIDATreeOfInterfaceId(params);
			renderIDAVO(root);
			return idaList;
		} catch (DataException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/deleteIDAs/{params}", headers = "Accept=application/json")
	public @ResponseBody String deleteInterfaceInfo(@PathVariable String params){
		String vv = interfaceManager.deleteIDAs(params);
		return vv;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/deleteInterfaceInfos/{params}", headers = "Accept=application/json")
	public @ResponseBody void deleteIDAs(@PathVariable String params){
		interfaceManager.deleteInterfaceInfos(params);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, value = "/saveIDA/{params}", headers = "Accept=application/json")
	public @ResponseBody void saveIDA(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String params){
		Map<String,String> map = null;
		ObjectMapper mapper =new ObjectMapper();
		try {
			map = mapper.readValue(params, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (map == null) {
			return ;
		}
		interfaceManager.saveIDA(map);
	}
	
	private void renderIDAVO(IDAVO idaVO) {
		IDA node = idaVO.getValue();
		node.setStructName("|--" + node.getStructName());
		node.setStructName(this.indentSpace + node.getStructName());
		idaList.add(node);
		List<IDAVO> children = idaVO.getChildNodes();
		if (children != null) {
			String tmpIndent = this.indentSpace;
			this.indentSpace += "&nbsp;&nbsp;&nbsp;&nbsp;";
			for (IDAVO child : children) {
				renderIDAVO(child);
			}
			this.indentSpace = tmpIndent;
		}
	}
	
}
