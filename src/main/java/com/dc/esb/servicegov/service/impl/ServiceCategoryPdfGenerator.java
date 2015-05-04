package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.entity.ServiceCategory;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.util.PdfUtils;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-29
 * Time: 下午6:23
 */
@Component
public class ServiceCategoryPdfGenerator  {

    private static final Log log = LogFactory.getLog(ServiceCategoryPdfGenerator.class);
    @Autowired
    private ServiceCategoryManagerImpl serviceCategoryManager;
    @Autowired
    private ServicePdfGenerator servicePdfGenerator;
    
	public File generate(List<ServiceCategory> serviceCategorys) throws Exception {
		File pdfFile = null;
		FileOutputStream out = null;
		String pdfDir = "tmppdf";
		File pdfDirFile = new File(pdfDir);
		if(!pdfDirFile.exists()){
			pdfDirFile.mkdirs();
		}
		try{
			if(null != serviceCategorys && serviceCategorys.size() > 0){
				String parentCategoryId = serviceCategorys.get(0).getParentId();
				String categoryId = serviceCategorys.get(0).getCategoryId();
				String categoryName = serviceCategorys.get(0).getCategoryName();
				String filePath = "tmppdf"+File.separator +"浦发银行服务手册-"+parentCategoryId+".pdf";
				pdfFile = new File(filePath);
                if (pdfFile.exists()) {
                    boolean deleted = pdfFile.delete();
                    if (!deleted) {
                        String errorMsg = "删除已经存在的文件[" + filePath + "]";
                        log.error(errorMsg);
                        throw new Exception(errorMsg);
                    }
                }
                pdfFile.createNewFile();
                Document document = new Document();
                out = new FileOutputStream(pdfFile);
                PdfWriter.getInstance(document, out).setInitialLeading(16);
                document.open();
    			Phrase categoryPhrase = new Phrase(categoryId,PdfUtils.ST_SONG_BIG_BOLD_FONT);
    			Paragraph categoryParagraph = new Paragraph(categoryPhrase);
                try{
                	int i=0;
                	for(ServiceCategory serviceCategory : serviceCategorys){
                		try{
                			Chapter chapter = new Chapter(categoryParagraph, i++);
                			Section serviceSection = chapter.addSection(categoryParagraph);
                        	serviceSection.setBookmarkTitle(categoryId + "(" + categoryName + ")");
                        	serviceSection.setBookmarkOpen(false);
                        	serviceSection.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
                        	render(serviceCategory, document, serviceSection);
                		}catch(Exception e){
                			log.error(e, e);
                			throw e;
                		}
                	}
                }catch(Exception e){
                	log.error(e, e);
                }finally{
                	if(null!=document){
                		document.close();
                	}
                }
			}else {
                String errorMsg = "生成PDF失败，服务分类列表为空！";
                log.error(errorMsg);
                throw new DataException(errorMsg);
            }
		}catch(Exception e){
			log.error(e, e);
		}finally{
			if(out!=null){
				out.close();
			}
		}
		return pdfFile;
	}
    public void generate(List<ServiceCategory> serviceCategorys, Document document, Section section) throws Exception {
        for (ServiceCategory serviceCategory : serviceCategorys) {
            try {
                render(serviceCategory, document, section);
            } catch (Exception e) {
                String errorMsg = "为服务分类[" + serviceCategory.getCategoryId() + "]创建pdf时失败！";
                log.error(errorMsg);
                throw e;
            }
        }
    }
	private void render(ServiceCategory serviceCategory, Document document,Section serviceSection) throws Exception {
		String categoryId = serviceCategory.getCategoryId();
		String categoryName = serviceCategory.getCategoryName();
		Phrase categoryTitlePhrase = new Phrase();
		PdfUtils.renderInLine("分类:",categoryTitlePhrase,PdfUtils.ST_SONG_MIDDLE_BOLD_FONT);
		PdfUtils.renderInLine(categoryId,categoryTitlePhrase,PdfUtils.ST_SONG_MIDDLE_BOLD_FONT);
		PdfUtils.renderInLine("("+categoryName+")",categoryTitlePhrase,PdfUtils.ST_SONG_MIDDLE_BOLD_FONT);
		Paragraph paragraph = new Paragraph(categoryTitlePhrase);
		//添加目录
		Section subSection = serviceSection.addSection(paragraph);
        subSection.setBookmarkTitle(categoryId + "(" + categoryName + ")");
        subSection.setBookmarkOpen(false);
        subSection.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
        serviceSection.add(categoryTitlePhrase);
        paragraph.setSpacingAfter(10);
        document.add(paragraph);
        
        List<Service> services = serviceCategoryManager.getServiceByCategoryId(categoryId);
        servicePdfGenerator.generate(services, document, subSection);
        
	}
}
