package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-29
 * Time: 下午5:58
 */
@Entity
@Table(name = "SG_MM_MSM_GROUP_SVC_R")
public class ServiceBelong {
    @Id
    @Column(name = "SERVICEID")
    private String serviceId;
    @Column(name = "GROUPID")
    private String groupId;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
