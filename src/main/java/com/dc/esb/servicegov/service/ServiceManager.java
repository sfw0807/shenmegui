package com.dc.esb.servicegov.service;

import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.vo.SDA;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Vincent Fan
 * Date: 14-8-22
 * Time: 下午5:03
 */
public interface ServiceManager {
    public List<Service> getAllServices();
    public Service getServiceById();
}
