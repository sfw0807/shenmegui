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
@Table(name = "INTERFACE")
public class Interface {
    @Id
    @Column(name = "INTERFACE_ID")
    private String interfaceId;
    @Column(name = "INTERFACE_NAME")
    private String interfaceName;
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "ECODE")
    private String ecode;
    @Column(name = "VERSION")
    private String version;
    @Column(name = "STATE")
    private String state;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
