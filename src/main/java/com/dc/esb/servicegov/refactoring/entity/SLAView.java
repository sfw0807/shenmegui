package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SLA_VIEW")
public class SLAView implements Serializable{

	private static final long serialVersionUID = -3834591624643114164L;

	@Id
	@Column(name="SERVICE_ID")
	private String serviceId;
	
	@Column(name="SERVICE_NAME")
	private String serviceName;
	
	@Id
	@Column(name="OPERATION_ID")
	private String operationId;
	
	@Column(name="OPERATION_NAME")
	private String operationName;
	
	@Id
	@Column(name="ECODE")
	private String interfaceId;
	
	@Column(name="INTERFACE_NAME")
	private String interfaceName;
	
	@Column(name="SYS_NAME")
	private String sysName;
	
	@Column(name="SYS_AB")
	private String sysAB;
	
	@Column(name="SLA_REMARK")
	private String slaRemark;
	
	@Column(name="CURRENTCOUNT")
	private String currentCount;
	
	@Column(name="SUCCESSRATE")
	private String successRate;
	
	@Column(name="AVERAGETIME")
	private String averageTime;
	
	@Column(name="TIMEOUT")
	private String timeOut;

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

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getSlaRemark() {
		return slaRemark;
	}

	public void setSlaRemark(String slaRemark) {
		this.slaRemark = slaRemark;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysAB() {
		return sysAB;
	}

	public void setSysAB(String sysAB) {
		this.sysAB = sysAB;
	}
	
	
}
