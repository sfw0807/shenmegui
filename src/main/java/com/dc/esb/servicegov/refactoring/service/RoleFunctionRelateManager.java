package com.dc.esb.servicegov.refactoring.service;

import java.util.List;
import java.util.Map;

import com.dc.esb.servicegov.refactoring.entity.Function;
import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.vo.InvokeInfoVo;
import com.dc.esb.servicegov.refactoring.vo.SystemInvokeServiceInfo;

public interface RoleFunctionRelateManager {
	public List<Function> getFunctionByRole(String roleId);
}
