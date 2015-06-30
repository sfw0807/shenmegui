package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.OlaDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.OLA;
import com.dc.esb.servicegov.service.OLAService;

@Service
@Transactional
public class OLAServiceImpl extends AbstractBaseService<OLA,String> implements OLAService{

    @Autowired
    private OlaDAOImpl olaDAO;

    @Override
    public HibernateDAO<OLA, String> getDAO() {
        return olaDAO;
    }
}
