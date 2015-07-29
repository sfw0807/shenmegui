package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.service.impl.ExcelExportServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Administrator on 2015/7/14.
 */
@Controller
@RequestMapping("/excelExporter")
public class ExcelExportController {

    private static Log log = LogFactory.getLog(ExcelExportController.class);

    @Autowired
    private ExcelExportServiceImpl excelExportServiceImpl;

    @RequiresPermissions({"excelExport-get"})
    @RequestMapping(method = RequestMethod.POST, value = "/exportService", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportService(HttpServletRequest request, HttpServletResponse response,
                          String id, String type) {
        String codedFileName = null;
        OutputStream fOut = null;
        try
        {
            // 进行转码，使其支持中文文件名
            response.setContentType("application/zip");
            codedFileName = java.net.URLEncoder.encode(type+"_"+id, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
            // response.addHeader("Content-Disposition", "attachment;   filename=" + codedFileName + ".xls");
            // 产生工作簿对象
            HSSFWorkbook workbook = excelExportServiceImpl.genderExcel(id, type);

            fOut = response.getOutputStream();
            workbook.write(fOut);
        }
        catch (UnsupportedEncodingException e1)
        {}
        catch (Exception e)
        {}
        finally
        {
            try
            {
                if(fOut != null){
                    fOut.flush();
                    fOut.close();
                }
            }
            catch (IOException e)
            {
                log.error("IO异常");
            }
        }
        return true;
    }

    /**
     * @param request
     * @param response
     * @param categoryId
     * @return
     */
    @RequiresPermissions({"excelExport-get"})
    @RequestMapping(method = RequestMethod.POST, value = "/exportServiceView", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportServiceView(HttpServletRequest request, HttpServletResponse response,
                          String categoryId) {
        String codedFileName = null;
        OutputStream fOut = null;
        try
        {
            // 进行转码，使其支持中文文件名
            response.setContentType("application/zip");
            codedFileName = java.net.URLEncoder.encode("serviceview_"+categoryId, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
            // response.addHeader("Content-Disposition", "attachment;   filename=" + codedFileName + ".xls");
            // 产生工作簿对象
            HSSFWorkbook workbook = excelExportServiceImpl.genderServiceView(categoryId);

            fOut = response.getOutputStream();
            workbook.write(fOut);
        }
        catch (UnsupportedEncodingException e1)
        {
            log.error(e1, e1);
        }
        catch (Exception e)
        {
            log.error(e, e);
        }
        finally
        {
            try
            {
                if(fOut != null){
                    fOut.flush();
                    fOut.close();
                }
            }
            catch (IOException e)
            {
                log.error("IO异常");
            }
        }
        return true;
    }

}
