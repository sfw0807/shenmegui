package com.dc.esb.servicegov.dao.impl;


import com.dc.esb.servicegov.entity.Organization;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author G
 */
@Repository
@Transactional
public class OrganizationDAOImpl extends HibernateDAO<Organization, String> {
    private Log log = LogFactory.getLog(OrganizationDAOImpl.class);
}
