package com.dc.esb.servicegov.refactoring.vo;

public class SystemNode {
	private String nodeName;
	private String nodeLabel;
	private String nodeType;
	private String nodeX;
	private String nodeY;
	private String nodeDesc;
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeLabel() {
		return nodeLabel;
	}
	public void setNodeLabel(String nodeLabel) {
		this.nodeLabel = nodeLabel;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getNodeX() {
		return nodeX;
	}
	public void setNodeX(String nodeX) {
		this.nodeX = nodeX;
	}
	public String getNodeY() {
		return nodeY;
	}
	public void setNodeY(String nodeY) {
		this.nodeY = nodeY;
	}
	public String getNodeDesc() {
		return nodeDesc;
	}
	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nodeDesc == null) ? 0 : nodeDesc.hashCode());
		result = prime * result
				+ ((nodeLabel == null) ? 0 : nodeLabel.hashCode());
		result = prime * result
				+ ((nodeName == null) ? 0 : nodeName.hashCode());
		result = prime * result + ((nodeX == null) ? 0 : nodeX.hashCode());
		result = prime * result + ((nodeY == null) ? 0 : nodeY.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemNode other = (SystemNode) obj;
		if (nodeDesc == null) {
			if (other.nodeDesc != null)
				return false;
		} else if (!nodeDesc.equals(other.nodeDesc))
			return false;
		if (nodeLabel == null) {
			if (other.nodeLabel != null)
				return false;
		} else if (!nodeLabel.equals(other.nodeLabel))
			return false;
		if (nodeName == null) {
			if (other.nodeName != null)
				return false;
		} else if (!nodeName.equals(other.nodeName))
			return false;
		if (nodeX == null) {
			if (other.nodeX != null)
				return false;
		} else if (!nodeX.equals(other.nodeX))
			return false;
		if (nodeY == null) {
			if (other.nodeY != null)
				return false;
		} else if (!nodeY.equals(other.nodeY))
			return false;
		return true;
	}
	
}
