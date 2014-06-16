package com.dc.esb.servicegov.vo;

import com.dc.esb.servicegov.entity.ServiceInvokeRelation;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-10
 * Time: 下午1:41
 */
public class ServiceInvokeVo {

    private String serviceId;
    private String operationId;
    private String interfaceId;
    private String consumerName;
    private String providerName;
    private String passBySysName;
    private String messageType;

    public ServiceInvokeVo(ServiceInvokeRelation sr) {
        this.interfaceId = sr.getInterfaceId();
        this.serviceId = sr.getServiceId();
        this.operationId = sr.getOperationId();
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
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

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getPassBySysName() {
        return passBySysName;
    }

    public void setPassBySysName(String passBySysName) {
        this.passBySysName = passBySysName;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
