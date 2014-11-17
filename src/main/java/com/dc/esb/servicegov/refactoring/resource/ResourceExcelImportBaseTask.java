package com.dc.esb.servicegov.refactoring.resource;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.dc.esb.servicegov.refactoring.resource.impl.IndexInterfaceParse;
import com.dc.esb.servicegov.refactoring.resource.impl.IndexServiceParse;
import com.dc.esb.servicegov.refactoring.resource.impl.InterfaceParse;
import com.dc.esb.servicegov.refactoring.resource.impl.InvokeInfoParse;
import com.dc.esb.servicegov.refactoring.resource.impl.ServiceParse;

public interface ResourceExcelImportBaseTask {

	public void init(Row row, Sheet interfaceSheet);

	public void initParse(InvokeInfoParse invokeInfoParse,
			InterfaceParse interfaceParse, ServiceParse serviceParse,
			IndexInterfaceParse indexInterfaceParse,
			IndexServiceParse indexServiceParse);
}
