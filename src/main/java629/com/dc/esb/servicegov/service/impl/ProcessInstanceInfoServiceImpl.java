package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.ProcessInstanceInfo;
import com.dc.esb.servicegov.service.ProcessInstanceInfoService;

@Service
@Transactional
public class ProcessInstanceInfoServiceImpl extends AbstractBaseService<ProcessInstanceInfo> implements ProcessInstanceInfoService{

}
