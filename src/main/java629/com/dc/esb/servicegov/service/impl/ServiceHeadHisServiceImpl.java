package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.ServiceHeadHis;
import com.dc.esb.servicegov.service.ServiceHeadHisService;

@Service
@Transactional
public class ServiceHeadHisServiceImpl extends AbstractBaseService<ServiceHeadHis> implements ServiceHeadHisService{

}
