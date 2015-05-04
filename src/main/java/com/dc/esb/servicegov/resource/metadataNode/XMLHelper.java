package com.dc.esb.servicegov.resource.metadataNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XMLHelper {

	public static String formatXML(Document doc, String charset)
			throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(charset);
		doc.setXMLEncoding(charset);
		StringWriter sw = new StringWriter();
		XMLWriter xw = new XMLWriter(sw, format);
		String xml = "";

		xw.write(doc);
		xw.flush();
		xw.close();
		byte[] aa = sw.getBuffer().toString().getBytes(charset);
		xml = new String(aa, charset);

		return xml;
	}

	public static String documentToXML(Document doc) throws Exception {
		return formatXML(doc, "utf-8");
	}

	public static void saveXMLFile(File file, String content) throws Exception {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream out;
		out = new FileOutputStream(file);
		out.write(content.getBytes("UTF-8"));
		out.flush();
		out.close();
	}

}
