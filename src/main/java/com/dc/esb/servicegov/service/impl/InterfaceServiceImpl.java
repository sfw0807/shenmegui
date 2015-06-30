package com.dc.esb.servicegov.service.impl;

import java.util.List;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.InterfaceDAOImpl;
import com.dc.esb.servicegov.entity.Interface;
import com.dc.esb.servicegov.service.InterfaceService;
@Service
@Transactional
public class InterfaceServiceImpl extends AbstractBaseService<Interface, String> implements InterfaceService{
	@Autowired
	private InterfaceDAOImpl interfaceDAOImpl;

	@Override
	public HibernateDAO<Interface, String> getDAO() {
		return interfaceDAOImpl;
	}
}
