package com.dc.esb.servicegov.refactoring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SG_SLA")
@IdClass(ServiceLevelAgreementPK.class)
public class ServiceLevelAgreement {
	@Id
	private String serviceId;
	@ManyToOne(targetEntity=Service.class)
	@JoinColumns({
		@JoinColumn(name="SERVICE_ID", referencedColumnName = "SERVICE_ID", insertable=false, updatable=false)
	})
	private Service service;

	@Id
	private String operationId;
	
	@ManyToOne(targetEntity=Operation.class)
	@JoinColumns({
			@JoinColumn(name="SERVICE_ID", referencedColumnName = "SERVICE_ID", insertable=false, updatable=false),
			@JoinColumn(name="OPERATION_ID", referencedColumnName = "OPERATION_ID", insertable=false, updatable=false)
	})
	private Operation operation;
	@Id
	private String slaName;
	@Column(name="SLA_VALUE")
	private String slaValue;
	@Column(name="SLA_REMARK")
	private String slaRemark;
	
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
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public String getSlaName() {
		return slaName;
	}
	public void setSlaName(String slaName) {
		this.slaName = slaName;
	}
	public String getSlaValue() {
		return slaValue;
	}
	public void setSlaValue(String slaValue) {
		this.slaValue = slaValue;
	}
	public String getSlaRemark() {
		return slaRemark;
	}
	public void setSlaRemark(String slaRemark) {
		this.slaRemark = slaRemark;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
}
