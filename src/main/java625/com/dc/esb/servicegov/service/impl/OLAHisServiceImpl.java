package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.OLAHis;
import com.dc.esb.servicegov.service.OLAHisService;

@Service
@Transactional
public class OLAHisServiceImpl extends BaseServiceImpl<OLAHis,String> implements OLAHisService{

}
