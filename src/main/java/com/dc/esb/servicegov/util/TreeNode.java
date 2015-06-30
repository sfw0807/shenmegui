package com.dc.esb.servicegov.util;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	private String id;
	private String text;
	private String click;
	
	private List<TreeNode> children = new ArrayList<TreeNode>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}
	
}
