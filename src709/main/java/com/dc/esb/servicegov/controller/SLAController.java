package com.dc.esb.servicegov.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.dc.esb.servicegov.entity.SLA;
import com.dc.esb.servicegov.entity.System;
import com.dc.esb.servicegov.service.impl.SLAServiceImpl;

@Controller
@RequestMapping("/sla")
public class SLAController {
	@Autowired
	private SLAServiceImpl slaServiceImpl;

	@RequestMapping(method = RequestMethod.POST, value = "/addList", headers = "Accept=application/json")
	public @ResponseBody
	boolean add(@RequestBody SLA[] slas) {
		for(SLA sla : slas){
			slaServiceImpl.save(sla);
		}
		return true;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody
	boolean add(@RequestBody SLA sla) {
		
		slaServiceImpl.save(sla);
		return true;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/modify", headers = "Accept=application/json")
	public @ResponseBody
	boolean modify(@RequestBody SLA sla) {
		slaServiceImpl.save(sla);
		return true;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}", headers = "Accept=application/json")
	public @ResponseBody
	boolean delete(@PathVariable String id) {
		slaServiceImpl.deleteById(id);
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAll/{serviceId}/{operationId}", headers = "Accept=application/json")
	public @ResponseBody
	Map<String,Object> getAll(@PathVariable(value = "serviceId") String serviceId,
			@PathVariable(value = "operationId") String operationId,HttpServletRequest req) {
		String starpage = req.getParameter("page");
	    String rows = req.getParameter("rows");

	    StringBuffer hql = new StringBuffer("select s from SLA s where s.slaTemplateId = null");
	    
	    hql.append(" and s.serviceId = ? and s.operationId = ?");
	    
	    List<SearchCondition> searchConds = new ArrayList<SearchCondition>();
		
	    SearchCondition search = new SearchCondition();
	    search.setFieldValue(serviceId);
	    searchConds.add(search);
	    
	    search = new SearchCondition();
	    search.setFieldValue(operationId);
	    searchConds.add(search);
	    
	    Page page = slaServiceImpl.findPage(hql.toString(), Integer.parseInt(rows), searchConds);
        page.setPage(Integer.parseInt(starpage));

        List<SLA> sla = slaServiceImpl.findBy(hql.toString(),page,searchConds);
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("total", page.getResultCount());
		resMap.put("rows",sla);
		return resMap;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{serviceId}/{operationId}", headers = "Accept=application/json")
	public @ResponseBody
	boolean deleteBySOId(@PathVariable(value = "serviceId") String serviceId,
			@PathVariable(value = "operationId") String operationId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("serviceId", serviceId);
		params.put("operationId", operationId);
		List<SLA> slas = slaServiceImpl.findBy(params);
		for (SLA sla : slas) {
			slaServiceImpl.delete(sla);
		}
		return true;
	}

	@RequestMapping("/slaPage")
	public ModelAndView olaPage(String operationId, String serviceId, HttpServletRequest req){
		return slaServiceImpl.slaPage(operationId, serviceId, req);
	}
}
