package com.dc.esb.servicegov.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.VersionHisDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.VersionHis;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
@Service
@Transactional
public class VersionHisServiceImpl  extends AbstractBaseService<VersionHis, String>{
	@Autowired
	VersionHisDAOImpl hisDaoImpl;
	
	@Override
	public void save(VersionHis entity){
		hisDaoImpl.save(entity);
	}
	
	public List<VersionHis> hisVersionList(String keyValue){
		return hisDaoImpl.hisVersionList(keyValue);
	}
	
	public void updateVerionHis(String type, String[] versionHisIds){
		hisDaoImpl.updateVerionHis(type, versionHisIds);
	}

	@Override
	public HibernateDAO<VersionHis, String> getDAO() {
		return hisDaoImpl;
	}
}
