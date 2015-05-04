package com.dc.esb.servicegov.resource.metadataNode;

import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;


public class MetadataNodeHelper {

	private static MetadataNodeHelper metadataHelper;

	private MetadataNodeHelper() {

	}

	public static IMetadataNode cloneNode(IMetadataNode srcNode) {
		IMetadataNode targetNode = new MetadataNode();
		targetNode.setNodeID(srcNode.getNodeID());
		targetNode.setMetadataID(srcNode.getMetadataID());
		targetNode.setProperty(srcNode.getProperty());
		if (srcNode.hasChild()) {
			for (IMetadataNode childNode : srcNode.getChild()) {
				targetNode.addChild(cloneNode(childNode));
			}
		}
		return targetNode;
	}

	public static MetadataNodeHelper getInstance() {
		if (null == metadataHelper) {
			metadataHelper = new MetadataNodeHelper();
		}
		return metadataHelper;
	}

	public static Document MetadataNode2Document(IMetadataNode node) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(node.getNodeID());
		setFieldAttribute(node, root);
		appendChildNodeToElement(node, root);
		return document;
	}

	public static Document MetadataNode2Document(IMetadataNode node,
			List<Namespace> namespaces) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(node.getNodeID());
		for (Namespace namespace : namespaces) {
			root.add(namespace);
		}
		setFieldAttribute(node, root);
		appendChildNodeToElement(node, root);
		return document;
	}

	public static Document MetadataNode2Document(IMetadataNode node,
			List<Namespace> namespaces, List<String> attrExclude) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(node.getNodeID());
		if (null != namespaces) {
			for (Namespace namespace : namespaces) {
				root.add(namespace);
			}
		}
		setFieldAttribute(node, root, attrExclude);
		appendChildNodeToElement(node, root, attrExclude);
		return document;
	}

	public static Document MetadataNode2DocumentWithInclude(IMetadataNode node,
			List<Namespace> namespaces, List<String> attrInclude) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(node.getNodeID());
		if (null != namespaces) {
			for (Namespace namespace : namespaces) {
				root.add(namespace);
			}
		}
		setFieldAttributeWithInculde(node, root, attrInclude);
		appendChildNodeToElementWithInclude(node, root, attrInclude);
		return document;
	}

	public static void appendChildNodeToElement(IMetadataNode metadataNode,
			Element element, List<String> attrExclude) {
		for (IMetadataNode cNode : metadataNode.getChild()) {
			Element cele = element.addElement(cNode.getNodeID());
			setFieldAttribute(cNode, cele, attrExclude);
			if (cNode.hasChild()) {
				if (cNode.hasAttribute() && cNode.hasAttribute("type")) {
					String is_struct = cNode.getProperty().containsKey(
							"is_struct") ? cNode.getProperty().getProperty(
							"is_struct") : "false";
					cele.addAttribute("is_struct", is_struct);
				}
				appendChildNodeToElement(cNode, cele, attrExclude);
			}
		}
	}

	public static void appendChildNodeToElementWithInclude(
			IMetadataNode metadataNode, Element element, List<String> include) {
		for (IMetadataNode cNode : metadataNode.getChild()) {
			Element cele = element.addElement(cNode.getNodeID());
			setFieldAttributeWithInculde(cNode, cele, include);
			if (cNode.hasChild()) {
				IMetadataNodeAttribute attr = cNode.getProperty();
				if(null != attr){
					if (cNode.hasAttribute("type")) {
						if( attr.getProperty("type").equalsIgnoreCase("struct")){
							attr.remove("type");
							attr.setProperty("type", "array");
						}
						if (attr.getProperty("type").equals("array")) {
							String is_struct = cNode.getProperty().containsKey(
									"is_struct") ? cNode.getProperty().getProperty(
									"is_struct") : "false";
							cele.addAttribute("is_struct", is_struct);
						}
						if("".equals(attr.getProperty("type"))){
							if(cNode.hasAttribute("remark")){
								if(attr.getProperty("remark")!=null 
										&& attr.getProperty("remark").toLowerCase().startsWith("start")){
									cele.addAttribute("is_struct", "true");
								}
							}
						}
					}
				}
				
				appendChildNodeToElementWithInclude(cNode, cele, include);
			}
		}
	}

	public static void appendChildNodeToElement(IMetadataNode metadataNode,
			Element element) {
		for (IMetadataNode cNode : metadataNode.getChild()) {
			Element cele = element.addElement(cNode.getNodeID());
			setFieldAttribute(cNode, cele);
			if (cNode.hasChild()) {
				if (cNode.hasAttribute() && cNode.hasAttribute("type")) {
					String is_struct = cNode.getProperty().containsKey(
							"is_struct") ? cNode.getProperty().getProperty(
							"is_struct") : "false";
					cele.addAttribute("is_struct", is_struct);
				}
				appendChildNodeToElement(cNode, cele);
			}
		}
	}

	public static void setFieldAttribute(IMetadataNode node, Element element) {
		if (node.hasAttribute()) {
			Properties pro = node.getProperty().getProperty();
			for (Object objectProperty : pro.keySet()) {
				String key = (String) objectProperty;
				String propertyValue = (String) pro.get(key);
				if ((propertyValue != null) && (!propertyValue.equals(""))) {
					element.addAttribute((String) objectProperty,
							(String) pro.get(objectProperty));
				}
			}
		}
	}

	public static void setFieldAttribute(IMetadataNode node, Element element,
			List<String> exclude) {
		if (node.hasAttribute()) {
			Properties pro = node.getProperty().getProperty();
			for (Object objectProperty : pro.keySet()) {
				String key = (String) objectProperty;
				if (!exclude.contains(key)) {
					String propertyValue = (String) pro.get(key);
					if ((propertyValue != null) && (!propertyValue.equals(""))) {
						element.addAttribute((String) objectProperty,
								(String) pro.get(objectProperty));
					}
				}
			}
		}
	}

	public static void setFieldAttributeWithInculde(IMetadataNode node,
			Element element, List<String> include) {
		if (node.hasAttribute()) {
			Properties pro = node.getProperty().getProperty();
			String value = (String) pro.get("type");
			if(null != value){
				if(value.equalsIgnoreCase("struct")){
					pro.remove("type");
					pro.setProperty("type", "array");
				}
			}
			for (Object objectProperty : pro.keySet()) {
				String key = (String) objectProperty;
				if (include.contains(key + "=" + (String) pro.get(key))) {
					element.addAttribute((String) objectProperty,
							(String) pro.get(objectProperty));
				} else if (include.contains(key)) {
					String propertyValue = (String) pro.get(key);
					if ((propertyValue != null) && (!propertyValue.equals(""))) {
						element.addAttribute((String) objectProperty,
								(String) pro.get(objectProperty));
					}
				}
			}
		}
	}

	

	public static void copyChild(IMetadataNode srcNode, IMetadataNode destNode) {
		if (null != srcNode) {
			if (srcNode.hasChild()) {
				for (IMetadataNode childNode : srcNode.getChild()) {
					destNode.addChild(childNode);
				}
			}
		}
	}

}
