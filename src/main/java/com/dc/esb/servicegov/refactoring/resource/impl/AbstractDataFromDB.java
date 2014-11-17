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
package com.dc.esb.servicegov.refactoring.resource.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.refactoring.dao.impl.HeadSDADAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.IdaDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.IdaPROPDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SDADAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SdaPROPDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.ServiceHeadRelateDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.resource.IDataFromDB;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.Attr;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.IMetadataNodeAttribute;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataNode;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataNodeAttribute;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.IMetadataNode.Type;
import com.dc.esb.servicegov.refactoring.util.DefaultTemplate;


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
	
	@Autowired
	private SDADAOImpl sdaDAO;
	@Autowired
	private IdaDAOImpl idaDAO;
	@Autowired
	private IdaPROPDAOImpl idaPropDAO;
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
//		if(DefaultTemplate.hasTemplate(sheadId)){
//			return DefaultTemplate.getTemplate(sheadId);
//		}
		MetadataNode serviceNodes = new MetadataNode();
		if(sheadId == null){
			return serviceNodes;
		}
		String id = headSDADAO.getTopResourceIdBySheadId(sheadId);
		getHeadSDAAllChild(id, sheadId, serviceNodes, ResourceType.HEAD);
//		DefaultTemplate.saveTemplate(sheadId, serviceNodes);
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
		String id = sdaDAO.getTopResourceId(serviceid, operationid);
		MetadataNode serviceNodes = new MetadataNode();
		getAllChild(id, serviceid, serviceNodes, ResourceType.SERVICE);
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
		String childNodeName = this.getHeadStructName(nodeId);
		if (hasHeadChildren(nodeId)) {
			List<String> cNode = this.getHeadChildren(nodeId);
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
		String childNodeName = this.getStructName(nodeId, structid, type);
		
		if (hasChildren(nodeId, structid, type)) {
			List<String> cNode = this.getChildren(nodeId, structid, type);
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
		Map<String, String> info = this.getNodeInfo(nodeId, type);
		List<Attr> attr = getNodeAttr(nodeId, type);
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

	// 获取节点属性
	@SuppressWarnings("unchecked")
	private List<Attr> getNodeAttr(String childId, ResourceType type) {
		List<Attr> nodeProps =  null;
		if (type == ResourceType.SERVICE){
			nodeProps = sdaPropDAO.getsdaPropMapBySdaId(childId);
		}
		else{
			nodeProps = idaPropDAO.getIdaPropMapByIdaId(childId);
		}
		return nodeProps;
	}

	protected boolean hasChildren(String structId, String serviceId,
			ResourceType type) {
		if (structId == null) {
			return false;
		}
		List<String> childStructIds = this.getChildren(structId, serviceId,
				type);
		if(childStructIds == null){
			return false;
		}
		return childStructIds.size() > 0;
	}
	
	/**
	 * 根据resourceid判断业务报文头下是否有子节点
	 * @param resourceid
	 * @return
	 */
	protected boolean hasHeadChildren(String resourceid) {
		if (resourceid == null) {
			return false;
		}
		List<String> childStructIds = this.getHeadChildren(resourceid);
		if(childStructIds == null){
			return false;
		}
		return childStructIds.size() > 0;
	}
	
	@SuppressWarnings("unchecked")
	protected List<String> getChildren(String structId, String serviceid,
			ResourceType type) {
		if (structId == null) {
			return null;
		}
		List<String> childStructIds = null;
		if (ResourceType.SERVICE == type) {
			childStructIds = sdaDAO.getChildIdsByResourceId(structId);
		} else {
			childStructIds = idaDAO.getChildIdsByResourceId(structId);
		}
		return childStructIds;
	}
	
	/**
	 * 根据resourceid获取业务报文头所有孩子节点resourceid列表
	 * @param structId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<String> getHeadChildren(String resourceid) {
		if (resourceid == null ) {
			return new ArrayList<String>();
		}
		List<String> childStructIds = null;
		childStructIds = headSDADAO.getChildIdsByResourceId(resourceid);
		return childStructIds;
	}

	protected String getStructName(String structId, String serviceid,
			ResourceType type) {
		String structName = null;
		if (structId == null ) {
			return null;
		}
		if (ResourceType.SERVICE == type) {
			structName = sdaDAO.getStructIdByResourceId(structId);
		} else {
			structName = idaDAO.getStructIdByResourceId(structId);
		}
		return structName;
	}
	
	/**
	 * 根据resourceid获取业务报文头structName
	 * @param resourceid
	 * @return
	 */
	protected String getHeadStructName(String resourceid) {
		String structName = null;
		if (resourceid == null ) {
			return null;
		}
		structName = headSDADAO.getStructIdByResourceId(resourceid);
		return structName;
	}

	@SuppressWarnings("unchecked")
	protected MetadataNode getInterfaceData(String interfaceid) {

		String id = idaDAO.getTopResourceId(interfaceid);
		MetadataNode interfaceNodes = new MetadataNode();
		getAllChild(id, interfaceid, interfaceNodes, ResourceType.INTERFACE);
		return interfaceNodes;

		
	}

	/**
	 * 覆盖方法
	 * 
	 * @see com.dc.sg.impls.metadata.dao.IDataFromDB#getNodeFromDB(java.lang.String,
	 *      com.dc.sg.impls.metadata.dao.IDataFromDB.resouceType)
	 */
	public MetadataNode getNodeFromDB(InvokeInfo invokeInfo, ResourceType type) {
		MetadataNode node = null;
		if (type == ResourceType.INTERFACE) {
			node = getInterfaceData(invokeInfo.getEcode());
		} else if (type == ResourceType.SERVICE) {
			node = getServiceData(invokeInfo.getServiceId(),invokeInfo.getOperationId());
		}
		return node;
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

}
