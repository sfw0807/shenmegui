package com.dc.esb.servicegov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.BVMappingDAOImpl;
import com.dc.esb.servicegov.entity.BaseLineVersionHisMapping;

@Service
@Transactional
public class BVServiceImpl extends BaseServiceImpl<BaseLineVersionHisMapping>{
	@Autowired
	private BVMappingDAOImpl daoImpl;
	
}
