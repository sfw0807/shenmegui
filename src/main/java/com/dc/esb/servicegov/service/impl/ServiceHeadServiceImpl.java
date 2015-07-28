package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.ServiceHeadDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.ServiceHead;
import com.dc.esb.servicegov.service.ServiceHeadService;

@Service
@Transactional
public class ServiceHeadServiceImpl extends AbstractBaseService<ServiceHead,String> implements ServiceHeadService{

    @Autowired
    private ServiceHeadDAOImpl serviceHeadDAO;

    @Override
    public HibernateDAO<ServiceHead, String> getDAO() {
        return null;
    }
}
