package com.dc.esb.servicegov.refactoring.resource;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


public interface IParse {

	public void parse(Row row,Sheet interfaceSheet);
}
