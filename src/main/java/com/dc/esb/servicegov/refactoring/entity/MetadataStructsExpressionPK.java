package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MetadataStructsExpressionPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5546545776464545L;
	@Column(name = "STRUCTID")
	private String structId;
	@Column(name = "ELEMENTID")
	private String elementId;

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
		if (null != elementId) {
			hashCode ^= elementId.hashCode();
		}
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MetadataStructsExpressionPK)) {
			return false;
		}
		MetadataStructsExpressionPK another = (MetadataStructsExpressionPK) obj;
		boolean structIdEq = (null == this.structId) ? (null == another
				.getStructId()) : (this.structId.equals(another
				.getStructId()));
		boolean metadataIdEq = (null == this.elementId) ? (null == another
				.getMetadataId()) : (this.elementId.equals(another
				.getMetadataId()));
		return structIdEq && metadataIdEq;
	}

	public String getMetadataId() {
		return elementId;
	}

	public void setMetadataId(String metadataId) {
		this.elementId = metadataId;
	}
}
