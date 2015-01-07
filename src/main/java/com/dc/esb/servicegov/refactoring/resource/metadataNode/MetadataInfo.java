package com.dc.esb.servicegov.refactoring.resource.metadataNode;

import java.util.Properties;


public class MetadataInfo implements IMetadataInfo {

	private String channelID, clientType, groupID, interfaceID, interfaceName,
			superInterfaceID, serviceID, serviceName, superServiceID, sdoType,
			version, tranCode, serviceScene, switchField, switchExp, remark,
			resourceid, providerSysId,extendsname,interfacetype,messageType;
	public String getProviderSysId() {
		return providerSysId;
	}

	public void setProviderSysId(String providerSysId) {
		this.providerSysId = providerSysId;
	}

	public String getResourceid() {
		return resourceid;
	}

	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private Properties properties = null;

	public String getChannelId() {
		return this.channelID;
	}

	public String getClientType() {
		return this.clientType;
	}

	public Properties getExtProperties() {
		return this.properties;
	}

	public String getGroupId() {
		return this.groupID;
	}

	public String getInterfaceId() {
		return this.interfaceID;
	}

	public String getInterfaceName() {
		return this.interfaceName;
	}

	public String getSdoType() {
		return this.sdoType;
	}

	public String getServiceId() {
		return this.serviceID;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public String getServiceSceneId() {
		return this.serviceScene;
	}

	public String getSuperInterfaceId() {
		return this.superInterfaceID;
	}

	public String getSuperServiceId() {
		return this.superServiceID;
	}

	public String getSwitchExp() {
		return this.switchExp;
	}

	public String getSwitchField() {
		return this.switchField;
	}

	public String getTranCode() {
		return this.tranCode;
	}

	public String getVersion() {
		return this.version;
	}

	public void setChannelId(String channelId) {
		this.channelID = channelId;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public void setExtProperties(Properties extProperties) {
		this.properties = extProperties;
	}

	public void setGroupId(String groupId) {
		this.groupID = groupId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceID = interfaceId;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public void setSdoType(String sdoType) {
		this.sdoType = sdoType;
	}

	public void setServiceId(String serviceId) {
		this.serviceID = serviceId;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public void setServiceSceneId(String serviceSceneId) {
		this.serviceScene = serviceSceneId;
	}

	public void setSuperInterfaceId(String superInterfaceId) {
		this.superInterfaceID = superInterfaceId;
	}

	public void setSuperServiceId(String superServiceId) {
		this.superServiceID = superServiceId;
	}

	public void setSwitchExp(String swtichExp) {
		this.switchExp = swtichExp;
	}

	public void setSwitchField(String swField) {
		this.switchField = swField;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public void setVerison(String version) {
		this.version = version;
	}

	public String getExtendsname() {
		return extendsname;
	}

	public void setExtendsname(String extendsname) {
		this.extendsname = extendsname;
	}

	public String getExtendsName() {
		return this.extendsname;
	}

	public void setExtendsName(String extendsname) {
		this.extendsname = extendsname;
	}

	public String getInterfaceType() {
		return this.interfacetype;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfacetype = interfaceType;
	}

	public String getMessageType() {
		return this.messageType;
	}

	public void setMessageType(String messagetype) {
		this.messageType = messagetype;
	}

}
