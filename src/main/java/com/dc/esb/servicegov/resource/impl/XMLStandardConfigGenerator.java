package com.dc.esb.servicegov.resource.impl;

import com.dc.esb.servicegov.dao.impl.RemainingServiceDAOImpl;
import com.dc.esb.servicegov.entity.InvokeInfo;
import com.dc.esb.servicegov.resource.metadataNode.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.dc.esb.servicegov.util.PackerUnPackerConstants.CONSUMER;
import static com.dc.esb.servicegov.util.PackerUnPackerConstants.PROVIDER;

/**
 * Created by vincentfxz on 15/4/27.
 */
@Component
public class XMLStandardConfigGenerator extends AbstractGenerater {

    @Autowired
    private RemainingServiceDAOImpl remainingServiceDAO;
    @Autowired
    private ServiceDataFromDB serviceDataFromDB;

    private String prdMsgType;
    private String csmMsgType;
    private String serviceId;

    List<String> attrInculde;
    private List<File> defaultInterfaceFiles = null;

    private static final String IN_CONFIG_DIR_PATH = "in_config/metadata";
    private static final String OUT_CONFIG_DIR_PATH = "out_config/metadata";

    private static final Log log = LogFactory
            .getLog(SpdbSpecDefaultInterfaceGenerater.class);

    public XMLStandardConfigGenerator() {
        attrInculde = new ArrayList<String>();
        attrInculde.add("type=array");
        attrInculde.add("type=selfjoinarray");
        attrInculde.add("type=selfjoinchild");
        attrInculde.add("is_struct");
        attrInculde.add("metadataid");
        attrInculde.add("package_type");
        attrInculde.add("expression");
        attrInculde.add("store-mode");
    }

