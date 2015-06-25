package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="METADATA")
public class Metadata {
	@Id
    @Column(name = "METADATA_ID")
	private String metadataId;
	
	 @Column(name = "METADATA_NAME")
	 private String metadataName;
	 
	 @Column(name = "CHINESE_NAME")
	 private String chineseName;
	 
	 @Column(name = "CATEGORY_WORD_ID")
	 private String categoryWordId;
	 
	 @Column(name = "REMARK")
	 private String remark;
	 
	 @Column(name = "TYPE")
	 private String type;
	 
	 @Column(name = "LENGTH")
	 private String length;
	 
	 @Column(name = "SCALE")
	 private String scale;
	 
	 @Column(name = "ENUM_ID")
	 private String enumId;

	 @Column(name = "METADATA_ALIAS")
	 private String metadataAlias;
	 
	 @Column(name = "BUSS_DEFINE")
	 private String bussDefine;
	 
	 @Column(name = "BUSS_RULE")
	 private String bussRule;
	 
	 @Column(name = "DATA_SOURCE")
	 private String dataSource;
	 
	 @Column(name = "TEMPLATE_ID")
	 private String templateId;
	 
	 @Column(name = "STATUS")
	 private String status;
	 
	 @Column(name = "VERSION")
	 private String version;
	 
	 @Column(name = "OPT_USER")
	 private String optUser;
	
	 @Column(name = "OPT_DATE")
	 private String optDate;
	 
	 @Column(name = "AUDIT_USER")
	 private String auditUser;
	
	 @Column(name = "AUDIT_DATE")
	 private String auditDate;

	public String getMetadataId() {
		return metadataId;
	}

	public void setMetadataId(String metadataId) {
		this.metadataId = metadataId;
	}

	public String getMetadataName() {
		return metadataName;
	}

	public void setMetadataName(String metadataName) {
		this.metadataName = metadataName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getCategoryWordId() {
		return categoryWordId;
	}

	public void setCategoryWordId(String categoryWordId) {
		this.categoryWordId = categoryWordId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getEnumId() {
		return enumId;
	}

	public void setEnumId(String enumId) {
		this.enumId = enumId;
	}

	public String getMetadataAlias() {
		return metadataAlias;
	}

	public void setMetadataAlias(String metadataAlias) {
		this.metadataAlias = metadataAlias;
	}

	public String getBussDefine() {
		return bussDefine;
	}

	public void setBussDefine(String bussDefine) {
		this.bussDefine = bussDefine;
	}

	public String getBussRule() {
		return bussRule;
	}

	public void setBussRule(String bussRule) {
		this.bussRule = bussRule;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	

	public String getOptUser() {
		return optUser;
	}

	public void setOptUser(String optUser) {
		this.optUser = optUser;
	}

	public String getOptDate() {
		return optDate;
	}

	public void setOptDate(String optDate) {
		this.optDate = optDate;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	
	 
}
