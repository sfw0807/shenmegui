package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.ServiceInvokeRelationDAOImpl;
import com.dc.esb.servicegov.entity.ServiceInvokeRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ServiceInvokeRelationManagerImpl {
	@Autowired
	private ServiceInvokeRelationDAOImpl serviceInvokeRelationDAO;
	
	
	public List<ServiceInvokeRelation> getDupRealtion(){
		return serviceInvokeRelationDAO.getDupRelation();
	}
}