    public List<File> generate(InvokeInfo invokeInfo) throws Exception {
        this.prdMsgType = invokeInfo.getProvideMsgType();
        this.csmMsgType = invokeInfo.getConsumeMsgType();
        defaultInterfaceFiles = new ArrayList<File>();
        String operationId = invokeInfo.getOperationId();
        log.info("[配置生成]：场景ID[" + operationId + "]");
        String interfaceId = invokeInfo.getEcode();
        log.info("[配置生成]：接口ID[" + interfaceId + "]");
        String interfaceType = invokeInfo.getDirection();
        log.info("[配置生成]：接口类型ID[" + interfaceType + "]");
        String serviceId = invokeInfo.getServiceId();
        this.serviceId = serviceId;
        log.info("[配置生成]：服务ID[" + serviceId + "]");

        MetadataNode operationNode = serviceDataFromDB.getNodeFromDB(serviceId, operationId, true);
        log.info("start to handle operation[" + operationId + "]");
        if (CONSUMER.equalsIgnoreCase(interfaceType)) {
            log.info("[标准配置生成]:开始生成调用方标准配置");
            String dir = serviceId + operationId + "(" + csmMsgType + "-"
                    + prdMsgType + ")" + File.separator
                    + OUT_CONFIG_DIR_PATH;
            String reqFileName = "channel_"+invokeInfo.getProvideSysId()+"_service_" + serviceId
                    + operationId + ".xml";
            String rspFileName = "service_" + serviceId + operationId
                    + "_system_"+invokeInfo.getProvideSysId()+".xml";
            File outConfigDir = new File(dir);
            if (!outConfigDir.exists()) {
                outConfigDir.mkdirs();
            }
            if (null != operationNode) {
                IMetadataNode reqOperationNode = operationNode.getChild("REQUEST");
                reqOperationNode.setNodeID("service");
                reqOperationNode.getProperty().setProperty("package_type", "xml");
                reqOperationNode.getProperty().setProperty("store-mode", "UTF-8");
                MetadataNode sysHeadNode = new MetadataNode();
                MetadataNode serviceCodeNode = new MetadataNode();
                serviceCodeNode.setMetadataID("ServiceCode");
                MetadataNode serviceSceneNode = new MetadataNode();
                serviceSceneNode.setMetadataID("ServiceScene");
                MetadataNode consumerIdNode = new MetadataNode();
                consumerIdNode.setMetadataID("ConsumerId");
                MetadataNode orgConsumerIdNode = new MetadataNode();
                orgConsumerIdNode.setMetadataID("OrgConsumerId");
                MetadataNode consumerSeqNoNode = new MetadataNode();
                consumerSeqNoNode.setMetadataID("ConsumerSeqNo");
                MetadataNode orgConsumerSeqNoNode = new MetadataNode();
                orgConsumerSeqNoNode.setMetadataID("OrgConsumerSeqNo");
                MetadataNode tranDateNode = new MetadataNode();
                tranDateNode.setMetadataID("TranDate");
                MetadataNode tranTimeNode = new MetadataNode();
                tranTimeNode.setMetadataID("TranTime");
                sysHeadNode.addChild(serviceCodeNode);
                sysHeadNode.addChild(serviceSceneNode);
                sysHeadNode.addChild(consumerIdNode);
                sysHeadNode.addChild(orgConsumerIdNode);
                sysHeadNode.addChild(consumerSeqNoNode);
                sysHeadNode.addChild(orgConsumerSeqNoNode);
                sysHeadNode.addChild(tranDateNode);
                sysHeadNode.addChild(tranTimeNode);
                reqOperationNode.addChild(sysHeadNode);

                Document providerUnPackDocument = MetadataNodeHelper
                        .MetadataNode2DocumentWithInclude(reqOperationNode, null,
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
                log.info("start to generate default consumer config for ["
                        + operationId + "]");

                reqFileName = dir + File.separator + reqFileName;
                Document providerPackDocument = MetadataNodeHelper
                        .MetadataNode2DocumentWithInclude(operationNode, null,
                                attrInculde);
                String providerReqSoapXML = this.documentToXML(providerPackDocument);
                File reqSoapFile = new File(reqFileName);
                if (!reqSoapFile.exists()) {
                    log.info("create file [" + reqSoapFile.getAbsolutePath() + "]");
                    reqSoapFile.createNewFile();
                } else {
                    log.error(reqSoapFile.getName() + " must not be exist!");
                }
                this.saveXMLFile(reqSoapFile, providerReqSoapXML);
            }
        } else if (PROVIDER.equalsIgnoreCase(interfaceType)) {
            log.info("[标准配置生成]:开始生成提供方标准配置");
            String dir = serviceId + operationId + "(" + csmMsgType + "-"
                    + prdMsgType + ")" + File.separator
                    + IN_CONFIG_DIR_PATH;
            String rspFileName = "service_" + serviceId + operationId
                    + "_system_" + invokeInfo.getConsumeSysId() + ".xml";
            String reqFileName = "channel_" + invokeInfo.getConsumeSysId()
                    + "_service_" + serviceId + operationId
                    + ".xml";
            File inConfigDir = new File(dir);
            if (!inConfigDir.exists()) {
                inConfigDir.mkdirs();
            }
            if (null != operationNode) {
                String[] headArr = new String[]{"ServiceCode", "ServiceScene", "ConsumerId", "OrgConsumerId","OrgConsumerSeqNo","TranDate","TranTime"};
                defaultInterfaceFiles.add(inConfigDir);
                IMetadataNode reqOperationNode = operationNode.getChild("REQUEST");
                reqOperationNode.setNodeID("service");
                reqOperationNode.getProperty().setProperty("package_type", "xml");
                reqOperationNode.getProperty().setProperty("store-mode", "UTF-8");
                MetadataNode sysHeadNode = new MetadataNode();
                sysHeadNode.setNodeID("SYS_HEAD");
                for(String headEle : headArr){
                    MetadataNode ele = new MetadataNode();
                    ele.setMetadataID(headEle);
                    ele.setNodeID(headEle);
                    IMetadataNodeAttribute attribute = new MetadataNodeAttribute();
                    attribute.setProperty("metadataid", headEle);
                    ele.setProperty(attribute);
                    sysHeadNode.addChild(ele);
                }
                reqOperationNode.addChild(sysHeadNode);
                reqOperationNode.getChild("SvcBody").setNodeID("BODY");
                Document consumerUnPackDocument = MetadataNodeHelper
                        .MetadataNode2DocumentWithInclude(reqOperationNode, null,
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
                IMetadataNode rspOperationNode = operationNode.getChild("RESPONSE");
                rspOperationNode.setNodeID("service");
                rspOperationNode.getProperty().setProperty("package_type", "xml");
                rspOperationNode.getProperty().setProperty("store-mode", "UTF-8");
                MetadataNode rspsysHeadNode = new MetadataNode();
                rspsysHeadNode.setNodeID("SYS_HEAD");
                for(String headEle : headArr){

                    MetadataNode ele = new MetadataNode();
                    ele.setMetadataID(headEle);
                    ele.setNodeID(headEle);
                    IMetadataNodeAttribute attribute = new MetadataNodeAttribute();
                    if("ServiceCode".equalsIgnoreCase(headEle)){
                        attribute.setProperty("expression", "'"+serviceId+"'");
                    }
                    if("ServiceScene".equalsIgnoreCase(headEle)){
                        attribute.setProperty("expression", "'"+operationId+"'");
                    }

                    attribute.setProperty("metadataid", headEle);
                    ele.setProperty(attribute);
                    rspsysHeadNode.addChild(ele);
                }
                MetadataNode returnStatusNode = new MetadataNode();
                returnStatusNode.setNodeID("ReturnStatus");
                IMetadataNodeAttribute returnStatusNodeAttr = new MetadataNodeAttribute();
                returnStatusNodeAttr.setProperty("metadataid","ReturnStatus");
                returnStatusNode.setProperty(returnStatusNodeAttr);
                rspsysHeadNode.addChild(returnStatusNode);
                MetadataNode arrayNode = new MetadataNode();
                arrayNode.setNodeID("array");
                returnStatusNode.addChild(arrayNode);
                MetadataNode retNode = new MetadataNode();
                retNode.setNodeID("RET");
                IMetadataNodeAttribute retNodeAttr = new MetadataNodeAttribute();
                retNodeAttr.setProperty("metadataid", "Ret");
                retNodeAttr.setProperty("is_struct", "false");
                retNode.setProperty(retNodeAttr);
                arrayNode.addChild(retNode);
                MetadataNode returnCodeNode = new MetadataNode();
                returnCodeNode.setNodeID("ReturnCode");
                IMetadataNodeAttribute returnCodeNodeAttr = new MetadataNodeAttribute();
                returnCodeNodeAttr.setProperty("metadataid","ReturnCode");
                returnCodeNode.setProperty(returnCodeNodeAttr);
                retNode.addChild(returnCodeNode);
                MetadataNode returnMsgNode = new MetadataNode();
                returnMsgNode.setNodeID("ReturnMsg");
                IMetadataNodeAttribute returnMsgNodeAttr = new MetadataNodeAttribute();
                returnMsgNodeAttr.setProperty("metadataid","ReturnMsg");
                returnMsgNode.setProperty(returnMsgNodeAttr);
                retNode.addChild(returnMsgNode);
                rspOperationNode.addChild(rspsysHeadNode);
                rspOperationNode.getChild("SvcBody").setNodeID("BODY");
                Document consumerPackDocument = MetadataNodeHelper
                        .MetadataNode2DocumentWithInclude(rspOperationNode, null,
                                attrInculde);

                String consumerRspSoapXML = this.documentToXML(consumerPackDocument);
                rspFileName = dir + File.separator + rspFileName;
                File rspSoapFile = new File(rspFileName);
                if (!rspSoapFile.exists()) {
                    log.info("create file [" + rspSoapFile.getAbsolutePath() + "]");
                    rspSoapFile.createNewFile();
                } else {
                    log.error(rspSoapFile.getAbsoluteFile() + " must not be exist!");
                }
                this.saveXMLFile(rspSoapFile, consumerRspSoapXML);
                log.info("start to generate default provider config for ["
                        + operationId + "]");

            }
        }

        return this.defaultInterfaceFiles;
    }


    @Override
    public void generate(String resource) throws Exception {

    }
}
