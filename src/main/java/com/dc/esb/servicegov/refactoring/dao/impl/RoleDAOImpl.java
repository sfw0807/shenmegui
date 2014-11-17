package com.dc.esb.servicegov.refactoring.dao.impl;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.Role;

/**
 * 
 * @author G
 * 
 */
@Repository
@Transactional
public class RoleDAOImpl extends HibernateDAO<Role, String> {
	private Log log = LogFactory.getLog(RoleDAOImpl.class);
	public Integer getMaxId(){
		String hql = "SELECT MAX(id) FROM Role";
		Query query = getSession().createQuery(hql);
		Object obj = query.uniqueResult();
		return (Integer)obj;
	}
}
