package com.dc.esb.servicegov.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.LogInfoDAOImpl;
import com.dc.esb.servicegov.entity.LogInfo;
import com.dc.esb.servicegov.entity.User;
import com.dc.esb.servicegov.service.LogManager;
import com.dc.esb.servicegov.util.LoginUtil;
import com.dc.esb.servicegov.util.UUIDUtil;

@Service
@Transactional
public class LogManagerImpl implements LogManager{
	private Log log = LogFactory.getLog(LogManagerImpl.class);
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
	
	public void save(String detail, String functionId) {
		LogInfo entity = new LogInfo();
		User user = LoginUtil.getUser();
		entity.setId(UUIDUtil.getUUID());
//		if (user == null) {
//			entity.setUserId(String.valueOf(user.getId()));
//		} else {
			// 默认用户 unlogin
			entity.setUserId("2");
//		}
		entity.setTime(new Timestamp(System.currentTimeMillis()));
		entity.setDetail(detail);
		entity.setFunctionId(functionId);
		entity.setType("");
		logDAO.save(entity);
	}
	
	@Override
	public List<LogInfo> getLogList() {
		return logDAO.getAll();
	}

	@Override
	public boolean setLog(String detail ,String functionId,String type) {
		boolean flag = false;
		try{
			LogInfo entity = new LogInfo();
			User user = LoginUtil.getUser();
			if(null!=user){
				entity.setId(UUIDUtil.getUUID());
				entity.setUserId(String.valueOf(user.getId()));
				entity.setTime(new Timestamp(System.currentTimeMillis()));
				entity.setDetail(detail);
				entity.setFunctionId(functionId);
				entity.setType(type);
				logDAO.save(entity);
				flag = true;
			}else{
				log.error("用户未登录,日志信息记录失败");
			}
		}catch(Exception e){
			log.error(e,e);
		}
		return flag;
	}
}
