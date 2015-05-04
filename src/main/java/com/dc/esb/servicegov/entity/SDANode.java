package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-27
 * Time: 上午10:43
 */
//@Entity
//@Table(name = "SG_MM_MSM_SDA_R")
public class SDANode {
//    @Id
//    @Column(name = "RESOURCEID")
    private String resourceId;
//    @Column(name = "STRUCTNAME")
    private String structName;
//    @Column(name = "STRUCTALIAS")
    private String structAlias;
//    @Column(name = "SERVICEID")
    private String serviceId;
//    @Column(name = "STRUCTINDEX")
    private int structIndex;
//    @Column(name = "METADATAID")
    private String metadataId;
//    @Column(name = "ACTIONID")
    private String actionId;
//    @Column(name = "PARENTRESOURCEID")
    private String parentResourceId;
//    @Column(name = "TYPE")
    private String type;
//    @Column(name = "REMARK")
    private String remark;
//    @Column(name = "REQUIRED")
    private String required;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getStructName() {
        return structName;
    }

    public void setStructName(String structName) {
        this.structName = structName;
    }

    public String getStructAlias() {
        return structAlias;
    }

    public void setStructAlias(String structAlias) {
        this.structAlias = structAlias;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public int getStructIndex() {
        return structIndex;
    }

    public void setStructIndex(int structIndex) {
        this.structIndex = structIndex;
    }


    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getParentResourceId() {
        return parentResourceId;
    }

    public void setParentResourceId(String parentResourceId) {
        this.parentResourceId = parentResourceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }
}