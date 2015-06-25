package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.SessionInfo;
import com.dc.esb.servicegov.service.SessionInfoService;

@Service
@Transactional
public class SessionInfoServiceImpl extends BaseServiceImpl<SessionInfo> implements SessionInfoService{

}
