package com.dc.esb.servicegov.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class MetaStructPK implements Serializable{
	
	private String structId;
	private String elementId;
	
	public String getStructId() {
		return structId;
	}
	public void setStructId(String structId) {
		this.structId = structId;
	}
	public String getElementId() {
		return elementId;
	}
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	
	@Override
    public boolean equals(Object obj) {
        if(!(obj instanceof  MetaStructPK)){
            return false;
        }
        MetaStructPK another = (MetaStructPK) obj;
        return ((null == this.structId) ?(null == another.getStructId()):(this.structId.equals(another.getStructId())))&&
                ((null == this.elementId) ?(null == another.getElementId()):(this.elementId.equals(another.getElementId())));
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        if(null != structId){
            hashCode ^= structId.hashCode();
        }
        if(null != elementId){
            hashCode ^= elementId.hashCode();
        }
        return hashCode;
    }
	

}
