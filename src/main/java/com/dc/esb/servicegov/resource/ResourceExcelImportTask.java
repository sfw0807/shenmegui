package com.dc.esb.servicegov.resource;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.dc.esb.servicegov.resource.impl.IndexInterfaceParse;
import com.dc.esb.servicegov.resource.impl.IndexServiceParse;
import com.dc.esb.servicegov.resource.impl.InterfaceParse;
import com.dc.esb.servicegov.resource.impl.InvokeInfoParse;
import com.dc.esb.servicegov.resource.impl.ServiceParse;

/**
 *
 */
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
        this.invokeInfoParse = invokeInfoParse;
        this.interfaceParse = interfaceParse;
        this.serviceParse = serviceParse;
        this.indexInterfaceParse = indexInterfaceParse;
        this.indexServiceParse = indexServiceParse;
    }

    public void parse() {
        if (invokeInfoParse.parseRow(row)) {
            // 存在SDA
            if (interfaceSheet != null) {
                if (serviceParse.parse(row, interfaceSheet)) {
                    interfaceParse.parse(row, interfaceSheet);
                }
            } else {
                if (indexServiceParse.parse(row)) {
                    indexInterfaceParse.parse(row);
                }
            }
        }
    }

    public void run() {
        // TODO Auto-generated method stub
        this.parse();
    }

}
