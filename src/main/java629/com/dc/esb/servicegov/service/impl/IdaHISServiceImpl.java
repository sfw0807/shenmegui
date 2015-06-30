package com.dc.esb.servicegov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dc.esb.servicegov.dao.impl.IdaHISDAOImpl;

@Service
@Transactional
public class IdaHISServiceImpl {
	@Autowired
	private IdaHISDAOImpl IdaHISDAOImpl;
}
