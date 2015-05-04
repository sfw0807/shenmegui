package com.dc.esb.servicegov.resource.metadataNode;

import java.util.List;

public interface IMetadataNode {
	
	public void addChild(int position, IMetadataNode metadataNode);

	public void setNodeID(String id);

	public String getNodeID();

	public void setNodeType(Type type);

	public Type getNodeType();

	public void setProperty(IMetadataNodeAttribute attribute);

	public IMetadataNodeAttribute getProperty();

	public void setMetadataID(String metadataID);
	
	public String getResourceid();

	public String getMetadataID();

	public boolean hasAttribute(String name);

	public boolean hasAttribute();

	public boolean hasChild();

	public int length();

	public void remove(String nodeID);

	public void remove(IMetadataNode node);

	public void addChild(IMetadataNode node);
	
	public IMetadataNode getChild(String name);
	
	public List<IMetadataNode> getChild();
	
	public boolean hasChild(String name);
	
	public IMetadataNode getMetadataNode(String xpath);
	
	public boolean getXpathChild(String xpath);
	
	public IMetadataNode getNodeByPath(String xpath);
	
	public void setParentNode(IMetadataNode parentNode) ;
	public IMetadataNode getParentNode() ;

	public void setNodeExpresion(String nodeExpresion);
	public String getNodeExpresion();
	
	public void setNodeIsheader(String isHeader);
	public String getNodeIsHeader();
	
	public void setInterfaceScale(String interfaceScale);
	public String getInterfaceScale();
	
	public void setInterfaceLength(String interfaceLength);
	public String getInterfaceLength();

	public enum Type {
		atom, struction, array,unfixheader;
	}

}
