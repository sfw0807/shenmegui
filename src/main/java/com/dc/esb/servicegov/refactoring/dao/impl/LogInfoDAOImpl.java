package com.dc.esb.servicegov.refactoring.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.LogInfo;

/**
 * 
 * @author G
 * 
 */
@Repository
@Transactional
public class LogInfoDAOImpl extends HibernateDAO<LogInfo, String> {
	
	private Log log = LogFactory.getLog(LogInfoDAOImpl.class);
	
}
