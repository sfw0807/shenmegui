package com.dc.esb.servicegov.export.util;

import com.dc.esb.servicegov.entity.Ida;
import com.dc.esb.servicegov.entity.SDA;
import com.dc.esb.servicegov.export.bean.MetadataNode;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/7/16.
 */
public class ExportUtil {

    /**
     * 递归获取SDA\
     * 某一节点的所有子节点
     *
     * @param sdas
     * @param sda
     * @param requestBody
     * @return
     */
    public static MetadataNode recursionFindSDA(List<SDA> sdas, SDA sda, MetadataNode requestBody) {
        List<MetadataNode> childList = getChildList(sdas, sda);// 得到子节点列表
        requestBody.setChildreans(childList);
        Iterator<MetadataNode> it = childList.iterator();
        while (it.hasNext()) {
            MetadataNode body = (MetadataNode) it.next();
            SDA s = new SDA();
            s.setSdaId(body.getNodeId());
            if (hasChild(sdas, s)) {
                recursionFindSDA(sdas, s, body);
            }
        }

        return requestBody;
    }

    /**
     * 递归获取IDA
     * 某一节点的所有子节点
     *
     * @param idas
     * @param ida
     * @param requestBody
     * @return
     */
    public static MetadataNode recursionFindIDA(List<Ida> idas, Ida ida, MetadataNode requestBody) {
        List<MetadataNode> childList = getChildList(idas, ida);// 得到子节点列表
        requestBody.setChildreans(childList);
        Iterator<MetadataNode> it = childList.iterator();
        while (it.hasNext()) {
            MetadataNode body = (MetadataNode) it.next();
            Ida s = new Ida();
            s.setId(body.getNodeId());
            if (hasChild(idas, ida)) {
                recursionFindIDA(idas, s, body);
            }
        }

        return requestBody;
    }

    private static List<MetadataNode> getChildList(List<SDA> sdas, SDA sda) {
        List<MetadataNode> result = new ArrayList<MetadataNode>();
        for (SDA s : sdas) {
            if (s.getParentId() != null && s.getParentId().equals(sda.getSdaId())) {
                MetadataNode requestBody = new MetadataNode();
                requestBody.setNodeId(s.getSdaId());
                requestBody.setMetadataId(s.getMetadataId());
                requestBody.setNodeName(s.getStructName());
                requestBody.setChineseName(s.getStructAlias());
                requestBody.setType(s.getType());
                result.add(requestBody);
            }
        }
        return result;
    }

    private static List<MetadataNode> getChildList(List<Ida> idas, Ida ida) {
        List<MetadataNode> result = new ArrayList<MetadataNode>();
        for (Ida s : idas) {
            if (s.get_parentId() != null && s.get_parentId().equals(ida.getId())) {
                MetadataNode requestBody = new MetadataNode();
                requestBody.setNodeId(s.getId());
                requestBody.setMetadataId(s.getMetadataId());
                requestBody.setNodeName(s.getStructName());
                requestBody.setChineseName(s.getStructAlias());
                requestBody.setType(s.getType());
                result.add(requestBody);
            }
        }
        return result;
    }

    // 判断是否有子节点
    private static boolean hasChild(List<SDA> sdas, SDA sda) {
        for (SDA s : sdas) {
            if (s.getParentId() != null && s.getParentId().equals(sda.getSdaId())) {
               return true;
            }
        }
        return false;
    }

    // 判断是否有子节点
    private static boolean hasChild(List<Ida> idas, Ida ida) {
        for (Ida s : idas) {
            if (s.get_parentId() != null && s.get_parentId().equals(ida.getId())) {
               return true;
            }
        }
        return false;
    }

    public static void generatorFile(MetadataNode requestBody, Element root) {
        for (MetadataNode body : requestBody.getChildreans()) {
            String prefix = "";

            if (root.getNamespace() != null && !"".equals(root.getNamespace().getPrefix())) {
                prefix = root.getNamespace().getPrefix() + ":";
            }
            Element ele = root.addElement(prefix + body.getNodeName());
            if (body.getChildreans() != null && body.getChildreans().size() > 0) {
                Element sdo = ele;
                if (body.getType() != null && body.getType().equalsIgnoreCase("array")) {
                    ele.addAttribute("type", "array");
                    sdo = ele.addElement(prefix + "sdo");

                }
                generatorFile(body, sdo);
            } else {
                String metadataId = body.getMetadataId();
                if (metadataId == null || "".equals(metadataId)) {
                    metadataId = body.getNodeName();
                }
                ele.addAttribute("metadataid", metadataId);
                ele.addAttribute("chinese_name", body.getChineseName());
            }
        }
    }

    public static String generatorServiceDefineSOAP(List<SDA> sdas, SDA sda) {
        MetadataNode esbBody = new MetadataNode();
        esbBody.setNodeId(sda.getSdaId());
        esbBody.setNodeName(sda.getStructName());
        esbBody = ExportUtil.recursionFindSDA(sdas, sda, esbBody);

        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("utf-8");
        Element element = doc.addElement("root");

        element.addNamespace(Contants.NAME_SPACE, Contants.NAME_SPACE_URL);
        Element SvcBody = element.addElement(Contants.NAME_SPACE+":SvcBody");
        ExportUtil.generatorFile(esbBody, SvcBody);

        return SvcBody.asXML().replace("xmlns:" + Contants.NAME_SPACE + "=" + Contants.NAME_SPACE_URL+"\"", "");
    }

    public static String generatorServiceDefineXML(List<SDA> sdas, SDA sda) {
        MetadataNode esbBody = new MetadataNode();
        esbBody.setNodeId(sda.getSdaId());
        esbBody.setNodeName(sda.getStructName());
        esbBody = ExportUtil.recursionFindSDA(sdas, sda, esbBody);

        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("utf-8");
        Element element = doc.addElement("BODY");
        ExportUtil.generatorFile(esbBody, element);

        String xml = doc.asXML();

        return xml.substring(xml.indexOf("<BODY>"));
    }

    public static void main(String[] a) {
        ClassLoader loader = ExportUtil.class.getClassLoader();
        String path = loader.getResource("template/in_config/channel_service_template_soap.xml").getPath();
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(new File(path));
            Element root = document.getRootElement();

//            List list =
            List<Namespace> ss = root.additionalNamespaces();
            for (Namespace s : ss) {

                System.out.println(s.asXML());
            }
        } catch (Exception e) {
        }
    }
}
