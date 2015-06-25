package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.WorkItemInfo;
import com.dc.esb.servicegov.service.WorkItemInfoService;

@Service
@Transactional
public class WorkItemInfoServiceImpl extends BaseServiceImpl<WorkItemInfo,String> implements WorkItemInfoService{

}
