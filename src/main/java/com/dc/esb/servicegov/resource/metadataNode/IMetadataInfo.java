package com.dc.esb.servicegov.resource.metadataNode;

import java.util.Properties;

public interface IMetadataInfo {
	Properties getExtProperties();
	void setExtProperties(Properties extProperties);
	String getInterfaceId();
	//接口ID
	void setInterfaceId(String interfaceId);
	String getSuperInterfaceId();
	//父接口ID
	void setSuperInterfaceId(String superInterfaceId);
	String getServiceId();
	//服务ID
	void setServiceId(String serviceId);
	String getSuperServiceId();
	//父服务ID
	void setSuperServiceId(String superServiceId);
	String getInterfaceName();
	//接口名称
	void setInterfaceName(String interfaceName);
	String getChannelId();
	//渠道名称
	void setChannelId(String channelId);
	//SDO类型
	String getSdoType();
	void setSdoType(String sdoType);
	//客户端类型
	String getClientType();
	void setClientType(String clientType);
	//交易码
	String getTranCode();
	void setTranCode(String tranCode);
	//服务名称
	String getServiceName();
	void setServiceName(String serviceName);
	//服务场景ID
	String getServiceSceneId();
	void setServiceSceneId(String serviceSceneId);
	//分组ID
	String getGroupId();
	void setGroupId(String groupId);
	String toString();
	//陈功标识字段
	void setSwitchField(String swField);
	String getSwitchField();
	//判读表格式
	void setSwitchExp(String swtichExp);
	String getSwitchExp();
	//模板版本
	void setVerison(String version);
	String getVersion();
	//描述
	void setRemark(String remark);
	String getRemark();
	void setExtendsName(String extendsname);
	String getExtendsName();
	//接口类型
	void setInterfaceType(String interfaceType);
	String getInterfaceType();
	//提供系统
	void setProviderSysId(String providersysid);
	String getProviderSysId();
	//报文类型
	void setMessageType(String messagetype);
	String getMessageType();
}
