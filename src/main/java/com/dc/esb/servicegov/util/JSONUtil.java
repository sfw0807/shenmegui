package com.dc.esb.servicegov.util;

import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

public class JSONUtil {
	public static JsonConfig genderJsonConfig(String[] names){
		JsonConfig config = new JsonConfig();  //过滤属性  
	    config.setIgnoreDefaultExcludes(false);       
	    config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
	    final  String[] fnames = names;
	    config.setJsonPropertyFilter( new PropertyFilter(){ 
	    	public boolean apply(Object source/* 属性的拥有者 */ , String name /*属性名字*/ , Object value/* 属性值 */ ){ 
	    		if(fnames != null && fnames.length > 0){
	    			for(String fname : fnames){
	    				if(name.equals(fname)){
	    					return false;
	    				}
	    			}
	    		}
	    		
	    		return true;
	    	} 
	    	}); 
	    return config;
	}
}
