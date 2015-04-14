package com.dc.esb.servicegov.refactoring.resource.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dc.esb.servicegov.refactoring.resource.util.ExcelHelper;
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
import com.dc.esb.servicegov.refactoring.util.GlobalImport;
import com.dc.esb.servicegov.refactoring.util.GlobalMap;
import com.dc.esb.servicegov.refactoring.util.GlobalMenuId;
import com.dc.esb.servicegov.refactoring.util.ServiceStateUtils;
import com.dc.esb.servicegov.refactoring.util.UserOperationLogUtil;
import com.dc.esb.servicegov.refactoring.util.Utils;

@Service
@Transactional(rollbackFor = Exception.class)
public class InterfaceParse implements IParse {

    private Log log = LogFactory.getLog(InterfaceParse.class);

    private ExcelTool excelTool = ExcelTool.getInstance();
    private static final String INIT_VERSION = "1.0.0";
    private static final String OUTPUT_LABEL = "输出";
    private static final String SVCBODY = "SvcBody";
    private static final String ROOT_LABEL = "root";
    private static final String REQUEST = "REQUEST";
    private static final String RESPONSE = "RESPONSE";

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
    public boolean parse(Row row, Sheet interfaceSheet) {
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
        return true;
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
        if (GlobalImport.operateFlag) {
            Interface tmpi = interfaceDAO.findUniqueBy("interfaceId", interfaceId);
            if (tmpi != null) {
                i.setVersion(Utils.modifyversionno(tmpi.getVersion()));
                i.setState(tmpi.getState());
            } else {
                i.setVersion(INIT_VERSION);
                i.setState(ServiceStateUtils.DEFINITION);
            }
        } else {
            Interface tmpi = interfaceDAO.findUniqueBy("interfaceId", interfaceId);
            if (tmpi != null) {
                i.setVersion(tmpi.getVersion());
                i.setState(tmpi.getState());
            } else {
                i.setVersion(INIT_VERSION);
                i.setState(ServiceStateUtils.DEFINITION);
            }
        }
        i.setThrough(through);
        i.setModifyUser("");
        i.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        interfaceDAO.TxSaveInterface(i);
        log.info("insert interface finished!");
    }

    public void insertIDA() {
        Node sdaNode = this.getIDANodes();
        seq = 0;
        list = new ArrayList<IDA>();
        // 先删除sda数据
        if (idaPropDAO.delIdaPROPByInterfaceId(interfaceId)) {
            if (idaDAO.delIDAByInterfaceId(interfaceId)) {
                renderInsertIDANode(sdaNode);
                log.info("merge Ida list finished！");
                if (idaDAO.batchInsertIDAs(list)) {
                    log.info("import Ida info finished");
                    idaPropDAO.batchIdaPropList(idaPropList);
                    log.info("import ida_prop infos finished");
                }
            }
        }
        log.info("insert IDA finished!");
    }

    // 递归插入node
    public void renderInsertIDANode(Node node) {
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
        list.add(ida);
        if (node.hasChild()) {
            for (Node childNode : node.getChildNodes()) {
                renderInsertIDANode(childNode);
            }
        }
    }

