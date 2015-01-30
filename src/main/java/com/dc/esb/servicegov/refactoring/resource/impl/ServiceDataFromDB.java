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
 * ServiceDataFromDB.java
 */
package com.dc.esb.servicegov.refactoring.resource.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.refactoring.dao.impl.OLADAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.OLA;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataNode;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataStructonHelper;


/**
 * 功能:
 * 
 * @since : jdk1.6
 * 
 */
@Service
public class ServiceDataFromDB extends AbstractDataFromDB {


	private MetadataStructonHelper helper = MetadataStructonHelper
			.getInstance();

	@Autowired
	private OLADAOImpl olaDAO;
	@Autowired
	private OperationDAOImpl operationDAO;

	/**
	 * 覆盖方法
	 * 
	 * @see com.dc.sg.impls.metadata.dao.IDataFromDB#getNodeFromDB(String,
	 *      com.dc.sg.impls.metadata.dao.IDataFromDB.resouceType)
	 */
	public MetadataNode getNodeFromDB(String serviceId, String operationId, ResourceType type) {
		return super.getServiceData(serviceId, operationId);
	}

	public MetadataNode getNodeFromDB(String serviceId, String operationId) {
		return this.getNodeFromDB(serviceId, operationId, ResourceType.SERVICE);
	}

/**
	 * 
	 * @param resourceName
	 * @param flag
	 *            :true 获取该服务完整结构，merger父服务后的结构。false：只获取当前服务的结构不进行meger
	 * @return
	 */
	public MetadataNode getNodeFromDB(String serviceId, String operationId, boolean flag) {

		if (flag) {
			MetadataNode sNode = getNodeFromDB(serviceId, operationId);
			MetadataNode node = this.mergerExtendService(serviceId, operationId, sNode);
			return node;

		} else
			return this.getNodeFromDB(serviceId, operationId);

	}

	@SuppressWarnings("unchecked")
	public List<String> getSubServiceId(String serviceid) {
		
		return operationDAO.getOperationIdsbyServiceId(serviceid);
	}

	public List<String> getAllSubServiceList(String serviceID) {
		List<String> list = new ArrayList<String>();
		if (serviceID != null && !"".equals(serviceID)) {
			if(this.hasOperation(serviceID)&& !list.contains(serviceID)){
				list.add(serviceID);
			}
			List<String> sub = this.getSubServiceId(serviceID);
			for(String subServiceID:sub){
				if(this.hasOperation(subServiceID))
					list.add(subServiceID);
				this.getAllSubServiceList(subServiceID);
			}
		}
		return list;
	}

    /**
     * 判断具有wsdl_host属性并且是true的操作
     * @param serviceID
     * @return
     */
	@SuppressWarnings("unchecked")
	public boolean hasOperation(String serviceID) {
		List operation = olaDAO.getWsdlOperationByServiceId(serviceID);
		return operation != null ? operation.size() > 0 : false;
	}
	
