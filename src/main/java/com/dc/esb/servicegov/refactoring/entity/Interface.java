package com.dc.esb.servicegov.refactoring.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dc.esb.servicegov.refactoring.vo.InterfaceVO;

@Entity
@Table(name = "INTERFACE")
public class Interface {

	@Id
	@Column(name = "INTERFACE_ID")
	private String interfaceId;
	@Column(name = "ECODE")
	private String ecode;
	@Column(name = "INTERFACE_NAME")
	private String interfaceName;
	@Column(name = "REMARK")
	private String remark;
	@Column(name = "VERSION")
	private String version;
	@Column(name = "state")
	private String state;
	@Column(name = "THROUGH")
	private String through;
	@Column(name = "MODIFYUSER")
	private String modifyUser;
	@Column(name = "UPDATETIME")
	private Timestamp updateTime;

	public Interface() {
		
	}
	
	public Interface(InterfaceVO vo) {
		this.ecode = vo.getEcode();
		this.interfaceId = vo.getInterfaceId();
		this.interfaceName = vo.getInterfaceName();
		this.remark = vo.getRemark();
		this.state = "";
		this.setThrough(vo.getThrough());
		this.updateTime = new Timestamp(java.lang.System.currentTimeMillis());
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getThrough() {
		return through;
	}

	public void setThrough(String through) {
		this.through = through;
	}
}
