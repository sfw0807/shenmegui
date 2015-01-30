package com.dc.esb.servicegov.refactoring.wsdl.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
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

import com.dc.esb.servicegov.refactoring.dao.impl.MetadataStructsAttrDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.ServiceHeadRelateDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.HeadSDA;
import com.dc.esb.servicegov.refactoring.entity.Metadata;
import com.dc.esb.servicegov.refactoring.entity.MetadataStructsAttr;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.SDA;
import com.dc.esb.servicegov.refactoring.entity.Service;
import com.dc.esb.servicegov.refactoring.exception.DataException;
import com.dc.esb.servicegov.refactoring.service.impl.MetadataManagerImpl;
import com.dc.esb.servicegov.refactoring.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.refactoring.util.OperationComparator;
import com.dc.esb.servicegov.refactoring.vo.HeadSDAVO;
import com.dc.esb.servicegov.refactoring.vo.SDAVO;
import com.dc.esb.servicegov.wsdl.WSDLGenerator;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-20
 * Time: 上午2:07
 */
@Component
public class SpdbServiceSchemaGenerator implements WSDLGenerator<List<Service>> {

    private static final Log log = LogFactory.getLog(SpdbServiceSchemaGenerator.class);
    private static final Namespace NS_URI_XMLNS = new Namespace("x", "http://www.w3.org/2001/XMLSchema");
    private static final QName QN_ELEM = new QName("element", NS_URI_XMLNS);
    private static final QName QN_COMPLEX_TYPE = new QName("complexType", NS_URI_XMLNS);
    private static final QName QN_SEQ = new QName("sequence", NS_URI_XMLNS);
    @Autowired
    private ServiceManagerImpl serviceManager;
    @Autowired
    private MetadataManagerImpl metadataManager;
    @Autowired
    private MetadataStructsAttrDAOImpl matadataStructAttrDAO;
    @Autowired
    private ServiceHeadRelateDAOImpl serviceHeadRelateDAO;
    private Map<String, String> uniqueMap = new HashMap<String, String>();

    @Override
    public boolean generate(List<Service> services) throws Exception {
        return false;
    }

