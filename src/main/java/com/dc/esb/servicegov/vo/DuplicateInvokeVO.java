package com.dc.esb.servicegov.vo;

/**
 * 
 * @author G
 * 
 */
public class DuplicateInvokeVO {

	private String serviceId;
	private String operationId;
	private String interfaceId;
	private String provideSys;
	private String provideMsgType;
	private String consumeMsgType;
	private String through;
	
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
	public String getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	public String getProvideSys() {
		return provideSys;
	}
	public void setProvideSys(String provideSys) {
		this.provideSys = provideSys;
	}
	public String getProvideMsgType() {
		return provideMsgType;
	}
	public void setProvideMsgType(String provideMsgType) {
		this.provideMsgType = provideMsgType;
	}
	public String getConsumeMsgType() {
		return consumeMsgType;
	}
	public void setConsumeMsgType(String consumeMsgType) {
		this.consumeMsgType = consumeMsgType;
	}
	public String getThrough() {
		return through;
	}
	public void setThrough(String through) {
		this.through = through;
	}
}
