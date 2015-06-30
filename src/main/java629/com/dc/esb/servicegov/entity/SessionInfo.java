package com.dc.esb.servicegov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SESSIONINFO")
public class SessionInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "LASTMODIFICATIONDATE")
	private String lastModificationDate;
	
	@Column(name = "RULESBYTEARRAY")
	private String rulesByteArray;
	
	@Column(name = "STARTDATE")
	private String startDate;
	
	@Column(name = "OPTLOCK")
	private String optLock;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(String lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public String getRulesByteArray() {
		return rulesByteArray;
	}

	public void setRulesByteArray(String rulesByteArray) {
		this.rulesByteArray = rulesByteArray;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getOptLock() {
		return optLock;
	}

	public void setOptLock(String optLock) {
		this.optLock = optLock;
	}
	
	
}
