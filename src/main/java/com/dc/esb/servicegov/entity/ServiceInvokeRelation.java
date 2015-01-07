package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-9
 * Time: 下午6:05
 */
@Entity
@Table(name = "SG_MM_MSM_SERVICE_INVOKE_RELAT")
public class ServiceInvokeRelation {
    @Id
    @Column(name = "RECORDID")
    private String recordId;
    @Column(name = "PROVIDESYS")
    private String providerSystemId;
    @Column(name = "CONSUMESYS")
    private String consumerSystemAb;
    @Column(name = "SERVICE")
    private String serviceId;
    @Column(name = "SCENE")
    private String operationId;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "ECODE")
    private String interfaceId;
    @Column(name = "FUNCTYPE")
    private String functionType;
    @Column(name = "PASSEDSYS")
    private String passbySys;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getProviderSystemId() {
        return providerSystemId;
    }

    public void setProviderSystemId(String providerSystemId) {
        this.providerSystemId = providerSystemId;
    }

    public String getConsumerSystemAb() {
        return consumerSystemAb;
    }

    public void setConsumerSystemAb(String consumerSystemAb) {
        this.consumerSystemAb = consumerSystemAb;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public String getPassbySys() {
        return passbySys;
    }

    public void setPassbySys(String passbySys) {
        this.passbySys = passbySys;
    }
}
