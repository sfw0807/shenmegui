package com.dc.esb.servicegov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.WorkItemHandlerDAOImpl;

@Service
@Transactional
public class WorkItemHandlerServiceImpl {
	@Autowired
	private WorkItemHandlerDAOImpl workItemHandlerDAOImpl;
}
