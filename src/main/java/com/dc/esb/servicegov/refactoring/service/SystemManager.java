package com.dc.esb.servicegov.refactoring.service;

import java.util.List;
import com.dc.esb.servicegov.refactoring.entity.System;

public interface SystemManager {
	
	public List<System> getAllSystems();
	public void delSystemById(String id);
	public void insertOrUpdate(System system);
	public System getSystemById(String id);
	public boolean isSystemUsed(String id);
	public void batchDelSystem(String[] idArr);
	public System getSystemByAb(String systemAb);
}
