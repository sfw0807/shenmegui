package com.dc.esb.servicegov.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dc.esb.servicegov.dao.impl.ServiceInvokeDAOImpl;
import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.ServiceInvokeService;
@Service
@Transactional
public class ServiceInvokeServiceImpl implements ServiceInvokeService{
	@Autowired
	private ServiceInvokeDAOImpl serviceInvokeDAOImpl;

	@Override
	public void editEntity(ServiceInvoke entity) {
		serviceInvokeDAOImpl.save(entity);
		
	}

	@Override
	public List<ServiceInvoke> getEntityAll() {
		return serviceInvokeDAOImpl.getAll();
	}

	@Override
	public List<ServiceInvoke> getEntityByName(String name, String value) {
		return serviceInvokeDAOImpl.findBy(name, value);
	}

	@Override
	public void removeEntity(String id) {
		serviceInvokeDAOImpl.delete(id);
		
	}
}
