package com.dc.esb.servicegov.service.impl;


import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.System;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.SystemDAOImpl;
import com.dc.esb.servicegov.service.SystemService;

@Service
@Transactional
public class SystemServiceImpl extends AbstractBaseService<System, String> implements SystemService{

	@Autowired
	private SystemDAOImpl systemDAOImpl;


	@Override
	public HibernateDAO<System, String> getDAO() {
		return systemDAOImpl;
	}
}
