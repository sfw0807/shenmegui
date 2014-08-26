package com.dc.esb.servicegov.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.InterfaceDAOImpl;
import com.dc.esb.servicegov.dao.impl.SystemDAOImpl;
import com.dc.esb.servicegov.entity.Interface;
import com.dc.esb.servicegov.entity.System;

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
    @Autowired
    private InterfaceDAOImpl interfaceDAO;

    public List<System> getAllSystems() {
        return systemDAO.getAll();
    }

    public System getSystemByInterface(String interfaceId) {
        Interface in = interfaceDAO.findUniqueBy("interfaceId", interfaceId);
        String systemId = in.getSysId();
        System system = systemDAO.findUniqueBy("systemId", systemId);
        return system;
    }
    
    public System getSystemById(String systemId) {
    	return systemDAO.findUniqueBy("systemId", systemId);
    }
}
