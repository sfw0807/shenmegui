package com.dc.esb.servicegov.refactoring.resource.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.MetadataDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.Metadata;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.AbstractGenerater;

@Service
@Transactional(rollbackFor = Exception.class)
public class MetadataXMLGenerater extends AbstractGenerater {

	private static final Log log = LogFactory
			.getLog(MetadataXMLGenerater.class);

	@Autowired
	private MetadataDAOImpl metadataDAO;
	private String fileName = "metadata.xml";
	private String metadataXMLContent = "";
	private File metadataFile;

	public File generate() {
		try {
			List<Metadata> metadataDesc = metadataDAO.getOrderbyMetadataId();
			Document doc = this.metadataDesc2Document(metadataDesc);
			this.metadataXMLContent = this.documentToXML(doc);
			metadataFile = new File(fileName);
			this.saveXMLFile(metadataFile, this.metadataXMLContent);
		} catch (Exception e) {
			log.error("Generate metadata xml error!" + e);
		}
		return metadataFile;
	}

	public String getMetadataXML() {
		return this.metadataXMLContent;
	}

	public File getMetadataFile() {
		return this.metadataFile;
	}

	private Document metadataDesc2Document(List<Metadata> metadatas) {
		Document document = DocumentHelper.createDocument();
		Element codeRootElement = null;
		Element proElement = null;
		codeRootElement = document.addElement("metadata");
		for (Metadata metadata : metadatas) {
			proElement = codeRootElement.addElement(metadata.getMetadataId());
			String metadataType = metadata.getType();
			String metadataLength = metadata.getLength();
			if (null != metadataType && !"".equals(metadataType)) {
				if("array".equals(metadataType)){
					proElement.addAttribute("type", metadataType);
				}
				else{
					proElement.addAttribute("type", "string");
				}
			}
			if (null != metadataLength && !"".equals(metadataLength)) {
				proElement.addAttribute("length", metadataLength);
			}
		}
		return document;
	}



	@Override
	public void generate(String resource) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

