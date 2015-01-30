package com.dc.esb.servicegov.refactoring.excel.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.excel.support.MappingExcelUtils;
import com.dc.esb.servicegov.refactoring.dao.impl.FunctionDAOImpl;
import com.dc.esb.servicegov.refactoring.excel.IConfigGenerater;
import com.dc.esb.servicegov.refactoring.service.impl.SLAViewManagerImpl;
import com.dc.esb.servicegov.refactoring.vo.SLAViewInfoVO;
import com.dc.esb.servicegov.refactoring.vo.SvcAsmRelateInfoVO;

@Service
@Transactional
public class TranConsumerCountExcelGenerator {
	private Log log = LogFactory.getLog(TranConsumerCountExcelGenerator.class);
	@Autowired
	private FunctionDAOImpl functionDAO;	
	Workbook wb;
	Sheet sheet;
	private CellStyle headStyle;
	private CellStyle bodyStyle;	
	public static final String fileName = "TranConsumerCount.xls";
	private void init(){
		wb = new HSSFWorkbook();
		sheet = wb.createSheet();
		headStyle = MappingExcelUtils.getTitleCellStyle(wb);
		bodyStyle = MappingExcelUtils.getBodyCellStyle(wb);
	}
	private void CreateHeadInfo(){
		Row headRow = sheet.createRow(0);
		MappingExcelUtils.fillCell(headRow, 0, "交易代码", headStyle);
		MappingExcelUtils.fillCell(headRow, 1, "交易名称", headStyle);
		MappingExcelUtils.fillCell(headRow, 2, "调用方", headStyle);
		MappingExcelUtils.fillCell(headRow, 3, "经由系统", headStyle);
		MappingExcelUtils.fillCell(headRow, 4, "提供方", headStyle);
		MappingExcelUtils.fillCell(headRow, 5, "调用方报文格式", headStyle);
		MappingExcelUtils.fillCell(headRow, 6, "前台交易码", headStyle);
		MappingExcelUtils.fillCell(headRow, 7, "负责人", headStyle);
		MappingExcelUtils.fillCell(headRow, 8, "备注", headStyle);
	}
	private void createBodyInfo(Map<String,String> conditions){
		List exportList = getExportDataByConditions(conditions);
		if(exportList != null && exportList.size() > 0){
			log.info("export TranProviderCount data count :" + exportList.size());
			for(int i=0;i<exportList.size();i++){
				Map map = (Map)exportList.get(i);
				Row bodyRow = sheet.createRow(i + 1);
				MappingExcelUtils.fillCell(bodyRow, 0, (String)map.get("TRANCODE"), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 1, (String)map.get("TRANNANE"), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 2, (String)map.get("CONSUMER"), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 3, (String)map.get("PASSEDSYS"), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 4, (String)map.get("PROVIDER"), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 5, (String)map.get("CONSUMERMSGTYPE"), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 6, (String)map.get("FONTTRANCODE"), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 7, (String)map.get("CHARGER"), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 8, (String)map.get("REMARK"), bodyStyle);
			}
		}
	}
	private void setOtherStyle(){
		sheet.setColumnWidth(0, 35*256);
		sheet.setColumnWidth(1, 45*256);
		sheet.setColumnWidth(2, 40*256);
		sheet.setColumnWidth(3, 25*256);
		sheet.setColumnWidth(4, 15*256);
		sheet.setColumnWidth(5, 15*256);
		for(int i=0;i<= sheet.getLastRowNum();i++){
			sheet.getRow(i).setHeightInPoints((short) 23.5);
		}
	}
	public File generate(Map<String,String> mapConditions) throws Exception {
		// TODO Auto-generated method stub
		File file = new File(fileName);
		file.deleteOnExit();
		file.createNewFile();
		FileOutputStream os = new FileOutputStream(file);
		init();
		log.info("generate excel file!");
		CreateHeadInfo();
		createBodyInfo(mapConditions);
		setOtherStyle();
		wb.write(os);
		os.close();
		return file;
	}	
	public List getExportDataByConditions(
			Map<String, String> mapConditions) {
//		trancode : trancode,
//		tranname: tranname,
//		consumer: consumer,
//		passedsys: passedsys,
//		provider: provider,
//		consumerMsgType: consumerMsgType,
//		frontcode: frontcode
		String trancode = mapConditions.get("trancode");
		String tranname = mapConditions.get("tranname");
		String consumer = mapConditions.get("consumer");
		String passedsys = mapConditions.get("passedsys");
		String provider = mapConditions.get("provider");
		String consumerMsgType = mapConditions.get("consumerMsgType");
		String frontcode = mapConditions.get("frontcode");
		return functionDAO.getTranConsumerByCondition(trancode, tranname, consumer, passedsys, provider, consumerMsgType, frontcode);
	}
}
