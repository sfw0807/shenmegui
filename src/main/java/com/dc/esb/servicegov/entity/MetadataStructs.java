package com.dc.esb.servicegov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="METADATA_STRUCTS")
public class MetadataStructs implements Serializable{
	
	private static final long serialVersionUID = 5598744861213245l;
	
	@Id
	@Column(name="STRUCTID")
	private String structId;
	@Column(name="STRUCTNAME")
	private String structName;
	@Column(name="REMARK")
	private String remark;
	public String getStructId() {
		return structId;
	}
	public void setStructId(String structId) {
		this.structId = structId;
	}
	public String getStructName() {
		return structName;
	}
	public void setStructName(String structName) {
		this.structName = structName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
