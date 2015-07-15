package com.dc.esb.servicegov.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.impl.BaseLineServiceImpl;
import com.dc.esb.servicegov.service.impl.ServiceInvokeServiceImpl;
import com.dc.esb.servicegov.util.JSONUtil;

@Controller
@RequestMapping("/baseLine")
public class BaseLineController {
	@Autowired
	private BaseLineServiceImpl baseLineServiceImpl;
	@Autowired
	private ServiceInvokeServiceImpl serviceInvokeServiceImpl;

	@RequestMapping("/operationList")
	@ResponseBody
	public Map<String, Object> operationList() {

		Map<String, Object> result = new HashMap<String, Object>();
		List<?> rows = baseLineServiceImpl.operationList();

		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/release", headers = "Accept=application/json")
	public @ResponseBody
	boolean release(HttpServletRequest req, String code, String blDesc,
			String versionHisIds) {
		return baseLineServiceImpl.release(req, code, blDesc, versionHisIds);
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
	public List<?> getBLOperationHiss(String baseId) {
		return baseLineServiceImpl.getBLOperationHiss(baseId);
	}

	@RequestMapping("/getBLInvoke")
	@ResponseBody
	public Map<String, Object> getBLInvoke(String baseId) {
		List<?> rows = serviceInvokeServiceImpl.getBLInvoke(baseId);
		JsonConfig serviceInvokeJC = JSONUtil.genderJsonConfig(ServiceInvoke.simpleFields());
        JSONArray ja = JSONArray.fromObject(rows, serviceInvokeJC);
        
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", rows.size());
		result.put("rows", ja);
		return result;
	}
}
