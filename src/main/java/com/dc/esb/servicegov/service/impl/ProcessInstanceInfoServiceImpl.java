package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.ProcessInstanceInfo;
import com.dc.esb.servicegov.service.ProcessInstanceInfoService;

/**
 * TODO no DAO
 */
@Service
@Transactional
public class ProcessInstanceInfoServiceImpl extends AbstractBaseService<ProcessInstanceInfo,String> implements ProcessInstanceInfoService{

    @Override
    public HibernateDAO<ProcessInstanceInfo, String> getDAO() {
        return null;
    }
}
