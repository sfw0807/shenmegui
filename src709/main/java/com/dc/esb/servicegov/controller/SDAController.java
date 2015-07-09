package com.dc.esb.servicegov.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.entity.SDA;
import com.dc.esb.servicegov.service.impl.SDAServiceImpl;
import com.dc.esb.servicegov.util.TreeNode;

@Controller
@RequestMapping("sda")
public class SDAController {
	@Autowired
	SDAServiceImpl serviceImpl;
	
	//根据serviceId,operationId获取服务数据协议信息
	@RequestMapping("/sdaPage")
	public ModelAndView sdaPage(String operationId, String serviceId, HttpServletRequest req){
		return serviceImpl.sdaPage(operationId, serviceId, req);
	}
	//根据serviceId，operationId获取sda树
	@RequestMapping("/sdaTree")
	@ResponseBody
	public List<TreeNode> getSDATree(String serviceId, String operationId){
		return serviceImpl.genderSDATree(serviceId, operationId);
	}
	//生成一个sdaId
	@RequestMapping("/genderSDAUuid")
	@ResponseBody
	public String genderSDAUuid(){
		String result = UUID.randomUUID().toString();
		return result;
	}
	//保存对象数组
	@RequestMapping(method = RequestMethod.POST, value = "/saveSDA", headers = "Accept=application/json")
	@ResponseBody
	public boolean saveSDA(@RequestBody SDA[] sdas){
		return serviceImpl.save(sdas);
	}
	
	//删除数据
	@RequestMapping(method = RequestMethod.POST, value = "/deleteSDA", headers = "Accept=application/json")
	@ResponseBody
	public boolean deleteSDA(@RequestBody String[] delIds){
		return serviceImpl.delete(delIds);
	}
	
	//
	@RequestMapping("/moveUp")
	@ResponseBody
	public boolean moveUp(String sdaId){
		return serviceImpl.moveUp(sdaId);
	}
	
	@RequestMapping("/moveDown")
	@ResponseBody
	public boolean moveDown(String sdaId){
		return serviceImpl.moveDown(sdaId);
	}
}
