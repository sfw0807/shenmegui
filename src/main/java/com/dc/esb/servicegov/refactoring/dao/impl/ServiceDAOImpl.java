package com.dc.esb.servicegov.refactoring.dao.impl;

import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.Service;

@Repository
public class ServiceDAOImpl extends HibernateDAO<Service, String> {

}
