package com.dc.esb.servicegov.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.service.impl.OperationServiceImpl;

@Controller
@RequestMapping("/operation")
public class OperationController {
	@Autowired
	private OperationServiceImpl operationServiceImpl;

	// 根据服务id获取场景列表
	@RequestMapping("/getAll")
	@ResponseBody
	public Map<String, Object> getAll() {

		Map<String, Object> result = new HashMap<String, Object>();
		List<?> rows = operationServiceImpl.getEntityAll();

		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}

	// 根据服务id获取场景列表
	@RequestMapping("/getOperationByServiceId/{serviceId}")
	@ResponseBody
	public Map<String, Object> getOperationByServiceId(
			@PathVariable(value = "serviceId") String serviceId) {

		Map<String, Object> result = new HashMap<String, Object>();
		List<?> rows = operationServiceImpl.getOperationByServiceId(serviceId);

		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}

	// 获取待审核列表
	@RequestMapping("/getAudits/{serviceId}")
	@ResponseBody
	public Map<String, Object> getAudits(
			@PathVariable(value = "serviceId") String serviceId) {
		List<?> rows = operationServiceImpl.getByServiceState(serviceId, "0");

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}

	// 根据服务id跳转到场景添加页面
	@RequestMapping("/addPage/{serviceId}")
	public ModelAndView addPage(HttpServletRequest req,
			@PathVariable(value = "serviceId") String serviceId) {
		return operationServiceImpl.addPage(req, serviceId);
	}

	// 场景号唯一性验证
	@RequestMapping(method = RequestMethod.GET, value = "/uniqueValid", headers = "Accept=application/json")
	public @ResponseBody
	boolean uniqueValid(String operationId, String serviceId) {
		return operationServiceImpl.uniqueValid(operationId, serviceId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody
	boolean add(HttpServletRequest req, Operation Operation) {
		return operationServiceImpl.addOperation(req, Operation);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/afterAdd", headers = "Accept=application/json")
	public @ResponseBody
	boolean afterAdd(HttpServletRequest req, String serviceId, String operationId, String consumerStr, String providerStr) {
		return operationServiceImpl.afterAdd(req, serviceId, operationId, consumerStr, providerStr);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/edit", headers = "Accept=application/json")
	public @ResponseBody
	boolean edit(HttpServletRequest req, Operation Operation) {
		return operationServiceImpl.editOperation(req, Operation);
	}

	// 根据服务id跳转到场景修改页面
	@RequestMapping("/editPage")
	public ModelAndView editPage(HttpServletRequest req, String operationId,
			String serviceId) {
		return operationServiceImpl.editPage(req, operationId, serviceId);
	}

	@RequestMapping("/deletes")
	@ResponseBody
	public boolean deletes(HttpServletRequest req, String serviceId, String operationIds) {
		operationServiceImpl.deleteOperations(req, serviceId, operationIds);
		return true;
	}

	@RequestMapping("/detailPage")
	public ModelAndView detailPage(HttpServletRequest req, String operationId,
			String serviceId) {
		return operationServiceImpl.detailPage(req, operationId, serviceId);
	}

	@RequestMapping("/release")
	public ModelAndView release(HttpServletRequest req, String operationId,
			String serviceId, String versionDesc) {
		return operationServiceImpl.release(req, operationId, serviceId, versionDesc);
	}
	
	@RequestMapping("/releaseBatch")
	@ResponseBody
	public boolean releaseBatch(HttpServletRequest req, @RequestBody Operation[] operations) {
		return operationServiceImpl.releaseBatch(req, operations);
	}
	
	@RequestMapping("/auditPage")
	public ModelAndView auditPage(HttpServletRequest req, String serviceId) {
		return operationServiceImpl.auditPage(req, serviceId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/auditPass", headers = "Accept=application/json")
	@ResponseBody
	public boolean auditPass(@RequestBody String[] operationIds) {
		return operationServiceImpl.auditOperation("1", operationIds);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/auditUnPass", headers = "Accept=application/json")
	@ResponseBody
	public boolean auditUnPass(@RequestBody String[] operationIds) {
		return operationServiceImpl.auditOperation("2", operationIds);
	}

	// 根据系统id查询该系统过是有接口
	@RequestMapping("/judgeInterface")
	@ResponseBody
	public boolean judgeInterface(String systemId) {
		return operationServiceImpl.judgeInterface(systemId);
	}
	
	@RequestMapping("/interfacePage")
	public ModelAndView interfacePage(String operationId, String serviceId, HttpServletRequest req) {
		return operationServiceImpl.interfacePage(operationId, serviceId, req);
	}
	//根据系统id查询接口列表
	@RequestMapping("/getInterface")
	@ResponseBody
	public Map<String, Object> getInterface(String systemId) {
		List<?> rows = operationServiceImpl.getInterface(systemId);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}
	@RequestMapping("/getInterfaceByOSS")
	@ResponseBody
	public Map<String, Object> getInterfaceByOSS(String serviceId, String operationId, String systemId) {
		List<?> rows = operationServiceImpl.getInterfaceByOSS(serviceId, operationId, systemId);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}
}
