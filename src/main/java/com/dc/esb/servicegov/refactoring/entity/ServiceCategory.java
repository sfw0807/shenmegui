package com.dc.esb.servicegov.refactoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SERVICE_CATEGORY")
public class ServiceCategory implements Serializable{
	
	private static final long serialVersionUID = 69594125452445721l;
	
	@Id
	@Column(name="category_id")
	private String categoryId;
	@Column(name="category_name")
	private String categoryName;
	@Column(name="parent_id")
	private String parentId;
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
