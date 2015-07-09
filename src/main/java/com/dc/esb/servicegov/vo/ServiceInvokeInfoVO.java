package com.dc.esb.servicegov.vo;

import com.dc.esb.servicegov.entity.ServiceInvoke;

/**
 * Created by vincentfxz on 15/7/9.
 */
public class ServiceInvokeInfoVO {
    private String interfaceId;
    private String interfaceName;
    private String serviceId;
    private String serviceName;
    private String nodeType;
    private String bussCategory;
    private String status;

    public ServiceInvokeInfoVO(ServiceInvoke serviceInvoke){
        this.interfaceId = serviceInvoke.getInterfaceId();
        this.serviceId = serviceInvoke.getServiceId();
        this.interfaceId = serviceInvoke.getInterfaceId();
        this.interfaceName = serviceInvoke.getInter().getInterfaceName();
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getBussCategory() {
        return bussCategory;
    }

    public void setBussCategory(String bussCategory) {
        this.bussCategory = bussCategory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
