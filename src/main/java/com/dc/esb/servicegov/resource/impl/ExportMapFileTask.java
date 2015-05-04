package com.dc.esb.servicegov.resource.impl;

import static com.dc.esb.servicegov.resource.impl.AbstractSOPMakeFileGenerater.FIELD_IDENTIFY;
import static com.dc.esb.servicegov.resource.impl.AbstractSOPMakeFileGenerater.FIELD_PRIFIX_STRING;
import static com.dc.esb.servicegov.resource.impl.AbstractSOPMakeFileGenerater.GRD_IDENTIFY;
import static com.dc.esb.servicegov.resource.impl.AbstractSOPMakeFileGenerater.INTERFACE_LENGTH;
import static com.dc.esb.servicegov.resource.impl.AbstractSOPMakeFileGenerater.INTERFACE_SCALE;
import static com.dc.esb.servicegov.resource.impl.AbstractSOPMakeFileGenerater.INTERFACE_TYPE;
import static com.dc.esb.servicegov.resource.impl.AbstractSOPMakeFileGenerater.MAPFILE_FIELD_LABEL;
import static com.dc.esb.servicegov.resource.impl.AbstractSOPMakeFileGenerater.MAPFILE_FORM_LABEL;
import static com.dc.esb.servicegov.resource.impl.AbstractSOPMakeFileGenerater.OJECT_PRIFIX;
import static com.dc.esb.servicegov.resource.impl.AbstractSOPMakeFileGenerater.REQUEST;
import static com.dc.esb.servicegov.resource.impl.AbstractSOPMakeFileGenerater.RESPONSE;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dc.esb.servicegov.resource.IExportMapFileTask;
import com.dc.esb.servicegov.resource.metadataNode.IMetadataNode;
import com.dc.esb.servicegov.resource.metadataNode.MetadataNode;
import com.dc.esb.servicegov.util.SDAConstants;

public class ExportMapFileTask implements IExportMapFileTask {

Log log = LogFactory.getLog(ExportMapFileTask.class);
	
	private String interfaceName;
	private List<File> mapFiles = null;
	private int objectCount = 1;
	private int fieldCount = 1;
	private CountDownLatch countDown;
	private XMPassedInterfaceDataFromDB db;
	
	@Override
	public void init(String interfaceName, List<File> files, XMPassedInterfaceDataFromDB db, CountDownLatch countDown) {
		this.interfaceName = interfaceName;
		this.mapFiles = files;
		this.db = db;
		this.countDown = countDown;
		this.objectCount = 1;
		this.fieldCount = 1;
	}
	
	@Override
	public void run() {
		this.generate();
		
	}

	/**
	 * generate sop mkfiles using interface node from db. The returned files
	 * including: 1.one request file 2.several resp files 3.several field files
	 * as metadata structures in service gov platform
	 * 
	 * 通过metadatanode来生成接口的MapFiles， 生成的文件包括，
	 * 一个请求Object文件，一个或多个返回Object文件，若干Grid文件。
	 * 
	 * @param interfaceNode
	 * @throws java.io.IOException
	 */
	@Override
	public List<File> generate() {
		
		MetadataNode interfaceNode = db.getInterfaceData(interfaceName);
		File dir = new File(interfaceName + "mapfiles");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
//		this.mapFiles.add(dir);

		try {
			File reqFile = new File(getObjectSopMakeFilePath(interfaceName));
			if (!reqFile.exists()) {
				reqFile.createNewFile();
			}
			writeContentToFile(
					generateSOPObjectMapFileContent(interfaceNode,
							SDAConstants.REQUEST, interfaceName), reqFile);

			File respFile = new File(getObjectSopMakeFilePath(interfaceName));
			if (!respFile.exists()) {
				respFile.createNewFile();
			}
			writeContentToFile(
					generateSOPObjectMapFileContent(interfaceNode,
							SDAConstants.RESPONSE, interfaceName), respFile);
			this.mapFiles.add(reqFile);
			this.mapFiles.add(respFile);
		} catch (IOException e) {
			log.error("ExportMapFileTask generate IO Exception");
			e.printStackTrace();
		}
		countDown.countDown();
		return this.mapFiles;
	}
	
	protected void writeContentToFile(String content, File targetFile) throws IOException{
		if(null != targetFile){
			FileWriter fWriter = new FileWriter(targetFile);
			fWriter.append(content);
			fWriter.flush();
			fWriter.close();
		}
	}
	
	/**
	 * generate sop object map file 生成Request或者Response请求对象文件的内容
	 * 
	 * @param interfaceNode
	 * @param type
	 * @return
	 * @throws java.io.IOException
	 */
	public String generateSOPObjectMapFileContent(MetadataNode interfaceNode,
			String type, String interfaceName) throws IOException {
		StringBuilder content = new StringBuilder();
		if (interfaceNode != null && type != null) {
			IMetadataNode node = null;
			if (type.equals(REQUEST)) {
				node = interfaceNode.getChild(REQUEST);
			} else if (type.equals(RESPONSE)) {
				node = interfaceNode.getChild(RESPONSE);
			}
			content.append(MAPFILE_FORM_LABEL);
			content.append(System.getProperty("line.separator"));
			if (node.hasChild()) {
				List<IMetadataNode> children = node.getChild();
				for (IMetadataNode child : children) {
					if (null != child) {
						content.append(nodeToSopMapfileObject(
								(MetadataNode) child, interfaceName));
					}
				}
			}
		}
		return content.toString();
	}
	
