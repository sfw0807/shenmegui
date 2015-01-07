package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.RemainingService;
import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.service.impl.InterfaceManager;
import com.dc.esb.servicegov.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.wsdl.impl.SpdbWSDLGenerator;
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

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-20
 * Time: 上午1:51
 */
@Controller
@RequestMapping("/wsdl")
public class WSDLExportController {

    private static Log log = LogFactory.getLog(WSDLExportController.class);
    @Autowired
    private SpdbWSDLGenerator spdbWSDLGenerator;
    @Autowired
    private ServiceManagerImpl serviceManager;
    @Autowired
    private InterfaceManager interfaceManager;

    @RequestMapping(method = RequestMethod.GET, value = "/byService/{id}", headers = "Accept=application/json")
     public
     @ResponseBody
     boolean exportService(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
        InputStream in = null;
        OutputStream out = null;
        boolean success = false;
        try {
            File wsdlZip = spdbWSDLGenerator.generate(id);
            if (null == wsdlZip) {
                String errorMsg = "生成的WSDL文件不存在！";
                DataException dataException = new DataException(errorMsg);
                throw dataException;

            } else {
                response.setContentType("application/zip");
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(wsdlZip.getName().getBytes("gbk"), "iso-8859-1"));
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
        return success;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/byInterface/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportInterface(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
        InputStream in = null;
        OutputStream out = null;
        boolean success = false;
        try {
            Service service = interfaceManager.getServiceByInterfaceId(id);
            if(log.isDebugEnabled()){
                log.debug("使用接口["+id+"]查找到服务["+service.getServiceId()+"]开始导出WSDL");
            }
            File wsdlZip = spdbWSDLGenerator.generate(service.getServiceId());
            if (null == wsdlZip) {
                String errorMsg = "生成的WSDL文件不存在！";
                DataException dataException = new DataException(errorMsg);
                throw dataException;
            } else {
                response.setContentType("application/zip");
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(wsdlZip.getName().getBytes("gbk"), "iso-8859-1"));
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
        return success;
    }

    /**
     * check WSDL Version return "1" for remaining services and "2" for new services
     * @param serviceId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/checkWSDLVersion/byService/{serviceId}", headers = "Accept=application/json")
    public
    @ResponseBody
    String checkWSDLVersionByService(@PathVariable String serviceId) {
        List<Service> services = serviceManager.getServiceById(serviceId);
        if(null == services){
            return "0";
        }
        if(services.size() == 0){
            return "0";
        }
        List<RemainingService> remainingServices = serviceManager.getRemainingServiceByServiceId(serviceId);
        return (null != remainingServices && remainingServices.size() > 0) ? "1":"2";
    }

    /**
     * check WSDL Version return "1" for remaining services and "2" for new services  "0" for error
     * @param interfaceId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/checkWSDLVersion/byInterface/{interfaceId}", headers = "Accept=application/json")
    public
    @ResponseBody
    String checkWSDLVersionByInterface(@PathVariable String interfaceId) throws DataException {
        Service service = interfaceManager.getServiceByInterfaceId(interfaceId);
        List<Service> services = serviceManager.getServiceById(service.getServiceId());
        if(null == services){
            return "0";
        }
        if(services.size() == 0){
            return "0";
        }
        List<RemainingService> remainingServices = serviceManager.getRemainingServiceByServiceId(service.getServiceId());
        return (null != remainingServices && remainingServices.size() > 0) ? "1":"2";
    }

}
