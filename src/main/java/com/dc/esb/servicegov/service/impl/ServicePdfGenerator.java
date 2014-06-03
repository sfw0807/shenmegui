package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.service.PdfGenerator;
import com.dc.esb.servicegov.util.PdfUtils;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Section;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-29
 * Time: 下午3:37
 */
@org.springframework.stereotype.Component
@Qualifier("servicePdfGenerator")
public class ServicePdfGenerator implements PdfGenerator<List<Service>> {
    private static final Log log = LogFactory.getLog(OperationPdfGenerator.class);
    private static final String TMP_PDF_DIR = "D:/pdftmp";
    @Autowired
    private ServiceManagerImpl serviceManager;
    @Autowired
    private MetadataManagerImpl metadataManager;
    @Autowired
    private OperationPdfGenerator operationPdfGenerator;

    @Override
    public File generate(List<Service> services) throws Exception {
        File pdfFile = null;
        FileOutputStream out = null;
        try {
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
                out = new FileOutputStream(pdfFile);
                PdfWriter.getInstance(document, out)
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
        } finally {
            out.close();
        }
        return pdfFile;
    }

    public void generate(List<Service> services, Document document, Section section) throws Exception {
        for (Service service : services) {
            try {
                render(service, document, section);
            } catch (Exception e) {
                String errorMsg = "为服务[" + service.getServiceId() + "]创建pdf时失败！";
                log.error(errorMsg);
                throw e;
            }
        }
    }

    public void render(Service service, Document document, Section section) throws Exception {
        String serviceId = service.getServiceId();
        String serviceName = service.getServiceName();
        Phrase serviceTitlePhrase = new Phrase();
        PdfUtils.renderInLine("服务", serviceTitlePhrase, PdfUtils.ST_SONG_MIDDLE_BOLD_FONT);
        PdfUtils.renderInLine(":", serviceTitlePhrase, PdfUtils.NORMAL_MIDDLE_BOLD_FONT);
        PdfUtils.renderInLine(serviceId, serviceTitlePhrase, PdfUtils.NORMAL_MIDDLE_BOLD_FONT);
        PdfUtils.renderInLine("(", serviceTitlePhrase, PdfUtils.NORMAL_MIDDLE_BOLD_FONT);
        PdfUtils.renderInLine(serviceName, serviceTitlePhrase, PdfUtils.ST_SONG_MIDDLE_BOLD_FONT);
        PdfUtils.renderInLine(")", serviceTitlePhrase, PdfUtils.NORMAL_MIDDLE_BOLD_FONT);
        Paragraph paragraph = new Paragraph(serviceTitlePhrase);
        //添加目录
        Section subSection = section.addSection(paragraph);
        subSection.setBookmarkTitle(serviceId + "(" + serviceName + ")");
        subSection.setBookmarkOpen(false);
        subSection.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
//        section.add(serviceTitlePhrase);
        paragraph.setSpacingAfter(10);
//        document.add(paragraph);
        String serviceRemark = service.getServiceRemark();
        //添加功能描述
        serviceRemark = serviceRemark == null ? "尚无关于服务的描述。" : serviceRemark;
        serviceRemark = "".equals(serviceRemark) ? "尚无关于服务的描述。" : serviceRemark;
        Phrase descPhrase = new Phrase();
        PdfUtils.renderInLine("功能描述", descPhrase, PdfUtils.ST_SONG_MIDDLE_FONT);
        PdfUtils.renderInLine(":", descPhrase, PdfUtils.NORMAL_MIDDLE_FONT);
        PdfUtils.renderInLine(serviceRemark, descPhrase, PdfUtils.ST_SONG_MIDDLE_FONT);
        Paragraph descParagraph = new Paragraph(descPhrase);
        subSection.add(descParagraph);
//        document.add(descParagraph);
        //添加场景说明
        List<Service> operations = serviceManager.getOpertions(serviceId);
        if (null != operations) {
            Phrase opDescTitlePhrase = new Phrase();
            PdfUtils.renderInLine("该服务存在以下的操作", opDescTitlePhrase, PdfUtils.ST_SONG_MIDDLE_FONT);
            PdfUtils.renderInLine(":", opDescTitlePhrase, PdfUtils.NORMAL_MIDDLE_FONT);
            Paragraph opDescTitleParagraph = new Paragraph(opDescTitlePhrase);
            subSection.add(opDescTitleParagraph);
//            document.add(opDescTitleParagraph);

        }
        operationPdfGenerator.generate(operations, document, subSection);
    }


}
