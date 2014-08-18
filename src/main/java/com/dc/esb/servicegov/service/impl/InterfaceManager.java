package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.InterfaceDAOImpl;
import com.dc.esb.servicegov.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.dao.impl.ServiceInvokeRelationDAOImpl;
import com.dc.esb.servicegov.entity.Interface;
import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.entity.ServiceInvokeRelation;
import com.dc.esb.servicegov.exception.DataException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    private static final Log log = LogFactory.getLog(InterfaceManager.class);
    @Autowired
    private ServiceDAOImpl serviceDAO;
    @Autowired
    private InterfaceDAOImpl interfaceDAO;
    @Autowired
    private ServiceInvokeRelationDAOImpl serviceInvokeRelationDAO;

    public List<Interface> getAllInterfaces() {
        return interfaceDAO.getAll();
    }

    public List<Interface> getInterfacesByOperation(String operationId) {
        List<Interface> interfaces = null;
        if (null != operationId) {
            interfaces = interfaceDAO.findBy("serviceId", operationId);
        }
        return interfaces;
    }

    public Service getServiceByInterfaceId(String interfaceId) throws DataException {
        Service service = null;
        List<ServiceInvokeRelation> serviceInvokeRelations = serviceInvokeRelationDAO.findBy("interfaceId", interfaceId);
        if(null == serviceInvokeRelations){
            String errorMsg = "接口["+interfaceId+"]调用关系不存在";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        //Todo 调用关系表中存在重复的接口。因为，1.调用方不同。2场景也可能不同。
//        if(serviceInvokeRelations.size() > 1){
//            String errorMsg = "接口["+interfaceId+"]存在多个调用关系";
//            log.error(errorMsg);
//            throw new DataException(errorMsg);
//        }
        ServiceInvokeRelation serviceInvokeRelation = serviceInvokeRelations.get(0);
        String serviceId = serviceInvokeRelation.getServiceId();
        List<Service> services = serviceDAO.findBy("serviceId", serviceId);
        if(null != services){
            service = services.get(0);
        } else{
            String errorMsg = "接口["+interfaceId+"]对应的服务["+serviceId+"]不存在！";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        return service;
    }
}
