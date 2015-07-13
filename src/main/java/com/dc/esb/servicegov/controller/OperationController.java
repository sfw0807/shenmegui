package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.impl.OperationServiceImpl;
import com.dc.esb.servicegov.service.impl.ServiceInvokeServiceImpl;
import com.dc.esb.servicegov.service.impl.ServiceServiceImpl;
import com.dc.esb.servicegov.service.impl.SystemServiceImpl;
import org.apache.commons.lang.StringUtils;
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
    @RequestMapping("/addPage/{serviceId}")
    public ModelAndView addPage(HttpServletRequest req, @PathVariable(value = "serviceId") String serviceId) {
        ModelAndView mv = new ModelAndView("service/operation/add");
        Service service = serviceService.getUniqueByServiceId(serviceId);
        if (service != null) {
            mv.addObject("service", service);
        }
        return mv;
    }

    //场景号唯一性验证

    /**
     * 场景号唯一性验证
     *
     * @param operationId
     * @param serviceId
     * @return
     */
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

    /**
     * TODO 这是什么鬼
     *
     * @param req
     * @param serviceId
     * @param operationId
     * @param consumerStr
     * @param providerStr
     * @return
     */
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
            mv.addObject("systemList", systems);
            Map<String, String> params = new HashMap<String, String>();
            params.put("serviceId", serviceId);
            params.put("operationId", operationId);
            /**
             * TODO use Constants
             */
            params.put("type", "0");
            List<ServiceInvoke> consumerInvokes = serviceInvokeService.findBy(params);
            mv.addObject("consumerList", consumerInvokes);
            params.put("type", "1");
            List<ServiceInvoke> providerInvokes = serviceInvokeService.findBy(params);
            mv.addObject("providerList", providerInvokes);
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
    public ModelAndView release(HttpServletRequest req, String operationId, String serviceId,String versionDesc) {
        operationServiceImpl.release(operationId, serviceId, versionDesc);
        return detailPage(req, operationId, serviceId);
    }

    @RequestMapping("/releaseBatch")
    @ResponseBody
    public boolean releaseBatch(@RequestBody Operation[] operations) {
        return operationServiceImpl.releaseBatch(operations);
    }

	@RequestMapping("/auditPage")
	public ModelAndView auditPage(HttpServletRequest req, String serviceId) {
        ModelAndView mv = new ModelAndView("service/operation/audit");
        //根据serviceId查询service信息
        com.dc.esb.servicegov.entity.Service service = serviceService.getUniqueByServiceId(serviceId);
        if(service != null){
            mv.addObject("service", service);
        }
        return mv;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/auditPass", headers = "Accept=application/json")
	@ResponseBody
	public boolean auditPass(@RequestBody String[] operationIds) {
		return operationServiceImpl.auditOperation(STATE_PASS, operationIds);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/auditUnPass", headers = "Accept=application/json")
	@ResponseBody
	public boolean auditUnPass(@RequestBody String[] operationIds) {
		return operationServiceImpl.auditOperation(STATE_UNPASS, operationIds);
	}

	// 根据系统id查询该系统过是有接口
	@RequestMapping("/judgeInterface")
	@ResponseBody
	public boolean judgeInterface(String systemId) {
		return systemService.containsInterface(systemId);
	}

	@RequestMapping("/interfacePage")
	public ModelAndView interfacePage(String operationId, String serviceId, HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("service/operation/interfacePage");
        // 根据serviceId获取service信息
        if (StringUtils.isNotEmpty(serviceId)) {
            com.dc.esb.servicegov.entity.Service service = serviceService.getById(serviceId);
            if (service != null) {
                mv.addObject("service", service);
            }
            if (StringUtils.isNotEmpty(operationId)) {
                // 根据serviceId,operationId获取operation信息
                Operation operation = operationServiceImpl.getOperation(serviceId,operationId);
                if (operation != null) {
                    mv.addObject("operation", operation);
                    List<?> systemList = systemService.getAll();
                    mv.addObject("systemList", systemList);
                }
            }
        }
        return mv;
	}

	//根据系统id查询接口列表

    /**
     * TODO 这个方法放在这个Controller是什么道理
     * @param systemId
     * @return
     */
	@RequestMapping("/getInterface")
	@ResponseBody
	public Map<String, Object> getInterface(String systemId) {
		List<ServiceInvoke> rows = serviceInvokeService.findBy("systemId", systemId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}

    /**
     * TODO 这个方法放在这个Controller是什么道理
     * @param serviceId
     * @param operationId
     * @param systemId
     * @return
     */
	@RequestMapping("/getInterfaceByOSS")
	@ResponseBody
	public Map<String, Object> getInterfaceByOSS(String serviceId, String operationId, String systemId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("serviceId", serviceId);
        params.put("operationId", operationId);
		List<?> rows = serviceInvokeService.findBy(params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
	}
}
