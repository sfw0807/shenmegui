package com.dc.esb.servicegov.refactoring.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.refactoring.excel.impl.ServiceDetailExcelGenerator;
import com.dc.esb.servicegov.refactoring.excel.impl.SvcAsmRelateExcelGenerator;
import com.dc.esb.servicegov.refactoring.service.impl.SvcAsmRelateManagerImpl;
import com.dc.esb.servicegov.refactoring.vo.SvcAsmRelateInfoVO;

@Controller
@RequestMapping("/relateView")
public class SvcAsmRelateController {
	private Log log = LogFactory.getLog(SvcAsmRelateController.class);
	
	@Autowired
	private SvcAsmRelateManagerImpl svcAsmRelate;
	@Autowired 
	private SvcAsmRelateExcelGenerator excelGenerator;
	@Autowired
	private ServiceDetailExcelGenerator svcDetailExcelGenerator;
	
	/**
	 * get all export invoke interface infos
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/configExport", headers = "Accept=application/json")
	public @ResponseBody List<SvcAsmRelateInfoVO> getAllExportInvokeInfos(){
		return svcAsmRelate.getAllExportInvokeInfos();
	}
	
	/**
	 * get all service interface infos
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/svcasm", headers = "Accept=application/json")
	public @ResponseBody List<SvcAsmRelateInfoVO> getAllSvcAsmRelateInfo(){
		return svcAsmRelate.getAllSvcAsmRelateInfo();
	}
	
	/**
	 * get all service details infos
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/svcDetails", headers = "Accept=application/json")
	public @ResponseBody List<SvcAsmRelateInfoVO> getAllServiceDetailsInfo(){
		return svcAsmRelate.getAllServiceDetailsInfo();
	}
	
	/**
	 * export service interface infos
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
		InputStream in = null;
		OutputStream out = null;
		boolean success = false;
		try {
			log.info("condition params :" + params);
			File svcAsmExcel = null;
			Map<String,String> mapConditions = null;
			ObjectMapper mapper =new ObjectMapper();
			mapConditions = mapper.readValue(params, Map.class);
			
			svcAsmExcel = excelGenerator.generate(mapConditions);

			if (null == svcAsmExcel) {
				String errorMsg = "生成Excel不存在！";
				DataException dataException = new DataException(errorMsg);
				throw dataException;

			} else {
				response.setContentType("application/zip");
				response.addHeader("Content-Disposition",
						"attachment;filename="
								+ new String(svcAsmExcel.getName().getBytes(
										"gbk"), "iso-8859-1"));
				in = new BufferedInputStream(new FileInputStream(svcAsmExcel));
				out = new BufferedOutputStream(response.getOutputStream());
				long fileLength = svcAsmExcel.length();
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
	/**
	 * get service details infos
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, value = "/svcDetailExport/{params}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportServiceDetailsExcel(HttpServletRequest request,
			HttpServletResponse response,@PathVariable
			String params) {
		InputStream in = null;
		OutputStream out = null;
		boolean success = false;
		try {
			log.info("condition params :" + params);
			File svcAsmExcel = null;
			Map<String,String> mapConditions = null;
			ObjectMapper mapper =new ObjectMapper();
			mapConditions = mapper.readValue(params, Map.class);
			
			svcAsmExcel = svcDetailExcelGenerator.generate(mapConditions);

			if (null == svcAsmExcel) {
				String errorMsg = "生成Excel不存在！";
				DataException dataException = new DataException(errorMsg);
				throw dataException;

			} else {
				response.setContentType("application/zip");
				response.addHeader("Content-Disposition",
						"attachment;filename="
								+ new String(svcAsmExcel.getName().getBytes(
										"gbk"), "iso-8859-1"));
				in = new BufferedInputStream(new FileInputStream(svcAsmExcel));
				out = new BufferedOutputStream(response.getOutputStream());
				long fileLength = svcAsmExcel.length();
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
