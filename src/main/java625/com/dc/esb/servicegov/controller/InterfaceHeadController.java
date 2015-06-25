package com.dc.esb.servicegov.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.entity.Ida;
import com.dc.esb.servicegov.entity.InterfaceHead;
import com.dc.esb.servicegov.service.IdaService;
import com.dc.esb.servicegov.service.InterfaceHeadService;
import com.dc.esb.servicegov.util.TreeNode;

@Controller
@RequestMapping("/interfaceHead")
public class InterfaceHeadController {

	@Autowired
	private InterfaceHeadService<InterfaceHead> interfaceHeadService;
	
	@Autowired
	private IdaService idaService;

	@RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
	public @ResponseBody
	List<TreeNode> getAll() {
		List<InterfaceHead> heads = interfaceHeadService.getEntityAll();

		List<TreeNode> trees = new ArrayList<TreeNode>();
		TreeNode root = new TreeNode();
		root.setId("root");
		root.setText("报文头管理");
		for (InterfaceHead head : heads) {
			TreeNode tree = new TreeNode();
			tree.setId(head.getHeadId());
			tree.setText(head.getHeadName());
			trees.add(tree);
		}
		root.setChildren(trees);
		List<TreeNode> rootList = new ArrayList<TreeNode>();
		rootList.add(root);
		return rootList;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody
	boolean save(@RequestBody
	InterfaceHead head) {
		
		boolean add = false;
		if(head.getHeadId()==null || "".equals(head.getHeadId())){
			add = true;
		}
		interfaceHeadService.editEntity(head);
		
		//添加报文，自动生成固定报文头<root><request><response>
		if(add){
			//root
			Ida ida = new Ida();
			ida.setHeadId(head.getHeadId());
			ida.set_parentId(null);
			ida.setStructName("root");
			ida.setStructAlias("根节点");
			idaService.editEntity(ida);
			String parentId = ida.getId();
			
			ida = new Ida();
			ida.setHeadId(head.getHeadId());
			ida.set_parentId(parentId);
			ida.setStructName("request");
			ida.setStructAlias("请求头");
			idaService.editEntity(ida);
			
			ida = new Ida();
			ida.setHeadId(head.getHeadId());
			ida.set_parentId(parentId);
			ida.setStructName("response");
			ida.setStructAlias("响应头");
			idaService.editEntity(ida);
		}
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/edit/{headId}", headers = "Accept=application/json")
	public ModelAndView getInterfaceHead(@PathVariable
	String headId) {

		InterfaceHead head = interfaceHeadService.getInterfaceHead(headId);
		ModelAndView modelAndView = new ModelAndView();  
        modelAndView.addObject("head", head);  
        modelAndView.setViewName("sysadmin/header_edit");  
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/delete/{headId}", headers = "Accept=application/json")
	public @ResponseBody
	boolean delete(@PathVariable
			String headId) {
		interfaceHeadService.removeEntity(headId);
		
		List<Ida> idas = idaService.getEntityByName("headId", headId);
		for(Ida ida :idas){
			
			idaService.removeEntity(ida.getId());
		}
		return true;
	}
}
