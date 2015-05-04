package com.dc.esb.servicegov.tranlink;

public class Main {
	public static void main(String[] args) {
//		String excelPath = "D:\\esb\\doc\\需求文档\\外围系统接口需求\\全行接口梳理\\全行存量系统交易清单.xlsx";
//		ExcelParse excelParse = new ExcelParse();
//		try {
//			excelParse.parseProvider(excelPath);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		String excelPath = "D:\\esb\\doc\\需求文档\\外围系统接口需求\\全行接口梳理\\渠道\\存量交易_渠道（网银）.xlsx";
//		ExcelParse excelParse = new ExcelParse();
//		try {
//			excelParse.parseConsumer(excelPath);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		String sql = "SELECT T1.TRANCODE TRANCODE," +
				"T1.TRANNANE TRANNANE," +
				"T1.CONSUMER CONSUMER," +
				"T2.PROVIDER PROVIDER " +
				"FROM ESB.TRANCONSUMER T1,TRANPROVIDER T2  " +
				"WHERE  T1.TRANCODE = T2.TRANCODE";
//		List<Map<String,String>> list = DBUtil.query(sql);
//		ExcelExport export = new ExcelExport();
//		try {
//			export.exportFile(list);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}