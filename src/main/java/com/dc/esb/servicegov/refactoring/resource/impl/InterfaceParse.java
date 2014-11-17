package com.dc.esb.servicegov.refactoring.resource.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.IdaDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.IdaPROPDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.InterfaceDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.InterfaceExtendsDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.IDA;
import com.dc.esb.servicegov.refactoring.entity.IdaPROP;
import com.dc.esb.servicegov.refactoring.entity.Interface;
import com.dc.esb.servicegov.refactoring.entity.InterfaceExtends;
import com.dc.esb.servicegov.refactoring.resource.IParse;
import com.dc.esb.servicegov.refactoring.resource.node.Node;
import com.dc.esb.servicegov.refactoring.util.ExcelTool;
import com.dc.esb.servicegov.refactoring.util.GenerateUUID;
import com.dc.esb.servicegov.refactoring.util.GlobalMap;
import com.dc.esb.servicegov.refactoring.util.ServiceStateUtils;
import com.dc.esb.servicegov.refactoring.util.Utils;

@Service
@Transactional(rollbackFor = Exception.class)
public class InterfaceParse implements IParse {

	private Log log = LogFactory.getLog(InterfaceParse.class);

	private ExcelTool excelTool = ExcelTool.getInstance();
	private static final String initVersion = "1.0.0";
	private static final String outputStr = "输出";
	private static final String svcBody = "SvcBody";
	private static final String rootStr = "root";
	private static final String request = "request";
	private static final String response = "response";

	private int structIndex;
	private int seq;

	@Autowired
	private InterfaceDAOImpl interfaceDAO;
	@Autowired
	private IdaDAOImpl idaDAO;
	@Autowired
	private InterfaceExtendsDAOImpl iExtendsDAO;
	@Autowired
	private IdaPROPDAOImpl idaPropDAO;

	private Sheet interfaceSheet;

	private String interfaceId;
	private String interfaceName;
	private String through;
	// 接口报文类型
	private String interfaceMsgType;
	// 报文转换方向
	private String msgChangeType;
	// 接口方向
	private String direction;
	private List<IDA> list;
	private List<IdaPROP> idaPropList = null;
	private List<String> mapFileNamesOfCurrentElement = null;
	private Map<String, String> resultMap = null;
	private Map<String, String> expressionMap = null;
	private Map<String, String> expandMap = null;
	private StringBuilder mapfileResultMapInfo = null;

	@Override
	public void parse(Row row, Sheet interfaceSheet) {
		// TODO Auto-generated method stub
		idaPropList = new ArrayList<IdaPROP>();
		mapFileNamesOfCurrentElement = new ArrayList<String>();
		resultMap = new HashMap<String, String>();
		expressionMap = new HashMap<String, String>();
		expandMap = new HashMap<String, String>();
		mapfileResultMapInfo = new StringBuilder();
		this.interfaceSheet = interfaceSheet;
		String serviceIdAndName = excelTool
				.getCellContent(interfaceSheet, 0, 7);
		serviceIdAndName = serviceIdAndName.replace("（", "(");
		serviceIdAndName = serviceIdAndName.replace("）", ")");
		String serviceId = serviceIdAndName.substring(serviceIdAndName
				.indexOf("(") + 1, serviceIdAndName.length() - 1);
		String operationId = excelTool.getCellContent(row.getCell(3));
		interfaceMsgType = excelTool.getCellContent(row.getCell(9));
		interfaceMsgType = interfaceMsgType.replace("（", "(");
		interfaceMsgType = interfaceMsgType.replace("）", ")");
		interfaceMsgType = interfaceMsgType.replace("非标", "");
		interfaceMsgType = interfaceMsgType.replace("(", "");
		interfaceMsgType = interfaceMsgType.replace(")", "");

		msgChangeType = excelTool.getCellContent(row.getCell(12));
		msgChangeType = msgChangeType.replace("（", "(");
		msgChangeType = msgChangeType.replace("）", ")");
		msgChangeType = msgChangeType.replace("非标", "");
		msgChangeType = msgChangeType.replace("(", "");
		msgChangeType = msgChangeType.replace(")", "");
		msgChangeType = msgChangeType.replace("->", "-");

		// 报文转换方向
		direction = excelTool.getCellContent(row.getCell(7));
		if ("Provider".equals(direction)) {
			direction = "1";
		} else {
			direction = "0";
		}

		interfaceId = excelTool.getCellContent(row.getCell(0));
		interfaceName = excelTool.getCellContent(row.getCell(1));
		if ("".equals(interfaceId)) {
			interfaceId = serviceId + operationId;
		}
		through = excelTool.getCellContent(row.getCell(17));
		try {
			// insert Interface
			insertInterface();
			// 如果是SOP报文，需要配置模板接口
			if (interfaceMsgType.equalsIgnoreCase("sop")
					&& !interfaceId.endsWith("Template")) {
				insertDefaultConfig();
			}
			// insert IDA;
			log.info("begin to import Interface IDA!");
			// 判断哪种格式的文档，从第7行或第5行开始解析SDA
			if ("".equals(excelTool.getCellContent(interfaceSheet, 2, 0))) {
				structIndex = 7;
			} else {
				structIndex = 5;
			}
			insertIDA();
			log.info("import interface infos finished!");
		} catch (Exception e) {
			log.error("import interface Infos error!", e);
		}
	}

