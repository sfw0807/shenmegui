package com.dc.esb.servicegov.service;

import java.util.List;


public interface SystemService {

public List<com.dc.esb.servicegov.entity.System> getSystemByName(String name,String value);
	
	public List<com.dc.esb.servicegov.entity.System> getSystemAll();
	
	public void editSystem(com.dc.esb.servicegov.entity.System entity);
	
	public void removeSystem(String id);
}
