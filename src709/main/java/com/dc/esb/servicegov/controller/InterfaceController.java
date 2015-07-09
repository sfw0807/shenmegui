package com.dc.esb.servicegov.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.dao.support.Page;
import com.dc.esb.servicegov.dao.support.SearchCondition;
import com.dc.esb.servicegov.entity.Ida;
import com.dc.esb.servicegov.entity.Interface;
import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.IdaService;
import com.dc.esb.servicegov.service.InterfaceService;
import com.dc.esb.servicegov.service.ServiceInvokeService;
import com.dc.esb.servicegov.service.SystemService;
import com.dc.esb.servicegov.util.TreeNode;

@Controller
@RequestMapping("/interface")
public class InterfaceController {

	private static final Log log = LogFactory.getLog(InterfaceController.class);

	@Autowired
	private InterfaceService interfaceService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private ServiceInvokeService serviceInvokeService;
	
	@Autowired
	private IdaService idaService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/getLeftTree", headers = "Accept=application/json")
	public @ResponseBody
	List<TreeNode> getLeftTree() {
		List<TreeNode> rootList = new ArrayList<TreeNode>();
		List<com.dc.esb.servicegov.entity.System> systems = systemService.getAll();
		for(com.dc.esb.servicegov.entity.System s:systems){
			TreeNode root = new TreeNode();
			root.setId(s.getSystemId());
			root.setText(s.getSystemChineseName());
			root.setClick("disable");
			try {
				List<ServiceInvoke> serviceIns = s.getServiceInvokes();
				List<TreeNode> childList = new ArrayList<TreeNode>();
				for(ServiceInvoke si : serviceIns){
					TreeNode child = new TreeNode();
					child.setId(si.getInter().getInterfaceId());
					child.setText(si.getInter().getInterfaceName());
					childList.add(child);
				}
				Collections.sort(childList,new Comparator<TreeNode>(){
					@Override
					public int compare(TreeNode o1, TreeNode o2) {
						return o1.getText().compareToIgnoreCase(o2.getText());
					}
				});
				root.setChildren(childList);
			} catch (Exception e) {
				String errorMsg = "构建树失败!";
				log.error(errorMsg, e);
			}
			rootList.add(root);
		}
		return rootList;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody
	boolean save(@RequestBody
	Interface inter) {
		
		//新增操作
		boolean add = false;
		if(inter.getInterfaceId()==null || "".equals(inter.getInterfaceId())){
			add = true;
		}
		interfaceService.save(inter);
		if(add){
			ServiceInvoke si = inter.getServiceInvoke();
			si.setInterfaceId(inter.getInterfaceId());
			serviceInvokeService.save(si);
			
			//添加报文，自动生成固定报文头<root><request><response>
			//root
			Ida ida = new Ida();
			ida.setInterfaceId(inter.getInterfaceId());
			ida.set_parentId(null);
			ida.setStructName("root");
			ida.setStructAlias("根节点");
			idaService.save(ida);
			String parentId = ida.getId();
			
			ida = new Ida();
			ida.setInterfaceId(inter.getInterfaceId());
			ida.set_parentId(parentId);
			ida.setStructName("request");
			ida.setStructAlias("请求头");
			ida.setSeq(0);
			idaService.save(ida);
			
			ida = new Ida();
			ida.setInterfaceId(inter.getInterfaceId());
			ida.set_parentId(parentId);
			ida.setSeq(1);
			ida.setStructName("response");
			ida.setStructAlias("响应头");
			idaService.save(ida);
		}
		return true;
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/delete/{interfaceId}", headers = "Accept=application/json")
	public @ResponseBody
	boolean delete(@PathVariable
			String interfaceId) {
		//删除IDA
		Map<String, String> interfaceParams = new HashMap<String, String>();
		interfaceParams.put("interfaceId", interfaceId);
		List<Ida> idas = idaService.findBy(interfaceParams);
		for(Ida ida :idas){
			idaService.deleteById(ida.getId());
		}
		//删除InvokeRelation
		Map<String, String> invokeParams = new HashMap<String, String>();
		interfaceParams.put("interfaceId", interfaceId);
		List<ServiceInvoke> sis = serviceInvokeService.findBy(invokeParams);
		for(ServiceInvoke s :sis){
			serviceInvokeService.deleteById(s.getInvokeId());
		}
		//删除Interface
		interfaceService.deleteById(interfaceId);
		return true;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/edit/{interfaceId}", headers = "Accept=application/json")
	public ModelAndView getInterface(@PathVariable
	String interfaceId) {

		Interface inter = interfaceService.getById(interfaceId);
		ModelAndView modelAndView = new ModelAndView();  
        modelAndView.addObject("inter", inter);  
        modelAndView.setViewName("interface/interface_edit");  
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/getInterface/{systemId}", headers = "Accept=application/json")
	public @ResponseBody Map<String,Object> getInterfaceBySystemId(@PathVariable String systemId,HttpServletRequest req){
		
		String starpage = req.getParameter("page");
		
		String rows = req.getParameter("rows");
		
		String ecode = req.getParameter("ecode");
		String interfaceName = req.getParameter("interfaceName");
		
		List<SearchCondition> searchConds = new ArrayList<SearchCondition> ();
		
		String hql = "SELECT t1 FROM Interface t1,ServiceInvoke t2 where t1.interfaceId=t2.interfaceId " +
		"and t2.systemId=? ";
		
		SearchCondition searchCond = new SearchCondition();
		
		searchCond.setField("systemId");
		searchCond.setFieldValue(systemId);
		searchConds.add(searchCond);
		if(ecode!=null && !"".equals(ecode)){
			hql +=" and t1.ecode=?";
			searchCond.setField("ecode");
			searchCond.setFieldValue(ecode);
			searchConds.add(searchCond);
		}
		if(interfaceName!=null && !"".equals(interfaceName)){
			hql += " and t1.interfaceName=?";
			searchCond = new SearchCondition();
			searchCond.setField("interfaceName");
			searchCond.setFieldValue(interfaceName);
			searchConds.add(searchCond);
		}
		Page page = interfaceService.findPage(hql, Integer.parseInt(rows), searchConds);
		page.setPage(Integer.parseInt(starpage));
		
		hql += " order by t1.interfaceName ";
		
		List<Interface> inters = interfaceService.findBy(hql,page,searchConds);
		for(Interface i :inters){
			//避免转化json错误，设置ServiceInvoke=null;
			i.setServiceInvoke(null);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", page.getResultCount());
		map.put("rows", inters);
		return map;
		
	}
}
