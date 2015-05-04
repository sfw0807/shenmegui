package com.dc.esb.servicegov.dao.impl;

import com.dc.esb.servicegov.entity.Service;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceDAOImpl extends HibernateDAO<Service, String> {

    /**
     * new transaction to save service
     *
     * @param service
     */
    public void TxSaveService(Service service) {
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
