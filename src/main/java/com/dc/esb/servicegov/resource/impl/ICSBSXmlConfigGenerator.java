package com.dc.esb.servicegov.resource.impl;

import com.dc.esb.servicegov.entity.InvokeInfo;
import com.dc.esb.servicegov.resource.IConfigGenerater;
import com.dc.esb.servicegov.resource.metadataNode.IMetadataNode;
import com.dc.esb.servicegov.resource.metadataNode.IMetadataNodeAttribute;
import com.dc.esb.servicegov.resource.metadataNode.XMLHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.dc.esb.servicegov.util.PackerUnPackerConstants.*;

/**
 * Created by vincentfxz on 15/5/17.
 */
@Component(value = "ICSBSXmlConfigGenerator")
public class ICSBSXmlConfigGenerator implements IConfigGenerater<InvokeInfo, List<File>> {

    @Autowired
    private XMPassedInterfaceDataFromDB interfaceDataFromDB;
    private static final Log log = LogFactory.getLog(ServiceIdentiryConfigGenerator.class);

    private List<File> targetFiles = null;

    //跟节点名称
    private static final String ROOTSTRING = "iccard";

    //节点属性白名单
    private static final List<String> include_attr;

    static {
        include_attr = new ArrayList<String>();
        include_attr.add("type=array");
        // String 参数不需要
//        include_attr.add("type=string");
//        include_attr.add("type=binary");
//        include_attr.add("type=double");
//        include_attr.add("type=selfjoinarray");
//        include_attr.add("type=selfjoinchild");
        include_attr.add("is_struct");
        include_attr.add("metadataid");
        include_attr.add("package_type");
//        include_attr.add("length");
        include_attr.add("expression");
        include_attr.add("isSdoHeader");
//        include_attr.add("searchField");
//        include_attr.add("bytelength");
//        include_attr.add("name");
        include_attr.add("store-mode");
//        include_attr.add("successValue");
//        include_attr.add("align");
//        include_attr.add("fill_char");
//        include_attr.add("size");
//        include_attr.add("scale");
        include_attr.add("variable_flag");

    }

    /**
     * 配置文件生成
     *
     * @param invokeInfo
     * @return
     * @throws Exception
     */
    @Override
    public List<File> generate(InvokeInfo invokeInfo) throws Exception {
        targetFiles = new ArrayList<File>();
        String interfaceType = invokeInfo.getDirection();
        if (CONSUMER.equalsIgnoreCase(interfaceType)) {
            generateConsumerConfig(invokeInfo);
        } else if (PROVIDER.equalsIgnoreCase(interfaceType)) {
            generateProviderConfig(invokeInfo);
        }
        return targetFiles;

    }

    /**
     * 生成IN端配置文件
     *
     * @param invokeInfo
     */
    private void generateConsumerConfig(InvokeInfo invokeInfo) {
        //获取服务ID和操作ID
        String serviceId = invokeInfo.getServiceId();
        String operationId = invokeInfo.getOperationId();
        String interfaceId = invokeInfo.getEcode();
        String consumerSysId = invokeInfo.getConsumeSysId();
        String configPath = serviceId + operationId + "("
                + invokeInfo.getConsumeMsgType() + "-"
                + invokeInfo.getProvideMsgType() + ")" + File.separator
                + IN_CONF_DIR;
        // 获取服务操作的IMetadataNode信息
        IMetadataNode metadataNode = interfaceDataFromDB.getInterfaceData(interfaceId);
        //建立路径
        File dir = new File(configPath);
        dir.deleteOnExit();
        dir.mkdirs();
        targetFiles.add(dir);
        //拆包文件名和组包文件名
        String reqConfigFileName = configPath + File.separator + "channel_"
                + consumerSysId + "_service_" + serviceId + operationId
                + ".xml";
        String rspConfigFileName = configPath + File.separator + "service_"
                + serviceId + operationId + "_system_" + consumerSysId
                + ".xml";
        //获取接口的请求和响应结构节点
        IMetadataNode reqNode = metadataNode.getChild(REQ_LABEL);
        IMetadataNode rspNode = metadataNode.getChild(RSP_LABEL);
        //分别创建拆包文件和组包文件
        try {
            createReqConfigFile(reqNode, reqConfigFileName, interfaceId);
            createRspConfigFile(rspNode, rspConfigFileName, interfaceId);
        } catch (Exception e) {
            log
                    .error("[Fix Config Generater]:Fail to create file, req node : ["
                            + reqNode + "], rsp node : [" + rspNode + "]");
        }

    }

    /**
     * 生成Out端配置文件
     *
     * @param invokeInfo
     */
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
        //组包文件和拆包文件名
        String reqConfigFileName = configPath + File.separator + "service_"
                + serviceId + operationId + "_system_" + sysId
                + ".xml";

