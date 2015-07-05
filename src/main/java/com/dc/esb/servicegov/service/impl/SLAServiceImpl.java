package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.SLADAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.SLA;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SLAServiceImpl extends AbstractBaseService<SLA, String> {

    @Autowired
    private SLADAOImpl slaDAOImpl;
    @Autowired
    private ServiceServiceImpl serviceService;
    @Autowired
    private OperationServiceImpl operationService;

    @Override
    public HibernateDAO<SLA, String> getDAO() {
        return slaDAOImpl;
    }

    public List<SLA> getTemplateSLA(Map<String, String> params) {
        return slaDAOImpl.findTemplateBy(params);
    }

}