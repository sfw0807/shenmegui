package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.MetadataDAOImpl;
import com.dc.esb.servicegov.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.dao.impl.SDADAOImpl;
import com.dc.esb.servicegov.entity.Metadata;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.SDA;
import com.dc.esb.servicegov.util.PdfUtils;
import com.dc.esb.servicegov.vo.SDAVO;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/29.
 */
@Service
@Transactional
public class PdfServiceImpl {
    protected Log log = LogFactory.getLog(getClass());
    @Autowired
    private OperationDAOImpl operationDAO;
    @Autowired
    private MetadataDAOImpl metadataDAO;
    @Autowired
    private SDADAOImpl sdadao;
    int maxWidth = 520;

    private static final String serviceType = "service";
    private static final String serviceCategoryType0 = "root";
    private static final String serviceCategoryType1 = "serviceCategory1";
    private static final String serviceCategoryType2 = "serviceCategory2";

    protected Log logger = LogFactory.getLog(getClass());

    public File genderServicePdf(String id, String type) throws Exception{
        if(serviceType.equals(type)){
            return genderPdfByService(id);
        }
        if(serviceCategoryType0.equals(type)){
            return genderPdfByServiceCategory0(id);
        }
        if(serviceCategoryType1.equals(type)){
            return genderPdfByServiceCategory1(id);
        }
        if(serviceCategoryType2.equals(type)){
            return genderPdfByServiceCategory2(id);
        }
        return null;
    }

    /**
     * 根据服务id生成pdf
     * @param serviceId
     * @return
     */
    public File genderPdfByService(String serviceId) throws Exception{
        List<Operation> operations = operationDAO.findBy("serviceId", serviceId);
        return genderService(operations);
    }

    /**
     * 根据服务类别id生成pdf
     * @param serviceCategoryId
     * @return
     */
    public File genderPdfByServiceCategory0(String serviceCategoryId) throws Exception{
        List<Operation> operations = operationDAO.getAll();
        return genderService(operations);
    }
    public File genderPdfByServiceCategory1(String serviceCategoryId) throws Exception{
        List<Operation> operations = operationDAO.getByCategoryId1(serviceCategoryId);
        return genderService(operations);
    }
    public File genderPdfByServiceCategory2(String serviceCategoryId) throws Exception{
        List<Operation> operations = operationDAO.getByCategoryId2(serviceCategoryId);
        return genderService(operations);
    }

