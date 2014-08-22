package com.dc.esb.servicegov.vo;


public class SDANode4IVo {

    private String resourceId;
    private String structName;
    private String structAlias;
    private int structIndex;
    private String metadataId;
    private String actionId;
    private String parentResourceId;
    private String type;
    private String remark;
    private String required;
    private String interfaceId;
    private String length;
    private String scale;

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
