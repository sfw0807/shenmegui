package com.dc.esb.servicegov.refactoring.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="METADATA_STRUCTS_EXPRESSSION")
@IdClass(MetadataStructsExpressionPK.class)
public class MetadataStructsExpression {
	
	@Id
	@Column(name="STRUCTID")
	private String structId;
	@Id
	@Column(name="ELEMENTID")
	private String elementId;
	@Column(name="ATTRIBUTEID")
	private String attributeId;
	@Column(name="ATTRIBUTEVALUE")
	private String attributeName;
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
	
	
}
