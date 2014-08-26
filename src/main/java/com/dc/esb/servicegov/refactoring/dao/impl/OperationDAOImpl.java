package com.dc.esb.servicegov.refactoring.dao.impl;

import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.OperationPK;

@Repository
public class OperationDAOImpl extends HibernateDAO<Operation, OperationPK> {

}
