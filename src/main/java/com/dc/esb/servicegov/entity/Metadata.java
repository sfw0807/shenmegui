package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-27
 * Time: 上午11:07
 */
@Entity
@Table(name="SG_MM_MDT_METADATA")
public class Metadata {
    @Id
    @Column(name="METADATAID")
    private String metadataId;
    @Column(name="METADATANAME")
    private String metadataName;
    @Column(name="GROUPACTIONID")
    private String groupActionId;
    @Column(name="METADATADESC")
    private String metadataDesc;
    @Column(name="CODEVALUE")
    private String codeValue;
    @Column(name="STATE")
    private String state;
    @Column(name="ACTIONID")
    private String actionId;
    @Column(name="RELEASEID")
    private String releaseId;
    @Column(name="VERSIONNUM")
    private String versionNum;
    @Column(name="OPERATEUSERID")
    private String operateUserId;

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getMetadataName() {
        return metadataName;
    }

    public void setMetadataName(String metadataName) {
        this.metadataName = metadataName;
    }

    public String getGroupActionId() {
        return groupActionId;
    }

    public void setGroupActionId(String groupActionId) {
        this.groupActionId = groupActionId;
    }

    public String getMetadataDesc() {
        return metadataDesc;
    }

    public void setMetadataDesc(String metadataDesc) {
        this.metadataDesc = metadataDesc;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(String operateUserId) {
        this.operateUserId = operateUserId;
    }
}
