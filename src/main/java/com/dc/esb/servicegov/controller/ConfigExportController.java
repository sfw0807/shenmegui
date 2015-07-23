package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.*;
import com.dc.esb.servicegov.export.IMetadataConfigGenerator;
import com.dc.esb.servicegov.export.bean.ExportBean;
import com.dc.esb.servicegov.export.impl.StandardSOAPConfigGenerator;
import com.dc.esb.servicegov.export.impl.StandardXMLConfigGenerator;
import com.dc.esb.servicegov.export.util.ExportUtil;
import com.dc.esb.servicegov.export.util.FileUtil;
import com.dc.esb.servicegov.export.util.ZipUtil;
import com.dc.esb.servicegov.service.*;
import com.dc.esb.servicegov.service.impl.LogInfoServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.System;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/7/14.
 */
@Controller
@RequestMapping("/export")
public class ConfigExportController {

    protected Log logger = LogFactory.getLog(getClass());
    @Autowired
    SystemService systemService;

    @Autowired
    ServiceInvokeService serviceInvokeService;

    @Autowired
    SDAService sdaService;

    @Autowired
    IdaService idaService;

    @Autowired
    StandardXMLConfigGenerator standardXMLConfigGenerator;

    @Autowired
    StandardSOAPConfigGenerator standardSOAPConfigGenerator;

    @Autowired
    InterfaceService interfaceService;

    @Autowired
    ProtocolService protocolService;

    @Autowired
    LogInfoServiceImpl logInfoService;

