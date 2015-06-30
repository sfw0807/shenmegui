package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.SessionInfoDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.SessionInfo;
import com.dc.esb.servicegov.service.SessionInfoService;

@Service
@Transactional
public class SessionInfoServiceImpl extends AbstractBaseService<SessionInfo,String> implements SessionInfoService{

    @Autowired
    private SessionInfoDAOImpl sessionInfoDAO;


    @Override
    public HibernateDAO<SessionInfo, String> getDAO() {
        return null;
    }
}