	/**
	 * 配置接口模板
	 */
	public void insertDefaultConfig() {
		InterfaceExtends iExtends = new InterfaceExtends();
		iExtends.setId(GenerateUUID.genRandom());
		iExtends.setInterfaceId(interfaceId);
		if ("1".equals(direction)) {
			iExtends.setSuperInterfaceId("OutSOPTemplate");
			iExtends.setSuperInterfaceName("Out端SOP模板接口");
		} else {
			iExtends.setSuperInterfaceId("InSOPTemplate");
			iExtends.setSuperInterfaceName("SOP模板接口");
		}
		if (iExtendsDAO.delByIExtends(iExtends)) {
			iExtendsDAO.TxSaveInterfaceExtends(iExtends);
		}
		log.info("insert interfaceExtends finished!");
	}

	public void insertInterface() {
		Interface i = new Interface();
		i.setInterfaceId(interfaceId);
		i.setEcode(interfaceId);
		i.setInterfaceName(interfaceName);
		i.setVersion(initVersion);
		i.setState(ServiceStateUtils.DEFINITION);
		i.setThrough(through);
		i.setModifyUser("");
		i.setUpdateTime(new Timestamp(java.lang.System.currentTimeMillis()));
		interfaceDAO.TxSaveInterface(i);
		log.info("insert interface finished!");
	}

	public void insertIDA() {
		Node sdaNode = this.getIDANodes();
//		log.info("导入的Ida属性信息");
//		for(IdaPROP idap :idaPropList){
//			log.info("name=" +idap.getName() +"## value="+ idap.getValue());
//		}
		seq = 0;
		list = new ArrayList<IDA>();
		// 先删除sda数据
		if (idaPropDAO.delIdaPROPByInterfaceId(interfaceId)) {
			if (idaDAO.delIDAByInterfaceId(interfaceId)) {
				renderInsertSDANode(sdaNode);
				if (idaDAO.batchInsertIDAs(list)) {
					log.info("导入IDA数据完成，开始导入IDa属性信息");
					idaPropDAO.batchIdaPropList(idaPropList);
				}
			}
		}
		log.info("insert IDA finished!");
	}

