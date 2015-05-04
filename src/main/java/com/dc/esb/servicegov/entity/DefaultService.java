package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-30
 * Time: 下午4:59
 */
@Entity
@Table(name = "DEFAULT_SERVICE")
public class DefaultService {
    @Id
    @Column(name = "SERVICE_ID")
    private String defaultServiceId;
    @Column(name = "SERVICE_NAME")
    private String defaultServiceName;
    @Column(name = "SERVICE_REMARK")
    private String defaultServiceRemark;

    public String getDefaultServiceId() {
        return defaultServiceId;
    }

    public void setDefaultServiceId(String defaultServiceId) {
        this.defaultServiceId = defaultServiceId;
    }

    public String getDefaultServiceName() {
        return defaultServiceName;
    }

    public void setDefaultServiceName(String defaultServiceName) {
        this.defaultServiceName = defaultServiceName;
    }

    public String getDefaultServiceRemark() {
        return defaultServiceRemark;
    }

    public void setDefaultServiceRemark(String defaultServiceRemark) {
        this.defaultServiceRemark = defaultServiceRemark;
    }
}
