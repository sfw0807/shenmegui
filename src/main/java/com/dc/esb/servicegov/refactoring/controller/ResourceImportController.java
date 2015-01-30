package com.dc.esb.servicegov.refactoring.controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dc.esb.servicegov.refactoring.dao.impl.FunctionDAOImpl;
import com.dc.esb.servicegov.refactoring.resource.impl.MetadataImport;
import com.dc.esb.servicegov.refactoring.resource.impl.ResourceExcelImport;
import com.dc.esb.servicegov.refactoring.util.GlobalImport;
import com.dc.esb.servicegov.refactoring.util.GlobalMenuId;

@Controller
@RequestMapping("/import")
public class ResourceImportController {
	private Log log = LogFactory.getLog(ResourceImportController.class);

	@Autowired
	private ResourceExcelImport resourceImport;
	@Autowired
	private FunctionDAOImpl functionDAO;
	@Autowired
	private MetadataImport metadataImport;
	
	@RequestMapping(method = RequestMethod.POST, value = "/mapping")
	public @ResponseBody
	String importMapping(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("file")
			MultipartFile file,@RequestParam("select")
			String operateFlag) throws Exception {
		response.setContentType("text/html");
		response.setCharacterEncoding("GB2312");
		log.info("覆盖标识: " + operateFlag);
		if ("Y".equals(operateFlag)) {
			GlobalImport.operateFlag = true;
		} else {
			GlobalImport.operateFlag = false;
		}
		log.info("import fileName is: " + file.getOriginalFilename());
		String fileName = file.getOriginalFilename();
		// only xlsx and xlsm file is allowed
		if (fileName.toLowerCase().endsWith("xlsx")
				|| fileName.toLowerCase().endsWith("xlsm")
				|| fileName.toLowerCase().endsWith("xls")) {
			InputStream is = file.getInputStream();
			synchronized (ResourceImportController.class) {
				GlobalImport.flag = true;
				// 保存资源导入的menuId
				GlobalMenuId.saveMenuId(GlobalMenuId.resourceImportMenuId, functionDAO.getMenuIdByName("资源导入"));
				resourceImport.parse(is, file.getOriginalFilename());
				java.io.PrintWriter writer = response.getWriter();
				writer.println("<script language=\"javascript\">");
				if (GlobalImport.flag) {
					writer.println("alert('导入成功!');");
				} else {
					writer.println("alert('导入失败，请查看日志!');");
				}
				writer.println("window.parent.top.location.href=\""
						+ request.getContextPath() + "/jsp/resourceImport.jsp"
						+ "\";");
				writer.println("</script>");
				writer.flush();
				writer.close();
				return "SUCCESS";
			}
		} else {
			log.info("file type is wrong!");
			java.io.PrintWriter writer = response.getWriter();
			writer.println("<script language=\"javascript\">");
			writer.println("alert('导入失败,文件类型不支持!');");
			writer.println("window.parent.top.location.href=\""
					+ request.getContextPath() + "/jsp/resourceImport.jsp"
					+ "\";");
			writer.println("</script>");
			writer.flush();
			writer.close();
			return "FAILED";
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/online")
	public @ResponseBody
	String getOnlineInfo(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("file")
			MultipartFile file) throws Exception {
		response.reset();
		response.setContentType("text/html");
		response.setCharacterEncoding("GB2312");
		log.info("import fileName is: " + file.getOriginalFilename());
		String fileName = file.getOriginalFilename();
		//only  xlsx and xlsm file is allowed
		if (fileName.toLowerCase().endsWith("xlsx")
				|| fileName.toLowerCase().endsWith("xlsm")
				|| fileName.toLowerCase().endsWith("xls")) {
			InputStream is = file.getInputStream();
			synchronized (ResourceImportController.class) {
				String result = resourceImport.parseOnlineExcel(is, file.getOriginalFilename());
				java.io.PrintWriter writer = response.getWriter();
				writer.println("<script language=\"javascript\">");
				writer.println("alert('"+result+"');");
				writer.println("window.parent.top.location.href=\""
						+ request.getContextPath() + "/jsp/resourceImport.jsp" + "\";");
				writer.println("</script>");
				writer.flush();
				writer.close();
				return result;
			}
		} else {
			log.info("file type is wrong!");
			java.io.PrintWriter writer = response.getWriter();
			writer.println("<script language=\"javascript\">");
			writer.println("alert('导入失败,文件类型不支持!');");
			writer.println("window.parent.top.location.href=\""
					+ request.getContextPath() + "/jsp/resourceImport.jsp" + "\";");
			writer.println("</script>");
			writer.flush();
			writer.close();
			return "导入失败,文件类型不支持";
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/metadata")
	public @ResponseBody
	String importMetadata(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("file")
			MultipartFile file) throws Exception {
		response.setContentType("text/html");
		response.setCharacterEncoding("GB2312");
		log.info("import fileName is: " + file.getOriginalFilename());
		String fileName = file.getOriginalFilename();
		// only xlsx and xlsm file is allowed
		if (fileName.toLowerCase().endsWith("xlsx")
				|| fileName.toLowerCase().endsWith("xlsm")
				|| fileName.toLowerCase().endsWith("xls")) {
			InputStream is = file.getInputStream();
			synchronized (ResourceImportController.class) {
				GlobalImport.flag = true;
				// 保存资源导入的menuId
				GlobalMenuId.saveMenuId(GlobalMenuId.resourceImportMenuId, functionDAO.getMenuIdByName("资源导入"));
				metadataImport.parse(is, fileName);
				java.io.PrintWriter writer = response.getWriter();
				writer.println("<script language=\"javascript\">");
				if (GlobalImport.flag) {
					writer.println("alert('导入成功!');");
				} else {
					writer.println("alert('导入失败，请查看日志!');");
				}
				writer.println("window.parent.top.location.href=\""
						+ request.getContextPath() + "/jsp/resourceImport.jsp"
						+ "\";");
				writer.println("</script>");
				writer.flush();
				writer.close();
				return "SUCCESS";
			}
		} else {
			log.info("file type is wrong!");
			java.io.PrintWriter writer = response.getWriter();
			writer.println("<script language=\"javascript\">");
			writer.println("alert('导入失败,文件类型不支持!');");
			writer.println("window.parent.top.location.href=\""
					+ request.getContextPath() + "/jsp/resourceImport.jsp"
					+ "\";");
			writer.println("</script>");
			writer.flush();
			writer.close();
			return "FAILED";
		}
	}
}
