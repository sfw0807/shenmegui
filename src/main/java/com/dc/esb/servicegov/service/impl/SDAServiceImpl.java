package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.SdaDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.SDA;
import com.dc.esb.servicegov.service.SDAService;

@Service
@Transactional
public class SDAServiceImpl extends AbstractBaseService<SDA,String> implements SDAService{

    @Autowired
    private SdaDAOImpl sdaDAO;

    @Override
    public HibernateDAO<SDA, String> getDAO() {
        return sdaDAO;
    }
}
