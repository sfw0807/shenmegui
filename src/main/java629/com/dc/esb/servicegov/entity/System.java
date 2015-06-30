package com.dc.esb.servicegov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYSTEM")
public class System implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id  
	@Column(name = "SYSTEM_ID")
	private String systemId;
	
	@Column(name = "SYSTEM_AB")
	private String systemAb;
	
	@Column(name = "SYSTEM_CHINESE_NAME")
	private String systemChineseName;
	
	@Column(name = "FEATURE_DESC")
	private String featureDesc;
	
	@Column(name = "WORK_RANGE")
	private String workRange;
	
	@Column(name = "PRINCIPAL1")
	private String principal1;
	
	@Column(name = "PRINCIPAL2")
	private String principal2;
	
	@Column(name = "OPT_USER")
	private String optUser;
	
	@Column(name = "OPT_DATE")
	private String optDate;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSystemAb() {
		return systemAb;
	}

	public void setSystemAb(String systemAb) {
		this.systemAb = systemAb;
	}

	public String getSystemChineseName() {
		return systemChineseName;
	}

	public void setSystemChineseName(String systemChineseName) {
		this.systemChineseName = systemChineseName;
	}

	public String getFeatureDesc() {
		return featureDesc;
	}

	public void setFeatureDesc(String featureDesc) {
		this.featureDesc = featureDesc;
	}

	public String getWorkRange() {
		return workRange;
	}

	public void setWorkRange(String workRange) {
		this.workRange = workRange;
	}

	public String getPrincipal1() {
		return principal1;
	}

	public void setPrincipal1(String principal1) {
		this.principal1 = principal1;
	}

	public String getPrincipal2() {
		return principal2;
	}

	public void setPrincipal2(String principal2) {
		this.principal2 = principal2;
	}

	public String getOptUser() {
		return optUser;
	}

	public void setOptUser(String optUser) {
		this.optUser = optUser;
	}

	public String getOptDate() {
		return optDate;
	}

	public void setOptDate(String optDate) {
		this.optDate = optDate;
	}
}
