package com.dc.esb.servicegov.resource.impl;



import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.dao.impl.OLADAOImpl;
import com.dc.esb.servicegov.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.entity.InvokeInfo;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.resource.metadataNode.IMetadataNode;
import com.dc.esb.servicegov.resource.metadataNode.IMetadataNodeAttribute;
import com.dc.esb.servicegov.util.PackerUnPackerConstants;


@Service
public class PackerUnPackerConfigHelper {

	private static final Log log = LogFactory
			.getLog(PackerUnPackerConfigHelper.class);
	
	@Autowired
	private OperationDAOImpl operationDAO;
	@Autowired
	private OLADAOImpl olaDAO;
	
	public String getServiceIdByOperationId(String operationId) {
		String serviceId = null;
		if (null != operationId) {
			List<Operation> services = operationDAO.findBy("operationId", operationId);
			log.info("[配置文件Helper]: 读取到对应操作[" + operationId + "]的父服务["
					+ services.size() + "]个！");
			// 导出时父服务中只需要一个包含wsdl_host属性的服务
			for (Operation operation : services) {
				log.info("[配置文件Helper]: 读取到父服务: [" + operation.getServiceId() + "]");
				if (isWsdlHostService(operationId,operation.getServiceId())) {
					serviceId = operation.getServiceId();
					break;
				}
			}
		}
		return serviceId;
	}

	// 区分具有wsdl_host属性的服务
	@SuppressWarnings("unchecked")
	private boolean isWsdlHostService(String operationId,String serviceId) {

		List list = olaDAO.getWsdlHostByOperationIdAndServiceId(operationId, serviceId);
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}

	public File getTempWorkDir() {
		long currentTime = System.currentTimeMillis();
		File tempWorkDir = new File(String.valueOf(currentTime));
		tempWorkDir.mkdirs();
		return tempWorkDir;
	}

	public void reverseInterfaceDirection(Map<String, String> interfaceInfo) {
		if (null != interfaceInfo) {
			String interfaceType = interfaceInfo.get("INTERFACETYPE");
			if (PackerUnPackerConstants.CONSUMER.equalsIgnoreCase(interfaceType)) {
				interfaceType = PackerUnPackerConstants.PROVIDER;
			} else if (PackerUnPackerConstants.PROVIDER.equalsIgnoreCase(interfaceType)) {
				interfaceType = PackerUnPackerConstants.CONSUMER;
			}
			if (interfaceInfo.containsKey("INTERFACETYPE")) {
				interfaceInfo.remove("INTERFACETYPE");
				interfaceInfo.put("INTERFACETYPE", interfaceType);
			}
		}

	}
	
	/**
	 * 调换I和O的接口方向
	 * @param invokeInfo
	 */
	public void reverseInterfaceDirection(InvokeInfo invokeInfo) {
		if (null != invokeInfo) {
			String interfaceType = invokeInfo.getDirection();
			if (PackerUnPackerConstants.CONSUMER.equalsIgnoreCase(interfaceType)) {
				interfaceType = PackerUnPackerConstants.PROVIDER;
			} else if (PackerUnPackerConstants.PROVIDER.equalsIgnoreCase(interfaceType)) {
				interfaceType = PackerUnPackerConstants.CONSUMER;
			}
			invokeInfo.setDirection(interfaceType);
		}

	}

