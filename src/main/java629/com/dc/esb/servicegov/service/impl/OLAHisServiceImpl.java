package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.OLAHis;
import com.dc.esb.servicegov.service.OLAHisService;

@Service
@Transactional
public class OLAHisServiceImpl extends AbstractBaseService<OLAHis> implements OLAHisService{

}
