package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class ServiceLevelAgreementPK implements Serializable{

	private static final long serialVersionUID = -3855766136612762248L;
	
	@Column(name="SERVICE_ID")
	private String serviceId;
	@Column(name="OPERATION_ID")
	private String operationId;
	@Column(name="SLA_NAME")
	private String slaName;
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
	@Override
    public boolean equals(Object obj) {
        if(!(obj instanceof  ServiceLevelAgreementPK)){
            return false;
        }
        ServiceLevelAgreementPK another = (ServiceLevelAgreementPK) obj;
        return ((null == this.operationId) ?(null == another.getOperationId()):(this.operationId.equals(another.getOperationId())))&&
                ((null == this.serviceId) ?(null == another.getServiceId()):(this.serviceId.equals(another.getServiceId())))&&
                ((null == this.slaName) ?(null == another.getSlaName()):(this.serviceId.equals(another.getSlaName())));
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
        if(null != slaName){
            hashCode ^= slaName.hashCode();
        }
        return hashCode;
    }
	
}
