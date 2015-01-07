package com.dc.esb.servicegov.refactoring.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.LogInfoDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.LogInfo;
import com.dc.esb.servicegov.refactoring.entity.User;
import com.dc.esb.servicegov.refactoring.service.LogManager;
import com.dc.esb.servicegov.refactoring.util.LoginUtil;
import com.dc.esb.servicegov.refactoring.util.UUIDUtil;

@Service
@Transactional
public class LogManagerImpl implements LogManager{

	@Autowired
	LogInfoDAOImpl logDAO;
	
	public void save(String detail) {
		LogInfo entity = new LogInfo();
		User user = LoginUtil.getUser();
		entity.setId(UUIDUtil.getUUID());
		entity.setUserId(String.valueOf(user.getId()));
		entity.setTime(new Timestamp(System.currentTimeMillis()));
		entity.setDetail(detail);
		entity.setFunctionId("");
		entity.setType("");
		logDAO.save(entity);
	}
	
}
