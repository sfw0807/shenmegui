package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.Generator;
import com.dc.esb.servicegov.service.GeneratorService;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import com.dc.esb.servicegov.service.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.GeneratorDAOImpl;

@Service
@Transactional
public class GeneratorServiceImpl extends AbstractBaseService<Generator, String> implements GeneratorService {
	@Autowired
	private  GeneratorDAOImpl generatorDAOImpl;

	@Override
	public HibernateDAO<Generator, String> getDAO() {
		return generatorDAOImpl;
	}
}
