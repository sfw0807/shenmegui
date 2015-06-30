package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.SlaDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.SLA;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SLAServiceImpl extends AbstractBaseService<SLA, String>{

    @Autowired
    private SlaDAOImpl slaDAOImpl;

    @Override
    public HibernateDAO<SLA, String> getDAO() {
        return slaDAOImpl;
    }

    public List<SLA> getTemplateSLA(Map<String,String> params){
        return slaDAOImpl.findTemplateBy(params);
    }
}