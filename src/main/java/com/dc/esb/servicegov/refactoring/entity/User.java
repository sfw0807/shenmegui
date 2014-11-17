package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SG_USER")
public class User implements Serializable{
	private static final long serialVersionUID = 6105059618965814740L;
	@Id
	@Column(name="USER_ID")
	private int id;
	@Column(name="USER_NAME")
	private String name;
	@Column(name="USER_PASSWORD")
	private String password;
	@Column(name="USER_ROLE_ID")
	private int roleId;
	@Column(name="USER_ORG_ID")
	private String orgId;
	@ManyToOne(targetEntity=Role.class)
	@JoinColumn(name="USER_ROLE_ID",referencedColumnName="ROLE_ID",insertable=false,updatable=false)
	private Role role;
	@ManyToOne(targetEntity=Organization.class)
	@JoinColumn(name="USER_ORG_ID",referencedColumnName="ORG_ID",insertable=false,updatable=false)
	private Organization org;
	@Column(name="USER_LASTDATE")
	private String lastdate;
	@Column(name="USER_REMARK")
	private String remark;
	@Column(name="USER_STATUS")
	private String status;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Organization getOrg() {
		return org;
	}
	public void setOrg(Organization org) {
		this.org = org;
	}
	public String getLastdate() {
		return lastdate;
	}
	public void setLastdate(String lastdate) {
		this.lastdate = lastdate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
