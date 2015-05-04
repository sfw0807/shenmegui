package com.dc.esb.servicegov.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SG_SLA")
public class SLA implements Serializable {
    private static final long serialVersionUID = 6065805182895922221L;
    @Id
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Id
    @Column(name = "OPERATION_ID")
    private String operationId;
    @Id
    @Column(name = "SLA_NAME")
    private String slaName;
    @Column(name = "SLA_VALUE")
    private String slaValue;
    @Column(name = "SLA_REMARK")
    private String slaRemark;
    @Column(name = "MODIFYUSER")
    private String modifyUser;
    @Column(name = "UPDATETIME")
    private String updateTime;
    @ManyToOne(targetEntity = Operation.class)
    @JoinColumns({
            @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERVICE_ID", insertable = false, updatable = false),
            @JoinColumn(name = "OPERATION_ID", referencedColumnName = "OPERATION_ID", insertable = false, updatable = false)
    })
    private Operation operation;

    public SLA() {

    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SLA)) {
            return false;
        }
        SLA another = (SLA) obj;
        return ((null == this.serviceId) ? (null == another.getServiceId()) : (this.serviceId.equals(another.getServiceId()))) &&
                ((null == this.operationId) ? (null == another.getOperationId()) : (this.operationId.equals(another.getOperationId()))) &&
                ((null == this.slaName) ? (null == another.getSlaName()) : (this.slaName.equals(another.getSlaName())));
    }


    public SLA(String serviceId, String operationId, String slaName, String slaValue) {
        this.serviceId = serviceId;
        this.operationId = operationId;
        this.slaName = slaName;
        this.slaValue = slaValue;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getSlaName() {
        return slaName;
    }

    public void setSlaName(String slaName) {
        this.slaName = slaName;
    }

    public String getSlaValue() {
        return slaValue;
    }

    public void setSlaValue(String slaValue) {
        this.slaValue = slaValue;
    }

    public String getSlaRemark() {
        return slaRemark;
    }

    public void setSlaRemark(String slaRemark) {
        this.slaRemark = slaRemark;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