    public boolean generate(Service serviceDO, String dirPath) {

        String serviceId = serviceDO.getServiceId();
        BufferedOutputStream schemaOut = null;
        try {
            List<Operation> operations = serviceManager.getOperationsByServiceId(serviceId);
            // 无SDA数据的操作，从操作列表中删除
            List<Operation> delList = new ArrayList<Operation>();
            for(int i=0;i<operations.size();i++){
            	Operation operationDO = operations.get(i);
            	if(serviceManager.checkSdaExists(operationDO.getOperationId(),serviceId) == false){
            		delList.add(operationDO);
            	}
            }
            operations.removeAll(delList);
            OperationComparator operationComparator = new OperationComparator();
            Collections.sort(operations, operationComparator);
            String headServiceId = serviceHeadRelateDAO.findUniqueBy("serviceId", serviceDO.getServiceId()).getSheadId();
            Document document = DocumentHelper.createDocument();
            Element schemaRoot = document.addElement(new QName("schema", new Namespace("x", "http://www.w3.org/2001/XMLSchema")));
            schemaRoot.addNamespace("d", "http://esb.spdbbiz.com/metadata");
            schemaRoot.addNamespace("s", "http://esb.spdbbiz.com/services/" + serviceId);
            schemaRoot.addAttribute("targetNamespace", "http://esb.spdbbiz.com/services/" + serviceId);
            schemaRoot.addAttribute("elementFormDefault", "qualified");
            schemaRoot.addAttribute("attributeFormDefault", "qualified");
            Element metadataImportElem = schemaRoot.addElement(new QName("import", NS_URI_XMLNS));
            metadataImportElem.addAttribute("namespace", "http://esb.spdbbiz.com/metadata");
            metadataImportElem.addAttribute("schemaLocation", "SoapHeader.xsd");

            Element reqHeaderElem = schemaRoot.addElement(QN_ELEM);
            reqHeaderElem.addAttribute("name", "ReqHeader");
            reqHeaderElem.addAttribute("type", "d:ReqHeaderType");
            Element rspHeaderElem = schemaRoot.addElement(QN_ELEM);
            rspHeaderElem.addAttribute("name", "RspHeader");
            rspHeaderElem.addAttribute("type", "d:RspHeaderType");

           
			if (null != headServiceId && !"".equals(headServiceId)) {
				//To do
				HeadSDAVO sHeadSDA = serviceManager.getHeadSDAofService(headServiceId);
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

			}

            


            for (Operation operationDO : operations) {
                log.error("开始处理场景["+operationDO.getOperationId()+"]");
                String operationId = operationDO.getOperationId();
                //Todo
                String tmpOperationId = handleDupOperationIdIssue(operationId);
                SDAVO sda = serviceManager.getSDAofService(operationDO);
                List<SDAVO> childSDAs = sda.getChildNode();
                if (null == childSDAs) {
                    throw new DataException("sda 异常");
                }
                SDAVO reqSDA = null;
                SDAVO rspSDA = null;
                for (SDAVO childSDA : childSDAs) {
                    if ("request".equalsIgnoreCase(childSDA.getValue().getStructId())) {
                        reqSDA = childSDA;
                    }
                    if ("response".equalsIgnoreCase(childSDA.getValue().getStructId())) {
                        rspSDA = childSDA;
                    }
                }
                if (null == reqSDA && null == rspSDA) {
                    throw new DataException("sda 异常， 不存在request或者response节点");
                }

                //req
                Element reqOperation = schemaRoot.addElement(QN_ELEM);
                reqOperation.addAttribute("name", "Req" + tmpOperationId);
                String reqOperationTypeName = "s:Req" + tmpOperationId + "Type";
                reqOperation.addAttribute("type", reqOperationTypeName);
                Element reqOperationType = schemaRoot.addElement(QN_COMPLEX_TYPE);
                reqOperationType.addAttribute("name", "Req" + tmpOperationId + "Type");
                Element reqOperationTypeSeq = reqOperationType.addElement(QN_SEQ);
                Element reqSvcHeaderElem = reqOperationTypeSeq.addElement(QN_ELEM);
                reqSvcHeaderElem.addAttribute("name", "ReqSvcHeader");
                reqSvcHeaderElem.addAttribute("type", "s:ReqSvcHeaderType");
                Element reqSvcBodyElem = reqOperationTypeSeq.addElement(QN_ELEM);
                reqSvcBodyElem.addAttribute("name", "SvcBody");
                reqSvcBodyElem.addAttribute("minOccurs", "0");
                Element reqSvcBodyComplexType = reqSvcBodyElem.addElement(QN_COMPLEX_TYPE);
                Element reqSvcBodyTypeSeq = reqSvcBodyComplexType.addElement(QN_SEQ);
                List<SDAVO> reqChildSDAs = reqSDA.getChildNode();
                SDAVO svcBodySDA = null;
                for (SDAVO reqChildSDA : reqChildSDAs) {
                    if ("svcbody".equalsIgnoreCase(reqChildSDA.getValue().getStructId())) {
                        svcBodySDA = reqChildSDA;
                        break;
                    }
                }
                if (null != svcBodySDA) {
                    List<SDAVO> svcBodySubSDAs = svcBodySDA.getChildNode();
                    if (null != svcBodySubSDAs) {
                        for (SDAVO svcBodySubSDA : svcBodySubSDAs) {
                            renderSDA(svcBodySubSDA, reqSvcBodyTypeSeq, schemaRoot);
                        }
                    } else {
                        log.warn(" req svc body 没有内容！");
                    }

                }
                //rsp

                Element rspOperation = schemaRoot.addElement(QN_ELEM);
                rspOperation.addAttribute("name", "Rsp" + tmpOperationId);
                String rspOperationTypeName = "s:Rsp" + tmpOperationId + "Type";
                rspOperation.addAttribute("type", rspOperationTypeName);
                Element rspOperationType = schemaRoot.addElement(QN_COMPLEX_TYPE);
                rspOperationType.addAttribute("name", "Rsp" + tmpOperationId + "Type");
                Element rspOperationTypeSeq = rspOperationType.addElement(QN_SEQ);
                Element rspSvcHeaderElem = rspOperationTypeSeq.addElement(QN_ELEM);
                rspSvcHeaderElem.addAttribute("name", "RspSvcHeader");
                rspSvcHeaderElem.addAttribute("type", "s:RspSvcHeaderType");
                Element rspSvcBodyElem = rspOperationTypeSeq.addElement(QN_ELEM);
                rspSvcBodyElem.addAttribute("minOccurs", "0");
                rspSvcBodyElem.addAttribute("name", "SvcBody");
                Element rspSvcBodyComplexType = rspSvcBodyElem.addElement(QN_COMPLEX_TYPE);
                Element rspSvcBodyTypeSeq = rspSvcBodyComplexType.addElement(QN_SEQ);
                List<SDAVO> rspChildSDAs = rspSDA.getChildNode();
                SDAVO rspSvcBodySDA = null;
                for (SDAVO rspChildSDA : rspChildSDAs) {
                    if ("svcbody".equalsIgnoreCase(rspChildSDA.getValue().getStructId())) {
                        rspSvcBodySDA = rspChildSDA;
                        break;
                    }
                }
                if (null != rspSvcBodySDA) {
                    List<SDAVO> svcBodySubSDAs = rspSvcBodySDA.getChildNode();
                    if (null != svcBodySubSDAs) {
                        for (SDAVO svcBodySubSDA : svcBodySubSDAs) {
                            renderSDA(svcBodySubSDA, rspSvcBodyTypeSeq, schemaRoot);
                        }
                    } else {
                        log.warn(" rsp svc body 没有内容！");
                    }
                }
            }
            // 美化格式
            File schemaFile = new File(dirPath + File.separator + serviceId + ".xsd");
            if (!schemaFile.exists()) {
                schemaFile.createNewFile();
            }
//            File wsdlFile = new File(serviceId + ".wsdl");
            schemaOut = new BufferedOutputStream(new FileOutputStream(schemaFile));
//            BufferedOutputStream wsdlOut = new BufferedOutputStream(new FileOutputStream(wsdlFile));

            XMLWriter writer = null;
            OutputFormat format = OutputFormat.createPrettyPrint();
            writer = new XMLWriter(schemaOut, format);
            writer.write(document);

            return true;
        } catch (DataException e) {
            log.error(e, e);
        } catch (UnsupportedEncodingException e) {
            log.error(e, e);
        } catch (IOException e) {
            log.error(e, e);
        } finally {
            uniqueMap.clear();
            if (null != schemaOut) {

                try {
                    schemaOut.close();
                } catch (IOException e) {
                    log.error(e,e);
                }
            }
        }
        return true;
    }

