package com.dc.esb.servicegov.service;

import java.util.List;


public interface BaseService<T> {
	
	public List<T> getEntityByName(String name, String value);

	public List<T> getEntityAll();

	public void editEntity(T entity);

	public void removeEntity(String id);
}
