package com.dc.esb.servicegov.refactoring.resource.metadataNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


public abstract class AbstractGenerater implements IGenerate<String> {
	private static final Log log = LogFactory.getLog(AbstractGenerater.class);
	
	abstract public void generate(String resource) throws Exception;

	protected String formatXML(Document doc, String charset) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(charset);
		doc.setXMLEncoding(charset);
		StringWriter sw = new StringWriter();
		XMLWriter xw = new XMLWriter(sw, format);
		String xml = "";
		try {
			xw.write(doc);
			xw.flush();
			xw.close();
			byte[] aa = sw.getBuffer().toString().getBytes(charset);
			xml = new String(aa, charset);
		} catch (IOException e) {
			if (log.isErrorEnabled()) {
				log.error("Save file error file", e);
			}
		}finally{
			try {
				xw.close();
			} catch (IOException e) {
				if (log.isErrorEnabled()) {
					log.error("Close XMLWriter error !", e);
				}
			}
		}
		return xml;
	}
	
	public String documentToXML(Document doc) throws Exception {
		return this.formatXML(doc, "utf-8");
	}
	
	protected void saveXMLFile(File file, String content) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			out.write(content.getBytes("UTF-8"));
			out.flush();
			out.close();
		} catch (IOException e) {
			log.error("Create  file error!"+e);
		}
	}
	
	protected void setFieldAttribute(IMetadataNode node, Element element) {
		if (node.hasAttribute()) {
			Properties pro = node.getProperty().getProperty();
			for (Object objectProperty : pro.keySet()) {
				String propertyValue = (String)pro.get(objectProperty);
				if((propertyValue != null)&&(!propertyValue.equals(""))){
					element.addAttribute((String) objectProperty, (String) pro
							.get(objectProperty));
				}
			}
		}
	}
}
