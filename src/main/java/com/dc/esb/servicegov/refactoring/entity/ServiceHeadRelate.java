package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SERVICE_HEAD_RELATE")
public class ServiceHeadRelate implements Serializable{
	private static final long serialVersionUID = -1522831852522930619L;
	@Id
	@Column(name="SHEADID")
	private String sheadId;
	@Id
	@Column(name="SERVICEID")
	private String serviceId;
	public String getSheadId() {
		return sheadId;
	}
	public void setSheadId(String sheadId) {
		this.sheadId = sheadId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
}
