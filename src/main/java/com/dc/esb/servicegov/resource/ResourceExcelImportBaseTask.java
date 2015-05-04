package com.dc.esb.servicegov.resource;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.dc.esb.servicegov.resource.impl.IndexInterfaceParse;
import com.dc.esb.servicegov.resource.impl.IndexServiceParse;
import com.dc.esb.servicegov.resource.impl.InterfaceParse;
import com.dc.esb.servicegov.resource.impl.InvokeInfoParse;
import com.dc.esb.servicegov.resource.impl.ServiceParse;

public interface ResourceExcelImportBaseTask {

	public void init(Row row, Sheet interfaceSheet);

	public void initParse(InvokeInfoParse invokeInfoParse,
                          InterfaceParse interfaceParse, ServiceParse serviceParse,
                          IndexInterfaceParse indexInterfaceParse,
                          IndexServiceParse indexServiceParse);
}
