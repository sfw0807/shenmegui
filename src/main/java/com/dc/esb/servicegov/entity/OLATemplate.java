package com.dc.esb.servicegov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OLA_TEMPLATE")
public class OLATemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OLA_TEMPLATE_ID")
	private String olaTemplateId;
	
	@Column(name = "DESCRIPTION")
	private String desc;

	public String getOlaTemplateId() {
		return olaTemplateId;
	}

	public void setOlaTemplateId(String olaTemplateId) {
		this.olaTemplateId = olaTemplateId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
