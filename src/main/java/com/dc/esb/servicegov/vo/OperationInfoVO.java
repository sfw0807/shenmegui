package com.dc.esb.servicegov.vo;

import com.dc.esb.servicegov.entity.AuditState;

public class OperationInfoVO {
	private String operationId;
	private String operationName;
	private String serviceId;
	private String serviceName;	
	private String remark;
	private String version;
	private String state;
	private String publishVersion;
	private String publishDate;
	private String action;
	private String history;
	private String invoke;
	private AuditState audit;
	public AuditState getAudit() {
		return audit;
	}
	public void setAudit(AuditState audit) {
		this.audit = audit;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPublishVersion() {
		return publishVersion;
	}
	public void setPublishVersion(String publishVersion) {
		this.publishVersion = publishVersion;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	public String getInvoke() {
		return invoke;
	}
	public void setInvoke(String invoke) {
		this.invoke = invoke;
	}	
}
