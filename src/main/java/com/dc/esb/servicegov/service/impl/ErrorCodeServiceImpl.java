package com.dc.esb.servicegov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.ErrorCodeDAOImpl;
import com.dc.esb.servicegov.entity.ErrorCode;
import com.dc.esb.servicegov.service.ErrorCodeService;

@Service
@Transactional
public class ErrorCodeServiceImpl implements ErrorCodeService {

	@Autowired
	private ErrorCodeDAOImpl errorCodeDAOImpl;
	
	@Override
	public void insertErrorCode(ErrorCode errorCode) {
		errorCodeDAOImpl.save(errorCode);
	}

}
