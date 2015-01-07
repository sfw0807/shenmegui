package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SG_MM_MSM_SVC_OLA")
public class ServiceOLA {
	
	@Id
	@Column(name="ACTIONID")
	private String actionId;
	@Column(name="RELATIONID")
	private String relationId;
	@Column(name="PROTOCOLID")
	private String protocolId;
	@Column(name="PROTOCOLVALUE")
	private String protocolValue;
	@Column(name="DESCRIPTION")
	private String description;
	@Column(name="VERSIONNO")
	private String versionNo;
	@Column(name="UPDATEDATE")
	private String updateDate;
	@Column(name="VERSIONST")
	private String versionSt;
	@Column(name="PRODUCTNO")
	private String productNo;
	
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public String getProtocolId() {
		return protocolId;
	}
	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}
	public String getProtocolValue() {
		return protocolValue;
	}
	public void setProtocolValue(String protocolValue) {
		this.protocolValue = protocolValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getVersionSt() {
		return versionSt;
	}
	public void setVersionSt(String versionSt) {
		this.versionSt = versionSt;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
}
