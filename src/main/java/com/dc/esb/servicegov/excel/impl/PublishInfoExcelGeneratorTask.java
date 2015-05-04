package com.dc.esb.servicegov.excel.impl;

import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.dc.esb.servicegov.excel.support.MappingExcelUtils;
import com.dc.esb.servicegov.vo.SvcAsmRelateInfoVO;


public class PublishInfoExcelGeneratorTask implements ExcelGenerateBaseTask<SvcAsmRelateInfoVO> {

	private List<SvcAsmRelateInfoVO> list;
	private CellStyle headCellStyle;
	private CellStyle bodyCellStyle;
	Workbook wb;
	Sheet sheet;
	
	@Override
	public void createBodyInfo() {
		// TODO Auto-generated method stub
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Row bodyRow = sheet.createRow(i + 1);
				SvcAsmRelateInfoVO vo = list.get(i);
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

	@Override
	public void createHeadInfo() {
		// TODO Auto-generated method stub
		Row headRow = sheet.createRow(0);
		MappingExcelUtils.fillCell(headRow, 0, "服务ID/名称", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 1, "操作ID/名称", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 2, "交易代码/名称", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 3, "源系统简称/名称", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 4, "调用方系统简称/名称", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 5, "提供方系统简称/名称", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 6, "调用方报文类型", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 7, "提供方报文类型", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 8, "是否穿透", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 9, "交易状态", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 10, "修订次数", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 11, "上线日期", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 12, "上线版本", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 13, "备注", headCellStyle);
	}

	@Override
	public void init(List<SvcAsmRelateInfoVO> list, Workbook wb, Sheet sheet) {
		// TODO Auto-generated method stub
		this.wb = wb;
		this.sheet = sheet;
		headCellStyle = MappingExcelUtils.getTitleCellStyle(wb);
		bodyCellStyle = MappingExcelUtils.getBodyCellStyle(wb);
		this.list = list;
	}

	@Override
	public void setOtherStyle() {
		// TODO Auto-generated method stub
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

	//@Override
	public void run() {
		// TODO Auto-generated method stub
		createHeadInfo();
		createBodyInfo();
		setOtherStyle();
	}


}
