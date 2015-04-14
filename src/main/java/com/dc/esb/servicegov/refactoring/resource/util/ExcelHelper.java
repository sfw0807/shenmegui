package com.dc.esb.servicegov.refactoring.resource.util;

import com.dc.esb.servicegov.refactoring.util.ExcelTool;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Created by vincentfxz on 15/4/9.
 */
public class ExcelHelper {
    private static final int SDA_REMARK_COL = 12;
    private static final int SDA_TYPE_COL = 9;
    private static final int SDA_METADATA_COL = 7;
    private static final int SDA_REQUIRE_COL = 11;

    private static final int IDA_REMARK_COL = 5;
    private static final int IDA_NODE_ID_COL = 0;
    private static final int IDA_NODE_NAME_COL = 1;
    private static final int IDA_TYPE_COL = 2;
    private static final int IDA_LENGTH_COL = 3;
    private static final int IDA_REQUIRE_COL = 4;

    private static final ExcelTool excelTool = ExcelTool.getInstance();

    public static String getSDAType(Sheet interfaceSheet, int row) {
        String type = excelTool
                .getCellContent(interfaceSheet, row, SDA_TYPE_COL);
        type = type.replace("（", "(");
        type = type.replace("）", ")");
        type = type.toLowerCase();
        return type;
    }

    public static String getSDARemark(Sheet interfaceSheet, int row) {
        return excelTool
                .getCellContent(interfaceSheet, row, SDA_REMARK_COL);
    }

    public static String getSDAMetadata(Sheet interfaceSheet, int row) {
        return excelTool
                .getCellContent(interfaceSheet, row, SDA_METADATA_COL);
    }

    public static String getSDARequire(Sheet interfaceSheet, int row) {
        return excelTool
                .getCellContent(interfaceSheet, row, SDA_REQUIRE_COL);
    }

    public static String getIDARemark(Sheet interfaceSheet, int row) {
        return excelTool
                .getCellContent(interfaceSheet, row, IDA_REMARK_COL);
    }

    public static String getIDANodeID(Sheet interfaceSheet, int row) {
        return excelTool
                .getCellContent(interfaceSheet, row, IDA_NODE_ID_COL);
    }

    public static String getIDANodeName(Sheet interfaceSheet, int row) {
        return excelTool
                .getCellContent(interfaceSheet, row, IDA_NODE_NAME_COL);
    }

    public static String getIDAType(Sheet interfaceSheet, int row) {
        String type = excelTool
                .getCellContent(interfaceSheet, row, IDA_TYPE_COL);
        type = type.replace("（", "(");
        type = type.replace("）", ")");
        type = type.toLowerCase();
        return type;
    }

    public static String getIDALength(Sheet interfaceSheet, int row) {
        return excelTool
                .getCellContent(interfaceSheet, row, IDA_LENGTH_COL);
    }

    public static String getIDARequired(Sheet interfaceSheet, int row) {
        return excelTool
                .getCellContent(interfaceSheet, row, IDA_REQUIRE_COL);
    }


}
