package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.System;
import com.dc.esb.servicegov.service.impl.SystemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-11
 * Time: 上午11:16
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemManager systemManager;

    @RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
    public
    @ResponseBody
    List<System> getAllSystem() {
        return systemManager.getAllSystems();
    }
}
