package com.dc.esb.servicegov.entity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Vincent Fan
 * Date: 14-8-22
 * Time: 下午5:07
 */
public class OperationPK {

    private String operationId;
    private String serviceId;

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

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof  OperationPK)){
            return false;
        }
        OperationPK another = (OperationPK) obj;
        return ((null == this.operationId) ?(null == another.getServiceId()):(this.operationId.equals(another.getOperationId())))&&
                ((null == this.serviceId) ?(null == another.getServiceId()):(this.serviceId.equals(another.getServiceId())));
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        if(null != serviceId){
            hashCode ^= serviceId.hashCode();
        }
        if(null != operationId){
            hashCode ^= operationId.hashCode();
        }
        return hashCode;
    }


}
