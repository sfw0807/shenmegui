package com.dc.esb.servicegov.refactoring.excel.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.excel.support.MappingExcelUtils;
import com.dc.esb.servicegov.refactoring.excel.IConfigGenerater;
import com.dc.esb.servicegov.refactoring.service.impl.PublishInfoManagerImpl;
import com.dc.esb.servicegov.refactoring.vo.PublishInfoVO;
import com.dc.esb.servicegov.refactoring.vo.SvcAsmRelateInfoVO;

@Service
@Transactional
public class PublishInfoExcelGenerator implements IConfigGenerater<Object, PublishInfoVO>{

	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(PublishInfoExcelGenerator.class);
	Workbook wb;
	Sheet sheet;
	private CellStyle headCellStyle;
	private CellStyle bodyCellStyle;
	private static final String fileNamePrefix = "onlineByDate";
	private static final String fileNameSuffix = ".xls";
	private Map<String,String> mapConditions;
	private List<PublishInfoVO> infos ;
	
	@Autowired
	private PublishInfoManagerImpl publishInfoManager;
	
	private void init(Map<String,String> mapConditions){
		wb = new HSSFWorkbook();
		
		sheet = wb.createSheet("Index");
		headCellStyle = MappingExcelUtils.getTitleCellStyle(wb);
		bodyCellStyle = MappingExcelUtils.getBodyCellStyle(wb);
		this.mapConditions = mapConditions;
	}
	
	private void createHeadInfo(){
		Row headRow = sheet.createRow(0);
		MappingExcelUtils.fillCell(headRow, 0, "上线日期", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 1, "上线服务总数", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 2, "新增服务数", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 3, "修改服务数", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 4, "上线操作总数", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 5, "新增操作数", headCellStyle);
		MappingExcelUtils.fillCell(headRow, 6, "修改操作数", headCellStyle);
	}
	
	private void createBodyInfo(){
		infos = new ArrayList<PublishInfoVO>();
		infos = publishInfoManager.getAllPublishTotalInfos(mapConditions);
		int rowIndex = 1;
		if(infos != null && infos.size() > 0){
		  for(PublishInfoVO vo : infos){
			Row bodyRow = sheet.createRow(rowIndex);
			MappingExcelUtils.fillCell(bodyRow, 0, vo.getONLINEDATE(), bodyCellStyle);
			MappingExcelUtils.fillCell(bodyRow, 1, String.valueOf(vo.getCOUNTOFSERVICES()), bodyCellStyle);
			MappingExcelUtils.fillCell(bodyRow, 2, String.valueOf(vo.getCOUNTOFADDSERVICES()), bodyCellStyle);
			MappingExcelUtils.fillCell(bodyRow, 3, String.valueOf(vo.getCOUNOFMODIFYSERVICES()), bodyCellStyle);
			MappingExcelUtils.fillCell(bodyRow, 4, String.valueOf(vo.getCOUNTOFOPERATIONS()), bodyCellStyle);
			MappingExcelUtils.fillCell(bodyRow, 5, String.valueOf(vo.getCOUNTOFADDOPERATIONS()), bodyCellStyle);
			MappingExcelUtils.fillCell(bodyRow, 6, String.valueOf(vo.getCOUNTOFMODIFYOPERATIONS()), bodyCellStyle);
			rowIndex++; 
			// 设置超链接
			Hyperlink href = new HSSFHyperlink(Hyperlink.LINK_DOCUMENT);
			href.setAddress("#" + vo.getONLINEDATE() + "!A1");
			bodyRow.getCell(0).setHyperlink(href);
		  }
		}
	}
	
	private void setOtherStyle(){
		sheet.setColumnWidth(0, 18*256);
		sheet.setColumnWidth(1, 18*256);
		sheet.setColumnWidth(2, 18*256);
		sheet.setColumnWidth(3, 18*256);
		sheet.setColumnWidth(4, 18*256);
		sheet.setColumnWidth(5, 18*256);
		sheet.setColumnWidth(6, 18*256);
		for(int i=0;i<= sheet.getLastRowNum();i++){
			sheet.getRow(i).setHeightInPoints((short) 24);
		}
	}
	
	@Override
	public List<PublishInfoVO> getAllExportData() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<PublishInfoVO> getExportDataByConditions(
			Map<String, String> mapConditions) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public File generate(Map<String,String> mapConditions) throws Exception{ 
		
		File excelFile = new File(fileNamePrefix+fileNameSuffix);
//		ExecutorService executor = Executors.newFixedThreadPool(100);
		init(mapConditions);
		String prdMsgType = (mapConditions.get("prdMsgType") == null)?"":mapConditions.get("prdMsgType");
		String csmMsgType = (mapConditions.get("csmMsgType") == null)?"":mapConditions.get("csmMsgType");
		// create Index sheet contents
		createHeadInfo();
		createBodyInfo();
		setOtherStyle();
		// generate other sheets
		List<PublishInfoExcelGeneratorTask> cc = new ArrayList<PublishInfoExcelGeneratorTask>();
//		CountDownLatch countDown = new CountDownLatch(infos.size());
		for(PublishInfoVO vo : infos){
			// get Each sheet export data info
			List<SvcAsmRelateInfoVO> list = publishInfoManager.getAllExportDatasByOnlineDate(vo.getONLINEDATE(), prdMsgType, csmMsgType);
			Sheet sheet = wb.createSheet(vo.getONLINEDATE());
			// instance Task Object 
			PublishInfoExcelGeneratorTask pTask = new PublishInfoExcelGeneratorTask();
			pTask.init(list, wb, sheet);
			pTask.run();
			cc.add(pTask);
//			countDown.countDown();
		}
//		for(PublishInfoExcelGeneratorTask task : cc){
//			executor.execute(task);
//		}
//		countDown.await(60 * 2, TimeUnit.SECONDS);
		FileOutputStream outputStream = new FileOutputStream(excelFile);
		wb.write(outputStream);
		if (null != outputStream) {
			try {
				outputStream.close();
			} catch (IOException e) {
				log.error("关闭文件[" + excelFile.getAbsolutePath() + "]输出流失败！");
			}
		}
//		executor.shutdown();
		return excelFile;
	}
	
}
