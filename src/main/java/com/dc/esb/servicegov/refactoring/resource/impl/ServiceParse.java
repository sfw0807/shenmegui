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
import com.dc.esb.servicegov.refactoring.dao.impl.ServiceHeadRelateDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.OLA;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.SDA;
import com.dc.esb.servicegov.refactoring.entity.ServiceHeadRelate;
import com.dc.esb.servicegov.refactoring.resource.IParse;
import com.dc.esb.servicegov.refactoring.resource.node.Node;
import com.dc.esb.servicegov.refactoring.util.AuditUtil;
import com.dc.esb.servicegov.refactoring.util.ExcelTool;
import com.dc.esb.servicegov.refactoring.util.GenerateUUID;
import com.dc.esb.servicegov.refactoring.util.GlobalImport;
import com.dc.esb.servicegov.refactoring.util.GlobalMap;
import com.dc.esb.servicegov.refactoring.util.GlobalMenuId;
import com.dc.esb.servicegov.refactoring.util.ServiceStateUtils;
import com.dc.esb.servicegov.refactoring.util.UserOperationLogUtil;
import com.dc.esb.servicegov.refactoring.util.Utils;

@Service
@Transactional(rollbackFor = Exception.class)
public class ServiceParse implements IParse {

	private Log log = LogFactory.getLog(ServiceParse.class);

	private ExcelTool excelTool = ExcelTool.getInstance();
	private String initVersion;
	private String initOperationVersion;
	private String initOperationState;
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
	@Autowired
	private ServiceHeadRelateDAOImpl serviceHeadDAO;
	

	private Sheet interfaceSheet;

	private String serviceId;
	private String serviceName;
	private String serviceRemark;
	private String operationId;
	private String operationName;
	private String operationRemark;
	private String categoryId;
	private String shead;
	private List<SDA> list;
	private String prefix;
	private String duplicatePrefix;

