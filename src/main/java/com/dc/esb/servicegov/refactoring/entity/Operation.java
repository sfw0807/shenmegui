package com.dc.esb.servicegov.refactoring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

//OPERATION_ID  	VARCHAR(255) NOT NULL,
//SERVICE_ID    	VARCHAR(40) NOT NULL,
//OPERATION_NAME	VARCHAR(100),
//REMARK        	VARCHAR(500),
//VERSION       	VARCHAR(25),
//STATE         	VARCHAR(40),

@Entity
@Table(name="OPERATION")
@IdClass(OperationPK.class)
public class Operation {
	@Id
	private String operationId;
	@Id
	private String serviceId;
	@Column(name="OPERATION_NAME")
	private String operationName;
	@Column(name="REMARK")
	private String remark;
	@Column(name="VERSION")
	private String version;
	@Column(name="STATE")
	private String state;
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
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
