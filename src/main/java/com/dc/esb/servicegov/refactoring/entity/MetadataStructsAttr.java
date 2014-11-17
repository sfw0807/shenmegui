package com.dc.esb.servicegov.refactoring.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="METADATA_STRUCTS_ATTR")
@IdClass(MetadataStructsAttrPK.class)
public class MetadataStructsAttr {
	
	@Id
	@Column(name="STRUCTID")
	private String structId;
	@Id
	@Column(name="METADATAID")
	private String metadataId;
	@Column(name="ELEMENTID")
	private String elementId;
	@Column(name="ELEMENTNAME")
	private String elementName;
	@Column(name="REMARK")
	private String remark;
	@Column(name="ISREQUIRED")
	private String isRequired;
	@ManyToOne(targetEntity=Metadata.class)
	@JoinColumn(name="METADATAID",referencedColumnName="METADATA_ID",insertable=false,updatable=false)
	private Metadata metadata;
	

	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	public String getMetadataId() {
		return metadataId;
	}
	public void setMetadataId(String metadataId) {
		this.metadataId = metadataId;
	}
	
	
}
