package com.dc.esb.servicegov.resource.impl;

import static com.dc.esb.servicegov.util.PackerUnPackerConstants.CONSUMER;
import static com.dc.esb.servicegov.util.PackerUnPackerConstants.IN_CONF_DIR;
import static com.dc.esb.servicegov.util.PackerUnPackerConstants.OUT_CONF_DIR;
import static com.dc.esb.servicegov.util.PackerUnPackerConstants.PROVIDER;
import static com.dc.esb.servicegov.util.PackerUnPackerConstants.REQ_LABEL;
import static com.dc.esb.servicegov.util.PackerUnPackerConstants.RSP_LABEL;
import static com.dc.esb.servicegov.util.PackerUnPackerConstants.SVCBODY_LABEL;

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

import com.dc.esb.servicegov.entity.InvokeInfo;
import com.dc.esb.servicegov.resource.IConfigGenerater;
import com.dc.esb.servicegov.resource.metadataNode.FixMetadataNodeHelper;
import com.dc.esb.servicegov.resource.metadataNode.IMetadataNode;
import com.dc.esb.servicegov.resource.metadataNode.IMetadataNodeAttribute;
import com.dc.esb.servicegov.resource.metadataNode.XMLHelper;

@Service
public class FixPackerUnPackerConfigGenerater implements
		IConfigGenerater<InvokeInfo, List<File>> {

	private static final Log log = LogFactory
			.getLog(FixPackerUnPackerConfigGenerater.class);

	private static final List<String> include_attr;
	static {
		include_attr = new ArrayList<String>();
		include_attr.add("type=array");
		// String 参数不需要
		include_attr.add("type=string");
		include_attr.add("type=double");
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
		include_attr.add("size");
		include_attr.add("scale");
	}

	@Autowired
	private PackerUnPackerConfigHelper packerUnPackerConfigHelper;
	@Autowired
	private SpdbSpecDefaultInterfaceGenerater spdbSpecDefaultInterfaceGenerater;
	@Autowired
	private XMPassedInterfaceDataFromDB interfaceDataFromDB;
	
	private FixMetadataNodeHelper fixMetadataNodeHelper = FixMetadataNodeHelper.getInstance();
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
//		if()
		List<File> files = spdbSpecDefaultInterfaceGenerater
				.generate(invokeInfo);
		targetFiles.addAll(files);
		return targetFiles;
	}

	private void generateConsumerConfig(InvokeInfo invokeInfo) {

		String operationId = invokeInfo.getOperationId();
		String interfaceId = invokeInfo.getEcode();
		String interfaceMSGType = "1".equals(invokeInfo.getDirection()) ? invokeInfo
				.getProvideMsgType()
				: invokeInfo.getConsumeMsgType();
		String serviceId = packerUnPackerConfigHelper
				.getServiceIdByOperationId(operationId);

		// 获取服务操作的IMetadataNode信息
		IMetadataNode metadataNode = interfaceDataFromDB.getInterfaceData(interfaceId);
		String configPath = serviceId + operationId + "("
				+ invokeInfo.getConsumeMsgType() + "-"
				+ invokeInfo.getProvideMsgType() + ")" + File.separator
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

		IMetadataNode reqNode = metadataNode.getChild(REQ_LABEL);
		IMetadataNode rspNode = metadataNode.getChild(RSP_LABEL);
		if(reqNode.hasChild("SVCBODY_LABEL")){
			reqNode = reqNode.getChild(SVCBODY_LABEL);
		}
		if(rspNode.hasChild("SVCBODY_LABEL")){
			rspNode = rspNode.getChild(SVCBODY_LABEL);
		}
		try {
			createReqConfigFile(reqNode, reqConfigFileName, interfaceId);
			createRspConfigFile(rspNode, rspConfigFileName, interfaceId);
		} catch (Exception e) {
			log
					.error("[Fix Config Generater]:Fail to create file, req node : ["
							+ reqNode + "], rsp node : [" + rspNode + "]");

		}

	}

	private void generateProviderConfig(InvokeInfo invokeInfo) {
		String operationId = invokeInfo.getOperationId();
		String interfaceId = invokeInfo.getEcode();
		String interfaceType = invokeInfo.getDirection();
		String sysId = invokeInfo.getProvideSysId();
		String serviceId = invokeInfo.getServiceId();
		// 获取服务操作的IMetadataNode信息
		IMetadataNode metadataNode = interfaceDataFromDB.getInterfaceData(interfaceId);
		String configPath = serviceId + operationId + "("
				+ invokeInfo.getConsumeMsgType() + "-"
				+ invokeInfo.getProvideMsgType() + ")" + File.separator
				+ OUT_CONF_DIR;
		File dir = new File(configPath);
		dir.deleteOnExit();
		dir.mkdirs();
		targetFiles.add(dir);

		String reqConfigFileName = configPath + File.separator + "service_"
				+ serviceId + operationId + "_system_" + sysId
				+ ".xml";

		String rspConfigFileName = configPath + File.separator + "channel_"
				+ sysId + "_service_" + serviceId
				+ operationId + ".xml";

		IMetadataNode reqNode = metadataNode.getChild(REQ_LABEL);
		//暂时不处理表达式
//		invokeParseExpression(reqNode, "request", interfaceType);
		IMetadataNode rspNode = metadataNode.getChild(RSP_LABEL);
		invokeParseExpression(rspNode, "response", interfaceType);
		if(reqNode.hasChild("SVCBODY_LABEL")){
			reqNode = reqNode.getChild(SVCBODY_LABEL);
		}
		if(rspNode.hasChild("SVCBODY_LABEL")){
			rspNode = rspNode.getChild(SVCBODY_LABEL);
		}
		try {
			// 去除SvcBody节点
			createReqConfigFile(reqNode, reqConfigFileName, interfaceId);
			// 去除SvcBody节点
			createRspConfigFile(rspNode, rspConfigFileName, interfaceId);
		} catch (Exception e) {
			log.error(
					"[Fix Config Generater]:Fail to create file, req node : ["
							+ reqNode + "], rsp node : [" + rspNode + "]", e);

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
				String[] argsStrings = argstring.split(",");
				if (null != argsStrings) {
					argMap = new HashMap<String, String>();
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

	private File createReqConfigFile(IMetadataNode configNode, String filePath, String interfaceId) throws Exception {
		
		File configFile = new File(filePath);
		if (!configFile.exists()) {
			configFile.createNewFile();
		}
		Document document = fixMetadataNodeHelper
				.MetadataNode2DocumentWithInclude(configNode, null,
						include_attr, REQ_LABEL, interfaceId);
		String content = XMLHelper.documentToXML(document);
		XMLHelper.saveXMLFile(configFile, content);
		return configFile;
	}

	private File createRspConfigFile(IMetadataNode configNode, String filePath, String interfaceId)
			throws Exception {

		File configFile = new File(filePath);
		if (!configFile.exists()) {
			configFile.createNewFile();
		}
		Document document = fixMetadataNodeHelper
				.MetadataNode2DocumentWithInclude(configNode, null,
						include_attr, RSP_LABEL, interfaceId);
		String content = XMLHelper.documentToXML(document);
		XMLHelper.saveXMLFile(configFile, content);
		return configFile;
	}
}
