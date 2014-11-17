package com.dc.esb.servicegov.refactoring.vo;

import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;

public class RelationVO {

	private String id;
	private String ecode;
	private String serviceId;
	private String operationId;
	private String consumerSys;
	private String providerSys;
	private String passBySys;
	
	RelationVO() {
		super();
	}
	
	public RelationVO(InvokeInfo r) {
		this.ecode = r.getEcode();
		this.serviceId = r.getServiceId();
		this.operationId = r.getOperationId();
		this.consumerSys = r.getConsumeSysId();
		this.providerSys = r.getProvideSysId();
		this.passBySys = r.getPassbySysId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getConsumerSys() {
		return consumerSys;
	}

	public void setConsumerSys(String consumerSys) {
		this.consumerSys = consumerSys;
	}

	public String getProviderSys() {
		return providerSys;
	}

	public void setProviderSys(String providerSys) {
		this.providerSys = providerSys;
	}

	public String getPassBySys() {
		return passBySys;
	}

	public void setPassBySys(String passBySys) {
		this.passBySys = passBySys;
	}
	
	
}
