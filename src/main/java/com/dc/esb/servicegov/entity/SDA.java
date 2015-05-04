package com.dc.esb.servicegov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SDA")
public class SDA implements Serializable{
	private static final long serialVersionUID = -8290864127288905517L;
	@Id
	@Column(name="ID")
	private String id;
	@Column(name="STRUCT_ID")
	private String structId;
	@Column(name="METADATA_ID")
	private String metadataId;
	@Column(name="TYPE")
	private String type;
	@Column(name="SEQ")
	private int seq;
	@Column(name="REQUIRED")
	private String required;
	@Column(name="REMARK")
	private String remark;
	@Column(name="SERVICE_ID")
	private String serviceId;		
	@Column(name="OPERATION_ID")
	private String operationId;
	@Column(name="PARENT_ID")
	private String parentId;
	@Column(name="MODIFYUSER")
	private String modifyUser;
	@Column(name="UPDATETIME")
	private String updateTime;	
	@ManyToOne(targetEntity=Operation.class)
	@JoinColumns({
		@JoinColumn(name="SERVICE_ID",referencedColumnName="SERVICE_ID",insertable=false,updatable=false),
		@JoinColumn(name="OPERATION_ID",referencedColumnName="OPERATION_ID",insertable=false,updatable=false)
	})
	private Operation operation;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStructId() {
		return structId;
	}
	public void setStructId(String structId) {
		this.structId = structId;
	}
	public String getMetadataId() {
		return metadataId;
	}
	public void setMetadataId(String metadataId) {
		this.metadataId = metadataId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}	
}