	/**
	 * 将一个Metadata Node写为一个Field或者Grid的过程
	 * 
	 * @param node
	 * @param interfaceName
	 * @return
	 * @throws java.io.IOException
	 */
	private String nodeToSopMapfileObject(MetadataNode node,
			String interfaceName) throws IOException {
		System.out
				.println("**********************************************nodeToSopMapfileObject");
		System.out.println(node);
		StringBuilder sb = new StringBuilder();
		if (null != node) {
			if (node.hasChild()) {
				String gridName = generateSopGridMapFile(interfaceName, node);
				sb.append(nodeToMapfileGrid(gridName));
			} else {
				sb.append(nodeToMapfileField(node));
			}
		}
		return sb.toString();
	}
	
	/**
	 * 生成Grid File
	 * 
	 * @param interfaceName
	 * @param node
	 * @return
	 * @throws java.io.IOException
	 */
	private String generateSopGridMapFile(String interfaceName,
			MetadataNode node) throws IOException {
		System.out.println("************************************Field");
		System.out.println(node);
		StringBuilder sb = new StringBuilder();
		sb.append(MAPFILE_FIELD_LABEL);
		sb.append(System.getProperty("line.separator"));
		String gridFileName = getGridSopMakeFileName(interfaceName);
		List<IMetadataNode> children = node.getChild();
		for (IMetadataNode child : children) {
			if (child.hasChild()) {
				String childGridName = generateSopGridMapFile(interfaceName,
						(MetadataNode) child);

				sb.append(nodeToMapfileGrid(childGridName));
			} else {
				sb.append(nodeToMapfileField((MetadataNode) child));
			}
		}
		System.out.println(sb.toString());
		File gridFile = new File(interfaceName + "mapfiles" + File.separator + gridFileName);
		writeContentToFile(sb.toString(), gridFile);
		this.mapFiles.add(gridFile);
		return gridFileName;
	}
	
	private String getGridSopMakeFileName(String interfaceName) {
		String fileName = null;
		if (null != interfaceName) {
			fileName = FIELD_PRIFIX_STRING + interfaceName + fieldCount;
			fieldCount++;
		}
		return fileName;
	}
	
	private String nodeToMapfileGrid(String gridName) {
		return GRD_IDENTIFY + ":" + gridName + System.getProperty("line.separator");
	}

	private String nodeToMapfileField(MetadataNode node) {
		return nodeToMapFileObj(FIELD_IDENTIFY, node);
	}

	private String getSopMapfileType(String formerType) {

		if (formerType.toLowerCase().startsWith("char")) {
			return "S";
		} else if (formerType.toLowerCase().startsWith("decimal")) {
			return "D";
		} else if (formerType.toLowerCase().startsWith("date")) {
			return "Q";
		} else if (formerType.toLowerCase().startsWith("integer")) {
			return "N";
		} else if (formerType.toLowerCase().startsWith("short")) {
			return "n";
		} else if (formerType.toLowerCase().startsWith("long")) {
			return "L";
		} else if (formerType.toLowerCase().startsWith("time")) {
			return "T";
		} else if (formerType.toLowerCase().startsWith("hex")) {
			return "H";
		} else if (formerType.toLowerCase().startsWith("chn")) {
			return "B";
		}
		return formerType;
	}

	private String nodeToMapFileObj(String type, MetadataNode node) {
		log.info("parse node" + node);
		StringBuilder mapFieldBuilder = new StringBuilder();
		mapFieldBuilder.append(type);
		mapFieldBuilder.append(":");
		mapFieldBuilder.append(node.getNodeID());
		mapFieldBuilder.append(" ");
		if (null == node.getProperty().getProperty(INTERFACE_LENGTH)) {
			mapFieldBuilder.append("0");
		} else {
			mapFieldBuilder.append(node.getProperty().getProperty(
					INTERFACE_LENGTH));
		}
		mapFieldBuilder.append(" ");
		if (node.getProperty().getProperty(INTERFACE_TYPE) == null) {
			System.out.println(node.getNodeID());
		}
		mapFieldBuilder.append(getSopMapfileType(node.getProperty()
				.getProperty(INTERFACE_TYPE)));
		mapFieldBuilder.append(" ");
		if (null == node.getProperty().getProperty(INTERFACE_SCALE) 
				|| "".equals(node.getProperty().getProperty(INTERFACE_SCALE))) {
			mapFieldBuilder.append("0");
		} else {
			mapFieldBuilder.append(node.getProperty().getProperty(
					INTERFACE_SCALE));
		}
		mapFieldBuilder.append(" ");
		// align to do this is hard coded with 0
		mapFieldBuilder.append("0");
		mapFieldBuilder.append(" ");
		// fillchar this is hard coded with 0
		mapFieldBuilder.append("0");
		mapFieldBuilder.append(" ");
		// turnmode this is hard coded with 0
		mapFieldBuilder.append("0");
		mapFieldBuilder.append(" ");
		// string this is hard coded with NULL
		mapFieldBuilder.append("NULL");
		mapFieldBuilder.append(" ");
		// encrypt this is hard coded with 0
		mapFieldBuilder.append("0");
		mapFieldBuilder.append(System.getProperty("line.separator"));
		return mapFieldBuilder.toString();
	}

	protected void initCounter() {
		objectCount = 1;
		fieldCount = 1;
	}

	protected void initMapFileList() {
		this.mapFiles.clear();
	}

	private String getObjectSopMakeFilePath(String interfaceName) {
		String fileName = null;
		if (null != interfaceName) {
			fileName = interfaceName + "mapfiles" + File.separator + OJECT_PRIFIX
					+ interfaceName + objectCount;
			objectCount++;
		}
		return fileName;

	}

}
