package com.dc.esb.servicegov.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RemainingServicePK implements Serializable {

    private String serviceId;
    private String operationId;

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

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof  RemainingServicePK)){
            return false;
        }
        RemainingServicePK another = (RemainingServicePK) obj;
        return ((null == this.serviceId) ?(null == another.getServiceId()):(this.serviceId.equals(another.getServiceId())))&&
                ((null == this.operationId) ?(null == another.getOperationId()):(this.operationId.equals(another.getOperationId())));
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