    public void renderSDA(SDAVO sda, Element parentElem, Element typeParentElem) throws DataException {
        //数组
        SDA sdaNode = sda.getValue();
        if (null != sdaNode) {
            Element nodeElement = parentElem.addElement(QN_ELEM);
            String nodeType = sdaNode.getType().trim();
            String required = sdaNode.getRequired();
            boolean isComplexNode = null != sda.getChildNode() && sda.getChildNode().size() > 0;
            if ("array".equalsIgnoreCase(nodeType) || isComplexNode) {
                nodeElement.addAttribute("name", sdaNode.getStructId());
                if ("array".equalsIgnoreCase(nodeType)) {
                    nodeElement.addAttribute("maxOccurs", "unbounded");
                }
                nodeElement.addAttribute("type", "s:" + sdaNode.getStructId() + "Type");

                if (!uniqueMap.containsKey(sdaNode.getStructId())) {
                    Element arrayTypeElement = typeParentElem.addElement(QN_COMPLEX_TYPE);
                    arrayTypeElement.addAttribute("name", sdaNode.getStructId() + "Type");
                    Element arrayTypeSeq = arrayTypeElement.addElement(QN_SEQ);
                    List<SDAVO> childSDAs = sda.getChildNode();
                    if (null != childSDAs) {
                        for (SDAVO childSDA : childSDAs) {
                            renderSDA(childSDA, arrayTypeSeq, typeParentElem);
                        }
                    }
                    uniqueMap.put(sdaNode.getStructId(), null);
                } else {
                    log.error(sdaNode.getStructId() + "重复");
                }
            } else if ("struct".equalsIgnoreCase(nodeType)) {
                nodeElement.addAttribute("name", sdaNode.getStructId());
                Element sequence = nodeElement.addElement(QN_COMPLEX_TYPE).addElement(QN_SEQ);
                parseStruct(sdaNode.getMetadataId(), sequence, typeParentElem);
            } else {
                String metadataId = sdaNode.getMetadataId();
                if (null != metadataId) {
                    metadataId = metadataId.trim();
                    Metadata metadata = metadataManager.getMetadataById(metadataId);
                    String metadataType = metadata.getType();
                    nodeElement.addAttribute("ref", "s:" + metadataId);
                    if (!uniqueMap.containsKey(sdaNode.getStructId())) {
                        Element nodeTypeElement = typeParentElem.addElement(QN_ELEM);
                        nodeTypeElement.addAttribute("name", metadataId);
                        if ("number".equalsIgnoreCase(metadataType)) {
                            nodeTypeElement.addAttribute("type", "x:decimal");
                        } else if ("string".equalsIgnoreCase(metadataType)) {
                            nodeTypeElement.addAttribute("type", "x:string");
                        } else {
                            nodeTypeElement.addAttribute("type", "x:int");
                        }
                        uniqueMap.put(sdaNode.getStructId(), null);
                    } else {
                        log.error(sdaNode.getStructId() + "重复");
                    }

                } else {
                    throw new DataException("sda非法，服务字段几点必须有元数据！");
                }
            }

            if ("Y".equalsIgnoreCase(required)) {
                nodeElement.addAttribute("minOccurs", "1");
            } else {
                nodeElement.addAttribute("minOccurs", "0");
            }

        }
    }

