package com.dc.esb.servicegov.resource.metadataNode;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

public interface IMetadataNodeAttribute {
	
	public String getProperty(String key);

	public void setAttributeProperty(String key, Map<String, String> value);

	public Map<String,String> getAttributeProperty(String key);
		
	public void setProperty(String key, String value);

	public Properties getProperty();
	
	public boolean containsKey(String key);
	
	public void remove(String key);
	
	public Enumeration<?> getKeys();
	public boolean isEmpty();

	public Map<String, Map<String, String>> getAttributeMap();

}
