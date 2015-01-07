package com.dc.esb.servicegov.refactoring.vo;

import java.util.ArrayList;
import java.util.List;

import com.dc.esb.servicegov.refactoring.entity.IDA;

public class IDAVO {

	private IDA value;
	private List<IDAVO> childNodes;
	private String xpath;

	public void addChild(IDAVO child) {
		if (childNodes == null) {
			childNodes = new ArrayList<IDAVO>();
		}
		childNodes.add(child);
	}
	
	public IDA getValue() {
		return value;
	}

	public void setValue(IDA value) {
		this.value = value;
	}

	public List<IDAVO> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<IDAVO> childNodes) {
		this.childNodes = childNodes;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

}
