package com.dc.esb.servicegov.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.entity.ServiceInvokeRelation;
import com.dc.esb.servicegov.service.impl.ServiceInvokeRelationManagerImpl;

@Controller
@RequestMapping("/serviceInvokeRelation")
public class ServiceInvokeRelationController {
	
	@Autowired
	private ServiceInvokeRelationManagerImpl serviceInvokeRelationManager;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/listDup", headers = "Accept=application/json")
    private
    @ResponseBody
    List<ServiceInvokeRelation> getDupRelation() {
		return serviceInvokeRelationManager.getDupRealtion();
	}
	
}
