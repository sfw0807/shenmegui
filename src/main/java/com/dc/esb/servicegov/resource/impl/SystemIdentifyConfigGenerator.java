package com.dc.esb.servicegov.resource.impl;

import com.dc.esb.servicegov.entity.InvokeInfo;
import com.dc.esb.servicegov.resource.metadataNode.AbstractGenerater;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincentfxz on 15/4/27.
 */
@Component
public class SystemIdentifyConfigGenerator extends AbstractGenerater {

    private static final Log log = LogFactory.getLog(ServiceIdentiryConfigGenerator.class);

    public List<File> generate(InvokeInfo invokeInfo){
        String prdMsgType = invokeInfo.getProvideMsgType();
        String csmMsgType = invokeInfo.getConsumeMsgType();
        String operationId = invokeInfo.getOperationId();
        String serviceId = invokeInfo.getServiceId();
        log.info("[配置生成]：场景ID[" + operationId + "]");
        String interfaceId = invokeInfo.getEcode();
        log.info("[配置生成]：接口ID[" + interfaceId + "]");
        String interfaceType = invokeInfo.getDirection();
        log.info("[配置生成]：接口类型ID[" + interfaceType + "]");
        log.info("[配置生成]：服务ID[" + serviceId + "]");

        List<File> files = new ArrayList<File>();
        String dir = serviceId + operationId + "(" + csmMsgType + "-"
                + prdMsgType + ")" +File.separator +"out_config";
        File dirFile = new File(dir);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
//        files.add(dirFile);
        String fileName = "systemIdentify.xml";
        String filePath = dir + File.separator + fileName;
        File file = new File(filePath);
        if("xml".equalsIgnoreCase(invokeInfo.getConsumeMsgType())){
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("systems");
            Element systemElement = root.addElement("system");
            systemElement.addAttribute("id", invokeInfo.getProvideSysId());
            Element serviceElement = systemElement.addElement("service");
            serviceElement.addText(serviceId + operationId);
            String providerReqSoapXML = null;
            try {
                providerReqSoapXML = this.documentToXML(document);
            } catch (Exception e) {
                log.error(e,e);
            }

            if (!file.exists()) {
                log.info("create file [" + file.getAbsolutePath() + "]");
                try {
                    file.createNewFile();

                } catch (IOException e) {
                    log.error(e,e);
                }

            } else {
                log.error(file.getName() + " must not be exist!");
            }
            this.saveXMLFile(file, providerReqSoapXML);
            files.add(file);
        }

        return files;
    }



    @Override
    public void generate(String resource) throws Exception {

    }
}
