package com.dc.esb.servicegov.refactoring.resource;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.dc.esb.servicegov.refactoring.resource.impl.IndexInterfaceParse;
import com.dc.esb.servicegov.refactoring.resource.impl.IndexServiceParse;
import com.dc.esb.servicegov.refactoring.resource.impl.InterfaceParse;
import com.dc.esb.servicegov.refactoring.resource.impl.InvokeInfoParse;
import com.dc.esb.servicegov.refactoring.resource.impl.ServiceParse;

public class ResourceExcelImportTask implements ResourceExcelImportBaseTask {

	private Row row;
	private Sheet interfaceSheet;
	private InvokeInfoParse invokeInfoParse;
	private InterfaceParse interfaceParse;
	private IndexInterfaceParse indexInterfaceParse;
	private ServiceParse serviceParse;
	private IndexServiceParse indexServiceParse;

	@Override
	public void init(Row row, Sheet interfaceSheet) {
		this.row = row;
		this.interfaceSheet = interfaceSheet;
	}

	@Override
	public void initParse(InvokeInfoParse invokeInfoParse,
			InterfaceParse interfaceParse, ServiceParse serviceParse,
			IndexInterfaceParse indexInterfaceParse,
			IndexServiceParse indexServiceParse) {
		// TODO Auto-generated method stub
		this.invokeInfoParse = invokeInfoParse;
		this.interfaceParse = interfaceParse;
		this.serviceParse = serviceParse;
		this.indexInterfaceParse = indexInterfaceParse;
		this.indexServiceParse = indexServiceParse;
	}

	public void parse() {
		invokeInfoParse.parseRow(row);
		// 存在SDA
		if (interfaceSheet != null) {
			serviceParse.parse(row, interfaceSheet);
			interfaceParse.parse(row, interfaceSheet);
		} else {
			indexServiceParse.parse(row, interfaceSheet);
			indexInterfaceParse.parse(row, interfaceSheet);
		}
	};

	public void run() {
		// TODO Auto-generated method stub
		this.parse();
	}

}
