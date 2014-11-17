package com.dc.esb.servicegov.refactoring.resource.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.MetadataDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.OLADAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SDADAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.OLA;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.SDA;
import com.dc.esb.servicegov.refactoring.resource.IParse;
import com.dc.esb.servicegov.refactoring.resource.node.Node;
import com.dc.esb.servicegov.refactoring.util.ExcelTool;
import com.dc.esb.servicegov.refactoring.util.GenerateUUID;
import com.dc.esb.servicegov.refactoring.util.GlobalImport;
import com.dc.esb.servicegov.refactoring.util.GlobalMap;
import com.dc.esb.servicegov.refactoring.util.ServiceStateUtils;
import com.dc.esb.servicegov.refactoring.util.Utils;

@Service
@Transactional(rollbackFor = Exception.class)
public class ServiceParse implements IParse {

	private Log log = LogFactory.getLog(ServiceParse.class);

	private ExcelTool excelTool = ExcelTool.getInstance();
	private static final String initVersion = "1.0.0";
	private static final String outputStr = "输出";
	private static final String svcBody = "SvcBody";
	private static final String request = "request";
	private static final String response = "response";

	private int structIndex;
	private int seq;
	
	@Autowired
	private ServiceDAOImpl serviceDAO;
	@Autowired
	private OperationDAOImpl operationDAO;
	@Autowired
	private SDADAOImpl sdaDAO;
	@Autowired
	private MetadataDAOImpl metadataDAO;
	@Autowired
	private OLADAOImpl olaDAO;

	private Sheet interfaceSheet;

	private String serviceId;
	private String serviceName;
	private String serviceRemark;
	private String operationId;
	private String operationName;
	private String operationRemark;
	private String categoryId;
	private List<SDA> list;
	private String prefix;

	@Override
	public void parse(Row row, Sheet interfaceSheet) {
		GlobalMap.globaNodePathMap.clear();
		// TODO Auto-generated method stub
		this.interfaceSheet = interfaceSheet;
		String serviceIdAndName = excelTool
				.getCellContent(interfaceSheet, 0, 7);
		serviceIdAndName = serviceIdAndName.replace("（", "(");
		serviceIdAndName = serviceIdAndName.replace("）", ")");
		serviceId = serviceIdAndName.substring(
				serviceIdAndName.indexOf("(") + 1,
				serviceIdAndName.length() - 1);
		if ("".equals(serviceId)) {
			log.error("元数据页[服务Id]不能为空!");
			GlobalImport.flag = false;
			return;
		}
		serviceName = serviceIdAndName.substring(0, serviceIdAndName
				.indexOf("("));
		operationId = excelTool.getCellContent(row.getCell(3));
		if ("".equals(operationId)) {
			log.error("元数据页[操作Id]不能为空!");
			GlobalImport.flag = false;
			return;
		}
		operationName = excelTool.getCellContent(row.getCell(4));
		// 新文档格式
		if ("".equals(excelTool.getCellContent(interfaceSheet, 2, 0))) {
			serviceRemark = excelTool.getCellContent(interfaceSheet, 2, 7);
			operationRemark = excelTool.getCellContent(interfaceSheet, 3, 7);
		}
		// 获取服务分组
		categoryId = serviceId.substring(1, 6);
		try {
			// insert service
			insertService();
			// insert operation
			insertOperation();
			// insert ola
			insertOLA();
			// insert SDA;
			log.info("begin to import sevice SDA!");
			// 判断哪种格式的文档，从第7行或第5行开始解析SDA
			if("".equals(excelTool.getCellContent(interfaceSheet, 2, 0))){
				structIndex = 7;
			}
			else{
				structIndex = 5;
			}
			insertSDA();
			log.info("import service infos finished!");
		} catch (Exception e) {
			log.error("import service Infos error!", e);
		}
	}

	public void insertService() {
		com.dc.esb.servicegov.refactoring.entity.Service service = new com.dc.esb.servicegov.refactoring.entity.Service();
		service.setServiceId(serviceId);
		service.setServiceName(serviceName);
		service.setServiceRemark(serviceRemark);
		service.setCategoryId(categoryId);
		service.setVersion(initVersion);
		service.setState(ServiceStateUtils.DEFINITION);
		service.setModifyUser("");
		service.setUpdateTime(Utils.getTime());
		serviceDAO.TxSaveService(service);
		log.info("insert service finished!");
	}

