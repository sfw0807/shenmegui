package com.dc.esb.servicegov.service.impl;

import java.util.List;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dc.esb.servicegov.dao.impl.IdaDAOImpl;
import com.dc.esb.servicegov.entity.Ida;
import com.dc.esb.servicegov.service.IdaService;

@Service
@Transactional
public class IdaServiceImpl extends AbstractBaseService<Ida, String> implements IdaService{
	@Autowired
	private IdaDAOImpl idaDAOImpl;

	@Override
	public HibernateDAO<Ida, String> getDAO() {
		return idaDAOImpl;
	}
}
