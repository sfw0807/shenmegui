package com.dc.esb.servicegov.dao.impl;

import com.dc.esb.servicegov.entity.Operation;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.entity.SDA;
@Repository
public class SDADAOImpl extends BaseDAOImpl<SDA> {
    public SDA findRootByOperation(Operation operation){
        String hql = " from " + SDA.class.getName() + " as s where s.serviceId=? and s.operationId=? and s.parentId is null";
        SDA sda = this.findUnique(hql, operation.getServiceId(), operation.getOperationId());
        return  sda;
    }
}
