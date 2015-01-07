package com.dc.esb.servicegov.controller;


import com.dc.esb.servicegov.entity.RemainingService;

import java.util.ArrayList;
import java.util.List;

import com.dc.esb.servicegov.entity.RemainingService;
import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.vo.ServiceInvokeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.entity.SDANode;
import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.entity.ServiceExtendInfo;
import com.dc.esb.servicegov.entity.ServiceOLA;
import com.dc.esb.servicegov.entity.ServiceSLA;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.vo.SDA;
import com.dc.esb.servicegov.vo.ServiceInvokeVo;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-4
 * Time: 上午10:33
 */
@Controller
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private ServiceManagerImpl serviceManager;

    List<SDANode> lstSDA = new ArrayList<SDANode>();
    
    /**
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Service> getAllService() {
        return serviceManager.getAllServices();
    }

    /**
     * 
     * @param serviceId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getInvokeRelation/{serviceId}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<ServiceInvokeVo> getServiceInvokeRelation(@PathVariable String serviceId) {
        return serviceManager.getServiceInvokeInfo(serviceId);
    }

    /**
     * 
     * @param operationId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getByOperationId/{operationId}", headers = "Accept=application/json")
     public
     @ResponseBody
     List<Service> getServiceByOperationId(@PathVariable String operationId) {
        return serviceManager.getServiceByOperationId(operationId);
    }

    /**
     * check WSDL Version return "1" for remaining services and "2" for new services
     * @param serviceId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/checkWSDLVersion/{serviceId}", headers = "Accept=application/json")
    public
    @ResponseBody
    String checkWSDLVersion(@PathVariable String serviceId) {
        List<Service> services = serviceManager.getServiceById(serviceId);
        if(null == services){
            return "0";
        }
        if(services.size() == 0){
            return "0";
        }
        List<RemainingService> remainingServices = serviceManager.getRemainingServiceByServiceId(serviceId);
        return (null != remainingServices && remainingServices.size() > 0) ? "1":"2";
    }
    
    /**
     * 
     * @param resourceId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getServiceExtendInfoByOperationId/{resourceId}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<ServiceExtendInfo> getServiceExtendInfoByOperationId(@PathVariable String resourceId) {
        return serviceManager.getServiceExtendInfoByOperationId(resourceId);
    }
    
    /**
     * 
     * @param serviceId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getServiceById/{serviceId}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Service> getServiceById(@PathVariable String serviceId) {
        return serviceManager.getServiceById(serviceId);
    }
    
    /**
     * 
     * @param resourceId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getServiceByResourceId/{resourceId}", headers = "Accept=application/json")
    public
    @ResponseBody
    Service getServiceByResourceId(@PathVariable String resourceId) {
        return serviceManager.getServiceByResourceId(resourceId);
    }
    
    /**
     * 
     * @param serviceId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getServiceSlaById/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<ServiceSLA> getServiceSlaById(@PathVariable String id) {
        Service service =  serviceManager.getServiceByResourceId(id);
        return serviceManager.getServiceSlaById(service.getResourceId());
    }
    
    /**
     * 
     * @param serviceId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getServiceOlaById/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<ServiceOLA> getServiceOlaById(@PathVariable String id) {
        Service service =  serviceManager.getServiceByResourceId(id);
        return serviceManager.getServiceOlaById(service.getResourceId());
    }
    
    /**
     * 
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getServiceChild/{id}", headers = "Accept=application/json")
	public @ResponseBody
	List<SDANode> getChildSDAInfo(@PathVariable String id) {
		try {
			lstSDA = new ArrayList<SDANode>();
			Service service = serviceManager.getServiceById(id).get(0);
			SDA sda =  serviceManager.getSDAofService(service);
			renderSDA(sda);
		} catch (DataException e) {
			e.printStackTrace();
		}
		return lstSDA;
	}
    
    @RequestMapping(method = RequestMethod.GET, value = "/getServiceChildByResourceId/{id}", headers = "Accept=application/json")
	public @ResponseBody
	List<SDANode> getServiceChildByResourceId(@PathVariable String id) {
		try {
			lstSDA = new ArrayList<SDANode>();
			Service service = serviceManager.getServiceByResourceId(id);
			SDA sda =  serviceManager.getSDAofService(service);
			renderSDA(sda);
		} catch (DataException e) {
			e.printStackTrace();
		}
		return lstSDA;
	}
    /**
     * 
     * @param sda
     */
    private void renderSDA(SDA sda) {
		SDANode node = sda.getValue();
		lstSDA.add(node);
		if (sda.getChildNode() != null) {
			List<SDA> childSda = sda.getChildNode();
			for (SDA a : childSda) {
				renderSDA(a);
			}
		}
	}
}
