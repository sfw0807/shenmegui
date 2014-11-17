package com.dc.esb.servicegov.refactoring.resource.metadataNode;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;



public class MetadataStructonHelper {

	private static MetadataStructonHelper helper;

	private MetadataStructonHelper() {
	}

	public static MetadataStructonHelper getInstance() {
		if (helper == null) {
			synchronized (MetadataStructonHelper.class) {
				helper = new MetadataStructonHelper();
			}
		}
		return helper;
	}

	public MetadataNode merger(MetadataNode sourceNode, MetadataNode targetNode) {
		MetadataNode node = this.merger(sourceNode, targetNode, null);
		this.mergerNode(sourceNode, node);
		return node;
	}

	/**
	 * 元结构合并方法
	 * 
	 * @param sourceNode
	 *            源元结构
	 * @param targetNode
	 *            目标元结构
	 * @return 合并后的元结构
	 */
	private MetadataNode merger(MetadataNode sourceNode,
			MetadataNode targetNode, String parentPath) {
		if (sourceNode.hasChild()) {
			List<IMetadataNode> children = sourceNode.getChild();
			for (IMetadataNode node : children) {
				String path = "";
				if (parentPath == null || parentPath.equals("")){
					path = node.getNodeID();
					parentPath = targetNode.getNodeID();
				}
				else
					path = parentPath + "." + node.getNodeID();
				IMetadataNode tNode = targetNode.getNodeByPath(path);
				if (tNode != null) {
					this.mergerNode(node, tNode);
					//move this statment to here for avoiding the same reference be deleted
					targetNode = this.merger((MetadataNode) node, targetNode, path);
				} else {
					targetNode.getNodeByPath(parentPath).addChild(node);
				}
			}
		}
		return targetNode;
	}

	/**
	 * 元结点的合并
	 * 
	 * @param sourceNode
	 * @param targetNode
	 * @return
	 */
	private void mergerNode(IMetadataNode sourceNode, IMetadataNode targetNode) {
		String metadataID = sourceNode.getMetadataID();
		targetNode.setMetadataID(metadataID);
		String nodeId = sourceNode.getNodeID();
		targetNode.setNodeID(nodeId);
		Properties sPro = null;
		if (sourceNode.hasAttribute()) {
			sPro = sourceNode.getProperty().getProperty();
			Properties tPro = null;
			if (targetNode.hasAttribute()) {
				tPro = targetNode.getProperty().getProperty();
			} else {
				MetadataNodeAttribute attr = new MetadataNodeAttribute();
				targetNode.setProperty(attr);
				tPro = targetNode.getProperty().getProperty();
			}
			for (Iterator<?> iterator = sPro.keySet().iterator(); iterator
					.hasNext();) {
				String key = (String) iterator.next();
				if (tPro.containsKey(key)) {
					tPro.remove(key);
					tPro.put(key, sPro.get(key));
				} else {
					tPro.put(key, sPro.get(key));
				}
			}
		}
	}
}