	// 递归插入node
	public void renderInsertSDANode(Node node) {
		seq++;
		IDA ida = new IDA();
		ida.setId(node.getId());
		ida.setStructName(node.getNodeId());
		ida.setStructAlias(node.getNodeAlias());
		ida.setMetadataId(node.getMetadataId());
		ida.setSeq(seq);
		ida.setType(node.getNodeType());
		ida.setLength(node.getNodeLength());
		ida.setScale(node.getNodeScale());
		ida.setRequired(node.getNodeRequire());
		ida.setParentId(node.getParentId());
		ida.setInterfaceId(interfaceId);
		ida.setRemark(node.getNodeRemark());
		// 用户session获取
		// ida.setModifyUser("");
		// ida.setUpdateTime(Utils.getTime());
		list.add(ida);
//		log.info("IDA=Info ： " + node.getId() + "===" + node.getNodeId()
//				+ "===" + node.getMetadataId() + "===" + seq + "==="
//				+ interfaceId + "===" + node.getParentId() + "==="
//				+ node.getNodeType() + "===" + node.getNodeLength() + "==="
//				+ node.getNodeScale());
		if (node.hasChild()) {
			for (Node childNode : node.getChildNodes()) {
				renderInsertSDANode(childNode);
			}
		}
	}

	public Node getIDANodes() {
		// 根节点
		Node root = new Node();
		String rootId = GenerateUUID.genRandom();
		root.setId(rootId);
		root.setNodeId(rootStr);
		root.setParentId("/");
		// request节点
		Node requestNode = new Node();
		String requestId = GenerateUUID.genRandom();
		requestNode.setId(requestId);
		requestNode.setNodeId(request);
		requestNode.setParentId(rootId);
		root.appendChild(requestNode);
		// response 节点
		Node responseNode = new Node();
		String responseId = GenerateUUID.genRandom();
		responseNode.setId(responseId);
		responseNode.setNodeId(response);
		responseNode.setParentId(rootId);
		root.appendChild(responseNode);
		// 左边无数据 “输入”下一行为空 “输入”下一行为“输出”，继续判断“输出”下一行是否为空
		if ("".equals(excelTool.getCellContent(interfaceSheet, structIndex, 0))) {

		} else if (outputStr.equals(excelTool.getCellContent(interfaceSheet,
				structIndex + 1, 0))
				&& "".equals(excelTool.getCellContent(interfaceSheet,
						structIndex + 2, 0))) {
		} else {
			// 添加request的childs
			this.appendRequestNodeChilds(requestNode);
			// 添加response的childs
			this.appendResponseNodeChilds(responseNode);
		}
		// 处理扩展属性MapFile
		for (Map.Entry<String, String> entry : expandMap.entrySet()) {
			IdaPROP idap = new IdaPROP();
			idap.setId(GenerateUUID.genRandom());
			idap.setIdaId(entry.getKey());
			idap.setName("mapfile");
			idap.setValue(entry.getValue());
			idaPropList.add(idap);
		}
		for (Map.Entry<String, String> entry : resultMap.entrySet()) {
			mapfileResultMapInfo
					.append(entry.getKey() + "=" + entry.getValue());
			mapfileResultMapInfo.append(",");
		}
		// 处理root 和 response 的扩展属性
		IdaPROP idap1 = new IdaPROP();
		idap1.setId(GenerateUUID.genRandom());
		idap1.setIdaId(responseId);
		idap1.setName("resultMap");
		idap1.setValue(mapfileResultMapInfo.toString());
		idaPropList.add(idap1);
		IdaPROP idap2 = new IdaPROP();
		idap2.setId(GenerateUUID.genRandom());
		idap2.setIdaId(rootId);
		idap2.setName("callSeq");
		idap2.setValue(msgChangeType);
		idaPropList.add(idap2);
		IdaPROP idap3 = new IdaPROP();
		idap3.setId(GenerateUUID.genRandom());
		idap3.setIdaId(rootId);
		idap3.setName("import_into");
		idap3.setValue("root.TRAN_BODY");
		idaPropList.add(idap3);

		log.info("root" + root.getId());
		return root;
	}

