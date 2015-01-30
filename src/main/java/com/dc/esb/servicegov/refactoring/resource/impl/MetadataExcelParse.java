package com.dc.esb.servicegov.refactoring.resource.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.MetadataDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.Metadata;
import com.dc.esb.servicegov.refactoring.util.ExcelTool;
import com.dc.esb.servicegov.refactoring.util.GlobalImport;
import com.dc.esb.servicegov.refactoring.util.GlobalMenuId;
import com.dc.esb.servicegov.refactoring.util.UserOperationLogUtil;
import com.dc.esb.servicegov.refactoring.util.Utils;


@Service
@Transactional(rollbackFor=Exception.class)
public class MetadataExcelParse {

    private Log log = LogFactory.getLog(MetadataExcelParse.class);
	private ExcelTool excelTool = ExcelTool.getInstance();

	// metadata sheet
	private Sheet mdtSheet;
	// metadata array sheet
	private Sheet arraySheet;
	
	@Autowired
	private MetadataDAOImpl metadataDAO;
	
	public boolean parse(Sheet mdtSheet, Sheet arraySheet){
		log.info("begin import metadatas!");
		this.mdtSheet = mdtSheet;
		this.arraySheet = arraySheet;
		if(mdtSheet != null){
			parseMdtSheet();
		}
		if(arraySheet != null){
			parseArraySheet();
		}
		log.info("import metadatas finished!");
		return true;
	}
	/**
	 * parse metadata sheet
	 */
	private boolean parseMdtSheet(){
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
		    if("".equals(metadataId)){
		    	log.error("元数据Excel中[数据字典]页 规范字段名称不能为空！");
				UserOperationLogUtil.saveLog("元数据Excel中[数据字典]页 规范字段名称不能为空！", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
				GlobalImport.flag = false;
				return false;
		    }
			name = excelTool.getCellContent(mdtSheet, rowNum, 2);
			value = excelTool.getCellContent(mdtSheet, rowNum, 1);
			value = value.replace("（", "(");
			value = value.replace("）", ")");
			if("".equals(value)){
		    	log.error("元数据Excel中[数据字典]页 规范字段类型不能为空！");
				UserOperationLogUtil.saveLog("元数据Excel中[数据字典]页 规范字段类型不能为空！", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
				GlobalImport.flag = false;
				return false;
		    }
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
		// 检查元数据Id是否存在重复
		String duplicateId = null;
		if((duplicateId = checkDuplicateList(list)) != null){
			log.error("元数据Excel中[数据字典]页 规范字段名称["+ duplicateId +"]不能重复！");
			UserOperationLogUtil.saveLog("元数据Excel中[数据字典]页 规范字段名称["+ duplicateId +"]不能重复！", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
			GlobalImport.flag = false;
			return false;
		}
		metadataDAO.batchSaveMetadatas(list);
		return true;
	}
	
	/**
	 * parse metadata array sheet
	 */
	private boolean parseArraySheet(){
		int rowNum = 1;
		List<Metadata> list = new ArrayList<Metadata>();
		Metadata metadata = null;
		while(!"".equals(excelTool.getCellContent(arraySheet, rowNum, 0))){
			metadata = new Metadata();
			metadata.setMetadataId(excelTool.getCellContent(arraySheet, rowNum, 0));
			if("".equals(excelTool.getCellContent(arraySheet, rowNum, 0))){
		    	log.error("元数据Excel中[数组]页 规范字段名称不能为空！");
				UserOperationLogUtil.saveLog("元数据Excel中[数组]页 规范字段名称不能为空！", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
				GlobalImport.flag = false;
				return false;
		    }
			metadata.setName(excelTool.getCellContent(arraySheet, rowNum, 2));
			metadata.setType("array");
			metadata.setRemark(excelTool.getCellContent(arraySheet, rowNum, 5));
			// session get
			metadata.setModifyUser("");
			metadata.setUpdateTime(Utils.getTime());
			list.add(metadata);
			rowNum++;
		}
		// 检查元数据Id是否存在重复
		String duplicateId = null;
		if((duplicateId = checkDuplicateList(list)) != null){
			log.error("元数据Excel中[数组]页 规范字段名称["+ duplicateId +"]不能重复！");
			UserOperationLogUtil.saveLog("元数据Excel中[数组]页 规范字段名称["+ duplicateId +"]不能重复！", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
			GlobalImport.flag = false;
			return false;
		}
		metadataDAO.batchSaveMetadatas(list);
		return true;
	}
	
	/**
	 * 检查导入的元数据是否存在重复，重复则不导入，提示失败
	 * @param list
	 */
	public String checkDuplicateList(List<Metadata> list){
		String duplicateId = null;
		for(int i=0; i<list.size(); i++){
			Metadata firstMdt = list.get(i);
			for(int j = i + 1; j<list.size(); j++){
				Metadata secondMdt = list.get(j);
				if(firstMdt.getMetadataId().equals(secondMdt.getMetadataId())){
					return firstMdt.getMetadataId();
				}
			}
		}
		return duplicateId;
	}
}
