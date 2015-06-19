package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="IDA")
public class Ida {
	@Id
    @Column(name = "ID")
	private String id;
	
	@Column(name = "STRUCTNAME")
	private String structName;
	
	@Column(name = "STRUCTALIAS")
	private String structAlias;
	
	@Column(name = "METADATA_ID")
	private String metadataId;
	
	@Column(name = "SEQ")
	private String seq;
	
	 @Column(name = "TYPE")
	 private String type;
	 
	 @Column(name = "SCALE")
	 private String scale;
	 
	 @Column(name = "LENGTH")
	 private String length;
	 
	 @Column(name = "REQUIRED")
	 private String required;
	 
	 @Column(name = "RARENT_ID")
	 private String rarentId;
	 
	 @Column(name = "INTERFACE_ID")
	 private String interfaceId;
	 
	 
	 @Column(name = "OPT_USER")
	 private String potUser;
	
	 @Column(name = "OPT_DATE")
	 private String potDate;
	 
	 @Column(name = "HEAD_ID")
	 private String headId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getMetadataId() {
		return metadataId;
	}

	public void setMetadataId(String metadataId) {
		this.metadataId = metadataId;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getRarentId() {
		return rarentId;
	}

	public void setRarentId(String rarentId) {
		this.rarentId = rarentId;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getPotUser() {
		return potUser;
	}

	public void setPotUser(String potUser) {
		this.potUser = potUser;
	}

	public String getPotDate() {
		return potDate;
	}

	public void setPotDate(String potDate) {
		this.potDate = potDate;
	}

	public String getHeadId() {
		return headId;
	}

	public void setHeadId(String headId) {
		this.headId = headId;
	}
	 
	 
}