    public Node getIDANodes() {
        // 根节点
        Node root = new Node();
        String rootId = GenerateUUID.genRandom();
        root.setId(rootId);
        root.setNodeId(ROOT_LABEL);
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
        String remark = ExcelHelper.getIDARemark(interfaceSheet, structIndex);
        // 左边无数据 “输入”下一行为空 “输入”下一行为“输出”，继续判断“输出”下一行是否为空
        if ("".equals(excelTool.getCellContent(interfaceSheet, structIndex, 0))
                && !"不映射".equals(remark)
                && !"给空白".equals(remark)
                && !"不映射".equals(remark)) {

        } else if (OUTPUT_LABEL.equals(excelTool.getCellContent(interfaceSheet,
                structIndex + 1, 0))
                && "".equals(excelTool.getCellContent(interfaceSheet,
                structIndex + 2, 0))) {
        } else {
            // 添加request的childs
            this.appendRequestNodeChilds(requestNode);
            // 添加response的childs
            this.appendResponseNodeChilds(responseNode);
        }
        // 处理扩展属

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
        // 处理root 和 RESPONSE 的扩展属性
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
            SvcBodyRequestNode.setNodeId(SVCBODY);
            SvcBodyRequestNode.setParentId(requestNode.getId());
            requestNode.appendChild(SvcBodyRequestNode);
            this.renderNode(SvcBodyRequestNode, REQUEST);
        } else {
            this.renderNode(requestNode, REQUEST);
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
            SvcBodyResponseNode.setNodeId(SVCBODY);
            SvcBodyResponseNode.setParentId(responseNode.getId());
            responseNode.appendChild(SvcBodyResponseNode);
            this.renderNode(SvcBodyResponseNode, RESPONSE);
        } else {
            this.renderNode(responseNode, RESPONSE);
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
    public boolean renderNode(Node node, String renderType) {
        boolean flag = true;
        String remark = ExcelHelper.getIDARemark(interfaceSheet, structIndex);
        String imdtNode = ExcelHelper.getIDANodeID(interfaceSheet, structIndex);
        String type = ExcelHelper.getIDAType(interfaceSheet, structIndex);
        String structName = ExcelHelper.getIDANodeName(interfaceSheet, structIndex);
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
            if ("".equals(imdtNode) && !remark.contains("不映射") && !remark.contains("给空白")) {
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
                    return renderNode(node, renderType);
                }
            }
            // 不映射的元数据行直接转到下一行处理struct数组类型
            if (remark.contains("不映射") || remark.contains("给空白")) {
                structIndex++;
                return renderNode(node, renderType);
            }
            if (remark.toLowerCase().startsWith("start")
                    && type.toLowerCase().equals("struct")) {
                structIndex++;
                return renderNode(node, renderType);
            }
            if (remark.toLowerCase().startsWith("end")
                    && type.toLowerCase().equals("struct")) {
                structIndex++;
                return renderNode(node, renderType);
            }
            // 非数组节点
            if ("".equals(remark)
                    || (!remark.toLowerCase().startsWith("start") && !remark
                    .toLowerCase().startsWith("end"))) {
                if (!"".equals(imdtNode) && "".equals(type)) {
                    String message = "接口" + interfaceId + "元数据 [" + imdtNode + "]类型为空，导入失败！";
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
            // struct表对象数组处理
            if (remark.toLowerCase().startsWith("start")
                    && structName.contains("表对象")) {
                childNode = this.createChildNode(node);
                node.appendChild(childNode);
                structIndex++;
                return renderNode(childNode, renderType);
            }
            if (remark.toLowerCase().startsWith("end")
                    && structName.contains("表对象")) {
                structIndex++;
                return renderNode(node.getParentNode(), renderType);
            }
            // array数组处理
            if (remark.toLowerCase().startsWith("start")
                    && type.toLowerCase().equals("array")) {
                childNode = this.createChildNode(node);
                node.appendChild(childNode);
                structIndex++;
                return renderNode(childNode, renderType);
            }
            if (remark.toLowerCase().startsWith("end")
                    && type.toLowerCase().equals("array")) {
                structIndex++;
                return renderNode(node.getParentNode(), renderType);
            }
        }
        return true;
    }

    // 根据structIndex、node生成ChildNode节点
    public Node createChildNode(Node node) {
        String structId = ExcelHelper.getIDANodeID(interfaceSheet, structIndex);
        String structAlias = ExcelHelper.getIDANodeName(interfaceSheet, structIndex);
        String metadataId = ExcelHelper.getSDAMetadata(interfaceSheet, structIndex);
        String isRequired = ExcelHelper.getIDARequired(interfaceSheet, structIndex);
        String type = ExcelHelper.getIDAType(interfaceSheet, structIndex);
        String remark = ExcelHelper.getIDARemark(interfaceSheet, structIndex);
        String length = ExcelHelper.getIDALength(interfaceSheet, structIndex);
        if (remark.length() >= 2000) {
            remark = remark.substring(0, 1999);
        }
        Node childNode = new Node();
        childNode.setId(GenerateUUID.genRandom());
        childNode.setNodeId(structId);
        childNode.setNodeAlias(structAlias);
        childNode.setMetadataId(metadataId);
        if (type.toUpperCase().equals(("D"))) {
            childNode.setNodeType("decimal");
        } else {
            if (remark.toLowerCase().startsWith("start")) {
                childNode.setNodeType("array");
            } else if (type.toLowerCase().equals("struct")) {
                childNode.setNodeType("struct");
            } else {
                childNode.setNodeType(type);
                childNode.setNodeLength(Utils.getDataLengthFromLength(length));
                childNode.setNodeScale(Utils.getDataScaleFromLength(length));
            }
        }
        childNode.setNodeRequire(isRequired);
        childNode.setParentId(node.getId());
        childNode.setNodeRemark(remark);
        childNode.setParentNode(node);
        return childNode;
    }
}