    public File genderService(List<Operation> operations) throws Exception{
        File pdfFile = null;
        String pdfDir = "tmppdf";
        File pdfDirFile = new File(pdfDir);
        if(!pdfDirFile.exists()){
            pdfDirFile.mkdirs();
        }
        if (null != operations) {
            String serviceId = operations.get(0).getService().getServiceId();
            String serviceName = operations.get(0).getService().getServiceName();
            String pdfPath = pdfDir+File.separator +"浦发银行服务手册-"+serviceId+".pdf";
            pdfFile = new File(pdfPath);
            if (pdfFile.exists()) {
                log.error("file path:"+pdfFile.getAbsolutePath());
                boolean deleted = pdfFile.delete();
                if (!deleted) {
                    String errorMsg = "删除已经存在的文件[" + pdfPath + "]";
                    log.error(errorMsg);
                }
            }
            pdfFile.createNewFile();
            //创建pdf的document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile))
                    .setInitialLeading(16);
            document.open();
            Phrase opDescPhrase = new Phrase();
            PdfUtils.renderInLine(serviceId + ":", opDescPhrase, PdfUtils.NORMAL_MIDDLE_FONT);
            PdfUtils.renderInLine(serviceName.trim() , opDescPhrase, PdfUtils.ST_SONG_MIDDLE_FONT);
            document.add(opDescPhrase);
            try {
                int i=0;
                for (Operation operation : operations) {
                    try {
                        Phrase operationPhrase = new Phrase(operation.getOperationName(), PdfUtils.ST_SONG_BIG_BOLD_FONT);
                        Paragraph operationParagraph = new Paragraph(operationPhrase);
                        Chapter chapter = new Chapter(operationParagraph, i++);
                        render(operation, document, chapter);
                    } catch (Exception e) {
                        String errorMsg = "为操作[" + operation.getOperationId() + ":" + operation.getOperationName() + "]创建pdf时失败！";
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
        }
        return pdfFile;
    }
    private void render(Operation operation, Document document,Chapter chapter) throws Exception {
        Section operationSection = renderTitle(operation, document, chapter);
        renderSDA(operation, document, operationSection);
    }
    private Section renderTitle(Operation operation, Document document, Chapter chapter) throws Exception {
        String serviceId = operation.getService().getServiceId();
        String serviceName = operation.getService().getServiceName();
        Section operationSection = null;
        try {
            Phrase opDescPhrase = new Phrase();
            Paragraph opDescParagraph = new Paragraph(opDescPhrase);
            operationSection = chapter.addSection(opDescParagraph);
            operationSection.setBookmarkTitle(serviceId + "(" + serviceName + ")");
            operationSection.setBookmarkOpen(false);
            operationSection.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
        } catch (Exception e) {
            String errorMsg = "渲染服务名称[" + serviceId + serviceName + "]失败！";
            log.error(errorMsg, e);
            throw e;
        }
        return operationSection;

    }

    private void renderSDA(Operation operationDO, Document document, Section section) throws Exception {
        try {
            SDAVO sda = getSDAofService(operationDO);
            if(sda!=null){
                PdfPTable table = new PdfPTable(6);
                PdfPCell thCell = new PdfPCell();

                Phrase operationPhrase = new Phrase();
                operationPhrase.add(new Phrase("操作", PdfUtils.ST_SONG_SMALL_BOLD_FONT));
                operationPhrase.add(new Phrase(":" + operationDO.getOperationId(), PdfUtils.TABLE_BOLD_FONT));
                PdfUtils.renderTableHeader(operationPhrase, thCell);
//                PdfUtils.renderChineseTableHeader("服务", thCell);

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
                List<SDAVO> childrenOfRoot = sda.getChildNode();
                SDAVO reqSDA = null;
                SDAVO rspSDA = null;
                if (null != childrenOfRoot) {
                    for (SDAVO node : childrenOfRoot) {
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
                    List<SDAVO> childrenOfReq = reqSDA.getChildNode();
                    if (null != childrenOfReq) {
                        for (SDAVO childOfReq : childrenOfReq) {
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
                    List<SDAVO> childrenOfRsp = rspSDA.getChildNode();
                    if (null != childrenOfRsp) {
                        for (SDAVO childSDA : childrenOfRsp) {
                            renderSDANode(childSDA, table, 0, Color.CYAN);
                        }
                    }
                }
                table.setSpacingBefore(5);
                section.add(table);
                document.add(table);
            }else{
                PdfPTable table = new PdfPTable(6);
                PdfPCell thCell = new PdfPCell();

                Phrase operationPhrase = new Phrase();
                operationPhrase.add(new Phrase("操作", PdfUtils.ST_SONG_SMALL_BOLD_FONT));
                operationPhrase.add(new Phrase(":" + operationDO.getOperationId(), PdfUtils.TABLE_BOLD_FONT));
                PdfUtils.renderTableHeader(operationPhrase, thCell);
//                PdfUtils.renderChineseTableHeader("服务", thCell);

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
                PdfPCell headerDirectcell = new PdfPCell();
                PdfUtils.renderChineseTableHeader("无SDA", headerDirectcell);
                headerDirectcell.setBackgroundColor(Color.PINK);
                headerDirectcell.setColspan(6);
                table.addCell(headerDirectcell);
                table.setSpacingBefore(5);
                section.add(table);
                document.add(table);
            }
        } catch (Exception e) {
            log.error("渲染服务[" + operationDO.getOperationId() + "]的SDA失败！");
            throw e;
        }
    }
    private void renderSDANode(SDAVO sda, PdfPTable table, int offset, Color indexColor) throws Exception {
        String sdaNodeId = sda.getValue().getStructName();
        String sdaNodeType = "";
        String sdaNodeChineseName = sda.getValue().getStructAlias();
        String sdaNodeRequired = sda.getValue().getRequired();
        String sdaNodeResist = "";
        String sdaNodeRemark = sda.getValue().getRemark();

        String metadataId = sda.getValue().getMetadataId();
        if (null != metadataId && !"".equals(metadataId)) {
            Metadata metadata = metadataDAO.findUniqueBy("metadataId", metadataId);
            String type = "";
            String length = "";
            String scale = "";
            if(null!=metadata){
                type = metadata.getType();
                length = metadata.getLength();
                scale = metadata.getScale();
            }else{
                sdaNodeType = "该元数据不存在";
            }
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

        List<SDAVO> childSDAs = sda.getChildNode();
        if (null != childSDAs) {
            int childOffSet = offset + 10;
            for (SDAVO childSDA : childSDAs) {
                renderSDANode(childSDA, table, childOffSet, indexColor);
            }
        }
    }

    public SDAVO getSDAofService(Operation operation){
        SDA sda = sdadao.findRootByOperation(operation);
        SDAVO sdavo = genderSDAVO(sda);
        return sdavo;
    }
    public SDAVO genderSDAVO(SDA sda){
        SDAVO sdavo = new SDAVO();
        sdavo.setValue(sda);
        List<SDAVO> children = new ArrayList<SDAVO>();
        List<SDA> sdaList = sdadao.findBy("parentId", sda.getSdaId());
        for(int i = 0; i< sdaList.size(); i++){
            SDAVO child = genderSDAVO(sdaList.get(i));
            children.add(child);
        }
        sdavo.setChildNode(children);
        return sdavo;
    }
}
