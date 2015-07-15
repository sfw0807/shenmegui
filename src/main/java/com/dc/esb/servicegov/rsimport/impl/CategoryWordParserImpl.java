package com.dc.esb.servicegov.rsimport.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.NonUniqueObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dc.esb.servicegov.entity.CategoryWord;

import com.dc.esb.servicegov.rsimport.IResourceParser;
import com.dc.esb.servicegov.rsimport.support.ExcelUtils;
import com.dc.esb.servicegov.service.impl.CategoryWordServiceImpl;
@Component
public class CategoryWordParserImpl implements IResourceParser {

	private static final Log log = LogFactory.getLog(CategoryWordParserImpl.class);

	private static final String SHEET_NAME = "表3类别词";
	private static final int START_ROW_NUM = 1;
	private static final int CHINESE_WORD_COLUMN = 0;
	private static final int ENGLISH_WORD_COLUMN = 1;
	private static final int ESGLISGA_COLUMN = 2;
	private static final int REMARK_COLUMN = 3;

    @Autowired
    private CategoryWordServiceImpl categoryWordService;
	@Override
	public void parse(Workbook workbook) {
		Sheet sheet = workbook.getSheet(SHEET_NAME);//根据页签名找到对应的表
		parseSheet(sheet);
	}
	
	private void parseSheet(Sheet sheet) {
		for (int rowNum = START_ROW_NUM; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			CategoryWord categoryWord =parseRow(row);
			try{
				categoryWordService.save(categoryWord);
			}catch(NonUniqueObjectException e){
				log.error("类别词["+categoryWord.getId()+"]重复,执行覆盖！",e);
				CategoryWord categoryWordToDel = categoryWordService.getById(categoryWord.getId());
				categoryWordService.delete(categoryWordToDel);
				categoryWordService.save(categoryWord);
			}
		}
	}
	
	private CategoryWord parseRow(Row row) {//将对应列的数据插入list
		CategoryWord categoryWord = new CategoryWord();
		categoryWord.setChineseWord(ExcelUtils.getValue(row.getCell(CHINESE_WORD_COLUMN)));
		categoryWord.setEnglishWord(ExcelUtils.getValue(row.getCell(ENGLISH_WORD_COLUMN)));
		categoryWord.setEsglisgAb(ExcelUtils.getValue(row.getCell(ESGLISGA_COLUMN)));
		categoryWord.setRemark(ExcelUtils.getValue(row.getCell(REMARK_COLUMN)));
		return categoryWord;
	}
}
