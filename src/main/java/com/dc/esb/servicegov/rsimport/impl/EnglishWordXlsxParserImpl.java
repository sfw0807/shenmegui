package com.dc.esb.servicegov.rsimport.impl;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dc.esb.servicegov.entity.EnglishWord;
import com.dc.esb.servicegov.rsimport.IResourceParser;
import com.dc.esb.servicegov.rsimport.support.ExcelUtils;
import com.dc.esb.servicegov.service.impl.EnglishWordServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EnglishWordXlsxParserImpl implements IResourceParser {

	private static final String SHEET_NAME = "表2中英文名称及缩写对照表";
	private static final int START_ROW_NUM = 2;
	private static final int CHINESE_WORD_COLUMN = 0;
	private static final int ENGLISH_WORD_COLUMN = 1;
	private static final int WORDA_COLUMN = 2;
	private static final int OPT_DATE_COLUMN = 3;
	private static final int OPT_USER_COLUMN = 4;
	@Autowired
    private EnglishWordServiceImpl englishWordService;
	@Override
	public void parse(Workbook workbook) {
		Sheet sheet = workbook.getSheet(SHEET_NAME);
		parseSheet(sheet);
	}

	private void parseSheet(Sheet sheet) {
//		List<EnglishWord> englishWords = new ArrayList<EnglishWord>();
		for (int rowNum = START_ROW_NUM; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			EnglishWord englishWord =parseRow(row);
			englishWordService.save(englishWord);
		}
		
	}

	private EnglishWord parseRow(Row row) {
		EnglishWord englishWord = new EnglishWord();
		englishWord.setChineseWord(ExcelUtils.getValue(row.getCell(CHINESE_WORD_COLUMN)));
		englishWord.setEnglishWord(ExcelUtils.getValue(row.getCell(ENGLISH_WORD_COLUMN)));
		englishWord.setWordAb(ExcelUtils.getValue(row.getCell(WORDA_COLUMN)));
		englishWord.setOptDate(ExcelUtils.getValue(row.getCell(OPT_DATE_COLUMN)));
		englishWord.setOptUser(ExcelUtils.getValue(row.getCell(OPT_USER_COLUMN)));
		return englishWord;
	}

}
