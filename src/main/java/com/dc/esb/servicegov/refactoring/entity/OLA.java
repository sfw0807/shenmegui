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
@Table(name="OLA")
public class OLA implements Serializable{		

	private static final long serialVersionUID = 8344983366735550997L;
	@Id
	@Column(name="OPERATION_ID")
	private String operationId;
	@Id
	@Column(name="SERVICE_ID")
	private String serviceId;
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
	@ManyToOne(targetEntity=Operation.class)
	@JoinColumns({
		@JoinColumn(name="SERVICE_ID",referencedColumnName="SERVICE_ID",insertable=false,updatable=false),
		@JoinColumn(name="OPERATION_ID",referencedColumnName="OPERATION_ID",insertable=false,updatable=false)
	})
	private Operation operation;
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
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}	
}
