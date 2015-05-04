package com.dc.esb.servicegov.service;

import java.util.List;
import java.util.Map;

import com.dc.esb.servicegov.entity.SLAView;
import com.dc.esb.servicegov.vo.SLAViewInfoVO;

public interface SLAViewManager {
	public abstract List<SLAView> getAllSLAInfo();
	public abstract List<SLAViewInfoVO> getExportInfo(Map<String, String> conditions);
}
