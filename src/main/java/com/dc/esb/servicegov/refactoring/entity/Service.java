package com.dc.esb.servicegov.refactoring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//SERVICE_ID    	VARCHAR(255) NOT NULL PRIMARY KEY,
//SERVICE_NAME  	VARCHAR(100),
//SERVICE_REMARK	VARCHAR(500),
//CATEGORY_ID   	CHAR(5),
//VERSION       	VARCHAR(25),
//STATE         	VARCHAR(40) 

@Entity
@Table(name="SERVICE")
public class Service {
	@Id
	@Column(name="SERVICE_ID")
	private String serviceId;
	@Column(name="SERVICE_NAME")
	private String serviceName;
	@Column(name="SERVICE_REMARK")
	private String serviceRemark;
	@Column(name="CATEGORY_ID")
	private String categoryId;
	@Column(name="VERSION")
	private String version;
	@Column(name="STATE")
	private String state;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceRemark() {
		return serviceRemark;
	}
	public void setServiceRemark(String serviceRemark) {
		this.serviceRemark = serviceRemark;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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
