package com.dc.esb.servicegov.refactoring.vo;



public class SvcAsmRelateInfoVO {
	private String serviceInfo;
	private String operationInfo;
	private String interfaceInfo;
	private String provideSysInfo;
	private String passbySysInfo;
	private String consumeSysInfo;
	private String consumeMsgType;
	private String provideMsgType;
	private String direction;
	private String through;
	private String state;
	private String modifyTimes;
	private String onlineDate;
	private String onlineVersion;
	private String field;
	
	@Override
    public boolean equals(Object obj) {
        if(!(obj instanceof  SvcAsmRelateInfoVO)){
            return false;
        }
        SvcAsmRelateInfoVO another = (SvcAsmRelateInfoVO) obj;
        return ((null == this.serviceInfo) ?(null == another.getServiceInfo()):(this.serviceInfo.equals(another.getServiceInfo())))&&
               ((null == this.operationInfo) ?(null == another.getOperationInfo()):(this.operationInfo.equals(another.getOperationInfo())))&&
                ((null == this.interfaceInfo) ?(null == another.getInterfaceInfo()):(this.interfaceInfo.equals(another.getInterfaceInfo())))&&
                ((null == this.provideMsgType) ?(null == another.getProvideMsgType()):(this.provideMsgType.equals(another.getProvideMsgType())))&&
                ((null == this.consumeMsgType) ?(null == another.getConsumeMsgType()):(this.consumeMsgType.equals(another.getConsumeMsgType())));
    }
	@Override
    public int hashCode() {
        int hashCode = 0;
        if(null != serviceInfo){
            hashCode ^= serviceInfo.hashCode();
        }
        if(null != operationInfo){
            hashCode ^= operationInfo.hashCode();
        }
        if(null != interfaceInfo){
            hashCode ^= interfaceInfo.hashCode();
        }
        if(null != provideMsgType){
            hashCode ^= provideMsgType.hashCode();
        }
        if(null != consumeMsgType){
            hashCode ^= consumeMsgType.hashCode();
        }
        return hashCode;
    }
	

	public String getServiceInfo() {
		return this.serviceInfo;
	}

	public void setServiceInfo(String serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public String getOperationInfo() {
		return this.operationInfo;
	}

	public void setOperationInfo(String operationInfo) {
		this.operationInfo = operationInfo;
	}

	public String getInterfaceInfo() {
		return this.interfaceInfo;
	}

	public void setInterfaceInfo(String interfaceInfo) {
		this.interfaceInfo = interfaceInfo;
	}

	public String getProvideSysInfo() {
		return this.provideSysInfo;
	}

	public void setProvideSysInfo(String provideSysInfo) {
		this.provideSysInfo = provideSysInfo;
	}

	public String getPassbySysInfo() {
		return this.passbySysInfo;
	}

	public void setPassbySysInfo(String passbySysInfo) {
		this.passbySysInfo = passbySysInfo;
	}

	public String getConsumeSysInfo() {
		return this.consumeSysInfo;
	}

	public void setConsumeSysInfo(String consumeSysInfo) {
		this.consumeSysInfo = consumeSysInfo;
	}

	public String getConsumeMsgType() {
		return this.consumeMsgType;
	}

	public void setConsumeMsgType(String consumeMsgType) {
		this.consumeMsgType = consumeMsgType;
	}

	public String getProvideMsgType() {
		return this.provideMsgType;
	}

	public void setProvideMsgType(String provideMsgType) {
		this.provideMsgType = provideMsgType;
	}

	public String getThrough() {
		return this.through;
	}

	public void setThrough(String through) {
		this.through = through;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getModifyTimes() {
		return this.modifyTimes;
	}

	public void setModifyTimes(String modifyTimes) {
		this.modifyTimes = modifyTimes;
	}

	public String getOnlineDate() {
		return this.onlineDate;
	}

	public void setOnlineDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}

	public String getOnlineVersion() {
		return this.onlineVersion;
	}

	public void setOnlineVersion(String onlineVersion) {
		this.onlineVersion = onlineVersion;
	}

	public String getField() {
		return this.field;
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