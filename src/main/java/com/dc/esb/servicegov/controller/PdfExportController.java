package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.entity.ServiceCategory;
import com.dc.esb.servicegov.service.impl.*;
import com.dc.esb.servicegov.util.PdfUtils;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-27
 * Time: 下午2:41
 */
@Controller
@RequestMapping("/pdfExport")
public class PdfExportController {

    private static final Log log = LogFactory.getLog(PdfExportController.class);

    @Autowired
    private ServiceManagerImpl serviceManager;
    @Autowired
    private ServiceCategoryManagerImpl serviceCategoryManager;
    @Autowired
    private OperationPdfGenerator operationPdfGenerator;
    @Autowired
    private ServiceCategoryPdfGenerator serviceCategoryPdfGenerator;
    @Autowired
    private ServicePdfGenerator servicePdfGenerator;

    @RequestMapping(method = RequestMethod.GET, value = "/operationpdf/{serviceId}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportOperationPdfByServiceId(@PathVariable String serviceId, HttpServletRequest request, HttpServletResponse response) {
        InputStream in = null;
        OutputStream out = null;
        File pdfDir = new File("tmppdf");
        try {
            List<Operation> operations = serviceManager.getOperationsByServiceId(serviceId);
            File pdfFile = operationPdfGenerator.generate(operations);
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

    @RequestMapping(method = RequestMethod.GET, value = "/servicepdf/{categoryId}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportServiceByCategoryId(@PathVariable String categoryId, HttpServletRequest request, HttpServletResponse response) {
        InputStream in = null;
        OutputStream out = null;
        File pdfDir = new File("tmppdf");
        try {
            List<Service> services = serviceCategoryManager.getServiceByCategoryId(categoryId);
            File pdfFile = servicePdfGenerator.generate(services);
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

    @RequestMapping(method = RequestMethod.GET, value = "/categorypdf/{categoryId}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportCategoryByParentCategoryId(@PathVariable String categoryId, HttpServletRequest request, HttpServletResponse response) {
        InputStream in = null;
        OutputStream out = null;
        File pdfDir = new File("tmppdf");
        try {
            List<ServiceCategory> serviceCategorys = serviceCategoryManager.getSubServiceCategoryByParentId(categoryId);
            File pdfFile = serviceCategoryPdfGenerator.generate(serviceCategorys);

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

    @RequestMapping(method = RequestMethod.GET, value = "/allpdf", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportAll(HttpServletRequest request, HttpServletResponse response) {
        InputStream in = null;
        OutputStream out = null;
        File pdfDir = null;
        try {
            String[] ids = {"0100", "0200", "0300", "0400", "0500", "0600",
                    "0700", "0800", "0900", "1000", "1100", "1200", "1300"};
            Thread[] threadArray = new Thread[ids.length];
            /***** 多线程 *******/
            for (int i = 0; i < ids.length; i++) {
                Thread t = new ServiceCategoryPdfGenerateTask(ids[i],
                        serviceCategoryManager, serviceCategoryPdfGenerator);
                t.start();
                threadArray[i] = t;
            }
//			Thread.sleep(1000 * 120);
            boolean endFlag = true;
            int hasDeadNum = 0;
            while (endFlag) {
                for (int i = 0; i < threadArray.length; i++) {
                    if (!threadArray[i].isAlive()) {
                        hasDeadNum++;
                    }
                }
                if (hasDeadNum == threadArray.length) {
                    endFlag = false;
                }
                hasDeadNum = 0;
            }
            /***** 多线程 *******/
            String allPath = "tmppdf"
                    + File.separator
                    + "浦发银行服务手册-all.pdf";
            File pdfAllFile = null;
            pdfDir = new File("tmppdf");
            if (pdfDir.exists()) {
                if (pdfDir.isDirectory()) {
                    File[] files = pdfDir.listFiles();
                    String[] paramStr = new String[files.length + 1];
                    for (int i = 0; i < paramStr.length - 1; i++) {
                        paramStr[i] = files[i].getAbsolutePath();
                    }
                    paramStr[files.length] = allPath;
                    //sort
                    List<String> tmpList = new ArrayList<String>();
                    for (int i = 0; i < paramStr.length; i++) {
                        tmpList.add(paramStr[i]);
                    }
                    Collections.sort(tmpList);
                    paramStr = tmpList.toArray(paramStr);
//					for (int i = 0; i < paramStr.length; i++) {
//						System.out.println(paramStr[i]);
//					}
                    pdfAllFile = PdfUtils.mergePdfFile(paramStr);
                }
            }
            if (null != pdfAllFile && pdfAllFile.exists()) {
                try {
                    response.setContentType("application/pdf");
                    response.addHeader(
                            "Content-Disposition",
                            "attachment;filename="
                                    + new String(pdfAllFile.getName().getBytes(
                                    "gbk"), "iso-8859-1"));
                    in = new BufferedInputStream(
                            new FileInputStream(pdfAllFile));
                    out = new BufferedOutputStream(response.getOutputStream());
                    long fileLength = pdfAllFile.length();
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
