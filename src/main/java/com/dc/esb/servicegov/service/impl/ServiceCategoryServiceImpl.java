package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.ServiceCategoryDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.ServiceCategory;
import com.dc.esb.servicegov.service.ServiceCategoryService;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceCategoryServiceImpl extends AbstractBaseService<ServiceCategory, String> implements ServiceCategoryService{
	
	@Autowired
	private ServiceCategoryDAOImpl serviceCategoryDAOImpl;
	@Override
	public HibernateDAO<ServiceCategory, String> getDAO() {
		return serviceCategoryDAOImpl;
	}
}
