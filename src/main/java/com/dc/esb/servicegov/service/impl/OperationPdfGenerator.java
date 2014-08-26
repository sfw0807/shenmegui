package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.service.PdfGenerator;
import com.dc.esb.servicegov.util.PdfUtils;
import com.dc.esb.servicegov.vo.MetadataViewBean;
import com.dc.esb.servicegov.vo.SDA;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-28
 * Time: 下午1:51
 */
@Component
public class OperationPdfGenerator implements PdfGenerator<List<Service>> {

    private static final Log log = LogFactory.getLog(OperationPdfGenerator.class);
    //    private static final String TMP_PDF_DIR = System.getProperty("com.dc.esb.servicegov.pdf.dir");
    private static final String TMP_PDF_DIR = "D:/pdftmp";
    private static Font ST_SONG_FONT = null;
    @Autowired
    private ServiceManagerImpl serviceManager;
    @Autowired
    private MetadataManagerImpl metadataManager;

    static {
        BaseFont bfChinese = null;
        String font = "STSongStd-Light";
        String code = "UniGB-UCS2-H";
        try {
            bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
            ST_SONG_FONT = new Font(bfChinese, 12, Font.NORMAL, Color.BLACK);

        } catch (Exception e) {
            String errorMsg = "获取中文字体[" + font + ":" + code + "]失败！";
            log.error(errorMsg);
        }
    }

