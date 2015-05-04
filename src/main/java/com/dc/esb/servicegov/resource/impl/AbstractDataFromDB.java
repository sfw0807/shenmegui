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
 * AbstractDataFromDB.java
 */
package com.dc.esb.servicegov.resource.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.dao.impl.HeadSDADAOImpl;
import com.dc.esb.servicegov.dao.impl.IdaDAOImpl;
import com.dc.esb.servicegov.dao.impl.IdaPROPDAOImpl;
import com.dc.esb.servicegov.dao.impl.SDADAOImpl;
import com.dc.esb.servicegov.dao.impl.SdaPROPDAOImpl;
import com.dc.esb.servicegov.dao.impl.ServiceHeadRelateDAOImpl;
import com.dc.esb.servicegov.resource.IDataFromDB;
import com.dc.esb.servicegov.resource.metadataNode.Attr;
import com.dc.esb.servicegov.resource.metadataNode.IMetadataNodeAttribute;
import com.dc.esb.servicegov.resource.metadataNode.MetadataNode;
import com.dc.esb.servicegov.resource.metadataNode.MetadataNodeAttribute;
import com.dc.esb.servicegov.resource.metadataNode.IMetadataNode.Type;


/**
 * 功能:
 * 
 * @since : jdk1.6
 * 
 */
@Service
public abstract class AbstractDataFromDB implements IDataFromDB {
	protected static final Log log = LogFactory
			.getLog(AbstractDataFromDB.class);
	List<Map<String,String>> dataMap = new ArrayList<Map<String,String>>();
	List<Attr> allIdaProp = new ArrayList<Attr>();
	
	@Autowired
	private SDADAOImpl sdaDAO;
	@Autowired
	private IdaDAOImpl idaDAO;
	@Autowired
	private IdaPROPDAOImpl idaPropDAO;
	@SuppressWarnings("unused")
	@Autowired
	private SdaPROPDAOImpl sdaPropDAO;
	@Autowired
	private ServiceHeadRelateDAOImpl serviceHeadRelateDAO;
	@Autowired
	private HeadSDADAOImpl headSDADAO;
	
	/**
	 * 获取业务报文头SDA
	 * @param serviceid
	 * @param operationid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected MetadataNode getHeadData(String serviceid) {
		String sheadId = serviceHeadRelateDAO.getHeadByServiceId(serviceid);
		log.info("开始导出服务头[" + sheadId + "]SDA");
		// 获取所有HeadSda的Map
		dataMap.clear();
		dataMap = headSDADAO.getAllHeadSDAMapBySheadId(sheadId);
		MetadataNode serviceNodes = new MetadataNode();
		if(sheadId == null){
			return serviceNodes;
		}
		String id = this.getTopResourceIdByMap();
		getHeadSDAAllChild(id, sheadId, serviceNodes, ResourceType.HEAD);
		log.info("完成导出服务头[" + sheadId + "]SDA");
		return serviceNodes;
	}
	
	/**
	 * 获取服务SDA
	 * @param serviceid
	 * @param operationid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected MetadataNode getServiceData(String serviceid, String operationid) {
		// 获取所有服务的sda
		dataMap.clear();
		dataMap = sdaDAO.getAllSDAMapByServiceIdAndOperationId(serviceid, operationid);
		log.info("开始导出操作[" + operationid + "]SDA");
//		String id = sdaDAO.getTopResourceId(serviceid, operationid);
		String id = this.getTopResourceIdByMap();
		MetadataNode serviceNodes = new MetadataNode();
		getAllChild(id, serviceid, serviceNodes, ResourceType.SERVICE);
		log.info("完成导出操作[" + operationid + "]SDA");
		return serviceNodes;
	}
	
	/**
	 * 获取服务HEADSDA的所有数据
	 * @param nodeId
	 * @param structid
	 * @param nodes
	 * @param type
	 */
	protected void getHeadSDAAllChild(String nodeId, String structid,
			MetadataNode nodes, ResourceType type) {
		String childNodeName = this.getStructNameByMap(nodeId,null,null);
		if (hasChildrenByMap(nodeId,null,null)) {
			List<String> cNode = this.getChildrenByMap(nodeId,null,null);
			if (childNodeName != null && !"".equals(childNodeName)) {

				if ((null == nodes.getNodeType())
						|| (!nodes.getNodeType().equals("array"))) {
					nodes.setNodeType(Type.struction);
				}

				setNodeAttribute(this.setNodeInfo(nodeId, type), nodes);
			}
			nodes.setNodeID(childNodeName);
			nodes.setResourceid(nodeId);
			for (String id : cNode) {
				MetadataNode node = new MetadataNode();
				getHeadSDAAllChild(id, structid, node, type);
				nodes.addChild(node);
			}
		} else {
			nodes.setNodeType(Type.atom);
			nodes.setNodeID(childNodeName);
			nodes.setResourceid(nodeId);
			if (childNodeName != null && !"".equals(childNodeName)) {
				setNodeAttribute(this.setNodeInfo(nodeId, type), nodes);
			}
			setNodeAttribute(this.setNodeInfo(nodeId, type), nodes);
		}
	}

