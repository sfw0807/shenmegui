package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.OperationHis;
import com.dc.esb.servicegov.service.OperationHisService;

@Service
@Transactional
public class OperationHisServiceImpl extends AbstractBaseService<OperationHis> implements OperationHisService{
	
}
