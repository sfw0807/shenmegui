package com.dc.esb.servicegov.vo;

import com.dc.esb.servicegov.entity.RelationViewNew;
import com.dc.esb.servicegov.refactoring.entity.SvcAsmRelateView;

public class RelationNewVO {

	private String ecode;
	private String interfaceName;
	private String serviceId;
	private String serviceName;
	private String operationId;
	private String operationName;
	private String sysId;
	private String versionNo;
	private String versionSt;
	private String productNo;
	private String onLineDate;
	private String consumerSys;
	private String sourceSys;
	public String getSourceSys() {
		return sourceSys;
	}

	public void setSourceSys(String sourceSys) {
		this.sourceSys = sourceSys;
	}
	private String passBySys;
	private String providerSysAb;
	private String providerSysName;
	
	RelationNewVO() {
		super();
	}
	
	public RelationNewVO(RelationViewNew r) {
		this.ecode = r.getEcode();
		this.interfaceName = r.getInterfaceName();
		this.serviceId = r.getServiceId();
		this.serviceName = r.getServiceName();
		this.operationId = r.getOperationId();
		this.operationName = r.getOperationName();
		this.sysId = r.getSysId();
		this.versionNo = r.getVersionNo();
		this.versionSt = r.getVersionSt();
		this.productNo = r.getProductNo();
		this.onLineDate = r.getOnLineDate();
		this.sourceSys = r.getConsumerSys();
		// 经由系统为空 调用方为原系统 原系统置为空 
		// 否则 调用方为经由系统
		if (r.getPassBySys() == null || "".equals(r.getPassBySys())) {
			this.consumerSys = r.getConsumerSys();
			this.sourceSys = "";
		} else {
			this.consumerSys = r.getPassBySys();
		}
		this.passBySys = r.getPassBySys();
		this.providerSysAb = r.getProviderSysAb();
		this.providerSysName = r.getProviderSysName();
	}
	
	public RelationNewVO(SvcAsmRelateView r) {
		this.ecode = r.getInterfaceId();
		this.interfaceName = r.getInterfaceName();
		this.serviceId = r.getServiceId();
		this.serviceName = r.getServiceName();
		this.operationId = r.getOperationId();
		this.operationName = r.getOperationName();
		this.sysId = r.getPrdSysID();
		this.versionSt = r.getState();
		this.versionNo = r.getVersion();
		this.productNo = r.getOnlineVersion();
		this.onLineDate = r.getOnlineDate();
		this.sourceSys = r.getCsmSysAB();
		// 经由系统为空 调用方为原系统 原系统置为空 
		// 否则 调用方为经由系统
		if (r.getPassbySysAB() == null || "".equals(r.getPassbySysAB())) {
			this.consumerSys = r.getCsmSysAB();
			this.sourceSys = "";
		} else {
			this.consumerSys = r.getPassbySysAB();
		}
		this.passBySys = r.getPassbySysAB();
		this.providerSysAb = r.getPrdSysAB();
		this.providerSysName = r.getPrdSysName();
	}
	
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
