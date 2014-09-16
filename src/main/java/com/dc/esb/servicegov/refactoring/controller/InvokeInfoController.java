package com.dc.esb.servicegov.refactoring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.service.impl.InvokeInfoManagerImpl;
import com.dc.esb.servicegov.refactoring.vo.SystemInvokeServiceInfo;
import com.dc.esb.servicegov.refactoring.vo.TableViewBean;

@Controller
@RequestMapping("/invokeInfo")
public class InvokeInfoController {
	
	@Autowired
	private InvokeInfoManagerImpl invokeInfoManager;
	
	@RequestMapping(method = RequestMethod.GET, value = "/syssvc", headers = "Accept=application/json")
	public 
	@ResponseBody List<SystemInvokeServiceInfo>
	getSystemServiceInvokeInfo(){
		List<SystemInvokeServiceInfo> systemInvokeServiceInfos = null;
		systemInvokeServiceInfos = invokeInfoManager.getSystemInvokeServiceInfo();
		return systemInvokeServiceInfos;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/serverside/syssvc", headers = "Accept=application/json")
	public 
	@ResponseBody TableViewBean<SystemInvokeServiceInfo> 
	getSystemServiceInvokeInfo(@RequestParam("sEcho") String sEcho){
		TableViewBean<SystemInvokeServiceInfo> tableViewBean = new TableViewBean<SystemInvokeServiceInfo>();
		tableViewBean.setiTotalDisplayRecords("25");
		tableViewBean.setsEcho("2");
		List<SystemInvokeServiceInfo> systemInvokeServiceInfos = null;
		systemInvokeServiceInfos = invokeInfoManager.getSystemInvokeServiceInfo();
		tableViewBean.setiTotalRecords(String.valueOf(systemInvokeServiceInfos.size()));
		tableViewBean.setAaData(systemInvokeServiceInfos);
		return tableViewBean;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/all", headers = "Accept=application/json")
	public 
	@ResponseBody List<InvokeInfo>
	getAllInvokeInfo(){
		List<InvokeInfo> invokeInfos = null;
		invokeInfos = invokeInfoManager.getInvokeInfos();
		return invokeInfos;
	}

    @RequestMapping(method = RequestMethod.POST, value = "/test", headers = "Accept=application/json")
    public
    @ResponseBody
    void addAmmeter(@RequestBody Test ammeter) {
        System.out.println(ammeter);
    }





}
