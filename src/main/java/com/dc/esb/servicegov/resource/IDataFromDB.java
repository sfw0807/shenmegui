/*
 * Copyright 2012 digital china financial software Inc.
 * All rights reserved.
 * project name: ServiceGovernance_PatchLine      
 * version:  InteractiveFrame1.0          
 *---------------------------------------------------
 * author: li.jun
 * date:   2012-2-22
 * note:          
 *
 *---------------------------------------------------
 * modificator:   
 * date:          
 * note:          
 *
 */
/**
 * IDataFromDB.java
 */
package com.dc.esb.servicegov.resource;

import com.dc.esb.servicegov.resource.metadataNode.MetadataNode;

/**
 *功能:
 * 
 * 
 */
public interface IDataFromDB {
	/**
	 *功能:从数据库获取资源数据的类型：接口，服务
	 */
	public enum ResourceType {
		INTERFACE, SERVICE, HEAD
	}
	/**
	 * 
	 * 功能:获取资源数据并转换为元数据结构节点。
	 * @param resourceName
	 * @param type
	 * @return
	 * MetadataNode 
	 */
	public MetadataNode getNodeFromDB(String resourceName, ResourceType type);

}
