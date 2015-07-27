package com.dc.esb.servicegov.rsimport.impl;

import com.dc.esb.servicegov.entity.Metadata;
import com.dc.esb.servicegov.rsimport.IResourceParser;
import com.dc.esb.servicegov.rsimport.support.ExcelUtils;
import com.dc.esb.servicegov.service.impl.MetadataServiceImpl;
import com.dc.esb.servicegov.service.support.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.hibernate.NonUniqueObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetadataArrayParserImpl implements IResourceParser {

    private static final Log log = LogFactory.getLog(MetadataXlsxParserImpl.class);

    private static final String SHEET_NAME = "ARRAY";
    private static final int START_ROW_NUM = 2;
    private static final int DATA_CATEGORY_COLUMN = 0;
    private static final int BUZZ_CATEGORY_COLUMN = 2;
    private static final int METADATA_ID_COLUMN = 3;
    private static final int CHINESE_NAME_COLUMN = 4;
    private static final int METADATA_NAME_COLUMN = 5;
    private static final int CATEGORY_WORD_ID = 6;
    private static final int TYPE_COLUMN = 7;
    private static final int DATA_FORMULA_COLUMN = 8;
    private static final int OPT_DATE_COLUMN = 13;
    private static final int OPT_USER_COLUMN = 14;
    @Autowired
    private MetadataServiceImpl metadataService;

    @Override
    public void parse(Workbook workbook) {
        Sheet sheet = workbook.getSheet(SHEET_NAME);
        if (null != sheet) {

            parseSheet(sheet);
        }
    }

    private void parseSheet(Sheet sheet) {
//		List<Metadata> metadatas = new ArrayList<Metadata>();
        for (int rowNum = START_ROW_NUM; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            Metadata metadata = parseRow(row);
            String userName = (String) SecurityUtils.getSubject().getPrincipal();
            metadata.setOptUser(userName);
            try {
                metadataService.addMetadata(metadata);
            } catch (NonUniqueObjectException e) {
                log.error("元数据[" + metadata.getMetadataId() + "]重复,执行覆盖！", e);
                Metadata metadataToDel = metadataService.getById(metadata.getMetadataId());
                metadataService.delete(metadataToDel);
                metadataService.save(metadata);
            }
        }
    }

    private Metadata parseRow(Row row) {
        Metadata metadata = new Metadata();
        metadata.setMetadataId(getValueFromCell(row, METADATA_ID_COLUMN));
        metadata.setChineseName(getValueFromCell(row, CHINESE_NAME_COLUMN));
        metadata.setMetadataName(getValueFromCell(row, METADATA_NAME_COLUMN));
        metadata.setCategoryWordId(getValueFromCell(row, CATEGORY_WORD_ID));
        metadata.setDataCategory(getValueFromCell(row, DATA_CATEGORY_COLUMN));
        metadata.setBuzzCategory(getValueFromCell(row, BUZZ_CATEGORY_COLUMN));
        String dataFormula = getValueFromCell(row, DATA_FORMULA_COLUMN);
        String type = getTypeFromFormula(dataFormula);
        metadata.setType(type);
        metadata.setLength("");
        metadata.setScale("");
        metadata.setOptDate(getValueFromCell(row, OPT_DATE_COLUMN));
        metadata.setOptUser(getValueFromCell(row, OPT_USER_COLUMN));
        metadata.setStatus(Constants.Metadata.STATUS_UNAUDIT);
        return metadata;
    }

    //TODO 本地化修改
    public static String getTypeFromFormula(String formula) {
        String type = "String";
        if (null != formula) {
            if (StringUtils.containsIgnoreCase(formula, "ARRAY")) {
                type = "String";
            } else if (StringUtils.containsIgnoreCase(formula, "STRUCT")) {
                type = "Struct";
            }
        }
        return type;
    }

    public static String getValueFromCell(Row row, int column) {
        return ExcelUtils.getValue(row.getCell(column));
    }


}
