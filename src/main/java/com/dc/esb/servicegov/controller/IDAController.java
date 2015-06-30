package com.dc.esb.servicegov.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.entity.Ida;
import com.dc.esb.servicegov.service.IdaService;

@Controller
@RequestMapping("/ida")
public class IDAController {
	
	@Autowired
	IdaService idaService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/getHeads/{headId}", headers = "Accept=application/json")
	public @ResponseBody
	Map<String,Object> getHeads(@PathVariable String headId) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String, String> params = new HashMap<String, String>();
		params.put("headId", headId);
		List<Ida> idas = idaService.findBy(params);
		map.put("total", idas.size());
		map.put("rows", idas);
		return map;
	} 
	
	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody
	boolean save(@RequestBody
	Ida ida) {
		idaService.save(ida);
		return true;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/delete/{id}", headers = "Accept=application/json")
	public @ResponseBody
	boolean delete(@PathVariable
	String id) {
		idaService.deleteById(id);
		return true;
	}
}
