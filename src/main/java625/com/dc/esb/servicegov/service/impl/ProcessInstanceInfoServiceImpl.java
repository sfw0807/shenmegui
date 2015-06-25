package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.ProcessInstanceInfo;
import com.dc.esb.servicegov.service.ProcessInstanceInfoService;

@Service
@Transactional
public class ProcessInstanceInfoServiceImpl extends BaseServiceImpl<ProcessInstanceInfo,String> implements ProcessInstanceInfoService{

}
