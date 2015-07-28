package com.dc.esb.servicegov.export.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dc.esb.servicegov.entity.Metadata;
import com.dc.esb.servicegov.export.IConfigGenerator;
import com.dc.esb.servicegov.service.impl.MetadataServiceImpl;

@Component
public class MetadataConfigGenerator implements IConfigGenerator {
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(MetadataConfigGenerator.class);
	@Autowired
	private MetadataServiceImpl metadataService;

	@Override
	public File generate() {
		File metadataConfigFile = new File("metadata.xml");
		// 获取元数据
		List<Metadata> metadatas = metadataService.getAllMetadata();
		//创建文件
		Document doc = DocumentHelper.createDocument();
		Element rootElement = doc.addElement("metadata");//根节点
		for (int i = 0; i < metadatas.size(); i++) {
			//子节点
			Element info = rootElement.addElement(metadatas.get(i)
					.getMetadataId());
			//向根节点下面的子节点插入属性
			info.addAttribute("type", metadatas.get(i).getType());
			if (!metadatas.get(i).getType().equals("array")) {//判断子节点是否为数组
				info.addAttribute("length", metadatas.get(i).getLength());
				info.addAttribute("chinese_name", metadatas.get(i)
						.getChineseName());
				info.addAttribute("scale", metadatas.get(i).getScale());
			}
		}
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			FileOutputStream fos = new FileOutputStream("metadata.xml");
			XMLWriter writer = new XMLWriter(fos, format);
			writer.write(doc);
			writer.close();
		} catch (IOException e) {
			log.error(e, e);
		}
		return metadataConfigFile;
	}
}