    @RequiresPermissions({"config-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/exportHandle/{serviceId}/{operationId}/{providerSystemId}/{providerInterfaceId}/{providerIsStandard}/{consumerSystemId}/{consumerInterfaceId}/{consumerIsStandard}/{providerStandardType}/{consumerStandardType}", headers = "Accept=application/json")
    public String export(@PathVariable String serviceId, @PathVariable String operationId, @PathVariable String providerSystemId, @PathVariable String providerInterfaceId, @PathVariable boolean providerIsStandard,
                         @PathVariable String consumerInterfaceId, @PathVariable String consumerSystemId, @PathVariable boolean consumerIsStandard, @PathVariable String providerStandardType, @PathVariable String consumerStandardType, HttpServletResponse response) {
        File in_file = null;
        ExportBean export = new ExportBean(serviceId, operationId, providerSystemId, providerInterfaceId, providerIsStandard, consumerSystemId, consumerInterfaceId, consumerIsStandard);
        Map<String, String> sdaMap = new HashMap<String, String>();
        sdaMap.put("serviceId", export.getServiceId());
        sdaMap.put("operationId", export.getOperationId());
        List<SDA> sdas = sdaService.findBy(sdaMap);

        Map<String, String> idaMap = new HashMap<String, String>();
        sdaMap.put("interfaceId", export.getConsumerInterfaceId());
        List<Ida> idas = idaService.findBy(idaMap);
        SDA SDARequest = null;
        SDA SDAResponse = null;
        for (SDA sda : sdas) {
            if (sda.getStructName().equalsIgnoreCase("request")) {
                SDARequest = sda;
                continue;
            }
            if (sda.getStructName().equalsIgnoreCase("response")) {
                SDAResponse = sda;
            }
            if (SDARequest != null && SDAResponse != null) {
                break;
            }
        }
        String requestText = "";
        String responseText = "";
        String requestSOAPText  = "";
        String responseSOAPText = "";

        //消费方是否标准接口
        if (export.isConsumerIsStandard()) {

            requestText = ExportUtil.generatorServiceDefineXML(sdas, SDARequest);
            responseText = ExportUtil.generatorServiceDefineXML(sdas, SDAResponse);
            if (consumerStandardType.equalsIgnoreCase("xml")) {
                standardXMLConfigGenerator.init(requestText,responseText);
                in_file = standardXMLConfigGenerator.generatorIn(idas, sdas, export);
            } else if (consumerStandardType.equalsIgnoreCase("soap")) {
                requestSOAPText = ExportUtil.generatorServiceDefineSOAP(sdas,SDARequest);
                responseSOAPText = ExportUtil.generatorServiceDefineSOAP(sdas,SDAResponse);
                standardSOAPConfigGenerator.init(requestText,responseText,requestSOAPText,responseSOAPText);
                in_file = standardSOAPConfigGenerator.generatorIn(idas, sdas, export);
            }
        } else {

            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("serviceId", export.getServiceId());
            paramMap.put("operationId", export.getOperationId());
            paramMap.put("interfaceId", export.getConsumerInterfaceId());
            paramMap.put("systemId", export.getConsumerSystemId());
            ServiceInvoke invoke = serviceInvokeService.findUniqueBy(paramMap);
            if (invoke != null) {
                String protocolId = invoke.getProtocolId();
                if (protocolId == null || "".equals(protocolId)) {
                    logger.error("消费方接口未关联协议，导出失败");
//                    return "消费方提供方接口未关联协议，导出失败";
                    logInfoService.saveLog("消费方接口未关联协议，导出失败","导出");
                    return null;
                } else {


                    Protocol protocol = protocolService.getById(protocolId);
                    String generatorClass = protocol.getGeneratorId();

                    try {
                        Class c = Class.forName(generatorClass);
                        try {
                            IMetadataConfigGenerator generator = (IMetadataConfigGenerator) c.newInstance();
                            in_file = generator.generatorIn(idas, sdas, export);
                        } catch (InstantiationException e) {
                            logger.error("消费方接口协议报文生成类实例化失败,导出失败,错误信息：" + e.getMessage());
//                            return "消费方接口协议报文生成类构造方法是不可访问,导出失败，请查看日志!";
                            logInfoService.saveLog("消费方接口协议报文生成类实例化失败,导出失败","导出");
                            return null;
                        } catch (IllegalAccessException e) {

                            logger.error("消费方接口协议报文生成类构造方法是不可访问,导出失败,错误信息：" + e.getMessage());
                            logInfoService.saveLog("消费方接口协议报文生成类构造方法是不可访问,导出失败","导出");
//                            return "消费方接口协议报文生成类构造方法是不可访问,导出失败";
                            return null;
                        }
                    } catch (ClassNotFoundException e) {
                        logger.error("消费方接口协议报文生成类未找到，导出失败");
                        logInfoService.saveLog("消费方接口协议报文生成类未找到，导出失败","导出");
//                        return "消费方接口协议报文生成类未找到，导出失败";
                        return null;
                    }

                }

            }

        }

        Map<String, String> providerIDAMap = new HashMap<String, String>();
        providerIDAMap.put("interfaceId", export.getProviderSystemId());
        List<Ida> provideridas = idaService.findBy(providerIDAMap);
        //提供方是否标准接口
        if (export.isProviderIsStandard()) {
            if (providerStandardType.equalsIgnoreCase("xml")) {
                standardXMLConfigGenerator.generatorOut(provideridas, sdas, export);
            } else if (providerStandardType.equalsIgnoreCase("soap")) {
                standardSOAPConfigGenerator.generatorOut(idas, sdas, export);
            }

        } else {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("serviceId", export.getServiceId());
            paramMap.put("operationId", export.getOperationId());
            paramMap.put("interfaceId", export.getProviderInterfaceId());
            paramMap.put("systemId", export.getProviderSystemId());
            ServiceInvoke invoke = serviceInvokeService.findUniqueBy(paramMap);
            if (invoke != null) {

                String protocolId = invoke.getProtocolId();
                if (protocolId == null || "".equals(protocolId)) {
                    logger.error("提供方接口未关联协议，导出失败");
                    logInfoService.saveLog("提供方接口未关联协议，导出失败","导出");
//                    return "提供方接口未关联协议，导出失败";
                    return null;
                } else {


                    Protocol protocol = protocolService.getById(protocolId);
                    String generatorClass = protocol.getGeneratorId();

                    try {
                        Class c = Class.forName(generatorClass);
                        try {
                            IMetadataConfigGenerator generator = (IMetadataConfigGenerator) c.newInstance();
                            generator.generatorOut(provideridas, sdas, export);
                        } catch (InstantiationException e) {
                            logger.error("提供方接口协议报文生成类实例化失败,导出失败,错误信息：" + e.getMessage());
//                            return "提供方接口协议报文生成类构造方法是不可访问,导出失败，请查看日志!";
                            logInfoService.saveLog("提供方接口协议报文生成类实例化失败,导出失败","导出");
                            return null;
                        } catch (IllegalAccessException e) {
                            logger.error("提供方接口协议报文生成类构造方法是不可访问,导出失败,错误信息：" + e.getMessage());
//                            return "提供方接口协议报文生成类构造方法是不可访问,导出失败";
                            logInfoService.saveLog("提供方接口协议报文生成类构造方法是不可访问,导出失败","导出");
                            return null;
                        }
                    } catch (ClassNotFoundException e) {
                        logger.error("提供方接口协议报文生成类未找到，导出失败");
                        logInfoService.saveLog("提供方接口协议报文生成类未找到，导出失败","导出");
//                        return "提供方接口协议报文生成类未找到，导出失败";
                        return null;
                    }

                }
            }
        }
        String path = in_file.getPath();
        ZipUtil.compressZip(path, path + "/metadata.zip", "metadata.zip");
        InputStream in = null;
        OutputStream out = null;
        try {
            File metadata = new File(path + "/metadata.zip");

            response.setContentType("application/zip");
            response.addHeader("Content-Disposition",
                    "attachment;filename=metadata.zip");
            in = new BufferedInputStream(new FileInputStream(metadata));
            out = new BufferedOutputStream(response.getOutputStream());
            long fileLength = metadata.length();
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


        } finally {
            try {
                in.close();
                out.close();
                FileUtil.deleteDirectory(new File(path).getParent());
            } catch (Exception e) {
                logger.error("导出文件，关闭流异常," + e.getMessage());
            }
        }

        return null;
    }

    @RequiresPermissions({"config-get"})
    @RequestMapping(method = RequestMethod.POST, value = "/getSystem/{serviceId}/{operationId}/{type}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Map<String, String>> getSystemAll(@PathVariable String serviceId, @PathVariable String operationId, @PathVariable String type) {
        List<Map<String, String>> resList = new ArrayList<Map<String, String>>();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("serviceId", serviceId);
        paramMap.put("operationId", operationId);
        paramMap.put("type", type);
        List<ServiceInvoke> invokes = serviceInvokeService.findBy(paramMap);

        Map<String, String> map = new HashMap<String, String>();


        for (ServiceInvoke system : invokes) {
            if (!contains(resList, system.getSystemId())) {
                map = new HashMap<String, String>();
                map.put("id", system.getSystemId());
                map.put("text", system.getSystem().getSystemAb());
                resList.add(map);
            }
        }
        return resList;
    }

    @RequiresPermissions({"config-get"})
    @RequestMapping(method = RequestMethod.POST, value = "/getInterface/{serviceId}/{operationId}/{type}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Map<String, String>> getInterfaceAll(@PathVariable String serviceId, @PathVariable String operationId, @PathVariable String type) {
        List<Map<String, String>> resList = new ArrayList<Map<String, String>>();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("serviceId", serviceId);
        paramMap.put("operationId", operationId);
        paramMap.put("type", type);
        List<ServiceInvoke> invokes = serviceInvokeService.findBy(paramMap);

        Map<String, String> map = new HashMap<String, String>();


        for (ServiceInvoke system : invokes) {
            map = new HashMap<String, String>();
            map.put("id", system.getInterfaceId());
            map.put("text", system.getInter().getInterfaceName());
            resList.add(map);
        }
        return resList;
    }

    private boolean contains(List<Map<String, String>> mapList, String systemId) {
        for (Map<String, String> map : mapList) {
            java.util.Iterator iter = map.keySet().iterator();
            while (iter.hasNext()) {
                String value = map.get(iter.next());
                if (systemId.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @ExceptionHandler({UnauthenticatedException.class, UnauthorizedException.class})
    public String processUnauthorizedException() {
        return "403";
    }

}
