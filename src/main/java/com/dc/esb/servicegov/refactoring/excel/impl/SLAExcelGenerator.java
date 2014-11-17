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
import com.dc.esb.servicegov.refactoring.excel.IConfigGenerater;
import com.dc.esb.servicegov.refactoring.service.impl.SLAViewManagerImpl;
import com.dc.esb.servicegov.refactoring.vo.SLAViewInfoVO;
import com.dc.esb.servicegov.refactoring.vo.SvcAsmRelateInfoVO;

@Service
@Transactional
public class SLAExcelGenerator implements IConfigGenerater<Object, SLAViewInfoVO>{
	private Log log = LogFactory.getLog(SLAExcelGenerator.class);
	@Autowired
	private SLAViewManagerImpl slaViewManager;	
	Workbook wb;
	Sheet sheet;
	private CellStyle headStyle;
	private CellStyle bodyStyle;	
	public static final String fileName = "sla.xls";
	private void init(){
		wb = new HSSFWorkbook();
		sheet = wb.createSheet();
		headStyle = MappingExcelUtils.getTitleCellStyle(wb);
		bodyStyle = MappingExcelUtils.getBodyCellStyle(wb);
	}
	private void CreateHeadInfo(){
		Row headRow = sheet.createRow(0);
		MappingExcelUtils.fillCell(headRow, 0, "服务ID/名称", headStyle);
		MappingExcelUtils.fillCell(headRow, 1, "操作ID/名称", headStyle);
		MappingExcelUtils.fillCell(headRow, 2, "交易代码/名称", headStyle);
		MappingExcelUtils.fillCell(headRow, 3, "提供方系统简称/名称", headStyle);
		MappingExcelUtils.fillCell(headRow, 4, "业务成功率", headStyle);
		MappingExcelUtils.fillCell(headRow, 5, "超时时间", headStyle);
		MappingExcelUtils.fillCell(headRow, 6, "并发数", headStyle);
		MappingExcelUtils.fillCell(headRow, 7, "平均响应时间", headStyle);
		MappingExcelUtils.fillCell(headRow, 8, "备注", headStyle);
	}
	private void createBodyInfo(Map<String,String> conditions){
		List<SLAViewInfoVO> exportList = getExportDataByConditions(conditions);
		if(exportList != null && exportList.size() > 0){
			log.info("export sla data count :" + exportList.size());
			for(int i=0;i<exportList.size();i++){
				Row bodyRow = sheet.createRow(i + 1);
				SLAViewInfoVO vo = exportList.get(i);
				MappingExcelUtils.fillCell(bodyRow, 0, vo.getServiceInfo(), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 1, vo.getOperationInfo(), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 2, vo.getInterfaceInfo(), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 3, vo.getProvideSysInfo(), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 4, vo.getSuccessRate(), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 5, vo.getTimeOut(), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 6, vo.getCurrentCount(), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 7, vo.getAverageTime(), bodyStyle);
				MappingExcelUtils.fillCell(bodyRow, 8, vo.getSlaRemark()==null ? "":vo.getSlaRemark(), bodyStyle);
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
	public List<SLAViewInfoVO> getAllExportData() {		
		return null;
	}	
	public List<SLAViewInfoVO> getExportDataByConditions(
			Map<String, String> mapConditions) {
		return slaViewManager.getExportInfo(mapConditions);
	}


	
}
