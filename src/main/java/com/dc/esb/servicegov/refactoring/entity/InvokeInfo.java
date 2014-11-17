package com.dc.esb.servicegov.refactoring.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.dc.esb.servicegov.refactoring.vo.InterfaceVO;

@Entity
@Table(name = "INVOKE_RELATION")
@IdClass(InvokeInfoPK.class)
public class InvokeInfo {
	
	@Column(name = "ID")
	private int id;
	@Id
	private String serviceId;
	@Id
	private String operationId;
	@Id
	private String ecode;
	@Id
	private String provideSysId;
	@Id
	private String consumeSysId;
	@Column(name = "PASSBY_SYS_ID")
	private String passbySysId;
	@Column(name = "CONSUME_MSG_TYPE")
	private String consumeMsgType;
	@Column(name = "PROVIDE_MSG_TYPE")
	private String provideMsgType;
	@Column(name="MODIFYUSER")
	private String modifyUser;
	@Column(name="UPDATETIME")
	private Timestamp updateTime;
	@Column(name="FUNCTYPE")
	private String funcType;
	@Column(name="SVCHEADER")
	private String svcHeader;
	@Column(name="THROUGH")
	private String through;
	@Column(name="DIRECTION")
	private String direction;
	
	public InvokeInfo(){
		
	}
	
	public InvokeInfo(InterfaceVO vo) {
		this.serviceId = vo.getServiceId();
		this.operationId = vo.getOperationId();
		this.ecode = vo.getEcode();
		this.provideSysId = vo.getProviderSysId();
		this.consumeSysId = vo.getConsumerSysId();
		this.consumeMsgType = vo.getConsumerMsgType();
		this.provideMsgType = vo.getProviderMsgType();
		this.passbySysId = vo.getPassBySys();
		this.updateTime = new Timestamp(java.lang.System.currentTimeMillis());
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

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public String getProvideSysId() {
		return provideSysId;
	}

	public void setProvideSysId(String provideSysId) {
		this.provideSysId = provideSysId;
	}

	public String getConsumeSysId() {
		return consumeSysId;
	}

	public void setConsumeSysId(String consumeSysId) {
		this.consumeSysId = consumeSysId;
	}

	public String getPassbySysId() {
		return passbySysId;
	}

	public void setPassbySysId(String passbySysId) {
		this.passbySysId = passbySysId;
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

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}


	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFuncType() {
		return funcType;
	}

	public void setFuncType(String funcType) {
		this.funcType = funcType;
	}

	public String getSvcHeader() {
		return svcHeader;
	}

	public void setSvcHeader(String svcHeader) {
		this.svcHeader = svcHeader;
	}

	public String getThrough() {
		return through;
	}

	public void setThrough(String through) {
		this.through = through;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
