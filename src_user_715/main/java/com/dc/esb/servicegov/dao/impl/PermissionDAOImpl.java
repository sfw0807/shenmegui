package com.dc.esb.servicegov.dao.impl;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.Permission;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lenovo on 2015/7/13.
 */
@Repository
@Transactional
public class PermissionDAOImpl extends HibernateDAO<Permission,String> {
}
