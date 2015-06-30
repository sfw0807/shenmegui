package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.ServiceCategoryHis;
import com.dc.esb.servicegov.service.ServiceCategoryHisService;

@Service
@Transactional
public class ServiceCategoryHisServiceImpl extends AbstractBaseService<ServiceCategoryHis> implements ServiceCategoryHisService{

}
