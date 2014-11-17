package com.dc.esb.servicegov.refactoring.dao.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.Function;
import com.dc.esb.servicegov.refactoring.entity.IDA;
import com.dc.esb.servicegov.refactoring.entity.Interface;

/**
 * 
 * @author G
 * 
 */
@Repository
@Transactional
public class FunctionDAOImpl extends HibernateDAO<Function, String> {
	private Log log = LogFactory.getLog(FunctionDAOImpl.class);
	public Integer getMaxId(){
		String hql = "SELECT MAX(id) FROM Function";
		Query query = getSession().createQuery(hql);
		Object obj = query.uniqueResult();
		return (Integer)obj;
	}
}
