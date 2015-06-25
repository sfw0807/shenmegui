package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dc.esb.servicegov.service.SystemService;

@Service
@Transactional
public class SystemServiceImpl extends BaseServiceImpl<System> implements SystemService{

}
