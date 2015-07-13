package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.ServiceInvokeDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.InvokeConnection;
import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.ServiceInvokeService;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServiceInvokeServiceImpl extends AbstractBaseService<ServiceInvoke, String> implements ServiceInvokeService{

	@Autowired
	private ServiceInvokeDAOImpl serviceInvokeDAOImpl;

	@Override
	public HibernateDAO<ServiceInvoke, String> getDAO() {
		return serviceInvokeDAOImpl;
	}




}