    private Element parseStruct(String nodeName, Element element, Element typeParentElem) throws DataException {

        List<MetadataStructsAttr> metaStructNodes = matadataStructAttrDAO.findBy("structId", nodeName);
        for (MetadataStructsAttr metaStructNode : metaStructNodes) {
        	Metadata metadata = metadataManager.getMetadataById(metaStructNode.getElementId());
            String metadataId = metadata.getMetadataId();
            String metadataType = metadata.getType();
            Element nodeElement = element.addElement(QN_ELEM);
            nodeElement.addAttribute("ref", "s:" + metadataId);
            if("N".equalsIgnoreCase(metaStructNode.getIsRequired())) {
                nodeElement.addAttribute("minOccurs", "0");
            }
            if (!uniqueMap.containsKey(metadataId)) {
                Element nodeTypeElement = typeParentElem.addElement(QN_ELEM);
                nodeTypeElement.addAttribute("name", metadataId);
                if ("number".equalsIgnoreCase(metadataType)) {
                    nodeTypeElement.addAttribute("type", "x:decimal");
                } else if ("string".equalsIgnoreCase(metadataType)) {
                    nodeTypeElement.addAttribute("type", "x:string");
                } else {
                    nodeTypeElement.addAttribute("type", "x:int");
                }
                uniqueMap.put(metadataId, null);
            } else {
                log.error(metadataId + "重复");
            }
        }


        return null;

    }

//    private void renderType(SDAVO sdaVO, Element parentElem, Element typeParentElem) throws DataException {
//        SDA sdaNode = sdaVO.getValue();
//        if (null != sdaNode) {
//            Element nodeElement = null;
//            if (null != parentElem) {
//                nodeElement = parentElem.addElement(QN_ELEM);
//            }
//            String nodeType = sdaNode.getType().trim();
//            String required = sdaNode.getRequired();
//            boolean isComplexNode = null != sdaVO.getChildNode() && sdaVO.getChildNode().size() > 0;
//            if ("array".equalsIgnoreCase(nodeType) || isComplexNode) {
//                if (null != parentElem) {
//                    nodeElement.addAttribute("name", sdaNode.getStructId());
//                    if ("array".equalsIgnoreCase(nodeType)) {
//                        nodeElement.addAttribute("maxOccurs", "unbounded");
//                    }
//                    nodeElement.addAttribute("type", "s:" + sdaNode.getStructId() + "Type");
//                }
//                if (!uniqueMap.containsKey(sdaNode.getStructId())) {
//                    Element arrayTypeElement = typeParentElem.addElement(QN_COMPLEX_TYPE);
//                    arrayTypeElement.addAttribute("name", sdaNode.getStructId() + "Type");
//                    Element arrayTypeSeq = arrayTypeElement.addElement(QN_SEQ);
//
//                    List<SDAVO> childSDAs = sdaVO.getChildNode();
//                    if (null != childSDAs) {
//                        for (SDAVO childSDA : childSDAs) {
//                            renderType(childSDA, arrayTypeSeq, typeParentElem);
//                        }
//                    }
//                    uniqueMap.put(sdaNode.getStructId(), null);
//                } else {
//                    log.error(sdaNode.getStructId() + "重复");
//                }
//
//            } else {
//                String metadataId = sdaNode.getMetadataId();
//                if (null != metadataId) {
//                    metadataId = metadataId.trim();
//                    Metadata metadata = metadataManager.getMetadataById(metadataId);
//                    String metadataType = metadata.getType();
//                    if (null != parentElem) {
//                        nodeElement.addAttribute("ref", "s:" + metadataId);
//                    }
//
//                    if (!uniqueMap.containsKey(sdaNode.getStructId())) {
//                        Element nodeTypeElement = typeParentElem.addElement(QN_ELEM);
//                        nodeTypeElement.addAttribute("name", metadataId);
//                        if ("number".equalsIgnoreCase(metadataType)) {
//                            nodeTypeElement.addAttribute("type", "x:decimal");
//                        } else if ("string".equalsIgnoreCase(metadataType)) {
//                            nodeTypeElement.addAttribute("type", "x:string");
//                        } else {
//                            nodeTypeElement.addAttribute("type", "x:int");
//                        }
//                        uniqueMap.put(sdaNode.getStructId(), null);
//                    } else {
//                        log.error(sdaNode.getStructId() + "重复");
//                    }
//
//                } else {
//                    throw new DataException("sda非法，服务字段几点必须有元数据！");
//                }
//            }
//
//            if ("Y".equalsIgnoreCase(required)) {
//                if (null != parentElem) {
//                    nodeElement.addAttribute("minOccurs", "1");
//                }
//            } else {
//                if (null != parentElem) {
//                    nodeElement.addAttribute("minOccurs", "0");
//                }
//
//            }
//
//        }
//    }
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
                    nodeElement.addAttribute("type", "s:" + sdaNode.getStructName() + "Type");
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
                        nodeElement.addAttribute("ref", "s:" + metadataId);
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
    public boolean generate(String serviceId, String dirPath) {

    	Service service = serviceManager.getServiceById(serviceId);
        generate(service, dirPath);
        return true;
    }

    public String handleDupOperationIdIssue(String operationId){
        if(operationId.indexOf("-") > -1){
            String tmpOperationId = operationId.substring(0, operationId.indexOf("-"));
            return tmpOperationId;
        }else{
            return operationId;
        }
    }
}
