package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PUBLISH_INFO")
public class PublishInfo implements Serializable{
	
	private static final long serialVersionUID = 666666l;
	
	@Id
	@Column(name="ID")
	private Integer id;
	@Column(name="IR_ID")
	private Integer iRid;
	@Column(name="ONLINE_DATE")
	private String onlineDate;
	@Column(name="SERVICE_VERSION")
	private String serviceVersion;
	@Column(name="OPERATION_VERSION")
	private String operationVersion;
	@Column(name="INTERFACE_VERSION")
	private String inerfaceVersion;
	@Column(name="FIELD")
	private String field;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIRid() {
		return iRid;
	}
	public void setIRid(Integer rid) {
		iRid = rid;
	}
	public String getOnlineDate() {
		return onlineDate;
	}
	public void setOnlineDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}
	public String getServiceVersion() {
		return serviceVersion;
	}
	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}
	public String getOperationVersion() {
		return operationVersion;
	}
	public void setOperationVersion(String operationVersion) {
		this.operationVersion = operationVersion;
	}
	public String getInerfaceVersion() {
		return inerfaceVersion;
	}
	public void setInerfaceVersion(String inerfaceVersion) {
		this.inerfaceVersion = inerfaceVersion;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
}
