package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PROTOCOL")
public class Protocol {
	@Id
	@Column(name = "PROTOCOL_ID")
	private String protocolId;
	
	@Column(name = "PROTOCOL_NAME")
	private String protocolName;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "ENCODING")
	private String encoding;
		
	@Column(name = "MSG_TYPE")
	private String msgType;
	
	@Column(name = "TIMEOUT")
	private String timeout;
	
	@Column(name = "SUCC_CODE")
	private String succCode;
	
	@Column(name = "ERROR_CODE")
	private String errorCode;
	
	@Column(name = "MSG_TEMPLATE_ID")
	private String msgTemplateId;
	
	@Column(name = "GENERATOR_ID")
	private String generatorId;

	public String getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}

	public String getProtocolName() {
		return protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
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

	public String getSuccCode() {
		return succCode;
	}

	public void setSuccCode(String succCode) {
		this.succCode = succCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsgTemplateId() {
		return msgTemplateId;
	}

	public void setMsgTemplateId(String msgTemplateId) {
		this.msgTemplateId = msgTemplateId;
	}

	public String getGeneratorId() {
		return generatorId;
	}

	public void setGeneratorId(String generatorId) {
		this.generatorId = generatorId;
	}
	
	
}
