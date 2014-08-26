package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OperationPK implements Serializable{
	
	@Column(name="OPERATION_ID")
	private String operationId;
	@Column(name="SERVICE_ID")
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
        return ((null == this.operationId) ?(null == another.getOperationId()):(this.operationId.equals(another.getOperationId())))&&
                ((null == this.serviceId) ?(null == another.getServiceId()):(this.serviceId.equals(another.getServiceId())));
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        if(null != operationId){
            hashCode ^= operationId.hashCode();
        }
        if(null != serviceId){
            hashCode ^= serviceId.hashCode();
        }
        return hashCode;
    }
    
    @Override
	public String toString() {
		return this.serviceId + ":" + this.operationId;
	}
}