        String rspConfigFileName = configPath + File.separator + "channel_"
                + sysId + "_service_" + serviceId
                + operationId + ".xml";
        //接口请求和响应数据结构节点
        IMetadataNode reqNode = metadataNode.getChild(REQ_LABEL);
        IMetadataNode rspNode = metadataNode.getChild(RSP_LABEL);
        //生成组包和拆包的配置
        try {
            createReqConfigFile(reqNode, reqConfigFileName, interfaceId);
            createRspConfigFile(rspNode, rspConfigFileName, interfaceId);
        } catch (Exception e) {
            log.error(
                    "[Fix Config Generater]:Fail to create file, req node : ["
                            + reqNode + "], rsp node : [" + rspNode + "]", e);

        }
    }

    private File createReqConfigFile(IMetadataNode configNode, String filePath, String interfaceId) throws Exception {

        File configFile = new File(filePath);
        if (!configFile.exists()) {
            configFile.createNewFile();
        }
        Document document = metadataNode2DocumentWithInclude(configNode,
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
        Document document = metadataNode2DocumentWithInclude(configNode,
                        include_attr, RSP_LABEL, interfaceId);
        String content = XMLHelper.documentToXML(document);
        XMLHelper.saveXMLFile(configFile, content);
        return configFile;
    }

    public Document metadataNode2DocumentWithInclude(IMetadataNode node, List<String> attrInclude,
                                                     String rqLabel, String interfaceId) {

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(ROOTSTRING);
        root.addAttribute("package_type", "xml");
        root.addAttribute("store-mode", "gbk");
        setFieldAttributeWithInculde(node, root, attrInclude, rqLabel);
        appendChildNodeToElementWithInclude(node, root, attrInclude, rqLabel);
        return document;
    }

    public void setFieldAttributeWithInculde(IMetadataNode node,
                                             Element element, List<String> include, String rqLabel) {
        boolean arrayFlag = false;
        if (node.hasAttribute()) {
            Properties pro = node.getProperty().getProperty();
            String value = (String) pro.get("type");
            String length = (String) pro.get("length");
            String scale = (String) pro.get("scale");
            if (null != value) {
                if (value.equalsIgnoreCase("struct")) {
                    pro.remove("type");
                    pro.setProperty("type", "array");
                } else if (value.equalsIgnoreCase("array")) {
                    pro.setProperty("type", "array");
                    arrayFlag = true;
                } else if ("".equals(value)
                        && !"SvcBody".equals(node.getNodeID())) {
                    pro.setProperty("type", "array");
                    arrayFlag = true;
                } else {
                    pro.setProperty("length", length);
                }
            }
            for (Object objectProperty : pro.keySet()) {
                String key = (String) objectProperty;
                if (include.contains(key + "=" + (String) pro.get(key))) {
                    element.addAttribute((String) objectProperty, (String) pro
                            .get(objectProperty));
                } else if (include.contains(key) || include.contains(key.toLowerCase())) {
                    String propertyValue = (String) pro.get(key);
                    if ((propertyValue != null) && (!propertyValue.equals(""))) {
                        if ("type".equals(key)) {
                            element.addAttribute((String) objectProperty,
                                    (String) pro.get(objectProperty));
                        } else {
                            element.addAttribute((String) objectProperty,
                                    (String) pro.get(objectProperty));
                        }
                    }
                }
            }

        }
    }

    public void appendChildNodeToElementWithInclude(IMetadataNode metadataNode,
                                                    Element element, List<String> include, String rqLabel) {

        for (IMetadataNode cNode : metadataNode.getChild()) {
            IMetadataNodeAttribute attr1 = cNode.getProperty();
            if (("array").equals(attr1.getProperty("type"))) {
                Element arraySizeEle = element.addElement(cNode.getNodeID() + "Size");
                arraySizeEle.addAttribute("type", "int");
                arraySizeEle.addAttribute("length", "3");
                arraySizeEle.addAttribute("isSdoHeader", "true");
            }
            Element cele = element.addElement(cNode.getNodeID());
            setFieldAttributeWithInculde(cNode, cele, include, rqLabel);
            if (cNode.hasChild()) {
                IMetadataNodeAttribute attr = cNode.getProperty();
                if (null != attr) {
                    if (cNode.hasAttribute("type")) {
                        if (attr.getProperty("type").equalsIgnoreCase("struct")) {
                            attr.remove("type");
                            attr.setProperty("type", "array");
                        }
                        if (attr.getProperty("type").equals("array")) {
                            String is_struct = cNode.getProperty().containsKey(
                                    "is_struct") ? cNode.getProperty()
                                    .getProperty("is_struct") : "false";
                            cele.addAttribute("is_struct", is_struct);
                            cele.addAttribute("size", "/root/" + cNode.getNodeID() + "Size");
                            cele = cele.addElement("sdo");
                        }
                        if ("".equals(attr.getProperty("type"))) {
                            if (cNode.hasAttribute("remark")) {
                                if (attr.getProperty("remark") != null
                                        && attr.getProperty("remark")
                                        .toLowerCase().startsWith(
                                                "start")) {
                                    cele.addAttribute("is_struct", "true");
                                    cele.addAttribute("is_struct", "true");
                                }
                            }
                        }
                    }
                }
                appendChildNodeToElementWithInclude(cNode, cele, include,
                        rqLabel);
            }
        }
    }

}
