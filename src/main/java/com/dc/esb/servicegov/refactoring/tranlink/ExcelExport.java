package com.dc.esb.servicegov.refactoring.tranlink;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelExport {
	private Workbook wb;
	private Sheet sheet;
	private CellStyle headStyle;
	private CellStyle bodyStyle;
	public static final String fileName = "TranLink.xls";
	
	private void init(){
		wb = new HSSFWorkbook();
		sheet = wb.createSheet();
		headStyle = getTitleCellStyle(wb);
		bodyStyle = getBodyCellStyle(wb);
	}
	
	public File exportFile(List<Map<String,String>> list) throws Exception{
		File file = new File(fileName);
		//file.deleteOnExit();
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		init();
		Row headRow = sheet.createRow(0);
		sheet.setColumnWidth(0,8*256);
		sheet.setColumnWidth(1,80*256);
		sheet.setColumnWidth(2,80*256);
		fillCell(headRow, 0, "交易码", headStyle);
		fillCell(headRow, 1, "交易名称", headStyle);
		fillCell(headRow, 2, "交易链路", headStyle);
		int i=1;
		for(Map<String,String> map:list){
			
			String consumer = map.get("CONSUMER");
			if(consumer.indexOf("（")>0 && consumer.indexOf("）")>0){
				String sourceSys = consumer.substring(consumer.indexOf("（")+1, consumer.indexOf("）"));
				String passedSys = consumer.substring(0, consumer.indexOf("（"));
				if(sourceSys.indexOf("/")>0){
					String[] sys = sourceSys.split("/");
					for (int j = 0; j < sys.length; j++) {
						Row bodyRow = sheet.createRow(i);
						fillCell(bodyRow, 0, map.get("TRANCODE"), bodyStyle);
						fillCell(bodyRow, 1, map.get("TRANNANE"), bodyStyle);
						fillCell(bodyRow, 2, sys[j]+"-->"+passedSys+"-->"+map.get("PROVIDER"), bodyStyle);
					}
				}else{
					Row bodyRow = sheet.createRow(i);
					fillCell(bodyRow, 0, map.get("TRANCODE"), bodyStyle);
					fillCell(bodyRow, 1, map.get("TRANNANE"), bodyStyle);
					fillCell(bodyRow, 2, sourceSys+"-->"+passedSys+"-->"+map.get("PROVIDER"), bodyStyle);
				}
			}else{
				Row bodyRow = sheet.createRow(i);
				fillCell(bodyRow, 0, map.get("TRANCODE"), bodyStyle);
				fillCell(bodyRow, 1, map.get("TRANNANE"), bodyStyle);
				fillCell(bodyRow, 2, map.get("CONSUMER")+"-->"+map.get("PROVIDER"), bodyStyle);
			}

			i++;
		}
		wb.write(fos);
		fos.close();
		System.out.println(file.getAbsolutePath());
		return file;
	}
	public static CellStyle getBaseCellStyle(Workbook wb){
		CellStyle cellStyle = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setFontName("宋体");
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		return cellStyle;
	}	
	public static CellStyle getBaseHeadCellStyle(Workbook wb){
		CellStyle cellStyle = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setFontName("宋体");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		return cellStyle;
	}	
	public static CellStyle getTitleCellStyle(Workbook wb){
		CellStyle cellStyle = getBaseHeadCellStyle(wb);
		cellStyle.setFillForegroundColor(new HSSFColor.GREY_50_PERCENT()
				.getIndex());
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		return cellStyle;
	}
	public static CellStyle getBodyCellStyle(Workbook wb){
		CellStyle cellStyle = getBaseCellStyle(wb);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		return cellStyle;
	}	
	public static void fillCell(Row row, int colNo,String data, CellStyle cellStyle){
		Cell cell = row.createCell(colNo);
		if (cellStyle != null){
			cell.setCellStyle(cellStyle);
		}
		cell.setCellValue(data);
	}
}
