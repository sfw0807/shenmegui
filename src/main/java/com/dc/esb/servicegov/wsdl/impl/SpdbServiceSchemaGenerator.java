package com.dc.esb.servicegov.wsdl.impl;

import com.dc.esb.servicegov.dao.impl.MetaStructNodeDAOImpl;
import com.dc.esb.servicegov.entity.MetaStructNode;
import com.dc.esb.servicegov.entity.SDANode;
import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.service.impl.MetadataManagerImpl;
import com.dc.esb.servicegov.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.vo.MetadataViewBean;
import com.dc.esb.servicegov.vo.SDA;
import com.dc.esb.servicegov.wsdl.WSDLGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: Administrator Date: 14-6-20 Time: 上午2:07
 */
@Component
public class SpdbServiceSchemaGenerator implements WSDLGenerator<List<Service>> {

	private static final Log log = LogFactory
			.getLog(SpdbServiceSchemaGenerator.class);
	private static final Namespace NS_URI_XMLNS = new Namespace("x",
			"http://www.w3.org/2001/XMLSchema");
	private static final QName QN_ELEM = new QName("element", NS_URI_XMLNS);
	private static final QName QN_COMPLEX_TYPE = new QName("complexType",
			NS_URI_XMLNS);
	private static final QName QN_SEQ = new QName("sequence", NS_URI_XMLNS);
	@Autowired
	private ServiceManagerImpl serviceManager;
	@Autowired
	private MetadataManagerImpl metadataManager;
	@Autowired
	private MetaStructNodeDAOImpl metaStructNodeDAO;
	private Map<String, String> uniqueMap = new HashMap<String, String>();

	@Override
	public boolean generate(List<Service> services) throws Exception {
		return false;
	}

	private List<Service> getAbstractService(Service service) {
		Service abService = null;
		return serviceManager.getParentService(service);
	}

