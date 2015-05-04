package com.dc.esb.servicegov.service;

import java.util.List;

import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.Service;

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
	public List<Service> getAuditServices();
	public boolean auditService(String serviceId, String auditState);
	public boolean checkServicePassed(String serviceId);
	public boolean submitService(String serviceId);
}
