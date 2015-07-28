package com.dc.esb.servicegov.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
	   boolean save(@RequestBody List list) {
  	 for (int i = 0; i < list.size(); i++) {
           LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) list.get(i);
           Set<String> keySet = map.keySet();
           SLATemplate slaTemplate=new SLATemplate();
           if(map.get("slaTemplateId")!=null&&!"".equals(map.get("slaTemplateId"))){
        	   slaTemplate.setSlaTemplateId(map.get("slaTemplateId"));
           }
           slaTemplate.setTemplateName(map.get("templateName"));
           slaTemplate.setDesc(map.get("desc"));
           slaTemplateServiceImpl.save(slaTemplate);
  	 }
      return true;
  }


	@RequestMapping(method = RequestMethod.DELETE, value = "/delete", headers = "Accept=application/json")
	public @ResponseBody
	boolean delete(@RequestBody List list) {
        for (int i = 0; i < list.size(); i++) {
            LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) list.get(i);
            Set<String> keySet = map.keySet();
            String id = map.get("slaTemplateId");
            slaTemplateServiceImpl.deleteById(id);
        }
        return true;
 
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addSla/{serviceId}/{operationId}/{slaTemplateId}", headers = "Accept=application/json")
	public @ResponseBody
	   boolean saveOla(@RequestBody List list,@PathVariable(value = "serviceId") String serviceId,
				@PathVariable(value = "operationId") String operationId,@PathVariable(value = "slaTemplateId") String slaTemplateId) {
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
            sla.setSlaTemplateId(slaTemplateId);
            slaServiceImpl.save(sla);
   	 }
       return true;
   }
}
