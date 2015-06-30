package com.dc.esb.servicegov.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dc.esb.servicegov.dao.impl.BaseDAOImpl;
import com.dc.esb.servicegov.service.support.BaseService;

public class BaseServiceImpl<T> implements BaseService<T>{

	@Autowired
	private BaseDAOImpl<T> baseDAOImpl;
	@Override
	public void editEntity(T entity) {
		baseDAOImpl.save(entity);
	}

	@Override
	public List<T> getEntityByName(String name, String value) {
		return baseDAOImpl.findBy(name, value);
	}

	@Override
	public List<T> getEntityAll() {
		return baseDAOImpl.getAll();
	}

	@Override
	public void removeEntity(String id) {
		baseDAOImpl.delete(id);
	}

}
