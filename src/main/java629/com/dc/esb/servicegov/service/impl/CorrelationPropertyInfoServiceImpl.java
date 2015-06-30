package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.CorrelationPropertyInfo;
import com.dc.esb.servicegov.service.CorrelationPropertyInfoService;

@Service
@Transactional
public class CorrelationPropertyInfoServiceImpl extends AbstractBaseService<CorrelationPropertyInfo> implements CorrelationPropertyInfoService{

}
