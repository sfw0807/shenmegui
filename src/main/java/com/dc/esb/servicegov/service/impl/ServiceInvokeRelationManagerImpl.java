package com.dc.esb.servicegov.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.ServiceInvokeRelationDAOImpl;
import com.dc.esb.servicegov.entity.ServiceInvokeRelation;

@Component
@Transactional
public class ServiceInvokeRelationManagerImpl {
	@Autowired
	private ServiceInvokeRelationDAOImpl serviceInvokeRelationDAO;
	
	
	public List<ServiceInvokeRelation> getDupRealtion(){
		return null;
	}
}
