package com.dc.esb.servicegov.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.BaseDAOImpl;
@Transactional
public class BaseServiceImpl<T,PK extends Serializable>{

	@Autowired
	public BaseDAOImpl<T> baseDAOImpl;
	
	//@Transactional(propagation=Propagation.REQUIRED , rollbackFor = java.lang.Exception.class)
	public void editEntity(T entity) {
		baseDAOImpl.save(entity);
	}

	public List<T> getEntityByName(String name, String value) {
		return baseDAOImpl.findBy(name, value);
	}

	public List<T> getEntityAll() {
		return baseDAOImpl.getAll();
	}

	//@Transactional(propagation=Propagation.REQUIRED , rollbackFor = java.lang.Exception.class)
	public void removeEntity(String id) {
		baseDAOImpl.delete(id);
	}
}
