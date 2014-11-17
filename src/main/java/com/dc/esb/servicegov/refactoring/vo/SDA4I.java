package com.dc.esb.servicegov.refactoring.vo;

import java.util.ArrayList;
import java.util.List;

import com.dc.esb.servicegov.entity.SDANodeProperty;
import com.dc.esb.servicegov.refactoring.entity.IDA;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-27
 * Time: 上午11:31
 */
public class SDA4I {
	
	private List<SDANodeProperty> properties;
    private IDA value;
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
    
    public IDA getValue() {
        return value;
    }

    public void setValue(IDA value) {
        this.value = value;
    }

    public List<SDA4I> getChildNode() {
        return childNode;
    }

    public void setChildNode(List<SDA4I> childNode) {
        this.childNode = childNode;
    }
}