	public void insertOperation() {
		Operation operation = new Operation();
		operation.setOperationId(operationId);
		operation.setOperationName(operationName);
		operation.setRemark(operationRemark);
		operation.setServiceId(serviceId);
		operation.setVersion(initVersion);
		operation.setState(ServiceStateUtils.DEFINITION);
		operation.setModifyUser("");
		operation.setUpdateTime(Utils.getTime());
		operationDAO.TxSaveOperation(operation);
		log.info("insert operation finished!");
	}
	
	public void insertOLA(){
		OLA ola = new OLA();
		ola.setOperationId(operationId);
		ola.setServiceId(serviceId);
		ola.setOlaName("wsdl_operation");
		ola.setOlaValue("true");
		ola.setOlaRemark("wsdl_operation");
		ola.setUpdateTime(Utils.getTime());
		ola.setModifyUser("");
		olaDAO.txSaveOla(ola);
		log.info("insert OLA finished!");
	}

	public void insertSDA() {
		Node sdaNode = this.getSDANodes();
		seq = 0;
		list = new ArrayList<SDA>();
		// 先删除sda数据
		if (sdaDAO.deleteSDAByServiceAndOperationId(serviceId, operationId)) {
			renderInsertSDANode(sdaNode);
			sdaDAO.batchInsertSDAs(list);
		}
		log.info("insert SDA finished!");
	}

	// 递归插入node
	public void renderInsertSDANode(Node node) {
		seq++;
		SDA sda = new SDA();
		sda.setId(node.getId());
		sda.setStructId(node.getNodeId());
		sda.setMetadataId(node.getMetadataId());
		sda.setType(node.getNodeType());
		sda.setSeq(seq);
		sda.setRequired(node.getNodeRequire());
		sda.setRemark(node.getNodeRemark());
		sda.setOperationId(operationId);
		sda.setServiceId(serviceId);
		sda.setParentId(node.getParentId());
		// 用户session获取
		sda.setModifyUser("");
		sda.setUpdateTime(Utils.getTime());
		list.add(sda);
//		log.info("SDA=Info ： " + node.getId() + "===" + node.getNodeId()
//				+ "===" + node.getMetadataId() + "===" + seq + "==="
//				+ operationId + "===" + serviceId + "===" + node.getParentId());
		if (node.hasChild()) {
			for (Node childNode : node.getChildNodes()) {
				renderInsertSDANode(childNode);
			}
		}
	}

	public Node getSDANodes() {
		// 根节点
		Node root = new Node();
		String rootId = GenerateUUID.genRandom();
		root.setId(rootId);
		root.setNodeId(operationId);
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
		// 添加request的childs
		this.appendRequestNodeChilds(requestNode);
		// 添加response的childs
		this.appendResponseNodeChilds(responseNode);
		log.info("root" + root.getId());
		return root;
	}

	public void appendRequestNodeChilds(Node requestNode) {
		// 添加SvcBody节点
		Node SvcBodyRequestNode = new Node();
		SvcBodyRequestNode.setId(GenerateUUID.genRandom());
		SvcBodyRequestNode.setNodeId(svcBody);
		SvcBodyRequestNode.setParentId(requestNode.getId());
		requestNode.appendChild(SvcBodyRequestNode);
		// 用来记录元数据对应的nodePath
		prefix = svcBody + ".";
		this.renderNode(SvcBodyRequestNode, request);
		// 转到[输出]行下一行，开始解析response节点信息
		structIndex++;
	}

	public void appendResponseNodeChilds(Node responseNode) {
		// 添加SvcBody节点
		Node SvcBodyResponseNode = new Node();
		SvcBodyResponseNode.setId(GenerateUUID.genRandom());
		SvcBodyResponseNode.setNodeId(svcBody);
		SvcBodyResponseNode.setParentId(responseNode.getId());
		responseNode.appendChild(SvcBodyResponseNode);
		// 用来记录元数据对应的nodePath
		prefix = svcBody + ".";
		this.renderNode(SvcBodyResponseNode, response);
	}

