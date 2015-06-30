package com.dc.esb.servicegov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROCESSINSTANCEINFO")
public class ProcessInstanceInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "INSTANCEID")
	private String instanceId;
	
	@Column(name = "LASTMODIFICATIONDATE")
	private String lastModificationDate;
	
	@Column(name = "LASTREADDATE")
	private String lastReadDate;
	
	@Column(name = "PROCESSID")
	private String processId;
	
	@Column(name = "PROCESSINSTANCEBYTEARRAY")
	private String processInstanceByteArray;
	
	@Column(name = "STARTDATE")
	private String startDate;
	
	@Column(name = "OPTLOCK")
	private String optLock;

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(String lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public String getLastReadDate() {
		return lastReadDate;
	}

	public void setLastReadDate(String lastReadDate) {
		this.lastReadDate = lastReadDate;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessInstanceByteArray() {
		return processInstanceByteArray;
	}

	public void setProcessInstanceByteArray(String processInstanceByteArray) {
		this.processInstanceByteArray = processInstanceByteArray;
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
