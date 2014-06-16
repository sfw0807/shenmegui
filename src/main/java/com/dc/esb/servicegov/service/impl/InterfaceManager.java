package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.InterfaceDAOImpl;
import com.dc.esb.servicegov.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.entity.Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-9
 * Time: 上午11:51
 */
@Component
public class InterfaceManager {
    @Autowired
    private ServiceDAOImpl serviceDAO;
    @Autowired
    private InterfaceDAOImpl interfaceDAO;

    public List<Interface> getAllInterfaces(){
        return interfaceDAO.getAll();
    }

    public List<Interface> getInterfacesByOperation(String operationId){
        List<Interface> interfaces = null;
        if(null != operationId){
            interfaces = interfaceDAO.findBy("serviceId", operationId);
        }
        return interfaces;
    }
}
