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
import com.dc.esb.servicegov.refactoring.service.impl.SvcAsmRelateManagerImpl;
import com.dc.esb.servicegov.refactoring.vo.SvcAsmRelateInfoVO;

@Service
@Transactional
public class SvcAsmRelateExcelGenerator implements IConfigGenerater<Object, SvcAsmRelateInfoVO>{

	private Log log = LogFactory.getLog(SvcAsmRelateExcelGenerator.class);
	Workbook wb;
	Sheet sheet;
	private CellStyle headCellStyle;
	private CellStyle bodyCellStyle;
	public static final String fileName = "svcAsmRelate.xls";
	@Autowired
	private SvcAsmRelateManagerImpl svcAsmRelateManager;
	
	private void init(){
		wb = new HSSFWorkbook();
		sheet = wb.createSheet();
		headCellStyle = MappingExcelUtils.getTitleCellStyle(wb);
		bodyCellStyle = MappingExcelUtils.getBodyCellStyle(wb);
	}
	
	private void createHeadInfo(){
		Row headRow = sheet.createRow(0);
		MappingExcelUtils.fillCell(headRow, 0, "服务ID/名称", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 1, "操作ID/名称", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 2, "交易代码/名称", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 3, "源系统简称/名称", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 4, "调用方系统简称/名称", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 5, "提供方系统简称/名称", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 6, "调用报文类型", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 7, "提供报文类型", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 8, "是否穿透", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 9, "交易状态", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 10, "修订次数", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 11, "上线日期", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 12, "上线版本", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 13, "备注", headCellStyle);
	}
	private void createBodyInfo(Map<String,String> conditionsMap){
		List<SvcAsmRelateInfoVO> exportList = getExportDataByConditions(conditionsMap);
		if(exportList != null && exportList.size() > 0){
			log.info("export data count :" + exportList.size());
			for(int i=0;i<exportList.size();i++){
				Row bodyRow = sheet.createRow(i + 1);
				SvcAsmRelateInfoVO vo = exportList.get(i);
				MappingExcelUtils.fillCell(bodyRow, 0, vo.getServiceInfo(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 1, vo.getOperationInfo(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 2, vo.getInterfaceInfo(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 3, vo.getConsumeSysInfo(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 4, vo.getPassbySysInfo(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 5, vo.getProvideSysInfo(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 6, vo.getConsumeMsgType(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 7, vo.getProvideMsgType(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 8, vo.getThrough(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 9, vo.getState(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 10, vo.getModifyTimes(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 11, vo.getOnlineDate(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 12, vo.getOnlineVersion(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 13, vo.getField(), bodyCellStyle);
			}
		}
	}

	private void setOtherStyle(){
		sheet.setColumnWidth(0, 15*256);
		sheet.setColumnWidth(1, 15*256);
		sheet.setColumnWidth(2, 15*256);
		sheet.setColumnWidth(3, 15*256);
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
		createHeadInfo();
		createBodyInfo(mapConditions);
		setOtherStyle();
		wb.write(os);
		os.close();
		return file;
	}

	@Override
	public List<SvcAsmRelateInfoVO> getAllExportData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SvcAsmRelateInfoVO> getExportDataByConditions(
			Map<String, String> mapConditions) {
		// TODO Auto-generated method stub
		return svcAsmRelateManager.getInfosByConditions(mapConditions);
	}

	
}
