package com.dc.esb.servicegov.refactoring.excel;


import java.util.List;
import java.util.Map;

public interface IConfigGenerater<I, O> {
	
	public List<O> getAllExportData();
	public List<O> getExportDataByConditions(Map<String,String> mapConditions);
	
}