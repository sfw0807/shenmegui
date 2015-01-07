package com.dc.esb.servicegov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RELATION_VIEW_NEW")
public class RelationViewNew implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9065240411932978220L;
	@Id
	@Column(name="ECODE")
	private String ecode;
	@Column(name="INTERFACENAME")
	private String interfaceName;
	@Id
	@Column(name="SERVICEID")
	private String serviceId;
	@Column(name="SERVICENAME")
	private String serviceName;
	@Id
	@Column(name="OPERATIONID")
	private String operationId;
	@Column(name="OPERATIONNAME")
	private String operationName;
	@Column(name="SYSID")
	private String sysId;
	@Column(name="VERSINNO")
	private String versionNo;
	@Column(name="VERSIONST")
	private String versionSt;
	@Column(name="PRODUCTNO")
	private String productNo;
	@Column(name="ONLINEDATE")
	private String onLineDate;
	@Id
	@Column(name="CONSUMESYS")
	private String consumerSys;
	@Column(name="PASSEDSYS")
	private String passBySys;
	@Id
	@Column(name="PRDSYSAB")
	private String providerSysAb;
	@Column(name="PRDSYSNAME")
	private String providerSysName;
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
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
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getVersionSt() {
		return versionSt;
	}
	public void setVersionSt(String versionSt) {
		this.versionSt = versionSt;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getOnLineDate() {
		return onLineDate;
	}
	public void setOnLineDate(String onLineDate) {
		this.onLineDate = onLineDate;
	}
	public String getConsumerSys() {
		return consumerSys;
	}
	public void setConsumerSys(String consumerSys) {
		this.consumerSys = consumerSys;
	}
	public String getPassBySys() {
		return passBySys;
	}
	public void setPassBySys(String passBySys) {
		this.passBySys = passBySys;
	}
	public String getProviderSysAb() {
		return providerSysAb;
	}
	public void setProviderSysAb(String providerSysAb) {
		this.providerSysAb = providerSysAb;
	}
	public String getProviderSysName() {
		return providerSysName;
	}
	public void setProviderSysName(String providerSysName) {
		this.providerSysName = providerSysName;
	}
}
