package com.dc.esb.servicegov.service.impl;

import java.util.List;

import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.service.ServiceService;

@Service
@Transactional
public class ServiceServiceImpl extends AbstractBaseService<com.dc.esb.servicegov.entity.Service> implements ServiceService{
	@Autowired
    private ServiceDAOImpl serviceDAOImpl;
	public List<com.dc.esb.servicegov.entity.Service> getAll(){
		return serviceDAOImpl.getAll();
	}
	
	public List<com.dc.esb.servicegov.entity.Service> getByProperty(String key, String value){
		
		return serviceDAOImpl.findBy(key, value);
	}
	
	public com.dc.esb.servicegov.entity.Service getUniqueByServiceId(String serviceId){
		return serviceDAOImpl.findUniqueBy("serviceId", serviceId);
	}
	
	public List<Operation> getOperationByServiceId(String serviceId){
		String hql = " from Operation a where a.serviceId = ?";
		return serviceDAOImpl.find(hql, serviceId);
	}
}
