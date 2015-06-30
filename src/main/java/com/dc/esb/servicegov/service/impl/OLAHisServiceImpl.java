package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.OLAHis;
import com.dc.esb.servicegov.service.OLAHisService;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 没有DAO
 */
@Service
@Transactional
public class OLAHisServiceImpl extends AbstractBaseService<OLAHis,String> implements OLAHisService{


    @Override
    public HibernateDAO<OLAHis, String> getDAO() {
        return null;
    }
}
