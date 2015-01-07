package com.dc.esb.servicegov.refactoring.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dc.esb.servicegov.refactoring.entity.HeadSDA;
import com.dc.esb.servicegov.refactoring.entity.SDA;

public class HeadSDAVO implements Serializable{
	private static final long serialVersionUID = -8532079950395648627L;
	private HeadSDA value;
    private List<HeadSDAVO> childNode;
    private String xpath;
    
    public void addChild(HeadSDAVO sdaVO){
    	if(null == childNode){
    		childNode = new ArrayList<HeadSDAVO>();
    	}
    	childNode.add(sdaVO);
    }

	public HeadSDA getValue() {
		return value;
	}

	public void setValue(HeadSDA value) {
		this.value = value;
	}

	public List<HeadSDAVO> getChildNode() {
		return childNode;
	}

	public void setChildNode(List<HeadSDAVO> childNode) {
		this.childNode = childNode;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	} 
}
