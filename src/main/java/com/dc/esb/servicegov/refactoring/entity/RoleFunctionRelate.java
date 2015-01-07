package com.dc.esb.servicegov.refactoring.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SG_ROLE_FUNCTION_RELATE")
public class RoleFunctionRelate implements Serializable{
	private static final long serialVersionUID = 6026646093616593088L;
	@Id
	@Column(name="ROLE_ID")
	private int roleId;
	@ManyToOne(targetEntity=Role.class)
	@JoinColumn(name="ROLE_ID" ,referencedColumnName="ROLE_ID",insertable=false,updatable=false)
	private Role role;
	@Id
	@Column(name="FUNCTION_ID")
	private int functionId;
	@ManyToOne(targetEntity=Function.class)
	@JoinColumn(name="FUNCTION_ID" ,referencedColumnName="FUNCTION_ID",insertable=false,updatable=false)
	private Function function;
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Function getFunction() {
		return function;
	}
	public void setFunction(Function function) {
		this.function = function;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getFunctionId() {
		return functionId;
	}
	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}
}
