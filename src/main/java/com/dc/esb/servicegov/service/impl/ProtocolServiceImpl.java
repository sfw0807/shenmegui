package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.Protocol;
import com.dc.esb.servicegov.service.ProtocolService;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dc.esb.servicegov.dao.impl.ProtocolDAOImpl;

@Service
@Transactional
public class ProtocolServiceImpl extends AbstractBaseService<Protocol, String> implements ProtocolService {
	@Autowired
	private ProtocolDAOImpl protocolDAOImpl;

	@Override
	public HibernateDAO<Protocol, String> getDAO() {
		return protocolDAOImpl;
	}
}