	public void handleExpression(IMetadataNode reqNode, String interfaceType,
			String type) {

		IMetadataNodeAttribute attr = reqNode.getProperty();
		String expression = attr.getProperty("expression");
		StringBuilder parsedExpression = new StringBuilder();
		Map<String, String> args = getArgsFromExpression(expression);
		if ("request".equalsIgnoreCase(type)) {
			if (interfaceType
					.equalsIgnoreCase(PackerUnPackerConstants.CONSUMER)) {
				parsedExpression.append("ff:getMasterCodeValue('");
			}
			if (interfaceType
					.equalsIgnoreCase(PackerUnPackerConstants.PROVIDER)) {
				parsedExpression.append("ff:getSlaveCodeValue('");
			}
			parsedExpression.append(args.get("masterCode"));
			parsedExpression.append("','");
			parsedExpression.append(args.get("slaveCode"));
			parsedExpression.append("',${");
//			String serviceNodePath = args.get("serviceNodePath");
			String rawPath = args.get("serviceNodePath");
			if (null != rawPath) {
				parsedExpression.append(invokeParseExpressionPath(rawPath));

			}
			parsedExpression.append("},'','");
			parsedExpression.append(args.get("whateverItisa1"));
			parsedExpression.append("')");
		}

		if ("response".equalsIgnoreCase(type)) {
			if (interfaceType
					.equalsIgnoreCase(PackerUnPackerConstants.CONSUMER)) {
				parsedExpression.append("ff:getSlaveCodeValue('");
			}
			if (interfaceType
					.equalsIgnoreCase(PackerUnPackerConstants.PROVIDER)) {
				parsedExpression.append("ff:getMasterCodeValue('");
			}
			parsedExpression.append(args.get("masterCode"));
			parsedExpression.append("','");
			parsedExpression.append(args.get("slaveCode"));
			parsedExpression.append("',${");
			String rawPath = args.get("serviceNodePath");
			if (null != rawPath) {
				parsedExpression.append(invokeParseExpressionPath(rawPath));

			}
			parsedExpression.append("},'','");
			parsedExpression.append(args.get("whateverItisa1"));
			parsedExpression.append("')");
		}
		attr.remove("expression");
		attr.setProperty("expression", parsedExpression.toString());
	}

	public void handleExpression(IMetadataNode reqNode, String interfaceType,
			String type, String path) {
		IMetadataNodeAttribute attr = reqNode.getProperty();
		String expression = attr.getProperty("expression");
		StringBuilder parsedExpression = new StringBuilder();
		Map<String, String> args = getArgsFromExpression(expression);
		if ("request".equalsIgnoreCase(type)) {
			if (interfaceType
					.equalsIgnoreCase(PackerUnPackerConstants.CONSUMER)) {
				parsedExpression.append("ff:getMasterCodeValue('");
			}
			if (interfaceType
					.equalsIgnoreCase(PackerUnPackerConstants.PROVIDER)) {
				parsedExpression.append("ff:getSlaveCodeValue('");
			}
			parsedExpression.append("Ecif"+args.get("masterCode"));
			parsedExpression.append("','");
			parsedExpression.append(args.get("slaveCode"));
			parsedExpression.append("',${");
//			String serviceNodePath = args.get("serviceNodePath");
			// String rawPath = args.get("serviceNodePath");
			// if (null != rawPath) {
			// parsedExpression
			// .append(invokeParseExpressionPath(rawPath));
			//
			// }
			parsedExpression.append(path);

			parsedExpression.append("},'','");
			if(PackerUnPackerConstants.PROVIDER.equalsIgnoreCase(interfaceType)){
				parsedExpression.append("0");
			}else{
				parsedExpression.append("1");
			}
//			parsedExpression.append(args.get("whateverItisa1"));
			parsedExpression.append("')");
		}

		if ("response".equalsIgnoreCase(type)) {
			if (interfaceType
					.equalsIgnoreCase(PackerUnPackerConstants.CONSUMER)) {
				parsedExpression.append("ff:getSlaveCodeValue('");
			}
			if (interfaceType
					.equalsIgnoreCase(PackerUnPackerConstants.PROVIDER)) {
				parsedExpression.append("ff:getMasterCodeValue('");
			}
			parsedExpression.append("Ecif" + args.get("masterCode"));
			parsedExpression.append("','");
			parsedExpression.append(args.get("slaveCode"));
			parsedExpression.append("',${");
			// String rawPath = args.get("serviceNodePath");
			// if (null != rawPath) {
			// parsedExpression
			// .append(invokeParseExpressionPath(rawPath));
			//
			// }
//			parsedExpression.append(invokeParseExpressionPath(path));
			parsedExpression.append(path);
			parsedExpression.append("},'','");
			if(PackerUnPackerConstants.CONSUMER.equalsIgnoreCase(interfaceType)){
				parsedExpression.append("0");
			}else{
				parsedExpression.append("1");
			}
			parsedExpression.append("')");
		}
		attr.remove("expression");
		attr.setProperty("expression", parsedExpression.toString());
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
//				StringTokenizer stringTokenizer = new StringTokenizer(
//						argstring, ",");
				String[] argsStrings = argstring.split(",");
				if (null != argsStrings) {
					argMap = new HashMap<String, String>();
//					String token = null;
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

}
