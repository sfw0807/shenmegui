package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SVC_ASM_RELATE_VIEW")
public class SvcAsmRelateView implements Serializable{
	
	private static final long serialVersionUID = 13456789l;
	@Column(name="ID")
	private int id;
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
	@Id
	@Column(name="CSMSYSAB")
	private String csmSysAB;
	@Column(name="CSMSYSID")
	private String csmSysId;
	@Column(name="CSMSYSNAME")
	private String csmSysName;
	@Column(name="PASSBYSYSAB")
	private String passbySysAB;
	@Column(name="PASSBYSYSNAME")
	private String passbySysName;
	@Column(name="PRDSYSAB")
	private String prdSysAB;
	@Column(name="PRDSYSID")
	private String prdSysID;
	@Column(name="PRDSYSNAME")
	private String prdSysName;
	@Id
	@Column(name="CONSUME_MSG_TYPE")
	private String consumeMsgType;
	@Id
	@Column(name="PROVIDE_MSG_TYPE")
	private String provideMsgType;
	@Column(name="DIRECTION")
	private String direction;
	@Column(name="THROUGH")
	private String through;
	@Column(name="VERSIONST")
	private String state;
	@Column(name="VERSION")
	private String version;
	@Column(name="ONLINE_DATE")
	private String onlineDate;
	@Column(name="ONLINE_VERSION")
	private String onlineVersion;
	@Column(name="FIELD")
	private String field;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getCsmSysAB() {
		return csmSysAB;
	}
	public void setCsmSysAB(String csmSysAB) {
		this.csmSysAB = csmSysAB;
	}
	public String getCsmSysId() {
		return csmSysId;
	}
	public void setCsmSysId(String csmSysId) {
		this.csmSysId = csmSysId;
	}
	public String getCsmSysName() {
		return csmSysName;
	}
	public void setCsmSysName(String csmSysName) {
		this.csmSysName = csmSysName;
	}
	public String getPassbySysAB() {
		return passbySysAB;
	}
	public void setPassbySysAB(String passbySysAB) {
		this.passbySysAB = passbySysAB;
	}
	public String getPassbySysName() {
		return passbySysName;
	}
	public void setPassbySysName(String passbySysName) {
		this.passbySysName = passbySysName;
	}
	public String getPrdSysAB() {
		return prdSysAB;
	}
	public void setPrdSysAB(String prdSysAB) {
		this.prdSysAB = prdSysAB;
	}
	public String getPrdSysID() {
		return prdSysID;
	}
	public void setPrdSysID(String prdSysID) {
		this.prdSysID = prdSysID;
	}
	public String getPrdSysName() {
		return prdSysName;
	}
	public void setPrdSysName(String prdSysName) {
		this.prdSysName = prdSysName;
	}
	public String getConsumeMsgType() {
		return consumeMsgType;
	}
	public void setConsumeMsgType(String consumeMsgType) {
		this.consumeMsgType = consumeMsgType;
	}
	public String getProvideMsgType() {
		return provideMsgType;
	}
	public void setProvideMsgType(String provideMsgType) {
		this.provideMsgType = provideMsgType;
	}
	public String getThrough() {
		return through;
	}
	public void setThrough(String through) {
		this.through = through;
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
	public String getOnlineDate() {
		return onlineDate;
	}
	public void setOnlineDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}
	public String getOnlineVersion() {
		return onlineVersion;
	}
	public void setOnlineVersion(String onlineVersion) {
		this.onlineVersion = onlineVersion;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}
