package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.impl.ServiceInvokeServiceImpl;
import com.dc.esb.servicegov.vo.ServiceInvokeViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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
}
