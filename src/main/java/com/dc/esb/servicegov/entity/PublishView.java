package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "PUBLISH_VIEW")
public class PublishView {

	@Id
	@Column(name="ID")
	private Integer id;
	@Column(name="ECODE")
	private String interfaceId;
	@Column(name="SERVICE_ID")
	private String serviceId;
	@Column(name="OPERATION_ID")
	private String operationId;
	@Column(name="PROVIDE_SYS_ID")
	private String prdSysId;
	@Column(name="CONSUME_SYS_ID")
	private String csmSysId;
	@Column(name="ONLINE_DATE")
	private String onlineDate;
	@Column(name="VERSIONST")
	private String versionSt;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
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
	public String getPrdSysId() {
		return prdSysId;
	}
	public void setPrdSysId(String prdSysId) {
		this.prdSysId = prdSysId;
	}
	public String getCsmSysId() {
		return csmSysId;
	}
	public void setCsmSysId(String csmSysId) {
		this.csmSysId = csmSysId;
	}
	public String getOnlineDate() {
		return onlineDate;
	}
	public void setOnlineDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}
	public String getVersionSt() {
		return versionSt;
	}
	public void setVersionSt(String versionSt) {
		this.versionSt = versionSt;
	}

}
