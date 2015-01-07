package com.dc.esb.servicegov.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.dao.impl.MetaStructNodeDAOImpl;
import com.dc.esb.servicegov.entity.MetaStructNode;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.service.impl.InterfaceManager;
import com.dc.esb.servicegov.service.impl.MetadataManagerImpl;
import com.dc.esb.servicegov.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.vo.MetadataViewBean;
import com.dc.esb.servicegov.vo.SDA4I;

@Controller
@RequestMapping("/config")
public class ConfigExportController {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private InterfaceManager interfaceManager;
	@Autowired
	private ServiceManagerImpl serviceManager;

	@Autowired
	private MetaStructNodeDAOImpl metaStructNodeDAO;

	@Autowired
	private MetadataManagerImpl metadataManager;

	@RequestMapping(method = RequestMethod.GET, value = "/export", headers = "Accept=application/json")
	public @ResponseBody
	boolean exportService(HttpServletRequest request,
			HttpServletResponse response) {
		File reqFile = null;
		File rspFile = null;
		OutputStream reqOut = null;
		OutputStream rspOut = null;
		boolean success = false;
		try {
			reqFile = new File("reqFile");
			rspFile = new File("rspFile");
			log.info("generate files [" + reqFile.getAbsolutePath() + "], ["
					+ rspFile.getAbsolutePath() + "]");
			reqFile.createNewFile();
			rspFile.createNewFile();

			reqOut = new BufferedOutputStream(new FileOutputStream(reqFile));
			rspOut = new BufferedOutputStream(new FileOutputStream(rspFile));

			SDA4I ida = serviceManager
					.getSDA4IofInterfaceId("UIASQueryUserInfo");
			SDA4I reqIda = null;
			SDA4I rspIda = null;
			Document reqDocument = DocumentHelper.createDocument();
			Document rspDocument = DocumentHelper.createDocument();
			Element reqEle = renderTemplate(reqDocument);
			Element rspEle = renderTemplate(rspDocument);
			for (SDA4I child : ida.getChildNode()) {
				String nodeStructName = child.getValue().getStructName();
				log.info("root child [" + nodeStructName + "]");
				if ("request".equalsIgnoreCase(nodeStructName)) {
					reqIda = child;
					for (SDA4I childOfReq : reqIda.getChildNode()) {
						log.info("render node ["
								+ childOfReq.getValue().getStructName() + "]");
						renderNode(childOfReq, reqEle);
					}
				} else if ("response".equalsIgnoreCase(nodeStructName)) {
					rspIda = child;
					for (SDA4I childOfRsp : rspIda.getChildNode()) {
						log.info("render node ["
								+ childOfRsp.getValue().getStructName() + "]");
						Element elementOfResult = rspEle.addElement(childOfRsp
								.getValue().getStructName());
						elementOfResult.addAttribute("metadataid", childOfRsp
								.getValue().getMetadataId());
						elementOfResult.addAttribute("type", "array");
						elementOfResult.addAttribute("is_struct", "false");

						for (SDA4I childOfresult : childOfRsp.getChildNode()) {
							log.info("render node ["
									+ childOfresult.getValue().getStructName()
									+ "]");
							renderNode(childOfresult, elementOfResult);
						}
						// renderNode(childOfRsp, rspEle);
					}
				}
			}

			XMLWriter writer = null;
			OutputFormat format = OutputFormat.createPrettyPrint();
			writer = new XMLWriter(reqOut, format);
			writer.write(reqDocument);

			writer = new XMLWriter(rspOut, format);
			writer.write(rspDocument);
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			try {
				reqOut.close();
				rspOut.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return success;
	}

	private Element renderTemplate(Document doc) {
		// <QueryUserInfoResult package_type="xml">
		Element rootEle = doc.addElement("UIASQueryUserInfo");
		rootEle.addAttribute("package_type", "xml");
		return rootEle;
	}

	private void renderNode(SDA4I ida, Element element) throws DataException {
		String idaID = ida.getValue().getStructName();
		String idaMetadataId = ida.getValue().getStructName();
		Element child = element.addElement(idaID);
		child.addAttribute("metadataid", idaMetadataId);
		child.addAttribute("type", "array");
		child.addAttribute("is_struct", "false");
		parseStruct(idaID, child);
	}

	private Element parseStruct(String nodeName, Element element)
			throws DataException {
		log.info("render struct node[" + nodeName + "]");
		List<MetaStructNode> metaStructNodes = null;
		if(nodeName.indexOf("RoleInfo") > 0){
			log.info("**********parse RoleInfo ["+nodeName+"]");
			metaStructNodes = metaStructNodeDAO.findBy(
					"structId", "RoleInfo");
		}else if(nodeName.indexOf("TaskInfo") > 0){
			log.info("**********parse TaskInfo ["+nodeName+"]");
			metaStructNodes = metaStructNodeDAO.findBy(
					"structId", "TaskInfo");
		}else if(nodeName.indexOf("FrontFncInfo") > 0){
			log.info("**********parse FrontFncInfo ["+nodeName+"]");
			metaStructNodes = metaStructNodeDAO.findBy(
					"structId", "FrontFncInfo");
		}else if(nodeName.indexOf("ChlOrgs") > 0){
			log.info("**********parse ChildOrgs ["+nodeName+"]");
			metaStructNodes = metaStructNodeDAO.findBy(
					"structId", "ChildOrgs");
		}else if(nodeName.indexOf("ChlGrpList") > 0){
			log.info("**********parse ChildGroupList ["+nodeName+"]");
			metaStructNodes = metaStructNodeDAO.findBy(
					"structId", "ChildGroupList");
		}else{
			metaStructNodes = metaStructNodeDAO.findBy(
					"structId", nodeName);
		}
		for (MetaStructNode metaStructNode : metaStructNodes) {
			MetadataViewBean metadata = metadataManager
					.getMetadataById(metaStructNode.getElementId());
			String metadataId = metadata.getMetadataId();
			String metadataType = metadata.getType();
			List<MetaStructNode> childNodes = null;
			if(metadataId.indexOf("RoleInfo") > 0){
				childNodes = metaStructNodeDAO.findBy(
						"structId", "RoleInfo");
			}else if(metadataId.indexOf("TaskInfo") > 0){
				childNodes = metaStructNodeDAO.findBy(
						"structId", "TaskInfo");
			}else if(metadataId.indexOf("FrontFncInfo") > 0){
				childNodes = metaStructNodeDAO.findBy(
						"structId", "FrontFncInfo");
			}else if(metadataId.indexOf("ChlOrgs") > 0){
				childNodes = metaStructNodeDAO.findBy(
						"structId", "ChildOrgs");
			}else if(metadataId.indexOf("ChlGrpList") > 0){
				childNodes = metaStructNodeDAO.findBy(
						"structId", "ChildGroupList");
			}else{
				childNodes = metaStructNodeDAO.findBy(
						"structId", metadataId);
			}
			
			if (null != childNodes && childNodes.size() > 0) {
				Element nodeElement = element.addElement(metaStructNode
						.getElementName());
				nodeElement.addAttribute("metadataid", metadataId);
				nodeElement.addAttribute("type", "array");
				nodeElement.addAttribute("is_struct", "false");
				parseStruct(metadataId, nodeElement);
			} else {
				Element nodeElement = element.addElement(metaStructNode
						.getElementName());
				nodeElement.addAttribute("metadataid", metadataId);
			}

		}

		return null;

	}

}
