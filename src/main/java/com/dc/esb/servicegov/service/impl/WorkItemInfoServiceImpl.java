package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.WorkItemInfoDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.WorkItemInfo;
import com.dc.esb.servicegov.service.WorkItemInfoService;

@Service
@Transactional
public class WorkItemInfoServiceImpl extends AbstractBaseService<WorkItemInfo,String> implements WorkItemInfoService{

    @Autowired
    private WorkItemInfoDAOImpl workItemInfoDAO;

    @Override
    public HibernateDAO<WorkItemInfo, String> getDAO() {
        return workItemInfoDAO;
    }
}
