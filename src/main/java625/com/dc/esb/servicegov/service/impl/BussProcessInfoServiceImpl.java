package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.BussProcessInfo;
import com.dc.esb.servicegov.service.BussProcessInfoService;

@Service
@Transactional
public class BussProcessInfoServiceImpl extends BaseServiceImpl<BussProcessInfo,String> implements BussProcessInfoService{

}
