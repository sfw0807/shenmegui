package com.dc.esb.servicegov.refactoring.resource.impl;

import com.dc.esb.servicegov.refactoring.util.ExcelTool;
import com.dc.esb.servicegov.refactoring.util.GlobalImport;
import com.dc.esb.servicegov.refactoring.util.GlobalMenuId;
import com.dc.esb.servicegov.refactoring.util.UserOperationLogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
public class MetadataImport {
    private Log log = LogFactory.getLog(MetadataImport.class);
    private ExcelTool excelTool = ExcelTool.getInstance();
    private Workbook wb;
    private static final String mdtStr = "表4数据字典";
    private static final String headMdtStr = "表6报文头";
    private static final String arrayStr = "表5数组";
    // metadata sheet
    private Sheet mdtSheet;
    // metadata array sheet
    private Sheet arraySheet;
    // metadata header sheet
    private Sheet headerMdtSheet;
    @Autowired
    private MetadataExcelParse metadataParse;

    // identify metadata metadataStructs service and interface
    public boolean parse(InputStream is, String fileName) {
        try {
            wb = WorkbookFactory.create(is);
            mdtSheet = wb.getSheet(mdtStr);
            arraySheet = wb.getSheet(arrayStr);
            headerMdtSheet = wb.getSheet(headMdtStr);

            // check xlsx valid
            if (checkDocuValid()) {
                metadataParse.parseMdtSheet(mdtSheet);
                metadataParse.parseMdtSheet(headerMdtSheet);
                metadataParse.parseArraySheet(arraySheet);
            }
            return true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            log.error("File Not Found!", e);
            return false;
        } catch (InvalidFormatException i) {
            log.error("InvalidFormatException!", i);
            return false;
        } catch (IOException o) {
            log.error("IO exception!", o);
            return false;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        }
    }

    private boolean checkDocuValid() {
        if (mdtSheet == null && arraySheet == null) {
            log.error("元数据Excel中未找到[数据字典]和[数组]向导页！");
            UserOperationLogUtil.saveLog("元数据Excel中未找到[数据字典]和[数组]向导页！", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
            GlobalImport.flag = false;
            return false;
        }
        if (mdtSheet != null &&
                arraySheet != null &&
                mdtSheet.getPhysicalNumberOfRows() <= 1 &&
                arraySheet.getPhysicalNumberOfRows() <= 1) {
            log.error("[数据字典]和[数组]向导页不存在导入的元数据！");
            UserOperationLogUtil.saveLog("[数据字典]和[数组]向导页不存在导入的元数据！", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
            GlobalImport.flag = false;
            return false;
        }
        if (mdtSheet != null) {
//            Row row_0 = mdtSheet.getRow(0);
//            String cell0 = excelTool.getCellContent(row_0.getCell(0));
//            String cell1 = excelTool.getCellContent(row_0.getCell(1));
//            String cell2 = excelTool.getCellContent(row_0.getCell(2));
//            if (!"规范字段名称".equals(cell0)) {
//                log.error("[数据字典]页的第1列应该为 [规范字段名称] !");
//                UserOperationLogUtil.saveLog("[数据字典]页的第1列应该为 [规范字段名称] !",
//                        GlobalMenuId.menuIdMap
//                                .get(GlobalMenuId.resourceImportMenuId));
//                GlobalImport.flag = false;
//                return false;
//            }
//			if (!"规范字段类型".equals(cell1)) {
//				log.error("[数据字典]页的第2列应该为 [规范字段类型] !");
//				UserOperationLogUtil.saveLog("[数据字典]页的第2列应该为 [规范字段类型] !",
//						GlobalMenuId.menuIdMap
//								.get(GlobalMenuId.resourceImportMenuId));
//				GlobalImport.flag = false;
//				return false;
//			}
//			if (!"规范字段说明".equals(cell2)) {
//				log.error("[数据字典]页的第3列应该为 [规范字段说明] !");
//				UserOperationLogUtil.saveLog("[数据字典]页的第3列应该为 [规范字段说明] !",
//						GlobalMenuId.menuIdMap
//								.get(GlobalMenuId.resourceImportMenuId));
//				GlobalImport.flag = false;
//				return false;
//			}
        }
        if (arraySheet != null) {
//			Row row = arraySheet.getRow(0);
//			String cell0 = excelTool.getCellContent(row.getCell(0));
//			String cell1 = excelTool.getCellContent(row.getCell(1));
//			String cell2 = excelTool.getCellContent(row.getCell(2));
//			if (!"规范字段名称".equals(cell0)) {
//				log.error("[数组]页的第1列应该为 [规范字段名称] !");
//				UserOperationLogUtil.saveLog("[数组]页的第1列应该为 [规范字段名称] !",
//						GlobalMenuId.menuIdMap
//								.get(GlobalMenuId.resourceImportMenuId));
//				GlobalImport.flag = false;
//				return false;
//			}
//			if (!"规范字段类型".equals(cell1)) {
//				log.error("[数组]页的第2列应该为 [规范字段类型] !");
//				UserOperationLogUtil.saveLog("[数组]页的第2列应该为 [规范字段类型] !",
//						GlobalMenuId.menuIdMap
//								.get(GlobalMenuId.resourceImportMenuId));
//				GlobalImport.flag = false;
//				return false;
//			}
//			if (!"规范字段说明".equals(cell2)) {
//				log.error("[数组]页的第3列应该为 [规范字段说明] !");
//				UserOperationLogUtil.saveLog("[数组]页的第3列应该为 [规范字段说明] !",
//						GlobalMenuId.menuIdMap
//								.get(GlobalMenuId.resourceImportMenuId));
//				GlobalImport.flag = false;
//				return false;
//			}
        }
        return true;
    }
}
