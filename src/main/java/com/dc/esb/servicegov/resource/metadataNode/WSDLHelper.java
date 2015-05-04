package com.dc.esb.servicegov.resource.metadataNode;




public class WSDLHelper {
	private static WSDLHelper wsdlHelper;
	
	public static WSDLHelper getInstance(){
		if(wsdlHelper == null){
			wsdlHelper = new WSDLHelper();
		}
		return wsdlHelper;
	}
	
	
	public String getHost(String serviceID) {
		
		return "http://esb.spdbbiz.com";
	}

}
