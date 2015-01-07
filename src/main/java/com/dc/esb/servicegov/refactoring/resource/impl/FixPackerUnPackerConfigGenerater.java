package com.dc.esb.servicegov.refactoring.resource.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
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
import com.dc.esb.servicegov.refactoring.resource.metadataNode.XMLHelper;
import com.dc.esb.servicegov.refactoring.util.SDAConstants;
import static com.dc.esb.servicegov.refactoring.util.PackerUnPackerConstants.*;

@Service
public class FixPackerUnPackerConfigGenerater implements
		IConfigGenerater<InvokeInfo, List<File>> {

	private static final Log log = LogFactory
			.getLog(FixPackerUnPackerConfigGenerater.class);

	private static final List<String> include_attr;
	static {
		include_attr = new ArrayList<String>();
		include_attr.add("type=array");
		include_attr.add("type=string");
		include_attr.add("type=selfjoinarray");
		include_attr.add("type=selfjoinchild");
		include_attr.add("is_struct");
		include_attr.add("metadataid");
		include_attr.add("package_type");
		include_attr.add("length");
		include_attr.add("expression");
		include_attr.add("isSdoHeader");
		include_attr.add("searchField");
		include_attr.add("bytelength");
		include_attr.add("name");
		include_attr.add("store-mode");
		include_attr.add("successValue");
		include_attr.add("align");
		include_attr.add("fill_char");

	}

	@Autowired
	private PackerUnPackerConfigHelper packerUnPackerConfigHelper;
	@Autowired
	private XMPassedInterfaceDataFromDB xmPassedInterfaceDataFromDB;
	@Autowired
	private SpdbSpecDefaultInterfaceGenerater spdbSpecDefaultInterfaceGenerater;
	private List<File> targetFiles = null;
	@Override
	public List<File> generate(InvokeInfo invokeInfo) throws Exception {
		targetFiles = new ArrayList<File>();
		String interfaceType = invokeInfo.getDirection();
		if (CONSUMER.equalsIgnoreCase(interfaceType)) {
			generateConsumerConfig(invokeInfo);
			invokeInfo.setDirection(PROVIDER);
		} else if (PROVIDER.equalsIgnoreCase(interfaceType)) {
			generateProviderConfig(invokeInfo);
			invokeInfo.setDirection(CONSUMER);
		}
		List<File> files = spdbSpecDefaultInterfaceGenerater.generate(invokeInfo);
		targetFiles.addAll(files);
		return targetFiles;
	}

	private void generateConsumerConfig(InvokeInfo invokeInfo) {

		String operationId = invokeInfo.getOperationId();
		String interfaceId = invokeInfo.getEcode();
		String interfaceMSGType = "1".equals(invokeInfo.getDirection())?invokeInfo.getProvideMsgType():invokeInfo.getConsumeMsgType();
		String serviceId = packerUnPackerConfigHelper
				.getServiceIdByOperationId(operationId);

		List<IMetadataNode> templateInterfaceNodes = getTemplateInterfaceNodes(interfaceId);

		for (IMetadataNode templateInterfaceNode : templateInterfaceNodes) {
			String configPath = serviceId + operationId +"(" + invokeInfo.getConsumeMsgType() + "-"
			+ invokeInfo.getProvideMsgType() + ")" +  File.separator
					+ IN_CONF_DIR;
			File dir = new File(configPath);
			dir.deleteOnExit();
			dir.mkdirs();
			targetFiles.add(dir);

			String reqConfigFileName = configPath + File.separator + "channel_"
					+ interfaceMSGType + "_service_" + serviceId + operationId
					+ ".xml";

			String rspConfigFileName = configPath + File.separator + "service_"
					+ serviceId + operationId + "_system_" + interfaceMSGType
					+ ".xml";
			IMetadataNode interfaceNode = xmPassedInterfaceDataFromDB
					.getNodeFromDB(interfaceId, ResourceType.INTERFACE);
			IMetadataNode reqNode = interfaceNode.getChild(REQ_LABEL);
			IMetadataNode rspNode = interfaceNode.getChild(RSP_LABEL);

			/*
			 * reqNode.setNodeID("root"); rspNode.setNodeID("root");
			 */

			invokeAddPackageType(reqNode, interfaceMSGType.toLowerCase());
			invokeAddPackageType(rspNode, interfaceMSGType.toLowerCase());

			try {
				createReqConfigFile(reqNode, templateInterfaceNode,
						reqConfigFileName, interfaceId);
				createRspConfigFile(rspNode, templateInterfaceNode,
						rspConfigFileName);
			} catch (Exception e) {
				log
						.error("[Fix Config Generater]:Fail to create file, req node : ["
								+ reqNode + "], rsp node : [" + rspNode + "]");

			}

		}

	}

	private void generateProviderConfig(InvokeInfo invokeInfo) {

		String operationId = invokeInfo.getOperationId();
		String interfaceId = invokeInfo.getEcode();
		String interfaceType = invokeInfo.getDirection();
		String interfaceMSGType = "1".equals(invokeInfo.getDirection())?invokeInfo.getProvideMsgType():invokeInfo.getConsumeMsgType();
		String sysId = invokeInfo.getProvideSysId();
		String serviceId = packerUnPackerConfigHelper
				.getServiceIdByOperationId(operationId);

		List<IMetadataNode> templateInterfaceNodes = getTemplateInterfaceNodes(interfaceId);

		for (IMetadataNode templateInterfaceNode : templateInterfaceNodes) {

			String configPath = serviceId + operationId +"(" + invokeInfo.getConsumeMsgType() + "-"
			+ invokeInfo.getProvideMsgType() + ")" + File.separator
					+ OUT_CONF_DIR;
			File dir = new File(configPath);
			dir.deleteOnExit();
			dir.mkdirs();
			targetFiles.add(dir);

			String reqConfigFileName = configPath + File.separator + "service_"
					+ serviceId + operationId + "_system_"
					+ sysId.toLowerCase() + "System.xml";

			String rspConfigFileName = configPath + File.separator + "channel_"
					+ sysId.toLowerCase() + "System_service_" + serviceId
					+ operationId + ".xml";

			IMetadataNode interfaceNode = xmPassedInterfaceDataFromDB
					.getNodeFromDB(interfaceId, ResourceType.INTERFACE);
			IMetadataNode reqNode = interfaceNode.getChild(REQ_LABEL);
			invokeParseExpression(reqNode, "request", interfaceType);
			IMetadataNode rspNode = interfaceNode.getChild(RSP_LABEL);
			invokeParseExpression(rspNode, "response", interfaceType);

			// reqNode.setNodeID("root");
			// rspNode.setNodeID("root");

			invokeAddPackageType(reqNode, interfaceMSGType.toLowerCase());
			invokeAddPackageType(rspNode, interfaceMSGType.toLowerCase());

			try {
				createReqConfigFile(interfaceNode, templateInterfaceNode,
						reqConfigFileName, interfaceId);
				createRspConfigFile(interfaceNode, templateInterfaceNode,
						rspConfigFileName);
			} catch (Exception e) {
				log.error(
						"[Fix Config Generater]:Fail to create file, req node : ["
								+ reqNode + "], rsp node : [" + rspNode + "]",
						e);

			}
		}
	}

	private void getNodePath(StringBuilder sb, IMetadataNode node) {
		if (null != node && null != sb) {
			IMetadataNode parentNode = node.getParentNode();
			if (null != parentNode) {
				if (!"root".equalsIgnoreCase(parentNode.getNodeID())
						&& !"request".equalsIgnoreCase(parentNode.getNodeID())
						&& !"response".equalsIgnoreCase(parentNode.getNodeID())) {
					getNodePath(sb, parentNode);
				}
			}
			sb.append(node.getNodeID());
			sb.append("/");
		}
	}

	private void invokeParseExpression(IMetadataNode node, String direction,
			String interfaceType) {

		// while(node.getParentNode()!=null){
		// String nodeId = node.getParentNode().getNodeID();
		// if("root".equals(nodeId)){
		// path = "/"+nodeId + path ;
		// break;
		// }else{
		// path = "/" + nodeId + "[*]" + path;
		// }
		// }

		if (null != node) {
			if (node.hasChild()) {
				for (IMetadataNode child : node.getChild()) {
					invokeParseExpression(child, direction, interfaceType);
				}
			} else {
				IMetadataNodeAttribute attr = node.getProperty();
				if (null != attr) {
					String expression = attr.getProperty("expression");
					if (null != expression) {
						StringBuilder pathSBuilder = new StringBuilder();
						getNodePath(pathSBuilder, node);

						String path = pathSBuilder.toString();
						String[] temp = path.split("/");
						String pathString = "";
						if (temp.length >= 2) {
							for (int i = 0; i < temp.length - 1; i++) {
								temp[i] = temp[i] + "[*]";
								pathString = "/" + temp[i];
							}
							pathString += "/" + temp[temp.length - 1];
						} else {
							pathString = path;
						}
						pathString = pathString.substring(0, pathString
								.length() - 1);
						log.debug("node path[" + path + "]");
						if (expression.startsWith("$codeConvert")) {
							StringBuilder parsedExpression = new StringBuilder();
							Map<String, String> args = getArgsFromExpression(expression);
							if (direction.equalsIgnoreCase("request")) {
								if (PROVIDER.equalsIgnoreCase(interfaceType)) {
									parsedExpression
											.append("ff:getSlaveCodeValue('");
								} else if (CONSUMER
										.equalsIgnoreCase(interfaceType)) {
									parsedExpression
											.append("ff:getMasterCodeValue('");
								}
							} else if (direction.equalsIgnoreCase("response")) {
								if (PROVIDER.equalsIgnoreCase(interfaceType)) {
									parsedExpression
											.append("ff:getMasterCodeValue('");
								} else if (CONSUMER
										.equalsIgnoreCase(interfaceType)) {
									parsedExpression
											.append("ff:getSlaveCodeValue('");
								}
							}
							parsedExpression.append(args.get("masterCode"));
							parsedExpression.append("','");
							parsedExpression.append(args.get("slaveCode"));
							parsedExpression.append("',${");
//							String serviceNodePath = args
//									.get("serviceNodePath");
//							String[] pathNodes = serviceNodePath.split(".");
//							String rawPath = args.get("serviceNodePath");
							// if (null != rawPath) {
							// parsedExpression
							// .append(invokeParseExpressionPath(rawPath));
							//
							// }
							if (null != pathString) {
								parsedExpression
										.append(invokeParseExpressionPath("/root/"
												+ pathString));

							}
							parsedExpression.append("},'','");
							parsedExpression.append(args.get("whateverItisa1"));
							parsedExpression.append("')");
							attr.remove("expression");
							attr.setProperty("expression", parsedExpression
									.toString());
						}
					}
				}
			}
		}
	}

	// add sdoroot and [*]
	private String invokeParseExpressionPath(String rawPath) {
		log.info("read expression raw path [" + rawPath + "]");
		String[] pathNodes = rawPath.split("\\.");
		if (pathNodes.length > 1) {
			for (int i = 1; i < pathNodes.length - 1; i++) {
				pathNodes[i] = pathNodes[i] + "[*]/";
				log.info("add [*]/sdo to " + pathNodes[i] + "");
			}
		}
		StringBuilder sb = new StringBuilder();
		for (String node : pathNodes) {
			sb.append(node);
			sb.append("/");
		}
		// 去掉最后的/
		String expressionPath = sb.substring(0, sb.length() - 1);
		return expressionPath;
	}

	private Map<String, String> getArgsFromExpression(String expression) {
		Map<String, String> argMap = null;
		if (null != expression) {
			expression = expression.trim();
			expression = expression.replace("（", "(");
			expression = expression.replace("）", ")");
			expression = expression.replace("，", ",");
			int left = expression.indexOf("(");
			int right = expression.indexOf(")");
			if (left > 0 && right > 0 && right > left) {
				String argstring = expression.substring(left + 1, right);
//				StringTokenizer stringTokenizer = new StringTokenizer(
//						argstring, ",");
				String[] argsStrings = argstring.split(",");
				if (null != argsStrings) {
					argMap = new HashMap<String, String>();
//					String token = null;
					for (String arg : argsStrings) {
						StringTokenizer argTokenizer = new StringTokenizer(arg,
								":");
						argMap.put(argTokenizer.nextToken(), argTokenizer
								.nextToken());
					}
				}
			}
		}
		return argMap;
	}

	private File createReqConfigFile(IMetadataNode configNode,
			IMetadataNode templateNode, String filePath, String interfaceId)
			throws Exception {

		IMetadataNode ConfigNode = createConfigNode(MetadataNodeHelper
				.cloneNode(templateNode), MetadataNodeHelper
				.cloneNode(configNode), SDAConstants.REQUEST);

		// TranCode
		IMetadataNode node_trancode = ConfigNode.getNodeByPath("root.TranCode");
		IMetadataNodeAttribute attr_trancode = node_trancode.getProperty();
		attr_trancode.setProperty("expression", "'" + interfaceId + "'");

		// ReturnCode
		IMetadataNode node_returncode = ConfigNode
				.getNodeByPath("root.ReturnCode");
		IMetadataNodeAttribute attr_returncode = node_returncode.getProperty();
		attr_returncode.setProperty("isSdoHeader", "true");
		if (node_returncode.hasAttribute("type")) {
			attr_returncode.remove("type");
			attr_returncode.setProperty("type", "string");
		} else {
			attr_returncode.setProperty("type", "string");
		}
		attr_returncode.setProperty("expression", "'      '");

		// BranchId
		IMetadataNode node_branchid = ConfigNode.getNodeByPath("root.BranchId");
		IMetadataNodeAttribute attr_branchid = node_branchid.getProperty();
		String length = attr_branchid.getProperty("length");
		String spaceString = "";
		for (int i = 0; i < Integer.parseInt(length); i++) {
			spaceString = spaceString + "0";

		}

		if (node_branchid.hasAttribute("align")) {
			attr_branchid.remove("align");
			attr_branchid.setProperty("align", "true");
		} else {
			attr_branchid.setProperty("align", "true");
		}
		if (node_branchid.hasAttribute("fill_char")) {
			attr_branchid.remove("fill_char");
			attr_branchid.setProperty("fill_char", spaceString);
		} else {
			attr_branchid.setProperty("fill_char", spaceString);
		}

		// pwd
		IMetadataNode node_pwd = ConfigNode.getNodeByPath("root.Pwd");
		IMetadataNodeAttribute attr_pwd = node_pwd.getProperty();
		if (node_pwd.hasAttribute("expression")) {
			attr_pwd.remove("expression");
			attr_pwd
					.setProperty("expression",
							"com.spdbank.esb.container.service.util.ESBFunction.transPIN(sdo)");
		} else {
			attr_pwd
					.setProperty("expression",
							"com.spdbank.esb.container.service.util.ESBFunction.transPIN(sdo)");
		}

		File configFile = new File(filePath);
		if (!configFile.exists()) {
			configFile.createNewFile();
		}
		Document document = MetadataNodeHelper
				.MetadataNode2DocumentWithInclude(ConfigNode, null,
						include_attr);
		String content = XMLHelper.documentToXML(document);
		XMLHelper.saveXMLFile(configFile, content);
		return configFile;
	}

	private File createRspConfigFile(IMetadataNode configNode,
			IMetadataNode templateNode, String filePath) throws Exception {

		IMetadataNode ConfigNode = createConfigNode(MetadataNodeHelper
				.cloneNode(templateNode), MetadataNodeHelper
				.cloneNode(configNode), SDAConstants.RESPONSE);

		File configFile = new File(filePath);
		if (!configFile.exists()) {
			configFile.createNewFile();
		}
		Document document = MetadataNodeHelper
				.MetadataNode2DocumentWithInclude(ConfigNode, null,
						include_attr);
		String content = XMLHelper.documentToXML(document);
		XMLHelper.saveXMLFile(configFile, content);
		return configFile;
	}

	private void invokeAddPackageType(IMetadataNode node, String packageType) {
		if (null != node) {
			IMetadataNodeAttribute attr = node.getProperty();
			if (null == attr) {
				attr = new MetadataNodeAttribute();
			}
			attr.setProperty("package_type", packageType);
		}
	}

	public List<IMetadataNode> getTemplateInterfaceNodes(String interfaceId) {
		List<IMetadataNode> templates = new ArrayList<IMetadataNode>();
		List<String> parentInterfaceIds = xmPassedInterfaceDataFromDB
				.getParentNodeIds(interfaceId);
		for (String parentId : parentInterfaceIds) {
			if (isExportAbleInterface(parentId)) {
				templates.add(xmPassedInterfaceDataFromDB.getNodeFromDB(
						parentId, ResourceType.INTERFACE));
			}
		}
		return templates;
	}

	/**
	 * 
	 * @param interfaceId
	 * @return
	 */
	public boolean isExportAbleInterface(String interfaceId) {
		MetadataNode interfaceNode = xmPassedInterfaceDataFromDB.getNodeFromDB(
				interfaceId, ResourceType.INTERFACE);
		String exportFlag = interfaceNode.getProperty().getProperty(
				"exportable");
		log.info(interfaceId + "是否可以导出：" + exportFlag);
		if (null != exportFlag) {
			if ("true".equalsIgnoreCase(exportFlag)) {
				return true;
			}
		}
		return true;
	}

	public IMetadataNode createConfigNode(IMetadataNode templateNode,
			IMetadataNode interfaceNode, String type) {
		String importInToNodeName = getImportIntoName(interfaceNode);
		if (null != importInToNodeName) {
			IMetadataNode targetNode = templateNode.getNodeByPath(type + "."
					+ importInToNodeName);
			IMetadataNode sourceNode = interfaceNode.getChild(type);
			if (null != targetNode && null != sourceNode) {
				if (sourceNode.hasChild()) {
					MetadataNodeHelper.copyChild(interfaceNode.getChild(type),
							targetNode);
				}
			} else {
				templateNode.getChild(type).remove(targetNode);
			}
		}
		List<IMetadataNode> targetNodes = templateNode.getChild(type)
				.getChild();
		if (targetNodes.size() != 1) {
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return targetNodes.get(0);
	}

	private String getImportIntoName(IMetadataNode interfaceNode) {
		return interfaceNode.getProperty().getProperty("import_into");
	}

	/**
	 * eg.
	 * 
	 * 修改属性值的方法
	 * 
	 * @param node
	 * @param interfaceId
	 */
//	private void handleHeader(IMetadataNode node, String interfaceId,
//			String interfaceType) {
//		IMetadataNode reqTranCodeNode = node
//				.getNodeByPath("request.root.TRAN_HEAD.JIAOYM");
//		if (null != reqTranCodeNode) {
//			IMetadataNodeAttribute attribute = reqTranCodeNode.getProperty();
//			if (null == attribute) {
//				attribute = new MetadataNodeAttribute();
//				reqTranCodeNode.setProperty(attribute);
//			}
//			if (interfaceType
//					.equalsIgnoreCase(PackerUnPackerConstants.PROVIDER)) {
//				attribute.setProperty("expression", "'" + interfaceId + "'");
//			}
//		}
//		IMetadataNode rspTranCodeNode = node
//				.getNodeByPath("response.root.CMTRAN_RCV_HEAD.JIAOYM");
//		if (null != rspTranCodeNode) {
//			IMetadataNodeAttribute attribute = rspTranCodeNode.getProperty();
//			if (null == attribute) {
//				attribute = new MetadataNodeAttribute();
//				rspTranCodeNode.setProperty(attribute);
//			}
//			if (interfaceType
//					.equalsIgnoreCase(PackerUnPackerConstants.CONSUMER)) {
//				attribute.setProperty("expression", "'" + interfaceId + "'");
//			}
//		}
//	}
}