	public boolean generate(Service serviceDO, String dirPath) {
		String serviceId = serviceDO.getServiceId();
		BufferedOutputStream schemaOut = null;
		try {
			List<Service> operations = serviceManager.getOpertions(serviceId);
			// List<Service> sHeadServices =
			// serviceManager.getServiceById("SHEAD");
			List<Service> sHeadServices = getAbstractService(serviceDO);
			Document document = DocumentHelper.createDocument();
			Element schemaRoot = document.addElement(new QName("schema",
					new Namespace("x", "http://www.w3.org/2001/XMLSchema")));
			schemaRoot.addNamespace("d", "http://esb.spdbbiz.com/metadata");
			schemaRoot.addNamespace("s", "http://esb.spdbbiz.com/services/"
					+ serviceId);
			schemaRoot.addAttribute("targetNamespace",
					"http://esb.spdbbiz.com/services/" + serviceId);
			schemaRoot.addAttribute("elementFormDefault", "qualified");
			schemaRoot.addAttribute("attributeFormDefault", "qualified");
			Element metadataImportElem = schemaRoot.addElement(new QName(
					"import", NS_URI_XMLNS));
			metadataImportElem.addAttribute("namespace",
					"http://esb.spdbbiz.com/metadata");
			metadataImportElem.addAttribute("schemaLocation", "http://10.112.20.145:8080/Publish/WSDLfilePath/SoapHeader.xsd");

			Element reqHeaderElem = schemaRoot.addElement(QN_ELEM);
			reqHeaderElem.addAttribute("name", "ReqHeader");
			reqHeaderElem.addAttribute("type", "d:ReqHeaderType");
			Element rspHeaderElem = schemaRoot.addElement(QN_ELEM);
			rspHeaderElem.addAttribute("name", "RspHeader");
			rspHeaderElem.addAttribute("type", "d:RspHeaderType");

			if (null != sHeadServices) {
				Service sHeadService = sHeadServices.get(0);
				if (null != sHeadService) {
					SDA sHeadSDA = serviceManager.getSDAofService(sHeadService);
					List<SDA> childSDAs = sHeadSDA.getChildNode();
					if (null == childSDAs) {
						throw new DataException("sda 异常");
					}
					SDA reqSDA = null;
					SDA rspSDA = null;
					for (SDA childSDA : childSDAs) {
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
						throw new DataException(
								"sda 异常， 不存在request或者response节点");
					}

					List<SDA> reqChildSDAs = reqSDA.getChildNode();
					SDA reqSvcHeaderSDA = null;
					for (SDA reqChildSDA : reqChildSDAs) {
						if ("ReqSvcHeader".equalsIgnoreCase(reqChildSDA
								.getValue().getStructName())) {
							reqSvcHeaderSDA = reqChildSDA;
							break;
						}
					}
					renderType(reqSvcHeaderSDA, null, schemaRoot);

					// rsp svc header
					List<SDA> rspChildSDAs = rspSDA.getChildNode();
					SDA rspSvcHeaderSDA = null;
					for (SDA rspChildSDA : rspChildSDAs) {
						if ("RspSvcHeader".equalsIgnoreCase(rspChildSDA
								.getValue().getStructName())) {
							rspSvcHeaderSDA = rspChildSDA;
							break;
						}
					}
					renderType(rspSvcHeaderSDA, null, schemaRoot);

				}

			}
			for (Service operationDO : operations) {
				String operationId = operationDO.getServiceId();
				// Todo
				String tmpOperationId = handleDupOperationIdIssue(operationId);
				SDA sda = serviceManager.getSDAofService(operationDO);
				List<SDA> childSDAs = sda.getChildNode();
				if (null == childSDAs) {
					throw new DataException("sda 异常");
				}
				SDA reqSDA = null;
				SDA rspSDA = null;
				for (SDA childSDA : childSDAs) {
					if ("request".equalsIgnoreCase(childSDA.getValue()
							.getStructName())) {
						reqSDA = childSDA;
					}
					if ("response".equalsIgnoreCase(childSDA.getValue()
							.getStructName())) {
						rspSDA = childSDA;
					}
				}
				if (null == reqSDA && null == rspSDA) {
					throw new DataException("sda 异常， 不存在request或者response节点");
				}

				// req
				Element reqOperation = schemaRoot.addElement(QN_ELEM);
				reqOperation.addAttribute("name", "Req" + tmpOperationId);
				String reqOperationTypeName = "s:Req" + tmpOperationId + "Type";
				reqOperation.addAttribute("type", reqOperationTypeName);
				Element reqOperationType = schemaRoot
						.addElement(QN_COMPLEX_TYPE);
				reqOperationType.addAttribute("name", "Req" + tmpOperationId
						+ "Type");
				Element reqOperationTypeSeq = reqOperationType
						.addElement(QN_SEQ);
				Element reqSvcHeaderElem = reqOperationTypeSeq
						.addElement(QN_ELEM);
				reqSvcHeaderElem.addAttribute("name", "ReqSvcHeader");
				reqSvcHeaderElem.addAttribute("type", "s:ReqSvcHeaderType");
				Element reqSvcBodyElem = reqOperationTypeSeq
						.addElement(QN_ELEM);
				reqSvcBodyElem.addAttribute("name", "SvcBody");
				reqSvcBodyElem.addAttribute("minOccurs", "0");
				Element reqSvcBodyComplexType = reqSvcBodyElem
						.addElement(QN_COMPLEX_TYPE);
				Element reqSvcBodyTypeSeq = reqSvcBodyComplexType
						.addElement(QN_SEQ);
				List<SDA> reqChildSDAs = reqSDA.getChildNode();
				SDA svcBodySDA = null;
				for (SDA reqChildSDA : reqChildSDAs) {
					if ("svcbody".equalsIgnoreCase(reqChildSDA.getValue()
							.getStructName())) {
						svcBodySDA = reqChildSDA;
						break;
					}
				}
				if (null != svcBodySDA) {
					List<SDA> svcBodySubSDAs = svcBodySDA.getChildNode();
					if (null != svcBodySubSDAs) {
						for (SDA svcBodySubSDA : svcBodySubSDAs) {
							renderSDA(svcBodySubSDA, reqSvcBodyTypeSeq,
									schemaRoot);
						}
					} else {
						log.warn("svc body 没有内容！");
					}

				}
				// rsp

				Element rspOperation = schemaRoot.addElement(QN_ELEM);
				rspOperation.addAttribute("name", "Rsp" + tmpOperationId);
				String rspOperationTypeName = "s:Rsp" + tmpOperationId + "Type";
				rspOperation.addAttribute("type", rspOperationTypeName);
				Element rspOperationType = schemaRoot
						.addElement(QN_COMPLEX_TYPE);
				rspOperationType.addAttribute("name", "Rsp" + tmpOperationId
						+ "Type");
				Element rspOperationTypeSeq = rspOperationType
						.addElement(QN_SEQ);
				Element rspSvcHeaderElem = rspOperationTypeSeq
						.addElement(QN_ELEM);
				rspSvcHeaderElem.addAttribute("name", "RspSvcHeader");
				rspSvcHeaderElem.addAttribute("type", "s:RspSvcHeaderType");
				Element rspSvcBodyElem = rspOperationTypeSeq
						.addElement(QN_ELEM);
				rspSvcBodyElem.addAttribute("minOccurs", "0");
				rspSvcBodyElem.addAttribute("name", "SvcBody");
				Element rspSvcBodyComplexType = rspSvcBodyElem
						.addElement(QN_COMPLEX_TYPE);
				Element rspSvcBodyTypeSeq = rspSvcBodyComplexType
						.addElement(QN_SEQ);
				List<SDA> rspChildSDAs = rspSDA.getChildNode();
				SDA rspSvcBodySDA = null;
				for (SDA rspChildSDA : rspChildSDAs) {
					if ("svcbody".equalsIgnoreCase(rspChildSDA.getValue()
							.getStructName())) {
						rspSvcBodySDA = rspChildSDA;
						break;
					}
				}
				if (null != rspSvcBodySDA) {
					List<SDA> svcBodySubSDAs = rspSvcBodySDA.getChildNode();
					if (null != svcBodySubSDAs) {
						for (SDA svcBodySubSDA : svcBodySubSDAs) {
							renderSDA(svcBodySubSDA, rspSvcBodyTypeSeq,
									schemaRoot);
						}
					} else {
						log.warn("svc body 没有内容！");
					}
				}
			}
			// 美化格式
			File schemaFile = new File(dirPath + File.separator + serviceId
					+ ".xsd");
			if (!schemaFile.exists()) {
				schemaFile.createNewFile();
			}
			// File wsdlFile = new File(serviceId + ".wsdl");
			schemaOut = new BufferedOutputStream(new FileOutputStream(
					schemaFile));
			// BufferedOutputStream wsdlOut = new BufferedOutputStream(new
			// FileOutputStream(wsdlFile));

			XMLWriter writer = null;
			OutputFormat format = OutputFormat.createPrettyPrint();
			writer = new XMLWriter(schemaOut, format);
			writer.write(document);
		} catch (DataException e) {
			log.error(e, e);
		} catch (UnsupportedEncodingException e) {
			log.error(e, e);
		} catch (IOException e) {
			log.error(e, e);
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			uniqueMap.clear();
			if (null != schemaOut) {
				try {
					schemaOut.close();
				} catch (IOException e) {
					log.error(e, e);
				}
			}
		}
		return true;
	}

