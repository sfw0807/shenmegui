package com.dc.esb.servicegov.refactoring.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dc.esb.servicegov.refactoring.entity.SDA;
import com.dc.esb.servicegov.refactoring.entity.SDAHistory;

public class SDAHistoryVO implements Serializable{
	private static final long serialVersionUID = -7345182986327730529L;
	private SDAHistory value;
    private List<SDAHistoryVO> childNode;
    private String xpath;
    
    public void addChild(SDAHistoryVO sdaVO){
    	if(null == childNode){
    		childNode = new ArrayList<SDAHistoryVO>();
    	}
    	childNode.add(sdaVO);
    }

	public SDAHistory getValue() {
		return value;
	}

	public void setValue(SDAHistory value) {
		this.value = value;
	}

	public List<SDAHistoryVO> getChildNode() {
		return childNode;
	}

	public void setChildNode(List<SDAHistoryVO> childNode) {
		this.childNode = childNode;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	} 
}
