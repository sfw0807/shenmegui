package com.dc.esb.servicegov.refactoring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.LogInfoDAOImpl;

@Component
@Transactional
public class LogInfoServiceImpl {

	@Autowired
	LogInfoDAOImpl logInfoDAO;
	
}
