package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.ContextMappingInfo;
import com.dc.esb.servicegov.service.ContextMappingInfoService;

@Service
@Transactional
public class ContextMappingInfoServiceImpl extends BaseServiceImpl<ContextMappingInfo,String> implements ContextMappingInfoService{

}
