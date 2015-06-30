package com.dc.esb.servicegov.service;


import com.dc.esb.servicegov.entity.InterfaceHead;
import com.dc.esb.servicegov.service.support.BaseService;

public interface InterfaceHeadService extends BaseService<InterfaceHead, String> {
    public void initHDA(InterfaceHead interfaceHead);
}
