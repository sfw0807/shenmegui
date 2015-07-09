package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SERVICE_INVOKE")
public class ServiceInvoke {
	@Id
	@Column(name = "INVOKE_ID")
	@GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")
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

	@Column(name = "DESCRIPTION")
	private String desc;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "PROTOCOL_ID")
	private String protocolId;

	@OneToOne
	@JoinColumn(name = "INTERFACE_ID", insertable = false, updatable = false)
	private Interface inter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SYSTEM_ID", insertable = false, updatable = false)
	private System system;

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

	@OrderBy("interfaceName")
	public Interface getInter() {
		return inter;
	}

	public void setInter(Interface inter) {
		this.inter = inter;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}
}
