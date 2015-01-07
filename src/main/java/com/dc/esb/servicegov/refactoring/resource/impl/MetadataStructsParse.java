package com.dc.esb.servicegov.refactoring.resource.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.MetadataStructsAttrDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.MetadataStructsDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.MetadataStructs;
import com.dc.esb.servicegov.refactoring.entity.MetadataStructsAttr;
import com.dc.esb.servicegov.refactoring.util.ExcelTool;


@Service
@Transactional(rollbackFor=Exception.class)
public class MetadataStructsParse {
	
	private Log log = LogFactory.getLog(MetadataStructsParse.class);
	private ExcelTool excelTool = ExcelTool.getInstance();
	
	private Row row;
	private Sheet sheet;
	// 下标从5 parse
	private int structIndex;

	@Autowired
	private MetadataStructsDAOImpl mdtStructsDAO;
	@Autowired
	private MetadataStructsAttrDAOImpl mdtStructsAttrDAO;
	
	private String structId;
	private String esbStructId;
	private String esbStructName;
	
	
	public void parse(Workbook wb, Sheet index1Sheet){
		log.info("begin to import metadataStructs!");
		for(int i=1;i<index1Sheet.getPhysicalNumberOfRows();i++){
			row = index1Sheet.getRow(i);
			// 结构英文名称
			structId = excelTool.getCellContent(row.getCell(0));
			// ESB结构英文名称
			esbStructId = excelTool.getCellContent(row.getCell(3));
			// ESB结构名称
			esbStructName = excelTool.getCellContent(row.getCell(4));
			saveMdtStruct(esbStructId,esbStructName);
			
			sheet = wb.getSheet(structId);
			if(sheet != null){
				parseSheet();
			}
		}
		log.info("import metadataStructs finished!");
	}
	
	public void parseSheet(){
		// 第5行开始解析
		structIndex = 5;
		List<MetadataStructsAttr> list = new ArrayList<MetadataStructsAttr>();
		while(!"".equals(excelTool.getCellContent(sheet, structIndex, 0))){
			String elementId = excelTool.getCellContent(sheet, structIndex, 0);
			String elementName = excelTool.getCellContent(sheet, structIndex, 1);
			String remark = excelTool.getCellContent(sheet, structIndex, 4);
			String isRequire = excelTool.getCellContent(sheet, structIndex, 3);
			String metadataId = excelTool.getCellContent(sheet, structIndex, 6);
			MetadataStructsAttr mdtAttr = new MetadataStructsAttr();
			mdtAttr.setStructId(esbStructId);
			mdtAttr.setElementId(elementId);
			mdtAttr.setElementName(elementName);
			mdtAttr.setMetadataId(metadataId);
			mdtAttr.setIsRequired(isRequire);
			mdtAttr.setRemark(remark);
			list.add(mdtAttr);
			structIndex++;
		}
		if(mdtStructsAttrDAO.delByStructId(esbStructId)){
			mdtStructsAttrDAO.batchInsOrUptByList(list);
		}
	}
	
	public void saveMdtStruct(String esbStructId,String esbStructName){
		MetadataStructs mdtStructs = new MetadataStructs();
		mdtStructs.setStructId(esbStructId);
		mdtStructs.setStructName(esbStructName);
		mdtStructsDAO.save(mdtStructs);
	}
}
