package com.dc.esb.servicegov.refactoring.service;

import java.util.List;

import com.dc.esb.servicegov.refactoring.entity.LogInfo;

public interface LogManager {
	public List<LogInfo> getLogList();
	public boolean setLog(String detail, String functionId, String type);
}
