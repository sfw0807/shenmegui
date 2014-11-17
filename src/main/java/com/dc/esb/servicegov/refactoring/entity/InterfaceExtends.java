package com.dc.esb.servicegov.refactoring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INTERFACE_EXTENDS")
public class InterfaceExtends {

	@Id
	@Column(name="ID")
	private String id;
	@Column(name="INTERFACEID")
	private String interfaceId;
	@Column(name="SUPERINTERFACEID")
	private String superInterfaceId;
	@Column(name="SUPERINTERFACENAME")
	private String superInterfaceName;
	@Column(name="VERSIONNO")
	private String versionno;
	@Column(name="VERSIONST")
	private String versionst;
	@Column(name="PRODUCTNO")
	private String productno;
	@Column(name="UPDATEDATE")
	private String updateDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
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
	public String getVersionno() {
		return versionno;
	}
	public void setVersionno(String versionno) {
		this.versionno = versionno;
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
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}
