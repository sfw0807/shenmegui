package com.dc.esb.servicegov.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.entity.InterfaceExtendInfo;
import com.dc.esb.servicegov.entity.SDANode4I;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.vo.InterfaceVo;
import com.dc.esb.servicegov.vo.SDA4I;

@Controller
@RequestMapping("/interface")
public class InterfaceShowController {
	
	@Autowired
	private ServiceManagerImpl serviceManager;
	  
	List<SDANode4I> lstSDA4I = new ArrayList<SDANode4I>();
	
	@RequestMapping(method = RequestMethod.GET, value = "/getInterfaceInfo/{id}", headers = "Accept=application/json")
	public @ResponseBody
	InterfaceVo getAllOperation(@PathVariable String id) {
		InterfaceVo vo = serviceManager.getInterfaceInfo(id);
		return vo;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getInterfaceExtendInfo/{id}", headers = "Accept=application/json")
	public @ResponseBody
	List<InterfaceExtendInfo> getInterfaceExtendInfo(@PathVariable String id) {
		InterfaceVo vo = serviceManager.getInterfaceInfo(id);
		return serviceManager.getInterfaceExtendInfo(vo.getResoutceId());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getInterfaceChildInfo/{id}", headers = "Accept=application/json")
	public @ResponseBody
	List<SDANode4I> getChildSDA4IInfo(@PathVariable String id) {
		try {
			lstSDA4I = new ArrayList<SDANode4I>();
			SDA4I sda =  serviceManager.getSDA4IofInterfaceId(id);
			renderSDA4I(sda);
		} catch (DataException e) {
			e.printStackTrace();
		}
		return lstSDA4I;
	}
	
	public void renderSDA4I(SDA4I sda) {
		SDANode4I node = sda.getValue();
		lstSDA4I.add(node);
		if (sda.getChildNode() != null) {
			List<SDA4I> childSda = sda.getChildNode();
			for (SDA4I a : childSda) {
				renderSDA4I(a);
			}
		}
	}
}
