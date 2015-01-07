package com.dc.esb.servicegov.refactoring.excel.impl;



import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelGenerateBaseTask<T> extends Runnable {

	public void init(List<T> list,Workbook wb,Sheet sheet);
	
	public void createHeadInfo();
	
	public void createBodyInfo();
	
	public void setOtherStyle();

}
