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
@Table(name = "SERVICE")
public class Service {
    @Id
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Column(name = "SERVICE_NAME")
    private String serviceName;
    @Column(name="SERVICE_REMARK")
    private String remark;
    @Column(name="CATEGORY_ID")
    private String categoryId;
    @Column(name="VERSION")
    private String version;
    @Column(name="STATE")
    private String state;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
