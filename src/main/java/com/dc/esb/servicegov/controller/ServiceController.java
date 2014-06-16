package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.vo.ServiceInvokeVo;
import com.dc.esb.servicegov.vo.ServiceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-4
 * Time: 上午10:33
 */
@Controller
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private ServiceManagerImpl serviceManager;

    @RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Service> getAllService(){
        return serviceManager.getAllServices();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getInvokeRelation/{serviceId}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<ServiceInvokeVo> getServiceInvokeRelation(@PathVariable String serviceId){
        return serviceManager.getServiceInvokeInfo(serviceId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByOperationId/{operationId}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Service> getServiceByOperationId(@PathVariable String operationId){
        return serviceManager.getServiceByOperationId(operationId);
    }
}
