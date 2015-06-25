package com.dc.esb.servicegov.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.SystemDAOImpl;
import com.dc.esb.servicegov.service.SystemService;

@Service
@Transactional
public class SystemServiceImpl implements SystemService{
	@Autowired
	private SystemDAOImpl systemDAOImpl;

	@Override
	public void editEntity(com.dc.esb.servicegov.entity.System entity) {
		systemDAOImpl.save(entity);
	}

	@Override
	public List<com.dc.esb.servicegov.entity.System> getEntityAll() {
		
		return systemDAOImpl.getAll();
	}

	@Override
	public List<com.dc.esb.servicegov.entity.System> getEntityByName(String name, String value) {
		return systemDAOImpl.findBy(name, value);
	}

	@Override
	public void removeEntity(String id) {
		systemDAOImpl.delete(id);
		
	}
}
