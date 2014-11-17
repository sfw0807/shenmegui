package com.dc.esb.servicegov.refactoring.resource.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.refactoring.dao.impl.DefaultIntefaceDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.ServiceHeadRelateDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.DefaultInteface;
import com.dc.esb.servicegov.refactoring.entity.ServiceHeadRelate;
import com.dc.esb.servicegov.refactoring.resource.IDataFromDB.ResourceType;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataNode;


/**
 * 
 * @author Vincent Fan
 * fetcher default interfaces in the interfaces which used by the service extendsed by the service to export
 *
 */
@Service
public class DefaultInterfaceFetcher {
	private Log log = LogFactory.getLog(DefaultInterfaceFetcher.class);
	
	@Autowired
	private XMPassedInterfaceDataFromDB xmPassedInterfaceDataFromDB;
	@Autowired
	private DefaultIntefaceDAOImpl defaultIntefaceDAO;
	@Autowired
	private ServiceHeadRelateDAOImpl serviceHeadDAO;
	/**
	 * 默认接口。
	 */
	
	public DefaultInterfaceFetcher(){
	}
	
	/*
	 * modify routing method for finding first default interface using by super
	 * services
	 * 
	 * @auther Vincent Fan
	 */
	public MetadataNode findDefaultInterfaceIn(String serviceId) {
		return findDefaultInterface(serviceId, "0");
	}

	/*
	 * modify routing method for finding first default interface using by super
	 * services
	 * 
	 * @auther Vincent Fan
	 */
	public  MetadataNode findDefaultInterfaceOut(String serviceId) {
		return findDefaultInterface(serviceId, "1");
	}

	/*
	 * modify routing method for finding first default interface using by super
	 * services
	 * 只能传入服务ID处理 默认接口配置查找
	 * @auther Vincent Fan
	 */
	private MetadataNode findDefaultInterface(String serviceId, String type) {
		MetadataNode interfaceNode = null;
		List<ServiceHeadRelate> list = serviceHeadDAO.findBy("serviceId", serviceId);
		if (list != null && list.size() > 0) {
			String shead = list.get(0).getSheadId();
			List<DefaultInteface> defaultIntefaces = defaultIntefaceDAO.getDefaultInterfaceByServiceId(shead, type);
				if ((null != defaultIntefaces)
						&& (defaultIntefaces.size() > 0)){
					DefaultInteface defaultInterface = defaultIntefaces.get(0);
					String interfaceId = defaultInterface.getInterfaceId();
							interfaceNode = xmPassedInterfaceDataFromDB.getNodeFromDB(
									interfaceId, ResourceType.INTERFACE);
					}
		}
		else{
			log.error("未找到服务[" + serviceId + "]对应的业务报文头信息，导出服务默认接口配置失败!");
		}
		return interfaceNode;
	}

}