	public void appendRequestNodeChilds(Node requestNode) {
		// 如果为SOAP的报文类型，需要添加SvcBody节点
		if (interfaceMsgType.equalsIgnoreCase("soap")) {
			// 添加SvcBody节点
			Node SvcBodyRequestNode = new Node();
			SvcBodyRequestNode.setId(GenerateUUID.genRandom());
			SvcBodyRequestNode.setNodeId(svcBody);
			SvcBodyRequestNode.setParentId(requestNode.getId());
			requestNode.appendChild(SvcBodyRequestNode);
			this.renderNode(SvcBodyRequestNode, request);
		} else {
			this.renderNode(requestNode, request);
		}
		// 转到[输出]行下一行，开始解析response节点信息
		structIndex++;
		// 处理expresssion表达式Map
		for (Map.Entry<String, String> entry : expressionMap.entrySet()) {
			IdaPROP idap = new IdaPROP();
			idap.setId(GenerateUUID.genRandom());
			idap.setIdaId(entry.getKey());
			idap.setName("expression");
			idap.setValue(entry.getValue());
			idaPropList.add(idap);
		}
	}

	public void appendResponseNodeChilds(Node responseNode) {
		expressionMap.clear();
		// 如果为SOAP的报文类型，需要添加SvcBody节点
		if (interfaceMsgType.equalsIgnoreCase("soap")) {
			// 添加SvcBody节点
			Node SvcBodyResponseNode = new Node();
			SvcBodyResponseNode.setId(GenerateUUID.genRandom());
			SvcBodyResponseNode.setNodeId(svcBody);
			SvcBodyResponseNode.setParentId(responseNode.getId());
			responseNode.appendChild(SvcBodyResponseNode);
			this.renderNode(SvcBodyResponseNode, response);
		} else {
			this.renderNode(responseNode, response);
		}
		// 处理expresssion表达式Map
		for (Map.Entry<String, String> entry : expressionMap.entrySet()) {
			IdaPROP idap = new IdaPROP();
			idap.setId(GenerateUUID.genRandom());
			idap.setIdaId(entry.getKey());
			idap.setName("expression");
			idap.setValue(entry.getValue());
			idaPropList.add(idap);
		}
	}

	// 递归增加节点
	public void renderNode(Node node, String renderType) {
		boolean flag = true;
		// 页面右侧SDA数据的remark 和 type
		String remark = excelTool
				.getCellContent(interfaceSheet, structIndex, 9);
		String type = excelTool.getCellContent(interfaceSheet, structIndex, 7);
		Node childNode;
		// request递归出口
		if (request.equals(renderType)) {
			if (outputStr.equals(excelTool.getCellContent(interfaceSheet,
					structIndex, 0))) {
				flag = false;
			}
		}
		// response递归出口
		if (response.equals(renderType)) {
			if ("".equals(excelTool.getCellContent(interfaceSheet, structIndex,
					6))) {
				flag = false;
			}
		}
		if (flag) {
			if (null != remark) {
				remark = remark.trim();
				if (remark.toLowerCase().contains("svcheader")) {
					// 添加IDAPROP属性信息
					IdaPROP idap = new IdaPROP();
					idap.setId(GenerateUUID.genRandom());
					idap.setIdaId(node.getId());
					idap.setName("isSdoHeader");
					idap.setValue("true");
					idap.setSeq("1");
					idaPropList.add(idap);
					structIndex++;
					renderNode(node, renderType);
				}
			}
			// 不映射的元数据行直接转到下一行处理struct数组类型
			if (remark.toLowerCase().startsWith("start")
					&& type.toLowerCase().equals("struct")) {
				structIndex++;
				renderNode(node, renderType);
			}
			if (remark.toLowerCase().startsWith("end")
					&& type.toLowerCase().equals("struct")) {
				structIndex++;
				renderNode(node, renderType);
			}
			// 非数组节点
			if ("".equals(remark)
					|| (!remark.toLowerCase().startsWith("start") && !remark
							.toLowerCase().startsWith("end"))) {
				childNode = this.createChildNode(node);
				node.appendChild(childNode);
				handleMapFileAndCode(childNode);
				structIndex++;
				renderNode(node, renderType);
			}
			// array数组处理
			if (remark.toLowerCase().startsWith("start")
					&& type.toLowerCase().equals("array")) {
				childNode = this.createChildNode(node);
				node.appendChild(childNode);
				handleMapFileAndCode(childNode);
				structIndex++;
				renderNode(childNode, renderType);
			}
			if (remark.toLowerCase().startsWith("end")
					&& type.toLowerCase().equals("array")) {
				structIndex++;
				renderNode(node.getParentNode(), renderType);
			}
		}
	}

