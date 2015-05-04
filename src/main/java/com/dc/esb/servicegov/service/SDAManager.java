package com.dc.esb.servicegov.service;

import java.util.List;

import com.dc.esb.servicegov.entity.SDA;

public interface SDAManager {
	
	public abstract List<SDA> getAllSDAs();
	public abstract SDA getSDAById(String id);
	public abstract boolean deleteSDA(String operationId, String serviceId);
	public abstract boolean saveSDA(List<SDA> sdaList);
	public abstract boolean saveSDA(SDA sda);
	public abstract boolean updateOperationVersion(String operationId, String serviceId);
}
