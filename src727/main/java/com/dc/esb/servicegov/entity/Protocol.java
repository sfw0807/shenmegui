package com.dc.esb.servicegov.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="PROTOCOL")
public class Protocol {

	@Id
	@Column(name = "PROTOCOL_ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
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

//	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//	@JoinColumn(name="PROTOCOL_ID",referencedColumnName = "PROTOCOL_ID")
//	private SystemProtocol systemProtocol;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "MSG_TEMPLATE_ID",referencedColumnName = "TEMPLATE_ID",insertable = false,updatable = false)
	private MsgTemplate msgTemplate;

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

	public MsgTemplate getMsgTemplate() {
		return msgTemplate;
	}

	public void setMsgTemplate(MsgTemplate msgTemplate) {
		this.msgTemplate = msgTemplate;
	}
}
