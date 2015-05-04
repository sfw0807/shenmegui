package com.dc.esb.servicegov.dao.impl;


import com.dc.esb.servicegov.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author G
 */
@Repository
@Transactional
public class UserDAOImpl extends HibernateDAO<User, String> {
    private Log log = LogFactory.getLog(UserDAOImpl.class);

    public Integer getMaxId() {
        String hql = "SELECT MAX(id) FROM User";
        Query query = getSession().createQuery(hql);
        Object obj = query.uniqueResult();
        return (Integer) obj;
    }
}
