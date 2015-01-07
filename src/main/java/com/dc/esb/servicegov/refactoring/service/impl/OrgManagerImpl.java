package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.controller.OrgController;
import com.dc.esb.servicegov.refactoring.dao.impl.OrganizationDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.Organization;
import com.dc.esb.servicegov.refactoring.service.OrgManager;

@Service
@Transactional
public class OrgManagerImpl implements OrgManager{
	private Log log = LogFactory.getLog(OrgManagerImpl.class);
	@Autowired
	private OrganizationDAOImpl orgDAO;
	public List<Organization> getAll() {
		return orgDAO.getAll();
	}
	public Organization getOrgById(String id) {
		return orgDAO.findUniqueBy("orgId", id);
	}
	public boolean save(Organization org) {
		boolean flag = false;
		try{
			orgDAO.save(org);
			flag = true;
		}catch(Exception e){
			log.error(e, e);
		}
		return flag;
	}
	public boolean delete(Organization org) {
		boolean flag = false;
		try{
			orgDAO.delete(org);
			flag = true;
		}catch(Exception e){
			log.error(e, e);
		}
		return flag;
	}
}
