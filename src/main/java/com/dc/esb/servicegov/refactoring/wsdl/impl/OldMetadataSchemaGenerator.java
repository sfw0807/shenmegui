package com.dc.esb.servicegov.refactoring.wsdl.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dc.esb.servicegov.refactoring.dao.impl.MetadataDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.ServiceHeadRelateDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.HeadSDA;
import com.dc.esb.servicegov.refactoring.entity.Metadata;
import com.dc.esb.servicegov.refactoring.entity.ServiceHeadRelate;
import com.dc.esb.servicegov.refactoring.exception.DataException;
import com.dc.esb.servicegov.refactoring.service.impl.MetadataManagerImpl;
import com.dc.esb.servicegov.refactoring.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.refactoring.vo.HeadSDAVO;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-24
 * Time: 上午10:04
 * compare to new xsd:
 * add shead sda to xml 
 * add all metadata to xml
 */
@Component
public class OldMetadataSchemaGenerator {
    private static final Log log = LogFactory.getLog(OldMetadataSchemaGenerator.class);
    private static final Namespace NS_URI_XMLNS = new Namespace("x", "http://www.w3.org/2001/XMLSchema");
    private static final QName QN_ELEM = new QName("element", NS_URI_XMLNS);
    private static final QName QN_COMPLEX_TYPE = new QName("complexType", NS_URI_XMLNS);
    private static final QName QN_SEQ = new QName("sequence", NS_URI_XMLNS);
    private static Namespace NS_XML = new Namespace("x", "http://www.w3.org/2001/XMLSchema");
	@Autowired
	private MetadataDAOImpl metadataDAO;
    @Autowired
    private ServiceHeadRelateDAOImpl serviceHeadRelateDAO;
    @Autowired
    private ServiceManagerImpl serviceManager;
    private Map<String, String> uniqueMap = new HashMap<String, String>();
    @Autowired
    private MetadataManagerImpl metadataManager;
    public File generate(String dirPath, String serviceId) throws DataException {
        Document document = DocumentHelper.createDocument();
        Element schemaRoot = document.addElement(new QName("schema", NS_XML));
        schemaRoot.addNamespace("tns", "http://esb.spdbbiz.com/metadata");
        schemaRoot.addNamespace("d", "http://esb.spdbbiz.com/metadata");
        schemaRoot.addAttribute("targetNamespace", "http://esb.spdbbiz.com/metadata");
        schemaRoot.addAttribute("elementFormDefault", "qualified");
        schemaRoot.addAttribute("attributeFormDefault", "qualified");
        ServiceHeadRelate serviceHeadRelat = serviceHeadRelateDAO.findUniqueBy("serviceId", serviceId);
        String headId = serviceHeadRelat.getSheadId();
		HeadSDAVO sHeadSDA = serviceManager.getHeadSDAofService(headId);
		List<HeadSDAVO> childSDAs = sHeadSDA.getChildNode();
		if (null == childSDAs) {
			throw new DataException("sda 异常");
		}
		HeadSDAVO reqSDA = null;
		HeadSDAVO rspSDA = null;
		for (HeadSDAVO childSDA : childSDAs) {
			if ("request".equalsIgnoreCase(childSDA.getValue()
					.getStructName())) {
				reqSDA = childSDA;
			}
			if ("response".equalsIgnoreCase(childSDA.getValue()
					.getStructName())) {
				rspSDA = childSDA;
			}
		}
		// req svc header
		if (null == reqSDA && null == rspSDA) {
			throw new DataException("sda 异常， 不存在request或者response节点");
		}
		List<HeadSDAVO> reqChildSDAs = reqSDA.getChildNode();
		HeadSDAVO reqSvcHeaderSDA = null;
		for (HeadSDAVO reqChildSDA : reqChildSDAs) {
			if ("ReqSvcHeader".equalsIgnoreCase(reqChildSDA.getValue()
					.getStructName())) {
				reqSvcHeaderSDA = reqChildSDA;
				break;
			}
		}
		renderType(reqSvcHeaderSDA, null, schemaRoot);

		// rsp svc header
		List<HeadSDAVO> rspChildSDAs = rspSDA.getChildNode();
		HeadSDAVO rspSvcHeaderSDA = null;
		for (HeadSDAVO rspChildSDA : rspChildSDAs) {
			if ("RspSvcHeader".equalsIgnoreCase(rspChildSDA.getValue()
					.getStructName())) {
				rspSvcHeaderSDA = rspChildSDA;
				break;
			}
		}
		renderType(rspSvcHeaderSDA, null, schemaRoot);
		
        Element reqComplexType = schemaRoot.addElement(new QName("complexType", NS_XML));
        reqComplexType.addAttribute("name", "ReqHeaderType");
        Element reqSequence = reqComplexType.addElement(new QName("sequence", NS_XML));
        Element reqMacElement = reqSequence.addElement(new QName("element", NS_XML));
        reqMacElement.addAttribute("name", "Mac");
        reqMacElement.addAttribute("type", "x:string");
        Element reqMacOrgIdElement = reqSequence.addElement(new QName("element", NS_XML));
        reqMacOrgIdElement.addAttribute("name", "MacOrgId");
        reqMacOrgIdElement.addAttribute("type", "x:string");
        Element reqMsgIdElement = reqSequence.addElement(new QName("element", NS_XML));
        reqMsgIdElement.addAttribute("name", "MsgId");
        reqMsgIdElement.addAttribute("type", "x:string");
        Element reqSourceSysIdElement = reqSequence.addElement(new QName("element", NS_XML));
        reqSourceSysIdElement.addAttribute("name", "SourceSysId");
        reqSourceSysIdElement.addAttribute("type", "x:string");
        Element reqConsumerIdElement = reqSequence.addElement(new QName("element", NS_XML));
        reqConsumerIdElement.addAttribute("name", "ConsumerId");
        reqConsumerIdElement.addAttribute("type", "x:string");
        Element reqServiceAdrElement = reqSequence.addElement(new QName("element", NS_XML));
        reqServiceAdrElement.addAttribute("name", "ServiceAdr");
        reqServiceAdrElement.addAttribute("type", "x:string");
        Element reqServiceActionElement = reqSequence.addElement(new QName("element", NS_XML));
        reqServiceActionElement.addAttribute("name", "ServiceAction");
        reqServiceActionElement.addAttribute("type", "x:string");
        Element reqReplyAdrElement = reqSequence.addElement(new QName("element", NS_XML));
        reqReplyAdrElement.addAttribute("name", "ReplyAdr");
        reqReplyAdrElement.addAttribute("type", "x:string");
        reqReplyAdrElement.addAttribute("minOccurs", "0");
        Element reqExtendContentElement = reqSequence.addElement(new QName("element", NS_XML));
        reqExtendContentElement.addAttribute("name", "ExtendContent");
        reqExtendContentElement.addAttribute("type", "x:string");
        reqExtendContentElement.addAttribute("minOccurs", "0");

        Element rspComplexType = schemaRoot.addElement(new QName("complexType", NS_XML));
        rspComplexType.addAttribute("name", "RspHeaderType");
        Element rspSequence = rspComplexType.addElement(new QName("sequence", NS_XML));
        Element macElement = rspSequence.addElement(new QName("element", NS_XML));
        macElement.addAttribute("name", "Mac");
        macElement.addAttribute("type", "x:string");
        Element macOrgIdElement = rspSequence.addElement(new QName("element", NS_XML));
        macOrgIdElement.addAttribute("name", "MacOrgId");
        macOrgIdElement.addAttribute("type", "x:string");
        Element msgIdElement = rspSequence.addElement(new QName("element", NS_XML));
        msgIdElement.addAttribute("name", "MsgId");
        msgIdElement.addAttribute("type", "x:string");
        Element targetSysIdElement = rspSequence.addElement(new QName("element", NS_XML));
        targetSysIdElement.addAttribute("name", "TargetSysId");
        targetSysIdElement.addAttribute("type", "x:string");
        Element relatedMsgIdElement = rspSequence.addElement(new QName("element", NS_XML));
        relatedMsgIdElement.addAttribute("name", "RelatedMsgId");
        relatedMsgIdElement.addAttribute("type", "x:string");
        relatedMsgIdElement.addAttribute("minOccurs", "0");
        Element serviceAdrElement = rspSequence.addElement(new QName("element", NS_XML));
        serviceAdrElement.addAttribute("name", "ServiceAdr");
        serviceAdrElement.addAttribute("type", "x:string");
        serviceAdrElement.addAttribute("minOccurs", "0");
        Element serviceActionElement = rspSequence.addElement(new QName("element", NS_XML));
        serviceActionElement.addAttribute("name", "ServiceAction");
        serviceActionElement.addAttribute("type", "x:string");
        serviceActionElement.addAttribute("minOccurs", "0");
        Element extendContentElement = rspSequence.addElement(new QName("element", NS_XML));
        extendContentElement.addAttribute("name", "ExtendContent");
        extendContentElement.addAttribute("type", "x:string");
        extendContentElement.addAttribute("minOccurs", "0");

        List<Metadata> metadataList = metadataDAO.getAll();
        for (Metadata metadata : metadataList) {
             if ("number".equalsIgnoreCase(metadata.getType())) {
            	 if(!uniqueMap.containsKey(metadata.getMetadataId())){
                	 Element metadataElement = schemaRoot.addElement(new QName("element", NS_XML));
                	 metadataElement.addAttribute("name", metadata.getMetadataId());
                	 metadataElement.addAttribute("type", "x:decimal");
            	 }
             } else if ("string".equalsIgnoreCase(metadata.getType())) {
            	 if(!uniqueMap.containsKey(metadata.getMetadataId())){
                	 Element metadataElement = schemaRoot.addElement(new QName("element", NS_XML));
                	 metadataElement.addAttribute("name", metadata.getMetadataId());
                	 metadataElement.addAttribute("type", "x:string"); 
            	 }
             } else if ("int".equalsIgnoreCase(metadata.getType())){
            	 if(!uniqueMap.containsKey(metadata.getMetadataId())){
                	 Element metadataElement = schemaRoot.addElement(new QName("element", NS_XML));
                	 metadataElement.addAttribute("name", metadata.getMetadataId());
                	 metadataElement.addAttribute("type", "x:int"); 
            	 }
             }
		}
        BufferedOutputStream schemaOut = null;
        try {
            // 美化格式
            File schemaFile = new File(dirPath + File.separator  + "metadata.xsd");
            if (!schemaFile.exists()) {

                schemaFile.createNewFile();
            }
            schemaOut = new BufferedOutputStream(new FileOutputStream(schemaFile));

            XMLWriter writer = null;
            OutputFormat format = OutputFormat.createPrettyPrint();
            writer = new XMLWriter(schemaOut, format);
            writer.write(document);
        } catch (IOException e) {
            log.error(e,e);
        } finally{
            if(null != schemaOut){
                try {
                    schemaOut.close();
                } catch (IOException e) {
                    log.error(e,e);
                }
            }
            uniqueMap.clear();
        }

        return null;
    }
    private void renderType(HeadSDAVO sdaVO, Element parentElem, Element typeParentElem) throws DataException {
    	HeadSDA sdaNode = sdaVO.getValue();
        if (null != sdaNode) {
            Element nodeElement = null;
            if (null != parentElem) {
                nodeElement = parentElem.addElement(QN_ELEM);
            }
            String nodeType = sdaNode.getType().trim();
            String required = sdaNode.getRequired();
            boolean isComplexNode = null != sdaVO.getChildNode() && sdaVO.getChildNode().size() > 0;
            if ("array".equalsIgnoreCase(nodeType) || isComplexNode) {
                if (null != parentElem) {
                    nodeElement.addAttribute("name", sdaNode.getStructName());
                    if ("array".equalsIgnoreCase(nodeType)) {
                        nodeElement.addAttribute("maxOccurs", "unbounded");
                    }
                    nodeElement.addAttribute("type", "d:" + sdaNode.getStructName() + "Type");
                }
                if (!uniqueMap.containsKey(sdaNode.getStructName())) {
                    Element arrayTypeElement = typeParentElem.addElement(QN_COMPLEX_TYPE);
                    arrayTypeElement.addAttribute("name", sdaNode.getStructName() + "Type");
                    Element arrayTypeSeq = arrayTypeElement.addElement(QN_SEQ);
                    List<HeadSDAVO> childSDAs = sdaVO.getChildNode();
                    if (null != childSDAs) {
                        for (HeadSDAVO childSDA : childSDAs) {
                            renderType(childSDA, arrayTypeSeq, typeParentElem);
                        }
                    }
                    uniqueMap.put(sdaNode.getStructName(), null);
                } else {
                    log.error(sdaNode.getStructName() + "重复");
                }
            } else {
                String metadataId = sdaNode.getMatadataId();
                if (null != metadataId) {
                    metadataId = metadataId.trim();
                    Metadata metadata = metadataManager.getMetadataById(metadataId);
                    String metadataType = metadata.getType();
                    if (null != parentElem) {
                        nodeElement.addAttribute("ref", "d:" + metadataId);
                    }
                    if (!uniqueMap.containsKey(sdaNode.getStructName())) {
                        Element nodeTypeElement = typeParentElem.addElement(QN_ELEM);
                        nodeTypeElement.addAttribute("name", metadataId);
                        if ("number".equalsIgnoreCase(metadataType)) {
                            nodeTypeElement.addAttribute("type", "x:decimal");
                        } else if ("string".equalsIgnoreCase(metadataType)) {
                            nodeTypeElement.addAttribute("type", "x:string");
                        } else {
                            nodeTypeElement.addAttribute("type", "x:int");
                        }
                        uniqueMap.put(sdaNode.getStructName(), null);
                    } else {
                        log.error(sdaNode.getStructName() + "重复");
                    }
                } else {
                    throw new DataException("sda非法，服务字段几点必须有元数据！");
                }
            }
            if ("Y".equalsIgnoreCase(required)) {
                if (null != parentElem) {
                    nodeElement.addAttribute("minOccurs", "1");
                }
            } else {
                if (null != parentElem) {
                    nodeElement.addAttribute("minOccurs", "0");
                }
            }
        }
    }
}
