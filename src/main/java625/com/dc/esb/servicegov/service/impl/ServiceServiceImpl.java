package com.dc.esb.servicegov.service.impl;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;
import com.dc.esb.servicegov.service.ServiceService;

@Service
@Transactional
public class ServiceServiceImpl extends BaseServiceImpl<com.dc.esb.servicegov.entity.Service,String> implements ServiceService{
	
	
}
