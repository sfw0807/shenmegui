package com.dc.esb.servicegov.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.service.impl.ServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.service.impl.OperationServiceImpl;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/operation")
public class OperationController {
    @Autowired
    private OperationServiceImpl operationServiceImpl;

    @Autowired
    private ServiceServiceImpl serviceService;

    @RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
    public
    @ResponseBody
    Map<String, Object> getAll() {

        Map<String, Object> result = new HashMap<String, Object>();
        List<Operation> rows = operationServiceImpl.getAll();
        result.put("total", rows.size());
        result.put("rows", rows);
        return result;
    }

    @RequestMapping("/getOperationByServiceId/{serviceId}")
    @ResponseBody
    public Map<String, Object> getOperationByServiceId(@PathVariable(value = "serviceId") String serviceId) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<?> rows = operationServiceImpl.getOperationByServiceId(serviceId);
        result.put("total", rows.size());
        result.put("rows", rows);
        return result;
    }

    //根据服务id跳转到场景添加页面
    @RequestMapping("/addPage/{serviceId}")
    public ModelAndView 阿杜dPageaddPage(HttpServletRequest req, @PathVariable(value = "serviceId") String serviceId) {
        ModelAndView mv = new ModelAndView("service/operation/add");
        Service service = serviceService.getUniqueByServiceId(serviceId);
        if (service != null) {
            mv.addObject("service", service);
        }
        return mv;
    }

    //场景号唯一性验证
    @RequestMapping(method = RequestMethod.GET, value = "/uniqueValid", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean uniqueValid(String operationId, String serviceId) {
        return operationServiceImpl.uniqueValid(operationId, serviceId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean add(Operation Operation) {
        operationServiceImpl.save(Operation);
        return true;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edit", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean edit(HttpServletRequest req, Operation Operation) {
        return operationServiceImpl.editOperation(req, Operation);
    }

    //根据服务id跳转到场景修改页面
    @RequestMapping("/editPage")
    public ModelAndView editPage(HttpServletRequest req, String operationId, String serviceId) {
        ModelAndView mv = new ModelAndView("service/operation/edit");
        //根据operationId查询operation
        Map<String, String> params = new HashMap<String, String>();
        params.put("serviceId", serviceId);
        params.put("operationId", operationId);
        Operation operation = operationServiceImpl.findUniqueBy(params);
        if (operation != null) {
            mv.addObject("operation", operation);
            //根据operation查询service信息
            Service service = serviceService.getUniqueByServiceId(operation.getServiceId());
            if (service != null) {
                mv.addObject("service", service);
            }
        }

        return mv;
    }

    @RequestMapping("/deletes")
    @ResponseBody
    public boolean deletes(String operationIds) {
        operationServiceImpl.deleteOperations(operationIds);
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getServiseById/{value}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Operation> getServiseById(@PathVariable String value) {
        String name = "serviceId";
        Map<String, String> params = new HashMap<String, String>();
        List<Operation> info = operationServiceImpl.findBy(params);
        return info;
    }

    @RequestMapping("/detailPage")
    public ModelAndView detailPage(HttpServletRequest req, String operationId, String serviceId) {
        return operationServiceImpl.detailPage(req, operationId, serviceId);
    }

    @RequestMapping("/release")
    public ModelAndView release(HttpServletRequest req, String operationId, String serviceId) {
        return operationServiceImpl.release(req, operationId, serviceId);
    }


}
