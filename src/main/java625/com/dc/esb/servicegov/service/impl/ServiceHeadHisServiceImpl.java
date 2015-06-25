package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.ServiceHeadHis;
import com.dc.esb.servicegov.service.ServiceHeadHisService;

@Service
@Transactional
public class ServiceHeadHisServiceImpl extends BaseServiceImpl<ServiceHeadHis,String> implements ServiceHeadHisService{

}
