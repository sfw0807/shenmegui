package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.OlaTemplateDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.OLATemplate;
import com.dc.esb.servicegov.service.OLATemplateService;

@Service
@Transactional
public class OLATemplateServiceImpl extends AbstractBaseService<OLATemplate,String> implements OLATemplateService{

    @Autowired
    private OlaTemplateDAOImpl olaTemplateDAO;

    @Override
    public HibernateDAO<OLATemplate, String> getDAO() {
        return olaTemplateDAO;
    }
}
