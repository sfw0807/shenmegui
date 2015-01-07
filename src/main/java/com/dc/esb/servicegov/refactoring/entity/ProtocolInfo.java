package com.dc.esb.servicegov.refactoring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="PROTOCOLINFO")
@IdClass(ProtocolInfoPK.class)
public class ProtocolInfo {	
	
	@Id
	private String sysId;
	@Column(name="CONNECTMODE")
	private String connectMode;
	@Column(name="SYSADDR")
	private String sysAddr;
	@Column(name="APPSCENE")
	private String appScene;
	@Id
	private String msgType;
	@Column(name="TIMEOUT")
	private String timeout;
	@Id
	private String sysType;
	@Column(name="CODETYPE")
	private String codeType;
	@Column(name="MACFLAG")
	private String macFlag;
	@Column(name="CURRENTTIMES")
	private String currentTimes;
	@Column(name="AVGRESTIME")
	private String avgResTime;
	@Column(name="SUCCESSRATE")
	private String successRate;
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public String getConnectMode() {
		return connectMode;
	}
	public void setConnectMode(String connectMode) {
		this.connectMode = connectMode;
	}
	public String getSysAddr() {
		return sysAddr;
	}
	public void setSysAddr(String sysAddr) {
		this.sysAddr = sysAddr;
	}
	public String getAppScene() {
		return appScene;
	}
	public void setAppScene(String appScene) {
		this.appScene = appScene;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public String getSysType() {
		return sysType;
	}
	public void setSysType(String sysType) {
		this.sysType = sysType;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getMacFlag() {
		return macFlag;
	}
	public void setMacFlag(String macFlag) {
		this.macFlag = macFlag;
	}
	public String getCurrentTimes() {
		return currentTimes;
	}
	public void setCurrentTimes(String currentTimes) {
		this.currentTimes = currentTimes;
	}
	public String getAvgResTime() {
		return avgResTime;
	}
	public void setAvgResTime(String avgResTime) {
		this.avgResTime = avgResTime;
	}
	public String getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(String successRate) {
		this.successRate = successRate;
	}
}
