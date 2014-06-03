package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-29
 * Time: 下午2:13
 */
@Entity
@Table(name="SG_MM_MDT_METADATAATTRIBUTES")
public class MetadataAttribute {
    @Id
    @Column(name="ATTRIBUTEID")
    private String attributeId;
    @Column(name="ATTRIBUTENAME")
    private String attributeName;
    @Column(name="ATTRIBUTEVALUE")
    private String attributeValue;
    @Column(name="ACTIONID")
    private String actionId;
    @Column(name="METADATAID")
    private String metadataId;
    @Column(name="RELEASEID")
    private String releaseId;

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }
}
