package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.Interface;
import com.dc.esb.servicegov.entity.System;
import com.dc.esb.servicegov.service.impl.InterfaceManager;
import com.dc.esb.servicegov.service.impl.SystemManager;
import com.dc.esb.servicegov.vo.InterfaceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-9
 * Time: 上午11:56
 */
@Controller
@RequestMapping("/interface")
public class InterfaceController {
    @Autowired
    private InterfaceManager interfaceManager;
    @Autowired
    private SystemManager systemManager;

    @RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
    private
    @ResponseBody
    List<Interface> getAllInterfaces() {
        return interfaceManager.getAllInterfaces();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/byOperationId/{operationId}", headers = "Accept=application/json")
    private
    @ResponseBody
    List<InterfaceVo> getInterfacesByOperationId(@PathVariable String operationId) {
        List<InterfaceVo> interfaceVos = null;
        if (null != operationId) {
            List<Interface> interfaces = interfaceManager.getInterfacesByOperation(operationId);
            interfaceVos = new ArrayList<InterfaceVo>();
            for (Interface i : interfaces) {
                InterfaceVo iv = new InterfaceVo(i);
                System system = systemManager.getSystemByInterface(i.getInterfaceId());
                iv.setSystemName(system.getSystemName());
                interfaceVos.add(iv);
            }
        }
        return interfaceVos;
    }
}
