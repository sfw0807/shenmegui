package com.dc.esb.servicegov.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import com.dc.esb.servicegov.entity.*;
import com.dc.esb.servicegov.service.MetadataService;
import com.dc.esb.servicegov.service.impl.ExcelImportServiceImpl;
import com.dc.esb.servicegov.service.impl.LogInfoServiceImpl;
import com.dc.esb.servicegov.service.impl.MetadataServiceImpl;
import com.dc.esb.servicegov.util.GlobalImport;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dc.esb.servicegov.service.ExcelImportService;
import com.dc.esb.servicegov.util.ExcelTool;

@Controller
@RequestMapping("/excelHelper")
public class ExcelImportController {

    protected Log logger = LogFactory.getLog(getClass());

//    @Qualifier("TaizhouExcelImportService")
    @Qualifier("BaseExcelImportService")
    @Autowired
    ExcelImportService excelImportService;

    @Autowired
    MetadataServiceImpl metadataService;

    @Autowired
    LogInfoServiceImpl logInfoService;
    /**
     * Excel 2003
     */
    private final static String XLS = "xls";
    /**
     * Excel 2007
     */
    private final static String XLSX = "xlsx";

    @RequiresPermissions({"service-add", "system-add"})
    @RequestMapping(method = RequestMethod.POST, value = "/fieldimport")
    public void importFieldMapping(@RequestParam("file")
                                   MultipartFile file, Model model, HttpServletResponse response, @RequestParam("select")
                                   String operateFlag) {
        response.setContentType("text/html");
        response.setCharacterEncoding("GB2312");
        logger.info("覆盖标识: " + operateFlag);
        if ("Y".equals(operateFlag)) {
            GlobalImport.operateFlag = true;
        } else {
            GlobalImport.operateFlag = false;
        }
        Workbook workbook = null;
        String extensionName = FilenameUtils.getExtension(file.getOriginalFilename());
        InputStream is = null;
        java.io.PrintWriter writer = null;
        StringBuffer msg = new StringBuffer();
        try {
            writer = response.getWriter();
            is = file.getInputStream();
            if (extensionName.toLowerCase().equals(XLS)) {
                workbook = new HSSFWorkbook(is);
            } else if (extensionName.toLowerCase().equals(XLSX)) {
                workbook = new XSSFWorkbook(is);
            } else {
                outPutError(writer);
                return;
            }
            // 读取交易索引Sheet页
            Sheet indexSheet = workbook.getSheet("INDEX");
            if (indexSheet == null) {
                logger.error("缺少INDEX sheet页");
                logInfoService.saveLog("缺少INDEX sheet页", "导入");
                outPutError(writer);
                return;
            }
            // 从第一行开始读，获取所有交易行
            List<ExcelImportServiceImpl.IndexDO> indexDOs = excelImportService.parseIndexSheet(indexSheet);
            for (ExcelImportServiceImpl.IndexDO indexDO : indexDOs) {
                //判断系统是否存在
                boolean exists = excelImportService.existSystem(indexDO.getSystemId());
                //系统不存在该行跳过，继续下一行解析
                if (!exists) {
                    logger.error("交易[" + indexDO.getSheetName() + "]," + indexDO.getSystemId() + "系统不存在");
                    logInfoService.saveLog("交易[" + indexDO.getSheetName() + "]," + indexDO.getSystemId() + "系统不存在", "导入");
                    msg.append("交易[" + indexDO.getSheetName() + "]," + indexDO.getSystemId() + "系统不存在，");
                    continue;
                }
                //开始解析每一个页面
                String sheetName = indexDO.getSheetName();
                if (sheetName != null && !"".equals(sheetName)) {
                    // 读取每个交易sheet页
                    logger.debug("开始获取" + sheetName + "交易信息=========================");
                    Sheet sheet = workbook.getSheet(sheetName);
                    //获取交易、服务、场景信息
                    Map<String, Object> infoMap = excelImportService.getInterfaceAndServiceInfo(sheet);
                    //获取接口、服务 输入 参数
                    Map<String, Object> inputMap = excelImportService.getInputArg(sheet);
                    //获取接口、服务 输出 参数
                    Map<String, Object> outMap = excelImportService.getOutputArg(sheet);
                    //获取接口头
                    Map<String, Object> headMap = excelImportService.getInterfaceHead(indexDO, workbook);
                    //获取公共信息
                    Map<String, String> publicMap = excelImportService.getPublicHead(indexDO);
                    if (infoMap == null || inputMap == null || outMap == null) {
                        msg.append(sheetName + "导入失败，");
                        continue;
                    }
                    logger.info("===========交易[" + sheetName + "],开始导入字段映射信息=============");
                    long time = java.lang.System.currentTimeMillis();
                    boolean result = excelImportService.executeImport(infoMap, inputMap, outMap, publicMap, headMap);

                    if (!result) {
                        logger.info("===========交易[" + sheetName + "],导入失败=============");
                        continue;
                    }
                    long useTime = java.lang.System.currentTimeMillis() - time;
                    logger.info("===========交易[" + sheetName + "],导入完成，耗时" + useTime + "ms=============");
                } else {
                    logger.error("交易代码为空。");
                    logInfoService.saveLog("第交易代码为空。", "导入");
                }

            }
            //组织返回
            outPut(writer, msg);
        } catch (IOException e) {
            logger.error("导入出现异常:异常信息：" + e.getMessage());
            logInfoService.saveLog("导入出现异常:异常信息：" + e.getMessage(), "导入");
            writer.println("alert('导入失败，请查看日志!');");
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (Exception e) {

                }
            }
        }
        GlobalImport.headMap.clear();//清空本次导入的业务报文头
        writer.println("window.location='/jsp/sysadmin/fieldmapping_import.jsp'");
        writer.println("</script>");
        writer.flush();
        writer.close();
    }

    @ExceptionHandler({UnauthenticatedException.class, UnauthorizedException.class})
    public String processUnauthorizedException() {
        return "403";
    }

    public void outPutError(java.io.PrintWriter writer) {
        writer.println("<script language=\"javascript\">");
        writer.println("alert('缺少INDEX sheet页!');");
        writer.println("window.location='/jsp/sysadmin/fieldmapping_import.jsp'");
        writer.println("</script>");
        writer.flush();
        writer.close();
    }

    public void outPut(java.io.PrintWriter writer, StringBuffer msg) {
        writer.println("<script language=\"javascript\">");
        if (msg.length() == 0) {
            writer.println("alert('导入成功!');");
        } else {
            writer.println("alert('" + msg.toString() + ",请查看日志！');");
        }
    }
}
