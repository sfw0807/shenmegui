package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.SLAHis;
import com.dc.esb.servicegov.service.SLAHisService;

@Service
@Transactional
public class SLAHisServiceImpl extends AbstractBaseService<SLAHis> implements SLAHisService{

}
