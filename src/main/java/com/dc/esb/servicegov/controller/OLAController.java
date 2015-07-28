package com.dc.esb.servicegov.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.entity.EnglishWord;
import com.dc.esb.servicegov.entity.OLA;
import com.dc.esb.servicegov.service.impl.OLAServiceImpl;

@Controller
@RequestMapping("/ola")
public class OLAController {
	@Autowired
	private OLAServiceImpl olaServiceImpl;
	
	@RequestMapping(method = RequestMethod.POST, value = "/addList", headers = "Accept=application/json")
	public @ResponseBody
	boolean add(@RequestBody List list) {
		for (int i = 0; i < list.size(); i++) {
            LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) list.get(i);
            Set<String> keySet = map.keySet();
            OLA ola=new OLA();
            ola.setOperationId(map.get("operationId"));
            ola.setServiceId(map.get("serviceId"));
            ola.setOlaName(map.get("olaName"));
            ola.setOlaValue(map.get("olaValue"));
            ola.setOlaDesc(map.get("olaDesc"));
            ola.setOlaRemark(map.get("olaRemark"));
            olaServiceImpl.save(ola);
   	 }
		return true;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add/{serviceId}/{operationId}", headers = "Accept=application/json")
	public @ResponseBody
	   boolean save(@RequestBody List list,@PathVariable(value = "serviceId") String serviceId,
				@PathVariable(value = "operationId") String operationId) {
   	 for (int i = 0; i < list.size(); i++) {
            LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) list.get(i);
            Set<String> keySet = map.keySet();
            OLA ola=new OLA();
            if(map.get("olaId")!=null&&!"".equals(map.get("olaId"))){
            	ola.setOlaId(map.get("olaId"));
            }
            ola.setOperationId(operationId);
            ola.setServiceId(serviceId);
            ola.setOlaName(map.get("olaName"));
            ola.setOlaValue(map.get("olaValue"));
            ola.setOlaDesc(map.get("olaDesc"));
            ola.setOlaRemark(map.get("olaRemark"));
            olaServiceImpl.save(ola);
   	 }
       return true;
   }

	@RequestMapping(method = RequestMethod.POST, value = "/modify", headers = "Accept=application/json")
	public @ResponseBody
	boolean modify(@RequestBody OLA ola) {
		olaServiceImpl.save(ola);
		return true;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete", headers = "Accept=application/json")
	public @ResponseBody
	boolean delete(@RequestBody List list) {
        for (int i = 0; i < list.size(); i++) {
            LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) list.get(i);
            Set<String> keySet = map.keySet();
            String id = map.get("olaId");
            olaServiceImpl.deleteById(id);
        }
        return true;
 
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAll/{serviceId}/{operationId}", headers = "Accept=application/json")
	public @ResponseBody
	List<OLA> getAll(@PathVariable(value = "serviceId") String serviceId,
			@PathVariable(value = "operationId") String operationId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("serviceId", serviceId);
		params.put("operationId", operationId);
		List<OLA> ola = olaServiceImpl.findBy(params);
		return ola;

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteAll/{serviceId}/{operationId}", headers = "Accept=application/json")
	public @ResponseBody
	boolean deleteBySOId(@PathVariable(value = "serviceId") String serviceId,
			@PathVariable(value = "operationId") String operationId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("serviceId", serviceId);
		params.put("operationId", operationId);
		List<OLA> olas = olaServiceImpl.findBy(params);
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
