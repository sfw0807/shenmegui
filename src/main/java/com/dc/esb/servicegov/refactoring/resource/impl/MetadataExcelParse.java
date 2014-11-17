package com.dc.esb.servicegov.refactoring.resource.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.MetadataDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.Metadata;
import com.dc.esb.servicegov.refactoring.util.ExcelTool;
import com.dc.esb.servicegov.refactoring.util.GlobalImport;
import com.dc.esb.servicegov.refactoring.util.Utils;


@Service
@Transactional(rollbackFor=Exception.class)
public class MetadataExcelParse {

    private Log log = LogFactory.getLog(MetadataExcelParse.class);
	private ExcelTool excelTool = ExcelTool.getInstance();

	private static final String mdtStr = "数据字典";
	private static final String arrayStr = "数组";

	// metadata sheet
	private Sheet mdtSheet;
	// metadata array sheet
	private Sheet arraySheet;
	
	@Autowired
	private MetadataDAOImpl metadataDAO;
	
	public void parse(Workbook wb){
		log.info("begin import metadatas!");
		mdtSheet = wb.getSheet(mdtStr);
		arraySheet = wb.getSheet(arrayStr);
		if(mdtSheet == null && arraySheet == null){
			log.error("元数据Excel中未找到[数据字典]和[数组]向导页！");
			GlobalImport.flag = false;
		}
		if(mdtSheet != null){
			parseMdtSheet();
		}
		if(arraySheet != null){
			parseArraySheet();
		}
		log.info("import metadatas finished!");
	}
	/**
	 * parse metadata sheet
	 */
	private void parseMdtSheet(){
		int rowNum = 1;
		List<Metadata> list = new ArrayList<Metadata>();
		Metadata metadata = null;
		String metadataId;
		// type length and scale
		String value;
		String type;
		String length;
		String scale;
		String name;
		String remark;
		while(!"".equals(excelTool.getCellContent(mdtSheet, rowNum, 0))){
		    metadataId = excelTool.getCellContent(mdtSheet, rowNum, 0);
			name = excelTool.getCellContent(mdtSheet, rowNum, 2);
			value = excelTool.getCellContent(mdtSheet, rowNum, 1);
			type = Utils.getDataType(value);
			length = Utils.getDataLength(value);
			scale = Utils.getDataScale(value);
			remark = excelTool.getCellContent(mdtSheet, rowNum, 6);
			
			metadata = new Metadata();
			metadata.setMetadataId(metadataId);
			metadata.setName(name);
			metadata.setType(type);
			metadata.setLength(length);
			metadata.setScale(scale);
			metadata.setRemark(remark);;
			// session get
			metadata.setModifyUser("");
			metadata.setUpdateTime(Utils.getTime());
			list.add(metadata);
			rowNum++;
		}
		metadataDAO.batchSaveMetadatas(list);
	}
	
	/**
	 * parse metadata array sheet
	 */
	private void parseArraySheet(){
		int rowNum = 1;
		List<Metadata> list = new ArrayList<Metadata>();
		Metadata metadata = null;
		while(!"".equals(excelTool.getCellContent(arraySheet, rowNum, 0))){
			metadata = new Metadata();
			metadata.setMetadataId(excelTool.getCellContent(arraySheet, rowNum, 0));
			metadata.setName(excelTool.getCellContent(arraySheet, rowNum, 2));
			metadata.setType("array");
			metadata.setRemark(excelTool.getCellContent(arraySheet, rowNum, 5));
			// session get
			metadata.setModifyUser("");
			metadata.setUpdateTime(Utils.getTime());
			list.add(metadata);
			rowNum++;
		}
		metadataDAO.batchSaveMetadatas(list);
	}
}
