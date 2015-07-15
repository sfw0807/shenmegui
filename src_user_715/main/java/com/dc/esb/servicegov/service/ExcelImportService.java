package com.dc.esb.servicegov.service;

import java.util.Map;

public interface ExcelImportService {
	public boolean executeImport(Map<String, Object> infoMap, Map<String, Object> inputMap, Map<String, Object> outMap,Map<String,String> publicMap);

	public boolean existSystem(String ... systemIds);

}
