package com.dc.esb.servicegov.refactoring.service;

import java.util.List;

import com.dc.esb.servicegov.refactoring.entity.Service;

public interface ServiceManager {
	
	public List<Service> getAllServices();
	public List<Service> getServicesByCategory(String categoryId);
	public Service getServiceById(String id);
}
