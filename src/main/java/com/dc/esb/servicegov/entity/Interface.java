package com.dc.esb.servicegov.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-9
 * Time: 上午11:37
 */
@Entity
@Table(name = "SG_MM_ASM_ADDTIONALSTRUCTURE")
public class Interface {
    @Id
    @Column(name = "INTERFACEID")
    private String interfaceId;
    @Column(name = "INTERFACENAME")
    private String interfaceName;
    @Column(name = "INTERFACEREMARK")
    private String interfaceRemark;
    @Column(name = "INTERFACETYPE")
    private String interfaceType;
    @Column(name = "SERVICEID")
    private String serviceId;
    @Column(name = "RESOURCEID")
    private String resoutceId;
    @Column(name = "INTERFACEMEGTYPE")
    private String interfaceMsgType;
    @Column(name = "SYSID")
    private String sysId;
    @Column(name = "ACTIONID")
    private String actionId;
    @Column(name = "VERSIONSTATE")
    private String versionState;
    @Column(name = "VERSIONNUMBER")
    private int versionNumber;

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
}
