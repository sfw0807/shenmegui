package com.dc.esb.servicegov.refactoring.resource.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.refactoring.resource.ResourceExcelImportTask;
import com.dc.esb.servicegov.refactoring.util.ExcelTool;
import com.dc.esb.servicegov.refactoring.util.GlobalImport;

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
	private MetadataExcelParse metadataParse;
	@Autowired
	private MetadataStructsParse metadataStructsParse;
	@Autowired
	private OnlineParse onlineParse;

	// identify metadata metadataStructs service and interface
	public void parse(InputStream is, String fileName) {
		try {
			// 判断导入的是否是元数据xlsx
			if (fileName.toLowerCase().startsWith("metadata")) {
				metadataParse.parse(wb);
				return;
			}
			wb = WorkbookFactory.create(is);
			indexSheet = wb.getSheet("INDEX");
			// 校验index页
			if (!checkImportExcel(indexSheet)) {
				return;
			}
			// 导入元数据结构
			index1Sheet = wb.getSheet("INDEX1");
			if (index1Sheet != null) {
				metadataStructsParse.parse(wb, index1Sheet);
			}

			int rowNum = indexSheet.getPhysicalNumberOfRows();
			log.info("import count of rows:" + (rowNum - 1));
			// ExecutorService executor = Executors.newFixedThreadPool(100);
			// CountDownLatch countDown = new CountDownLatch(rowNum-1);
			// List<ResourceExcelImportTask> pTaskList = new
			// ArrayList<ResourceExcelImportTask>();
			for (int i = 1; i < rowNum; i++) {
				Row row = indexSheet.getRow(i);
				String interfaceId = excelTool.getCellContent(row.getCell(0));
				if ("".equals(interfaceId)) {
					String operationId = excelTool.getCellContent(row
							.getCell(3));
					interfaceSheet = wb.getSheet(operationId);
				} else {
					interfaceSheet = wb.getSheet(interfaceId);
				}
				ResourceExcelImportTask t = new ResourceExcelImportTask();
				t.init(row, interfaceSheet);
				t.initParse(invokeInfoParse, interfaceParse, serviceParse,
						indexInterfaceParse, indexServiceParse);
				t.run();
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

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			log.error("File Not Found!", e);
		} catch (InvalidFormatException i) {
			log.error("InvalidFormatException!", i);
		} catch (IOException o) {
			log.error("IO exception!", o);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			GlobalImport.flag = false;
			return false;
		} else {
			Row row = indexSheet.getRow(0);
			if (row.getPhysicalNumberOfCells() != 19) {
				log.error("index页的列数量不正确!");
				GlobalImport.flag = false;
				return false;
			} else {
				String cell0 = excelTool.getCellContent(row.getCell(0));
				String cell1 = excelTool.getCellContent(row.getCell(1));
				String cell2 = excelTool.getCellContent(row.getCell(2));
				String cell3 = excelTool.getCellContent(row.getCell(3));
				String cell4 = excelTool.getCellContent(row.getCell(4));
				String cell5 = excelTool.getCellContent(row.getCell(5));
				String cell6 = excelTool.getCellContent(row.getCell(6));
				if ("交易代码".equals(cell0)) {
					log.error("index页的第1列应该为 [交易代码] !");
					GlobalImport.flag = false;
					return false;
				}
				if ("交易名称".equals(cell1)) {
					log.error("index页的第2列应该为 [交易名称] !");
					GlobalImport.flag = false;
					return false;
				}
				if ("服务名称".equals(cell2)) {
					log.error("index页的第3列应该为 [服务名称] !");
					GlobalImport.flag = false;
					return false;
				}
				if ("服务操作ID".equals(cell3)) {
					log.error("index页的第4列应该为 [服务操作ID] !");
					GlobalImport.flag = false;
					return false;
				}
				if ("服务操作名称".equals(cell4)) {
					log.error("index页的第5列应该为 [服务操作名称] !");
					GlobalImport.flag = false;
					return false;
				}
				if ("调用方".equals(cell5)) {
					log.error("index页的第6列应该为 [调用方] !");
					GlobalImport.flag = false;
					return false;
				}
				if ("服务操作提供方".equals(cell6)) {
					log.error("index页的第7列应该为 [服务操作提供方] !");
					GlobalImport.flag = false;
					return false;
				}
			}
		}
		return true;
	}
}
