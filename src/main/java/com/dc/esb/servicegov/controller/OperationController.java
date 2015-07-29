package com.dc.esb.servicegov.controller;

import static com.dc.esb.servicegov.service.support.Constants.STATE_PASS;
import static com.dc.esb.servicegov.service.support.Constants.STATE_UNPASS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dc.esb.servicegov.entity.OperationPK;
import com.dc.esb.servicegov.util.TreeNode;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.impl.OperationServiceImpl;
import com.dc.esb.servicegov.service.impl.ServiceInvokeServiceImpl;
import com.dc.esb.servicegov.service.impl.ServiceServiceImpl;
import com.dc.esb.servicegov.service.impl.SystemServiceImpl;
import com.dc.esb.servicegov.service.support.Constants;
import com.dc.esb.servicegov.util.JSONUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dc.esb.servicegov.service.support.Constants.STATE_PASS;
import static com.dc.esb.servicegov.service.support.Constants.STATE_UNPASS;

@Controller
@RequestMapping("/operation")
public class OperationController {
    @Autowired
    private OperationServiceImpl operationServiceImpl;
    @Autowired
    private ServiceServiceImpl serviceService;
    @Autowired
    private ServiceInvokeServiceImpl serviceInvokeService;
    @Autowired
    private SystemServiceImpl systemService;

    /**
     * 获取所有的服务场景
     *
     * @return
     */
    @RequiresPermissions({"service-get"})
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

