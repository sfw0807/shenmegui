package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.InterfaceHIS;
import com.dc.esb.servicegov.service.InterfaceHISService;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.InterfaceHISDAOImpl;
@Service
@Transactional
public class InterfaceHISServiceImpl extends AbstractBaseService<InterfaceHIS, String>{
	@Autowired
	private InterfaceHISDAOImpl interfaceHISDAOImpl;

	@Override
	public HibernateDAO<InterfaceHIS, String> getDAO() {
		return interfaceHISDAOImpl;
	}
}
