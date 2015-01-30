package com.dc.esb.servicegov.refactoring.resource.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
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
import com.dc.esb.servicegov.refactoring.util.PackerUnPackerConstants;
import com.dc.esb.servicegov.refactoring.util.SDAConstants;

@Service
public class SOPPackerUnPackerConfigGenerater implements 
		IConfigGenerater<InvokeInfo, List<File>> {

	private static final Log log = LogFactory
			.getLog(SOPPackerUnPackerConfigGenerater.class);

	@Autowired
	private XMPassedInterfaceDataFromDB xmPassedInterfaceDataFromDB;
	@Autowired
	private SpdbSpecDefaultInterfaceGenerater spdbSpecDefaultInterfaceGenerater;
	
	private List<File> configFiles = null;
	private static final List<String> include_attr;
	private String interfaceId = null;
	static {
		include_attr = new ArrayList<String>();
		include_attr.add("type=array");
		include_attr.add("type=string");
		include_attr.add("type=selfjoinarray");
		include_attr.add("type=selfjoinchild");
		include_attr.add("type=sopform");
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
	}

	public SOPPackerUnPackerConfigGenerater() {
		configFiles = new ArrayList<File>();
	}

	@Override
	public List<File> generate(InvokeInfo invokeInfo) {
		List<File> files = null;
		try {
			if (null != invokeInfo) {
				log.info("开始导出SOP报文的配置文件!");
				interfaceId = invokeInfo.getEcode();
				List<MetadataNode> templateInterfaceNodes = getTemplateInterfaceNodes(interfaceId, invokeInfo.getDirection());
				MetadataNode interfaceNode = xmPassedInterfaceDataFromDB
						.getNodeFromDB(interfaceId, ResourceType.INTERFACE);
				files = generateConfigs(interfaceNode, templateInterfaceNodes, invokeInfo);
			} else {
				log.error("[SOP配置导出]:接口信息不能为空");
			}
		} catch (Exception e) {

		}
		return files;
	}
	
	/**
	 * @author Vincent Fan
	 *  处理 多只交易共用交易码的情况下，InterfaceId增加了区分码的做法。
	 *  将区分码(由 "-"分隔)去除
	 */
	private String handleInterfaceIdForDupTrade(String interfaceId){
		String tmpInterfaceId = interfaceId;
		if(interfaceId.indexOf("-") > -1){
			tmpInterfaceId = interfaceId.substring(0,interfaceId.indexOf("-"));
		}
		return tmpInterfaceId;
	}

	private List<File> generateConfigs(MetadataNode interfaceNode,
			List<MetadataNode> templateInterfaceNodes, InvokeInfo invokeInfo)
			throws Exception {
		List<File> configFiles = new ArrayList<File>();
		try {
			String operationId = invokeInfo.getOperationId();
			//TODO remove this when the dup operationId issue is resolved
			String tmpOperationId = handleInterfaceIdForDupTrade(operationId);
			String interfaceId = invokeInfo.getEcode();
			String interfaceType = invokeInfo.getDirection();
			String interfaceMSGType = "1".equals(invokeInfo.getDirection())?invokeInfo.getProvideMsgType():invokeInfo.getConsumeMsgType();
			String sysId = invokeInfo.getProvideSysId();
			String serviceId = invokeInfo.getServiceId();
			log.info("[SOP配置生成]:查找到接口[" + interfaceId + "]对应的场景为["
					+ operationId + "]对应的服务为[" + serviceId + "]");
			log.info("[SOP配置生成]:处理接口ID["+interfaceId+"]");
			interfaceId = handleInterfaceIdForDupTrade(interfaceId);
			for (IMetadataNode templateInterfaceNode : templateInterfaceNodes) {
				sopParseHook(interfaceNode, templateInterfaceNode, interfaceId,
						interfaceType);
				String configPosition = null;
				String dir = serviceId + tmpOperationId +"(" + invokeInfo.getConsumeMsgType() + "-"
				+ invokeInfo.getProvideMsgType() + ")" + File.separator;
				String reInterfaceType = null; 
				if (PackerUnPackerConstants.PROVIDER.equalsIgnoreCase(interfaceType)) {
					reInterfaceType = PackerUnPackerConstants.CONSUMER;
					configPosition = PackerUnPackerConstants.DIRECTION_OUT;
					dir = dir + PackerUnPackerConstants.OUT_CONF_DIR;
					//生成consumer标准配置
					log.info("[SOP配置生成]:使用默认接口生成填充["+PackerUnPackerConstants.DIRECTION_IN+"]端配置！");
				} else if (PackerUnPackerConstants.CONSUMER.equalsIgnoreCase(interfaceType)) {
					reInterfaceType = PackerUnPackerConstants.PROVIDER;
					configPosition = PackerUnPackerConstants.DIRECTION_IN;
					dir = dir + PackerUnPackerConstants.IN_CONF_DIR;
					log.info("[SOP配置生成]:使用默认接口生成填充["+PackerUnPackerConstants.DIRECTION_OUT+"]端配置！");
					
				}
				if(PackerUnPackerConstants.CONSUMER.equalsIgnoreCase(interfaceType)){
					reInterfaceType = PackerUnPackerConstants.PROVIDER;
				}else if(PackerUnPackerConstants.PROVIDER.equalsIgnoreCase(interfaceType)){
					reInterfaceType = PackerUnPackerConstants.CONSUMER;
				}
				invokeInfo.setDirection(reInterfaceType);
				configFiles.addAll(spdbSpecDefaultInterfaceGenerater.generate(invokeInfo));
				File configDir = new File(dir);
				log.info("[SOP配置导出]:[" + configPosition + "]端配置的文件路径为["
						+ configDir.getAbsolutePath() + "]");
				configDir.mkdirs();

				generateReqConfigs(interfaceNode, templateInterfaceNode,
						configPosition, dir, serviceId, operationId,interfaceMSGType.toLowerCase(), sysId );
				//in_config
				generateRspConfigs(interfaceNode, templateInterfaceNode,
						configPosition, dir, serviceId, operationId, interfaceMSGType.toLowerCase(), sysId);
				// 对SOP某一文件做特殊处理
				log.info("对SOP报文In端以'_system_sop'结尾的文件做特殊处理");
				String specialDir =  serviceId + tmpOperationId +"(" + invokeInfo.getConsumeMsgType() + "-"
				+ invokeInfo.getProvideMsgType() + ")" + File.separator + PackerUnPackerConstants.IN_CONF_DIR; 
				handleSOPSpecialFile(specialDir);
				configFiles.add(configDir);
			}
		} catch (IOException e) {
			log.error("[SOP配置导出]:配置文件读写失败！", e);
		} catch (Exception e) {
			log.error("[SOP配置导出]:配置文件导出失败！", e);
			throw e;
		}
		return configFiles;
	}
	
	/**
	 * 
	 * @param contentNode
	 * @param templateNode
	 * @param direction
	 * @param dir
	 * @param serviceId
	 * @param operationId
	 * @param interfaceMsgType
	 * @param sysId
	 * @throws Exception
	 */
	private void generateRspConfigs(IMetadataNode contentNode,
			IMetadataNode templateNode, String direction, String dir,
			String serviceId, String operationId, String interfaceMsgType, String sysId) throws Exception {
		String tmpOperationId = handleInterfaceIdForDupTrade(operationId);
		if (null != contentNode && null != templateNode && null != direction) {
			Map<String, String> mapFileResultMap = getMapFileResultMap(contentNode);
			String rspConfigFileName = null;
			if (null != mapFileResultMap) {
				for (Map.Entry<String, String> entry : mapFileResultMap
						.entrySet()) {
					if (direction.equals(PackerUnPackerConstants.DIRECTION_IN)) {
						rspConfigFileName = dir + File.separator + "service_"
								+ serviceId + tmpOperationId + "_"
								+ entry.getKey() + "_system_"+interfaceMsgType.toLowerCase()+".xml";

					} else if (direction
							.equals(PackerUnPackerConstants.DIRECTION_OUT)) {
						
						if("SOP".equals(sysId)|| "PWM".equals(sysId) || "YJT".equals(sysId)|| "NBH".equals(sysId)){
							
						}else{
							sysId = sysId.toLowerCase();
						}
						
						rspConfigFileName = dir + File.separator
								+ "channel_"+sysId+"System_" + entry.getKey()
								+ "_service_" + serviceId + tmpOperationId
								+ ".xml";
					}

					IMetadataNode node = filterNodeByMapFile(contentNode,
							entry.getValue(), direction);
					IMetadataNode rspConfigNode = createConfigNode(
							MetadataNodeHelper.cloneNode(templateNode),
							MetadataNodeHelper.cloneNode(node),
							SDAConstants.RESPONSE);
					createConfigFile(rspConfigNode, rspConfigFileName);
				}
			} else {
				if (direction.equals(PackerUnPackerConstants.DIRECTION_IN)) {
					rspConfigFileName = dir + File.separator + "service_"
							+ serviceId + tmpOperationId + "_system_"+interfaceMsgType.toLowerCase()+".xml";
				} else if (direction
						.equals(PackerUnPackerConstants.DIRECTION_OUT)) {
					if("SOP".equals(sysId)|| "PWM".equals(sysId) || "YJT".equals(sysId)|| "NBH".equals(sysId)){
						
					}else{
						sysId = sysId.toLowerCase();
					}
					rspConfigFileName = dir + File.separator
							+ "channel_"+sysId+"System_service_" + serviceId + tmpOperationId
							+ ".xml";
				}
				IMetadataNode rspConfigNode = createConfigNode(
						MetadataNodeHelper.cloneNode(templateNode),
						MetadataNodeHelper.cloneNode(contentNode),
						SDAConstants.RESPONSE);

				createConfigFile(rspConfigNode, rspConfigFileName);
			}
		}
	}

	//in端拆包报文加入<d: serviceAdr/><d: serviceAction/>两个节点
	private void generateReqConfigs(IMetadataNode contentNode,
			IMetadataNode templateNode, String direction, String dir,
			String serviceId, String operationId, String interfaceMsgType, String interfaceSysId) throws Exception {
		log.info("[SOP配置文件生成]:开始生成配置文件,配置位置为[" + direction + "], 配置文件的场景为["
				+ operationId + "], 配置文件的服务为[" + serviceId + "]");
		String tmpOperationId = handleInterfaceIdForDupTrade(operationId);
		if (null != contentNode && null != templateNode && null != direction) {
			String reqConfigFileName = null;
			if (direction.equals(PackerUnPackerConstants.DIRECTION_IN)) {
				if("sop".equalsIgnoreCase(interfaceSysId)){
					reqConfigFileName = dir + File.separator
							+ "channel_"+interfaceMsgType+"_service_" + serviceId + tmpOperationId
							+ ".xml";
				}else{
					reqConfigFileName = dir + File.separator
							+ "channel_"+interfaceMsgType.toLowerCase()+"_service_" + serviceId + tmpOperationId
							+ ".xml";
				}
			} else if (direction.equals(PackerUnPackerConstants.DIRECTION_OUT)) {
				//SOP已经采用大写，文件名需要做特殊处理
				if("sop".equalsIgnoreCase(interfaceSysId)){
					reqConfigFileName = dir + File.separator + "service_"
							+ serviceId + tmpOperationId + "_system_"+interfaceSysId+"System.xml";
				}else{
					if("SOP".equals(interfaceSysId)|| "PWM".equals(interfaceSysId) || "YJT".equals(interfaceSysId)|| "NBH".equals(interfaceSysId)){
						
					}else{
						interfaceSysId = interfaceSysId.toLowerCase();
					}
					reqConfigFileName = dir + File.separator + "service_"
							+ serviceId + tmpOperationId + "_system_"+interfaceSysId+"System.xml";
				}
				
			}
			invokeAddServiceAdrNode(templateNode,this.interfaceId, serviceId, operationId,interfaceMsgType);
			invokeAddServiceActionNode(templateNode,this.interfaceId, serviceId, operationId,interfaceMsgType);
			log.info("[SOP配置文件生成]:开始生成配置文件[" + reqConfigFileName + "]");
			IMetadataNode reqConfigNode = createConfigNode(
					MetadataNodeHelper.cloneNode(templateNode),
					MetadataNodeHelper.cloneNode(contentNode),
					SDAConstants.REQUEST);

			createConfigFile(reqConfigNode, reqConfigFileName);
		}
	}

	/**
	 * this is a hook to make metadata a "sop metadata"
	 * 
	 * @param bodyNode
	 * @param templateNode
	 * @param interfaceId
	 */
	private void sopParseHook(IMetadataNode bodyNode,
			IMetadataNode templateNode, String interfaceId, String interfaceType) {
		if (null != bodyNode && null != templateNode && null != interfaceId
				&& null != interfaceType) {
			handleHeader(templateNode, interfaceId, interfaceType);
			handleBody(bodyNode, interfaceId, interfaceType);
		}
	}

	/**
	 * handle header node to make it "sop"
	 * 
	 * @param node
	 * @param interfaceId
	 */
	private void handleHeader(IMetadataNode node, String interfaceId,
			String interfaceType) {
		IMetadataNode reqTranCodeNode = node
				.getNodeByPath("request.root.TRAN_HEAD.JIAOYM");
		if (null != reqTranCodeNode) {
			IMetadataNodeAttribute attribute = reqTranCodeNode.getProperty();
			if (null == attribute) {
				attribute = new MetadataNodeAttribute();
				reqTranCodeNode.setProperty(attribute);
			}
			if (interfaceType
					.equalsIgnoreCase(PackerUnPackerConstants.PROVIDER)) {
				attribute.setProperty("expression", "'" + interfaceId + "'");
			}
		}
		IMetadataNode rspTranCodeNode = node
				.getNodeByPath("response.root.CMTRAN_RCV_HEAD.JIAOYM");
		if (null != rspTranCodeNode) {
			IMetadataNodeAttribute attribute = rspTranCodeNode.getProperty();
			if (null == attribute) {
				attribute = new MetadataNodeAttribute();
				rspTranCodeNode.setProperty(attribute);
			}
			if (interfaceType
					.equalsIgnoreCase(PackerUnPackerConstants.CONSUMER)) {
				attribute.setProperty("expression", "'" + interfaceId + "'");
			}
		}
	}

	/**
	 * handle body node to make it "sop"
	 * 
	 * @param bodyNode
	 */
	private void handleBody(IMetadataNode bodyNode, String interfaceId,
			String interfaceType) {
		IMetadataNodeAttribute attribute = bodyNode.getProperty();
		String callSeq = null;
		if (null != attribute) {
			callSeq = attribute.getProperty("callSeq");
			IMetadataNode rspNode = bodyNode.getChild("response");
			if (rspNode.hasChild()) {
				invokeAddMapfileNode(rspNode, interfaceType, interfaceId);
			}
			handleReq(bodyNode.getChild("request"), callSeq);
			handleRsp(rspNode, callSeq);
		}

	}

	private void invokeAddMapfileNode(IMetadataNode parentNode,
			String interfaceType, String interfaceId) {
		if (PackerUnPackerConstants.PROVIDER.equalsIgnoreCase(interfaceType)) {
			IMetadataNode metadataNode = new MetadataNode();
			metadataNode.setNodeID("mapfile");
			IMetadataNodeAttribute metadataNodeAttribute = new MetadataNodeAttribute();
			metadataNodeAttribute.setProperty("bytelength", "1");
			metadataNode.setProperty(metadataNodeAttribute);
			parentNode.addChild(0, metadataNode);
		} else if (PackerUnPackerConstants.CONSUMER
				.equalsIgnoreCase(interfaceType)) {
			IMetadataNode metadataNode = new MetadataNode();
			metadataNode.setNodeID("mapfile");
			IMetadataNodeAttribute metadataNodeAttribute = new MetadataNodeAttribute();
			metadataNodeAttribute.setProperty("bytelength", "1");
			metadataNodeAttribute.setProperty("expression", "'O" + interfaceId
					+ "2'");
			metadataNodeAttribute.setProperty("type", "string");
			metadataNode.setProperty(metadataNodeAttribute);
			parentNode.addChild(0, metadataNode);
		}
	}
	
	/**
	 * sop报文
	 * 添加节点<d: serviceAdr/><d: serviceAction/>
	 * 
	 * 
	 * @param templateNode
	 * @param interfaceId
	 * @param serviceId
	 * @param operationId
	 */
	private void invokeAddServiceAdrNode(IMetadataNode templateNode,
			 String interfaceId,String serviceId,String operationId,String interfaceMSGType){
//		List list = invokeInfoDAO.getInvokeByEcodeAndSOP(interfaceId, serviceId, operationId,interfaceMSGType);
//		if(list != null && list.size() > 0){
		if("sop".equals(interfaceMSGType)){
			IMetadataNode parentNode = templateNode.getNodeByPath("root.request.root.SYSTEM_HEAD");
			IMetadataNode metadataNode = new MetadataNode();
			metadataNode.setNodeID("ServiceAdr");
			
			IMetadataNodeAttribute attribute = new MetadataNodeAttribute();								
			//expression="'http://esb.spdbbiz.com:7701/services/S120020001'"
			String wsdlHost = "http://esb.spdbbiz.com";
//			List wsdlList = olaDAO.getWsdlHostByServiceId(serviceId);
//			if(wsdlList != null && wsdlList.size() > 0){
//				wsdlHost = wsdlList.get(0).toString();
//			}
			String expression = "'"+wsdlHost+":7701/services/"+serviceId+"'";
			attribute.setProperty("expression", expression);						
			attribute.setProperty("metadataid", "ServiceAdr");
			attribute.setProperty("length", "0");	
			metadataNode.setProperty(attribute);			
			parentNode.addChild(6,metadataNode);
		}
//		}	
	}
	/**
	 * sop报文
	 * 添加节点<d: serviceAdr/><d: serviceAction/>
	 * 
	 * @param templateNode
	 * @param interfaceId
	 * @param serviceId
	 * @param operationId
	 */
	private void invokeAddServiceActionNode(IMetadataNode templateNode,
			 String interfaceId,String serviceId,String operationId,String interfaceMSGType){
//		List list = invokeInfoDAO.getInvokeByEcodeAndSOP(interfaceId, serviceId, operationId,interfaceMSGType);
//		if(list != null && list.size() > 0){
		if("sop".equals(interfaceMSGType)){
			IMetadataNode parentNode = templateNode.getNodeByPath("root.request.root.SYSTEM_HEAD");
			IMetadataNode metadataNode = new MetadataNode();
			metadataNode.setNodeID("ServiceAction");
			
			IMetadataNodeAttribute attribute = new MetadataNodeAttribute();
			attribute.setProperty("length", "0");					
			attribute.setProperty("metadataid", "ServiceAction");				
			//expression="'urn:/BankFundSysAcctOpen'"
			String expression = "'urn:/"+operationId+"'";
			attribute.setProperty("expression", expression);	
			metadataNode.setProperty(attribute);
			parentNode.addChild(7,metadataNode);	
		}
//		}
		
	}

	private void handleReq(IMetadataNode reqNode, String callSeq) {
		if (isSopArray(reqNode)) {
			parseSopArray(reqNode);
		} else if (reqNode.hasChild()) {
			for (IMetadataNode childNode : reqNode.getChild()) {
				handleReq(childNode, callSeq);
			}
		} else {
			if (null != callSeq) {
				handleExpression(reqNode, callSeq, "request");
			}
			parseLeafNode(reqNode);
		}
	}

	private void handleRsp(IMetadataNode rspNode, String callSeq) {
		if (isSopArray(rspNode)) {
			parseSopArray(rspNode);
		}
		if (rspNode.hasChild()) {
			for (IMetadataNode childNode : rspNode.getChild()) {
				handleRsp(childNode, callSeq);
			}
		} else {
			if (null != callSeq) {
				handleExpression(rspNode, callSeq, "response");
			}
			parseLeafNode(rspNode);
		}
	}

	private void handleExpression(IMetadataNode reqNode, String callSeq,
			String type) {

		if (null != callSeq && callSeq.contains("-")) {
			IMetadataNodeAttribute attr = reqNode.getProperty();
			String expression = attr.getProperty("expression");

			if (null != expression && expression.startsWith("$")) {
				StringTokenizer stringTokenizer = new StringTokenizer(callSeq,
						"-");
				if (stringTokenizer.countTokens() == 2) {
					String in = stringTokenizer.nextToken();
					String out = stringTokenizer.nextToken();
					StringBuilder parsedExpression = new StringBuilder();
					Map<String, String> args = getArgsFromExpression(expression);
					if ("request".equalsIgnoreCase(type)) {
						if (in.equalsIgnoreCase("sop")) {
							parsedExpression.append("ff:getMasterCodeValue('");
						}
						if (out.equalsIgnoreCase("sop")) {
							parsedExpression.append("ff:getSlaveCodeValue('");
						}
						parsedExpression.append(args.get("masterCode"));
						parsedExpression.append("','");
						parsedExpression.append(args.get("slaveCode"));
						parsedExpression.append("',${");
						String rawPath = args.get("serviceNodePath");
						if (null != rawPath) {
							parsedExpression
									.append(invokeParseExpressionPath(rawPath));

						}
						parsedExpression.append("},'','");
						parsedExpression.append(args.get("whateverItisa1"));
						parsedExpression.append("')");
					}

					if ("response".equalsIgnoreCase(type)) {
						if (in.equalsIgnoreCase("sop")) {
							parsedExpression.append("ff:getSlaveCodeValue('");
						}
						if (out.equalsIgnoreCase("sop")) {
							parsedExpression.append("ff:getMasterCodeValue('");
						}
						parsedExpression.append(args.get("masterCode"));
						parsedExpression.append("','");
						parsedExpression.append(args.get("slaveCode"));
						parsedExpression.append("',${");
						String rawPath = args.get("serviceNodePath");
						if (null != rawPath) {
							parsedExpression
									.append(invokeParseExpressionPath(rawPath));

						}
						parsedExpression.append("},'','");
						parsedExpression.append(args.get("whateverItisa1"));
						parsedExpression.append("')");
					}
					attr.remove("expression");
					attr.setProperty("expression", parsedExpression.toString());
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
				pathNodes[i] = pathNodes[i] + "[*]/sdo";
				log.info("add [*]/sdo to " + pathNodes[i] + "");
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("/sdoroot/");
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
						argMap.put(argTokenizer.nextToken(),
								argTokenizer.nextToken());
					}
				}
			}
		}
		return argMap;
	}

	/**
	 * parse leaf node for sop
	 * 
	 * @param node
	 */
	private void parseLeafNode(IMetadataNode node) {
		if (null != node) {
			IMetadataNodeAttribute attr = node.getProperty();
			removeLengthForSOP(attr);
			addByteLength(attr);
		}
	}

	/**
	 * sop do not need length
	 */
	private void removeLengthForSOP(IMetadataNodeAttribute attr) {
		if (null != attr) {
			if (attr.containsKey("length")) {
				attr.remove("length");
			}
		}
	}

	/**
	 * add byte length to leaf node
	 * 
	 * @param node
	 */
	private void addByteLength(IMetadataNodeAttribute attr) {
		if (null != attr) {
			attr.setProperty("bytelength", "1");
		}
	}

	/**
	 * wether the node is a sop array node
	 * 
	 * @param node
	 * @return
	 */
	private boolean isSopArray(IMetadataNode node) {
		IMetadataNodeAttribute attr = node.getProperty();
		if (null != attr) {
			if (attr.containsKey("type")) {
				if (attr.getProperty("type").equals("array")
						|| attr.getProperty("type").equals("sopform")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * make the array node "sop"
	 * 
	 * @param metadataNode
	 */
	private void parseSopArray(IMetadataNode metadataNode) {
		String nodeId = metadataNode.getNodeID();
		String metadataid = metadataNode.getMetadataID();
		metadataNode.setNodeID(metadataid);
		IMetadataNodeAttribute attr = metadataNode.getProperty();
		if (null != attr) {
			attr.remove("type");
		} else {
			attr = new MetadataNodeAttribute();
		}
		attr.setProperty("type", "sopform");
		attr.setProperty("name", nodeId);
		IMetadataNode sdoNode = new MetadataNode();
		sdoNode.setNodeID("sdo");
		if (metadataNode.hasChild()) {
			for (IMetadataNode childNode : metadataNode.getChild()) {
				parseSopArrayElement(childNode);
			}
		}
		for (IMetadataNode childNode : metadataNode.getChild()) {
			sdoNode.addChild(childNode);
		}
		metadataNode.getChild().clear();
		metadataNode.addChild(sdoNode);
	}

	/**
	 * make the children of array node "sop"
	 * 
	 * @param metadataNode
	 */
	private void parseSopArrayElement(IMetadataNode metadataNode) {
		if (null != metadataNode) {
			IMetadataNodeAttribute attr = metadataNode.getProperty();
			if (null != attr) {
				attr.remove("length");
			} else {
				attr = new MetadataNodeAttribute();
			}
			String metadataid = metadataNode.getMetadataID();
			metadataNode.setNodeID(metadataid);
			if (metadataNode.hasChild()) {
				for (IMetadataNode childNode : metadataNode.getChild()) {
					parseSopArrayElement(childNode);
				}
			} else {
				attr.setProperty("bytelength", "1");
			}
		}
	}

	/**
	 * get the information of map files from body node
	 * 
	 * @param node
	 * @return
	 */
	private Map<String, String> getMapFileResultMap(IMetadataNode node) {
		IMetadataNode rspNode = node.getNodeByPath(SDAConstants.RESPONSE);
		IMetadataNodeAttribute attr = rspNode.getProperty();
		Map<String, String> mapFileResultMap = null;
		if (null != attr) {
			if (attr.containsKey("resultMap")) {
				String resultMap = attr.getProperty("resultMap");
				if (null != resultMap && resultMap.length() > 0) {
					mapFileResultMap = new HashMap<String, String>();
					resultMap = resultMap.trim();
					String[] resultMapElements = resultMap.split(",");
					for (String resultMapElement : resultMapElements) {
						StringTokenizer stringTokenizer = new StringTokenizer(
								resultMapElement, "=");
						if (stringTokenizer.countTokens() > 1) {
							mapFileResultMap.put(stringTokenizer.nextToken(),
									stringTokenizer.nextToken());
						}
					}
				}
			}
		}
		return mapFileResultMap;
	}

	/**
	 * get the content node exact for the map file
	 * 
	 * @param node
	 * @param mapFileName
	 * @return
	 */
	private IMetadataNode filterNodeByMapFile(IMetadataNode node,
			String mapFileName, String interfaceType) {
		IMetadataNode targetNode = null;
		if (null != node && null != mapFileName) {
			targetNode = MetadataNodeHelper.cloneNode(node);
			IMetadataNode rspNode = targetNode.getNodeByPath("response");
			IMetadataNode targetRspNode = new MetadataNode();
			targetRspNode.setNodeID(rspNode.getNodeID());
			targetRspNode.setMetadataID(rspNode.getMetadataID());
			targetRspNode.setProperty(rspNode.getProperty());
			if (PackerUnPackerConstants.DIRECTION_OUT
					.equalsIgnoreCase(interfaceType)) {
				IMetadataNode metadataNode = new MetadataNode();
				metadataNode.setNodeID("mapfile");
				IMetadataNodeAttribute metadataNodeAttribute = new MetadataNodeAttribute();
				metadataNodeAttribute.setProperty("bytelength", "1");
				metadataNode.setProperty(metadataNodeAttribute);
				targetRspNode.addChild(0, metadataNode);
			} else if (PackerUnPackerConstants.DIRECTION_IN
					.equalsIgnoreCase(interfaceType)) {
				IMetadataNode metadataNode = new MetadataNode();
				metadataNode.setNodeID("mapfile");
				IMetadataNodeAttribute metadataNodeAttribute = new MetadataNodeAttribute();
				metadataNodeAttribute.setProperty("bytelength", "1");
				metadataNodeAttribute.setProperty("expression", "'"
						+ mapFileName + "'");
				metadataNodeAttribute.setProperty("type", "string");
				metadataNode.setProperty(metadataNodeAttribute);
				targetRspNode.addChild(0, metadataNode);
			}
			if (rspNode.hasChild()) {
				for (IMetadataNode child : rspNode.getChild()) {
					IMetadataNodeAttribute childAttr = child.getProperty();
					if (null == childAttr) {
						continue;
					}

					if (!childAttr.containsKey("mapfile")) {
						continue;
					}

					if (childAttr.containsKey("mapfile")
							&& !childAttr.getProperty("mapfile").contains(
									mapFileName)) {
						continue;
					}
					targetRspNode.addChild(child);
				}

			}
			IMetadataNode nodeToReplace = targetNode.getChild("response");
			targetNode.remove(nodeToReplace);
			targetNode.addChild(targetRspNode);
		}
		return targetNode;
	}

	/**
	 * create configure file
	 * 
	 * @param configNode
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	private File createConfigFile(IMetadataNode configNode, String filePath)
			throws Exception {
		File configFile = new File(filePath);
		if (!configFile.exists()) {
			configFile.createNewFile();
		}
		Document document = MetadataNodeHelper
				.MetadataNode2DocumentWithInclude(configNode, null,
						include_attr);
		String content = XMLHelper.documentToXML(document);
		XMLHelper.saveXMLFile(configFile, content);
		return configFile;
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
				throw new Exception("目标元数据节点个数不正确！");
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

	public List<MetadataNode> getTemplateInterfaceNodes(String interfaceId, String direction) {
		List<MetadataNode> templates = new ArrayList<MetadataNode>();
		List<String> parentInterfaceIds = xmPassedInterfaceDataFromDB
				.getParentNodeIds(interfaceId);
		if(parentInterfaceIds.size() > 1){
			if("1".equals(direction)){
				parentInterfaceIds.remove("InSOPTemplate");
			}
			else{
				parentInterfaceIds.remove("OutSOPTemplate");
			}
		}
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
		return true;
	}

	public List<File> getConfigFiles() {
		return configFiles;
	}

	public void setConfigFiles(List<File> configFiles) {
		this.configFiles = configFiles;
	}
	
	public void handleSOPSpecialFile(String specialDir){
		try {
			File file = new File(specialDir);
			File[] files = file.listFiles();
			for (File tempfile : files) {
				String fileName = tempfile.getName();
				if (fileName.endsWith("_system_sop.xml")) {
					SAXReader saxReader = new SAXReader();
					Document xmlDoc  = saxReader.read(tempfile);
					Element element = xmlDoc.getRootElement();
					element.addAttribute("fixtranLen", "true");
					xmlDoc.remove(xmlDoc.getRootElement());
					xmlDoc.add(element);
					String content = XMLHelper.documentToXML(xmlDoc);
					XMLHelper.saveXMLFile(tempfile, content);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
