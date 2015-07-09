package com.dc.esb.servicegov.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.UserRoleRelation;
@Repository
@Transactional
public class UserRoleRelationDAOImpl extends HibernateDAO<UserRoleRelation, String>{

}
