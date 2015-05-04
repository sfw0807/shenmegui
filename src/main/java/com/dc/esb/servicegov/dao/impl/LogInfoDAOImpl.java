package com.dc.esb.servicegov.dao.impl;

import com.dc.esb.servicegov.entity.LogInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author G
 */
@Repository
@Transactional
public class LogInfoDAOImpl extends HibernateDAO<LogInfo, String> {

    private static final Log log = LogFactory.getLog(LogInfoDAOImpl.class);

}
