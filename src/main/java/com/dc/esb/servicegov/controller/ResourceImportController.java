package com.dc.esb.servicegov.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dc.esb.servicegov.rsimport.impl.MetadataArrayParserImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dc.esb.servicegov.rsimport.impl.CategoryWordParserImpl;
import com.dc.esb.servicegov.rsimport.impl.EnglishWordXlsxParserImpl;
import com.dc.esb.servicegov.rsimport.impl.MetadataXlsxParserImpl;

@Controller
@RequestMapping("/resourceImport")
public class ResourceImportController {
    private Log log = LogFactory.getLog(ResourceImportController.class);
    @Autowired
    private CategoryWordParserImpl categoryWordParserImpl;
    @Autowired
    private EnglishWordXlsxParserImpl englishWordXlsxParserImpl;
    @Autowired
    private MetadataXlsxParserImpl metadataXlsxParserImpl;
    @Autowired
    private MetadataArrayParserImpl metadataArrayParserImpl;

    @RequestMapping(method = RequestMethod.POST, value = "/import")
    public
    @ResponseBody
    String importMetadata(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam("file") MultipartFile file
    ) throws Exception {
        response.setContentType("text/html");
        response.setCharacterEncoding("GB2312");
        log.info("import fileName is: " + file.getOriginalFilename());
        String fileName = file.getOriginalFilename();
        //判断上传文件是否为xlsx或xls类型
        if (fileName.toLowerCase().endsWith("xlsx")
                || fileName.toLowerCase().endsWith("xls")) {
            synchronized (ResourceImportController.class) {//同步，防止有多个请求
                Workbook workbook = null;
                try {
                    if (fileName.toLowerCase().endsWith("xls")) {
                        workbook = new HSSFWorkbook(file.getInputStream());
                    } else if (fileName.toLowerCase().endsWith("xlsx")) {
                        workbook = new XSSFWorkbook(file.getInputStream());
                    }
                } catch (IOException e) {
                    log.error(e, e);
                }
                if (null != workbook) {
                    //TODO 暂时只要元数据导入
//                    englishWordXlsxParserImpl.parse(workbook);
//                    categoryWordParserImpl.parse(workbook);
                    metadataXlsxParserImpl.parse(workbook);
                    metadataArrayParserImpl.parse(workbook);
                }
                return "SUCCESS";
            }
        } else {
            return "FAILED";
        }
    }
}