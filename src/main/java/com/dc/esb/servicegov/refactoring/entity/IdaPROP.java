package com.dc.esb.servicegov.refactoring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "IDA_PROP")
public class IdaPROP {

	@Id
	@Column(name="ID")
	private String id;
	@Column(name="IDA_ID")
	private String idaId;
	@Column(name="PROPERTYNAME")
	private String name;
	@Column(name="PROPERTYVALUE")
	private String value;
	@Column(name="REMARK")
	private String remark;
	@Column(name="SEQ")
	private String seq;
	@Column(name="VERSIONNO")
	private String versionno;
	@Column(name="UPDATEDATE")
	private String updateDate;
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
	public String getIdaId() {
		return idaId;
	}
	public void setIdaId(String idaId) {
		this.idaId = idaId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getVersionno() {
		return versionno;
	}
	public void setVersionno(String versionno) {
		this.versionno = versionno;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
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
