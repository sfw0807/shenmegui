package com.dc.esb.servicegov.refactoring.resource.metadataNode;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MetadataNode implements IMetadataNode {

	private String metadataID, nodeID,nodeName,nType,nodeRequire,nodeRemark,nodeExpresion,nodeIsheader,interfaceScale,interfaceLength;
	private Type type = Type.atom;
	private IMetadataNodeAttribute attribute;
	private IMetadataNode parentNode = null;

	public IMetadataNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(IMetadataNode parentNode) {
		this.parentNode = parentNode;
	}

	public String getResourceid() {
		return reourceid;
	}

	public void setResourceid(String reourceid) {
		this.reourceid = reourceid;
	}

	private List<IMetadataNode> childs = null;
	private String reourceid = null;

	public String getMetadataID() {
		if (hasAttribute() && hasAttribute("metadataid")) {
			this.metadataID = getProperty().getProperty("metadataid");
		}
		return this.metadataID;
	}

	public String getNodeID() {
		return this.nodeID;
	}

	public Type getNodeType() {
		return this.type;
	}

	public IMetadataNodeAttribute getProperty() {
		return this.attribute;
	}

	public void setMetadataID(String metadataID) {
//		if (hasAttribute())
//			getProperty().setProperty("metadataid", metadataID);
//		else
			
			this.metadataID = metadataID;
	}

	public void setNodeID(String id) {
		this.nodeID = id;
	}

	public void setNodeType(Type type) {
		this.type = type;
	}

	public void setProperty(IMetadataNodeAttribute attribute) {
		this.attribute = attribute;
	}

	public void addChild(IMetadataNode node) {
		if (this.childs == null)
			this.childs = new ArrayList<IMetadataNode>();
		
		if(node!=null){
//			if (!this.hasChild(node.getNodeID())) {
				node.setParentNode(this);
				this.childs.add(node);
//			}
		}
	}

	public void remove(IMetadataNode node) {
		if (this.childs != null)
			this.childs.remove(node);
	}

	public void remove(String nodeID) {
		for (IMetadataNode node : this.childs) {
			if (nodeID.equals(node.getNodeID())) {
				this.childs.remove(node);
			}
		}
	}

	public int length() {
		if (this.childs != null)
			return this.childs.size();
		return 0;
	}

	public boolean hasChild() {
		return this.childs != null ? this.childs.size() > 0 : false;
	}

	public boolean hasAttribute() {

		return this.attribute != null && !this.attribute.isEmpty();
	}

	public boolean hasAttribute(String name) {
		return this.attribute.containsKey(name);
	}

	public boolean hasChild(String name) {
		if (hasChild()) {
			for (IMetadataNode node : this.childs) {
				if (node.getNodeID().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}

	public IMetadataNode getChild(String name) {
		if (hasChild()) {
			for (IMetadataNode node : this.childs) {
				if (node.getNodeID().equals(name)) {
					return node;
				}
			}
		}
		return null;
	}

	public List<IMetadataNode> getChild() {
		return this.childs;
	}

	public boolean getXpathChild(String xpath) {
		String[] path = xpath.split("\\.");
		IMetadataNode node = null;
		for (int i = 0; i < path.length; i++) {
			if (node == null) {
				if (this.hasChild(path[i])) {
					node = this.getChild(path[i]);
				} else
					return false;
			} else {
				if (node.hasChild(path[i])) {
					node = node.getChild(path[i]);
				} else {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 搜索当前节点的子节点，xpath不包含但前节点路径
	 */
	public IMetadataNode getMetadataNode(String xpath) {
		String[] path = xpath.split("\\.");
		IMetadataNode node = null;
		for (int i = 0; i < path.length; i++) {
			if (node == null) {
				if (this.hasChild(path[i])) {
					node = this.getChild(path[i]);
				}
			} else {
				if (node.hasChild(path[i])) {
					node = node.getChild(path[i]);
				}
			}
		}
		return node;
	}

	/**
	 * 搜索当前节点的子节点，xpath包含但前节点路径
	 */
	public IMetadataNode getNodeByPath(String xpath) {
		String[] paths = xpath.split("\\.");
		IMetadataNode node = null;
		IMetadataNode searchNode = this;
		for (String nodeName : paths) {
			if (nodeName.equals(searchNode.getNodeID())) {
				node = searchNode;
				continue;
			}
			searchNode = searchNode.getMetadataNode(nodeName);
			if (searchNode != null) {
				node = searchNode;
				continue;
			} else {
				node = null;
			}
		}

		return node;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.nodeID + "(" + reourceid + ")" + ":");
		buffer.append("(");
//		if (metadataID != null) {
//			buffer.append("metadataid=" + this.metadataID + "#");
//		}
		if (this.hasAttribute()) {
			for (Enumeration<?> e = this.attribute.getKeys(); e
					.hasMoreElements();) {
				String key = (String) e.nextElement();
				String value = this.attribute.getProperty(key);
				buffer.append(key);
				buffer.append("=");
				buffer.append(value + "#");
			}
		}
		buffer.append(")\n");
		if (this.hasChild()) {
			buffer.append("  " + "{\n");
			for (IMetadataNode node : this.childs) {
				buffer.append("    " + node.toString());
			}
			buffer.append("  " + "}\n");
		}
		return buffer.toString();
	}

	private void planeChildData(Map<String, IMetadataNode> map,
			MetadataNode node) {
		if (node.getResourceid() != null) {
			map.put(node.getResourceid(), node);
			if (node.hasChild()) {
				for (IMetadataNode cnode : node.getChild()) {
					planeChildData(map, (MetadataNode) cnode);
				}
			}
		}
	}

	// 元结构中的节点唯一标识扁平数据
	public Map<String, IMetadataNode> planeData() {
		Map<String, IMetadataNode> data = new LinkedHashMap<String, IMetadataNode>();
		if (this.reourceid != null) {
			planeChildData(data, this);
		}
		return data;
	}

	private void planeChild(Map<String, IMetadataNode> map, MetadataNode node) {
		if (node.getMetadataID() != null) {
			map.put(node.getMetadataID(), node);
		}
		if (node.hasChild()) {
			for (IMetadataNode cnode : node.getChild()) {
				planeChild(map, (MetadataNode) cnode);
			}
		}
	}

	// 元结构中的节点唯一标识扁平数据
	public Map<String, IMetadataNode> plane() {
		Map<String, IMetadataNode> data = new LinkedHashMap<String, IMetadataNode>();
		planeChild(data, this);
		return data;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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


	public String getNType() {
		return nType;
	}

	public void setNType(String type) {
		nType = type;
	}

	public String getNodeExpresion() {
		return this.nodeExpresion;
	}

	public void setNodeExpresion(String nodeExpresion) {
		this.nodeExpresion = nodeExpresion;
	}

	public String getNodeIsHeader() {
		return this.nodeIsheader;
	}

	public void setNodeIsheader(String isHeader) {
		this.nodeIsheader = isHeader;
	}

	public String getInterfaceLength() {
		return this.interfaceLength;
	}


	public void setInterfaceLength(String interfaceLength) {
		this.interfaceLength = interfaceLength;
	}

	public void setInterfaceScale(String interfaceScale) {
		this.interfaceScale = interfaceScale;
	}

	public String getInterfaceScale() {
		return this.interfaceScale;
	}

	@Override
	public void addChild(int position, IMetadataNode node) {
		if (this.childs == null)
			this.childs = new ArrayList<IMetadataNode>();
		
		if(node!=null){
			if (!this.hasChild(node.getNodeID())) {
				node.setParentNode(this);
				this.childs.add(position, node);
			}
		}
		
	}

}
