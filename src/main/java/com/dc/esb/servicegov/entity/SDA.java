package com.dc.esb.servicegov.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dc.esb.servicegov.util.DateUtils;
import com.dc.esb.servicegov.util.TreeNode;

@Entity
@Table(name = "SDA")
public class SDA implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SDA_ID")
	private String sdaId;
	
	@Column(name = "STRUCTNAME")
	private String structName;
	
	@Column(name = "STRUCTALIAS")
	private String structAlias;
	
	@Column(name = "METADATAID")
	private String metadataId;
	
	@Column(name = "SEQ")
	private String seq;
	
	@Column(name = "PARENT_ID")
	private String parentId;
	
	@Column(name = "SERVICE_ID")
	private String serviceId;
	
	@Column(name = "OPT_USER")
	private String optUser;
	
	@Column(name = "OPT_DATE")
	private String optDate;
	
	@Column(name = "OPERATION_ID")
	private String operationId;
	
	@Column(name = "DESCRIPTION")
	private String desc;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "HEAD_ID")
	private String headId;
	
	@Column(name = "version")
	private String version;
	
	@Column(name = "type")
	private String type;
	
	public SDA(){
		
	}
	
	public String getSdaId() {
		return sdaId;
	}

	public void setSdaId(String sdaId) {
		this.sdaId = sdaId;
	}

	public String getStructName() {
		return structName;
	}

	public void setStructName(String structName) {
		this.structName = structName;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
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

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getHeadId() {
		return headId;
	}

	public void setHeadId(String headId) {
		this.headId = headId;
	}

	public String getStructAlias() {
		return structAlias;
	}

	public void setStructAlias(String structAlias) {
		this.structAlias = structAlias;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
