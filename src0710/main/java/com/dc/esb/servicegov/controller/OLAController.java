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

import com.dc.esb.servicegov.entity.OLA;
import com.dc.esb.servicegov.service.impl.OLAServiceImpl;

@Controller
@RequestMapping("/ola")
public class OLAController {
	@Autowired
	private OLAServiceImpl olaServiceImpl;
	
	@RequestMapping(method = RequestMethod.POST, value = "/addList", headers = "Accept=application/json")
	public @ResponseBody
	boolean add(@RequestBody OLA[] olas) {
		for(OLA ola : olas){
			olaServiceImpl.save(ola);
		}
		return true;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody
	boolean add(@RequestBody OLA ola) {
		olaServiceImpl.save(ola);
		return true;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/modify", headers = "Accept=application/json")
	public @ResponseBody
	boolean modify(@RequestBody OLA ola) {
		olaServiceImpl.save(ola);
		return true;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}", headers = "Accept=application/json")
	public @ResponseBody
	boolean delete(@PathVariable String id) {
		olaServiceImpl.deleteById(id);
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAll/{serviceId}/{operationId}", headers = "Accept=application/json")
	public @ResponseBody
	List<OLA> getAll(@PathVariable(value = "serviceId") String serviceId,
			@PathVariable(value = "operationId") String operationId) {
		List<OLA> ola = olaServiceImpl.findByOS(serviceId, operationId);
		return ola;

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{serviceId}/{operationId}", headers = "Accept=application/json")
	public @ResponseBody
	boolean deleteBySOId(@PathVariable(value = "serviceId") String serviceId,
			@PathVariable(value = "operationId") String operationId) {
		List<OLA> olas = olaServiceImpl.findByOS(serviceId, operationId);
		for (OLA ola : olas) {
			olaServiceImpl.delete(ola);
		}
		return true;
	}
	
	@RequestMapping("/olaPage")
	public ModelAndView olaPage(String operationId, String serviceId, HttpServletRequest req){
		return olaServiceImpl.olaPage(operationId, serviceId, req);
	}
}
