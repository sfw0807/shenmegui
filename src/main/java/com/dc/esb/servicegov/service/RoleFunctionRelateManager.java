package com.dc.esb.servicegov.service;

import java.util.List;

import com.dc.esb.servicegov.entity.Function;

public interface RoleFunctionRelateManager {
	public List<Function> getFunctionByRole(String roleId);
}
