package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RELATION_VIEW")
public class RelationView {

	@Id
	@Column(name="INTERFACEID")
	private String interfaceId;
	@Column(name="INTERFACENAME")
	private String interfaceName;
	@Column(name="INTERFACEMEGTYPE")
	private String interfaceMegType;
	@Column(name="INTERFACETYPE")
	private String interfaceType;
	@Column(name="SYSID")
	private String systemId;
	@Column(name="SYSENGAB")
	private String systemEngName;
	@Column(name="VERSINNO")
	private String versionNo;
	@Column(name="PRODUCTNO")
	private String productNo;
	@Column(name="ONLINEDATE")
	private String onlineDate;
	@Column(name="SERVICEID")
	private String serviceId;
	@Column(name="RESOURCEID")
	private String resourceId;
	@Column(name="SERVICENAME")
	private String serviceName;
	@Column(name="SVRREMARK")
	private String serviceRemark;
	@Column(name="SUPERSERVICEID")
	private String superServiceId;
	@Column(name="SUPERSERVICENAME")
	private String superServiceName;
	@Column(name="SUPERSVCREMARK")
	private String superServiceRemark;
	@Column(name="VERSIONST")
	private String versionState;
	
	public String getVersionState() {
		return versionState;
	}
	public void setVersionState(String versionState) {
		this.versionState = versionState;
	}
	public String getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getInterfaceMegType() {
		return interfaceMegType;
	}
	public void setInterfaceMegType(String interfaceMegType) {
		this.interfaceMegType = interfaceMegType;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getOnlineDate() {
		return onlineDate;
	}
	public void setOnlineDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceRemark() {
		return serviceRemark;
	}
	public String getSuperServiceName() {
		return superServiceName;
	}
	public void setSuperServiceName(String superServiceName) {
		this.superServiceName = superServiceName;
	}
	public void setServiceRemark(String serviceRemark) {
		this.serviceRemark = serviceRemark;
	}
	public String getSuperServiceId() {
		return superServiceId;
	}
	public void setSuperServiceId(String superServiceId) {
		this.superServiceId = superServiceId;
	}
	public String getSuperServiceRemark() {
		return superServiceRemark;
	}
	public void setSuperServiceRemark(String superServiceRemark) {
		this.superServiceRemark = superServiceRemark;
	}
	public String getSystemEngName() {
		return systemEngName;
	}
	public void setSystemEngName(String systemEngName) {
		this.systemEngName = systemEngName;
	}
	
	
}
