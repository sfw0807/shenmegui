package com.dc.esb.servicegov.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.service.impl.BaseLineServiceImpl;

@Controller
@RequestMapping("/baseLine")
public class BaseLineController {
	@Autowired
	private BaseLineServiceImpl baseLineServiceImpl;
	@RequestMapping("/operationList")
	@ResponseBody
	public Map<String, Object> operationList() {
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<?> rows = baseLineServiceImpl.operationList();

		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}
	
	@RequestMapping("/operationHisList")
	@ResponseBody
	public Map<String, Object> operationHisList() {
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<?> rows = baseLineServiceImpl.operationHisList();

		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/release", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean release(HttpServletRequest req, String code, String desc,@RequestBody String[] versionHisIds){
		return baseLineServiceImpl.release(req, code, desc, versionHisIds);
	}
	
	@RequestMapping("/getBaseLine")
	@ResponseBody
	public Map<String, Object> getBaseLine(String code, String blDesc) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<?> rows = baseLineServiceImpl.getBaseLine(code, blDesc);

		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}
	@RequestMapping("/getBLOperationHiss")
	@ResponseBody
	public List<?> getBLOperationHiss(String baseId){
		return baseLineServiceImpl.getBLOperationHiss(baseId);
	}
	@RequestMapping("/getBLInvoke")
	@ResponseBody
	public List<?> getBLInvoke(String baseId){
		return baseLineServiceImpl.getBLInvoke(baseId);
	}
}
