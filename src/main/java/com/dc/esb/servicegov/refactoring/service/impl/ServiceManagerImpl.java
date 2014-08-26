package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.refactoring.service.ServiceManager;

@Service
@Transactional
public class ServiceManagerImpl implements ServiceManager{
	private Log log = LogFactory.getLog(getClass());
	@Autowired
	private ServiceDAOImpl serviceDAO;

	public List<com.dc.esb.servicegov.refactoring.entity.Service> getAllServices() {
		List<com.dc.esb.servicegov.refactoring.entity.Service> services = new ArrayList<com.dc.esb.servicegov.refactoring.entity.Service>();
		if(log.isInfoEnabled()){
			log.info("获取全部服务信息...");
		}
		try{
			services = serviceDAO.getAll();
		}catch(Exception e){
			log.error("获取全部服务信息失败",e);
		}
		return services;
	}

	@Override
	public List<com.dc.esb.servicegov.refactoring.entity.Service> getServicesByCategory(
			String categoryId) {
		List<com.dc.esb.servicegov.refactoring.entity.Service> services = new ArrayList<com.dc.esb.servicegov.refactoring.entity.Service>();
		if(log.isInfoEnabled()){
			log.info("获取服务分类为["+categoryId+"]的服务...");
		}
		try{
			services = serviceDAO.findBy("categoryId", categoryId);
		}catch(Exception e){
			log.error("获取全部服务信息失败",e);
		}
		return services;
	}

	@Override
	public com.dc.esb.servicegov.refactoring.entity.Service getServiceById(
			String id) {
		if(log.isInfoEnabled()){
			log.info("获取服务ID为["+id+"]的服务...");
		}
		return serviceDAO.get(id);
	}
}
