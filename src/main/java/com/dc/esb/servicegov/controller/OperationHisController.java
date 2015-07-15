package com.dc.esb.servicegov.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.entity.OperationHis;
import com.dc.esb.servicegov.service.impl.OperationHisServiceImpl;

@Controller
@RequestMapping("/operationHis")
public class OperationHisController {
	@Autowired
	private OperationHisServiceImpl operationHisServiceImpl;
	
	@RequestMapping("/hisPage")
	public ModelAndView hisPage(HttpServletRequest req, String operationId, String serviceId){
		return operationHisServiceImpl.hisPage(req, operationId, serviceId);
	}
	
	//根据服务和场景id 
	@RequestMapping("/getByOS/{serviceId}/{operationId}")
	@ResponseBody
	public Map<String, Object> getByOS(@PathVariable(value="serviceId") String serviceId, @PathVariable(value="operationId") String operationId) {
		List<?> rows = operationHisServiceImpl.getByOS(operationId, serviceId);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}

	@RequestMapping("/getByAutoId")
	@ResponseBody
	public OperationHis getByAutoId(String autoId){
		return operationHisServiceImpl.getByAutoId(autoId);
	}
	
	@RequestMapping("/operationHisList")
	@ResponseBody
	public Map<String, Object> operationHisList() {
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<?> rows = operationHisServiceImpl.operationHisList();

		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}
}