	// 根据structIndex、node生成ChildNode节点
	public Node createChildNode(Node node) {
		String structId = excelTool.getCellContent(interfaceSheet, structIndex,
				0);
		String structAlias = excelTool.getCellContent(interfaceSheet,
				structIndex, 1);
		String metadataId = excelTool.getCellContent(interfaceSheet,
				structIndex, 6);
		String isRequired = excelTool.getCellContent(interfaceSheet,
				structIndex, 3);
		String type = excelTool.getCellContent(interfaceSheet, structIndex, 2)
				.toLowerCase();
		String remark = excelTool
				.getCellContent(interfaceSheet, structIndex, 4).toLowerCase();
		Node childNode = new Node();
		childNode.setId(GenerateUUID.genRandom());
		childNode.setNodeId(structId);
		childNode.setNodeAlias(structAlias);
		childNode.setMetadataId(metadataId);
		if (remark.startsWith("start")) {
			childNode.setNodeType("array");
		} else if (type.equals("struct")) {
			childNode.setNodeType("struct");
		} else {
			childNode.setNodeType(Utils.getDataType(type));
			childNode.setNodeLength(Utils.getDataLength(type));
			childNode.setNodeScale(Utils.getDataScale(type));
		}
		childNode.setNodeRequire(isRequired);
		childNode.setParentId(node.getId());
		childNode.setNodeRemark(remark);
		childNode.setParentNode(node);
		return childNode;
	}

	// 处理标准代码和mapfile
	public void handleMapFileAndCode(Node node) {
		String nodeRemark = excelTool.getCellContent(interfaceSheet,
				structIndex, 4);
		String convertCodeLabel = excelTool.getCellContent(interfaceSheet,
				structIndex, 10);
		String serviceRemark = excelTool.getCellContent(interfaceSheet,
				structIndex, 9);
		String nodeMetadataId = excelTool.getCellContent(interfaceSheet,
				structIndex, 6);
		String id = node.getId();
		String slaveCode = null;
		String masterCode = null;
		String[] nodeRemarks = null;
		if (null != nodeRemark) {
			nodeRemarks = nodeRemark.split("\\n");
		}
		if (null != nodeRemarks) {
			for (String remark : nodeRemarks) {
				if (remark.toLowerCase().startsWith("mapfile")) {
					StringTokenizer stringTokenizer = new StringTokenizer(
							remark, ":");
					if (stringTokenizer.countTokens() < 3)
						continue;
					stringTokenizer.nextElement();
					String mapfileNames = stringTokenizer.nextToken();
					String veb = stringTokenizer.nextToken();
					if (veb.toLowerCase().equals("start")) {
						String[] mapfileNameArr = mapfileNames.split(",");
						for (String mapFileName : mapfileNameArr) {
							mapFileNamesOfCurrentElement.add(mapFileName);
						}
					}
					if (veb.toLowerCase().equals("end")) {
						String[] mapfileNameArr = mapfileNames.split(",");
						for (String mapFileName : mapfileNameArr) {
							if (mapFileNamesOfCurrentElement
									.contains(mapFileName)) {
								mapFileNamesOfCurrentElement
										.remove(mapFileName);
							}
						}
					}
				} else if (remark.startsWith("映射")) {
					String totleinfo = remark.split(":")[1];
					String[] infos = totleinfo.split(";");
					Pattern pattern = Pattern.compile("操作标志(.*?)输出(.*?)");
					for (String info : infos) {
						Matcher matcher = pattern.matcher(info);
						if (matcher.matches() && matcher.groupCount() >= 2) {
							String mapKey = matcher.group(1);
							String mapValue = matcher.group(2);
							resultMap.put(mapKey, mapValue);
						}
					}
				}
				if (null != convertCodeLabel && null == slaveCode) {
					if (convertCodeLabel.startsWith("需要做代码转换")
							|| convertCodeLabel.startsWith("需做代码转换")) {
						slaveCode = getSlaveCodeFromRemarks(nodeRemarks);
					}
				}
			}
		}

		if (null != convertCodeLabel && null != serviceRemark) {
			masterCode = getMasterCodeFromRemarks(serviceRemark);
		}

		if (null != slaveCode && null != masterCode) {
			String serviceNodePath = getServiceNodePath(nodeMetadataId);
			if (null != serviceNodePath) {
				StringBuilder sb = new StringBuilder();
				sb.append("$codeConvert:");
				sb.append("(slaveCode:");
				sb.append(slaveCode);
				sb.append(",");
				sb.append("masterCode:");
				sb.append(masterCode);
				sb.append(",");
				sb.append("serviceNodePath:");
				sb.append(serviceNodePath);
				sb.append(",");
				sb.append("whateverItisa1:");
				sb.append("1");
				sb.append(")");
				expressionMap.put(id, sb.toString());
			}
		}

		for (String mapfile : mapFileNamesOfCurrentElement) {
			if (!expandMap.containsKey(id)) {
				expandMap.put(id, mapfile);
			} else {
				expandMap.put(id, expandMap.get(id) + "," + mapfile);
			}
		}
	}

