package com.dc.esb.servicegov.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dc.esb.servicegov.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.impl.ServiceInvokeServiceImpl;
import com.dc.esb.servicegov.vo.ServiceInvokeViewBean;

/**
 * Created by vincentfxz on 15/7/8.
 */
@Controller
@RequestMapping("/serviceLink")
public class ServiceInvokeController {
    @Autowired
    private ServiceInvokeServiceImpl serviceInvokeService;

    @RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
    public
    @ResponseBody
    List<ServiceInvokeViewBean> getAllTreeBean() {
        List<ServiceInvokeViewBean>  serviceInvokeViewBeans = new ArrayList<ServiceInvokeViewBean>();
        List<ServiceInvoke> serviceInvokes =  serviceInvokeService.getAll();
        for(ServiceInvoke serviceInvoke : serviceInvokes){
            ServiceInvokeViewBean serviceInvokeViewBean = new ServiceInvokeViewBean(serviceInvoke);
            serviceInvokeViewBeans.add(serviceInvokeViewBean);
        }
        return serviceInvokeViewBeans;
    }

//根据系统id查询接口列表

    /**
     * @param systemId
     * @return
     */
    @RequestMapping("/getInterface")
    @ResponseBody
    public Map<String, Object> getInterface(String systemId) {
        List<ServiceInvoke> rows = serviceInvokeService.getDistinctInter( systemId);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", rows.size());
        result.put("rows", JSONUtil.getInterface().convert(rows, ServiceInvoke.simpleFields()));
        return result;
    }

    /**
     * @param serviceId
     * @param operationId
     * @param systemId
     * @return
     */
    @RequestMapping("/getInterfaceByOSS")
    @ResponseBody
    public Map<String, Object> getInterfaceByOSS(String serviceId, String operationId, String systemId) {
        List<?> rows = serviceInvokeService.findJsonBySO(serviceId, operationId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", rows.size());
        result.put("rows", rows);
        return result;
    }
}
