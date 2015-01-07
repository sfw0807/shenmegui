package com.dc.esb.servicegov.refactoring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRANS_STATE")
public class ServiceState {

	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="VERSIONST")
	private String devState;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDevState() {
		return devState;
	}

	public void setDevState(String devState) {
		this.devState = devState;
	}

}
