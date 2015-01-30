package com.dc.esb.servicegov.refactoring.excel.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.excel.support.MappingExcelUtils;
import com.dc.esb.servicegov.refactoring.service.impl.SystemManagerImpl;
import com.dc.esb.servicegov.refactoring.vo.ServiceDevProgressVO;

@Service
public class ServiceDevProgressExcelGenerator {

	private static final Log log = LogFactory.getLog(ServiceDevProgressExcelGenerator.class);
	
	@Autowired
	private SystemManagerImpl systemManager;
	
	Workbook wb;
	
	private CellStyle headCellStyle;
	private CellStyle cellStyle;
	private CellStyle cellStyleMaroon;
	private CellStyle cellStyleLightYellow;
	private CellStyle cellStyleLightGreen;
	
	private void init() {
		wb = new HSSFWorkbook();
		headCellStyle = MappingExcelUtils.getBodyHeaderCellStyle(wb);
		cellStyle = MappingExcelUtils.getCellStyle(wb);
		cellStyleMaroon = MappingExcelUtils.getCellStyleMaroon(wb);
		cellStyleLightYellow = MappingExcelUtils.getCellStyleLightYellow(wb);
		cellStyleLightGreen = MappingExcelUtils.getCellStyleLightGreen(wb);
	}
	
	public File generate(Map<String, String> conditions) {
		return null;
	}
	
	public File exportAll(String params) {
		init();
		File excelFile = null;
		FileOutputStream outputStream = null;
		try {
			Sheet sheet = wb.createSheet("服务开发统计信息");
			printHeader(sheet);
			List<ServiceDevProgressVO> lst = null;
			if ("null".equals(params)) {
				lst = systemManager.getSeviceDevInfo(null);
			} else {
				lst = systemManager.getSeviceDevInfo(params.split("\\|"));
			}
			printSheetContent(sheet, lst);
			setCellStyle(sheet, lst.size()+1);
			excelFile = File.createTempFile("service_develop_progress_excel", ".xls");
			outputStream = new FileOutputStream(excelFile);
			wb.write(outputStream);
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("关闭文件[" + excelFile.getAbsolutePath() + "]输出流失败！");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return excelFile;
	}
	
	private void printSheetContent(Sheet contentSheet, List<ServiceDevProgressVO> lst) {
		int totalUnderDefine = 0;
		int totalDev = 0;
		int totalUnit = 0;
		int totalSit = 0;
		int totalUat = 0;
		int totalProduct = 0;
		int toto = 0;
		int ttRow = lst.size()+3;
		for (int i=0; i<lst.size(); i++) {
			int row = i + 3;
			ServiceDevProgressVO vo = lst.get(i);
			MappingExcelUtils.fillCell(row, 0, vo.getSystemName(), contentSheet, cellStyle);
			MappingExcelUtils.fillCell(row, 1, vo.getSystemAb(), contentSheet, cellStyle);
			MappingExcelUtils.fillCell(row, 3, String.valueOf(vo.getUnderDefine()), contentSheet, cellStyle);
			MappingExcelUtils.fillCell(row, 4, String.valueOf(vo.getDev()), contentSheet, cellStyle);
			MappingExcelUtils.fillCell(row, 5, String.valueOf(vo.getUnitTest()), contentSheet, cellStyle);
			MappingExcelUtils.fillCell(row, 6, String.valueOf(vo.getSitTest()), contentSheet, cellStyle);
			MappingExcelUtils.fillCell(row, 7, String.valueOf(vo.getUatTest()), contentSheet, cellStyle);
			MappingExcelUtils.fillCell(row, 8, String.valueOf(vo.getProductTest()), contentSheet, cellStyle);
			MappingExcelUtils.fillCell(row, 9, String.valueOf(vo.getTotalNum()), contentSheet, cellStyle);
			
			totalUnderDefine += vo.getUnderDefine();
			totalDev += vo.getDev();
			totalUnit += vo.getUnitTest();
			totalSit += vo.getSitTest();
			totalUat += vo.getUatTest();
			totalProduct += vo.getProductTest();
			toto += vo.getTotalNum();
		}
		
		MappingExcelUtils.fillCell(ttRow, 0, "", contentSheet, cellStyle);
		MappingExcelUtils.fillCell(ttRow, 1, "总计", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(ttRow, 3, String.valueOf(totalUnderDefine), contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(ttRow, 4, String.valueOf(totalDev), contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(ttRow, 5, String.valueOf(totalUnit), contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(ttRow, 6, String.valueOf(totalSit), contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(ttRow, 7, String.valueOf(totalUat), contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(ttRow, 8, String.valueOf(totalProduct), contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(ttRow, 9, String.valueOf(toto), contentSheet, headCellStyle);
	}
	
	
	/**
	 * 设置sheet2(当前操作SHEET)文本字体和颜色 合并单元格
	 */
	private void setCellStyle(Sheet sheet, int lastIndex){
		
		Iterator<Row> iterator=sheet.iterator();
		//处理其他行
		Row row = null;
		while (iterator.hasNext()) {
			row = iterator.next();
			row.setHeightInPoints((short) 20);
		}
		for(int i=0;i<10;i++){
		    sheet.getRow(0).getCell(i).setCellStyle(cellStyleLightYellow);
		    sheet.getRow(1).getCell(i).setCellStyle(cellStyleLightYellow);
		    sheet.getRow(2).getCell(i).setCellStyle(cellStyleLightGreen);
		}
		
		for(int i=0;i<=lastIndex+2;i++){
//			if (i==2)
//				continue ;
			Row rowToRender = sheet.getRow(i);
			Cell cellToRender =rowToRender.getCell(2);
			if (cellToRender == null) {
				cellToRender = rowToRender.createCell(2);
			}
			cellToRender.setCellStyle(cellStyleMaroon);
		}
		
		
		//合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
		sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));
		sheet.addMergedRegion(new CellRangeAddress(0,0,3,8));
		sheet.addMergedRegion(new CellRangeAddress(0,1,9,9));
		sheet.addMergedRegion(new CellRangeAddress(0,lastIndex-1,2,2));
		
		// 设置列宽度
		sheet.setColumnWidth(0, 20*256);
		sheet.setColumnWidth(1, 14*256);
		sheet.setColumnWidth(2, 2*256);
		sheet.setColumnWidth(3, 14*256);
		sheet.setColumnWidth(4, 14*256);
		sheet.setColumnWidth(5, 14*256);
		sheet.setColumnWidth(6, 14*256);
		sheet.setColumnWidth(7, 14*256);
		sheet.setColumnWidth(8, 14*256);
		sheet.setColumnWidth(9, 14*256);
	}
	
	/**
	 * PAINT HEADER
	 */
	private void printHeader(Sheet contentSheet) {

		MappingExcelUtils.fillCell(0, 0, "系统名称", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(0, 1, "系统英文名称", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(0, 5, "服务状态/数量", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(0, 3, "服务状态/数量", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(1, 3, "服务定义", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(1, 4, "开发", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(1, 5, "联调测试", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(1, 6, "sit测试", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(1, 7, "uat测试", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(1, 8, "投产测试", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(0, 9, "总计", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(0, 2, "", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(1, 2, "", contentSheet, headCellStyle);
		
		for (int j=0; j<3; j++) {
			for (int i=0; i<10; i++) {
				if (j==2 || contentSheet.getRow(j).getCell(i) == null) {
					MappingExcelUtils.fillCell(j, i, "", contentSheet, cellStyle);
				}
			}
		}
	}
}
