package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.excel.impl.PublishInfoExcelGenerator;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.service.impl.OperationManagerImpl;
import com.dc.esb.servicegov.service.impl.PublishInfoManagerImpl;
import com.dc.esb.servicegov.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.vo.PublishInfoVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/publish")
public class PublishInfoController {
    @SuppressWarnings("unused")
    private Log log = LogFactory.getLog(PublishInfoController.class);

    @Autowired
    private PublishInfoManagerImpl publishInfoManager;
    @Autowired
    private ServiceManagerImpl serviceManager;
    @Autowired
    private OperationManagerImpl operationManager;
    @Autowired
    private PublishInfoExcelGenerator excelGenerator;

    /**
     * 上线统计(按日期) 所有数据
     *
     * @param request
     * @param response
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/total/{params}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<PublishInfoVO> getAllPublishTotalInfos(HttpServletRequest request,
                                                HttpServletResponse response, @PathVariable String params) {
        log.info("condition params :" + params);

        Map<String, String> mapConditions = null;
        if (params != null && !"".equals(params)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper
                    .disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
            try {
                mapConditions = mapper.readValue(params, Map.class);
            } catch (JsonParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return publishInfoManager.getAllPublishTotalInfos(mapConditions);
    }

    /**
     * 导出上线统计（按日期） 查询信息的结果
     *
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
                                    HttpServletResponse response, @PathVariable
    String params) {
        InputStream in = null;
        OutputStream out = null;
        boolean success = false;
        try {
            log.info("condition params :" + params);
            File svcAsmExcel = null;
            Map<String, String> mapConditions = null;
            ObjectMapper mapper = new ObjectMapper();
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
     * 按日期 上线服务的LIST总数
     *
     * @param request
     * @param response
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/totalServiceInfo/{params}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Service> getPublishServices(HttpServletRequest request,
                                     HttpServletResponse response, @PathVariable String params) {
        log.info("condition params :" + params);
        List<Service> list = new ArrayList<Service>();
        String[] strArr = params.split(",");
        String onlineDate = strArr[0];
        String prdMsgType = null;
        String csmMsgType = null;
        if (strArr.length > 1) {
            prdMsgType = strArr[1];
        }
        if (strArr.length > 2) {
            csmMsgType = strArr[2];
        }
        List<String> serviceIds = publishInfoManager.getPublishServiceIdsOrOperationIds(onlineDate, prdMsgType, csmMsgType, "s");
        if (serviceIds != null && serviceIds.size() > 0) {
            for (String serviceId : serviceIds) {
                Service pService = serviceManager.getServiceById(serviceId);
                list.add(pService);
            }
        }
        return list;
    }

    /**
     * 按日期 上线服务的LIST新增数
     *
     * @param request
     * @param response
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/totalServiceAddInfo/{params}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Service> getPublishAddServices(HttpServletRequest request,
                                        HttpServletResponse response, @PathVariable String params) {
        log.info("condition params :" + params);
        List<Service> list = new ArrayList<Service>();
        String[] strArr = params.split(",");
        String onlineDate = strArr[0];
        String prdMsgType = null;
        String csmMsgType = null;
        if (strArr.length > 1) {
            prdMsgType = strArr[1];
        }
        if (strArr.length > 2) {
            csmMsgType = strArr[2];
        }
        List<String> serviceIds = publishInfoManager.getAddServiceIdsOrOperationIds(onlineDate, prdMsgType, csmMsgType, "s");
        if (serviceIds != null && serviceIds.size() > 0) {
            for (String serviceId : serviceIds) {
                Service pService = serviceManager.getServiceById(serviceId);
                list.add(pService);
            }
        }
        return list;
    }

    /**
     * 按日期 上线服务LIST修改数
     *
     * @param request
     * @param response
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/totalServiceModifyInfo/{params}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Service> getPublishModifyServices(HttpServletRequest request,
                                           HttpServletResponse response, @PathVariable String params) {
        log.info("condition params :" + params);
        List<Service> list = new ArrayList<Service>();
        String[] strArr = params.split(",");
        String onlineDate = strArr[0];
        String prdMsgType = null;
        String csmMsgType = null;
        if (strArr.length > 1) {
            prdMsgType = strArr[1];
        }
        if (strArr.length > 2) {
            csmMsgType = strArr[2];
        }
        List<String> serviceIds = publishInfoManager.getModifyServiceIdsOrOperationIds(onlineDate, prdMsgType, csmMsgType, "s");
        if (serviceIds != null && serviceIds.size() > 0) {
            for (String serviceId : serviceIds) {
                Service pService = serviceManager.getServiceById(serviceId);
                list.add(pService);
            }
        }
        return list;
    }

    /**
     * 按日期 上线操作的LIST总数
     *
     * @param request
     * @param response
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/totalOperationInfo/{params}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Operation> getPublishOperations(HttpServletRequest request,
                                         HttpServletResponse response, @PathVariable String params) {
        List<Operation> list = new ArrayList<Operation>();
        log.info("condition params :" + params);
        String[] strArr = params.split(",");
        String onlineDate = strArr[0];
        String prdMsgType = null;
        String csmMsgType = null;
        if (strArr.length > 1) {
            prdMsgType = strArr[1];
        }
        if (strArr.length > 2) {
            csmMsgType = strArr[2];
        }
        List<String> sIdAndOIds = publishInfoManager.getPublishServiceIdsOrOperationIds(onlineDate, prdMsgType, csmMsgType, "p");
        if (sIdAndOIds != null && sIdAndOIds.size() > 0) {
            for (String sIdAndoId : sIdAndOIds) {
                String serviceId = sIdAndoId.substring(0, 10);
                String operationId = sIdAndoId.substring(10);
                Operation operation = operationManager.getOperation(operationId, serviceId);
                list.add(operation);
            }
        }
        return list;
    }

    /**
     * 按日期 上线操作的LIST新增数
     *
     * @param request
     * @param response
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/totalOperationAddInfo/{params}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Operation> getPublishAddOperations(HttpServletRequest request,
                                            HttpServletResponse response, @PathVariable String params) {
        List<Operation> list = new ArrayList<Operation>();
        log.info("condition params :" + params);
        String[] strArr = params.split(",");
        String onlineDate = strArr[0];
        String prdMsgType = null;
        String csmMsgType = null;
        if (strArr.length > 1) {
            prdMsgType = strArr[1];
        }
        if (strArr.length > 2) {
            csmMsgType = strArr[2];
        }
        List<String> sIdAndOIds = publishInfoManager.getAddServiceIdsOrOperationIds(onlineDate, prdMsgType, csmMsgType, "p");
        if (sIdAndOIds != null && sIdAndOIds.size() > 0) {
            for (String sIdAndoId : sIdAndOIds) {
                String serviceId = sIdAndoId.substring(0, 10);
                String operationId = sIdAndoId.substring(10);
                Operation operation = operationManager.getOperation(operationId, serviceId);
                list.add(operation);
            }
        }
        return list;
    }

    /**
     * 按日期 上线操作的LIST修改数
     *
     * @param request
     * @param response
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/totalOperationModifyInfo/{params}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Operation> getPublishModifyOperations(HttpServletRequest request,
                                               HttpServletResponse response, @PathVariable String params) {
        List<Operation> list = new ArrayList<Operation>();
        log.info("condition params :" + params);
        String[] strArr = params.split(",");
        String onlineDate = strArr[0];
        String prdMsgType = null;
        String csmMsgType = null;
        if (strArr.length > 1) {
            prdMsgType = strArr[1];
        }
        if (strArr.length > 2) {
            csmMsgType = strArr[2];
        }
        List<String> sIdAndOIds = publishInfoManager.getModifyServiceIdsOrOperationIds(onlineDate, prdMsgType, csmMsgType, "p");
        if (sIdAndOIds != null && sIdAndOIds.size() > 0) {
            for (String sIdAndoId : sIdAndOIds) {
                String serviceId = sIdAndoId.substring(0, 10);
                String operationId = sIdAndoId.substring(10);
                Operation operation = operationManager.getOperation(operationId, serviceId);
                list.add(operation);
            }
        }
        return list;
    }
}
