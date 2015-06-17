package com.dc.esb.servicegov.resource.metadataNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;

import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2015/5/7.
 */
public class NB8583MetadataNodeHelper {
    private Log log = LogFactory.getLog(NB8583MetadataNodeHelper.class);
    private final static String ROOTSTRING = "root";
    // 对应response
    private final static String REQUESTSTRING = "request";

    // 信用卡密码字段特殊处理
    private final static String PWD = "Pwd";

    // private final static String RESPONSESTRING = "response";

    public static NB8583MetadataNodeHelper nb8583MetadataNodeHelper = null;

    public static NB8583MetadataNodeHelper getInstance() {
        if (nb8583MetadataNodeHelper == null) {
            nb8583MetadataNodeHelper = new NB8583MetadataNodeHelper();
        }
        return nb8583MetadataNodeHelper;
    }

    public Document MetadataNode2DocumentWithInclude(IMetadataNode node,
                                                     List<Namespace> namespaces, List<String> attrInclude,
                                                     String rqLabel, String interfaceId) {
        if(rqLabel.equals("request"))
        {
            node = processNB8583Head(node,interfaceId);
        }else if(rqLabel.equals("response"))
        {
            node = processNB8583HeadChannel(node,interfaceId);
        }

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(ROOTSTRING);
        // 添加报文头的命名空间信息
        if (null != namespaces) {
            for (Namespace namespace : namespaces) {
                root.add(namespace);
            }
        }
        // 添加报文头的属性信息和报文头配置信息
        if (REQUESTSTRING.equals(rqLabel)) {
            root.addAttribute("package_type", "iso8583");
        } else {
            root.addAttribute("package_type", "iso8583");
        }

        setFieldAttributeWithInculde(node, root, attrInclude, rqLabel);
        appendChildNodeToElementWithInclude(node, root, attrInclude, rqLabel);
        return document;
    }

    private IMetadataNode processNB8583Head(IMetadataNode node,String interfaceId){
        if(null != node){
            IMetadataNode msgStartNode = new MetadataNode();
            msgStartNode.setNodeID("MSG_START");
            IMetadataNodeAttribute msgStartAttr = new MetadataNodeAttribute();
            msgStartAttr.setProperty("length","1");
            msgStartAttr.setProperty("type", "string");
            msgStartAttr.setProperty("isSdoHeader", "true");
            msgStartAttr.setProperty("expression", "'!'");
            if(interfaceId.equals("C804(CBSIII)")) {
                msgStartAttr.setProperty("store-mode", "EBCDIC");
            }
            msgStartNode.setProperty(msgStartAttr);
            node.addChild(0, msgStartNode);


            IMetadataNode msgStartNode2 = new MetadataNode();
            msgStartNode2.setNodeID("FIELD_LENGTH");
            IMetadataNodeAttribute msgStartAttr2 = new MetadataNodeAttribute();
            msgStartAttr2.setProperty("length","4");
            msgStartAttr2.setProperty("type", "string");
            msgStartAttr2.setProperty("isSdoHeader", "true");
            msgStartAttr2.setProperty("expression", "'0038'");
            if(interfaceId.equals("C804(CBSIII)")) {
                msgStartAttr2.setProperty("store-mode", "EBCDIC");
            }
            msgStartNode2.setProperty(msgStartAttr2);
            node.addChild(1, msgStartNode2);

            IMetadataNode msgStartNode3 = new MetadataNode();
            msgStartNode3.setNodeID("PACKAGE_TYPE");
            IMetadataNodeAttribute msgStartAttr3 = new MetadataNodeAttribute();
            msgStartAttr3.setProperty("length","1");
            msgStartAttr3.setProperty("type", "string");
            msgStartAttr3.setProperty("isSdoHeader", "true");
            msgStartAttr3.setProperty("expression", "'0'");
            if(interfaceId.equals("C804(CBSIII)")) {
                msgStartAttr3.setProperty("store-mode", "EBCDIC");
            }
            msgStartNode3.setProperty(msgStartAttr3);
            node.addChild(2, msgStartNode3);

            IMetadataNode msgStartNode4 = new MetadataNode();
            msgStartNode4.setNodeID("MSG_TYPE");
            IMetadataNodeAttribute msgStartAttr4 = new MetadataNodeAttribute();
            msgStartAttr4.setProperty("length","4");
            msgStartAttr4.setProperty("type", "string");
            msgStartAttr4.setProperty("metadataid", "MSG_TYPE");
            msgStartAttr4.setProperty("isSdoHeader", "true");
            msgStartAttr4.setProperty("expression", "'2011'");
            if(interfaceId.equals("C804(CBSIII)")) {
                msgStartAttr4.setProperty("store-mode", "EBCDIC");
            }
            msgStartNode4.setProperty(msgStartAttr4);
            node.addChild(3, msgStartNode4);

            IMetadataNode msgStartNode5 = new MetadataNode();
            msgStartNode5.setNodeID("bd1");
            IMetadataNodeAttribute msgStartAttr5 = new MetadataNodeAttribute();
            msgStartAttr5.setProperty("length","8");
            msgStartAttr5.setProperty("type", "string");
            msgStartAttr5.setProperty("metadataid", "MSG_TYPE");
            msgStartAttr5.setProperty("isSdoHeader", "true");
            msgStartAttr5.setProperty("expression", "ef:toByte('2022000080000000',16)");
            if(interfaceId.equals("C804(CBSIII)")) {
                msgStartAttr5.setProperty("store-mode", "EBCDIC");
            }
            msgStartNode5.setProperty(msgStartAttr5);
            node.addChild(4, msgStartNode5);


        }
        return node;
    }

