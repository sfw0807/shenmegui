package com.dc.esb.servicegov.dao.impl;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.SGUser;
import com.dc.esb.servicegov.entity.UserRoleRelation;

/**
 * Created by vincentfxz on 15/7/2.
 */
@Repository
@Transactional
public class UserDAOImpl extends HibernateDAO<SGUser, String> {

}