    @Override
    public File generate(List<Service> services) throws Exception {
        File pdfFile = null;
        if (null != services) {
            String pdfPath = TMP_PDF_DIR + File.separator + "浦发银行服务手册.pdf";
            pdfFile = new File(pdfPath);
            if (pdfFile.exists()) {
                boolean deleted = pdfFile.delete();
                if (!deleted) {
                    String errorMsg = "删除已经存在的文件[" + pdfPath + "]";
                    log.error(errorMsg);
                    throw new Exception(errorMsg);
                }
            }
            pdfFile.createNewFile();
            //创建pdf的document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile))
                    .setInitialLeading(16);
            document.open();
            try {
                for (Service service : services) {
                    try {
                        render(service, document, null);
                    } catch (Exception e) {
                        String errorMsg = "为服务[" + service.getServiceId() + ":" + service.getServiceName() + "]创建pdf时失败！";
                        log.error(errorMsg, e);
                        throw e;
                    }

                }
            } finally {
                document.close();
            }
        } else {
            String errorMsg = "生成PDF失败，服务为空！";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        return pdfFile;
    }

    public void generate(List<Service> services, Document document, Section section) throws Exception {
        for (Service service : services) {
            try {
                render(service, document, section);
            } catch (Exception e) {
                String errorMsg = "为操作[" + service.getServiceId() + ":" + service.getServiceName() + "]创建pdf时失败！";
                log.error(errorMsg, e);
                throw e;
            }
        }
    }
    
    private class operationRenderProcess implements Callable{

		@Override
		public Object call() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
    	
    }

    private void render(Service service, Document document, Section section) throws Exception {
    	long t1 = System.currentTimeMillis();
        Section operationSection = renderTitle(service, document, section);
        long t2 = System.currentTimeMillis();
        log.error("gen operation title:" + service.getServiceId() +",time:"+(t2-t1));
        renderSDA(service, document, operationSection);
        long t3 = System.currentTimeMillis();
        log.error("gen operation body:" + service.getServiceId() +",time:"+(t3-t1));
    }

    private Section renderTitle(Service service, Document document, Section section) throws Exception {
        String operationId = service.getServiceId();
        String operationName = service.getServiceName();
        Section operationSection = null;

        String operationRemark = service.getServiceRemark();
        if (null == operationRemark) {
            operationRemark = "该操作尚无描述。";
        } else {
            operationRemark = operationRemark.trim();
            if ("".equals(operationRemark)) {
                operationRemark = "该操作尚无描述。";
            }
        }

        try {
            Phrase opDescPhrase = new Phrase();
            PdfUtils.renderInLine(operationId + ",", opDescPhrase, PdfUtils.NORMAL_MIDDLE_FONT);
            PdfUtils.renderInLine(operationName + "。" + operationRemark, opDescPhrase, PdfUtils.ST_SONG_MIDDLE_FONT);
            Paragraph opDescParagraph = new Paragraph(opDescPhrase);
            opDescParagraph.setFirstLineIndent(20);
            operationSection = section.addSection(opDescParagraph);
            operationSection.setBookmarkTitle(operationId + "(" + operationName + ")");
            operationSection.setBookmarkOpen(false);
            operationSection.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
//            document.add(opDescParagraph);
        } catch (Exception e) {
            String errorMsg = "渲染服务名称[" + operationId + operationName + "]失败！";
            log.error(errorMsg, e);
            throw e;
        }
        return operationSection;

    }

    private void renderSDA(Service service, Document document, Section section) throws Exception {
        try {
        	long startTime = System.currentTimeMillis();
            SDA sda = serviceManager.getSDAofService(service);
            long endTime = System.currentTimeMillis();
            log.info("get SDA in ["+ (endTime - startTime) + "]");
            PdfPTable table = new PdfPTable(6);
            PdfPCell thCell = new PdfPCell();

            Phrase operationPhrase = new Phrase();
            operationPhrase.add(new Phrase("操作", PdfUtils.ST_SONG_SMALL_BOLD_FONT));
            operationPhrase.add(new Phrase(":" + service.getServiceId(), PdfUtils.TABLE_BOLD_FONT));
            PdfUtils.renderTableHeader(operationPhrase, thCell);
            thCell.setColspan(6);
            table.addCell(thCell);
            PdfPCell headerENcell = new PdfPCell();
            PdfUtils.renderChineseTableHeader("字段名称", headerENcell);
            table.addCell(headerENcell);
            PdfPCell headerTypeENcell = new PdfPCell();
            PdfUtils.renderChineseTableHeader("字段类型", headerTypeENcell);
            table.addCell(headerTypeENcell);
            PdfPCell headerCNcell = new PdfPCell(new Phrase());
            PdfUtils.renderChineseTableHeader("字段说明", headerCNcell);
            table.addCell(headerENcell);
            PdfPCell headerRequired = new PdfPCell();
            PdfUtils.renderChineseTableHeader("是否必输", headerRequired);
            table.addCell(headerRequired);
            PdfPCell headerResist = new PdfPCell();
            PdfUtils.renderChineseTableHeader("约束条件", headerResist);
            table.addCell(headerResist);
            PdfPCell headerRemarkcell = new PdfPCell();
            PdfUtils.renderChineseTableHeader("备注", headerRemarkcell);
            table.addCell(headerRemarkcell);
            List<SDA> childrenOfRoot = sda.getChildNode();
            SDA reqSDA = null;
            SDA rspSDA = null;
            if (null != childrenOfRoot) {
                for (SDA node : childrenOfRoot) {
                    if ("request".equalsIgnoreCase(node.getValue().getStructName())) {
                        reqSDA = node;
                    }
                    if ("response".equalsIgnoreCase(node.getValue().getStructName())) {
                        rspSDA = node;
                    }
                }
            }
            PdfPCell headerDirectcell = new PdfPCell();
            PdfUtils.renderChineseTableHeader("输入", headerDirectcell);
            headerDirectcell.setBackgroundColor(Color.PINK);
            headerDirectcell.setColspan(6);
            table.addCell(headerDirectcell);
            if (null != reqSDA) {
                List<SDA> childrenOfReq = reqSDA.getChildNode();
                if (null != childrenOfReq) {
                    for (SDA childOfReq : childrenOfReq) {
                        renderSDANode(childOfReq, table, 0, Color.PINK);
                    }
                }
            }
            PdfPCell headerDirectcell2 = new PdfPCell();
            PdfUtils.renderChineseTableHeader("输出", headerDirectcell2);
            headerDirectcell2.setBackgroundColor(Color.CYAN);
            headerDirectcell2.setColspan(6);
            table.addCell(headerDirectcell2);
            if (null != rspSDA) {
                List<SDA> childrenOfRsp = rspSDA.getChildNode();
                if (null != childrenOfRsp) {
                    for (SDA childSDA : childrenOfRsp) {
                        renderSDANode(childSDA, table, 0, Color.CYAN);
                    }
                }
            }
            table.setSpacingBefore(5);
            section.add(table);
//            document.add(table);
        } catch (Exception e) {
            log.error("渲染服务[" + service.getServiceId() + "]的SDA失败！");
            throw e;
        }
    }

    private void renderSDANode(SDA sda, PdfPTable table, int offset, Color indexColor) throws DataException {
        String sdaNodeId = sda.getValue().getStructName();
        String sdaNodeType = "";
        String sdaNodeChineseName = sda.getValue().getStructAlias();
        String sdaNodeRequired = sda.getValue().getRequired();
        String sdaNodeResist = "";
        String sdaNodeRemark = sda.getValue().getRemark();

        String metadataId = sda.getValue().getMetadataId();
        if (null != metadataId && !"".equals(metadataId)) {
            MetadataViewBean metadataViewBean = metadataManager.getMetadataById(metadataId);
            String type = metadataViewBean.getType();
            String length = metadataViewBean.getLength();
            String scale = metadataViewBean.getScale();
            if (null != type) {
                sdaNodeType = type;
            }
            String typeAndScale = null;
            if (null != length) {
                typeAndScale = length;
            }
            if (null != scale) {
                typeAndScale = typeAndScale + "," + scale;
            }
            if (null != typeAndScale) {
                sdaNodeType = sdaNodeType + "(" + typeAndScale + ")";
            }
        }
        PdfPCell idCell = new PdfPCell();
        PdfUtils.renderLatinTableData(sdaNodeId, idCell);
        idCell.setIndent(offset);
        if (null != indexColor) {
            idCell.setBackgroundColor(indexColor);
        }
        table.addCell(idCell);

        PdfPCell typeCell = new PdfPCell();
        PdfUtils.renderLatinTableData(sdaNodeType, typeCell);
        table.addCell(typeCell);

        PdfPCell cnCell = new PdfPCell();
        PdfUtils.renderChineseTableData(sdaNodeChineseName, cnCell);
        table.addCell(cnCell);

        PdfPCell requiredCell = new PdfPCell();
        PdfUtils.renderLatinTableData(sdaNodeRequired, requiredCell);
        table.addCell(requiredCell);

        PdfPCell resistCell = new PdfPCell();
        PdfUtils.renderChineseTableData(sdaNodeResist, requiredCell);
        table.addCell(resistCell);

        PdfPCell remarkCell = new PdfPCell();
        PdfUtils.renderChineseTableData(sdaNodeRemark, remarkCell);
        table.addCell(remarkCell);

        List<SDA> childSDAs = sda.getChildNode();
        if (null != childSDAs) {
            int childOffSet = offset + 10;
            for (SDA childSDA : childSDAs) {
                renderSDANode(childSDA, table, childOffSet, indexColor);
            }
        }
    }


}
