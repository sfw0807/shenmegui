package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SG_MM_ASM_EXTENDS")
public class InterfaceExtendInfo {

	@Column(name = "ACTIONID")
    @Id
    private String actionId;
    @Column(name = "RELATIONID")
    private String relationId;
    @Column(name = "SUPERINTERFACEID")
    private String superInterfaceId;
    @Column(name = "SUPERInterfaceNAME")
    private String superInterfaceName;
    @Column(name = "EXTENDSEQ")
    private int extendSeq;
    @Column(name = "VERSIONNO")
    private String versionNo;
    @Column(name = "UPDATEDATE")
    private String upateDate;
    @Column(name = "VERSIONST")
    private String versionSt;
    @Column(name = "PRODUCTNO")
    private String productNo;
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public String getSuperInterfaceId() {
		return superInterfaceId;
	}
	public void setSuperInterfaceId(String superInterfaceId) {
		this.superInterfaceId = superInterfaceId;
	}
	public String getSuperInterfaceName() {
		return superInterfaceName;
	}
	public void setSuperInterfaceName(String superInterfaceName) {
		this.superInterfaceName = superInterfaceName;
	}
	public int getExtendSeq() {
		return extendSeq;
	}
	public void setExtendSeq(int extendSeq) {
		this.extendSeq = extendSeq;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getUpateDate() {
		return upateDate;
	}
	public void setUpateDate(String upateDate) {
		this.upateDate = upateDate;
	}
	public String getVersionSt() {
		return versionSt;
	}
	public void setVersionSt(String versionSt) {
		this.versionSt = versionSt;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
}
