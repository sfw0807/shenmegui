package com.dc.esb.servicegov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProtocolInfoPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4475464722226716383L;
	@Column(name="SYSID")
	private String sysId;
	@Column(name="MESSAGETYPE")
	private String msgType;
	@Column(name="SYSTYPE")
	private String sysType;
	
	
	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	@Override
    public boolean equals(Object obj) {
        if(!(obj instanceof  ProtocolInfoPK)){
            return false;
        }
        ProtocolInfoPK another = (ProtocolInfoPK) obj;
        return ((null == this.sysId) ?(null == another.getSysId()):(this.sysId.equals(another.getSysId())))&&
               ((null == this.msgType) ?(null == another.getMsgType()):(this.msgType.equals(another.getMsgType())))&&
                ((null == this.sysType) ?(null == another.getSysType()):(this.sysType.equals(another.getSysType())));
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        if(null != sysId){
            hashCode ^= sysId.hashCode();
        }
        if(null != msgType){
            hashCode ^= msgType.hashCode();
        }
        if(null != sysType){
            hashCode ^= sysType.hashCode();
        }
        return hashCode;
    }

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
}
