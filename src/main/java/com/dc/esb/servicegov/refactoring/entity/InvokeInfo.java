package com.dc.esb.servicegov.refactoring.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "INVOKE_RELATION")
@IdClass(InvokeInfoPK.class)
public class InvokeInfo {
    @Id
    private String serviceId;
    @ManyToOne(targetEntity = Service.class)
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERVICE_ID", updatable = false, insertable = false)
    private Service service;
    @Id
    private String operationId;
    @ManyToOne
    @NotFound(action = NotFoundAction.EXCEPTION)
    @JoinColumns({
            @JoinColumn(name = "SERVICE_ID",referencedColumnName="SERVICE_ID", insertable = false, updatable = false),
            @JoinColumn(name = "OPERATION_ID", referencedColumnName="OPERATION_ID", insertable = false, updatable = false)
    })

    private Operation operation;
    @Id
    private String ecode;
    @Id
    private String provideSysId;
    @Id
    private String consumeSysId;
    @Column(name = "PASSBY_SYS_ID")
    private String passbySysId;
    @Column(name = "CONSUME_MSG_TYPE")
    private String consumeMsgType;
    @Column(name = "PROVIDE_MSG_TYPE")
    private String provideMsgType;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
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

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getProvideSysId() {
        return provideSysId;
    }

    public void setProvideSysId(String provideSysId) {
        this.provideSysId = provideSysId;
    }

    public String getConsumeSysId() {
        return consumeSysId;
    }

    public void setConsumeSysId(String consumeSysId) {
        this.consumeSysId = consumeSysId;
    }

    public String getPassbySysId() {
        return passbySysId;
    }

    public void setPassbySysId(String passbySysId) {
        this.passbySysId = passbySysId;
    }

    public String getConsumeMsgType() {
        return consumeMsgType;
    }

    public void setConsumeMsgType(String consumeMsgType) {
        this.consumeMsgType = consumeMsgType;
    }

    public String getProvideMsgType() {
        return provideMsgType;
    }

    public void setProvideMsgType(String provideMsgType) {
        this.provideMsgType = provideMsgType;
    }
}
