package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.SessionInfo;
import com.dc.esb.servicegov.service.SessionInfoService;

@Service
@Transactional
public class SessionInfoServiceImpl extends AbstractBaseService<SessionInfo> implements SessionInfoService{

}
