package com.dc.esb.servicegov.vo;

import com.dc.esb.servicegov.entity.Interface;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-9
 * Time: 下午3:05
 */
public class InterfaceVo {
    private String interfaceId;
    private String interfaceName;
    private String interfaceRemark;
    private String interfaceType;
    private String serviceId;
    private String resoutceId;
    private String interfaceMsgType;
    private String sysId;
    private String actionId;
    private String versionState;
    private int versionNumber;
    private String systemName;
    private String consumerSys;
    private String providerSys;
    private String bigSserviceId;
    private String bigSserviceName;

    public String getBigSserviceId() {
		return bigSserviceId;
	}


	public void setBigSserviceId(String bigSserviceId) {
		this.bigSserviceId = bigSserviceId;
	}


	public String getBigSserviceName() {
		return bigSserviceName;
	}


	public void setBigSserviceName(String bigSserviceName) {
		this.bigSserviceName = bigSserviceName;
	}


	public InterfaceVo(Interface i) {
        this.interfaceId = i.getInterfaceId();
        this.interfaceName = i.getInterfaceName();
        this.interfaceRemark = i.getInterfaceRemark();
        this.interfaceType = i.getInterfaceType();
        this.serviceId = i.getServiceId();
        this.resoutceId = i.getResoutceId();
        this.interfaceMsgType = i.getInterfaceMsgType();
        this.sysId = i.getSysId();
        this.actionId = i.getActionId();
        this.versionState = i.getVersionState();
        this.versionNumber = i.getVersionNumber();
    }

    
    public String getConsumerSys() {
		return consumerSys;
	}

	public void setConsumerSys(String consumerSys) {
		this.consumerSys = consumerSys;
	}

	public String getProviderSys() {
		return providerSys;
	}



	public void setProviderSys(String providerSys) {
		this.providerSys = providerSys;
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

    public String getInterfaceRemark() {
        return interfaceRemark;
    }

    public void setInterfaceRemark(String interfaceRemark) {
        this.interfaceRemark = interfaceRemark;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getResoutceId() {
        return resoutceId;
    }

    public void setResoutceId(String resoutceId) {
        this.resoutceId = resoutceId;
    }

    public String getInterfaceMsgType() {
        return interfaceMsgType;
    }

    public void setInterfaceMsgType(String interfaceMsgType) {
        this.interfaceMsgType = interfaceMsgType;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getVersionState() {
        return versionState;
    }

    public void setVersionState(String versionState) {
        this.versionState = versionState;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
