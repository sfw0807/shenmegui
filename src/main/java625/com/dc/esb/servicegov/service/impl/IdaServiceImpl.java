package com.dc.esb.servicegov.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dc.esb.servicegov.dao.impl.IdaDAOImpl;
import com.dc.esb.servicegov.entity.Ida;
import com.dc.esb.servicegov.service.IdaService;

@Service
@Transactional
public class IdaServiceImpl implements IdaService{
	@Autowired
	private IdaDAOImpl idaDAOImpl;

	@Override
	public void editEntity(Ida entity) {
		idaDAOImpl.save(entity);
	}

	@Override
	public List<Ida> getEntityAll() {
		
		return idaDAOImpl.getAll();
	}

	@Override
	public List<Ida> getEntityByName(String name, String value) {
		return idaDAOImpl.findBy(name, value);
	}

	@Override
	public void removeEntity(String id) {
		idaDAOImpl.delete(id);
		
	}
}
