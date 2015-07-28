package com.dc.esb.servicegov.service;

import com.dc.esb.servicegov.service.impl.ExcelImportServiceImpl;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

public interface ExcelImportService {
    public boolean executeImport(Map<String, Object> infoMap, Map<String, Object> inputMap, Map<String, Object> outMap, Map<String, String> publicMap, Map<String, Object> headMap);

    public boolean existSystem(String systemId);

    public List<ExcelImportServiceImpl.IndexDO> parseIndexSheet(Sheet indexSheet);

    public Map<String, Object> getInterfaceHead(ExcelImportServiceImpl.IndexDO indexDO, Workbook workbook);

    public Map<String, String> getPublicHead(ExcelImportServiceImpl.IndexDO indexDO);

    public Map<String, Object> getInputArg(Sheet sheet);

    public Map<String, Object> getOutputArg(Sheet sheet);

    public Map<String, Object> getInterfaceAndServiceInfo(Sheet tranSheet);

}