	public String getWSDLHost(String serviceID){
//		String wsdlHost = (String)dm.newTransaction(true).selectOne(
//				new DBAction("GetWsdlHost", serviceID));
		return "http://esb.spdbbiz.com";
		
	}
	/**
	 * 根据服务ID获取服务的OLA信息
	 * @param serviceid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public OLA getServiceOLAByServiceId(String serviceId) {
		OLA ola = new OLA();
		List<OLA> list = olaDAO.getWsdlHostByServiceId(serviceId);
		if(list != null && list.size() >0){
			ola.setServiceId(serviceId);
			ola.setOlaName("wsdl_host");
			ola.setOlaValue("http://esb.spdbbiz.com");
		}
		return ola;
	}
	
	/**
	 * 根据服务ID和操作ID获取操作OLA信息
	 * @param serviceid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public OLA getOperationOLAById(String serviceId,String operationId) {
		List<OLA> list = olaDAO.getWsdlHostByOperationIdAndServiceId(operationId, serviceId);
		if(list != null && list.size() >0){
			return list.get(0);
		}
		return null;
	}

//	@SuppressWarnings("unchecked")
//	public List<Map<String, String>> getAllSubServiceIds(String serviceid) {
//		List<Map<String, String>> serviceIdsWithPath = null;
//		if (null != serviceid) {
//			serviceIdsWithPath = dm.newTransaction(true).selectList(
//					new DBAction("selectSubServiceIds", serviceid));
//		}
//		return serviceIdsWithPath;
//	}
	
	private MetadataNode mergerExtendService(String serviceId, String operationId, MetadataNode sourceNode) {
		MergerObject obj = this.getServiceMergerObject(serviceId, operationId, sourceNode
				.getResourceid());
		MetadataNode mergerNode = sourceNode;
		if (obj.hasChild())
			mergerNode = this.merger(sourceNode, obj);
		return mergerNode;
	}

	private MetadataNode merger(MetadataNode sourceNode, MergerObject obj) {
		MetadataNode mergerNode = null;
		if (obj.hasChild()) {
			List<MergerObject> list = obj.getSupResourceList();
			for (MergerObject mObj : list) {
				String superResourceid = mObj.getResourceid();
				log.info("superResourceid 合并：" + superResourceid);
				MetadataNode sNode = super.getHeadData(superResourceid);
				if (mObj.hasChild()) {
					mergerNode = this.merger(sNode, mObj);
				} else
					mergerNode = sNode;
				mergerNode = helper.merger(sourceNode, mergerNode);
				sourceNode = mergerNode;
			}
		} else {
			MetadataNode sNode = super.getHeadData(obj.getResourceid());
			mergerNode = helper.merger(sourceNode, sNode);
		}
		return mergerNode;
	}

	/**
	 * 合并操作和业务报文头SDA
	 * @param serviceId
	 * @param operationId
	 * @param releaseId
	 * @return
	 */
	private MergerObject getServiceMergerObject(String serviceId, String operationId, String releaseId) {
		Map<String,String> operationMap = new HashMap<String,String>();
		operationMap.put("PATH", operationId);
		operationMap.put("SERVICEID", operationId);
		operationMap.put("SUPERSERVICEID", serviceId);
//		Map<String,String> serviceMap = new HashMap<String,String>();
//		serviceMap.put("PATH", operationId + "." + serviceId);
//		serviceMap.put("SERVICEID", serviceId);
//		serviceMap.put("SUPERSERVICEID", shead);
//		Map<String,String> headMap = new HashMap<String,String>();
//		headMap.put("PATH", operationId + "." + serviceId + "." + shead);
//		headMap.put("SERVICEID", shead);
//		headMap.put("SUPERSERVICEID", null);
		Map<String,String> headMap = new HashMap<String,String>();
		headMap.put("PATH", operationId + "." + serviceId);
		headMap.put("SERVICEID", serviceId);
		headMap.put("SUPERSERVICEID", null);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(operationMap);
//		list.add(serviceMap);
		list.add(headMap);
		MergerObject obj = new MergerObject();
		if (list.size() > 1) {
			for (Map<String, String> map : list) {
				String path = map.get("PATH");
				if (path.contains(".")) {
					path = path.substring(0, path.lastIndexOf("."));
				}
				String superServiceid = map.get("SUPERSERVICEID");
				if (obj.getMergerByPath(path) != null) {
					if (superServiceid != null && !superServiceid.equals("")) {
						MergerObject nObj = new MergerObject();
						nObj.setResourceid(superServiceid);
						obj.getMergerByPath(path).addSupResource(nObj);
					}
				} else {
					obj.setResourceid(path);
					MergerObject ncObj = new MergerObject();
					ncObj.setResourceid(superServiceid);
					obj.addSupResource(ncObj);
				}
			}
		}
		return obj;
	}

	/**
	 * @param releaseId
	 *            服务的标识号
	 * @return 服务继承关系列表，该处的服务继承关系的查找是自下而上。
	 */

	@Override
	public MetadataNode getNodeFromDB(String resourceName, ResourceType type) {
		// TODO Auto-generated method stub
		return null;
	}
}
