package com.dc.esb.servicegov.service;

import java.util.List;

import com.dc.esb.servicegov.entity.Function;

public interface FunctionManager {

	public List<Function> getAll();
	public String getFunctionNameById(String id);
	public Integer getMaxId();
	public boolean save(Function function);
	public Function getFunctionById(String functionId);
	public boolean delete(Function function);
}
