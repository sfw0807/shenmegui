package com.dc.esb.servicegov.refactoring.resource.impl;

import com.dc.esb.servicegov.refactoring.dao.impl.*;
import com.dc.esb.servicegov.refactoring.entity.OLA;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.SDA;
import com.dc.esb.servicegov.refactoring.entity.ServiceHeadRelate;
import com.dc.esb.servicegov.refactoring.resource.IParse;
import com.dc.esb.servicegov.refactoring.resource.node.Node;
import com.dc.esb.servicegov.refactoring.resource.util.ExcelHelper;
import com.dc.esb.servicegov.refactoring.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ServiceParse implements IParse {

    private static final Log log = LogFactory.getLog(ServiceParse.class);

    private static final ExcelTool excelTool = ExcelTool.getInstance();
    private String initVersion;
    private String initOperationVersion;
    private String initOperationState;
    private static final String OUTPUT_LABEL = "输出";
    private static final String SVC_BODY = "SvcBody";
    private static final String REQUEST = "REQUEST";
    private static final String RESPONSE = "RESPONSE";

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
        this.interfaceSheet = interfaceSheet;
        this.initVersion = "1.0.0";
        this.initOperationVersion = "1.0.0";
        this.initOperationState = ServiceStateUtils.DEFINITION;
        //获取服务ID加服务名称
        String serviceIdAndName = excelTool
                .getCellContent(interfaceSheet, 0, 8);
        serviceIdAndName = serviceIdAndName.replace("（", "(");
        serviceIdAndName = serviceIdAndName.replace("）", ")");
        String indexServiceIdAndName = excelTool.getCellContent(row.getCell(2));
        indexServiceIdAndName = indexServiceIdAndName.replace("（", "(");
        indexServiceIdAndName = indexServiceIdAndName.replace("）", ")");

        //获取报文头
        shead = excelTool.getCellContent(row.getCell(19));
        if ("".equals(shead)) {
            shead = "SHEAD";
        }
        //获取服务ID
        serviceId = serviceIdAndName.substring(
                serviceIdAndName.indexOf("(") + 1,
                serviceIdAndName.length() - 1);
        if ("".equals(serviceId)) {
            log.error("服务" + serviceId + operationId + "元数据页[服务Id]不能为空!");
            UserOperationLogUtil.saveLog("服务" + serviceId + operationId + "元数据页[服务Id]不能为空!", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
            GlobalImport.flag = false;
            return false;
        }
        String indexServiceId = indexServiceIdAndName.substring(
                indexServiceIdAndName.indexOf("(") + 1,
                indexServiceIdAndName.length() - 1);
        // 判断index也serviceId 和 元数据页的serviceId是否相同
        if (!serviceId.equals(indexServiceId)) {
            log.error("服务" + serviceId + operationId + "[index页]和[元数据页]服务Id不相同，导入失败!");
            UserOperationLogUtil.saveLog("服务" + serviceId + operationId + "[index页]和[元数据页]服务Id不相同，导入失败!", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
            GlobalImport.flag = false;
            return false;
        }
        //获取服务名称
        serviceName = serviceIdAndName.substring(0, serviceIdAndName
                .indexOf("("));
        //获取场景ID
        operationId = excelTool.getCellContent(row.getCell(3));
        if ("".equals(operationId)) {
            log.error("服务" + serviceId + operationId + "元数据页[操作Id]不能为空!");
            UserOperationLogUtil.saveLog("服务" + serviceId + operationId + "元数据页[操作Id]不能为空!", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
            GlobalImport.flag = false;
            return false;
        }
        //获取场景名称
        operationName = excelTool.getCellContent(row.getCell(4));
        //获取服务备注
        serviceRemark = excelTool.getCellContent(interfaceSheet, 2, 7);
        //获取场景备注
        operationRemark = excelTool.getCellContent(interfaceSheet, 3, 7);
        // 获取服务分组
        categoryId = serviceId.substring(0, 5);
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
            structIndex = 7;
            insertSDA();
            log.info("import service infos finished!");
        } catch (Exception e) {
            log.error("import service Infos error!", e);
        }
        return true;
    }

    private void insertService() {
        com.dc.esb.servicegov.refactoring.entity.Service service = new com.dc.esb.servicegov.refactoring.entity.Service();
        service.setServiceId(serviceId);
        service.setServiceName(serviceName);
        service.setServiceRemark(serviceRemark);
        service.setCategoryId(categoryId);
        //operateFlag: true为覆盖导入,false为不覆盖导入
        if (GlobalImport.operateFlag) {
            com.dc.esb.servicegov.refactoring.entity.Service tmpService = serviceDAO.findUniqueBy("serviceId", serviceId);
            if (tmpService != null) {
                if (AuditUtil.passed.equals(tmpService.getAuditState())) {
                    service.setVersion(Utils.modifyversionno(tmpService.getVersion()));
                } else {
                    service.setVersion(tmpService.getVersion());
                }
                service.setState(tmpService.getState());
            } else {
                service.setVersion(initVersion);
                service.setState(ServiceStateUtils.DEFINITION);
            }
        } else {
            com.dc.esb.servicegov.refactoring.entity.Service tmpService = serviceDAO.findUniqueBy("serviceId", serviceId);
            if (tmpService != null) {
                service.setVersion(tmpService.getVersion());
                service.setState(tmpService.getState());
            } else {
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

    private void insertOperation() {
        Operation operation = new Operation();
        operation.setOperationId(operationId);
        operation.setOperationName(operationName);
        operation.setRemark(operationRemark);
        operation.setServiceId(serviceId);
        if (GlobalImport.operateFlag) {
            Operation tmpOperation = operationDAO.getOperation(operationId, serviceId);
            if (tmpOperation != null) {
                if (AuditUtil.passed.equals(tmpOperation.getAuditState())) {
                    operation.setVersion(Utils.modifyversionno(tmpOperation.getVersion()));
                    this.initOperationVersion = Utils.modifyversionno(tmpOperation.getVersion());
                } else {
                    operation.setVersion(tmpOperation.getVersion());
                    this.initOperationVersion = tmpOperation.getVersion();
                }
                operation.setState(tmpOperation.getState());
                this.initOperationState = tmpOperation.getState();
            } else {
                operation.setVersion(initOperationVersion);
                operation.setState(initOperationState);
            }
        } else {
            Operation tmpOperation = operationDAO.getOperation(operationId, serviceId);
            if (tmpOperation != null) {
                operation.setVersion(tmpOperation.getVersion());
                operation.setState(tmpOperation.getState());
                this.initOperationVersion = tmpOperation.getVersion();
                this.initOperationState = tmpOperation.getState();
            } else {
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

    private void insertServiceHeader() {
        ServiceHeadRelate svcheader = new ServiceHeadRelate();
        svcheader.setSheadId(shead);
        svcheader.setServiceId(serviceId);
        serviceHeadDAO.txSaveSvcHeader(svcheader);
        log.info("insert service header finished!");
    }

    private void insertOLA() {
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

    private void insertSDA() {
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
    private void renderInsertSDANode(Node node) {
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

    /**
     * 获取SDA树
     * @return
     */
    private Node getSDANodes() {
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
        requestNode.setNodeId(REQUEST);
        requestNode.setParentId(rootId);
        root.appendChild(requestNode);
        // RESPONSE 节点
        Node responseNode = new Node();
        String responseId = GenerateUUID.genRandom();
        responseNode.setId(responseId);
        responseNode.setNodeId(RESPONSE);
        responseNode.setParentId(rootId);
        root.appendChild(responseNode);
        // 添加request的childs
        this.appendRequestNodeChildren(requestNode);
        // 添加response的childs
        this.appendResponseNodeChildren(responseNode);
        log.info("root" + root.getId());
        return root;
    }

    private void appendRequestNodeChildren(Node requestNode) {
        // 添加SvcBody节点
        Node svcBodyRequestNode = new Node();
        svcBodyRequestNode.setId(GenerateUUID.genRandom());
        svcBodyRequestNode.setNodeId(SVC_BODY);
        svcBodyRequestNode.setParentId(requestNode.getId());
        requestNode.appendChild(svcBodyRequestNode);
        // 用来记录元数据对应的nodePath
        prefix = SVC_BODY + ".";
        duplicatePrefix = REQUEST + ".";
        this.renderNode(svcBodyRequestNode, REQUEST);
        // 转到[输出]行下一行，开始解析response节点信息
        structIndex++;
    }

    private void appendResponseNodeChildren(Node responseNode) {
        // 添加SvcBody节点
        Node SvcBodyResponseNode = new Node();
        SvcBodyResponseNode.setId(GenerateUUID.genRandom());
        SvcBodyResponseNode.setNodeId(SVC_BODY);
        SvcBodyResponseNode.setParentId(responseNode.getId());
        responseNode.appendChild(SvcBodyResponseNode);
        // 用来记录元数据对应的nodePath
        prefix = SVC_BODY + ".";
        duplicatePrefix = RESPONSE + ".";
        this.renderNode(SvcBodyResponseNode, RESPONSE);
    }

    // 递归增加节点
    private boolean renderNode(Node node, String renderType) {
        boolean flag = true;
        String remark = ExcelHelper.getSDARemark(interfaceSheet, structIndex);
        String type = ExcelHelper.getSDAType(interfaceSheet, structIndex);
        String metadataId = ExcelHelper.getSDAMetadata(interfaceSheet, structIndex);
        String required = ExcelHelper.getSDARequire(interfaceSheet, structIndex);
        Node childNode;
        // request递归出口
        if (REQUEST.equals(renderType)) {
            if (OUTPUT_LABEL.equals(excelTool.getCellContent(interfaceSheet,
                    structIndex, 0))) {
                flag = false;
            }
        }
        // response递归出口
        if (RESPONSE.equals(renderType)) {
            if ("".equals(ExcelHelper.getSDAMetadata(interfaceSheet, structIndex))) {
                flag = false;
            }
        }
        if (flag) {
            if ("".equals(metadataId)) {
                structIndex++;
                renderNode(node, renderType);
            } else {
                // 元数据不为空 类型为空 跳过
                if (!"".equals(metadataId) && "".equals(type)) {
                    structIndex++;
                    return renderNode(node, renderType);
                }
                // 判断元数据是否存在
                if (!metadataDAO.checkIsExist(metadataId)) {
                    String message = "服务" + serviceId + operationId + "元数据 [" + metadataId + "]不存在，导入失败！";
                    log.error(message);
                    UserOperationLogUtil.saveLog(message, GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
                    GlobalImport.flag = false;
                    structIndex++;
                    return renderNode(node, renderType);
                }
                // 判断是否存在重复的元数据duplicatePrefix
                if (GlobalMap.duplicatePathMap.containsKey(duplicatePrefix + metadataId)) {
                    String message = "服务" + serviceId + operationId + "元数据 [" + metadataId + "]存在重复，导入失败！";
                    log.error(message);
                    UserOperationLogUtil.saveLog(message, GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
                    GlobalImport.flag = false;
                    structIndex++;
                    return renderNode(node, renderType);
                } else {
                    GlobalMap.duplicatePathMap.put(duplicatePrefix + metadataId, metadataId);
                }
                // 记录节点对应的nodePath,用于代码转换。
                GlobalMap.globaNodePathMap.put(metadataId, prefix + metadataId);
                // 非数组节点
                if ("".equals(remark)
                        || (!remark.toLowerCase().startsWith("start") && !remark
                        .toLowerCase().startsWith("end"))) {
                    childNode = this.createChildNode(node, required, type, metadataId, remark);
                    node.appendChild(childNode);
                    structIndex++;
                    return renderNode(node, renderType);
                }
                // array 和 struct 数组处理
                if (remark.toLowerCase().startsWith("start")
                        && "struct".equals(type)) {
                    childNode = this.createChildNode(node, required, type, metadataId, remark);
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
                    childNode = this.createChildNode(node, required, type, metadataId, remark);
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
    private Node createChildNode(Node parentNode, String required, String type, String metadataId, String remark) {
        if (!"".equals(required) && !"Y".equals(required) && !"N".equals(required)) {
            log.error("服务" + serviceId + operationId + "元数据页[是否必输]列只能是'Y'或'N' !");
            UserOperationLogUtil.saveLog("服务" + serviceId + operationId + "元数据页[是否必输]列只能是'Y'或'N' !", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
            GlobalImport.flag = false;
            return null;
        }
        if (type.indexOf("(") > 0) {
            type = type.substring(0, type.indexOf("("));
        }
        if (remark.length() >= 2000) {
            remark = remark.substring(0, 1999);
        }
        Node childNode = new Node();
        childNode.setId(GenerateUUID.genRandom());
        childNode.setNodeId(metadataId);
        childNode.setMetadataId(metadataId);
        childNode.setNodeRequire(required);
        childNode.setNodeType(type);
        childNode.setParentId(parentNode.getId());
        childNode.setNodeRemark(remark);
        childNode.setParentNode(parentNode);
        return childNode;
    }

    /**
     * return the prefix before last add
     *
     * @return
     */
    private String getLastPrefix(String prefix) {
        if (null != prefix) {
            if (prefix.contains(".")) {
                prefix = "";
            } else {
                String prefixWithoutLastDot = prefix.substring(0,
                        prefix.lastIndexOf("."));
                if (prefixWithoutLastDot.contains(".")) {
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
