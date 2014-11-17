package com.dc.esb.servicegov.refactoring.wsdl.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Component;

import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-23
 * Time: 下午7:03
 * compare to new xml:
 * nothing changed
 */
@Component
public class OldESBServiceDescriptorGenerator {
    private static final Log log = LogFactory.getLog(OldESBServiceDescriptorGenerator.class);

    public boolean generate(Service serviceDO, List<Operation> operations, String dirPath) {
        BufferedOutputStream descOut = null;
        try {
            Document document = DocumentHelper.createDocument();
            Element metadatasElement = document.addElement("metadatas");
            Element metadataElement = metadatasElement.addElement("metadata");
            Element serviceIdElement = metadataElement.addElement("serviceid");
            serviceIdElement.addText(serviceDO.getServiceId());
            Element wsdlElement = metadataElement.addElement("wsdl");
            for (Operation operation : operations) {
                String operationId = operation.getOperationId();
                Element operationElement = wsdlElement.addElement("operation");
                operationElement.addText(operationId);
            }
            Element fileElement = wsdlElement.addElement("file");
            fileElement.addText(serviceDO.getServiceId() + ".wsdl");
            Element xsdElement = metadataElement.addElement("xsd");
            xsdElement.addElement("common").addText(serviceDO.getServiceId() + ".xsd");

            Element instanceElement = metadataElement.addElement("instance");
            instanceElement.addElement("in");
            instanceElement.addElement("out");

            File descDir = new File(dirPath + File.separator + "Description");
            descDir.mkdirs();
            File descFile = new File(descDir.getAbsolutePath() + File.separator + serviceDO.getServiceId() + ".xml");
            descOut = new BufferedOutputStream(new FileOutputStream(descFile));
//            BufferedOutputStream wsdlOut = new BufferedOutputStream(new FileOutputStream(wsdlFile));

            XMLWriter writer = null;
            OutputFormat format = OutputFormat.createPrettyPrint();
            writer = new XMLWriter(descOut, format);
            writer.write(document);
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if (null != descOut) {
                try {
                    descOut.close();
                } catch (IOException e) {
                    log.error(e, e);
                }
            }
        }
        return true;
    }

}
