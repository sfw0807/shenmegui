package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="METADATA")
public class Metadata implements Serializable{
	
	private static final long serialVersionUID = 69589445122445721l;
	
	@Id
	@Column(name="metadata_id")
	private String metadataId;
	@Column(name="metadata_name")
	private String name;
	@Column(name="metadata_remark")
	private String remark;
	@Column(name="type")
	private String type;
	@Column(name="length")
	private String length;
	@Column(name="scale")
	private String scale;
	@Column(name="modifyuser")
	private String modifyUser;
	@Column(name="updatetime")
	private String updateTime;

	public String getTypeLengthAndScale() {
    	if (length == null || length.equals("")) {
    		return type;
    	} else if (scale == null || "".equals(scale)) {
    		return type + "(" + length + ")";
    	} else {
    		return type + "(" + length + "," + scale + ")";
    	}
    }
	
	public String getMetadataId() {
		return metadataId;
	}
	public void setMetadataId(String metadataId) {
		this.metadataId = metadataId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}
