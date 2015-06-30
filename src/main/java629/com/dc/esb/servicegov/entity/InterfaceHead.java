package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="INTERFACE_HEAD")
public class InterfaceHead {
	@Id
	@Column(name = "HEAD_ID")
	private String headId;
	
	@Column(name = "HEAD_NAME")
	private String headName;
	
	@Column(name = "HEAD_DESC")
	private String headDesc;
	
	@Column(name = "HEAD_REMARK")
	private String headRemark;

	public String getHeadId() {
		return headId;
	}

	public void setHeadId(String headId) {
		this.headId = headId;
	}

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public String getHeadDesc() {
		return headDesc;
	}

	public void setHeadDesc(String headDesc) {
		this.headDesc = headDesc;
	}

	public String getHeadRemark() {
		return headRemark;
	}

	public void setHeadRemark(String headRemark) {
		this.headRemark = headRemark;
	}
	
	
}
