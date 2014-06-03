package com.dc.esb.servicegov.util;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-28
 * Time: 下午3:55
 */
public class PdfUtils {

    private static final Log log = LogFactory.getLog(PdfUtils.class);
    public static Font ST_SONG_FONT = null;
    public static Font ST_SONG_SMALL_FONT = null;
    public static Font ST_SONG_SMALL_BOLD_FONT = null;
    public static Font ST_SONG_MIDDLE_FONT = null;
    public static Font ST_SONG_MIDDLE_BOLD_FONT = null;
    public static Font ST_SONG_BIG_FONT = null;
    public static Font ST_SONG_BIG_BOLD_FONT = null;
    public static Font TABLE_NORMAL_FONT = null;
    public static Font TABLE_BOLD_FONT = null;
    public static Font NORMAL_SMALL_FONT = null;
    public static Font NORMAL_SMALL_BOLD_FONT = null;
    public static Font NORMAL_MIDDLE_FONT = null;
    public static Font NORMAL_MIDDLE_BOLD_FONT = null;
    public static Font NORMAL_BIG_FONT = null;
    public static Font NORMAL_BIG_BOLD_FONT = null;

    static {
        BaseFont bfChinese = null;
        String font = "STSongStd-Light";
        String code = "UniGB-UCS2-H";
        try {
            bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
            Font fontChineseBlack = new Font(bfChinese, 12, Font.NORMAL, Color.BLACK);
            ST_SONG_FONT = new Font(bfChinese, 12, Font.NORMAL, Color.BLACK);
            ST_SONG_SMALL_FONT = new Font(bfChinese, 8, Font.NORMAL, Color.BLACK);
            ST_SONG_SMALL_BOLD_FONT = new Font(bfChinese, 8, Font.BOLD, Color.BLACK);
            ST_SONG_MIDDLE_BOLD_FONT = new Font(bfChinese, 10, Font.BOLD, Color.BLACK);
            ST_SONG_MIDDLE_FONT = new Font(bfChinese, 10, Font.NORMAL, Color.BLACK);
            ST_SONG_BIG_FONT = new Font(bfChinese, 12, Font.NORMAL, Color.BLACK);
            ST_SONG_BIG_BOLD_FONT = new Font(bfChinese, 12, Font.BOLD, Color.BLACK);
            TABLE_NORMAL_FONT = new Font(Font.TIMES_ROMAN, 8);
            TABLE_BOLD_FONT = new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.BLACK);
            NORMAL_SMALL_FONT = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.BLACK);
            NORMAL_SMALL_BOLD_FONT = new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.BLACK);
            NORMAL_MIDDLE_FONT = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.BLACK);
            NORMAL_MIDDLE_BOLD_FONT = new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.BLACK);
            NORMAL_BIG_FONT = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, Color.BLACK);
            NORMAL_BIG_BOLD_FONT = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.BLACK);
        } catch (Exception e) {
            String errorMsg = "获取中文字体[" + font + ":" + code + "]失败！";
            log.error(errorMsg);
        }
    }

    public static void renderChinese(String content, Document doc) throws Exception {
        Paragraph paragraph = new Paragraph(content, ST_SONG_FONT);
        doc.add(paragraph);
    }

    public static void renderLatin(String content, Document doc) throws Exception {
        Paragraph paragraph = new Paragraph(content);
        doc.add(paragraph);
    }

    public static void renderChineseInBlock(String content, Paragraph paragraph) {
        Phrase phrase = new Phrase(content, ST_SONG_FONT);
        paragraph.add(phrase);
    }

    public static void renderLatinInBlock(String content, Paragraph paragraph) {
        Phrase phrase = new Phrase(content);
        paragraph.add(phrase);
    }

    public static void renderInLine(String content, Phrase phrase, Font font) {
        Phrase phraseToAppend = new Phrase(content, font);
        phrase.add(phraseToAppend);
    }

    public static void renderLatinTableHeader(String content, PdfPCell cell) {
        renderTableHeader(content, cell, TABLE_NORMAL_FONT);
    }

    public static void renderChineseTableHeader(String content, PdfPCell cell) {
        renderTableHeader(content, cell, ST_SONG_SMALL_BOLD_FONT);
    }

    public static void renderTableHeader(String content, PdfPCell cell, Font font) {
        Phrase phrase = null;
        if (null != font) {
            phrase = new Phrase(content, font);
        } else {
            phrase = new Phrase(content);
        }
        cell.setPhrase(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(Color.LIGHT_GRAY);

    }

    public static void renderTableHeader(Phrase phrase, PdfPCell cell) {
        cell.setPhrase(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
    }

    public static void renderChineseTableData(String content, PdfPCell cell) {
        renderTableData(content, cell, ST_SONG_SMALL_FONT);
    }

    public static void renderLatinTableData(String content, PdfPCell cell) {
        renderTableData(content, cell, TABLE_NORMAL_FONT);
    }

    public static void renderTableData(String content, PdfPCell cell, Font font) {
        Phrase phrase = null;
        if (null != font) {
            phrase = new Phrase(content, font);
        } else {
            phrase = new Phrase(content);
        }
        cell.setPhrase(phrase);
    }
}
