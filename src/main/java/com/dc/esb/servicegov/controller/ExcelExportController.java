package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.RelationView;
import com.dc.esb.servicegov.entity.System;
import com.dc.esb.servicegov.excel.impl.ESBMappingExcelGenerator;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.service.impl.ServiceManagerImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/excel")
public class ExcelExportController {

	private static Log log = LogFactory.getLog(ExcelExportController.class);

    @Autowired
	private ServiceManagerImpl serviceManager;
    
    @Autowired
	private ESBMappingExcelGenerator excelGenerator;
    
    @RequestMapping(method = RequestMethod.GET, value = "/getAllSystem", headers = "Accept=application/json")
	public @ResponseBody
	List<System> getAllSystem() {
		return serviceManager.getAllSys();
	}
    
    @RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
	public @ResponseBody
	List<RelationView> getViewList() {
		return serviceManager.getRelationViewList();
	}
    
    
    @RequestMapping(method = RequestMethod.GET, value = "/isSysLinked/{id}", headers = "Accept=application/json")
	public @ResponseBody
	boolean isSysLinked(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
		String[] params = id.split(",");
		if (id.contains("consumer") && !serviceManager.isConsumerSysLinked(params[1])) {
			return false;
		} else if (!serviceManager.isProviderSysLinked(params[1])) {
			return false;
		}
    	return true;
	}
    
    @RequestMapping(method = RequestMethod.GET, value = "/byInterfaceId/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportService(HttpServletRequest request,
			HttpServletResponse response, @PathVariable
			String id) {
		InputStream in = null;
		OutputStream out = null;
		boolean success = false;
		try {
			File mappingExcel = null;
			String[] params = id.split(",");
			mappingExcel = excelGenerator.generate(params);

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
