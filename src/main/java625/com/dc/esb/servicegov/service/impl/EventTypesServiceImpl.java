package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.EventTypes;
import com.dc.esb.servicegov.service.EventTypesService;

@Service
@Transactional
public class EventTypesServiceImpl extends BaseServiceImpl<EventTypes,String> implements EventTypesService{

}
