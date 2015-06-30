package com.dc.esb.servicegov.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.service.impl.ServiceServiceImpl;

@Controller
@RequestMapping("/service")
public class ServiceController {
	@Autowired
	private ServiceServiceImpl serviceServiceImpl;
	
	@RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Service> getAll() {
		return serviceServiceImpl.getAll();
	}

	//根据服务id跳转到服务基本信息页面 
	@RequestMapping("/serviceGrid")
	public ModelAndView serviceGrid(String serviceId){
		ModelAndView mv = new ModelAndView("service/serviceGrid");
		Service entity = serviceServiceImpl.getUniqueByServiceId(serviceId);
		if(entity != null){
			mv.addObject("entity", entity);
		}
		return mv;
	}
	
}
