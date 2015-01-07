package com.dc.esb.servicegov.refactoring.resource.metadataNode;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;


public class MetadataNodeAttribute implements IMetadataNodeAttribute {

	private Map<String, Map<String, String>> map = new LinkedHashMap<String, Map<String, String>>();

	private Properties property = new Properties();

	public boolean containsKey(String key) {
		if (property != null)
			return this.property.containsKey(key);
		return false;
	}

	public String getProperty(String key) {
		if (property != null)
			return property.getProperty(key);
		return null;
	}

	public boolean isEmpty() {
		if (property == null) {
			return true;
		}
		return property.isEmpty();
	}

	public void setProperty(String key, String value) {
		property.put(key, value);
	}

	public void setAttributeProperty(String key, Map<String, String> value) {
		if (map == null)
			map = new LinkedHashMap<String, Map<String, String>>();
		map.put(key, value);
	}

	public Map<String, String> getAttributeProperty(String key) {
		return map.get(key);
	}

	public Map<String, Map<String, String>> getAttributeMap() {
		return map;
	}

	public Properties getProperty() {
		return this.property;
	}

	public Enumeration<?> getKeys() {
		if (property != null)
			return property.keys();
		return null;
	}

	@Override
	public void remove(String key) {
		if(property.containsKey(key)){
			property.remove(key);
		}	
	}

}
