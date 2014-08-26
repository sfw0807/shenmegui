package com.dc.esb.servicegov.refactoring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SYSTEM")
public class System {	
	
	@Id
	@Column(name="SYS_ID")
	private String systemId;
	@Column(name="SYS_AB")
	private String systemAb;
	@Column(name="SYS_NAME")
	private String systemName;
	@Column(name="REMARK")
	private String remark;
	
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
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
