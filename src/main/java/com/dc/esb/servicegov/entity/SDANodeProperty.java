package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-27
 * Time: 上午10:59
 */
@Entity
@Table(name="SG_MM_MSM_SDA_PROP_R")
public class SDANodeProperty {
    @Id
    @Column(name="RESOURCEID")
    private String resouceId;
    @Column(name="PROPERTYNAME")
    private String propertyName;
    @Column(name="PROPERTYALIAS")
    private String propertyAlias;
    @Column(name="PROPERTYVALUE")
    private String propertyValue;
    @Column(name="REMARK")
    private String remark;
    @Column(name="PROPERTYINDEX")
    private int properyIndex;
    @Column(name="GROUPID")
    private String groupId;
    @Column(name="ACTIONID")
    private String actionId;
    @Column(name="STRUCTID")
    private String structId;

    public String getResouceId() {
        return resouceId;
    }

    public void setResouceId(String resouceId) {
        this.resouceId = resouceId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyAlias() {
        return propertyAlias;
    }

    public void setPropertyAlias(String propertyAlias) {
        this.propertyAlias = propertyAlias;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getProperyIndex() {
        return properyIndex;
    }

    public void setProperyIndex(int properyIndex) {
        this.properyIndex = properyIndex;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getStructId() {
        return structId;
    }

    public void setStructId(String structId) {
        this.structId = structId;
    }
}
