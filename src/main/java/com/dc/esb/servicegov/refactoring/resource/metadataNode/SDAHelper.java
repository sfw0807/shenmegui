package com.dc.esb.servicegov.refactoring.resource.metadataNode;

import com.dc.esb.servicegov.refactoring.util.SDAConstants;


public class SDAHelper {
	
	private static SDAHelper sdaHelper;
	
	private SDAHelper(){
		
	}
	
	
	public static SDAHelper getInstance() {
		if(null == sdaHelper){
			sdaHelper = new SDAHelper();
		}
		return sdaHelper;
	}
	
	public MetadataNode getRequestNode(MetadataNode sda){
		return (MetadataNode) sda.getChild(SDAConstants.REQUEST);
	}
	
	public MetadataNode getResponseNode(MetadataNode sda) {
		return (MetadataNode) sda.getChild(SDAConstants.RESPONSE);
	}

}