	@Override
	public boolean parse(Row row, Sheet interfaceSheet) {
		GlobalMap.globaNodePathMap.clear();
		GlobalMap.duplicatePathMap.clear();
		// TODO Auto-generated method stub
		this.interfaceSheet = interfaceSheet;
		this.initVersion = "1.0.0";
		this.initOperationVersion = "1.0.0";
		this.initOperationState = ServiceStateUtils.DEFINITION;
		String serviceIdAndName = excelTool
				.getCellContent(interfaceSheet, 0, 7);
		serviceIdAndName = serviceIdAndName.replace("（", "(");
		serviceIdAndName = serviceIdAndName.replace("）", ")");
		shead = excelTool.getCellContent(row.getCell(19));
		if("".equals(shead)){
			shead = "SHEAD";
		}
		serviceId = serviceIdAndName.substring(
				serviceIdAndName.indexOf("(") + 1,
				serviceIdAndName.length() - 1);
		String indexServiceIdAndName = excelTool.getCellContent(row.getCell(2));
		indexServiceIdAndName = indexServiceIdAndName.replace("（", "(");
		indexServiceIdAndName = indexServiceIdAndName.replace("）", ")");
		String indexServiceId = indexServiceIdAndName.substring(
				indexServiceIdAndName.indexOf("(") + 1,
				indexServiceIdAndName.length() - 1);
		// 判断index也serviceId 和 元数据页的serviceId是否相同
		if(!serviceId.equals(indexServiceId)){
			log.error("服务" + serviceId + operationId + "[index页]和[元数据页]服务Id不相同，导入失败!");
			UserOperationLogUtil.saveLog("服务" + serviceId + operationId + "[index页]和[元数据页]服务Id不相同，导入失败!", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
			GlobalImport.flag = false;
			return false;
		}
		
		if ("".equals(serviceId)) {
			log.error("服务" + serviceId + operationId + "元数据页[服务Id]不能为空!");
			UserOperationLogUtil.saveLog("服务" + serviceId + operationId + "元数据页[服务Id]不能为空!", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
			GlobalImport.flag = false;
			return false;
		}
		serviceName = serviceIdAndName.substring(0, serviceIdAndName
				.indexOf("("));
		operationId = excelTool.getCellContent(row.getCell(3));
		if ("".equals(operationId)) {
			log.error("服务" + serviceId + operationId + "元数据页[操作Id]不能为空!");
			UserOperationLogUtil.saveLog("服务" + serviceId + operationId + "元数据页[操作Id]不能为空!", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
			GlobalImport.flag = false;
			return false;
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
			// insert svcheader
			insertServiceHeader();
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
		return true;
	}

	public void insertService() {
		com.dc.esb.servicegov.refactoring.entity.Service service = new com.dc.esb.servicegov.refactoring.entity.Service();
		service.setServiceId(serviceId);
		service.setServiceName(serviceName);
		service.setServiceRemark(serviceRemark);
		service.setCategoryId(categoryId);
		if(GlobalImport.operateFlag){
			com.dc.esb.servicegov.refactoring.entity.Service tmpService = serviceDAO.findUniqueBy("serviceId", serviceId);
			if(tmpService != null){
			    if(AuditUtil.passed.equals(tmpService.getAuditState())){
			    	service.setVersion(Utils.modifyversionno(tmpService.getVersion()));
			    }
			    else{
			    	service.setVersion(tmpService.getVersion());
			    }
				service.setState(tmpService.getState());
			}
			else{
				service.setVersion(initVersion);
			    service.setState(ServiceStateUtils.DEFINITION);
			}
		}
		else{
			com.dc.esb.servicegov.refactoring.entity.Service tmpService = serviceDAO.findUniqueBy("serviceId", serviceId);
			if(tmpService != null){
				service.setVersion(tmpService.getVersion());
				service.setState(tmpService.getState());
			}
			else{
				service.setVersion(initVersion);
			    service.setState(ServiceStateUtils.DEFINITION);
			}
		}
		service.setAuditState(AuditUtil.submit);
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
		if(GlobalImport.operateFlag){
		   Operation tmpOperation = operationDAO.getOperation(operationId, serviceId);
		   if(tmpOperation != null){
			   if(AuditUtil.passed.equals(tmpOperation.getAuditState())){
				   operation.setVersion(Utils.modifyversionno(tmpOperation.getVersion()));
				   this.initOperationVersion = Utils.modifyversionno(tmpOperation.getVersion());
			   }
			   else{
				   operation.setVersion(tmpOperation.getVersion());
				   this.initOperationVersion = tmpOperation.getVersion();
			   }
			   operation.setState(tmpOperation.getState());
			   this.initOperationState = tmpOperation.getState();
		   }
		   else{
			   operation.setVersion(initOperationVersion);
			   operation.setState(initOperationState);
		   }
		}
		else{
			Operation tmpOperation = operationDAO.getOperation(operationId, serviceId);
			   if(tmpOperation != null){
				   operation.setVersion(tmpOperation.getVersion());
				   operation.setState(tmpOperation.getState());
				   this.initOperationVersion = tmpOperation.getVersion();
				   this.initOperationState = tmpOperation.getState();
			   }
			   else{
				   operation.setVersion(initOperationVersion);
				   operation.setState(initOperationState);
			   }
		}
		operation.setAuditState(AuditUtil.submit);
		operation.setModifyUser("");
		operation.setUpdateTime(Utils.getTime());
		operationDAO.TxSaveOperation(operation);
		log.info("insert operation finished!");
	}
	
	public void insertServiceHeader(){
		ServiceHeadRelate svcheader = new ServiceHeadRelate();
		svcheader.setSheadId(shead);
		svcheader.setServiceId(serviceId);
		serviceHeadDAO.txSaveSvcHeader(svcheader);
		log.info("insert service header finished!");
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
		duplicatePrefix = request + ".";
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
		duplicatePrefix = response + ".";
		this.renderNode(SvcBodyResponseNode, response);
	}

	// 递归增加节点
	public boolean renderNode(Node node, String renderType) {
		boolean flag = true;
		String remark = excelTool
				.getCellContent(interfaceSheet, structIndex, 9);
		String type = excelTool.getCellContent(interfaceSheet, structIndex, 7)
				.toLowerCase();
		type = type.replace("（", "(");
		type = type.replace("）", ")");
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
			if("".equals(metadataId)){
				structIndex++;
				renderNode(node, renderType);
			}
			else{
				// 服务头当前行跳过
				if (null != remark) {
					remark = remark.trim();
					remark = remark.toUpperCase();
					if (remark.contains("SVCHEADER")) {
						structIndex++;
						return renderNode(node, renderType);
					}
				}
				//不映射的sda并且不是struct数组类型
				if(mapfileRemark.equals("不映射") && !type.toLowerCase().equals("struct")){
					structIndex++;
					return renderNode(node, renderType);
				}
				// 元数据不为空 类型为空 跳过
				if(!"".equals(metadataId) && "".equals(type)){
					structIndex++;
					return renderNode(node, renderType);
				}
				// mapfile重复行不记录
				if("duplicate".equals(mapfileRemark)){
					structIndex++;
					return renderNode(node, renderType);
				}
				// 判断元数据是否存在
				if(!metadataDAO.checkIsExist(metadataId)){
					String message = "服务" + serviceId + operationId + "元数据 [" + metadataId + "]不存在，导入失败！";
					log.error(message);
					UserOperationLogUtil.saveLog(message, GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
					GlobalImport.flag = false;
					structIndex++;
					return renderNode(node, renderType);
				}
				// 判断是否存在重复的元数据duplicatePrefix
				if(GlobalMap.duplicatePathMap.containsKey(duplicatePrefix + metadataId)){
							String message = "服务" + serviceId + operationId + "元数据 [" + metadataId + "]存在重复，导入失败！";
							log.error(message);
							UserOperationLogUtil.saveLog(message, GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
							GlobalImport.flag = false;
							structIndex++;
							return renderNode(node, renderType);
						}
						else{
							GlobalMap.duplicatePathMap.put(duplicatePrefix + metadataId, metadataId);
				}
				// 记录节点对应的nodePath,用于代码转换。
				GlobalMap.globaNodePathMap.put(metadataId, prefix + metadataId);
				// 非数组节点
				if ("".equals(remark)
						|| (!remark.toLowerCase().startsWith("start") && !remark
								.toLowerCase().startsWith("end"))) {
					if(!"".equals(metadataId) && "".equals(type)){
						String message = "服务" + serviceId + operationId + "元数据 [" + metadataId + "]类型为空，导入失败！";
						log.error(message);
						UserOperationLogUtil.saveLog(message, GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
						GlobalImport.flag = false;
						return true;
					}
					childNode = this.createChildNode(node);
					node.appendChild(childNode);
					structIndex++;
					return renderNode(node, renderType);
				}
				// array 和 struct 数组处理
				if (remark.toLowerCase().startsWith("start")
						&& "struct".equals(type)) {
					childNode = this.createChildNode(node);
					childNode.setNodeType("");
					node.appendChild(childNode);
					prefix = prefix + metadataId + ".";
					duplicatePrefix = duplicatePrefix + metadataId + ".";
					structIndex++;
					return renderNode(childNode, renderType);
				}
				if (remark.toLowerCase().startsWith("end") && "struct".equals(type)) {
					prefix = getLastPrefix(prefix);
					duplicatePrefix = getLastPrefix(duplicatePrefix);
					structIndex++;
					return renderNode(node.getParentNode(), renderType);
				}
				if (remark.toLowerCase().startsWith("start")
						&& "array".equals(type)) {
					childNode = this.createChildNode(node);
					node.appendChild(childNode);
					prefix = prefix + metadataId + ".";
					duplicatePrefix = duplicatePrefix + metadataId + ".";
					structIndex++;
					return renderNode(childNode, renderType);
				}
				if (remark.toLowerCase().startsWith("end") && "array".equals(type)) {
					prefix = getLastPrefix(prefix);
					duplicatePrefix = getLastPrefix(duplicatePrefix);
					structIndex++;
					return renderNode(node.getParentNode(), renderType);
				}
			}
		}
		return true;
	}

	// 根据structIndex、node生成ChildNode节点
	public Node createChildNode(Node node) {
		String metadataId = excelTool.getCellContent(interfaceSheet,
				structIndex, 6);
		String isRequired = excelTool.getCellContent(interfaceSheet,
				structIndex, 3);
		if(!"".equals(isRequired) && !"Y".equals(isRequired) && !"N".equals(isRequired)){
			log.error("服务" + serviceId + operationId + "元数据页[是否必输]列只能是'Y'或'N' !");
			UserOperationLogUtil.saveLog("服务" + serviceId + operationId + "元数据页[是否必输]列只能是'Y'或'N' !", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
			GlobalImport.flag = false;
			return null;
		}
		String type = excelTool.getCellContent(interfaceSheet, structIndex, 7)
				.toLowerCase();
		type = type.replace("（", "(");
		type = type.replace("）", ")");
		if (type.indexOf("(") > 0) {
			type = type.substring(0, type.indexOf("("));
		}
		String remark = excelTool
				.getCellContent(interfaceSheet, structIndex, 9);
		if(remark.length() >= 2000){
			remark = remark.substring(0,1999);
		}
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
