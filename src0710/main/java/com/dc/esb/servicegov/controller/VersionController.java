package com.dc.esb.servicegov.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.entity.VersionHis;
import com.dc.esb.servicegov.service.impl.VersionServiceImpl;

@Controller
@RequestMapping("/version")
public class VersionController {
	@Autowired
	private VersionServiceImpl versionServiceImpl;
	
	@RequestMapping("/hisVersionList")
	@ResponseBody
	public Map<String, Object> hisVersionList(String keyValue) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<VersionHis> rows = versionServiceImpl.hisVersionList(keyValue);

		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}
}
