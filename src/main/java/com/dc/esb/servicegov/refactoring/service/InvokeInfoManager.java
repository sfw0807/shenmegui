package com.dc.esb.servicegov.refactoring.service;

import java.util.List;

import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.vo.SystemInvokeServiceInfo;

public interface InvokeInfoManager {
	
	public List<SystemInvokeServiceInfo> getSystemInvokeServiceInfo();
	public List<InvokeInfo> getInvokeInfos();

}
