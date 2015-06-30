package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.SDAHis;
import com.dc.esb.servicegov.service.SDAHisService;

@Service
@Transactional
public class SDAHisServiceImpl extends AbstractBaseService<SDAHis> implements SDAHisService{

}
