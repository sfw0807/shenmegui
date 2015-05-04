package com.dc.esb.servicegov.vo;


import com.dc.esb.servicegov.entity.Function;
import com.dc.esb.servicegov.entity.LogInfo;
import com.dc.esb.servicegov.entity.User;

public class LogInfoVO {

	private String id;
	private String userId;
	private String functionId;
	private User user;
	private Function function;
	private String time;
	private String detail;
	private String type;
	
	public LogInfoVO(){
		
	}
	
	public LogInfoVO(LogInfo logInfo){
		this.id = logInfo.getId();
		this.user = logInfo.getUser();
		this.userId = logInfo.getUserId();
		this.functionId = logInfo.getFunctionId();
		this.function = logInfo.getFunction();
//		this.time = Utils.formatTimestamp(logInfo.getTime());
		this.time = logInfo.getTime().toString().substring(0,logInfo.getTime().toString().indexOf("."));
		this.detail = logInfo.getDetail();
		this.type = logInfo.getType();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
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
}
