package com.dc.esb.servicegov.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "SYSTEM_PROTOCOL")
//@IdClass(SystemProtocolPK.class)
public class SystemProtocol implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SYSTEM_ID")
	private String systemId;

	@Id
	@Column(name = "PROTOCOL_ID")
	private String protocolId;

	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
	@JoinColumn(name="SYSTEM_ID",referencedColumnName = "SYSTEM_ID",insertable = false,updatable = false)
	private System system;

	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
	@JoinColumn(name = "PROTOCOL_ID",referencedColumnName = "PROTOCOL_ID",insertable = false,updatable = false)
	private Protocol protocol;

	//@EmbeddedId
	//private SystemProtocolPK pk;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}


	public String getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

//	public SystemProtocolPK getPk() {
//		return pk;
//	}
//
//	public void setPk(SystemProtocolPK pk) {
//		this.pk = pk;
//	}
}
