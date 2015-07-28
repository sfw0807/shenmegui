package com.dc.esb.servicegov.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.service.impl.OperationServiceImpl;
import com.dc.esb.servicegov.service.impl.ServiceServiceImpl;
import org.apache.commons.lang.StringUtils;
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
	@Autowired
	private ServiceServiceImpl serviceService;
	@Autowired
	OperationServiceImpl operationService;

	@RequestMapping(method = RequestMethod.POST, value = "/addList", headers = "Accept=application/json")
	public @ResponseBody
	boolean add(@RequestBody List list) {
		for (int i = 0; i < list.size(); i++) {
            LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) list.get(i);
            Set<String> keySet = map.keySet();
            SLA sla=new SLA();
            sla.setOperationId(map.get("operationId"));
            sla.setServiceId(map.get("serviceId"));
            sla.setSlaName(map.get("slaName"));
            sla.setSlaValue(map.get("slaValue"));
            sla.setSlaDesc(map.get("slaDesc"));
            sla.setSlaRemark(map.get("slaRemark"));
            slaServiceImpl.save(sla);
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
            SLA sla=new SLA();
            if(map.get("slaId")!=null&&!"".equals(map.get("slaId"))){
            	sla.setSlaId(map.get("slaId"));
            }
            sla.setOperationId(operationId);
            sla.setServiceId(serviceId);
            sla.setSlaName(map.get("slaName"));
            sla.setSlaValue(map.get("slaValue"));
            sla.setSlaDesc(map.get("slaDesc"));
            sla.setSlaRemark(map.get("slaRemark"));
            slaServiceImpl.save(sla);
   	 }
       return true;
   }

	@RequestMapping(method = RequestMethod.POST, value = "/modify", headers = "Accept=application/json")
	public @ResponseBody
	boolean modify(@RequestBody SLA sla) {
		slaServiceImpl.save(sla);
		return true;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete", headers = "Accept=application/json")
	public @ResponseBody
	boolean delete(@RequestBody List list) {
        for (int i = 0; i < list.size(); i++) {
            LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) list.get(i);
            Set<String> keySet = map.keySet();
            String id = map.get("slaId");
            slaServiceImpl.deleteById(id);
        }
        return true;
 
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAll/{serviceId}/{operationId}", headers = "Accept=application/json")
	public @ResponseBody
	List<SLA> getAll(@PathVariable(value = "serviceId") String serviceId,
			@PathVariable(value = "operationId") String operationId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("serviceId", serviceId);
		params.put("operationId", operationId);
		List<SLA> sla = slaServiceImpl.findBy(params);
		return sla;

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteAll/{serviceId}/{operationId}", headers = "Accept=application/json")
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

		ModelAndView mv = new ModelAndView("service/sla/slaPage");
		// 根据serviceId获取service信息
		if (StringUtils.isNotEmpty(serviceId)) {
			com.dc.esb.servicegov.entity.Service service = serviceService.getById(serviceId);
			if (service != null) {
				mv.addObject("service", service);
			}
			if (StringUtils.isNotEmpty(operationId)) {
				// 根据serviceId,operationId获取operation信息
				Operation operation = operationService.getOperation(
						serviceId, operationId);
				if (operation != null) {
					mv.addObject("operation", operation);
				}
			}
		}
		return mv;
	}
}
