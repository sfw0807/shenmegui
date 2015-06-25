package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.SDA;
import com.dc.esb.servicegov.service.SDAService;

@Service
@Transactional
public class SDAServiceImpl extends BaseServiceImpl<SDA,String> implements SDAService{

}
