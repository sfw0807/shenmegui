package com.dc.esb.servicegov.service;

import com.dc.esb.servicegov.entity.Interface;
import com.dc.esb.servicegov.service.support.BaseService;

public interface InterfaceService  extends BaseService<Interface, String> {
    public Interface getInterfaceById(String hql,String interfaceId);
}
