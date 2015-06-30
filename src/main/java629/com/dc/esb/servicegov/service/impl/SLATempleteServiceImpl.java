package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.SLATemplete;
import com.dc.esb.servicegov.service.SLATempleteService;

@Service
@Transactional
public class SLATempleteServiceImpl extends AbstractBaseService<SLATemplete> implements SLATempleteService{

}
