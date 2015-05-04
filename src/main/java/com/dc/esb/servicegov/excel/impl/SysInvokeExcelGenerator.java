package com.dc.esb.servicegov.excel.impl;

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
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.excel.support.MappingExcelUtils;
import com.dc.esb.servicegov.dao.impl.ProtocolInfoDAOImpl;
import com.dc.esb.servicegov.dao.impl.SystemDAOImpl;
import com.dc.esb.servicegov.excel.IConfigGenerater;
import com.dc.esb.servicegov.service.impl.InvokeInfoManagerImpl;
import com.dc.esb.servicegov.vo.SystemInvokeServiceInfo;

@Service
@Transactional
public class SysInvokeExcelGenerator implements IConfigGenerater<Object, SystemInvokeServiceInfo>{

	private Log log = LogFactory.getLog(SysInvokeExcelGenerator.class);
	Workbook wb;
	Sheet sheet;
	private CellStyle headCellStyle;
	private CellStyle bodyCellStyle;
	public static final String fileName = "sysInvoke.xls";
	@Autowired
	private InvokeInfoManagerImpl invokeInfoManager;
	@Autowired
	private ProtocolInfoDAOImpl protocolInfoDAO;
	@Autowired
	private SystemDAOImpl systemDAO;
	
	
	private void init(){
		wb = new HSSFWorkbook();
		sheet = wb.createSheet();
		headCellStyle = MappingExcelUtils.getTitleCellStyle(wb);
		bodyCellStyle = MappingExcelUtils.getBodyCellStyle(wb);
	}
	
	private void createHeadInfo(){
		Row headRow = sheet.createRow(0);
		MappingExcelUtils.fillCell(headRow, 0, "系统简称", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 1, "系统名称", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 2, "首次上线日期(提供方)", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 3, "首次上线日期(调用方)", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 4, "协议类型", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 5, "提供报文类型", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 6, "调用报文类型", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 7, "提供服务数", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 8, "调用服务数", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 9, "提供操作数", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 10, "调用操作数", headCellStyle);
	}
	private void createBodyInfo(Map<String,String> conditionsMap){
		List<SystemInvokeServiceInfo> exportList = getExportDataByConditions(conditionsMap);
		String sysId = null;
		if(exportList != null && exportList.size() > 0){
			int ps =0;
			int cs =0;
			int pc =0;
			int cc =0;
			log.info("export data count :" + exportList.size());
			for(int i=0;i<exportList.size();i++){
				Row bodyRow = sheet.createRow(i + 1);
				SystemInvokeServiceInfo sysInvoke = exportList.get(i);
				MappingExcelUtils.fillCell(bodyRow, 0, sysInvoke.getSystemAB(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 1, sysInvoke.getSystemName(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 2, sysInvoke.getFirstPublishDate(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 3, sysInvoke.getSecondPublishDate(), bodyCellStyle);
				sysId = systemDAO.getSystemIdByAb(sysInvoke.getSystemAB());
				MappingExcelUtils.fillCell(bodyRow, 4, protocolInfoDAO.getConnectTypeBySysId(sysId), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 5, conditionsMap.get("prdMsgType"), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 6, conditionsMap.get("csmMsgType"), bodyCellStyle);
				//MappingExcelUtils.fillCell(bodyRow, 4, invokeInfoDAO.getMsgTypeByPrdSysId(sysId), bodyCellStyle);
				//MappingExcelUtils.fillCell(bodyRow, 5, invokeInfoDAO.getMsgTypeByCsmSysId(sysId), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 7, sysInvoke.getProvideServiceNum(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 8, sysInvoke.getConsumeServiceNum(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 9, sysInvoke.getProvideOperationNum(), bodyCellStyle);
				MappingExcelUtils.fillCell(bodyRow, 10, sysInvoke.getConsumeOperationNum(), bodyCellStyle);
				ps += Integer.parseInt(sysInvoke.getProvideServiceNum());
				cs += Integer.parseInt(sysInvoke.getConsumeServiceNum());
				pc += Integer.parseInt(sysInvoke.getProvideOperationNum());
				cc += Integer.parseInt(sysInvoke.getConsumeOperationNum());
			}
			Row lastRow = sheet.createRow(exportList.size() + 1);
			MappingExcelUtils.fillCell(lastRow, 0, "总计", bodyCellStyle);
			MappingExcelUtils.fillCell(lastRow, 1, "", bodyCellStyle);
			MappingExcelUtils.fillCell(lastRow, 2, "", bodyCellStyle);
			MappingExcelUtils.fillCell(lastRow, 3, "", bodyCellStyle);
			MappingExcelUtils.fillCell(lastRow, 4, "", bodyCellStyle);
			MappingExcelUtils.fillCell(lastRow, 5, "", bodyCellStyle);
			MappingExcelUtils.fillCell(lastRow, 6, "", bodyCellStyle);
			MappingExcelUtils.fillCell(lastRow, 7, String.valueOf(ps), bodyCellStyle);
			MappingExcelUtils.fillCell(lastRow, 8, String.valueOf(cs), bodyCellStyle);
			MappingExcelUtils.fillCell(lastRow, 9, String.valueOf(pc), bodyCellStyle);
			MappingExcelUtils.fillCell(lastRow, 10, String.valueOf(cc), bodyCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(sheet.getPhysicalNumberOfRows()-1,sheet.getPhysicalNumberOfRows()-1,0,6));
		}
		
	}

	private void setOtherStyle(){
		sheet.setColumnWidth(0, 15*256);
		sheet.setColumnWidth(1, 25*256);
		sheet.setColumnWidth(2, 15*256);
		sheet.setColumnWidth(3, 15*256);
		sheet.setColumnWidth(4, 15*256);
		sheet.setColumnWidth(5, 15*256);
		sheet.setColumnWidth(6, 15*256);
		sheet.setColumnWidth(7, 15*256);
		sheet.setColumnWidth(8, 15*256);
		sheet.setColumnWidth(9, 15*256);
		sheet.setColumnWidth(10, 15*256);
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
	public List<SystemInvokeServiceInfo> getAllExportData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SystemInvokeServiceInfo> getExportDataByConditions(
			Map<String, String> mapConditions) {
		// TODO Auto-generated method stub
		return invokeInfoManager.getSystemInvokeServiceInfo(mapConditions);
	}

	
}
