package com.dc.esb.servicegov.refactoring.service;

import java.util.List;
import java.util.Map;

import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.vo.InvokeInfoVo;
import com.dc.esb.servicegov.refactoring.vo.SystemInvokeServiceInfo;

public interface InvokeInfoManager {
	
	public List<SystemInvokeServiceInfo> getSystemInvokeServiceInfo(Map<String,String> mapConditions);
	public boolean deleteInvokeInfo(String operationId,String serviceId);
	public List<InvokeInfoVo> getAllInvokeInfo();
	public List<InvokeInfo> getInvokeInfoByPSys(String pid);
	public List<InvokeInfo> getInvokeInfoByCSys(String cid);
	//根据接口Id获取InvokeInfo信息。并且调用重写方法去重 
	public List<InvokeInfo> getDuplicateInvokeByEcode(String[] interfaceIds);
}
