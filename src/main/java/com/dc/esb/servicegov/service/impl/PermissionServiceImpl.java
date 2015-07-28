package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.PermissionDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.Permission;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lenovo on 2015/7/13.
 */
@Service
@Transactional
public class PermissionServiceImpl extends AbstractBaseService<Permission,String> {
    @Autowired
    private PermissionDAOImpl permissionDAOImpl;
    @Override
    public HibernateDAO<Permission, String> getDAO() {
        return permissionDAOImpl;
    }
}
