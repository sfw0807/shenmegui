package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.ServiceHead;
import com.dc.esb.servicegov.service.ServiceHeadService;

@Service
@Transactional
public class ServiceHeadServiceImpl extends BaseServiceImpl<ServiceHead> implements ServiceHeadService{

}
