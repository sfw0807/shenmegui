package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.InvokeConnection;
import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.impl.InvokeConnectionServiceImpl;
import com.dc.esb.servicegov.service.impl.ServiceInvokeServiceImpl;
import com.dc.esb.servicegov.vo.ServiceInvokeInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vincentfxz on 15/7/9.
 */
@Controller
@RequestMapping("/serviceLink")
public class ServiceLinkController {
    @Autowired
    private ServiceInvokeServiceImpl serviceInvokeService;
    @Autowired
    private InvokeConnectionServiceImpl invokeConnectionService;

    @RequestMapping(method = RequestMethod.GET, value = "/getServiceLink/system/{systemId}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<ServiceInvokeInfoVO> getServiceLink(@PathVariable("systemId") String systemId) {
        List<ServiceInvokeInfoVO> serviceInvokeInfoVOs = new ArrayList<ServiceInvokeInfoVO>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("systemId", systemId);
        List<ServiceInvoke> serviceInvokes = serviceInvokeService.findBy(params);
        for(ServiceInvoke serviceInvoke : serviceInvokes){
            ServiceInvokeInfoVO serviceInvokeInfoVO = new ServiceInvokeInfoVO(serviceInvoke);
            serviceInvokeInfoVOs.add(serviceInvokeInfoVO);
        }
        return serviceInvokeInfoVOs;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/serviceLinkInfo/system/{systemId}", headers = "Accept=application/json")
    public List<ServiceInvokeInfoVO> serviceLinkInfo(@PathVariable("systemId") String systemId) {
        List<ServiceInvokeInfoVO> serviceInvokeInfoVOs = new ArrayList<ServiceInvokeInfoVO>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("systemId", systemId);
        List<ServiceInvoke> serviceInvokes = serviceInvokeService.findBy(params);
        for(ServiceInvoke serviceInvoke : serviceInvokes){
            ServiceInvokeInfoVO serviceInvokeInfoVO = new ServiceInvokeInfoVO(serviceInvoke);
            serviceInvokeInfoVOs.add(serviceInvokeInfoVO);
        }
        return serviceInvokeInfoVOs;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/invokeConnections/sourceId/{sourceId}", headers = "Accept=application/json")
    public @ResponseBody List<InvokeConnection> getInvokeConnetcion(@PathVariable("sourceId") String sourceId){
        invokeConnectionService.getConnectionsStartWith(sourceId);

    }


}
