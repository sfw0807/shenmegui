package com.dc.esb.servicegov.resource.impl;

import com.dc.esb.servicegov.resource.ResourceExcelImportTask;
import com.dc.esb.servicegov.util.ExcelTool;
import com.dc.esb.servicegov.util.GlobalImport;
import com.dc.esb.servicegov.util.GlobalMenuId;
import com.dc.esb.servicegov.util.UserOperationLogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ResourceExcelImport {
    private Log log = LogFactory.getLog(ResourceExcelImport.class);

    private ExcelTool excelTool = ExcelTool.getInstance();
    private Workbook wb;
    private Sheet indexSheet;
    // 元数据结构Sheet
    private Sheet index1Sheet;
    private Sheet interfaceSheet;
    @Autowired
    private InvokeInfoParse invokeInfoParse;
    @Autowired
    private InterfaceParse interfaceParse;
    @Autowired
    private IndexInterfaceParse indexInterfaceParse;
    @Autowired
    private ServiceParse serviceParse;
    @Autowired
    private IndexServiceParse indexServiceParse;
    @Autowired
    private MetadataStructsParse metadataStructsParse;
    @Autowired
    private OnlineParse onlineParse;

    // identify metadata metadataStructs service and interface

    /**
     * 字段映射导入
     *
     * @param is
     * @param fileName
     * @return
     */
    public boolean parse(InputStream is, String fileName) {
        try {
            wb = WorkbookFactory.create(is);
            indexSheet = wb.getSheet("INDEX");
            // 校验index页
            if (!checkImportExcel(indexSheet)) {
                return false;
            }
            // 导入元数据结构
            index1Sheet = wb.getSheet("INDEX1");
            if (index1Sheet != null) {
                metadataStructsParse.parse(wb, index1Sheet);
            }

            int rowNum = indexSheet.getPhysicalNumberOfRows();
            log.info("import count of rows:" + (rowNum - 1));
            ResourceExcelImportTask t = new ResourceExcelImportTask();
            t.initParse(invokeInfoParse, interfaceParse, serviceParse,
                    indexInterfaceParse, indexServiceParse);
            // ExecutorService executor = Executors.newFixedThreadPool(100);
            // CountDownLatch countDown = new CountDownLatch(rowNum-1);
            // List<ResourceExcelImportTask> pTaskList = new
            // ArrayList<ResourceExcelImportTask>();
            for (int i = 1; i < rowNum; i++) {
                Row row = indexSheet.getRow(i);
                if (row != null) {
                    String interfaceId = excelTool.getCellContent(row
                            .getCell(0));
                    if ("".equals(interfaceId)) {
                        String operationId = excelTool.getCellContent(row
                                .getCell(3));
                        interfaceSheet = wb.getSheet(operationId);
                    } else {
                        interfaceSheet = wb.getSheet(interfaceId);
                    }
                    t.init(row, interfaceSheet);
                    t.run();
                }
                // pTaskList.add(t);
                // countDown.countDown();
            }
            log.info("资源导入完成!");
            // try {
            // countDown.await(60 * 2, TimeUnit.SECONDS);
            // } catch (InterruptedException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
            // for(ResourceExcelImportTask kSap : pTaskList){
            // executor.execute(kSap);
            // }
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

    // identify online excel
    public String parseOnlineExcel(InputStream is, String fileName) {
        try {
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheet("Sheet1");
            return onlineParse.parse(sheet);
        } catch (Exception e) {
            log.error(e, e);
            return "导入失败,原因：" + e.getMessage();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkImportExcel(Sheet indexSheet) {
        if (indexSheet == null) {
            log.error("index页不存在!");
            UserOperationLogUtil.saveLog("index页不存在!", GlobalMenuId.menuIdMap
                    .get(GlobalMenuId.resourceImportMenuId));
            GlobalImport.flag = false;
            return false;
        } else {
            if (indexSheet.getPhysicalNumberOfRows() <= 1) {
                log.error("index页不存在导入的数据!");
                UserOperationLogUtil.saveLog("index页不存在导入的数据!!",
                        GlobalMenuId.menuIdMap
                                .get(GlobalMenuId.resourceImportMenuId));
                GlobalImport.flag = false;
                return false;
            }
            Row row = indexSheet.getRow(0);
            String cell0 = excelTool.getCellContent(row.getCell(0));
            String cell1 = excelTool.getCellContent(row.getCell(1));
            String cell2 = excelTool.getCellContent(row.getCell(2));
            String cell3 = excelTool.getCellContent(row.getCell(3));
            String cell4 = excelTool.getCellContent(row.getCell(4));
            String cell5 = excelTool.getCellContent(row.getCell(5));
            String cell6 = excelTool.getCellContent(row.getCell(6));
            String cell18 = excelTool.getCellContent(row.getCell(18));
            if (!"交易代码".equals(cell0)) {
                log.error("index页的第1列应该为 [交易代码] !");
                UserOperationLogUtil.saveLog("index页的第1列应该为 [交易代码] !",
                        GlobalMenuId.menuIdMap
                                .get(GlobalMenuId.resourceImportMenuId));
                GlobalImport.flag = false;
                return false;
            }
            if (!"交易名称".equals(cell1)) {
                log.error("index页的第2列应该为 [交易名称] !");
                UserOperationLogUtil.saveLog("index页的第2列应该为 [交易名称] !",
                        GlobalMenuId.menuIdMap
                                .get(GlobalMenuId.resourceImportMenuId));
                GlobalImport.flag = false;
                return false;
            }
            if (!"服务名称".equals(cell2)) {
                log.error("index页的第3列应该为 [服务名称] !");
                UserOperationLogUtil.saveLog("index页的第3列应该为 [服务名称] !",
                        GlobalMenuId.menuIdMap
                                .get(GlobalMenuId.resourceImportMenuId));
                GlobalImport.flag = false;
                return false;
            }
            if (!"服务操作ID".equals(cell3)) {
                log.error("index页的第4列应该为 [服务操作ID] !");
                UserOperationLogUtil.saveLog("index页的第4列应该为 [服务操作ID] !",
                        GlobalMenuId.menuIdMap
                                .get(GlobalMenuId.resourceImportMenuId));
                GlobalImport.flag = false;
                return false;
            }
            if (!"服务操作名称".equals(cell4)) {
                log.error("index页的第5列应该为 [服务操作名称] !");
                UserOperationLogUtil.saveLog("index页的第5列应该为 [服务操作名称] !",
                        GlobalMenuId.menuIdMap
                                .get(GlobalMenuId.resourceImportMenuId));
                GlobalImport.flag = false;
                return false;
            }
            if (!"调用方".equals(cell5)) {
                log.error("index页的第6列应该为 [调用方] !");
                UserOperationLogUtil.saveLog("index页的第6列应该为 [调用方] !",
                        GlobalMenuId.menuIdMap
                                .get(GlobalMenuId.resourceImportMenuId));
                GlobalImport.flag = false;
                return false;
            }
            if (!"提供方".equals(cell6)) {
                log.error("index页的第7列应该为 [提供方] !");
                UserOperationLogUtil.saveLog("index页的第7列应该为 [提供方] !",
                        GlobalMenuId.menuIdMap
                                .get(GlobalMenuId.resourceImportMenuId));
                GlobalImport.flag = false;
                return false;
            }
//			if (!"业务报文头编号".equals(cell18)) {
//				log.error("index页的第20列应该为 [业务报文头编号] !");
//				UserOperationLogUtil.saveLog("index页的最后一列应该为 [业务报文头编号] !",
//						GlobalMenuId.menuIdMap
//								.get(GlobalMenuId.resourceImportMenuId));
//				GlobalImport.flag = false;
//				return false;
//			}
        }
        return true;
    }
}