	// 递归增加节点
	public void renderNode(Node node, String renderType) {
		boolean flag = true;
		String remark = excelTool
				.getCellContent(interfaceSheet, structIndex, 9);
		String type = excelTool.getCellContent(interfaceSheet, structIndex, 7)
				.toLowerCase();
		String metadataId = excelTool.getCellContent(interfaceSheet,
				structIndex, 6);
		String mapfileRemark = excelTool.getCellContent(interfaceSheet, structIndex, 10);
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
			// 服务头当前行跳过
			if (null != remark) {
				remark = remark.trim();
				remark = remark.toUpperCase();
				if (remark.contains("SVCHEADER")) {
					structIndex++;
					renderNode(node, renderType);
				}
			}
			// mapfile重复行不记录
			if("duplicate".equals(mapfileRemark)){
				structIndex++;
				renderNode(node, renderType);
			}
			// 判断元数据是否存在
			if(!metadataDAO.checkIsExist(metadataId)){
				String message = "元数据 [" + metadataId + "不存在，导入失败！";
				try {
					throw new Exception(message);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 记录节点对应的nodePath。
			GlobalMap.globaNodePathMap.put(metadataId, prefix + metadataId);
			// 非数组节点
			if ("".equals(remark)
					|| (!remark.toLowerCase().startsWith("start") && !remark
							.toLowerCase().startsWith("end"))) {
				childNode = this.createChildNode(node);
				node.appendChild(childNode);
				structIndex++;
				renderNode(node, renderType);
			}
			// array 和 struct 数组处理
			if (remark.toLowerCase().startsWith("start")
					&& "struct".equals(type)) {
				childNode = this.createChildNode(node);
				childNode.setNodeType("");
				node.appendChild(childNode);
				prefix = prefix + metadataId + ".";
				structIndex++;
				renderNode(childNode, renderType);
			}
			if (remark.toLowerCase().startsWith("end") && "struct".equals(type)) {
				prefix = getLastPrefix(prefix);
				structIndex++;
				renderNode(node.getParentNode(), renderType);
			}
			if (remark.toLowerCase().startsWith("start")
					&& "array".equals(type)) {
				childNode = this.createChildNode(node);
				node.appendChild(childNode);
				prefix = prefix + metadataId + ".";
				structIndex++;
				renderNode(childNode, renderType);
			}
			if (remark.toLowerCase().startsWith("end") && "array".equals(type)) {
				prefix = getLastPrefix(prefix);
				structIndex++;
				renderNode(node.getParentNode(), renderType);
			}
		}
	}

	// 根据structIndex、node生成ChildNode节点
	public Node createChildNode(Node node) {
		String metadataId = excelTool.getCellContent(interfaceSheet,
				structIndex, 6);
		String isRequired = excelTool.getCellContent(interfaceSheet,
				structIndex, 3);
		if(!"".equals(isRequired) && !"Y".equals(isRequired) && !"N".equals(isRequired)){
			log.error("元数据页[是否必输]列只能是'Y'或'N' !");
			GlobalImport.flag = false;
			return null;
		}
		String type = excelTool.getCellContent(interfaceSheet, structIndex, 7)
				.toLowerCase();
		if (type.indexOf("(") > 0) {
			type = type.substring(0, type.indexOf("("));
		}
		String remark = excelTool
				.getCellContent(interfaceSheet, structIndex, 9);
		Node childNode = new Node();
		childNode.setId(GenerateUUID.genRandom());
		childNode.setNodeId(metadataId);
		childNode.setMetadataId(metadataId);
		childNode.setNodeRequire(isRequired);
		childNode.setNodeType(type);
		childNode.setParentId(node.getId());
		childNode.setNodeRemark(remark);
		childNode.setParentNode(node);
		return childNode;
	}
	
	/**
	 * return the prefix before last add
	 * 
	 * @return
	 */
	private String getLastPrefix(String prefix) {
		if (null != prefix) {
			if (prefix.indexOf(".") < 0) {
				prefix = "";
			} else {
				String prefixWithoutLastDot = prefix.substring(0,
						prefix.lastIndexOf("."));
				if (prefixWithoutLastDot.indexOf(".") < 0) {
					prefix = "";
				} else {
					prefix = prefixWithoutLastDot.substring(0,
							prefixWithoutLastDot.lastIndexOf(".") + 1);
				}
			}
		}
		return prefix;
	}
}
