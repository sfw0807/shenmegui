package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.OLATemplate;
import com.dc.esb.servicegov.service.OLATemplateService;

@Service
@Transactional
public class OLATemplateServiceImpl extends BaseServiceImpl<OLATemplate,String> implements OLATemplateService{

}
