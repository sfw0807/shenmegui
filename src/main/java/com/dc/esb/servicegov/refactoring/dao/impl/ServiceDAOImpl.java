package com.dc.esb.servicegov.refactoring.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.Service;

@Repository
public class ServiceDAOImpl extends HibernateDAO<Service, String> {

	/**
	 * new transaction to save service
	 * @param service
	 */
	public void TxSaveService(com.dc.esb.servicegov.refactoring.entity.Service service){
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(service);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}
		session.close();
	}
}
