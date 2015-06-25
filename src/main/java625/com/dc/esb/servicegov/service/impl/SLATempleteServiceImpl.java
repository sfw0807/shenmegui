package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.SLATemplete;
import com.dc.esb.servicegov.service.SLATempleteService;

@Service
@Transactional
public class SLATempleteServiceImpl extends BaseServiceImpl<SLATemplete,String> implements SLATempleteService{

}
