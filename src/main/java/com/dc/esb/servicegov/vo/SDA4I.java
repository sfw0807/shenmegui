package com.dc.esb.servicegov.vo;

import java.util.ArrayList;
import java.util.List;

import com.dc.esb.servicegov.entity.SDANode4I;
import com.dc.esb.servicegov.entity.SDANodeProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-27
 * Time: 上午11:31
 */
public class SDA4I {
	
	private List<SDANodeProperty> properties;
    private SDANode4I value;
    private List<SDA4I> childNode;
    private String xpath;
    
    public void addChild(SDA4I sda){
    	if(null == childNode){
    		childNode = new ArrayList<SDA4I>();
    	}
    	childNode.add(sda);
    }
    
    public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public List<SDANodeProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<SDANodeProperty> properties) {
        this.properties = properties;
    }
    
    public SDANode4I getValue() {
        return value;
    }

    public void setValue(SDANode4I value) {
        this.value = value;
    }

    public List<SDA4I> getChildNode() {
        return childNode;
    }

    public void setChildNode(List<SDA4I> childNode) {
        this.childNode = childNode;
    }
}
