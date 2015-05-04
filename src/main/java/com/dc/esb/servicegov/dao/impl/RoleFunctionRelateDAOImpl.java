package com.dc.esb.servicegov.dao.impl;


import com.dc.esb.servicegov.entity.RoleFunctionRelate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author G
 */
@Repository
@Transactional
public class RoleFunctionRelateDAOImpl extends HibernateDAO<RoleFunctionRelate, String> {
    private Log log = LogFactory.getLog(RoleFunctionRelateDAOImpl.class);
}
