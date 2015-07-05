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
		Map<String,String> reqMap = new HashMap<String,String>();
		reqMap.put("headId", headId);
		List<Ida> idas = idaService.findBy(reqMap, "seq");
		map.put("total", idas.size());
		map.put("rows", idas);
		return map;
	}  
	
	@RequestMapping(method = RequestMethod.GET, value = "/getInterfaces/{interfaceId}", headers = "Accept=application/json")
	public @ResponseBody
	Map<String,Object> getInterfaces(@PathVariable String interfaceId) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,String> reqMap = new HashMap<String,String>();
		reqMap.put("interfaceId", interfaceId);
		List<Ida> idas = idaService.findBy(reqMap,"seq");
		map.put("total", idas.size());
		map.put("rows", idas);
		return map;
	} 
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody
	boolean save(@RequestBody
	Ida [] idas) {
		for(Ida ida :idas){
			idaService.save(ida);
		}
		return true;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/delete/{id}", headers = "Accept=application/json")
	public @ResponseBody
	boolean delete(@RequestBody
	String [] ids) {
		for(String id:ids){
			idaService.deleteById(id);
		}
		return true;
	}

	/**
	 * TODO不满足需求，不要
	 * @param id
	 * @param seq
	 * @param id2
	 * @param seq2
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/modifySEQ/{id}/{seq}/{id2}/{seq2}", headers = "Accept=application/json")
	public @ResponseBody
	boolean modifySEQ(@PathVariable
	String id,@PathVariable int seq,@PathVariable String id2,@PathVariable int seq2) {
		
		String hql = "update Ida set seq = ? where id=?";
//		idaService.exeHql(hql, seq,id);
//		idaService.exeHql(hql, seq2,id2);
		return true;
	}
}
