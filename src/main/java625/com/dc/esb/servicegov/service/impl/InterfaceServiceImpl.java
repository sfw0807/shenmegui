package com.dc.esb.servicegov.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.InterfaceDAOImpl;
import com.dc.esb.servicegov.entity.Interface;
import com.dc.esb.servicegov.service.InterfaceService;
@Service
@Transactional
public class InterfaceServiceImpl implements InterfaceService{
	@Autowired
	private InterfaceDAOImpl interfaceDAOImpl;

	@Override
	public void editEntity(Interface entity) {
		interfaceDAOImpl.save(entity);
	}

	@Override
	public List<Interface> getEntityAll() {
		
		return interfaceDAOImpl.getAll();
	}

	@Override
	public List<Interface> getEntityByName(String name, String value) {
		return interfaceDAOImpl.findBy(name, value);
	}

	@Override
	public void removeEntity(String id) {
		interfaceDAOImpl.delete(id);
		
	}
}
