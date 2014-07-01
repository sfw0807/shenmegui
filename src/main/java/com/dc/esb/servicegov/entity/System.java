package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-9
 * Time: 下午3:17
 */
@Entity
@Table(name = "SG_MM_SYS_SYSINFO")
public class System {
    @Id
    @Column(name = "ORGID")
    private String systemId;
    @Column(name = "SYSENGAB")
    private String systemAbbreviation;
    @Column(name = "SYSNAME")
    private String systemName;
    @Column(name = "SYSTYPE")
    private String systemType;
    @Column(name = "SYSMSGTYPE")
    private String systemMsgType;
    @Column(name = "SYSREMARK")
    private String systemRemark;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemAbbreviation() {
        return systemAbbreviation;
    }

    public void setSystemAbbreviation(String systemAbbreviation) {
        this.systemAbbreviation = systemAbbreviation;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getSystemMsgType() {
        return systemMsgType;
    }

    public void setSystemMsgType(String systemMsgType) {
        this.systemMsgType = systemMsgType;
    }

    public String getSystemRemark() {
        return systemRemark;
    }

    public void setSystemRemark(String systemRemark) {
        this.systemRemark = systemRemark;
    }
}
