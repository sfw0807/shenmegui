package com.dc.esb.servicegov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WORKITEMINFO")
public class WorkItemInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "workItemId")
	private String workItemId;
	
	@Column(name = "creationDate")
	private String creationDate;
	
	@Column(name = "name")
	private String name ;
	
	@Column(name = "processInstanceId")
	private String processInstanceId;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "optLock")
	private String optLock;
	
	@Column(name = "workItemByteArray")
	private String workItemByteArray;
	
	@Column(name = "instanceId")
	private String instanceId;

	public String getWorkItemId() {
		return workItemId;
	}

	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOptLock() {
		return optLock;
	}

	public void setOptLock(String optLock) {
		this.optLock = optLock;
	}

	public String getWorkItemByteArray() {
		return workItemByteArray;
	}

	public void setWorkItemByteArray(String workItemByteArray) {
		this.workItemByteArray = workItemByteArray;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	
}
