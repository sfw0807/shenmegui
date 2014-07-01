package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.service.impl.ServiceManagerImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
 * Date: 14-6-9
 * Time: 上午9:59
 */
@Controller
@RequestMapping("/operation")
public class OperationController {
    private static final Log log = LogFactory.getLog(OperationController.class);

    @Autowired
    private ServiceManagerImpl serviceManager;

    @RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Service> getAllOperation() {
        return serviceManager.getAllOperation();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/byServiceId/{serviceId}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Service> getAllOperation(@PathVariable String serviceId) {
        List<Service> operations = null;
        try {
            operations = serviceManager.getOpertions(serviceId);
        } catch (Exception e) {
            String errorMsg = "获取服务[" + serviceId + "]的操作失败！";
            log.error(errorMsg, e);
        }
        return operations;
    }

}
