package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.dao.impl.PermissionDAOImpl;
import com.dc.esb.servicegov.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2015/7/13.
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionDAOImpl permissionDAOImpl;

    @RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
    public
    @ResponseBody
    Map<String, String> getAll() {
        Map<String, String> resMap = new HashMap<String, String>();
        List<Permission> permissions = permissionDAOImpl.getAll();
        for (Permission p : permissions) {
            String key = p.getDescription();
            String val= p.getId()+"&"+ p.getName();
            if (resMap.containsKey(p.getDescription())) {
                val= p.getId()+"&"+ p.getName();
                resMap.put(key, resMap.get(key) + "," + val);
            } else {
                resMap.put(key,val);
            }
        }
        return resMap;
    }


}
