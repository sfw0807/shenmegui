package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//SERVICE_ID    	VARCHAR(255) NOT NULL PRIMARY KEY,
//SERVICE_NAME  	VARCHAR(100),
//SERVICE_REMARK	VARCHAR(500),
//CATEGORY_ID   	CHAR(5),
//VERSION       	VARCHAR(25),
//STATE         	VARCHAR(40) 

@Entity
@Table(name="SERVICE")
public class Service implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	@Column(name="MODIFYUSER")
	private String modifyUser;
	@Column(name="UPDATETIME")
	private String updateTime;
	@ManyToOne(targetEntity=ServiceCategory.class)
	@JoinColumn(name="CATEGORY_ID",referencedColumnName="CATEGORY_ID",insertable=false,updatable=false)
	private ServiceCategory serviceCategory;
	
	public ServiceCategory getServiceCategory() {
		return serviceCategory;
	}
	public void setServiceCategory(ServiceCategory serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
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
