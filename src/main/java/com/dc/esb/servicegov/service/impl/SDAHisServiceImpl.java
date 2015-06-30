package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.SDAHis;
import com.dc.esb.servicegov.service.SDAHisService;

/**
 * no dao
 */
@Service
@Transactional
public class SDAHisServiceImpl extends AbstractBaseService<SDAHis,String> implements SDAHisService{


    @Override
    public HibernateDAO<SDAHis, String> getDAO() {
        return null;
    }
}
