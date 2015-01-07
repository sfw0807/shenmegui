package com.dc.esb.servicegov.refactoring.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.refactoring.entity.System;
import com.dc.esb.servicegov.refactoring.excel.impl.SysInvokeExcelGenerator;
import com.dc.esb.servicegov.refactoring.service.TransStateManager;
import com.dc.esb.servicegov.refactoring.service.impl.InvokeInfoManagerImpl;
import com.dc.esb.servicegov.refactoring.vo.InvokeInfoVo;
import com.dc.esb.servicegov.refactoring.vo.SystemInvokeServiceInfo;

@Controller
@RequestMapping("/invokeInfo")
public class InvokeInfoController {
	private static final Log log = LogFactory.getLog(InvokeInfoController.class);
	@Autowired
	private InvokeInfoManagerImpl invokeInfoManager;
	@Autowired
	private TransStateManager transStateManager;
	@Autowired
	private SysInvokeExcelGenerator sysInvokeGenerate;
	@RequestMapping(method = RequestMethod.GET, value = "/syssvc/{params}", headers = "Accept=application/json")
	public @ResponseBody List<SystemInvokeServiceInfo> getSystemServiceInvokeInfo(
			HttpServletRequest request,HttpServletRequest response,@PathVariable String params){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			params = new String(params.getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        log.info("condition params :" + params);
		Map<String,String> mapConditions = null;
		if (params != null && !"".equals(params)) {
			ObjectMapper mapper = new ObjectMapper();
			mapper
					.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
			try {
				mapConditions = mapper.readValue(params, Map.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return invokeInfoManager.getSystemInvokeServiceInfo(mapConditions);
	}
	@RequestMapping(method = RequestMethod.GET, value = "/AllInvokeInfo", headers = "Accept=application/json")
	public @ResponseBody List<InvokeInfoVo> getAllInvokeInfo(){
		return invokeInfoManager.getAllInvokeInfo();
	}
	@RequestMapping(method = RequestMethod.POST, value = "/saveInvokeInfo", headers = "Accept=application/json")
	public @ResponseBody boolean saveInvokeInfo(@RequestBody String[] param){
		String operationId = param[0];
		String serviceId = param[1];
		String interfaceId = param[2];
		String provideMsgType = param[3];
		String consumeMsgType = param[4];
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("operationId", operationId);
		paramMap.put("serviceId", serviceId);
		paramMap.put("ecode", interfaceId);
		paramMap.put("provideMsgType", provideMsgType);
		paramMap.put("consumeMsgType", consumeMsgType);
		int irId = invokeInfoManager.getId(paramMap);
		String state = param[5];
		return transStateManager.updateState(irId, state);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/consumer", headers = "Accept=application/json")
	public @ResponseBody List<System> getConsumer(@RequestBody String[] param){
		String operationId = param[0];
		if(operationId.contains("%27")){
			operationId = operationId.split("%27")[1];
		}
		String serviceId = param[1];
		if(serviceId.contains("%27")){
			serviceId = serviceId.split("%27")[1];
		}
		String interfaceId = param[2];
		if(interfaceId.contains("%27")){
			interfaceId = interfaceId.split("%27")[1];
		}
		String provideMsgType = param[3];
		if(provideMsgType.contains("%27")){
			provideMsgType = provideMsgType.split("%27")[1];
		}
		String consumeMsgType = param[4];
		if(consumeMsgType.contains("%27")){
			consumeMsgType = consumeMsgType.split("%27")[1];
		}		
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("operationId", operationId);
		paramMap.put("serviceId", serviceId);
		paramMap.put("ecode", interfaceId);
		paramMap.put("provideMsgType", provideMsgType);
		paramMap.put("consumeMsgType", consumeMsgType);
		List<System> systemList = invokeInfoManager.getConsumer(paramMap);
		return systemList;
	}	
	@RequestMapping(method = RequestMethod.POST, value = "/addconsumer", headers = "Accept=application/json")
	public @ResponseBody boolean addConsumer(@RequestBody String[] param){
		return invokeInfoManager.addConsumer(param);
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, value = "/export/{params}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportSvcAsmRelateExcel(HttpServletRequest request,
			HttpServletResponse response,@PathVariable
			String params) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			params = new String(params.getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		InputStream in = null;
		OutputStream out = null;
		boolean success = false;
		try {
			log.info("condition params :" + params);
			File sysInvokeExcel = null;
			Map<String,String> mapConditions = null;
			ObjectMapper mapper =new ObjectMapper();
			mapConditions = mapper.readValue(params, Map.class);
			
			sysInvokeExcel = sysInvokeGenerate.generate(mapConditions);

			if (null == sysInvokeExcel) {
				String errorMsg = "生成Excel不存在！";
				DataException dataException = new DataException(errorMsg);
				throw dataException;

			} else {
				response.setContentType("application/zip");
				response.addHeader("Content-Disposition",
						"attachment;filename="
								+ new String(sysInvokeExcel.getName().getBytes(
										"gbk"), "iso-8859-1"));
				in = new BufferedInputStream(new FileInputStream(sysInvokeExcel));
				out = new BufferedOutputStream(response.getOutputStream());
				long fileLength = sysInvokeExcel.length();
				byte[] cache = null;
				if (fileLength > Integer.MAX_VALUE) {
					cache = new byte[Integer.MAX_VALUE];
				} else {
					cache = new byte[(int) fileLength];
				}
				int i = 0;
				while ((i = in.read(cache)) > 0) {
					out.write(cache, 0, i);
				}
				out.flush();
				success = true;
			}
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					log.error(e, e);
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					log.error(e, e);
				}

			}
		}
		return success;
	}
}
