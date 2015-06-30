package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.TagDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.Tag;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation= Propagation.REQUIRED , rollbackFor = Exception.class)
public class TagServiceImpl extends AbstractBaseService<Tag,String> {

    @Autowired
    private TagDAOImpl tagDAO;


    @Override
    public HibernateDAO<Tag, String> getDAO() {
        return tagDAO;
    }
}
