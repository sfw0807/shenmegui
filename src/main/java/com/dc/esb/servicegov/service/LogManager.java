package com.dc.esb.servicegov.service;

import java.util.List;

import com.dc.esb.servicegov.entity.LogInfo;

public interface LogManager {
	public List<LogInfo> getLogList();
	public boolean setLog(String detail, String functionId, String type);
}
