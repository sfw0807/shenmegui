package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.ServiceCategoryDAOImpl;
import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.entity.ServiceCategory;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.service.PdfGenerator;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-30
 * Time: 上午10:54
 */
@Component
@Qualifier("platformPdfGenerator")
public class PlatformPdfGenerator implements PdfGenerator {

    private static final Log log = LogFactory.getLog(PlatformPdfGenerator.class);
    private static final String TMP_PDF_DIR = "pdftmp";

    @Autowired
    private ServiceCategoryManagerImpl serviceCategoryManager;
    @Autowired
    private ServiceCategoryPdfGenerator serviceCategoryPdfGenerator;

    @Override
    public File generate(Object o) throws Exception {
        File pdfFile = null;
        FileOutputStream out = null;
        try {
            List<ServiceCategory> categories = serviceCategoryManager.getTopCategories();

            if (null != categories) {
                File dir = new File(TMP_PDF_DIR);
                dir.mkdirs();
                String pdfPath = TMP_PDF_DIR + File.separator + "ServiceCategory.pdf";
                log.info("开始生成pdf["+pdfPath+"]");
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
                PdfWriter writer = PdfWriter.getInstance(document, out);
                writer.setInitialLeading(16);
                writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);

                document.open();
                try {
                    serviceCategoryPdfGenerator.generateTopCategory(categories,document);
                } finally {
                    document.close();
                }
            } else {
                String errorMsg = "生成PDF失败，服务为空！";
                log.error(errorMsg);
                throw new DataException(errorMsg);
            }
        } finally {
            if(null != out){
                try{
                    out.close();
                }catch(Exception e){
                    log.error(e);
                }
            }
        }
        return pdfFile;
    }
}
