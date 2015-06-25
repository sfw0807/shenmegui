package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.service.OperationService;

@Service
@Transactional
public class OperationServiceImpl extends BaseServiceImpl<Operation> implements OperationService{
	
}
