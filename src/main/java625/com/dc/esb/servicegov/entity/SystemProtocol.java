package com.dc.esb.servicegov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYSTEM_PROTOCOL")
public class SystemProtocol implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SYSTEM_ID")
	private String systemId;
	
	@Column(name = "CONNECT_MODE")
	private String connectMode;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getConnectMode() {
		return connectMode;
	}

	public void setConnectMode(String connectMode) {
		this.connectMode = connectMode;
	}
}
