package com.dc.esb.servicegov.refactoring.resource.impl;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.refactoring.dao.impl.IdaDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.InterfaceExtendsDAOImpl;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataInfo;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataNode;
import com.dc.esb.servicegov.refactoring.util.DefaultTemplate;

@Service
public class XMPassedInterfaceDataFromDB extends AbstractDataFromDB {

	private MetadataInfo info = null;
	@Autowired
	private IdaDAOImpl idaDAO;
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
		// 设置IDA模板
		if(DefaultTemplate.checkIsIDATemplate(interfaceid)){
			if(DefaultTemplate.hasTemplate(interfaceid)){
				return DefaultTemplate.getTemplate(interfaceid);
			}
		}
		// 获取根节点的IDA的resourceid
		String resourceId = idaDAO.getTopResourceId(interfaceid);
		MetadataNode interfaceNodes = null;
		if (resourceId != null && !resourceId.equals("")) {
			interfaceNodes = this.getInterfaceData(interfaceid, resourceId);
		}
		// 设置IDA模板
        if(DefaultTemplate.checkIsIDATemplate(interfaceid)){
			   DefaultTemplate.saveTemplate(interfaceid, interfaceNodes);
		}
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

	@SuppressWarnings("unchecked")
	@Override
	protected List<String> getChildren(String structId, String serviceid,
			ResourceType type) {

		if (structId == null && serviceid == null) {
			return new ArrayList<String>();
		}
		List<String> childStructIds = idaDAO.getChildIdsByResourceId(structId);
		return childStructIds;
	}
	
	public List<String> getParentNodeIds(String interfaceId) {
		List<String> parentIds = iExtendsDAO.getSuperInterfaceIdsbyInterfaceid(interfaceId);
		return parentIds;
	}
}
