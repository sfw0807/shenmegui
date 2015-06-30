package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.OLA;
import com.dc.esb.servicegov.service.OLAService;

@Service
@Transactional
public class OLAServiceImpl extends AbstractBaseService<OLA> implements OLAService{

}
