package com.dc.esb.servicegov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MetadataStructsAttrPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -555555500446666L;
	@Column(name = "STRUCTID")
	private String structId;
	@Column(name = "METADATAID")
	private String metadataId;

	public String getStructId() {
		return structId;
	}

	public void setStructId(String structId) {
		this.structId = structId;
	}
	

	

	@Override
	public int hashCode() {
		int hashCode = 0;
		if (null != structId) {
			hashCode ^= structId.hashCode();
		}
		if (null != metadataId) {
			hashCode ^= metadataId.hashCode();
		}
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MetadataStructsAttrPK)) {
			return false;
		}
		MetadataStructsAttrPK another = (MetadataStructsAttrPK) obj;
		boolean structIdEq = (null == this.structId) ? (null == another
				.getStructId()) : (this.structId.equals(another
				.getStructId()));
		boolean metadataIdEq = (null == this.metadataId) ? (null == another
				.getMetadataId()) : (this.metadataId.equals(another
				.getMetadataId()));
		return structIdEq && metadataIdEq;
	}

	public String getMetadataId() {
		return metadataId;
	}

	public void setMetadataId(String metadataId) {
		this.metadataId = metadataId;
	}
}
