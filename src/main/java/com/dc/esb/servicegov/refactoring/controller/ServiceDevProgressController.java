package com.dc.esb.servicegov.refactoring.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.refactoring.entity.System;
import com.dc.esb.servicegov.refactoring.excel.impl.ServiceDevProgressExcelGenerator;
import com.dc.esb.servicegov.refactoring.service.impl.SystemManagerImpl;
import com.dc.esb.servicegov.refactoring.vo.ServiceDevProgressVO;

@Controller
@RequestMapping("/serviceDevInfo")
public class ServiceDevProgressController {

	private static Log log = LogFactory.getLog(ServiceDevProgressController.class);
	
	@Autowired
	private SystemManagerImpl systemManager;
	@Autowired
	private ServiceDevProgressExcelGenerator excelGenerator;
	
	@RequestMapping(method = RequestMethod.GET, value = "/getAllSystem", headers = "Accept=application/json")
	public @ResponseBody List<System> getAllSystem(){
		return systemManager.getAllSystems();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/serviceDevProgress", headers = "Accept=application/json")
	public @ResponseBody List<ServiceDevProgressVO> getServiceDevProgress(){
		return systemManager.getSeviceDevInfo(null);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, value = "/export/{params}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportService(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String params) {
		InputStream in = null;
		OutputStream out = null;
		boolean success = false;
		try {
			File mappingExcel = null;
			ObjectMapper mapper =new ObjectMapper();
			mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
			
			mappingExcel = excelGenerator.exportAll(params);

			if (null == mappingExcel) {
				String errorMsg = "生成的映射文件不存在！";
				DataException dataException = new DataException(errorMsg);
				throw dataException;

			} else {
				response.setContentType("application/zip");
				response.addHeader("Content-Disposition",
						"attachment;filename="
								+ new String(mappingExcel.getName().getBytes(
										"gbk"), "iso-8859-1"));
				in = new BufferedInputStream(new FileInputStream(mappingExcel));
				out = new BufferedOutputStream(response.getOutputStream());
				long fileLength = mappingExcel.length();
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
					in.close();
				} catch (IOException e) {
					log.error(e, e);
				}

			}
		}
		return success;
	}
}
