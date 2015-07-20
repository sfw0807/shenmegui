package com.dc.esb.servicegov.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.dc.esb.servicegov.entity.*;
import com.dc.esb.servicegov.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.mvel2.ast.Proto;
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

	@Autowired
	private InterfaceHeadService interfaceHeadService;

	@Autowired
	private InterfaceHeadRelateService interfaceHeadRelateService;

	@Autowired
	ProtocolService protocolService;

	@RequestMapping(method = RequestMethod.GET, value = "/getLeftTree", headers = "Accept=application/json")
	public @ResponseBody
	List<TreeNode> getLeftTree() {

		List<TreeNode> resList = new ArrayList<TreeNode>();
		TreeNode root = new TreeNode();
		root.setId("root");
		root.setText("系统");
		root.setClick("system");

		List<TreeNode> rootList = new ArrayList<TreeNode>();
		List<com.dc.esb.servicegov.entity.System> systems = systemService
				.getAll();

		for (com.dc.esb.servicegov.entity.System s : systems) {
			TreeNode rootinterface = new TreeNode();
			rootinterface.setId(s.getSystemId());
			rootinterface.setText(s.getSystemChineseName());
			rootinterface.setClick("disable");
			try {
				List<ServiceInvoke> serviceIns = s.getServiceInvokes();
				List<TreeNode> childList = new ArrayList<TreeNode>();
				for (ServiceInvoke si : serviceIns) {

					TreeNode child = new TreeNode();
					child.setId(si.getInter().getInterfaceId());
					child.setText(si.getInter().getInterfaceName() + "(" + si.getInter().getInterfaceId() + ")");
					if(!contains(childList,child)){
						childList.add(child);
					}

				}
				Collections.sort(childList, new Comparator<TreeNode>() {

					@Override
					public int compare(TreeNode o1, TreeNode o2) {
						return o1.getText().compareToIgnoreCase(o2.getText());
					}

				});
				rootinterface.setChildren(childList);
			} catch (Exception e) {
			}

			rootList.add(rootinterface);
		}

		root.setChildren(rootList);
		resList.add(root);
		return resList;
	}

	private boolean contains(List<TreeNode> childList,TreeNode treeNode){
		for(TreeNode node:childList){
			if(node.getId().equals(treeNode.getId())){
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody
	boolean save(@RequestBody
	Interface inter,HttpServletRequest request) {
		
		//新增操作
		boolean add = "add".equals(request.getParameter("type"));
		if(!add){


			String hql = "update ServiceInvoke set protocolId = ? where interfaceId = ?";
			serviceInvokeService.updateProtocolId(hql,inter.getServiceInvoke().get(0).getProtocolId(),inter.getInterfaceId());
			//修改接口关系表不更新
			inter.setServiceInvoke(null);
		}
		interfaceService.save(inter);
		if(add){
			//ServiceInvoke si = inter.getServiceInvoke();
			//si.setInterfaceId(inter.getInterfaceId());
			//serviceInvokeService.save(si);
			
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

	@RequestMapping(method = RequestMethod.GET, value = "/getInterById/{interfaceId}", headers = "Accept=application/json")
	public @ResponseBody Map<String,Object>  getInterById(@PathVariable
									 String interfaceId) {
		//String hql = "SELECT u.interfaceId,u.interfaceName,u.ecode,u.remark,u.status,u.version,u.optUser,u.optDate FROM Interface u WHERE interfaceId = ?";
		Interface inter =interfaceService.getById(interfaceId	);

		Interface resInter = new Interface();
		resInter.setInterfaceId(inter.getInterfaceId());
		resInter.setInterfaceName(inter.getInterfaceName());
		resInter.setEcode(inter.getEcode());
		resInter.setDesc(inter.getDesc());
		resInter.setRemark(inter.getRemark());
		resInter.setVersion(inter.getVersion());
		resInter.setOptDate(inter.getOptDate());
		resInter.setOptUser(inter.getOptUser());
		List<InterfaceHeadRelate> heads = inter.getHeadRelates();
		String headName = "";
		for(InterfaceHeadRelate head:heads){
			if(!"".equals(headName)){
				headName += ",";
			}
			headName += head.getInterfaceHead().getHeadName();
		}
		resInter.setHeadName(headName);
		Map<String,Object> map = new HashMap<String,Object>();

		List<Interface> inters = new ArrayList<Interface>();
		inters.add(resInter);
		map.put("total", 1);
		map.put("rows", inters);
		return map;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/getInterface/{systemId}", headers = "Accept=application/json")
	public @ResponseBody Map<String,Object> getInterfaceBySystemId(@PathVariable String systemId,HttpServletRequest req){
		
		String starpage = req.getParameter("page");
		
		String rows = req.getParameter("rows");
		
		String ecode = req.getParameter("ecode");
		String interfaceName = req.getParameter("interfaceName");
		String remark = req.getParameter("remark");
		String status = req.getParameter("status");
		String protocolId = req.getParameter("protocolId");
		String headId = req.getParameter("headId");

		List<SearchCondition> searchConds = new ArrayList<SearchCondition> ();
		
		StringBuffer hql = new StringBuffer("SELECT distinct t1 FROM Interface t1,ServiceInvoke t2 where t1.interfaceId=t2.interfaceId ");
		hql.append("and t2.systemId=? " );
		
		SearchCondition searchCond = new SearchCondition();
		
		searchCond.setField("systemId");
		searchCond.setFieldValue(systemId);
		searchConds.add(searchCond);
		if(ecode!=null && !"".equals(ecode)){
			searchCond = new SearchCondition();
			hql.append(" and t1.ecode=?");
			searchCond.setField("ecode");
			searchCond.setFieldValue(ecode);
			searchConds.add(searchCond);
		}
		if(interfaceName!=null && !"".equals(interfaceName)){
			searchCond = new SearchCondition();
			hql.append( " and t1.interfaceName=?");
			searchCond = new SearchCondition();
			searchCond.setField("interfaceName");
			searchCond.setFieldValue(interfaceName);
			searchConds.add(searchCond);
		}
		if(remark!=null && !"".equals(remark)){
			searchCond = new SearchCondition();
			hql.append( " and t1.remark like ?");
			searchCond = new SearchCondition();
			searchCond.setField("remark");
			searchCond.setFieldValue("%" + remark +"%");
			searchConds.add(searchCond);
		}
		if(status!=null && !"".equals(status)){
			searchCond = new SearchCondition();
			hql.append( " and t1.status=?");
			searchCond = new SearchCondition();
			searchCond.setField("status");
			searchCond.setFieldValue(status);
			searchConds.add(searchCond);
		}
		if(protocolId!=null && !"".equals(protocolId)){
			searchCond = new SearchCondition();
			hql.append( " and t2.protocolId=?");
			searchCond = new SearchCondition();
			searchCond.setField("protocolId");
			searchCond.setFieldValue(protocolId);
			searchConds.add(searchCond);
		}
		if(headId!=null && !"".equals(headId)){
			searchCond = new SearchCondition();
			hql.append( " and exists (select 1 from InterfaceHeadRelate t3 WHERE t3.interfaceId = t1.interfaceId and t3.headId = ?)");
			searchCond = new SearchCondition();
			searchCond.setField("headId");
			searchCond.setFieldValue(headId);
			searchConds.add(searchCond);
		}


		Page page = interfaceService.findPage(hql.toString(), Integer.parseInt(rows), searchConds);
		page.setPage(Integer.parseInt(starpage));
		
		hql.append(" order by t1.interfaceName ");
		
		List<Interface> inters = interfaceService.findBy(hql.toString(),page,searchConds);
		for(Interface i :inters){

			List<InterfaceHeadRelate> heads = i.getHeadRelates();
			String headName = "";
			if(heads!=null) {
				for (InterfaceHeadRelate head : heads) {
					if (!"".equals(headName)) {
						headName += ",";
					}
					headName += head.getInterfaceHead().getHeadName();
				}
			}
			if(i.getServiceInvoke()!=null && i.getServiceInvoke().size()>0) {
				ServiceInvoke invoke = i.getServiceInvoke().get(0);
				if (invoke.getProtocolId() != null && !"".equals(invoke.getProtocolId())) {
					Protocol p = protocolService.getById(invoke.getProtocolId());
					if (p != null) {
						i.setProtocolName(p.getProtocolName());
					}
				}
			}
			i.setHeadName(headName);
			//避免转化json错误，设置ServiceInvoke=null;
			i.setServiceInvoke(null);
			i.setHeadRelates(null);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", page.getResultCount());
		map.put("rows", inters);
		return map;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getHeadAll", headers = "Accept=application/json")
	public @ResponseBody List<Map<String,Object>> getHeadAll(HttpServletRequest request) {
		List<Map<String,Object>> resList = new ArrayList<Map<String, Object>>();
		List<InterfaceHead> heads =  interfaceHeadService.getAll();
		Map<String,Object> map = new HashMap<String, Object>();
		if(request.getParameter("query")!=null && !"".equals(request.getParameter("query"))) {
			map.put("id", "");
			map.put("text", "全部");
			resList.add(map);
		}

		for (InterfaceHead head :heads){
			map = new HashMap<String, Object>();
			map.put("id",head.getHeadId());
			map.put("text",head.getHeadName());
			resList.add(map);
		}
		return resList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getChecked/{interfaceId}", headers = "Accept=application/json")
	public @ResponseBody List<String> getChecked(@PathVariable String interfaceId) {
		List<String> resList = new ArrayList<String>();
//		Interface head =  interfaceService.getById(interfaceId);
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("interfaceId",interfaceId);
		List<InterfaceHeadRelate> heads = interfaceHeadRelateService.findBy(paramMap);
		for(InterfaceHeadRelate h:heads){
			resList.add(h.getHeadId());
		}
		return resList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/headRelate/{interfaceId}/{headIds}", headers = "Accept=application/json")
	public @ResponseBody boolean headRelate(@PathVariable String interfaceId,@PathVariable String headIds) {
		if(interfaceId!=null && headIds!=null){
			interfaceHeadRelateService.relateSave(interfaceId,headIds);
		}
		return true;
	}


	@RequestMapping(method = RequestMethod.GET, value = "/check/{interfaceId}", headers = "Accept=application/json")
	public @ResponseBody
	boolean check(@PathVariable String interfaceId) {
		Interface inter = interfaceService.findUniqueBy("interfaceId",interfaceId);
		if(inter!=null) {
			return true;
		}
		return  false;
	}
}
