package com.dc.esb.servicegov.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.impl.BaseLineServiceImpl;
import com.dc.esb.servicegov.service.impl.ServiceInvokeServiceImpl;
import com.dc.esb.servicegov.util.JSONUtil;

@Controller
@RequestMapping("/baseLine")
public class BaseLineController {
    @Autowired
    private BaseLineServiceImpl baseLineServiceImpl;
    @Autowired
    private ServiceInvokeServiceImpl serviceInvokeServiceImpl;

    @RequiresPermissions({"version-get"})
    @RequestMapping("/operationList")
    @ResponseBody
    public Map<String, Object> operationList() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<?> rows = baseLineServiceImpl.operationList();
        result.put("total", rows.size());
        result.put("rows", rows);
        return result;
    }

    @RequiresPermissions({"version-add"})
    @RequestMapping(method = RequestMethod.POST, value = "/release", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean release(HttpServletRequest req, String code, String blDesc,
                    String versionHisIds) {
        return baseLineServiceImpl.release(req, code, blDesc, versionHisIds);
    }

    @RequiresPermissions({"version-get"})
    @RequestMapping("/getBaseLine")
    @ResponseBody
    public Map<String, Object> getBaseLine(String code, String blDesc) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<?> rows = baseLineServiceImpl.getBaseLine(code, blDesc);
        result.put("total", rows.size());
        result.put("rows", rows);
        return result;
    }

    @RequiresPermissions({"version-get"})
    @RequestMapping("/getBLOperationHiss")
    @ResponseBody
    public List<?> getBLOperationHiss(String baseId) {
        return baseLineServiceImpl.getBLOperationHiss(baseId);
    }

    @RequiresPermissions({"version-get"})
    @RequestMapping("/getBLInvoke")
    @ResponseBody
    public Map<String, Object> getBLInvoke(String baseId) {
        List<?> rows = serviceInvokeServiceImpl.getBLInvoke(baseId);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", rows.size());
        result.put("rows", rows);
        return result;
    }

    @ExceptionHandler({UnauthenticatedException.class, UnauthorizedException.class})
    public String processUnauthorizedException() {
        return "403";
    }
}
