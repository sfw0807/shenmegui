package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.service.impl.PdfServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
 * Created by Administrator on 2015/7/29.
 */
@Controller
@RequestMapping("/pdfExporter")
public class PdfExportController {
    protected Log log = LogFactory.getLog(getClass());

    @Autowired
    PdfServiceImpl pfdServiceImpl;

//    @RequiresPermissions({"pdfExport-get"})
    @RequestMapping(method = RequestMethod.POST, value = "/exportService", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportService(HttpServletRequest request, HttpServletResponse response,
                          String id, String type) {
        InputStream in = null;
        OutputStream out = null;
        File pdfDir = new File("tmppdf");
        try {
            File pdfFile = pfdServiceImpl.genderServicePdf(id, type);
            if (null != pdfFile && pdfFile.exists()) {
                try {
                    response.setContentType("application/pdf");
                    response.addHeader(
                            "Content-Disposition",
                            "attachment;filename="
                                    + new String(pdfFile.getName().getBytes(
                                    "gbk"), "iso-8859-1"));
                    in = new BufferedInputStream(
                            new FileInputStream(pdfFile));
                    out = new BufferedOutputStream(response.getOutputStream());
                    long fileLength = pdfFile.length();
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
            }
        } catch (Exception e) {
            log.error(e, e);
        }
        deleteFile(pdfDir);
        return true;
    }

    public boolean deleteFile(File file) {
        boolean deleteResult = true;
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (null != subFiles) {
                for (File subFile : subFiles) {
                    deleteResult = deleteFile(subFile);
                }
            }
        }
        deleteResult = file.delete();
        if (!deleteResult) {
            log.error("删除临时文件[" + file.getAbsolutePath() + "]失败！");
        }
        return deleteResult;
    }
}
