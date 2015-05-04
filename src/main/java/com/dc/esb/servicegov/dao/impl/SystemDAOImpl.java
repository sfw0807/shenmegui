package com.dc.esb.servicegov.dao.impl;

import com.dc.esb.servicegov.entity.System;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SystemDAOImpl extends HibernateDAO<System, String> {
    private Log log = LogFactory.getLog(SystemDAOImpl.class);

    // delete batch

    public void batchDelete(List<System> list) {

        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            int count = 0;
            for (System system : list) {
                session.delete(system);
                if (++count % 30 == 0) {
                    getSession().flush();
                    getSession().clear();
                }
            }
            tx.commit();
        } catch (Exception e) {
            log.error("batch delete data failed!");
            tx.rollback();
        } finally {
            session.close();
        }
    }

    // get systemId by systemAb
    public String getSystemIdByAb(String systemAb) {
        System system = this.findUniqueBy("systemAb", systemAb);
        if (system == null) {
            system = new System();
        }
        return (system.getSystemId() == null) ? "" : system.getSystemId();
    }

    // get systemAb by systemId
    public String getSystemAbById(String systemId) {
        System system = this.findUniqueBy("systemId", systemId);
        if (system == null) {
            system = new System();
        }
        return (system.getSystemAb() == null) ? "" : system.getSystemAb();
    }
}
