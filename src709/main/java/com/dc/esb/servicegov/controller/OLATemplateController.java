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

import com.dc.esb.servicegov.entity.OLA;
import com.dc.esb.servicegov.entity.OLATemplate;
import com.dc.esb.servicegov.service.impl.OLAServiceImpl;
import com.dc.esb.servicegov.service.impl.OLATemplateServiceImpl;


@Controller
@RequestMapping("/olaTemplate")
public class OLATemplateController {
	@Autowired
	private OLATemplateServiceImpl olaTemplateServiceImpl;
	@Autowired
	private OLAServiceImpl olaServiceImpl;

	@RequestMapping(method = RequestMethod.GET, value = "/getOLA/{templateId}", headers = "Accept=application/json")
	public @ResponseBody
	List<OLA> getOLA(@PathVariable(value = "templateId") String templateId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("olaTemplateId", templateId);
		return olaServiceImpl.getTemplateOLA(params);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
	public @ResponseBody
	List<OLATemplate> getAll() {
		return olaTemplateServiceImpl.getAll();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody
	boolean add(@RequestBody OLATemplate olaTemplate) {
		olaTemplate.setOlaTemplateId(UUID.randomUUID().toString());
		olaTemplateServiceImpl.save(olaTemplate);
		return true;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/modify", headers = "Accept=application/json")
	public @ResponseBody
	boolean modify(@RequestBody OLATemplate olaTemplate) {
		olaTemplateServiceImpl.save(olaTemplate);
		return true;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}", headers = "Accept=application/json")
	public @ResponseBody
	boolean delete(@PathVariable String id) {
		olaTemplateServiceImpl.deleteById(id);
		return true;
	}

}
