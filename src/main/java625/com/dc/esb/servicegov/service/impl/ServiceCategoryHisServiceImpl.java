package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.ServiceCategoryHis;
import com.dc.esb.servicegov.service.ServiceCategoryHisService;

@Service
@Transactional
public class ServiceCategoryHisServiceImpl extends BaseServiceImpl<ServiceCategoryHis,String> implements ServiceCategoryHisService{

}
