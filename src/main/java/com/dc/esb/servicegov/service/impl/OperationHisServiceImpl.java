package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.OperationHis;
import com.dc.esb.servicegov.service.OperationHisService;

/**
 * TODO 没有dao
 */
@Service
@Transactional
public class OperationHisServiceImpl extends AbstractBaseService<OperationHis,String> implements OperationHisService{

    @Override
    public HibernateDAO<OperationHis, String> getDAO() {
        return null;
    }
}
