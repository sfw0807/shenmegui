package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.InvokeConnection;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.impl.InvokeConnectionServiceImpl;
import com.dc.esb.servicegov.service.impl.OperationServiceImpl;
import com.dc.esb.servicegov.service.impl.ServiceInvokeServiceImpl;
import com.dc.esb.servicegov.service.impl.ServiceServiceImpl;
import com.dc.esb.servicegov.vo.ServiceInvokeInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.portlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vincentfxz on 15/7/9.
 */
@Controller
@RequestMapping("/serviceLink")
public class ServiceLinkController {
    @Autowired
    private ServiceInvokeServiceImpl serviceInvokeService;
    @Autowired
    private InvokeConnectionServiceImpl invokeConnectionService;
    @Autowired
    private OperationServiceImpl operationService;
    @Autowired
    private ServiceServiceImpl serviceService;

    @RequestMapping(method = RequestMethod.GET, value = "/getServiceLink/system/{systemId}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<ServiceInvokeInfoVO> getServiceLink(@PathVariable("systemId") String systemId) {
        List<ServiceInvokeInfoVO> serviceInvokeInfoVOs = new ArrayList<ServiceInvokeInfoVO>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("systemId", systemId);
        List<ServiceInvoke> serviceInvokes = serviceInvokeService.findBy(params);
        for(ServiceInvoke serviceInvoke : serviceInvokes){
            ServiceInvokeInfoVO serviceInvokeInfoVO = new ServiceInvokeInfoVO(serviceInvoke);
            if(serviceInvoke.getOperationId()!=null && serviceInvoke.getServiceId()!=null){
                Operation operation = operationService.getOperation(serviceInvoke.getServiceId(),serviceInvoke.getOperationId());
                Service service = serviceService.getById(serviceInvoke.getServiceId());
                serviceInvokeInfoVO.setServiceName(service.getServiceName());
                serviceInvokeInfoVO.setOperationName(operation.getOperationName());
            }
            serviceInvokeInfoVOs.add(serviceInvokeInfoVO);
        }
        return serviceInvokeInfoVOs;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/serviceLinkInfo/system/{systemId}", headers = "Accept=application/json")
    public List<ServiceInvokeInfoVO> serviceLinkInfo(@PathVariable("systemId") String systemId) {
        List<ServiceInvokeInfoVO> serviceInvokeInfoVOs = new ArrayList<ServiceInvokeInfoVO>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("systemId", systemId);
        List<ServiceInvoke> serviceInvokes = serviceInvokeService.findBy(params);
        for(ServiceInvoke serviceInvoke : serviceInvokes){
            ServiceInvokeInfoVO serviceInvokeInfoVO = new ServiceInvokeInfoVO(serviceInvoke);
            if(serviceInvoke.getOperationId()!=null && serviceInvoke.getServiceId()!=null){
                Operation operation = operationService.getOperation(serviceInvoke.getServiceId(),serviceInvoke.getOperationId());
                Service service = serviceService.getById(serviceInvoke.getServiceId());
                serviceInvokeInfoVO.setServiceName(service.getServiceName());
                serviceInvokeInfoVO.setOperationName(operation.getOperationName());
            }
            serviceInvokeInfoVOs.add(serviceInvokeInfoVO);
        }
        return serviceInvokeInfoVOs;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/invokeConnections/sourceId/{sourceId}", headers = "Accept=application/json")
    public @ResponseBody List<InvokeConnection> getInvokeConnetcion(@PathVariable("sourceId") String sourceId){
        return invokeConnectionService.getConnectionsStartWith(sourceId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/parentInvokeConnections/sourceId/{sourceId}", headers = "Accept=application/json")
    public @ResponseBody List<InvokeConnection> parentInvokeConnections(@PathVariable("sourceId") String sourceId){
        return invokeConnectionService.getConnectionsEndWith(sourceId);
    }
    @RequestMapping(method = RequestMethod.POST, value = "/save", headers = "Accept=application/json")
    public @ResponseBody boolean save(@RequestBody InvokeConnection[] connections) {
        for(InvokeConnection connection : connections){
            String sourceId = connection.getSourceId();
            String targetId = connection.getTargetId();
            Map<String, String> params = new HashMap<String, String>();
            params.put("sourceId", sourceId);
            params.put("targetId", targetId);
            List<InvokeConnection> existedConnections = invokeConnectionService.findBy(params);
            if(null == existedConnections){
                invokeConnectionService.save(connection);
            }else if(existedConnections.size() == 0){
                invokeConnectionService.save(connection);
            }
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete", headers = "Accept=application/json")
    public @ResponseBody boolean delete(@RequestBody InvokeConnection[] connections) {
        for(InvokeConnection connection : connections){
            String sourceId = connection.getSourceId();
            String targetId = connection.getTargetId();
            Map<String, String> params = new HashMap<String, String>();
            params.put("sourceId", sourceId);
            params.put("targetId", targetId);
            List<InvokeConnection> existedConnections = invokeConnectionService.findBy(params);
            if(null != existedConnections){
                for(InvokeConnection invokeConnection : existedConnections){
                    invokeConnectionService.delete(invokeConnection);
                }
            }
        }
        return true;
    }
}
