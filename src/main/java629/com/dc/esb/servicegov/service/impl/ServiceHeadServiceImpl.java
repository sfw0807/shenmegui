package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.ServiceHead;
import com.dc.esb.servicegov.service.ServiceHeadService;

@Service
@Transactional
public class ServiceHeadServiceImpl extends AbstractBaseService<ServiceHead> implements ServiceHeadService{

}
