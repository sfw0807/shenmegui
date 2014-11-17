package com.dc.esb.servicegov.refactoring.vo;

import java.sql.Timestamp;

public class InvokeInfoVo {
	private String serviceInfo;
	private String operationInfo;
	private String interfaceInfo;
	private String provideSysInfo;
	private String consumeMsgType;
	private String provideMsgType;
	private String modifyUser;
	private Timestamp updateTime;
	private String funcType;
	private String svcHeader;
	private String through;
	private String state;
	private String onlineDate;
	private String onlineVersion;

	public String getServiceInfo() {
		return serviceInfo;
	}
	public void setServiceInfo(String serviceInfo) {
		this.serviceInfo = serviceInfo;
	}
	public String getOperationInfo() {
		return operationInfo;
	}
	public void setOperationInfo(String operationInfo) {
		this.operationInfo = operationInfo;
	}
	public String getInterfaceInfo() {
		return interfaceInfo;
	}
	public void setInterfaceInfo(String interfaceInfo) {
		this.interfaceInfo = interfaceInfo;
	}
	public String getProvideSysInfo() {
		return provideSysInfo;
	}
	public void setProvideSysInfo(String provideSysInfo) {
		this.provideSysInfo = provideSysInfo;
	}
	public String getConsumeMsgType() {
		return consumeMsgType;
	}
	public void setConsumeMsgType(String consumeMsgType) {
		this.consumeMsgType = consumeMsgType;
	}
	public String getProvideMsgType() {
		return provideMsgType;
	}
	public void setProvideMsgType(String provideMsgType) {
		this.provideMsgType = provideMsgType;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getFuncType() {
		return funcType;
	}
	public void setFuncType(String funcType) {
		this.funcType = funcType;
	}
	public String getSvcHeader() {
		return svcHeader;
	}
	public void setSvcHeader(String svcHeader) {
		this.svcHeader = svcHeader;
	}
	public String getThrough() {
		return through;
	}
	public void setThrough(String through) {
		this.through = through;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOnlineDate() {
		return onlineDate;
	}
	public void setOnlineDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}
	public String getOnlineVersion() {
		return onlineVersion;
	}
	public void setOnlineVersion(String onlineVersion) {
		this.onlineVersion = onlineVersion;
	}
}
