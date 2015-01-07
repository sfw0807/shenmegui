package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InvokeInfoPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4525187039122019005L;
	@Column(name = "SERVICE_ID")
	private String serviceId;
	@Column(name = "OPERATION_ID")
	private String operationId;
	@Column(name = "ECODE")
	private String ecode;
	@Column(name = "PROVIDE_SYS_ID")
	private String provideSysId;
	@Column(name = "CONSUME_SYS_ID")
	private String consumeSysId;

	@Override
	public int hashCode() {
		int hashCode = 0;
		if (null != operationId) {
			hashCode ^= operationId.hashCode();
		}
		if (null != serviceId) {
			hashCode ^= serviceId.hashCode();
		}
		if (null != ecode) {
			hashCode ^= ecode.hashCode();
		}
		if (null != provideSysId) {
			hashCode ^= provideSysId.hashCode();
		}
		if (null != consumeSysId) {
			hashCode ^= consumeSysId.hashCode();
		}
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof InvokeInfoPK)) {
			return false;
		}
		InvokeInfoPK another = (InvokeInfoPK) obj;
		boolean operationIdEq = (null == this.operationId) ? (null == another
				.getOperationId()) : (this.operationId.equals(another
				.getOperationId()));
		boolean serviceIdEq = (null == this.serviceId) ? (null == another
				.getServiceId()) : (this.serviceId.equals(another
				.getServiceId()));
		boolean ecodeEq = (null == this.ecode) ? (null == another.getEcode())
				: (this.ecode.equals(another.getEcode()));
		boolean provideSysIdEq = (null == this.provideSysId) ? (null == another
				.getProvideSysId()) : (this.provideSysId.equals(another
				.getProvideSysId()));
		boolean consumeSysIdEq = (null == this.consumeSysId) ? (null == another
				.getConsumeSysId()) : (this.consumeSysId.equals(another
				.getConsumeSysId()));
		return operationIdEq && serviceIdEq && ecodeEq && provideSysIdEq
				&& consumeSysIdEq;
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

}
