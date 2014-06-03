package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-29
 * Time: 下午3:48
 */
@Entity
@Table(name="SG_MM_MSM_EXTENDS")
public class ServiceExtendInfo {
    @Column(name="ACTIONID")
    @Id
    private String actionId;
    @Column(name="RELATIONID")
    private String relationId;
    @Column(name="SUPERSERVICEID")
    private String superServiceId;
    @Column(name="SUPERSERVICENAME")
    private String superServiceName;
    @Column(name="EXTENDSEQ")
    private int extendSeq;

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getSuperServiceId() {
        return superServiceId;
    }

    public void setSuperServiceId(String superServiceId) {
        this.superServiceId = superServiceId;
    }

    public String getSuperServiceName() {
        return superServiceName;
    }

    public void setSuperServiceName(String superServiceName) {
        this.superServiceName = superServiceName;
    }

    public int getExtendSeq() {
        return extendSeq;
    }

    public void setExtendSeq(int extendSeq) {
        this.extendSeq = extendSeq;
    }
}
