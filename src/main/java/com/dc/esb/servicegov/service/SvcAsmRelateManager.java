package com.dc.esb.servicegov.service;

import java.util.List;
import java.util.Map;

import com.dc.esb.servicegov.vo.SvcAsmRelateInfoVO;

public abstract interface SvcAsmRelateManager {
	public abstract List<SvcAsmRelateInfoVO> getAllSvcAsmRelateInfo();
	public abstract List<SvcAsmRelateInfoVO> getInfosByConditions(Map<String, String> mapConditions);
	public abstract List<SvcAsmRelateInfoVO> getAllServiceDetailsInfo();
	public abstract List<SvcAsmRelateInfoVO> getServiceDetailsInfoByConditions(Map<String, String> mapConditions);
	public abstract List<SvcAsmRelateInfoVO> getAllExportInvokeInfos();
}