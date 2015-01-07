/*
 * <p>Title: :Attr.java </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: digitalchina.Ltd</p>
 * @author wu.bo
 * Created :2011-5-18
 * @version 1.0
 * 修改历史:
 * <作者> <时间(yyyy/mm/dd)>  <修改内容>  <版本号>
 */

package com.dc.esb.servicegov.refactoring.resource.metadataNode;
/**
 * <p>描述:
 * <li>
 * </li>
 * </p>
 * @author wu.bo
 */
public class Attr {
	private String resourceid,structId,propertyName,propertyValue,propertyAlias,remark;
	private int propertyIndex;
	public String getResourceid() {
		return resourceid;
	}

	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}

	public String getStructId() {
		return structId;
	}

	public void setStructId(String structId) {
		this.structId = structId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getPropertyAlias() {
		return propertyAlias;
	}

	public void setPropertyAlias(String propertyAlias) {
		this.propertyAlias = propertyAlias;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setPropertyIndex(int propertyIndex) {
		this.propertyIndex = propertyIndex;
	}

	public int getPropertyIndex() {
		return propertyIndex;
	}
}


