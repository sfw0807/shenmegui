package com.dc.esb.servicegov.dao.impl;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.SGUser;
import org.springframework.stereotype.Repository;

/**
 * Created by vincentfxz on 15/7/2.
 */
@Repository
public class UserDAOImpl extends HibernateDAO<SGUser, String> {
}
