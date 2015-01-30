package com.dc.esb.servicegov.refactoring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//OPERATION_ID  	VARCHAR(255) NOT NULL,
//SERVICE_ID    	VARCHAR(40) NOT NULL,
//OPERATION_NAME	VARCHAR(100),
//REMARK        	VARCHAR(500),
//VERSION       	VARCHAR(25),
//STATE         	VARCHAR(40),

@Entity
@Table(name="SG_OPERATION")
@IdClass(OperationPK.class)
public class Operation {
	@Id
	private String operationId;
	@Id
	private String serviceId;
	@Column(name="OPERATION_NAME")
	private String operationName;
	@Column(name="REMARK")
	private String remark;
	@Column(name="VERSION")
	private String version;
	@Column(name="STATE")
	private String state;
	@Column(name="MODIFYUSER")
	private String modifyUser;
	@Column(name="UPDATETIME")
	private String updateTime;
	@Column(name="AUDITSTATE")
	private String auditState;
	@ManyToOne(targetEntity=Service.class)
	@JoinColumns({
		@JoinColumn(name="SERVICE_ID",referencedColumnName="SERVICE_ID",insertable=false,updatable=false)
	})
	private Service service;
	@ManyToOne(targetEntity=AuditState.class)
	@JoinColumn(name="AUDITSTATE",referencedColumnName="ID",insertable=false,updatable=false)
	private AuditState audit;
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public AuditState getAudit() {
		return audit;
	}
	public void setAudit(AuditState audit) {
		this.audit = audit;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((operationId == null) ? 0 : operationId.hashCode());
		result = prime * result
				+ ((operationName == null) ? 0 : operationName.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		if (operationId == null) {
			if (other.operationId != null)
				return false;
		} else if (!operationId.equals(other.operationId))
			return false;
		if (operationName == null) {
			if (other.operationName != null)
				return false;
		} else if (!operationName.equals(other.operationName))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
}
