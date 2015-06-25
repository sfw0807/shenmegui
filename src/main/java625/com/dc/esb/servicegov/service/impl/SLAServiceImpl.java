package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.SLA;
import com.dc.esb.servicegov.service.SLAService;

@Service
@Transactional
public class SLAServiceImpl extends BaseServiceImpl<SLA,String> implements SLAService{

}
