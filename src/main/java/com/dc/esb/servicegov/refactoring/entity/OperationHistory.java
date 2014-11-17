package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="OPERATION_HISTORY")
public class OperationHistory implements Serializable{
	private static final long serialVersionUID = 2684468511774355347L;
	@Id
	@Column(name="OPERATION_ID")
	private String operationId;
	@Id
	@Column(name="SERVICE_ID")
	private String serviceId;
	@Column(name="OPERATION_NAME")
	private String operationName;
	@Column(name="REMARK")
	private String remark;
	@Id
	@Column(name="OPERATION_VERSION")
	private String operationVersion;
	@Column(name="OPERATION_STATE")
	private String operationState;
	@Id
	@Column(name="SERVICE_VERSION")
	private String serviceVersion;
	@Column(name="MODIFYUSER")
	private String modifyUser;
	@Column(name="UPDATETIME")
	private String updateTime;
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
	public String getOperationVersion() {
		return operationVersion;
	}
	public void setOperationVersion(String operationVersion) {
		this.operationVersion = operationVersion;
	}
	public String getServiceVersion() {
		return serviceVersion;
	}
	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getOperationState() {
		return operationState;
	}
	public void setOperationState(String operationState) {
		this.operationState = operationState;
	}
}
