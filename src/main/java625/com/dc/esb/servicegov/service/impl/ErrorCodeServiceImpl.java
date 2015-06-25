package com.dc.esb.servicegov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dc.esb.servicegov.entity.ErrorCode;
import com.dc.esb.servicegov.service.ErrorCodeService;

@Service
@Transactional
public class ErrorCodeServiceImpl extends BaseServiceImpl<ErrorCode,String> implements ErrorCodeService {


}
