package com.dc.esb.servicegov.refactoring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dc.esb.servicegov.util.UUIDUtil;

@Entity
@Table(name = "IDA")
public class IDA {

	@Id
	@Column(name="ID")
	private String id;
	@Column(name="STRUCTNAME")
	private String structName;
	@Column(name="STRUCTALIAS")
	private String structAlias;
	@Column(name="METADATA_ID")
	private String metadataId;
	@Column(name="SEQ")
	private Integer seq;
	@Column(name="TYPE")
	private String type;
	@Column(name="SCALE")
	private String scale;
	@Column(name="LENGTH")
	private String length;
	@Column(name="REQUIRED")
	private String required;
	@Column(name="PARENT_ID")
	private String parentId;
	@Column(name="INTERFACE_ID")
	private String interfaceId;
	@Column(name="REMARK")
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public IDA(){
		super();
	}
	
	public IDA(String structName, String interfaceId, IDA parentIDA) {
		this.id = UUIDUtil.getUUID();
		this.structName = structName;
		this.interfaceId = interfaceId;
		this.metadataId = "";
		this.structAlias = "";
		if ("root".equals(structName)) {
			this.parentId = "/";
			this.setSeq(1);
		} else {
			this.setParentId(parentIDA.getId());
		}
	}
	
	
	
	public IDA(String structName, String structAlias, String metadataId,
			String type, String scale, String length, String required,
			String interfaceId, IDA parentIDA) {
		this.id = UUIDUtil.getUUID();
		this.structName = structName;
		this.structAlias = structAlias;
		this.metadataId = metadataId;
		this.type = type;
		this.scale = scale;
		this.length = length;
		this.required = required;
		this.parentId = parentIDA.getId();
		this.interfaceId = interfaceId;
	}



	public String getTypeLengthAndScale() {
    	if (length == null || length.equals("")) {
    		return type;
    	} else if (scale == null || "".equals(scale)) {
    		return type + "(" + length + ")";
    	} else {
    		return type + "(" + length + "," + scale + ")";
    	}
    }
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStructName() {
		return structName;
	}

	public void setStructName(String structName) {
		this.structName = structName;
	}

	public String getStructAlias() {
		return structAlias;
	}

	public void setStructAlias(String structAlias) {
		this.structAlias = structAlias;
	}

	public String getMetadataId() {
		return metadataId;
	}

	public void setMetadataId(String metadataId) {
		this.metadataId = metadataId;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
}
