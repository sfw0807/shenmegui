package com.dc.esb.servicegov.refactoring.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dc.esb.servicegov.refactoring.service.impl.LogManagerImpl;

public class UserOperationLogUtil {

	private static LogManagerImpl logManager = null;
	
	public static void init() {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		logManager = context.getBean(LogManagerImpl.class);
	}
	
	public static LogManagerImpl getLogManager() {
		if (logManager == null) {
			init();
		}
		return logManager;
	}
	
	public static void saveLog(String detail) {
		if (logManager == null) {
			init();
		}
		logManager.save(detail);
	}
}
