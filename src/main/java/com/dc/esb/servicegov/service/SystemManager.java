package com.dc.esb.servicegov.service;

import java.util.List;
import java.util.Map;

import com.dc.esb.servicegov.entity.System;

public interface SystemManager {
	
	public List<System> getAllSystems();
	public List<System> getSystemsByConditons(Map<String, String> map);
	public void delSystemById(String id);
	public void insertOrUpdate(System system);
	public System getSystemById(String id);
	public boolean isSystemUsed(String id);
	public void batchDelSystem(String[] idArr);
	public System getSystemByAb(String systemAb);
}
