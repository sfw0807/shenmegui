package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SG_LOG")
public class LogInfo implements Serializable{
	private static final long serialVersionUID = -1207333465639890164L;
	@Id
	@Column(name="LOG_ID")
	private String id;
	@Column(name="LOG_USER_ID")
	private String userId;
	@Column(name="LOG_FUNCTION_ID")
	private String functionId;
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="LOG_USER_ID", referencedColumnName="USER_ID",insertable=false,updatable=false)
	private User user;
	@ManyToOne(targetEntity=Function.class)
	@JoinColumn(name="LOG_FUNCTION_ID", referencedColumnName="FUNCTION_ID",insertable=false,updatable=false)
	private Function function;
	@Column(name="LOG_TIME")
	private Timestamp time;
	@Column(name="LOG_DETAIL")
	private String detail;
	@Column(name="LOG_TYPE")
	private String type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Function getFunction() {
		return function;
	}
	public void setFunction(Function function) {
		this.function = function;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFunctionId() {
		return functionId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
}