    /**
     * 根据服务获取场景列表
     *
     * @param serviceId
     * @return
     */
    @RequiresPermissions({"service-get"})
    @RequestMapping("/getOperationByServiceId/{serviceId}")
    @ResponseBody
    public Map<String, Object> getOperationByServiceId(@PathVariable(value = "serviceId") String serviceId) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Operation> rows = operationServiceImpl.findBy("serviceId", serviceId);
        result.put("total", rows.size());
        result.put("rows", rows);
        return result;
    }

    /**
     * 获取服务{serviceId}的待审核场景列表
     *
     * @param serviceId
     * @return
     */
    @RequiresPermissions({"service-get"})
    @RequestMapping("/getAudits/{serviceId}")
    @ResponseBody
    public Map<String, Object> getAudits(
            @PathVariable(value = "serviceId") String serviceId) {
        List<Operation> rows = operationServiceImpl.getUnAuditOperationByServiceId(serviceId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", rows.size());
        result.put("rows", rows);
        return result;
    }

    /**
     * 这是一个页面跳转控制,根据服务id跳转到场景添加页面
     *
     * @param req
     * @param serviceId
     * @return TODO 建议不要通过Controller控制页面跳转
     */
    @RequiresPermissions({"service-add"})
    @RequestMapping("/addPage/{serviceId}")
    public ModelAndView addPage(HttpServletRequest req, @PathVariable(value = "serviceId") String serviceId) {
        ModelAndView mv = new ModelAndView("service/operation/add");
        Service service = serviceService.getUniqueByServiceId(serviceId);
        if (service != null) {
            mv.addObject("service", service);
            List<com.dc.esb.servicegov.entity.System> systemList = systemService.getAll();
            mv.addObject("systemList", JSONUtil.getInterface().convert(systemList, com.dc.esb.servicegov.entity.System.simpleFields()));
        }
        return mv;
    }

    /**
     * 场景号唯一性验证
     *
     * @param operationId
     * @param serviceId
     * @return
     */
    @RequiresPermissions({"service-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/uniqueValid", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean uniqueValid(String operationId, String serviceId) {
        return operationServiceImpl.uniqueValid(serviceId, operationId);
    }

    @RequiresPermissions({"service-add"})
    @RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean add(Operation Operation) {
        operationServiceImpl.addOperation(Operation);
        return true;
    }

    /**
     * TODO 场景基本信息保存后，保存相关的接口映射关系。
     *
     * @param req
     * @param serviceId
     * @param operationId
     * @param consumerStr
     * @param providerStr
     * @return
     */
    @RequiresPermissions({"service-add"})
    @RequestMapping(method = RequestMethod.POST, value = "/afterAdd", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean afterAdd(HttpServletRequest req, String serviceId, String operationId, String consumerStr, String providerStr) {
        return operationServiceImpl.addInvoke(req, serviceId, operationId, consumerStr, providerStr);
    }

    /**
     * 修改场景
     *
     * @param req
     * @param Operation
     * @return
     */
    @RequiresPermissions({"service-update"})
    @RequestMapping(method = RequestMethod.POST, value = "/edit", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean edit(HttpServletRequest req, Operation Operation) {
        return operationServiceImpl.editOperation(req, Operation);
    }

    /**
     * 根据服务id，场景id跳转到场景编辑页面
     *
     * @param req
     * @param operationId
     * @param serviceId
     * @return
     */
    @RequiresPermissions({"service-update"})
    @RequestMapping("/editPage")
    public ModelAndView editPage(HttpServletRequest req, String operationId, String serviceId) {
        ModelAndView mv = new ModelAndView("service/operation/edit");
        //根据operationId查询operation
        Operation operation = operationServiceImpl.getOperation(serviceId, operationId);
        if (operation != null) {
            mv.addObject("operation", operation);
            //根据operation查询service信息
            Service service = serviceService.getById(operation.getServiceId());
            if (service != null) {
                mv.addObject("service", service);
            }
            List<com.dc.esb.servicegov.entity.System> systems = systemService.getAll();

            mv.addObject("systemList", JSONUtil.getInterface().convert(systems, com.dc.esb.servicegov.entity.System.simpleFields()));

            Map<String, String> params = new HashMap<String, String>();
            params.put("serviceId", serviceId);
            params.put("operationId", operationId);

            params.put("type", Constants.INVOKE_TYPE_CONSUMER);
            List<ServiceInvoke> consumerInvokes = serviceInvokeService.findBy(params);

            mv.addObject("consumerList", JSONUtil.getInterface().convert(consumerInvokes, ServiceInvoke.simpleFields()));

            params.put("type", Constants.INVOKE_TYPE_PROVIDER);
            List<ServiceInvoke> providerInvokes = serviceInvokeService.findBy(params);

            mv.addObject("providerList", JSONUtil.getInterface().convert(providerInvokes, ServiceInvoke.simpleFields()));
        }
        return mv;
    }

    @RequiresPermissions({"service-delete"})
    @RequestMapping(method = RequestMethod.POST, value = "/deletes", headers = "Accept=application/json")
    @ResponseBody
    public boolean deletes(@RequestBody OperationPK[] operationPks) {
        operationServiceImpl.deleteOperations(operationPks);
        return true;
    }

    @RequiresPermissions({"service-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getServiseById/{value}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Operation> getServiseById(@PathVariable String value) {
        String name = "serviceId";
        Map<String, String> params = new HashMap<String, String>();
        List<Operation> info = operationServiceImpl.findBy(params);
        return info;
    }

    @RequiresPermissions({"service-get"})
    @RequestMapping("/detailPage")
    public ModelAndView detailPage(HttpServletRequest req, String operationId, String serviceId) {
        return operationServiceImpl.detailPage(req, operationId, serviceId);
    }

    @RequiresPermissions({"service-update"})
    @RequestMapping("/release")
    public ModelAndView release(HttpServletRequest req, String operationId, String serviceId, String versionDesc) {
        operationServiceImpl.release(operationId, serviceId, versionDesc);
        return detailPage(req, operationId, serviceId);
    }

    @RequiresPermissions({"service-update"})
    @RequestMapping("/releaseBatch")
    @ResponseBody
    public boolean releaseBatch(@RequestBody Operation[] operations) {
        return operationServiceImpl.releaseBatch(operations);
    }

    @RequiresPermissions({"service-update"})
    @RequestMapping("/auditPage")
    public ModelAndView auditPage(HttpServletRequest req, String serviceId) {
        ModelAndView mv = new ModelAndView("service/operation/audit");
        //根据serviceId查询service信息
        Service service = serviceService.getUniqueByServiceId(serviceId);
        if (service != null) {
            mv.addObject("service", service);
        }
        return mv;
    }

    @RequiresPermissions({"service-update"})
    @RequestMapping(method = RequestMethod.POST, value = "/auditPass", headers = "Accept=application/json")
    @ResponseBody
    public boolean auditPass(@RequestBody String[] operationIds) {
        return operationServiceImpl.auditOperation(STATE_PASS, operationIds);
    }

    @RequiresPermissions({"service-update"})
    @RequestMapping(method = RequestMethod.POST, value = "/auditUnPass", headers = "Accept=application/json")
    @ResponseBody
    public boolean auditUnPass(@RequestBody String[] operationIds) {
        return operationServiceImpl.auditOperation(STATE_UNPASS, operationIds);
    }

    // 根据系统id查询该系统过是有接口
    @RequiresPermissions({"service-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/judgeInterface", headers = "Accept=application/json")
    @ResponseBody
    public boolean judgeInterface(String systemId) {
        boolean result = systemService.containsInterface(systemId);
        return result;
    }

    @RequiresPermissions({"service-get"})
    @RequestMapping("/interfacePage")
    public ModelAndView interfacePage(String operationId, String serviceId, HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("service/operation/interfacePage");
        // 根据serviceId获取service信息
        if (StringUtils.isNotEmpty(serviceId)) {
            Service service = serviceService.getById(serviceId);
            if (service != null) {
                mv.addObject("service", service);
            }
            if (StringUtils.isNotEmpty(operationId)) {
                // 根据serviceId,operationId获取operation信息
                Operation operation = operationServiceImpl.getOperation(serviceId, operationId);
                if (operation != null) {
                    mv.addObject("operation", operation);
                    List<?> systemList = systemService.getAll();
                    mv.addObject("systemList", systemList);
                }
            }
        }
        return mv;
	}
    /**
     * TODO根据元数据ID查询场景列表
     * @param metadataId
     * @return
     */
    @RequiresPermissions({"service-get"})
    @RequestMapping("/getByMetadataId/{metadataId}")
    @ResponseBody
    public List<TreeNode> getByMetadataId(@PathVariable(value = "metadataId") String metadataId){
        return operationServiceImpl.getTreeByMetadataId(metadataId);
    }

}
