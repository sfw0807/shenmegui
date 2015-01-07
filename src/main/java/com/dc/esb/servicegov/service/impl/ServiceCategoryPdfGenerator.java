package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.entity.ServiceCategory;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.service.PdfGenerator;
import com.dc.esb.servicegov.util.PdfUtils;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-29
 * Time: 下午6:23
 */
@Component
@Qualifier("serviceCategoryPdfGenerator")
public class ServiceCategoryPdfGenerator implements PdfGenerator<List<ServiceCategory>> {

    private static final Log log = LogFactory.getLog(ServiceCategoryPdfGenerator.class);
    private static final String TMP_PDF_DIR = "D:/pdftmp";
    @Autowired
    private ServiceCategoryManagerImpl serviceCategoryManager;
    @Autowired
    private ServicePdfGenerator servicePdfGenerator;
    @Autowired
    private ServiceManagerImpl serviceManager;

    @Override
    public File generate(List<ServiceCategory> serviceCategories) throws Exception {
        File pdfFile = null;
        FileOutputStream out = null;
        try {
            if (null != serviceCategories) {
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
                    for (ServiceCategory serviceCategory : serviceCategories) {
                        try {
                            render(serviceCategory, document, null);
                        } catch (Exception e) {
                            String errorMsg = "为服务分类[" + serviceCategory.getGroupId() + ":" + serviceCategory.getGroupName() + "]创建pdf时失败！";
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

    public void generate(List<ServiceCategory> serviceCategories, Document document, Section section) throws Exception {
        for (ServiceCategory serviceCategory : serviceCategories) {
            try {
                render(serviceCategory, document, section);
            } catch (Exception e) {
                String errorMsg = "为服务分组[" + serviceCategory.getGroupId() + ":" + serviceCategory.getGroupName() + "]创建pdf时失败！";
                log.error(errorMsg);
                throw e;
            }
        }
    }

    public void generateTopCategory(List<ServiceCategory> serviceCategories, Document document) throws Exception {
        int i = 1;
        for (ServiceCategory serviceCategory : serviceCategories) {
            try {
                Phrase topCategoryPhrase = new Phrase(serviceCategory.getGroupName(), PdfUtils.ST_SONG_BIG_BOLD_FONT);
                Paragraph topCategoryParagraph = new Paragraph(topCategoryPhrase);
                Chapter chapter = new Chapter(topCategoryParagraph, i++);
                List<ServiceCategory> subServiceCategories = serviceCategoryManager.getSubCategories(serviceCategory);
                generateSubCategory(subServiceCategories, document, chapter);
                document.add(chapter);
            } catch (Exception e) {
                String errorMsg = "为服务分组[" + serviceCategory.getGroupId() + ":" + serviceCategory.getGroupName() + "]创建pdf时失败！";
                log.error(errorMsg);
                throw e;
            }
        }
    }

    public void generateSubCategory(List<ServiceCategory> serviceCategories, Document document, Chapter chapter) throws Exception {
        for (ServiceCategory serviceCategory : serviceCategories) {
            try {
                Phrase subCategoryPhrase = new Phrase(serviceCategory.getGroupName(), PdfUtils.ST_SONG_BIG_BOLD_FONT);
                Paragraph subCategoryParagraph = new Paragraph(subCategoryPhrase);
                Section section = chapter.addSection(subCategoryParagraph);
                section.setBookmarkTitle(serviceCategory.getGroupName());
                section.setBookmarkOpen(false);
                section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
                render(serviceCategory, document, section);
            } catch (Exception e) {
                String errorMsg = "为服务分组[" + serviceCategory.getGroupId() + ":" + serviceCategory.getGroupName() + "]创建pdf时失败！";
                log.error(errorMsg);
                throw e;
            }
        }
    }

    private void render(ServiceCategory serviceCategory, Document document, Section section) throws Exception {
        if (null != serviceCategory && null != document) {
            String categoryId = serviceCategory.getGroupId();
//            String categoryName = serviceCategory.getGroupName();
//            Phrase cateTitlePhrase = new Phrase();
//            PdfUtils.renderInLine(categoryName, cateTitlePhrase, PdfUtils.ST_SONG_BIG_BOLD_FONT);
//            Paragraph cateTitleParagraph = new Paragraph(cateTitlePhrase);
//            section.add(cateTitleParagraph);
            //这里取到的是场景，还要再取服务
            List<Service> operations = serviceCategoryManager.getServicesByCategory(categoryId);
            List<Service> superServices = new ArrayList<Service>();
            for (Service operation : operations) {
                List<Service> services = serviceManager.getServiceOfOperation(operation);
                superServices.removeAll(services);
                superServices.addAll(services);
            }
            servicePdfGenerator.generate(superServices, document, section);
        }
    }
}
