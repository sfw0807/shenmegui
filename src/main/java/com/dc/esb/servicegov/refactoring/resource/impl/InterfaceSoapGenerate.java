package com.dc.esb.servicegov.refactoring.resource.impl;

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

import com.dc.esb.servicegov.refactoring.dao.impl.InvokeInfoDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.AbstractGenerater;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.IMetadataNode;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataNode;




@Service
public class InterfaceSoapGenerate extends AbstractGenerater {
	
	private static final Log log = LogFactory.getLog(InterfaceSoapGenerate.class);
	
	@Autowired
	private ServiceDataFromDB serviceDataFromDB;
	@Autowired
	private InvokeInfoDAOImpl invokeInfoDAO;

	private String CONSUMERINTERFACE = "0";
	private String PRIVODERINTERFACE = "1";
	private String interfaceType;
	private String sysID;
	private String ENVELOPE = "Envelope";
	private String BODY = "BODY";
	private String requestContent = "";
	private String responseContent = "";
	
	private List<File> interfaceFiles = new ArrayList<File>();
	
	protected String host;
	
	protected List<String> getAllOperationIds(String serviceId) {
		List<String> operations = null;
		if (null != serviceId) {
			operations = new ArrayList<String>();
			List<String> subServices = serviceDataFromDB.getAllSubServiceList(serviceId);
			if (serviceDataFromDB.hasOperation(serviceId)) {
				operations.add(serviceId);
			}
			for (String subService : subServices) {
				if (serviceDataFromDB.hasOperation(subService)) {
					operations.add(subService);
				}
			}
		}
		return operations;
	}
	
	protected void getHost(String serviceID) {
		this.host = serviceDataFromDB.getWSDLHost(serviceID);
	}
	
	protected MetadataNode getOperationNode(String operationId,String serviceId){
		return  serviceDataFromDB.getNodeFromDB(serviceId,operationId, true);
	}

	public void generate(String interfaceID, String interfaceType, String sysID) {
		this.interfaceType = interfaceType;
		this.sysID = sysID;
		this.generate(interfaceID);
	}

	@Override
	public void generate(String interfaceID) {
		InvokeInfo invokeInfo = invokeInfoDAO.getInvokeInfoByEcode(interfaceID);
		XMPassedInterfaceDataFromDB db = new XMPassedInterfaceDataFromDB();
		MetadataNode interfaceNode = db.getNodeFromDB(interfaceID);
		try{
		this.interface2SoapXMLFiles(interfaceNode, invokeInfo);
		}catch(Exception e){
			log.error("Generate soap interface error!"+e);
		}

	}

	private void interface2SoapXMLFiles(IMetadataNode interfaceNode,
			InvokeInfo invokeInfo) throws Exception {
		String serviceID = invokeInfo.getServiceId();
		IMetadataNode request = interfaceNode.getMetadataNode("request");
		IMetadataNode response = interfaceNode.getMetadataNode("response");
		Document requestDoc = this.MetadataNode2Document(request);
		Document responseDoc = this.MetadataNode2Document(response);
		String requestFile = "";
		String responseFile = "";
		if(interfaceType.equals(CONSUMERINTERFACE)){
			requestFile = "channel_"+sysID+"_service_" + serviceID + ".xml";
			responseFile = "service_" + serviceID + "_system_"+sysID+".xml";
			
		}else if(interfaceType.equals(PRIVODERINTERFACE)){
			requestFile = "service_" + serviceID + "_system_"+sysID+".xml";
			responseFile = "channel_"+sysID+"_service_" + serviceID + ".xml";
		}
		
		
		this.responseContent = this.documentToXML(responseDoc);
		this.requestContent = this.documentToXML(requestDoc);
		File reqSoapFile = new File(requestFile);
		this.saveXMLFile(reqSoapFile, requestContent);
		interfaceFiles.add(reqSoapFile);
		
		File respSoapFile = new File(responseFile);
		this.saveXMLFile(respSoapFile, responseContent);
		interfaceFiles.add(respSoapFile);
		
		
	}
	
	private Document MetadataNode2Document(IMetadataNode node){
		Document doc = DocumentHelper.createDocument();
		Element rootElement = doc.addElement(ENVELOPE);
		Element bodyEle = rootElement.addElement(BODY);
		for(IMetadataNode cNode:node.getChild()){
			Element cele = bodyEle.addElement(cNode.getNodeID());
			this.setFieldAttribute(cNode, cele);
			if(cNode.hasChild()){
				this.converChild(cNode, cele);
			}
		}
		return doc;
	}
	
	public MetadataNode getRootMetadata() {
		MetadataNode root = null;
		
		return root;
	}
	
	private void converChild(IMetadataNode node,Element ele){
		for(IMetadataNode cNode:node.getChild()){
			Element cele = ele.addElement(cNode.getNodeID());
			this.setFieldAttribute(cNode, cele);
			if(cNode.hasChild()){
				Element processEle = cele;
				this.converChild(cNode, processEle);
			}
		}
	}
	

	public List<File> getInterfaceFiles() {

		return this.interfaceFiles;
	}

	public String getRequestSoapXml() {

		return this.requestContent;
	}

	public String getResponseSoapXml() {

		return this.responseContent;
	}

}
