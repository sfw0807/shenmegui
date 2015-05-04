package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.InterfaceDAOImpl;
import com.dc.esb.servicegov.dao.impl.SystemDAOImpl;
import com.dc.esb.servicegov.entity.System;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-9
 * Time: 下午3:25
 */
@Component
@Transactional
public class SystemManager {
    @Autowired
    private SystemDAOImpl systemDAO;

    public List<System> getAllSystems() {
        return systemDAO.getAll();
    }

    
    public System getSystemById(String systemId) {
    	return systemDAO.findUniqueBy("systemId", systemId);
    }
}
