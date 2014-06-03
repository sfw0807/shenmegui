package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-27
 * Time: 上午10:36
 */
@Entity
@Table(name="SG_MM_MSM_SERVICE")
public class Service {
    @Id
    @Column(name="SERVICEID")
    private String serviceId;
    @Column(name="SERVICENAME")
    private String serviceName;
    @Column(name="SVRREMARK")
    private String serviceRemark;
    @Column(name="MODIFYUSERID")
    private String modifyUser;
    @Column(name="STATE")
    private String state;
    @Column(name="RESOURCEID")
    private String resourceId;
    @Column(name="VERSIONSTATE")
    private String versionState;
    @Column(name="ACTIONID")
    private String actionId;

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
}
