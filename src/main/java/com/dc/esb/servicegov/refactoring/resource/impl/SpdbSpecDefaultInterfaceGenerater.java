package com.dc.esb.servicegov.refactoring.resource.impl;

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
import com.dc.esb.servicegov.refactoring.resource.metadataNode.IMetadataNode;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.IMetadataNodeAttribute;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataNode;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataNodeAttribute;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataNodeHelper;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.SDAHelper;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.SoapConstants;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.SoapHelper;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.XMLHelper;
import com.dc.esb.servicegov.refactoring.util.SDAConstants;
import static com.dc.esb.servicegov.refactoring.util.PackerUnPackerConstants.*;

@Service
public class SpdbSpecDefaultInterfaceGenerater extends InterfaceSoapGenerate
		implements IConfigGenerater<InvokeInfo, List<File>> {

	// private InterfaceDataFromDB interfaceDataFromDB;
	@Autowired
	private DefaultInterfaceFetcher defaultInterfaceFetcher;
	@Autowired
	private MetadataStructHelper metadataStructHelper;
//	@Autowired
//	private PackerUnPackerConfigHelper packerUnPackerConfigHelper;
	@Autowired
	private ServiceDataFromDB serviceDataFromDB;
	private SoapHelper soapHelper;
	private SDAHelper sdaHelper;
	private boolean exportBoth = false;
	private String prdMsgType;
	private String csmMsgType;

	public void exportBoth() {
		this.exportBoth = true;
	}

	public void exportOneSide() {
		this.exportBoth = false;
	}

	/**
	 * out of date
	 */
	private String host;
	private List<Namespace> namespaces;
	List<String> attrExclude;
	List<String> attrInculde;
//	private Map<String, Namespace> defaultNamespaces;
	private List<File> defaultInterfaceFiles = null;

	private static final String IN_CONFIG_DIR_PATH = "in_config/metadata";
	private static final String OUT_CONFIG_DIR_PATH = "out_config/metadata";
	private static final String SPDB_REQUEST_PRFIX = "Req";
	private static final String SPDB_RESPONSE_PRFIX = "Rsp";

	private IMetadataNode getFaultNode() {
		IMetadataNode faultNode = new MetadataNode();
		faultNode.setNodeID("Fault");
		IMetadataNode faultCodeNode = new MetadataNode();
		faultCodeNode.setNodeID("faultcode");
		IMetadataNodeAttribute metadataNodeAttribute = new MetadataNodeAttribute();
		faultCodeNode.setProperty(metadataNodeAttribute);
		metadataNodeAttribute.setProperty("metadataid", "faultcode");
		faultNode.addChild(faultCodeNode);
		IMetadataNode faultStringNode = new MetadataNode();
		faultStringNode.setNodeID("faultstring");
		IMetadataNodeAttribute metadataNodeAttribute2 = new MetadataNodeAttribute();
		faultStringNode.setProperty(metadataNodeAttribute2);
		metadataNodeAttribute2.setProperty("metadataid", "faultstring");
		faultStringNode.setProperty(metadataNodeAttribute2);
		faultNode.addChild(faultStringNode);
		return faultNode;
	}

	private void invokeAddFaultNode(IMetadataNode parentNode) {
		IMetadataNode faultNode = getFaultNode();
		parentNode.addChild(faultNode);
	}

	private static final Log log = LogFactory
			.getLog(SpdbSpecDefaultInterfaceGenerater.class);

	public SpdbSpecDefaultInterfaceGenerater() {
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
	}

//	private Namespace getNamespace(String namespaceIdentifer) {
//		return defaultNamespaces.get(namespaceIdentifer);
//	}

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

	/**
	 * generate spdb spec in and out message
	 */
	@Override
	public void generate(String serviceID) {

		this.host = serviceDataFromDB.getWSDLHost(serviceID);
		// initNamespaceAndCheckPrerequiste(host);
		// getNamespacesFromRoot();
		namespaces.add(new Namespace(SoapConstants.NS_PREFIX_SOAP_ENC,
				"http://schemas.xmlsoap.org/soap/encoding/"));
		namespaces
				.add(new Namespace("xsd", "http://www.w3.org/2001/XMLSchema"));
		namespaces.add(new Namespace(SoapConstants.NS_PREFIX_SOAP,
				"http://schemas.xmlsoap.org/soap/envelope/"));
		namespaces.add(new Namespace(SoapConstants.NS_PREFIX_SERVICE, host
				+ "/services/" + serviceID));
		namespaces.add(new Namespace(SoapConstants.NS_PREFIX_METADATA, host
				+ "/metadata"));

		if (null != serviceID) {
			List<String> operations = serviceDataFromDB.getAllSubServiceList(serviceID);
			if (null != operations && operations.size() > 0) {
				for (String operation : operations) {
					MetadataNode defaultIn = defaultInterfaceFetcher
							.findDefaultInterfaceIn(serviceID);
					MetadataNode defaultOut = defaultInterfaceFetcher
							.findDefaultInterfaceOut(serviceID);
					MetadataNode operationNode = serviceDataFromDB.getNodeFromDB(serviceID, operation, true);
					log.info("start to handle operation[" + operation + "]");
					metadataStructHelper.parseMetadataStruct(operationNode);
					try {

						if (null != defaultIn && null != operationNode) {
							log.info("start to generate default consumer config for ["
									+ operation + "]");
							this.generateConsumerSoapXML(
									(MetadataNode) MetadataNodeHelper
											.cloneNode(defaultIn),
									(MetadataNode) MetadataNodeHelper
											.cloneNode(operationNode),
									operation, serviceID);
						}
						if (null != defaultOut && null != operationNode) {
							log.info("start to generate default provider config for ["
									+ operation + "]");
							this.generateProviderSoapXML(
									(MetadataNode) MetadataNodeHelper
											.cloneNode(defaultOut),
									(MetadataNode) MetadataNodeHelper
											.cloneNode(operationNode),
									operation, serviceID);
						}
					} catch (Exception e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}
				}
			}
		}
	}

	public void generateConsumerConfig(String operation,String serviceId) {
//		MetadataNode defaultIn = defaultInterfaceFetcher
//				.findDefaultInterfaceIn(serviceId);
	}

	public void generateProviderConfig() {

	}

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
		if (null != providerUnPachConfigNode) {
			IMetadataNodeAttribute attr = providerUnPachConfigNode
					.getProperty();
			if (null == attr) {
				attr = new MetadataNodeAttribute();
				providerUnPachConfigNode.setProperty(attr);
			}
			attr.setProperty("package_type", "xml");
			IMetadataNode bodyNode = providerUnPachConfigNode
					.getChild(SoapConstants.ELEM_BODY);
			invokeAddFaultNode(bodyNode);
		}
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
		root.setNodeID(SoapConstants.NS_PREFIX_SOAP + ":" + root.getNodeID());
		MetadataNode headerNode = (MetadataNode) root
				.getChild(SoapConstants.ELEM_HEADER);
		headerNode.setNodeID(SoapConstants.NS_PREFIX_SOAP + ":"
				+ headerNode.getNodeID());
		for (IMetadataNode childNode : headerNode.getChild()) {
			if (childNode.hasChild()) {
				addPrefix(childNode, SoapConstants.NS_PREFIX_SERVICE);
			} else {
				addPrefix(childNode, SoapConstants.NS_PREFIX_METADATA);
			}

		}

		MetadataNode bodyNode = (MetadataNode) root
				.getChild(SoapConstants.ELEM_BODY);
		bodyNode.setNodeID(SoapConstants.NS_PREFIX_SOAP + ":"
				+ bodyNode.getNodeID());
		for (IMetadataNode childNode : bodyNode.getChild()) {
			// if (childNode.hasChild()) {
			// addPrefix(childNode, SoapConstants.NS_PREFIX_SERVICE);
			// } else {
			// addPrefix(childNode, SoapConstants.NS_PREFIX_METADATA);
			// }
			addSchemaPrefix(childNode, SoapConstants.NS_PREFIX_SERVICE);
		}
	}

	protected void addSchemaPrefix(IMetadataNode node, String prifix) {
		if ("Fault".equalsIgnoreCase(node.getNodeID())) {
			node.setNodeID(SoapConstants.NS_PREFIX_SOAP + ":"
					+ node.getNodeID());
		} else if ("faultcode".equalsIgnoreCase(node.getNodeID())) {

		} else if ("faultstring".equalsIgnoreCase(node.getNodeID())) {

		} else {
			node.setNodeID(prifix + ":" + node.getNodeID());
		}
		if (node.hasChild()) {
			for (IMetadataNode childNode : node.getChild()) {
				addSchemaPrefix(childNode, SoapConstants.NS_PREFIX_SERVICE);
			}
		}
	}

	protected void addPrefix(IMetadataNode node, String prifix) {

		if ("Fault".equalsIgnoreCase(node.getNodeID())) {
			node.setNodeID(SoapConstants.NS_PREFIX_SOAP + ":"
					+ node.getNodeID());
		} else if ("faultcode".equalsIgnoreCase(node.getNodeID())) {

		} else if ("faultstring".equalsIgnoreCase(node.getNodeID())) {

		} else {
			node.setNodeID(prifix + ":" + node.getNodeID());
		}
		if (node.hasChild()) {
			for (IMetadataNode childNode : node.getChild()) {
				if (childNode.hasChild()) {
					addPrefix(childNode, SoapConstants.NS_PREFIX_SERVICE);
				} else {
					addPrefix(childNode, SoapConstants.NS_PREFIX_METADATA);
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
			String tmpOperationId = handleInterfaceIdForDupTrade(operationId);
			if (type.equalsIgnoreCase(SDAConstants.REQUEST)) {
				operationNode.setNodeID(SPDB_REQUEST_PRFIX + tmpOperationId);
				bodyNode.addChild(operationNode);
			} else if (type.equalsIgnoreCase(SDAConstants.RESPONSE)) {
				operationNode.setNodeID(SPDB_RESPONSE_PRFIX + tmpOperationId);
				bodyNode.addChild(operationNode);
				// 20130110 assign to Vincent Fan , zhanghe
				// bodyNode.addChild(getFaultNode());
			}
			MetadataNodeHelper.copyChild(serviceReqRspNode, operationNode);
		}
		return configNode;
	}
	

	/**
	 * 创建soap fault节点
	 */

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
		log.info("start generate consumer config using service: [" + serviceId
				+ "] operation: [" + operationId + "]");
		// getNamespacesFromRoot(defaultInterfaceNode);
		String reqSoapFileName = "channel_default_in_service_" + serviceId
				+ operationId + ".xml";
		String respSoapFileName = "service_" + serviceId + operationId
				+ "_system_default_in.xml";
		generateConsumerSoapXML(defaultInterfaceNode, serviceNode, operationId,
				serviceId, reqSoapFileName, respSoapFileName);

	}

	protected void generateConsumerSoapXML(MetadataNode defaultInterfaceNode,
			MetadataNode serviceNode, String operationId, String serviceId,
			String reqFileName, String rspFileName) throws Exception {
		// TODO remove this when the dup operationId problem be resolved
		String tmpOperationId = handleInterfaceIdForDupTrade(operationId);
		String dir = serviceId + tmpOperationId +"(" + csmMsgType + "-"
		+ prdMsgType + ")" + File.separator
				+ IN_CONFIG_DIR_PATH;
		File inConfigDir = new File(dir);
		if (!inConfigDir.exists()) {
			inConfigDir.mkdirs();
		}
		defaultInterfaceFiles.add(inConfigDir);
		MetadataNode consumerUnPackNode = this
				.createConsumerUnPackerConfigNode(defaultInterfaceNode,
						serviceNode, operationId);
		Document consumerUnPackDocument = MetadataNodeHelper
				.MetadataNode2DocumentWithInclude(consumerUnPackNode, null,
						attrInculde);

		String consumerReqSoapXML = this.documentToXML(consumerUnPackDocument);
		reqFileName = dir + File.separator + reqFileName;
		File reqSoapFile = new File(reqFileName);
		if (!reqSoapFile.exists()) {
			log.info("create file [" + reqSoapFile.getAbsolutePath() + "]");
			reqSoapFile.createNewFile();
		} else {
			log.error(reqSoapFile.getAbsoluteFile() + " must not be exist!");
		}
		this.saveXMLFile(reqSoapFile, consumerReqSoapXML);
		// 组包需要命名空间
		MetadataNode consumerPackNode = this.createConsumerPackerConfigNode(
				defaultInterfaceNode, serviceNode, operationId);
		Document consumerPackDocument = MetadataNodeHelper
				.MetadataNode2DocumentWithInclude(consumerPackNode, namespaces,
						attrInculde);

		String consumerRespSoapXML = this.documentToXML(consumerPackDocument);

		rspFileName = dir + File.separator + rspFileName;
		File respSoapFile = new File(rspFileName);
		if (!respSoapFile.exists()) {
			log.info("create file [" + respSoapFile.getAbsolutePath() + "]");
			respSoapFile.createNewFile();
		} else {
			log.error(respSoapFile.getName() + " must not be exist!");
		}
		XMLHelper.saveXMLFile(respSoapFile, consumerRespSoapXML);
	}

	protected void generateProviderSoapXML(MetadataNode defaultInterfaceNode,
			MetadataNode serviceNode, String operationId, String serviceId)
			throws Exception {
		getNamespacesFromRoot(defaultInterfaceNode);
		String respSoapFileName = "channel_default_out_service_" + serviceId
				+ operationId + ".xml";
		String reqSoapFileName = "service_" + serviceId + operationId
				+ "_system_default_out.xml";
		generateConsumerSoapXML(defaultInterfaceNode, serviceNode, operationId,
				serviceId, reqSoapFileName, respSoapFileName);

	}

	protected void generateProviderSoapXML(MetadataNode defaultInterfaceNode,
			MetadataNode serviceNode, String operationId, String serviceId,
			String reqFileName, String rspFileName) throws Exception {
		// TODO remove this when the dup operationId problem be resolved
		String tmpOperationId = handleInterfaceIdForDupTrade(operationId);
		String dir = serviceId + tmpOperationId + "(" + csmMsgType + "-"
		+ prdMsgType + ")" + File.separator
				+ OUT_CONFIG_DIR_PATH;
		File outConfigDir = new File(dir);
		if (!outConfigDir.exists()) {
			outConfigDir.mkdirs();
		}
		this.defaultInterfaceFiles.add(outConfigDir);

		// 不需要前缀的节点在前生成
		MetadataNode providerUnPackNode = createProviderUnPackConfigNode(
				defaultInterfaceNode, serviceNode, operationId);

		Document providerUnPackDocument = MetadataNodeHelper
				.MetadataNode2DocumentWithInclude(providerUnPackNode, null,
						attrInculde);
		String providerRespSoapXML = this.documentToXML(providerUnPackDocument);
		rspFileName = dir + File.separator + rspFileName;
		File respSoapFile = new File(rspFileName);
		if (!respSoapFile.exists()) {
			log.info("create file [" + respSoapFile.getAbsolutePath() + "]");
			respSoapFile.createNewFile();
		} else {
			log.error(respSoapFile.getName() + " must not be exist!");
		}
		this.saveXMLFile(respSoapFile, providerRespSoapXML);

		// 组包需要命名空间
		MetadataNode providerPackNode = createProviderPackConfigNode(
				defaultInterfaceNode, serviceNode, operationId);
		Document providerPackDocument = MetadataNodeHelper
				.MetadataNode2DocumentWithInclude(providerPackNode, namespaces,
						attrInculde);
		String providerReqSoapXML = this.documentToXML(providerPackDocument);

		reqFileName = dir + File.separator + reqFileName;
		File reqSoapFile = new File(reqFileName);
		if (!reqSoapFile.exists()) {
			log.info("create file [" + reqSoapFile.getAbsolutePath() + "]");
			reqSoapFile.createNewFile();
		} else {
			log.error(reqSoapFile.getName() + " must not be exist!");
		}
		this.saveXMLFile(reqSoapFile, providerReqSoapXML);
	}

	public List<File> getDefaultInterfaceFiles() {
		return this.defaultInterfaceFiles;
	}

	/**
	 * @author Vincent Fan 处理 多只交易共用交易码的情况下，InterfaceId增加了区分码的做法。 将区分码(由
	 *         "-"分隔)去除
	 */
	private String handleInterfaceIdForDupTrade(String interfaceId) {
		String tmpId = interfaceId;
		if (interfaceId.indexOf("-") > -1) {
			tmpId = interfaceId.substring(0, interfaceId.indexOf("-"));
		}
		return tmpId;
	}

	@Override
	public List<File> generate(InvokeInfo invokeInfo) throws Exception {
		this.prdMsgType = invokeInfo.getProvideMsgType();
		this.csmMsgType = invokeInfo.getConsumeMsgType();
		defaultInterfaceFiles = new ArrayList<File>();
		namespaces = new ArrayList<Namespace>();
		String operationId = invokeInfo.getOperationId();
		log.info("[配置生成]：场景ID[" + operationId + "]");
		String interfaceId = invokeInfo.getEcode();
		log.info("[配置生成]：接口ID[" + interfaceId + "]");
		String interfaceType = invokeInfo.getDirection();
		log.info("[配置生成]：接口类型ID[" + interfaceType + "]");
		String sysId = invokeInfo.getProvideSysId();
		if (null != sysId) {
			sysId = sysId.trim();
		}
		log.info("[配置生成]：系统名称[" + sysId + "]");
//        具有wsdl_host属性的服务
//		String serviceId = packerUnPackerConfigHelper
//				.getServiceIdByOperationId(operationId);
		String serviceId = invokeInfo.getServiceId();
		log.info("[配置生成]：服务ID[" + serviceId + "]");
		this.host = serviceDataFromDB.getWSDLHost(serviceId);
		log.info("[配置生成]：服务ID[" + this.host + "]");

		namespaces.add(new Namespace(SoapConstants.NS_PREFIX_SOAP_ENC,
				"http://schemas.xmlsoap.org/soap/encoding/"));
		namespaces
				.add(new Namespace("xsd", "http://www.w3.org/2001/XMLSchema"));
		namespaces.add(new Namespace(SoapConstants.NS_PREFIX_SOAP,
				"http://schemas.xmlsoap.org/soap/envelope/"));
		namespaces.add(new Namespace(SoapConstants.NS_PREFIX_SERVICE, host
				+ "/services/" + serviceId));
		namespaces.add(new Namespace(SoapConstants.NS_PREFIX_METADATA, host
				+ "/metadata"));

		MetadataNode operationNode = serviceDataFromDB.getNodeFromDB(invokeInfo.getServiceId(), operationId, true);
		log.info("start to handle operation[" + operationId + "]");
		metadataStructHelper.parseMetadataStruct(operationNode);
		// TODO remove this when the dup operation id issue be resolved
		String tmpOperationId = handleInterfaceIdForDupTrade(operationId);
		if (exportBoth) {
			log.info("[标准配置生成]:开始生成调用方及提供方标准配置");
			MetadataNode defaultIn = defaultInterfaceFetcher
					.findDefaultInterfaceIn(serviceId);
			String reqFileName = "channel_soap_service_" + serviceId
					+ tmpOperationId + ".xml";
			String rspFileName = "service_" + serviceId + tmpOperationId
					+ "_system_soap.xml";
			if (null != defaultIn && null != operationNode) {
				log.info("start to generate default consumer config for ["
						+ operationId + "]");
				this.generateConsumerSoapXML((MetadataNode) MetadataNodeHelper
						.cloneNode(defaultIn),
						(MetadataNode) MetadataNodeHelper
								.cloneNode(operationNode), operationId,
						serviceId, reqFileName, rspFileName);
			}

			reqFileName = "service_" + serviceId + tmpOperationId + "_system_"
					+ sysId.toLowerCase() + "System.xml";
			rspFileName = "channel_" + sysId.toLowerCase() + "System_service_"
					+ serviceId + tmpOperationId + ".xml";
			MetadataNode defaultOut = defaultInterfaceFetcher
					.findDefaultInterfaceOut(serviceId);
			if (null != defaultOut && null != operationNode) {
				log.info("start to generate default provider config for ["
						+ operationId + "]");
				this.generateProviderSoapXML((MetadataNode) MetadataNodeHelper
						.cloneNode(defaultOut),
						(MetadataNode) MetadataNodeHelper
								.cloneNode(operationNode), operationId,
						serviceId, reqFileName, rspFileName);
			}
			this.exportBoth = false;
		} else {
			if (CONSUMER.equalsIgnoreCase(interfaceType)) {
				log.info("[标准配置生成]:开始生成调用方标准配置");
				MetadataNode defaultIn = defaultInterfaceFetcher
						.findDefaultInterfaceIn(serviceId);
				String reqFileName = "channel_soap_service_" + serviceId
						+ tmpOperationId + ".xml";
				String rspFileName = "service_" + serviceId + tmpOperationId
						+ "_system_soap.xml";
				if (null != defaultIn && null != operationNode) {
					log.info("start to generate default consumer config for ["
							+ operationId + "]");
					this.generateConsumerSoapXML(
							(MetadataNode) MetadataNodeHelper
									.cloneNode(defaultIn),
							(MetadataNode) MetadataNodeHelper
									.cloneNode(operationNode), operationId,
							serviceId, reqFileName, rspFileName);
				}

			} else if (PROVIDER.equalsIgnoreCase(interfaceType)) {
				log.info("[标准配置生成]:开始生成提供方标准配置");
				String reqFileName = "service_" + serviceId + tmpOperationId
						+ "_system_" + sysId.toLowerCase() + "System.xml";
				String rspFileName = "channel_" + sysId.toLowerCase()
						+ "System_service_" + serviceId + tmpOperationId
						+ ".xml";
				MetadataNode defaultOut = defaultInterfaceFetcher
						.findDefaultInterfaceOut(serviceId);
				if (null != defaultOut && null != operationNode) {
					log.info("start to generate default provider config for ["
							+ operationId + "]");
					this.generateProviderSoapXML(
							(MetadataNode) MetadataNodeHelper
									.cloneNode(defaultOut),
							(MetadataNode) MetadataNodeHelper
									.cloneNode(operationNode), operationId,
							serviceId, reqFileName, rspFileName);
				}
			}
		}
		return this.defaultInterfaceFiles;
	}

}
