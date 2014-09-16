package com.dc.esb.servicegov.vo;

import com.dc.esb.servicegov.entity.ServiceInvokeRelation;

public class RelationVo {

	private String recordId;
	private String providerSystemId;
	private String consumerSystemAb;
	private String serviceId;
	private String operationId;
	private String type;
	private String interfaceId;
	private String functionType;
	private String passbySys;

	public RelationVo(ServiceInvokeRelation sr) {
		this.recordId = sr.getRecordId();
		this.providerSystemId = sr.getProviderSystemId();
		this.consumerSystemAb = sr.getConsumerSystemAb();
		this.serviceId = sr.getServiceId();
		this.operationId = sr.getOperationId();
		this.type = sr.getType();
		this.interfaceId = sr.getInterfaceId();
		this.functionType = sr.getFunctionType();
		this.passbySys = sr.getPassbySys();
	}
	
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getProviderSystemId() {
		return providerSystemId;
	}

	public void setProviderSystemId(String providerSystemId) {
		this.providerSystemId = providerSystemId;
	}

	public String getConsumerSystemAb() {
		return consumerSystemAb;
	}

	public void setConsumerSystemAb(String consumerSystemAb) {
		this.consumerSystemAb = consumerSystemAb;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getFunctionType() {
		return functionType;
	}

	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	public String getPassbySys() {
		return passbySys;
	}

	public void setPassbySys(String passbySys) {
		this.passbySys = passbySys;
	}
}