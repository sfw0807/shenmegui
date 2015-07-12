package com.dc.esb.servicegov.service;

import com.dc.esb.servicegov.entity.System;
import com.dc.esb.servicegov.entity.SystemProtocol;
import com.dc.esb.servicegov.service.support.BaseService;


public interface SystemService extends BaseService<System, String> {

    public void insertProtocol(SystemProtocol systemProtocol);

    public void deleteProtocol(SystemProtocol systemProtocol);
}
