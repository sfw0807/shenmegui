package com.dc.esb.servicegov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SAL")
public class SLA implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SLA_ID")
	private String slaId;
	
	@Column(name = "SLA_NAME")
	private String slaName;
	
	@Column(name = "SLA_VALUE")
	private String slaValue;
	
	@Column(name = "SLA_DESC")
	private String slaDesc;
	
	@Column(name = "SLA_REMARK")
	private String slaRemark;
	
	@Column(name = "OPERATION_ID")
	private String operationId;
	
	@Column(name = "SERVICE_ID")
	private String serviceId;
	
	@Column(name = "SLA_TEMPLATE_ID")
	private String slaTemplateId;

	public String getSlaId() {
		return slaId;
	}

	public void setSlaId(String slaId) {
		this.slaId = slaId;
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

	public String getSlaDesc() {
		return slaDesc;
	}

	public void setSlaDesc(String slaDesc) {
		this.slaDesc = slaDesc;
	}

	public String getSlaRemark() {
		return slaRemark;
	}

	public void setSlaRemark(String slaRemark) {
		this.slaRemark = slaRemark;
	}

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

	public String getSlaTemplateId() {
		return slaTemplateId;
	}

	public void setSlaTemplateId(String slaTemplateId) {
		this.slaTemplateId = slaTemplateId;
	}
	
	
}
