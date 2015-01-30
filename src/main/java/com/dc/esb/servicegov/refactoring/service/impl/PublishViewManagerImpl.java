package com.dc.esb.servicegov.refactoring.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.PublishViewDAOImpl;
import com.dc.esb.servicegov.refactoring.service.PublishViewManager;

@Service
@Transactional
public class PublishViewManagerImpl implements PublishViewManager{
	private Log log = LogFactory.getLog(PublishViewManagerImpl.class);
	@Autowired
	private PublishViewDAOImpl publishViewDAO;

	@Override
	public Integer countOfConsumerSys(String onlineDate) {
		if(log.isInfoEnabled()){
			log.info("获取上线调用方数量");
		}
		// TODO Auto-generated method stub
		return publishViewDAO.countOfConsumerSys(onlineDate);
	}

	@Override
	public Integer countOfDeletedService(String onlineDate) {
		if(log.isInfoEnabled()){
			log.info("获取上线删除服务数量");
		}
		// TODO Auto-generated method stub
		return publishViewDAO.countOfDeletedService(onlineDate);
	}

	@Override
	public Integer countOfModifyTimes(String onlineDate) {
		if(log.isInfoEnabled()){
			log.info("获取上线服务修订次数");
		}
		// TODO Auto-generated method stub
		return publishViewDAO.countOfModifyTimes(onlineDate);
	}

	@Override
	public Integer countOfOffLine(String onlineDate) {
		if(log.isInfoEnabled()){
			log.info("获取下线服务数量");
		}
		// TODO Auto-generated method stub
		return publishViewDAO.countOfOffLine(onlineDate);
	}

	@Override
	public Integer countOfOperation(String onlineDate) {
		if(log.isInfoEnabled()){
			log.info("获取上线操作数量");
		}
		// TODO Auto-generated method stub
		return publishViewDAO.countOfOperation(onlineDate);
	}

	@Override
	public Integer countOfProviderSys(String onlineDate) {
		if(log.isInfoEnabled()){
			log.info("获取上线提供方数量");
		}
		// TODO Auto-generated method stub
		return publishViewDAO.countOfProviderSys(onlineDate);
	}

	@Override
	public Integer countOfPublishTimes(String onlineDate) {
		if(log.isInfoEnabled()){
			log.info("投产次数");
		}
		// TODO Auto-generated method stub
		return publishViewDAO.countOfPublishTimes(onlineDate);
	}

	@Override
	public Integer countOfService(String onlineDate) {
		if(log.isInfoEnabled()){
			log.info("获取上线服务数量");
		}
		// TODO Auto-generated method stub
		return publishViewDAO.countOfService(onlineDate);
	}
	
}
