package com.dc.esb.servicegov.vo;

import com.dc.esb.servicegov.entity.Service;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-10
 * Time: 上午9:06
 */
public class ServiceVo {
    private String serviceId;
    private String serviceName;
    private String serviceRemark;
    private String modifyUser;
    private String state;
    private String resourceId;
    private String versionState;
    private String actionId;
    private String type;
    private String providerSysName;
    private String consumerSysName;

    public ServiceVo(Service service) {

        this.serviceId = service.getServiceId();
        this.serviceName = service.getServiceName();
        this.serviceRemark = service.getServiceRemark();
        this.modifyUser = service.getModifyUser();
        this.state = service.getState();
        this.resourceId = service.getResourceId();
        this.versionState = service.getVersionState();
        this.actionId = service.getActionId();
        this.type = service.getType();
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

    public String getServiceRemark() {
        return serviceRemark;
    }

    public void setServiceRemark(String serviceRemark) {
        this.serviceRemark = serviceRemark;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getVersionState() {
        return versionState;
    }

    public void setVersionState(String versionState) {
        this.versionState = versionState;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProviderSysName() {
        return providerSysName;
    }

    public void setProviderSysName(String providerSysName) {
        this.providerSysName = providerSysName;
    }

    public String getConsumerSysName() {
        return consumerSysName;
    }

    public void setConsumerSysName(String consumerSysName) {
        this.consumerSysName = consumerSysName;
    }
}
