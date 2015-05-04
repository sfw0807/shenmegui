package com.dc.esb.servicegov.vo;

/**
 * @author G
 */
public class InterfaceVO {

    /*--INTERFACE--*/
    private String interfaceId;
    private String ecode;
    private String interfaceName;
    private String through;
    private String remark;
    /*--INVOKE_RELATION--*/
    private int id;
    private String serviceId;
    private String operationId;
    // ecode ^
    private String providerSysId;
    private String consumerSysId;
    private String consumerMsgType;
    private String providerMsgType;
    private String passBySys;

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getThrough() {
        return through;
    }

    public void setThrough(String through) {
        this.through = through;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getProviderSysId() {
        return providerSysId;
    }

    public void setProviderSysId(String providerSysId) {
        this.providerSysId = providerSysId;
    }

    public String getConsumerSysId() {
        return consumerSysId;
    }

    public void setConsumerSysId(String consumerSysId) {
        this.consumerSysId = consumerSysId;
    }

    public String getConsumerMsgType() {
        return consumerMsgType;
    }

    public void setConsumerMsgType(String consumerMsgType) {
        this.consumerMsgType = consumerMsgType;
    }

    public String getProviderMsgType() {
        return providerMsgType;
    }

    public void setProviderMsgType(String providerMsgType) {
        this.providerMsgType = providerMsgType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPassBySys() {
        return passBySys;
    }

    public void setPassBySys(String passBySys) {
        this.passBySys = passBySys;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

}
