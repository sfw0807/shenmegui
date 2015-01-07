package com.dc.esb.servicegov.refactoring.resource.metadataNode;


public class SoapHelper {
	
	private static SoapHelper soapHelper ;
	
	private SoapHelper(){
		
	}
	
	public static SoapHelper getInstance(){
		if(null == soapHelper){
			soapHelper = new SoapHelper();
		}
		return soapHelper;
	}
	
	/**
	 * 返回一个符合SOAP协议的简单对象模板节点。
	 * @return
	 */
	public  MetadataNode getSoapTemplate() {
		MetadataNode root = new MetadataNode();
		root.setNodeID(SoapConstants.ELEM_ENVELOPE);
		MetadataNode head = new MetadataNode();
		head.setNodeID(SoapConstants.ELEM_HEADER);
		root.addChild(head);
		MetadataNode Body = new MetadataNode();
		Body.setNodeID(SoapConstants.ELEM_BODY);
		root.addChild(Body);
		return root;
	}
	

}
