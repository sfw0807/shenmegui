package com.dc.esb.servicegov.tranlink;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.dc.esb.servicegov.controller.TranImportController;
import com.dc.esb.servicegov.util.UserOperationLogUtil;

public class ExcelParse {
	private Log log = LogFactory.getLog(TranImportController.class);
	public void parseProvider(InputStream is) throws Exception{
//		List<String> deleteList = new ArrayList<String>();
//		deleteList.add("delete from TRANPROVIDER");
//		DBUtil.batchExcute(deleteList);
		ExcelTool tool = ExcelTool.getInstance();
		Workbook workBook = tool.getExcelWorkbook(is);
		int sheetNum = tool.getSheetCount(workBook);
		List<String> sqlList = new ArrayList<String>();
		List<String> msgList = new ArrayList<String>();
		for(int i=0;i<sheetNum;i++){
			Sheet sheet = workBook.getSheetAt(i);
			int rowNum = tool.getSheetRows(sheet);
			int count = 0;
			for(int j=1;j<rowNum;j++){
				Row row = sheet.getRow(j);
				if(row!=null){
					Cell cell0 = row.getCell(0);
					Cell cell1 = row.getCell(1);
					Cell cell2 = row.getCell(2);
					Cell cell3 = row.getCell(3);
					Cell cell4 = row.getCell(4);
					Cell cell5 = row.getCell(5);
					String tranCode = tool.getCellContent(cell0);
					String tranName = tool.getCellContent(cell1);
					String tranProvider = tool.getCellContent(cell2);
					String providerMsgType = tool.getCellContent(cell3);
					String charger = tool.getCellContent(cell4);
					String remark = tool.getCellContent(cell5);
					boolean isNullRowFlag = !"".equals(tranCode)
					&& !"".equals(tranName)
					&& !"".equals(tranProvider);
					if(isNullRowFlag){
//						System.out.println(tranCode
//								+","
//								+tranName
//								+","
//								+tranProvider					
//								);
						if(DBUtil.isTranExist("TRANPROVIDER",tranCode)){
							log.error("交易["+tranCode+"]已经导入，不再重复导入");
							UserOperationLogUtil.saveLog("交易["+tranCode+"]已经导入，不再重复导入", "36");
						}else{
							sqlList.add("INSERT INTO ESB.TRANPROVIDER(TRANCODE, TRANNANE, PROVIDER, PROVIDERMSGTYPE, CHARGER, REMARK) " +
									"VALUES('"+tranCode+"', '"+tranName+"', '"+tranProvider+"','"+providerMsgType+"','"+charger+"','"+remark+"')");
							count++;
						}
					}
				}
			}
			String msg = "sheet页["+sheet.getSheetName()+"]共有"+rowNum+"行,解析成功"+count+"行";
			msgList.add(msg);
		}
		boolean flag = DBUtil.batchExcute(sqlList);
		if(flag){
			for(String msg : msgList){
				UserOperationLogUtil.saveLog(msg, "36");
			}
		}
	}
	public void parseConsumer(InputStream is) throws Exception{
//		List<String> deleteList = new ArrayList<String>();
//		deleteList.add("delete from TRANCONSUMER");
//		DBUtil.batchExcute(deleteList);
		ExcelTool tool = ExcelTool.getInstance();
		Workbook workBook = tool.getExcelWorkbook(is);
		int sheetNum = tool.getSheetCount(workBook);
		List<String> sqlList = new ArrayList<String>();
		List<String> msgList = new ArrayList<String>();
		for(int i=0;i<sheetNum;i++){
			Sheet sheet = workBook.getSheetAt(i);
			int rowNum = tool.getSheetRows(sheet);
			int count = 0;
			for(int j=1;j<rowNum;j++){
				Row row = sheet.getRow(j);
				if(row!=null){
					Cell cell0 = row.getCell(0);
					Cell cell1 = row.getCell(1);
					Cell cell2 = row.getCell(2);
					Cell cell3 = row.getCell(3);
					Cell cell4 = row.getCell(4);
					Cell cell5 = row.getCell(5);
					Cell cell6 = row.getCell(6);
					Cell cell7 = row.getCell(7);
					String tranCode = tool.getCellContent(cell0);
					String tranName = tool.getCellContent(cell1);
					String tranConsumer = tool.getCellContent(cell2);
					String provider = tool.getCellContent(cell3);
					String consumerMsgType = tool.getCellContent(cell4);
					String frontCode = tool.getCellContent(cell5);
					String charger = tool.getCellContent(cell6);
					String remark = tool.getCellContent(cell7);
					boolean isNullRowFlag = !"".equals(tranCode)
					&& !"".equals(tranName)
					&& !"".equals(tranConsumer);
					if(DBUtil.isTranExist("TRANCONSUMER",tranCode)){
						log.error("交易["+tranCode+"]已经导入，不再重复导入");
						UserOperationLogUtil.saveLog("交易["+tranCode+"]已经导入，不再重复导入", "36");
					}else{
						if(isNullRowFlag){
//							System.out.println(tranCode
//									+","
//									+tranName
//									+","
//									+tranConsumer					
//									);
							if(tranConsumer.indexOf("（")>0 && tranConsumer.indexOf("）")>0){
								String sourceSys = tranConsumer.substring(tranConsumer.indexOf("（")+1, tranConsumer.indexOf("）"));
								String passedSys = tranConsumer.substring(0, tranConsumer.indexOf("（"));
								if(sourceSys.indexOf("/")>0){
									String[] sys = sourceSys.split("/");
									for (int k = 0; k < sys.length; k++) {
										sqlList.add("INSERT INTO ESB.TRANCONSUMER(TRANCODE, TRANNANE, CONSUMER,PASSEDSYS,PASSEDSYS, PROVIDER, CONSUMERMSGTYPE, FONTTRANCODE, CHARGER, REMARK) " +
												"VALUES('"+tranCode+"', '"+tranName+"', '"+sys[k]+"','"+passedSys+"','"+provider+"','"+consumerMsgType+"','"+frontCode+"','"+charger+"','"+remark+"')");
									}
									count++;
								}else{
									sqlList.add("INSERT INTO ESB.TRANCONSUMER(TRANCODE, TRANNANE, CONSUMER,PASSEDSYS,PASSEDSYS, PROVIDER, CONSUMERMSGTYPE, FONTTRANCODE, CHARGER, REMARK) " +
											"VALUES('"+tranCode+"', '"+tranName+"', '"+sourceSys+"','"+passedSys+"','"+provider+"','"+consumerMsgType+"','"+frontCode+"','"+charger+"','"+remark+"')");
									count++;
								}
							}else{
								if(tranConsumer.indexOf("/")>0){
									String[] sys = tranConsumer.split("/");
									for (int k = 0; k < sys.length; k++) {
										sqlList.add("INSERT INTO ESB.TRANCONSUMER(TRANCODE, TRANNANE, CONSUMER,PROVIDER, CONSUMERMSGTYPE, FONTTRANCODE, CHARGER, REMARK) " +
												"VALUES('"+tranCode+"', '"+tranName+"', '"+sys[k]+"','"+provider+"','"+consumerMsgType+"','"+frontCode+"','"+charger+"','"+remark+"')");
									}
									count++;
								}else{
									sqlList.add("INSERT INTO ESB.TRANCONSUMER(TRANCODE, TRANNANE, CONSUMER,PROVIDER, CONSUMERMSGTYPE, FONTTRANCODE, CHARGER, REMARK) " +
											"VALUES('"+tranCode+"', '"+tranName+"', '"+tranConsumer+"','"+provider+"','"+consumerMsgType+"','"+frontCode+"','"+charger+"','"+remark+"')");
									count++;
								}
							}
						}
					}
				}
			}
			String msg = "sheet页["+sheet.getSheetName()+"]共有"+rowNum+"行,解析成功"+count+"行";
			msgList.add(msg);
		}
		boolean flag = DBUtil.batchExcute(sqlList);
		if(flag){
			for(String msg : msgList){
				UserOperationLogUtil.saveLog(msg, "36");
			}	
		}
	}	
}
