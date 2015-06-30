package com.dc.esb.servicegov.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.entity.SLA;
import com.dc.esb.servicegov.entity.SLATemplate;
import com.dc.esb.servicegov.service.impl.SLAServiceImpl;
import com.dc.esb.servicegov.service.impl.SLATemplateServiceImpl;

@Controller
@RequestMapping("/slaTemplate")
public class SLATemplateController {
	@Autowired
	private SLATemplateServiceImpl slaTemplateServiceImpl;
	@Autowired
	private SLAServiceImpl slaServiceImpl;

	@RequestMapping(method = RequestMethod.GET, value = "/getSLA/{templateId}", headers = "Accept=application/json")
	public @ResponseBody
	List<SLA> getSLA(@PathVariable(value = "templateId") String templateId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("slaTemplateId", templateId);
		return slaServiceImpl.getTemplateSLA(params);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
	public @ResponseBody
	List<SLATemplate> getAll() {
		return slaTemplateServiceImpl.getAll();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody
	boolean add(@RequestBody SLATemplate slaTemplate) {
		slaTemplate.setSlaTemplateId(UUID.randomUUID().toString());
		slaTemplateServiceImpl.save(slaTemplate);
		return true;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/modify", headers = "Accept=application/json")
	public @ResponseBody
	boolean modify(@RequestBody SLATemplate slaTemplate) {
		slaTemplateServiceImpl.save(slaTemplate);
		return true;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}", headers = "Accept=application/json")
	public @ResponseBody
	boolean delete(@PathVariable String id) {
		slaTemplateServiceImpl.deleteById(id);
		return true;
	}

}
