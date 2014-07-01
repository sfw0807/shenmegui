package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-24
 * Time: 下午1:01
 */
@Entity
@Table(name="SG_MM_MDT_METADATA_STRUCTS")
public class MetaStructNode {

    @Column(name="STRUCTID")
    private String structId;
    @Column(name="ELEMENTID")
    private String elementId;
    @Column(name="ELEMENTNAME")
    private String elementName;
    @Column(name="ISREQUIRED")
    private String required;
    //TOdo this is a mork key
    @Id
    @Column(name="REMARK")
    private String remark;

    public String getStructId() {
        return structId;
    }

    public void setStructId(String structId) {
        this.structId = structId;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
