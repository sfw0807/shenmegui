package com.dc.esb.servicegov.refactoring.resource.node;

import java.util.ArrayList;
import java.util.List;

public class Node implements INode {
	private String id = "";
	private String nodeId = "";
	private String nodeAlias = "";
	private String nodeType = "";
	private String nodeLength = "";
	private String nodeScale = "";
	private String nodeRequire = "";
	private String nodeRemark = "";
	private String metadataId = "";
	private String parentId = "";
	private String nodeExpression = "";
	private String nodeIsheader = "";
	private Node parentNode ;
	private List<Node> childNodes;

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeRequire() {
		return nodeRequire;
	}

	public void setNodeRequire(String nodeRequire) {
		this.nodeRequire = nodeRequire;
	}

	public String getNodeRemark() {
		return nodeRemark;
	}

	public void setNodeRemark(String nodeRemark) {
		this.nodeRemark = nodeRemark;
	}

	public String getMetadataId() {
		return metadataId;
	}

	public void setMetadataId(String metadataId) {
		this.metadataId = metadataId;
	}

	public String getNodeExpression() {
		return nodeExpression;
	}

	public void setNodeExpression(String nodeExpression) {
		this.nodeExpression = nodeExpression;
	}

	public String getNodeIsheader() {
		return nodeIsheader;
	}

	public void setNodeIsheader(String nodeIsheader) {
		this.nodeIsheader = nodeIsheader;
	}

	public List<Node> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<Node> childNodes) {
		this.childNodes = childNodes;
	}

	@Override
	public void appendChild(Node node) {
		// TODO Auto-generated method stub
		if (node != null) {
			if (node.getNodeId() != null && !"".equals(node.getNodeId())) {
				if(this.childNodes != null){
				   this.childNodes.add(node);
				}
				else{
					this.childNodes = new ArrayList<Node>();
					this.childNodes.add(node);
				}
			}
		}
	}

	@Override
	public void delChild(Node node) {
		// TODO Auto-generated method stub
		if (node != null) {
			if(this.childNodes != null){
			   this.childNodes.remove(node);
			}
		}
	}

	@Override
	public boolean hasChild() {
		// TODO Auto-generated method stub
		return (this.childNodes != null) ? (this.childNodes.size() > 0) : false;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public String getNodeLength() {
		return nodeLength;
	}

	public void setNodeLength(String nodeLength) {
		this.nodeLength = nodeLength;
	}

	public String getNodeScale() {
		return nodeScale;
	}

	public void setNodeScale(String nodeScale) {
		this.nodeScale = nodeScale;
	}

	public String getNodeAlias() {
		return nodeAlias;
	}

	public void setNodeAlias(String nodeAlias) {
		this.nodeAlias = nodeAlias;
	}


}
