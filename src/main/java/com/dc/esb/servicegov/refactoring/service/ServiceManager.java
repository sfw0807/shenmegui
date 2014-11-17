package com.dc.esb.servicegov.refactoring.service;

import java.util.List;

import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.Service;

public interface ServiceManager {
	
	public List<Service> getAllServices();
	public List<Service> getServicesByCategory(String categoryId);
	public Service getServiceById(String id);
	public boolean delServiceById(String id);
	public boolean insertOrUpdateService(Service service);
	public List<Operation> getOperationsByServiceId(String id);
	public boolean redefService(String id);
	public boolean deployService(String id);
	public boolean publishService(String id);
}
