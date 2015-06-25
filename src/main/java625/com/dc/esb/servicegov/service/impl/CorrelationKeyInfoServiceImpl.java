package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.CorrelationKeyInfo;
import com.dc.esb.servicegov.service.CorrelationKeyInfoService;

@Service
@Transactional
public class CorrelationKeyInfoServiceImpl extends BaseServiceImpl<CorrelationKeyInfo,String> implements CorrelationKeyInfoService{

}
