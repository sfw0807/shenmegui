package com.dc.esb.servicegov.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dc.esb.servicegov.entity.HeadSDA;

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
