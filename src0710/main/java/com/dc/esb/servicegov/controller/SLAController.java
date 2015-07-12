package com.dc.esb.servicegov.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.entity.SLA;
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
	List<SLA> getAll(@PathVariable(value = "serviceId") String serviceId,
			@PathVariable(value = "operationId") String operationId) {
		List<SLA> sla = slaServiceImpl.findByOS(serviceId, operationId);
		return sla;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{serviceId}/{operationId}", headers = "Accept=application/json")
	public @ResponseBody
	boolean deleteBySOId(@PathVariable(value = "serviceId") String serviceId,
			@PathVariable(value = "operationId") String operationId) {
		List<SLA> slas = slaServiceImpl.findByOS(serviceId, operationId);
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
