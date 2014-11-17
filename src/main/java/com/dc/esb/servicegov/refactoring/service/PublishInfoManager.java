package com.dc.esb.servicegov.refactoring.service;

import java.util.List;
import java.util.Map;

import com.dc.esb.servicegov.refactoring.vo.PublishInfoVO;


public abstract interface PublishInfoManager {
	public abstract List<PublishInfoVO> getAllPublishTotalInfos(Map<String,String> mapConditions);

}