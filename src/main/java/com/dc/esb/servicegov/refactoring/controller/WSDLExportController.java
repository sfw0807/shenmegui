package com.dc.esb.servicegov.refactoring.controller;

import com.dc.esb.servicegov.refactoring.entity.RemainingService;
import com.dc.esb.servicegov.refactoring.entity.Service;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.refactoring.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.refactoring.util.ZIPUtils;
import com.dc.esb.servicegov.refactoring.wsdl.impl.OldSpdbWSDLGenerator;
import com.dc.esb.servicegov.refactoring.wsdl.impl.SpdbWSDLGenerator;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Administrator Date: 14-6-20 Time: 上午1:51
 */
@Controller
@RequestMapping("/wsdl")
public class WSDLExportController {

	private static Log log = LogFactory.getLog(WSDLExportController.class);
	@Autowired
	private SpdbWSDLGenerator spdbWSDLGenerator;
	@Autowired
	private OldSpdbWSDLGenerator oldspdbWSDLGenerator;
	@Autowired
	private ServiceManagerImpl serviceManager;

	@RequestMapping(method = RequestMethod.GET, value = "/byService/{id}", headers = "Accept=application/json")
	public @ResponseBody
	boolean exportService(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String id) {
		InputStream in = null;
		OutputStream out = null;
		boolean success = false;
		String serviceFlag = this.checkWSDLVersionByService(id);
		if ("1".equals(serviceFlag)) {
			log.info("service[" + id + "] is old service");
			try {
				File wsdlZip = oldspdbWSDLGenerator.generate(id);
				if (null == wsdlZip) {
					String errorMsg = "生成的WSDL文件不存在！";
					DataException dataException = new DataException(errorMsg);
					throw dataException;

				} else {
					response.setContentType("application/zip");
					response.addHeader(
							"Content-Disposition",
							"attachment;filename="
									+ new String(wsdlZip.getName().getBytes(
											"gbk"), "iso-8859-1"));
					in = new BufferedInputStream(new FileInputStream(wsdlZip));
					out = new BufferedOutputStream(response.getOutputStream());
					long fileLength = wsdlZip.length();
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
		} else if ("2".equals(serviceFlag)) {
			log.info("service[" + id + "] is new service");
			try {
				File wsdlZip = spdbWSDLGenerator.generate(id);
				if (null == wsdlZip) {
					String errorMsg = "生成的WSDL文件不存在！";
					DataException dataException = new DataException(errorMsg);
					throw dataException;

				} else {
					response.setContentType("application/zip");
					response.addHeader(
							"Content-Disposition",
							"attachment;filename="
									+ new String(wsdlZip.getName().getBytes(
											"gbk"), "iso-8859-1"));
					in = new BufferedInputStream(new FileInputStream(wsdlZip));
					out = new BufferedOutputStream(response.getOutputStream());
					long fileLength = wsdlZip.length();
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
		} else {
			log.error("error service");
		}
		return success;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/checkWSDLVersion/byService/{serviceId}", headers = "Accept=application/json")
	public @ResponseBody
	String checkWSDLVersionByService(@PathVariable String serviceId) {
		Service service = serviceManager.getServiceById(serviceId);
		if (null == service) {
			return "0";
		}
		List<RemainingService> remainingServices = serviceManager
				.getRemainingServiceByServiceId(serviceId);
		return (null != remainingServices && remainingServices.size() > 0) ? "1"
				: "2";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/byServiceList/{idList}", headers = "Accept=application/json")
	public @ResponseBody
	boolean exportServiceList(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String[] idList) {
		InputStream in = null;
		OutputStream out = null;
		boolean success = false;
		List<File> wsdlList = new ArrayList<File>();
		for (int i = 0; i < idList.length; i++) {
			String id = idList[i];
			String serviceFlag = this.checkWSDLVersionByService(id);
			if ("1".equals(serviceFlag)) {
				File wsdlZip = oldspdbWSDLGenerator.generate(id);
				if(null!=wsdlZip){
					wsdlList.add(wsdlZip);
				}else{
					log.error("error service["+id+"]");
				}
			}else if("2".equals(serviceFlag)){
				File wsdlZip = spdbWSDLGenerator.generate(id);
				if(null!=wsdlZip){
					wsdlList.add(wsdlZip);
				}else{
					log.error("error service["+id+"]");
				}
			}else{
				log.error("error service");
			}
		}
		File[] files = new File[wsdlList.size()];
		for (int i = 0; i < wsdlList.size(); i++) {
			files[i] = wsdlList.get(i);
		}
		Date d = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String time = simpleDateFormat.format(d);
		ZIPUtils zipUtil = new ZIPUtils();
		zipUtil.doZip(files, "wsdl_"+time+".zip");
		File exportFile = new File("wsdl_"+time+".zip");
		if(exportFile.exists()&&exportFile!=null){
			response.setContentType("application/zip");
			try {
				response.addHeader(
						"Content-Disposition",
						"attachment;filename="
								+ new String(exportFile.getName().getBytes(
										"gbk"), "iso-8859-1"));
				in = new BufferedInputStream(new FileInputStream(exportFile));
				out = new BufferedOutputStream(response.getOutputStream());
				long fileLength = exportFile.length();
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return success;
	}

}
