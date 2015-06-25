package com.dc.esb.servicegov.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.InterfaceHeadDAOImpl;
import com.dc.esb.servicegov.entity.InterfaceHead;
import com.dc.esb.servicegov.service.InterfaceHeadService;
@Service
@Transactional(propagation=Propagation.REQUIRED , rollbackFor = Exception.class)
public class InterfaceHeadServiceImpl implements InterfaceHeadService<InterfaceHead>{

	@Autowired 
	InterfaceHeadDAOImpl interfaceDao;
	
	//@Transactional(propagation=Propagation.REQUIRED , rollbackFor = java.lang.Exception.class)
	public void editEntity(InterfaceHead entity) {
		interfaceDao.save(entity);
	}

	public List<InterfaceHead> getEntityByName(String name, String value) {
		return interfaceDao.findBy(name, value);
	}

	public List<InterfaceHead> getEntityAll() {
		return interfaceDao.getAll();
	}

	//@Transactional(propagation=Propagation.REQUIRED , rollbackFor = java.lang.Exception.class)
	public void removeEntity(String id) {
		interfaceDao.delete(id);
	}

	@Override
	public InterfaceHead getInterfaceHead(String headId) {
		return interfaceDao.get(headId);
	}

}
