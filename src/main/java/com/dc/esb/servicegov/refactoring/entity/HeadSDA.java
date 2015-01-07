package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="HEADSDA")
public class HeadSDA implements Serializable{
	private static final long serialVersionUID = -5205631070005040562L;
	@Id
	@Column(name="RESOURCEID")
	private String id;
	@Column(name="STRUCTNAME")
	private String structName;
	@Column(name="STRUCTALIAS")
	private String structAlias;
	@Column(name="SERVICEID")
	private String headId;
	@Column(name="STRUCTINDEX")
	private int structIndex;
	@Column(name="METADATAID")
	private String matadataId;
	@Column(name="ACTIONID")
	private String actionId;
	@Column(name="PARENTRESOURCEID")
	private String parentId;
	@Column(name="TYPE")
	private String type;
	@Column(name="REMARK")
	private String remark;
	@Column(name="REQUIRED")
	private String required;
	@Column(name="VERSIONNO")
	private String versionno;
	@Column(name="UPDATEDATE")
	private String updatedate;
	@Column(name="VERSIONST")
	private String versionst;
	@Column(name="PRODUCTNO")
	private String productno;
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
	public String getHeadId() {
		return headId;
	}
	public void setHeadId(String headId) {
		this.headId = headId;
	}
	public int getStructIndex() {
		return structIndex;
	}
	public void setStructIndex(int structIndex) {
		this.structIndex = structIndex;
	}
	public String getMatadataId() {
		return matadataId;
	}
	public void setMatadataId(String matadataId) {
		this.matadataId = matadataId;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	public String getVersionno() {
		return versionno;
	}
	public void setVersionno(String versionno) {
		this.versionno = versionno;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getVersionst() {
		return versionst;
	}
	public void setVersionst(String versionst) {
		this.versionst = versionst;
	}
	public String getProductno() {
		return productno;
	}
	public void setProductno(String productno) {
		this.productno = productno;
	}
	
}
