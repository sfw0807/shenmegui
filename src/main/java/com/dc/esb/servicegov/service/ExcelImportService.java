package com.dc.esb.servicegov.service;

import com.dc.esb.servicegov.entity.Ida;

import java.util.List;
import java.util.Map;

public interface ExcelImportService {
	public boolean executeImport(Map<String, Object> infoMap, Map<String, Object> inputMap, Map<String, Object> outMap,Map<String,String> publicMap,Map<String,Object> headMap);

	public boolean existSystem(String systemId);

}
