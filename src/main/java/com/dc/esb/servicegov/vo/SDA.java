package com.dc.esb.servicegov.vo;

import com.dc.esb.servicegov.entity.SDANode;
import com.dc.esb.servicegov.entity.SDANodeProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-27
 * Time: 上午11:31
 */
public class SDA {
    private List<SDANodeProperty> properties;
    private SDANode value;
    private List<SDA> childNode;
    private String xpath;
    
    public void addChild(SDA sda){
    	if(null == childNode){
    		childNode = new ArrayList<SDA>();
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

    public SDANode getValue() {
        return value;
    }

    public void setValue(SDANode value) {
        this.value = value;
    }

    public List<SDA> getChildNode() {
        return childNode;
    }

    public void setChildNode(List<SDA> childNode) {
        this.childNode = childNode;
    }
}
