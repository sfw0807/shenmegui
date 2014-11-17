package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="OLA_HISTORY")
public class OLAHistory implements Serializable{		
	private static final long serialVersionUID = -3883139062524478519L;
	@Id
	@Column(name="OPERATION_ID")
	private String operationId;
	@Id
	@Column(name="OPERATION_VERSION")
	private String operationVersion;
	@Id
	@Column(name="SERVICE_ID")
	private String serviceId;
	@Id
	@Column(name="SERVICE_VERSION")
	private String serviceVersion;
	@Id
	@Column(name="OLA_NAME")
	private String olaName;
	@Column(name="OLA_VALUE")
	private String olaValue;
	@Column(name="OLA_REMARK")
	private String olaRemark;
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
	public String getOperationVersion() {
		return operationVersion;
	}
	public void setOperationVersion(String operationVersion) {
		this.operationVersion = operationVersion;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceVersion() {
		return serviceVersion;
	}
	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}
	public String getOlaName() {
		return olaName;
	}
	public void setOlaName(String olaName) {
		this.olaName = olaName;
	}
	public String getOlaValue() {
		return olaValue;
	}
	public void setOlaValue(String olaValue) {
		this.olaValue = olaValue;
	}
	public String getOlaRemark() {
		return olaRemark;
	}
	public void setOlaRemark(String olaRemark) {
		this.olaRemark = olaRemark;
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
}
