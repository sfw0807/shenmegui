package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.SDAProp;
import com.dc.esb.servicegov.service.SDAPropService;

@Service
@Transactional
public class SDAPropServiceImpl extends AbstractBaseService<SDAProp> implements SDAPropService{

}
