package com.dc.esb.servicegov.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Vincent Fan
 * Date: 14-8-22
 * Time: 下午4:39
 */

@Entity
@Table(name = "OPERATION")
@IdClass(OperationPK.class)
public class Operation {
    @Id
    @Column(name="OPERATION_ID")
    private String operationId;
    @Id
    @Column(name="SERVICE_ID")
    private String serviceId;
    @Column(name="OPERATION_NAME")
    private String operationName;
    @Column (name="REMARK")
    private String remark;
    @Column(name="VERSION")
    private String version;
    @Column(name="STATE")
    private String state;

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