	public void renderSDA(SDA sda, Element parentElem, Element typeParentElem)
			throws DataException {
		// 数组
		SDANode sdaNode = sda.getValue();
		if (null != sdaNode) {
			Element nodeElement = parentElem.addElement(QN_ELEM);
			String nodeType = sdaNode.getType().trim();
			String required = sdaNode.getRequired();
			boolean isComplexNode = null != sda.getChildNode()
					&& sda.getChildNode().size() > 0;
			if ("array".equalsIgnoreCase(nodeType) || isComplexNode) {
				nodeElement.addAttribute("name", sdaNode.getStructName());
				if ("array".equalsIgnoreCase(nodeType)) {
					nodeElement.addAttribute("maxOccurs", "unbounded");
				}
				nodeElement.addAttribute("type", "s:" + sdaNode.getStructName()
						+ "Type");

				if (!uniqueMap.containsKey(sdaNode.getStructName())) {
					Element arrayTypeElement = typeParentElem
							.addElement(QN_COMPLEX_TYPE);
					arrayTypeElement.addAttribute("name",
							sdaNode.getStructName() + "Type");
					Element arrayTypeSeq = arrayTypeElement.addElement(QN_SEQ);
					List<SDA> childSDAs = sda.getChildNode();
					if (null != childSDAs) {
						for (SDA childSDA : childSDAs) {
							renderSDA(childSDA, arrayTypeSeq, typeParentElem);
						}
					}
					uniqueMap.put(sdaNode.getStructName(), null);
				} else {
					log.error(sdaNode.getStructName() + "重复");
				}
			} else if ("struct".equalsIgnoreCase(nodeType)) {
				nodeElement.addAttribute("name", sdaNode.getStructName());
				Element sequence = nodeElement.addElement(QN_COMPLEX_TYPE)
						.addElement(QN_SEQ);
				parseStruct(sdaNode.getMetadataId(), sequence, typeParentElem);
			} else {
				String metadataId = sdaNode.getMetadataId();
				if (null != metadataId) {
					metadataId = metadataId.trim();
					MetadataViewBean metadata = metadataManager
							.getMetadataById(metadataId);
					String metadataType = metadata.getType();
					nodeElement.addAttribute("ref", "s:" + metadataId);
					if (!uniqueMap.containsKey(sdaNode.getStructName())) {
						Element nodeTypeElement = typeParentElem
								.addElement(QN_ELEM);
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
				nodeElement.addAttribute("minOccurs", "1");
			} else {
				nodeElement.addAttribute("minOccurs", "0");
			}

		}
	}

	private Element parseStruct(String nodeName, Element element,
			Element typeParentElem) throws DataException {
		List<MetaStructNode> metaStructNodes = metaStructNodeDAO.findBy(
				"structId", nodeName);
		for (MetaStructNode metaStructNode : metaStructNodes) {
			MetadataViewBean metadata = metadataManager
					.getMetadataById(metaStructNode.getElementId());
			String metadataId = metadata.getMetadataId();
			String metadataType = metadata.getType();
			List<MetaStructNode> childNodes = metaStructNodeDAO.findBy(
					"structId", metadataId);
			if (null != childNodes && childNodes.size() > 0) {
				Element nodeElement = element.addElement(QN_ELEM);
				nodeElement.addAttribute("name", metadataId);
				if ("N".equalsIgnoreCase(metaStructNode.getRequired())) {
					nodeElement.addAttribute("minOccurs", "0");
				}
				Element arrayTypeElement = nodeElement
						.addElement(QN_COMPLEX_TYPE);
				Element arrayTypeSeq = arrayTypeElement.addElement(QN_SEQ);
				parseStruct(metadataId, arrayTypeSeq,typeParentElem);
			} else {
				Element nodeElement = element.addElement(QN_ELEM);
				nodeElement.addAttribute("ref", "s:" + metadataId);
				if ("N".equalsIgnoreCase(metaStructNode.getRequired())) {
					nodeElement.addAttribute("minOccurs", "0");
				}
				if (!uniqueMap.containsKey(metadataId)) {
					Element nodeTypeElement = typeParentElem
							.addElement(QN_ELEM);
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

		}

		return null;

	}

	private void renderType(SDA sda, Element parentElem, Element typeParentElem)
			throws DataException {
		SDANode sdaNode = sda.getValue();
		if (null != sdaNode) {
			Element nodeElement = null;
			if (null != parentElem) {
				nodeElement = parentElem.addElement(QN_ELEM);
			}
			String nodeType = sdaNode.getType().trim();
			String required = sdaNode.getRequired();
			boolean isComplexNode = null != sda.getChildNode()
					&& sda.getChildNode().size() > 0;
			if ("array".equalsIgnoreCase(nodeType) || isComplexNode) {
				if (null != parentElem) {
					nodeElement.addAttribute("name", sdaNode.getStructName());
					if ("array".equalsIgnoreCase(nodeType)) {
						nodeElement.addAttribute("maxOccurs", "unbounded");
					}
					nodeElement.addAttribute("type",
							"s:" + sdaNode.getStructName() + "Type");
				}
				if (!uniqueMap.containsKey(sdaNode.getStructName())) {
					Element arrayTypeElement = typeParentElem
							.addElement(QN_COMPLEX_TYPE);
					arrayTypeElement.addAttribute("name",
							sdaNode.getStructName() + "Type");
					Element arrayTypeSeq = arrayTypeElement.addElement(QN_SEQ);

					List<SDA> childSDAs = sda.getChildNode();
					if (null != childSDAs) {
						for (SDA childSDA : childSDAs) {
							renderType(childSDA, arrayTypeSeq, typeParentElem);
						}
					}
					uniqueMap.put(sdaNode.getStructName(), null);
				} else {
					log.error(sdaNode.getStructName() + "重复");
				}

			} else {
				String metadataId = sdaNode.getMetadataId();
				if (null != metadataId) {
					metadataId = metadataId.trim();
					MetadataViewBean metadata = metadataManager
							.getMetadataById(metadataId);
					String metadataType = metadata.getType();
					if (null != parentElem) {
						nodeElement.addAttribute("ref", "s:" + metadataId);
					}

					if (!uniqueMap.containsKey(sdaNode.getStructName())) {
						Element nodeTypeElement = typeParentElem
								.addElement(QN_ELEM);
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
		List<Service> services = serviceManager.getServiceById(serviceId);
		for (Service service : services) {
			generate(service, dirPath);
		}
		return true;
	}

	public String handleDupOperationIdIssue(String operationId) {
		if (operationId.indexOf("-") > -1) {
			String tmpOperationId = operationId.substring(0,
					operationId.indexOf("-"));
			return tmpOperationId;
		} else {
			return operationId;
		}
	}
}