    private IMetadataNode processNB8583HeadChannel(IMetadataNode node,String interfaceId)
    {
        if(null != node)
        {
            IMetadataNode msgStartNode = new MetadataNode();
            msgStartNode.setNodeID("MSG_START");
            IMetadataNodeAttribute msgStartAttr = new MetadataNodeAttribute();
            msgStartAttr.setProperty("length","1");
            msgStartAttr.setProperty("type", "string");
            msgStartAttr.setProperty("metadataid", "MSG_START");
            if(interfaceId.equals("C804(CBSIII)")) {
                msgStartAttr.setProperty("store-mode", "EBCDIC");
            }
            msgStartNode.setProperty(msgStartAttr);
            node.addChild(0, msgStartNode);

            IMetadataNode msgStartNode2 = new MetadataNode();
            msgStartNode2.setNodeID("FIELD_LENGTH");
            IMetadataNodeAttribute msgStartAttr2 = new MetadataNodeAttribute();
            msgStartAttr2.setProperty("length","4");
            msgStartAttr2.setProperty("type", "string");
            msgStartAttr2.setProperty("metadataid", "FIELD_LENGTH");
            if(interfaceId.equals("C804(CBSIII)")) {
                msgStartAttr2.setProperty("store-mode", "EBCDIC");
            }
            msgStartNode2.setProperty(msgStartAttr2);
            node.addChild(1, msgStartNode2);

            IMetadataNode msgStartNode3 = new MetadataNode();
            msgStartNode3.setNodeID("PACKAGE_TYPE");
            IMetadataNodeAttribute msgStartAttr3 = new MetadataNodeAttribute();
            msgStartAttr3.setProperty("length","1");
            msgStartAttr3.setProperty("type", "string");
            msgStartAttr3.setProperty("metadataid", "PACKAGE_TYPE");
            if(interfaceId.equals("C804(CBSIII)")) {
                msgStartAttr3.setProperty("store-mode", "EBCDIC");
            }
            msgStartNode3.setProperty(msgStartAttr3);
            node.addChild(2, msgStartNode3);

            IMetadataNode msgStartNode4 = new MetadataNode();
            msgStartNode4.setNodeID("MSG_TYPE");
            IMetadataNodeAttribute msgStartAttr4 = new MetadataNodeAttribute();
            msgStartAttr4.setProperty("length","4");
            msgStartAttr4.setProperty("type", "string");
            msgStartAttr4.setProperty("metadataid", "MSG_TYPE");
            if(interfaceId.equals("C804(CBSIII)")) {
                msgStartAttr4.setProperty("store-mode", "EBCDIC");
            }
            msgStartNode4.setProperty(msgStartAttr4);
            node.addChild(3, msgStartNode4);

            IMetadataNode msgStartNode5 = new MetadataNode();
            msgStartNode5.setNodeID("bd1");
            IMetadataNodeAttribute msgStartAttr5 = new MetadataNodeAttribute();
            msgStartAttr5.setProperty("type","binary");
            msgStartAttr5.setProperty("metadataid", "bd1");
            msgStartAttr5.setProperty("length", "8");
            msgStartNode5.setProperty(msgStartAttr5);
            node.addChild(4, msgStartNode5);

            IMetadataNode msgStartNode6 = new MetadataNode();
            msgStartNode6.setNodeID("bd2");
            IMetadataNodeAttribute msgStartAttr6 = new MetadataNodeAttribute();
            msgStartAttr6.setProperty("type", "binary");
            msgStartAttr6.setProperty("metadataid", "bd2");
            msgStartAttr6.setProperty("length","4");
            if(interfaceId.equals("C804(CBSIII)")) {
                msgStartAttr6.setProperty("store-mode", "EBCDIC");
            }
            msgStartNode6.setProperty(msgStartAttr6);
            node.addChild(5, msgStartNode6);
        }

        return node;
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
                } else if (include.contains(key)||include.contains(key.toLowerCase())) {
                    String propertyValue = (String) pro.get(key);
                    if ((propertyValue != null) && (!propertyValue.equals(""))) {
                        if("type".equals(key)){
                            element.addAttribute((String) objectProperty,
                                    (String) pro.get(objectProperty));
                        }else{
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
                Element arraySizeEle = element.addElement(cNode.getNodeID()+"Size");
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
                            cele.addAttribute("size", "/root/"+cNode.getNodeID()+"Size");
                            cele = cele.addElement("sdo");
                        }
                        if ("".equals(attr.getProperty("type"))) {
                            if (cNode.hasAttribute("remark")) {
                                if (attr.getProperty("remark") != null
                                        && attr.getProperty("remark")
                                        .toLowerCase().startsWith(
                                                "start")) {
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
