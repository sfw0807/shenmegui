package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.ServiceCategory;
import com.dc.esb.servicegov.service.ServiceCategoryService;

@Service
@Transactional
public class ServiceCategoryServiceImpl extends AbstractBaseService<ServiceCategory> implements ServiceCategoryService{

}
