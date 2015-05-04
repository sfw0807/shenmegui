package com.dc.esb.servicegov.resource.node;





public interface INode {
	
	public boolean hasChild();
	public void appendChild(Node node);
	public void delChild(Node node);
	
}
