package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SERVICE_INVOKE")
public class ServiceInvoke {
	@Id
    @Column(name = "INVOKE_ID")
	private String invokeId;
	
	 @Column(name = "SYSTEM_ID")
	 private String systemId;
	 
	 @Column(name = "Is_Standard")
	 private String isStandard;
	 
	 @Column(name = "SERVICE_ID")
	 private String serviceId;
	 
	 @Column(name = "OPERATION_ID")
	 private String operationId;
	
	@Column(name = "INTERFACE_ID")
	private String interfaceId;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "DESC")
	private String desc;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "PROTOCOL_ID")
	private String protocolId;

	public String getInvokeId() {
		return invokeId;
	}

	public void setInvokeId(String invokeId) {
		this.invokeId = invokeId;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getIsStandard() {
		return isStandard;
	}

	public void setIsStandard(String isStandard) {
		this.isStandard = isStandard;
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

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}
	
	
}
