package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceServiceImpl extends AbstractBaseService<com.dc.esb.servicegov.entity.Service, String> {

    @Autowired
    private ServiceDAOImpl serviceDAOImpl;

    @Override
    public HibernateDAO<com.dc.esb.servicegov.entity.Service, String> getDAO() {
        return serviceDAOImpl;
    }

    public com.dc.esb.servicegov.entity.Service getUniqueByServiceId(String serviceId) {
        return super.findUniqueBy("serviceId", serviceId);
    }
}
