package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-27
 * Time: 上午10:43
 */
@Entity
@Table(name = "SG_MM_ASM_SDA_R")
public class SDANode4I {
    @Id
    @Column(name = "RESOURCEID")
    private String resourceId;
    @Column(name = "STRUCTNAME")
    private String structName;
    @Column(name = "STRUCTALIAS")
    private String structAlias;
    @Column(name = "STRUCTINDEX")
    private int structIndex;
    @Column(name = "METADATAID")
    private String metadataId;
    @Column(name = "ACTIONID")
    private String actionId;
    @Column(name = "PARENTRESOURCEID")
    private String parentResourceId;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "REQUIRED")
    private String required;
    @Column(name = "INTERFACEID")
    private String interfaceId;
    @Column(name = "LENGTH")
    private String length;
    @Column(name = "SCALE")
    private String scale;

    public String getTypeLengthAndScale() {
    	if (length == null || length.equals("")) {
    		return type;
    	} else if (scale == null || "".equals(scale)) {
    		return type + "(" + length + ")";
    	} else {
    		return type + "(" + length + "," + scale + ")";
    	}
    }
    
    public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceid(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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

    public int getStructIndex() {
        return structIndex;
    }

    public void setStructIndex(int structIndex) {
        this.structIndex = structIndex;
    }

    public String getMetadataId() {
		return metadataId;
	}

	public void setMetadataId(String metadataId) {
		this.metadataId = metadataId;
	}

	public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getParentResourceId() {
        return parentResourceId;
    }

    public void setParentResourceId(String parentResourceId) {
        this.parentResourceId = parentResourceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }
}
