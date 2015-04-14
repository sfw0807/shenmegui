package com.dc.esb.servicegov.refactoring.resource.impl;


import com.dc.esb.servicegov.refactoring.dao.impl.IdaDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.IdaPROPDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.InterfaceExtendsDAOImpl;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataInfo;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataNode;
import com.dc.esb.servicegov.refactoring.util.DefaultTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XMPassedInterfaceDataFromDB extends AbstractDataFromDB {

	private MetadataInfo info = null;
	@Autowired
	private IdaDAOImpl idaDAO;
	@Autowired
	private IdaPROPDAOImpl idaPropDAO;
	@Autowired
	private InterfaceExtendsDAOImpl iExtendsDAO;

	public MetadataNode getNodeFromDB(String resourceName) {
		return this.getNodeFromDB(resourceName, ResourceType.INTERFACE);
	}

	public MetadataNode getNodeFromDB(String resourceName, ResourceType type) {
		return this.getInterfaceData(resourceName);
	}

	public MetadataInfo getInterfaceInfo() {
		return this.info;
	}
	

	public MetadataNode getInterfaceData(String interfaceid) {
		log.info("开始导出接口[" + interfaceid +"]IDA !");
		// 获取IDA模板
		if(DefaultTemplate.checkIsIDATemplate(interfaceid)){
			if(DefaultTemplate.hasTemplate(interfaceid)){
				return DefaultTemplate.getTemplate(interfaceid);
			}
		}
		// 获取所有IDA和IDAPROP的所有数据
		super.dataMap.clear();
		super.dataMap = idaDAO.getAllIDAMapByInterfaceId(interfaceid);
		super.allIdaProp.clear();
		super.allIdaProp = idaPropDAO.getAllIdaPropList();
		// 获取根节点的IDA的resourceid
//		String resourceId = idaDAO.getTopResourceId(interfaceid);
		String resourceId = super.getTopResourceIdByMap();
		MetadataNode interfaceNodes = null;
		if (resourceId != null && !resourceId.equals("")) {
			interfaceNodes = this.getInterfaceData(interfaceid, resourceId);
		}
		// 设置IDA模板
        if(DefaultTemplate.checkIsIDATemplate(interfaceid)){
			   DefaultTemplate.saveTemplate(interfaceid, interfaceNodes);
		}
        log.info("完成导出接口[" + interfaceid +"]IDA !");
		return interfaceNodes;

	}



	public MetadataNode getInterfaceData(String interfaceId, String resourceId) {
		// 设置IDA模板
		MetadataNode interfaceNodes = null;
		interfaceNodes = new MetadataNode();
		getAllChild(resourceId, interfaceId, interfaceNodes,
				ResourceType.INTERFACE);
		// 设置IDA模板
		return interfaceNodes;
	}

	
	public List<String> getParentNodeIds(String interfaceId) {
		List<String> parentIds = iExtendsDAO.getSuperInterfaceIdsbyInterfaceid(interfaceId);
		return parentIds;
	}
}
