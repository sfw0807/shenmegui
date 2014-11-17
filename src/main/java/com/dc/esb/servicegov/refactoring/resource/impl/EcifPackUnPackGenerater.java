package com.dc.esb.servicegov.refactoring.resource.impl;

import static com.dc.esb.servicegov.refactoring.util.PackerUnPackerConstants.CONSUMER;
import static com.dc.esb.servicegov.refactoring.util.PackerUnPackerConstants.IN_CONF_DIR;
import static com.dc.esb.servicegov.refactoring.util.PackerUnPackerConstants.OUT_CONF_DIR;
import static com.dc.esb.servicegov.refactoring.util.PackerUnPackerConstants.PROVIDER;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.resource.IConfigGenerater;
import com.dc.esb.servicegov.refactoring.resource.IDataFromDB.ResourceType;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.IMetadataNode;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.IMetadataNodeAttribute;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataNode;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataNodeAttribute;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataNodeHelper;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.SDAHelper;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.SoapConstants;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.SoapHelper;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.WSDLHelper;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.XMLHelper;
import com.dc.esb.servicegov.refactoring.util.SDAConstants;

@Service
public class EcifPackUnPackGenerater extends InterfaceSoapGenerate implements
IConfigGenerater<InvokeInfo, List<File>> {

	@Autowired
	private DefaultInterfaceFetcher defaultInterfaceFetcher;
	@Autowired
	private MetadataStructHelper metadataStructHelper;
	@Autowired
	private XMPassedInterfaceDataFromDB xmPassedInterfaceDataFromDB;
	@Autowired
	private PackerUnPackerConfigHelper packerUnPackerConfigHelper;
	@Autowired
	private SpdbSpecDefaultInterfaceGenerater spdbSpecDefaultInterfaceGenerater;
	@Autowired
	private ServiceDataFromDB serviceDataFromDB;
	
	private SoapHelper soapHelper;
	private SDAHelper sdaHelper;
	private WSDLHelper wsdlHelper;
	private String interfaceId;
	private String host;
	private List<Namespace> namespaces;
	private String prdMsgType ;
	private String csmMsgType ;
//	private Map<String, Namespace> namespaceMap;
	List<String> attrExclude;
	List<String> attrInculde;
//	private Map<String, Namespace> defaultNamespaces;
	private List<File> ecifInterfaceFiles = null;

	// private static final String IN_CONFIG_DIR_PATH = "in_conf_ecif/metadata";
	// private static final String OUT_CONFIG_DIR_PATH =
	// "out_conf_ecif/metadata";
	/**
	 * out of date
	 */

	private static final Log log = LogFactory
			.getLog(EcifPackUnPackGenerater.class);

	public EcifPackUnPackGenerater() {
		namespaces = new ArrayList<Namespace>();
		attrInculde = new ArrayList<String>();
		attrInculde.add("type=array");
		attrInculde.add("type=selfjoinarray");
		attrInculde.add("type=selfjoinchild");
		attrInculde.add("is_struct");
		attrInculde.add("metadataid");
		attrInculde.add("package_type");
		attrInculde.add("expression");
		soapHelper = SoapHelper.getInstance();
		sdaHelper = SDAHelper.getInstance();
		wsdlHelper = WSDLHelper.getInstance();
	}

	/**
	 * out of date
	 * 
	 * @param root
	 * @return
	 */
	private Map<String, Namespace> getNamespacesFromRoot(MetadataNode root) {
		Map<String, Namespace> namespaceMap = null;
		Properties pro = root.getProperty().getProperty();
		if (root.hasAttribute()) {
			for (Object objectProperty : pro.keySet()) {
				String key = (String) objectProperty;
				String propertyValue = (String) pro.get(objectProperty);

				if ((propertyValue != null) && (!propertyValue.equals(""))) {
					if (propertyValue.startsWith("namespace")) {
						parseNamespaceString(key, propertyValue, namespaceMap);
					}
				}
			}
		}
		return namespaceMap;
	}

	/**
	 * 解析命名空间配置字符串，字符串为
	 * 
	 * @param namespaceString
	 */
	private void parseNamespaceString(String key, String value,
			Map<String, Namespace> namespaceMap) {
		String[] namespaceConfig = key.split("_");
		if (namespaceConfig.length < 3) {
			return;
		} else {
			Namespace namespace = new Namespace(namespaceConfig[2], value);
			namespaceMap.put(namespaceConfig[1], namespace);
		}
	}

	/**
	 * types will be included only if it is in the "inculde_attrs" config, both
	 * "metadataid" style and "metadataid=XX" style config can be accepted and
	 * processed by policy engine
	 * 
	 * @param root
	 * @return
	 */
	public List<String> getIncludingAttrList(MetadataNode root) {
		List<String> includingAttrList = null;
		String includingAttrs = root.getProperty().getProperty("include_attrs");
		String[] attrs = includingAttrs.split(",");
		if (null != attrs && attrs.length > 0) {
			includingAttrList = Arrays.asList(attrs);
		}
		return includingAttrList;
	}

//	/**
//	 * generate spdb spec in and out message
//	 */
//	@Override
//	public void generate(String serviceID) {
//		log.info("开始生成Ecif配置[" + serviceID + "]");
//
//		// this.host = wsdlHelper.getHost(serviceID);
//		this.host = "http://www.spdb.com.cn/spdb";
//		// initNamespaceAndCheckPrerequiste(host);
//		// getNamespacesFromRoot();
//		namespaces.add(new Namespace("SOAP-ENV",
//				"http://schemas.xmlsoap.org/soap/envelope/"));
//		namespaces.add(new Namespace("SOAP-ENC",
//				"http://schemas.xmlsoap.org/soap/encoding/"));
//		namespaces.add(new Namespace("xsi",
//				"http://www.w3.org/2001/XMLSchema-instance"));
//		namespaces
//				.add(new Namespace("xsd", "http://www.w3.org/2001/XMLSchema"));
//		namespaces.add(new Namespace("m0", host + "/metadata"));
//		namespaces.add(new Namespace("m1", host + "/service/esb/commonHeader"));
//		namespaces
//				.add(new Namespace("m2", host + "/service/ecif/partyService"));
//		namespaces.add(new Namespace("m", host
//				+ "/service/ecif/partyService/wsdl/v1"));
//		namespaces.add(new Namespace("m5", host + "/service/ecifStruct"));
//
//		if (null != serviceID) {
//			List<String> operations = wsdlHelper.getAllOperationIds(serviceID);
//
//			if (null != operations && operations.size() > 0) {
//				for (String operation : operations) {
//					MetadataNode defaultIn = defaultInterfaceFetcher
//							.findDefaultInterfaceIn(operation);
//					MetadataNode defaultOut = defaultInterfaceFetcher
//							.findDefaultInterfaceOut(operation);
//					List<Map<String, String>> list = xmPassedInterfaceDataFromDB
//							.getInterfaceIDByServiceID(operation);
//					if (null == list) {
//						continue;
//					}
//					if (0 == list.size()) {
//						continue;
//					}
//					MetadataNode operationNode = wsdlHelper
//							.getOperationNode(operation);
//					interfaceId = list.get(0).get("INTERFACEID");
//					MetadataNode interfaceNode = (MetadataNode) xmPassedInterfaceDataFromDB
//							.getNodeFromDB(interfaceId, ResourceType.INTERFACE);
//					if (null == operationNode || null == interfaceNode) {
//						continue;
//					}
//					log.info("[Ecif Generater]: find interface Node: ["
//							+ interfaceNode + "]");
//					interfaceNode.getChild("request").addChild(
//							0,
//							operationNode.getChild("request").getChild(
//									"ReqSvcHeader"));
//					interfaceNode.getChild("response").addChild(
//							0,
//							operationNode.getChild("response").getChild(
//									"RspSvcHeader"));
//					operationNode = interfaceNode;
//					metadataStructHelper
//							.parseMetadataStructForInterface(operationNode);
//					try {
//						if (null != defaultIn && null != operationNode) {
//							this.generateConsumerSoapXML(
//									(MetadataNode) MetadataNodeHelper
//											.cloneNode(defaultIn),
//									(MetadataNode) MetadataNodeHelper
//											.cloneNode(operationNode),
//									operation, serviceID);
//						}
//						if (null != defaultOut && null != operationNode) {
//							this.generateProviderSoapXML(
//									(MetadataNode) MetadataNodeHelper
//											.cloneNode(defaultOut),
//									(MetadataNode) MetadataNodeHelper
//											.cloneNode(operationNode),
//									operation, serviceID);
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//						log.error(e.getMessage());
//					}
//				}
//			}
//		}
//	}

	/**
	 * 通过默认In接口和服务节点生成消费方的拆包配置节点，
	 * 
	 * @param defaultInterfaceNode
	 * @param serviceNode
	 * @return
	 */
	protected MetadataNode createConsumerUnPackerConfigNode(
			MetadataNode defaultConsumerInterface, MetadataNode serviceNode,
			String operationId) {
		MetadataNode consumerUnPackerConfigNode = null;
		if (null != defaultConsumerInterface && null != serviceNode) {
			consumerUnPackerConfigNode = createConfigNode(
					defaultConsumerInterface, serviceNode, operationId,
					SDAConstants.REQUEST);
		}
		IMetadataNodeAttribute attr = consumerUnPackerConfigNode.getProperty();
		if (null == attr) {
			attr = new MetadataNodeAttribute();
			consumerUnPackerConfigNode.setProperty(attr);
		}
		attr.setProperty("package_type", "xml");
		return consumerUnPackerConfigNode;
	}

	/**
	 * 通过默认接口和服务节点生成消费方的组包配置节点，
	 * 
	 * @param defaultInterfaceNode
	 * @param serviceNode
	 * @return
	 */
	protected MetadataNode createConsumerPackerConfigNode(
			MetadataNode defaultConsumerInterface, MetadataNode serviceNode,
			String operationId) {
		MetadataNode consumerPackerConfigNode = null;
		if (null != defaultConsumerInterface && null != serviceNode) {
			consumerPackerConfigNode = createConfigNode(
					defaultConsumerInterface, serviceNode, operationId,
					SDAConstants.RESPONSE);
		}
		// 组包需要前缀
		IMetadataNodeAttribute attr = consumerPackerConfigNode.getProperty();
		if (null == attr) {
			attr = new MetadataNodeAttribute();
			consumerPackerConfigNode.setProperty(attr);
		}
		attr.setProperty("package_type", "soap");
		addPrefix(consumerPackerConfigNode);
		return consumerPackerConfigNode;
	}

	/**
	 * 通过默认接口和服务节点生成提供方的拆包配置节点
	 * 
	 * @param defaultProviderInterface
	 * @param serviceNode
	 * @return
	 */
	protected MetadataNode createProviderUnPackConfigNode(
			MetadataNode defaultProviderInterface, MetadataNode serviceNode,
			String operationId) {
		MetadataNode providerUnPachConfigNode = null;
		if (null != defaultProviderInterface && null != serviceNode) {
			providerUnPachConfigNode = createConfigNode(
					defaultProviderInterface, serviceNode, operationId,
					SDAConstants.RESPONSE);
		}
		IMetadataNodeAttribute attr = providerUnPachConfigNode.getProperty();
		if (null == attr) {
			attr = new MetadataNodeAttribute();
			providerUnPachConfigNode.setProperty(attr);
		}
		attr.setProperty("package_type", "xml");
		return providerUnPachConfigNode;
	}

	/**
	 * 通过默认接口和服务节点生成提供方的组包配置节点
	 * 
	 * @param defaultProviderInterface
	 * @param serviceNode
	 * @return
	 */
	protected MetadataNode createProviderPackConfigNode(
			MetadataNode defaultProviderInterface, MetadataNode serviceNode,
			String operationId) {
		MetadataNode providerPachConfigNode = null;
		if (null != defaultProviderInterface && null != serviceNode) {
			providerPachConfigNode = createConfigNode(defaultProviderInterface,
					serviceNode, operationId, SDAConstants.REQUEST);
		}
		// 组包需要前缀
		IMetadataNodeAttribute attr = providerPachConfigNode.getProperty();
		if (null == attr) {
			attr = new MetadataNodeAttribute();
			providerPachConfigNode.setProperty(attr);
		}
		attr.setProperty("package_type", "soap");
		addPrefix(providerPachConfigNode);
		return providerPachConfigNode;
	}

	protected void addPrefix(IMetadataNode root) {
		root.setNodeID("SOAP-ENV" + ":" + root.getNodeID());
		MetadataNode headerNode = (MetadataNode) root
				.getChild(SoapConstants.ELEM_HEADER);
		headerNode.setNodeID("SOAP-ENV" + ":" + headerNode.getNodeID());

		for (IMetadataNode childNode : headerNode.getChild()) {

			if (childNode.hasChild()) {
				addPrefix(childNode, "m");
			}

		}

		MetadataNode bodyNode = (MetadataNode) root
				.getChild(SoapConstants.ELEM_BODY);
		bodyNode.setNodeID("SOAP-ENV" + ":" + bodyNode.getNodeID());
		for (IMetadataNode childNode : bodyNode.getChild()) {
			if (childNode.hasChild()) {
				addPrefix(childNode, "m");
			}
		}
	}

	protected void handleExpression(IMetadataNode node, String interfaceType,
			String reqRsp, String path) {
		if("request".equals(node.getNodeID()) || "response".equals(node.getNodeID())){
			
		}else{
			path = path + "/" + node.getNodeID();
			
		}
		
		log.info("[Get Expression Path]: [" + path + "]");
		if (!node.hasChild()) {
			log.info("[Ecif Handle Expression]: handle leaf node ["
					+ node.getNodeID() + "]");
			IMetadataNodeAttribute attr = node.getProperty();
			if (null != attr) {
				String rawExpression = attr.getProperty("expression");
				if (null != rawExpression) {
					if (rawExpression.indexOf("$codeConvert") >= 0) {
						path = "Envelope/Body/" + path;
						packerUnPackerConfigHelper.handleExpression(node,
								interfaceType, reqRsp, path);
					}

				}

			}

		} else {
			if (path.split("/").length > 2) {
				path = path + "[*]";
			}
			for (IMetadataNode child : node.getChild()) {
				handleExpression(child, interfaceType, reqRsp, path);
			}
		}
	}

	protected void addPrefix(IMetadataNode node, String prifix) {
		node.setNodeID(prifix + ":" + node.getNodeID());
		if (node.hasChild()) {
			for (IMetadataNode childNode : node.getChild()) {
				if (childNode.hasChild()) {
					if (childNode.getNodeID().toLowerCase().endsWith("svcbody")) {
						addPrefix(childNode, "m2");
					} else {
						addPrefix(childNode, "m1");
					}
				} else {
					addPrefix(childNode, "m0");
				}
			}
		}
	}

	/**
	 * 
	 * @param defaultInterfaceNode
	 * @param serviceNode
	 * @param type
	 *            SDAConstants.REQUEST SDAConstants.RESPONSE
	 * @return
	 */
	protected MetadataNode createConfigNode(MetadataNode defaultInterfaceNode,
			MetadataNode serviceNode, String operationId, String type) {
		MetadataNode configNode = null;
		if (null != defaultInterfaceNode && null != serviceNode) {
			configNode = soapHelper.getSoapTemplate();
			// 获取默认接口中的request/response节点
			MetadataNode interfaceRepRspNode = null;
			if (type.equalsIgnoreCase(SDAConstants.REQUEST)) {
				interfaceRepRspNode = sdaHelper
						.getRequestNode(defaultInterfaceNode);
			} else if (type.equalsIgnoreCase(SDAConstants.RESPONSE)) {
				interfaceRepRspNode = sdaHelper
						.getResponseNode(defaultInterfaceNode);
			}
			// root节点的扩展属性在request/response节点中定义
			IMetadataNodeAttribute rootAttribute = interfaceRepRspNode
					.getProperty();
			configNode.setProperty(rootAttribute);
			// SOAP简单对象中的Header中的内容节点在默认接口中的request/response下定义
			MetadataNode headerNode = (MetadataNode) configNode
					.getChild(SoapConstants.ELEM_HEADER);
			MetadataNodeHelper.copyChild(interfaceRepRspNode, headerNode);
			// SOAP简单对象中的Body中的内容节点在默认服务中的request/response下定义
			MetadataNode serviceReqRspNode = null;
			if (type.equalsIgnoreCase(SDAConstants.REQUEST)) {
				serviceReqRspNode = sdaHelper.getRequestNode(serviceNode);
			} else if (type.equalsIgnoreCase(SDAConstants.RESPONSE)) {
				serviceReqRspNode = sdaHelper.getResponseNode(serviceNode);
			}
			MetadataNode bodyNode = (MetadataNode) configNode
					.getChild(SoapConstants.ELEM_BODY);
			// 创建operation节点
			MetadataNode operationNode = new MetadataNode();
			interfaceId = handleInterfaceIdForDupTrade(interfaceId);
			if (type.equalsIgnoreCase(SDAConstants.REQUEST)) {
				operationNode.setNodeID(interfaceId + "Request");
				bodyNode.addChild(operationNode);
			} else if (type.equalsIgnoreCase(SDAConstants.RESPONSE)) {
				operationNode.setNodeID(interfaceId + "Response");
				bodyNode.addChild(operationNode);
			}
			MetadataNodeHelper.copyChild(serviceReqRspNode, operationNode);
		}
		return configNode;
	}
	
	/**
	 * @author Vincent Fan
	 *  处理 多只交易共用交易码的情况下，InterfaceId增加了区分码的做法。
	 *  将区分码(由 "-"分隔)去除
	 */
	private String handleInterfaceIdForDupTrade(String interfaceId){
		if(interfaceId.indexOf("-") > -1){
			interfaceId = interfaceId.substring(0,interfaceId.indexOf("-"));
		}
		return interfaceId;
	}

	/**
	 * 生成消费方的拆组包配置文件
	 * 
	 * @param defaultInterfaceNode
	 * @param serviceNode
	 * @param operationId
	 * @param serviceId
	 * @throws Exception
	 */
	protected void generateConsumerSoapXML(MetadataNode defaultInterfaceNode,
			MetadataNode serviceNode, String operationId, String serviceId)
			throws Exception {
		// getNamespacesFromRoot(defaultInterfaceNode);
		String dir = serviceId + operationId + "(" + csmMsgType + "-"
		+ prdMsgType + ")" +File.separator + IN_CONF_DIR;
		String reqSoapFileName = dir + File.separator
				+ "channel_soap_service_" + serviceId + operationId
				+ ".xml";
		String respSoapFileName = dir + File.separator + "service_"
				+ serviceId + operationId + "_system_soap.xml";
		File inConfigDir = new File(dir);
		if (!inConfigDir.exists()) {
			inConfigDir.mkdirs();
		}
		this.ecifInterfaceFiles.add(inConfigDir);
		MetadataNode consumerUnPackNode = this
				.createConsumerUnPackerConfigNode(defaultInterfaceNode,
						serviceNode, operationId);
		Document consumerUnPackDocument = MetadataNodeHelper
				.MetadataNode2DocumentWithInclude(consumerUnPackNode, null,
						attrInculde);

		String consumerReqSoapXML = this.documentToXML(consumerUnPackDocument);
		File reqSoapFile = new File(reqSoapFileName);
		if (!reqSoapFile.exists()) {
			reqSoapFile.createNewFile();
		}
		this.saveXMLFile(reqSoapFile, consumerReqSoapXML);
		// 组包需要命名空间
		MetadataNode consumerPackNode = this.createConsumerPackerConfigNode(
				defaultInterfaceNode, serviceNode, operationId);
		Document consumerPackDocument = MetadataNodeHelper
				.MetadataNode2DocumentWithInclude(consumerPackNode, namespaces,
						attrInculde);

		String consumerRespSoapXML = this.documentToXML(consumerPackDocument);

		File respSoapFile = new File(respSoapFileName);
		if (!respSoapFile.exists()) {
			respSoapFile.createNewFile();
		}
		XMLHelper.saveXMLFile(respSoapFile, consumerRespSoapXML);
	}

	protected void generateProviderSoapXML(MetadataNode defaultInterfaceNode,
			MetadataNode serviceNode, String operationId, String serviceId)
			throws Exception {
		getNamespacesFromRoot(defaultInterfaceNode);
		String dir = serviceId + operationId + "(" + csmMsgType + "-"
		+ prdMsgType + ")" +File.separator + OUT_CONF_DIR;
		String respSoapFileName = dir + File.separator
				+ "channel_ecifSystem_service_" + serviceId + operationId
				+ ".xml";
		String reqSoapFileName = dir + File.separator + "service_"
				+ serviceId + operationId + "_system_ecifSystem.xml";
		File outConfigDir = new File(dir);
		if (!outConfigDir.exists()) {
			outConfigDir.mkdirs();
		}
		this.ecifInterfaceFiles.add(outConfigDir);

		// 不需要前缀的节点在前生成
		MetadataNode providerUnPackNode = createProviderUnPackConfigNode(
				defaultInterfaceNode, serviceNode, operationId);
		Document providerUnPackDocument = MetadataNodeHelper
				.MetadataNode2DocumentWithInclude(providerUnPackNode, null,
						attrInculde);
		String providerRespSoapXML = this.documentToXML(providerUnPackDocument);
		File respSoapFile = new File(respSoapFileName);
		if (!respSoapFile.exists()) {
			respSoapFile.createNewFile();
		}
		this.saveXMLFile(respSoapFile, providerRespSoapXML);

		// 组包需要命名空间
		MetadataNode providerPackNode = createProviderPackConfigNode(
				defaultInterfaceNode, serviceNode, operationId);
		Document providerPackDocument = MetadataNodeHelper
				.MetadataNode2DocumentWithInclude(providerPackNode, namespaces,
						attrInculde);
		String providerReqSoapXML = this.documentToXML(providerPackDocument);
		File reqSoapFile = new File(reqSoapFileName);
		if (!reqSoapFile.exists()) {
			reqSoapFile.createNewFile();
		}
		this.saveXMLFile(reqSoapFile, providerReqSoapXML);

	}

	public List<File> getEcifInterfaceFiles() {
		return ecifInterfaceFiles;
	}

	public void setEcifInterfaceFiles(List<File> ecifInterfaceFiles) {
		this.ecifInterfaceFiles = ecifInterfaceFiles;
	}

	@Override
	public List<File> generate(InvokeInfo invokeInfo) throws Exception {
		ecifInterfaceFiles = new ArrayList<File>();
		// TODO Auto-generated method stub
		String operationId = invokeInfo.getOperationId();
		log.info("[配置生成]：场景ID[" + operationId + "]");
		String interfaceId = invokeInfo.getEcode();
		this.interfaceId = interfaceId;
		log.info("[配置生成]：接口ID[" + interfaceId + "]");
		String interfaceType = invokeInfo.getDirection();
		log.info("[配置生成]：接口类型ID[" + interfaceType + "]");
		String sysId = invokeInfo.getProvideSysId();
		if (null != sysId) {
			sysId = sysId.trim();
		}
		this.prdMsgType = invokeInfo.getProvideMsgType();
		this.csmMsgType = invokeInfo.getConsumeMsgType();
		log.info("[配置生成]：系统名称[" + sysId + "]");
//      String serviceId = packerUnPackerConfigHelper
//				.getServiceIdByOperationId(operationId);
		String serviceId = invokeInfo.getServiceId();
		log.info("[配置生成]：服务ID[" + serviceId + "]");
		this.host = wsdlHelper.getHost(serviceId);
		log.info("[配置生成]：服务ID[" + this.host + "]");

		this.host = "http://www.spdb.com.cn/spdb";
		// initNamespaceAndCheckPrerequiste(host);
		// getNamespacesFromRoot();
		namespaces.add(new Namespace("SOAP-ENV",
				"http://schemas.xmlsoap.org/soap/envelope/"));
		namespaces.add(new Namespace("SOAP-ENC",
				"http://schemas.xmlsoap.org/soap/encoding/"));
		namespaces.add(new Namespace("xsi",
				"http://www.w3.org/2001/XMLSchema-instance"));
		namespaces
				.add(new Namespace("xsd", "http://www.w3.org/2001/XMLSchema"));
		namespaces.add(new Namespace("m0", host + "/metadata"));
		namespaces.add(new Namespace("m1", host + "/service/esb/commonHeader"));
		namespaces
				.add(new Namespace("m2", host + "/service/ecif/partyService"));
		namespaces.add(new Namespace("m", host
				+ "/service/ecif/partyService/wsdl/v1"));
		namespaces.add(new Namespace("m5", host + "/service/ecifStruct"));

		MetadataNode operationNode = serviceDataFromDB.getNodeFromDB(invokeInfo.getServiceId(), operationId, true);
		log.info("start to handle operation[" + operationId + "]");
		MetadataNode interfaceNode = (MetadataNode) xmPassedInterfaceDataFromDB
				.getNodeFromDB(interfaceId, ResourceType.INTERFACE);
		log.info("[Ecif Generater]: find interface Node: [" + interfaceNode
				+ "]");
		interfaceNode.getChild("request").addChild(0,
				operationNode.getChild("request").getChild("ReqSvcHeader"));
		interfaceNode.getChild("response").addChild(0,
				operationNode.getChild("response").getChild("RspSvcHeader"));
		operationNode = interfaceNode;
		metadataStructHelper.parseMetadataStructForInterface(operationNode);
		log.info("[Ecif Generater]: find operation after parse: ["
				+ operationNode + "]");
		if (CONSUMER.equalsIgnoreCase(interfaceType)) {
			log.info("[标准配置生成]:开始生成消费方标准配置");
			MetadataNode defaultIn = defaultInterfaceFetcher
					.findDefaultInterfaceIn(operationId);
			if (null != defaultIn && null != operationNode) {
				log.info("start to generate default consumer config for ["
						+ operationId + "]");
				interfaceId = handleInterfaceIdForDupTrade(interfaceId);
				handleExpression(operationNode.getChild("request"),
						interfaceType, "request", interfaceId + "Request");
				handleExpression(operationNode.getChild("response"),
						interfaceType, "response", interfaceId + "Response");
				operationId = handleInterfaceIdForDupTrade(operationId);
				this.generateConsumerSoapXML((MetadataNode) MetadataNodeHelper
						.cloneNode(defaultIn),
						(MetadataNode) MetadataNodeHelper
								.cloneNode(operationNode), operationId,
						serviceId);
			}

		} else if (PROVIDER.equalsIgnoreCase(interfaceType)) {
			MetadataNode defaultOut = defaultInterfaceFetcher
					.findDefaultInterfaceOut(operationId);
			if (null != defaultOut && null != operationNode) {
				log.info("start to generate default provider config for ["
						+ operationId + "]");
				interfaceId = handleInterfaceIdForDupTrade(interfaceId);
				handleExpression(operationNode.getChild("request"),
						interfaceType, "request", interfaceId + "Request");
				handleExpression(operationNode.getChild("response"),
						interfaceType, "response", interfaceId + "Response");
				operationId = handleInterfaceIdForDupTrade(operationId);
				this.generateProviderSoapXML((MetadataNode) MetadataNodeHelper
						.cloneNode(defaultOut),
						(MetadataNode) MetadataNodeHelper
								.cloneNode(operationNode), operationId,
						serviceId);
			}
		}

		packerUnPackerConfigHelper.reverseInterfaceDirection(invokeInfo);
		List<File> files = spdbSpecDefaultInterfaceGenerater.generate(invokeInfo);
		this.ecifInterfaceFiles.addAll(files);
		return this.ecifInterfaceFiles;
	}

}
