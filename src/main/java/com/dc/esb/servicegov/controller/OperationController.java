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

    @RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Operation> getAll() {
        return operationServiceImpl.getAll();
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
    public ModelAndView addPage(HttpServletRequest req, @PathVariable(value = "serviceId") String serviceId) {
        ModelAndView mv = new ModelAndView("service/operation/add");
        //根据serviceId查询service信息注入operation
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
        ServiceServiceImpl ss = context.getBean(ServiceServiceImpl.class);
        Service service = ss.getUniqueByServiceId(serviceId);
        if (service != null) {
            mv.addObject("service", service);
        }
        return mv;
    }

    //场景号唯一性验证
    @RequestMapping(method = RequestMethod.GET, value = "/uniqueValid/{serviceId}/{operationId}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean uniqueValid(@PathVariable(value="serviceId")String serviceId, @PathVariable(value="operationId") String operationId) {
        return operationServiceImpl.uniqueValid(serviceId,operationId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean add(Operation Operation) {
         operationServiceImpl.save(Operation);
        return true;
    }

    //根据服务id跳转到场景添加页面
    @RequestMapping("/editPage/{serviceId}/{operationId}")
    public ModelAndView editPage(HttpServletRequest req,@PathVariable(value="serviceId") String serviceId, @PathVariable(value = "operationId") String operationId) {
        ModelAndView mv = new ModelAndView("service/operation/edit");
        //根据operationId查询operation
        Map<String, String> params = new HashMap<String, String>();
        params.put("serviceId", serviceId);
        params.put("operationId", operationId);
        Operation operation = operationServiceImpl.findUniqueBy(params);
        if (operation != null) {
            mv.addObject("operation", operation);
            //根据operation查询service信息
            ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
            ServiceServiceImpl ss = context.getBean(ServiceServiceImpl.class);
            Service service = ss.getUniqueByServiceId(operation.getServiceId());
            if (service != null) {
                mv.addObject("service", service);
            }
        }

        return mv;
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
}
