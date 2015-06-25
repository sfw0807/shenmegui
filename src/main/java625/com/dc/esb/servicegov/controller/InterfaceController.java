package com.dc.esb.servicegov.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.entity.Interface;
import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.InterfaceService;
import com.dc.esb.servicegov.service.ServiceInvokeService;
import com.dc.esb.servicegov.service.SystemService;
import com.dc.esb.servicegov.util.TreeNode;

@Controller
@RequestMapping("/interface")
public class InterfaceController {

	@Autowired
	private InterfaceService interfaceService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private ServiceInvokeService serviceInvokeService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/getLeftTree", headers = "Accept=application/json")
	public @ResponseBody
	List<TreeNode> getLeftTree() {
		List<TreeNode> rootList = new ArrayList<TreeNode>();
		List<com.dc.esb.servicegov.entity.System> systems = systemService.getEntityAll();
		for(com.dc.esb.servicegov.entity.System s:systems){
			TreeNode root = new TreeNode();
			root.setId(s.getSystemId());
			root.setText(s.getSystemChineseName());
			root.setClick("disable");
			List<ServiceInvoke> serviceIns = s.getServiceInvokes();
			List<TreeNode> childList = new ArrayList<TreeNode>();
			for(ServiceInvoke si : serviceIns){
				
				TreeNode child = new TreeNode();
				child.setId(si.getInter().getInterfaceId());
				child.setText(si.getInter().getInterfaceName());
				childList.add(child);
			}
			root.setChildren(childList);
			
			rootList.add(root);
		}
		
		
		
		return rootList;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody
	boolean save(@RequestBody
	Interface inter) {
		interfaceService.editEntity(inter);
		ServiceInvoke si = inter.getServiceInvoke();
		si.setInterfaceId(inter.getInterfaceId());
		serviceInvokeService.editEntity(si);
		return true;
	}
}