	/**
	 * 获取服务SDA的所有数据
	 * @param nodeId
	 * @param structid
	 * @param nodes
	 * @param type
	 */
	protected void getAllChild(String nodeId, String structid,
			MetadataNode nodes, ResourceType type) {
		
		String childNodeName = this.getStructNameByMap(nodeId, structid, type);
		if (hasChildrenByMap(nodeId, structid, type)) {
			List<String> cNode = this.getChildrenByMap(nodeId, structid, type);
			if (childNodeName != null && !"".equals(childNodeName)) {

				if ((null == nodes.getNodeType())
						|| (!nodes.getNodeType().equals("array"))) {
					nodes.setNodeType(Type.struction);
				}

				setNodeAttribute(this.setNodeInfo(nodeId, type), nodes);
			}
			nodes.setNodeID(childNodeName);
			nodes.setResourceid(nodeId);
			for (String id : cNode) {
				MetadataNode node = new MetadataNode();
				getAllChild(id, structid, node, type);
				nodes.addChild(node);
			}
		} else {
			nodes.setNodeType(Type.atom);
			nodes.setNodeID(childNodeName);
			nodes.setResourceid(nodeId);
			if (childNodeName != null && !"".equals(childNodeName)) {
				setNodeAttribute(this.setNodeInfo(nodeId, type), nodes);
			}
		}	
	}
	

	protected List<Attr> setNodeInfo(String nodeId, ResourceType type) {
//		if(log.isInfoEnabled()){
//			log.info("get node info of ["+nodeId+" : "+type+"]");
//		}
		Map<String, String> info = this.getNodeInfoByMap(nodeId, type);
//		List<Attr> attr = getNodeAttr(nodeId, type);
		List<Attr> attr = this.getIdaPropListByIdaId(nodeId);
		if (info.containsKey("METADATAID")) {
			Attr att = new Attr();
			att.setPropertyName("metadataid");
			att.setPropertyValue(info.get("METADATAID"));
			attr.add(att);
		}
		
		if (info.containsKey("TYPE")) {
			Attr att = new Attr();
			att.setPropertyName("type");
			att.setPropertyValue(info.get("TYPE"));
			attr.add(att);
		}
		if (info.containsKey("LENGTH")) {
			Attr att = new Attr();
			att.setPropertyName("length");
			att.setPropertyValue(info.get("LENGTH"));
			attr.add(att);
		}
		if (info.containsKey("SCALE")) {
			Attr att = new Attr();
			att.setPropertyName("scale");
			att.setPropertyValue(info.get("SCALE"));
			attr.add(att);
		}

		if (info.containsKey("REQUIRED")) {
			Attr att = new Attr();
			att.setPropertyName("required");
			att.setPropertyValue(info.get("REQUIRED"));
			attr.add(att);
		}

		if (info.containsKey("REMARK")) {
			Attr att = new Attr();
			att.setPropertyName("remark");
			att.setPropertyValue(info.get("REMARK"));
			attr.add(att);
		}
		
		return attr;
	}

