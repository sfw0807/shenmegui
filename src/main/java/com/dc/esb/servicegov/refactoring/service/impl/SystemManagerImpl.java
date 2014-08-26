package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.SystemDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.System;
import com.dc.esb.servicegov.refactoring.service.SystemManager;

@Service
@Transactional
public class SystemManagerImpl implements SystemManager{
	
	@Autowired
	private SystemDAOImpl systemDAO;

	@Override
	public List<System> getAllSystems() {
		List<System> systems = new ArrayList<System>();
		systems = systemDAO.getAll();
		return systems;
	}
}
