package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SG_ROLE")
public class Role implements Serializable{
	private static final long serialVersionUID = 5050607042268342728L;
	@Id
	@Column(name="ROLE_ID")
	private int id;
	@Column(name="ROLE_NAME")
	private String name;
	@Column(name="ROLE_REMARK")
	private String remark;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
