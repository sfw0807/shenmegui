/*
 * Copyright 2013 digital china financial software Inc.
 * All rights reserved.
 * project name: ServiceGovernance_PatchLine_xmyh      
 * version:  InteractiveFrame1.0          
 *---------------------------------------------------
 * author: li.jun
 * date:   2013-1-3
 * note:          
 *
 *---------------------------------------------------
 * modificator:   
 * date:          
 * note:          
 *
 */
package com.dc.esb.servicegov.resource.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.dao.impl.SystemDAOImpl;
import com.dc.esb.servicegov.entity.InvokeInfo;
import com.dc.esb.servicegov.resource.metadataNode.AbstractGenerater;
import com.dc.esb.servicegov.resource.metadataNode.IMetadataNode;
import com.dc.esb.servicegov.resource.metadataNode.MetadataNode;

@Service
public class InterfaceXMLGenerater extends AbstractGenerater{
	
	@Autowired
	private SystemDAOImpl systemDAO;
	private static final int PROVIDER_INTERFACE_TYPE = 1;
	private static final int CUSTOMER_INTERFACE_TYPE = 0;
	
	private static final String IN_DIR_PATH = "in_conf_spec";
	private static final String OUT_DIR_PATH = "out_conf_spec";

	private static final Log log = LogFactory.getLog(InterfaceXMLGenerater.class);
	private List<File> interfaceFiles = new ArrayList<File>();
	public List<File> getInterfaceFiles() {
		return interfaceFiles;
	}

	public void setInterfaceFiles(List<File> interfaceFiles) {
		this.interfaceFiles = interfaceFiles;
	}
	
	public void generate(String serviceId,String interfaceId, int interfaceType, String systemId) throws Exception{
		if(checkGenerateArgs(serviceId, interfaceId, interfaceType, systemId)){
			String reqFilePath = null;
			String rspFilePath = null;
			String dirPath = null;
			if(PROVIDER_INTERFACE_TYPE == interfaceType){
				dirPath = OUT_DIR_PATH;
				reqFilePath = dirPath + File.separator+ "service_" + serviceId+ "_system_" + systemId + ".xml";
				rspFilePath = dirPath + File.separator+ "channel_" + systemId + "_service_" + serviceId  + ".xml";
			}else if(CUSTOMER_INTERFACE_TYPE == interfaceType){
				dirPath = IN_DIR_PATH;
				reqFilePath = dirPath + File.separator + "channel_" + systemId +"_service_" + serviceId  + ".xml";
				rspFilePath = dirPath + File.separator + "service_" + serviceId  + "_system_" + systemId + ".xml";
			}
			File dirFile = new File(dirPath);
			if(!dirFile.exists()){
				dirFile.mkdir();
			}
			File reqFile = new File(reqFilePath);
			if(!reqFile.exists()){
				reqFile.createNewFile();
			}
			File rspFile = new File(rspFilePath);
			if(!rspFile.exists()){
				rspFile.createNewFile();
			}
			
			XMPassedInterfaceDataFromDB db = new XMPassedInterfaceDataFromDB();
			MetadataNode interfaceNode = db.getNodeFromDB(interfaceId);
			
			interfaceToReqConfigFile(interfaceNode, reqFile);
			interfaceToRspConfigFile(interfaceNode, rspFile);
			
			interfaceFiles.add(reqFile);
			interfaceFiles.add(rspFile);
		}
	}
	
	private void interfaceToReqConfigFile(MetadataNode interfaceNode, File reqFile) throws Exception {
		interfaceToConfigFile(interfaceNode, reqFile, "request");
	}
	
	private void interfaceToRspConfigFile(MetadataNode interfaceNode, File rspFile) throws Exception{
		interfaceToConfigFile(interfaceNode, rspFile, "response");
	}
	
	private void interfaceToConfigFile(MetadataNode interfaceNode, File configFile, String type) throws Exception{
		if(null != interfaceNode){
			Document configXMLDocument = DocumentHelper.createDocument();
			Element root = configXMLDocument.addElement(interfaceNode.getNodeID());
			this.setFieldAttribute(interfaceNode, root);
			IMetadataNode contentNode = interfaceNode.getChild(type);
			if(null != contentNode){
				if(contentNode.hasChild()){
					for(IMetadataNode childNode : contentNode.getChild()){
						node2XML(childNode, root);
					}
				}
			}
			String content = this.documentToXML(configXMLDocument);
			saveXMLFile(configFile, content);
		}
	}
	
	private void node2XML(IMetadataNode node, Element superElement){
		if(null != node && null != superElement){
			Element nodeElement = superElement.addElement(node.getNodeID());
			this.setFieldAttribute(node, nodeElement);
			if(node.hasChild()){
				for(IMetadataNode childNode : node.getChild()){
					node2XML(childNode, nodeElement);
				}
			}
		}
	}
	
	private boolean checkGenerateArgs(String serviceId,  String interfaceId, int interfaceType, String systemId){
		return serviceId!=null   && interfaceId != null && systemId != null;
	}

	/**
	 * 根据InvokeInfo生成配置文件
	 */
	public void generate(InvokeInfo invokeInfo)  {
		        String serviceId = invokeInfo.getServiceId();
				String interfaceID = invokeInfo.getEcode();
				String sysID = invokeInfo.getProvideSysId();
				String sysAb = systemDAO.getSystemAbById(sysID);
				String interfaceType = invokeInfo.getDirection();
				try{
					this.generate(serviceId, interfaceID, Integer.parseInt(interfaceType), sysAb);
				}catch(Exception e){ 
					e.printStackTrace();
				}		
	}

	@Override
	public void generate(String resource) throws Exception {
		// TODO Auto-generated method stub
		log.info("未实现重载方法!");
	}
	
}
