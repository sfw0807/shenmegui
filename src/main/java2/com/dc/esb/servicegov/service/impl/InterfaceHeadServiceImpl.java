package com.dc.esb.servicegov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dc.esb.servicegov.dao.impl.InterfaceHeadDAOImpl;
@Service
@Transactional
public class InterfaceHeadServiceImpl {
	@Autowired
	private InterfaceHeadDAOImpl InterfaceHeadDAOImpl; 
}