	/**
	 * 根据接口描述获取标准从代码
	 * 
	 * @param remarks
	 * @return
	 */
	private String getSlaveCodeFromRemarks(String[] remarks) {
		String slaveCode = null;
		for (String remark : remarks) {
			remark = remark.trim();
			remark = remark.replace("，", "");
			remark = remark.replace(",", "");
			remark = remark.replace(" ", "");
			if (remark.contains("从代码")) {
				Pattern pattern = Pattern.compile("从代码(.*?)");
				Matcher matcher = pattern.matcher(remark);
				if (matcher.matches() && matcher.groupCount() > 0) {
					slaveCode = matcher.group(1);
				} else {
					log.info("无法解析从代码");
				}
			}
		}
		return slaveCode;
	}

	/**
	 * 根据接口描述获取标准主代码
	 * 
	 * @param remark
	 * @return
	 */
	private String getMasterCodeFromRemarks(String remark) {
		String masterCode = null;
		remark = remark.trim();
		remark = remark.replace("，", "");
		remark = remark.replace(",", "");
		remark = remark.replace("：", "");
		remark = remark.replace(":", "");
		remark = remark.replace(" ", "");
		remark = remark.replace("\n", "");
		remark = remark.replace("\r\n", "");
		if (remark.contains("见标准代码")) {
			Pattern pattern = Pattern.compile("见标准代码(.*?)");
			Matcher matcher = pattern.matcher(remark);
			if (matcher.matches() && matcher.groupCount() > 0) {
				masterCode = matcher.group(1);
			} else {
				log.info("无法解析主代码");
			}
		}
		return masterCode;
	}

	/**
	 * 获取服务节点的深度根路径
	 * 
	 * @param serviceNodeName
	 * @return
	 */
	private String getServiceNodePath(String serviceNodeName) {
		String serviceNodePath = null;
		if (null != serviceNodeName
				&& GlobalMap.globaNodePathMap.containsKey(serviceNodeName)) {
			serviceNodePath = GlobalMap.globaNodePathMap.get(serviceNodeName);
		}
		return serviceNodePath;
	}
}
