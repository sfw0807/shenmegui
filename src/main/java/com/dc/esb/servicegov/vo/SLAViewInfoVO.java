package com.dc.esb.servicegov.vo;

public class SLAViewInfoVO {
	private String serviceInfo;
	private String operationInfo;
	private String interfaceInfo;
	private String provideSysInfo;
	private String currentCount;
	private String successRate;
	private String averageTime;
	private String timeOut;
	private String slaRemark;
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
	public String getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(String currentCount) {
		this.currentCount = currentCount;
	}
	public String getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(String successRate) {
		this.successRate = successRate;
	}
	public String getAverageTime() {
		return averageTime;
	}
	public void setAverageTime(String averageTime) {
		this.averageTime = averageTime;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	public String getSlaRemark() {
		return slaRemark;
	}
	public void setSlaRemark(String slaRemark) {
		this.slaRemark = slaRemark;
	}
	
}
