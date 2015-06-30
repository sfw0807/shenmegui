package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="INTERFACE_HIS")
public class InterfaceHIS {
	@Id
	@Column(name = "INTERFACE_ID")
	private String interfaceId;
	
	@Column(name = "INTERFACE_NAME")
	private String interfaceName;
	
	@Column(name = "ECODE")
	private String ecode;
	
	@Column(name = "DESCRIPTION")
	private String desc;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "INTERFACE_HEAD_ID")
	private String interfaceHeadId;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "VERSION")
	private String version;
	
	@Column(name = "OPT_USER")
	private String potUser;
	
	@Column(name = "OPT_DATE")
	private String potDate;

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInterfaceHeadId() {
		return interfaceHeadId;
	}

	public void setInterfaceHeadId(String interfaceHeadId) {
		this.interfaceHeadId = interfaceHeadId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPotUser() {
		return potUser;
	}

	public void setPotUser(String potUser) {
		this.potUser = potUser;
	}

	public String getPotDate() {
		return potDate;
	}

	public void setPotDate(String potDate) {
		this.potDate = potDate;
	}
	
	
}
