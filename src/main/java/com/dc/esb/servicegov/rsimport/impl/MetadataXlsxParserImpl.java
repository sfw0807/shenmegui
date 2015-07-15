package com.dc.esb.servicegov.rsimport.impl;


import com.dc.esb.servicegov.entity.CategoryWord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.NonUniqueObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dc.esb.servicegov.entity.Metadata;
import com.dc.esb.servicegov.rsimport.IResourceParser;
import com.dc.esb.servicegov.rsimport.support.ExcelUtils;
import com.dc.esb.servicegov.service.impl.MetadataServiceImpl;

@Component
public class MetadataXlsxParserImpl implements IResourceParser {

	private static final Log log = LogFactory.getLog(MetadataXlsxParserImpl.class);

	private static final String SHEET_NAME = "数据字典";
	private static final int START_ROW_NUM = 2;
	private static final int METADATA_ID_COLUMN = 3;
	private static final int CHINESE_NAME_COLUMN = 4;
	private static final int METADATA_NAME_COLUMN = 5;
	private static final int CATEGORY_WORD_ID = 6;
	private static final int TYPE_COLUMN = 7;
	private static final int LENGTH_COLUMN = 8;
	private static final int OPT_DATE_COLUMN = 13;
	private static final int OPT_USER_COLUMN = 14;
	@Autowired
	private MetadataServiceImpl metadataService;
	@Override
	public void parse(Workbook workbook) {
		Sheet sheet = workbook.getSheet(SHEET_NAME);
		parseSheet(sheet);
	}
	private void parseSheet(Sheet sheet) {
//		List<Metadata> metadatas = new ArrayList<Metadata>();
		for (int rowNum = START_ROW_NUM; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			Metadata metadata =parseRow(row);
			try{
			metadataService.addMetadata(metadata);
			}catch(NonUniqueObjectException e){
				log.error("元数据["+metadata.getMetadataId()+"]重复,执行覆盖！",e);
				Metadata metadataToDel = metadataService.getById(metadata.getMetadataId());
				metadataService.delete(metadataToDel);
				metadataService.save(metadata);
			}
		}
	}

	private Metadata parseRow(Row row) {
		Metadata metadata = new Metadata();
		metadata.setMetadataId(ExcelUtils.getValue(row.getCell(METADATA_ID_COLUMN)));
		metadata.setChineseName(ExcelUtils.getValue(row.getCell(CHINESE_NAME_COLUMN)));	
		metadata.setMetadataName(ExcelUtils.getValue(row.getCell(METADATA_NAME_COLUMN)));
		metadata.setCategoryWordId(ExcelUtils.getValue(row.getCell(CATEGORY_WORD_ID)));
		metadata.setType(ExcelUtils.getValue(row.getCell(TYPE_COLUMN)));
		metadata.setLength(ExcelUtils.getValue(row.getCell(LENGTH_COLUMN)));
		metadata.setOptDate(ExcelUtils.getValue(row.getCell(OPT_DATE_COLUMN)));
		metadata.setOptUser(ExcelUtils.getValue(row.getCell(OPT_USER_COLUMN)));
		return metadata;
	}


}
