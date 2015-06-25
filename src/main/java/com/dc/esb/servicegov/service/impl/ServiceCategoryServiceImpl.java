package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.ServiceCategory;
import com.dc.esb.servicegov.service.ServiceCategoryService;

@Service
@Transactional
public class ServiceCategoryServiceImpl extends BaseServiceImpl<ServiceCategory> implements ServiceCategoryService{

}