	// getInterfaceStructNodeInfo
	protected Map<String, String> getNodeInfo(String structId, ResourceType type) {
		if (structId == null)
			return null;
		Map<String, String> info =  null;
		if (type == ResourceType.SERVICE) {
			info = sdaDAO.getSDAByResourceid(structId);
		}  else if  (type == ResourceType.INTERFACE){
			info = idaDAO.getIDAByResourceid(structId);
		} else{
			info = headSDADAO.getHEADSDAByResourceid(structId);
		}
		return info;
	}

	protected void setNodeAttribute(List<Attr> attr, MetadataNode node) {
		if (attr != null && attr.size() > 0) {
			IMetadataNodeAttribute nodeAttr = new MetadataNodeAttribute();
			for (Attr attribute : attr) {
				String name = attribute.getPropertyName();
				String value = attribute.getPropertyValue() == null ? ""
						: attribute.getPropertyValue();
				nodeAttr.setProperty(name, value);
			}
			node.setProperty(nodeAttr);
		} 
	}
	
	public class MergerObject {
		private String resourceid;
		private List<MergerObject> supResourceList = new ArrayList<MergerObject>();;

		public String getResourceid() {
			return resourceid;
		}

		public void setResourceid(String resourceid) {
			this.resourceid = resourceid;
		}

		public List<MergerObject> getSupResourceList() {
			return supResourceList;
		}

		public void addSupResource(MergerObject obj) {
			this.supResourceList.add(obj);
		}

		public boolean hasChild() {
			return !this.supResourceList.isEmpty();
		}

		public MergerObject getMergerByPath(String path) {
			String[] routes = path.split("\\.");
			MergerObject search = this;
			MergerObject obj = null;
			for (String route : routes) {
				if (route.equals(this.resourceid)) {
					obj = search;
					continue;
				}
				obj = search.getChild(route);
				if (obj != null) {
					search = obj;
				} else {
					break;
				}
			}
			return obj;
		}

		private MergerObject getChild(String resourceid) {
			if (!this.supResourceList.isEmpty()) {
				for (MergerObject obj : this.supResourceList) {
					if (resourceid.equals(obj.getResourceid())) {
						return obj;
					}
				}
			} else {
				return null;
			}
			return null;
		}
	}

	
	protected String getStructNameByMap(String structId, String serviceid,
			ResourceType type) {
		for(Map<String,String> map: dataMap){
			if(structId.equals(map.get("id"))){
				return map.get("structId");
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected List<String> getChildrenByMap(String structId, String serviceid,
			ResourceType type) {
		if (structId == null) {
			return null;
		}
		List<String> childIds = new ArrayList<String>();
		for(Map<String,String> map: dataMap){
			if(structId.equals(map.get("parentId"))){
				childIds.add(map.get("id"));
			}
		}
		return childIds;
	}
	// getInterfaceStructNodeInfo
	protected Map<String, String> getNodeInfoByMap(String structId, ResourceType type) {
		if (structId == null)
			return null;
		for(Map<String,String> map: dataMap){
			if(structId.equals(map.get("id"))){
				return map;
			}
		}
		return null;
	}
	
	protected boolean hasChildrenByMap(String structId, String serviceId,
			ResourceType type) {
		if (structId == null) {
			return false;
		}
		for(Map<String,String> map: dataMap){
			if(structId.equals(map.get("id"))){
				if("root".equals(map.get("structId"))){
					return true;
				}
			}
			if(structId.equals(map.get("parentId"))){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据ida_id获取接口元数据的属性信息
	 * @param ida_id
	 * @return
	 */
	protected List<Attr> getIdaPropListByIdaId(String ida_id){
		List<Attr> list = new ArrayList<Attr>();
		for(Attr attr: allIdaProp){
			if(ida_id.equals(attr.getStructId())){
				list.add(attr);
			}
		}
		return list;
	}
	
	/**
	 * get topResourceId from Map
	 * @return
	 */
	protected String getTopResourceIdByMap(){
		for(Map<String,String> map: dataMap){
			if("/".equals(map.get("parentId"))){
				return map.get("id");
			}
		}
		try {
			throw new Exception("未找到SDA的根节点");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
