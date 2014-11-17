package com.dc.esb.servicegov.refactoring.resource.impl;

import static com.dc.esb.servicegov.refactoring.util.PackerUnPackerConstants.IN_CONF_DIR;
import static com.dc.esb.servicegov.refactoring.util.PackerUnPackerConstants.OUT_CONF_DIR;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.refactoring.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.resource.IConfigGenerater;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.AbstractGenerater;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.IMetadataNode;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.IMetadataNodeAttribute;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataNode;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.XMLHelper;

@Service
public class SpdbServiceXMLGenerater extends AbstractGenerater implements
		IConfigGenerater<InvokeInfo, List<File>> {

	private static String SPDB_SPEC_SERVICE_DEF_ROOT = "esb_xml";
	private static final Log log = LogFactory
			.getLog(SpdbServiceXMLGenerater.class);

	private IMetadataNode defaultInterface;
	@Autowired
	private DefaultInterfaceFetcher defaultInterfaceFetcher;
//	@Autowired
//	private PackerUnPackerConfigHelper packerUnPackerConfigHelper;
    @Autowired
    private OperationDAOImpl operationDAO;
    @Autowired
    private ServiceDataFromDB servicedataFromDB;
    @Autowired
    private MetadataStructHelper metadataStructHelper;
	// 存放服务
	List<File> serviceFile = new ArrayList<File>();
	private String content = null;
	private String prdMsgType;
	private String csmMsgType;

	// private IMetadataNode getFaultNode(){
	// IMetadataNode faultNode = new MetadataNode();
	// faultNode.setNodeID("Fault");
	// IMetadataNode faultCodeNode = new MetadataNode();
	// faultCodeNode.setNodeID("faultcode");
	// IMetadataNodeAttribute metadataNodeAttribute = new
	// MetadataNodeAttribute();
	// faultCodeNode.setProperty(metadataNodeAttribute);
	// metadataNodeAttribute.setProperty("metadataid", "faultcode");
	// faultNode.addChild(faultCodeNode);
	// IMetadataNode faultStringNode = new MetadataNode();
	// faultStringNode.setNodeID("faultstring");
	// IMetadataNodeAttribute metadataNodeAttribute2 = new
	// MetadataNodeAttribute();
	// faultStringNode.setProperty(metadataNodeAttribute2);
	// metadataNodeAttribute2.setProperty("metadataid", "faultstring");
	// faultStringNode.setProperty(metadataNodeAttribute2);
	// faultNode.addChild(faultStringNode);
	// return faultNode;
	// }

	private void invokeAddFaultNode(Element parentNode) {
		Element faultElement = parentNode.addElement("Fault");
		Element faultCodeElement = faultElement.addElement("faultcode");
		faultCodeElement.addAttribute("metadataid", "faultcode");
		Element faultStringElement = faultElement.addElement("faultstring");
		faultStringElement.addAttribute("metadataid", "faultstring");

	}

	public void generate(String resource) {
		try {
			this.generate(resource, false, false);
		} catch (Exception e) {
			// TODO should be caught and print to log here
			e.printStackTrace();
		}
	}

	/**
	 * 生成服务和默认接口的xml函数
	 * 
	 * @param serviceID
	 *            服务ID
	 * @param isExpdefaultInterface
	 *            标识是否输出默认接口：true为输出，false为不输出
	 * @param isExpInterface
	 *            是否导出接口文件：true为导出，false为不导出
	 * @throws IOException
	 */
	public void generate(String serviceID, boolean isExpdefaultInterface,
			boolean isExpInterface) throws IOException {

		if (null != serviceID) {
			List<String> operations = getAllOperationIds(serviceID);
			File inConfigDir = new File(IN_CONF_DIR);
			File outConfigDir = new File(OUT_CONF_DIR);
			if (!inConfigDir.exists()) {
				inConfigDir.mkdirs();
			}
			serviceFile.add(inConfigDir);

			if (!outConfigDir.exists()) {
				outConfigDir.mkdirs();
			}
			serviceFile.add(outConfigDir);

			if (null != operations && operations.size() > 0) {
				for (String operation : operations) {
					File inXmlFile = null;
					File outXmlFile = null;
					/******************************************
					 * 1.通过serviceid得到与此服务id相关的节点信息
					 ******************************************/
					MetadataNode sNode = servicedataFromDB.getNodeFromDB(serviceID, operation, true);
					metadataStructHelper.parseMetadataStruct(sNode);
					String xmlContent = this.createXML(operation, null, sNode,
							"");// 生成的服务xml文件
					String inDefFileName = IN_CONF_DIR + File.separator
							+ "service_" + serviceID + operation + ".xml";
					String outDefFileName = OUT_CONF_DIR + File.separator
							+ "service_" + serviceID + operation + ".xml";
					inXmlFile = new File(inDefFileName);
					outXmlFile = new File(outDefFileName);

					if (!inXmlFile.exists() || !outXmlFile.exists()) {
						inXmlFile.createNewFile();
						outXmlFile.createNewFile();
					}
					this.saveXMLFile(outXmlFile, xmlContent);
					this.saveXMLFile(inXmlFile, xmlContent);
				}
			}
		}
	}

	private void generateByOperation(String serviceId, String operationId) {
		try {
			File inXmlFile = null;
			File outXmlFile = null;
			/******************************************
			 * 1.通过serviceid得到与此服务id相关的节点信息
			 ******************************************/
			MetadataNode sNode = servicedataFromDB.getNodeFromDB(serviceId, operationId, true);
			metadataStructHelper.parseMetadataStruct(sNode);
			String xmlContent = this.createXML(serviceId, null, sNode, "");// 生成的服务xml文件
			String tmpOperationId = handleInterfaceIdForDupTrade(operationId);
			String inDefFileName = serviceId + tmpOperationId +"(" + csmMsgType + "-"
			+ prdMsgType + ")" + File.separator
					+ IN_CONF_DIR + File.separator + "service_" + serviceId
					+ tmpOperationId + ".xml";
			String outDefFileName = serviceId + tmpOperationId +"(" + csmMsgType + "-"
			+ prdMsgType + ")" + File.separator
					+ OUT_CONF_DIR + File.separator + "service_" + serviceId
					+ tmpOperationId + ".xml";
			inXmlFile = new File(inDefFileName);
			outXmlFile = new File(outDefFileName);

			if (!inXmlFile.exists() || !outXmlFile.exists()) {
				inXmlFile.createNewFile();
				outXmlFile.createNewFile();
			}
			this.saveXMLFile(outXmlFile, xmlContent);
			this.saveXMLFile(inXmlFile, xmlContent);
		} catch (IOException e) {
			log.info("[SERVICE DEF GEN]: FAIL TO CREATE FILES");
		}

	}

	public String getGenerateContent() {
		return this.content;
	}

	public List<File> getServiceFile() {
		return serviceFile;
	}

	/**
	 * 如果没有接口可以输出，则输出自己本身
	 * 
	 * @param serviceNode
	 *            要输出的服务节点
	 * @param filetype
	 *            要输出的文件类型，传输的时候可能的值为：request，response
	 * @throws IOException
	 */
	public String createXML(String serviceId, MetadataNode defaultNode,
			MetadataNode serviceNode, String filetype) throws IOException {
		String content = "";// xml内容
		MetadataNode rootNode = null;
		if ("".equals(filetype)) {
			rootNode = serviceNode;
		} else {
			rootNode = (MetadataNode) serviceNode.getMetadataNode(filetype);
		}

		// add Tech Header here
		addTechHeader(serviceId, rootNode);

		Document doc = DocumentHelper.createDocument();
		if (defaultNode == null) {
			defaultNode = serviceNode;
		}

		Element rootElement = doc.addElement(SPDB_SPEC_SERVICE_DEF_ROOT);

		IMetadataNodeAttribute rootAttr = defaultNode.getProperty();
		if (null != rootAttr) {
			Properties rootPro = rootAttr.getProperty();
			for (Object o : rootPro.keySet()) {
				String key = (String) o;
				if ("remark".equals(key) || "required".equals(key)) {
					continue;
				}

				String value = (String) rootPro.get(o);

				if ("type".equals(key)) {

					if (!"array".equals(value)) {
						continue;
					}
				}

				if ((null == value) || ("".equals(value))) {
					continue;
				}

				rootElement.addAttribute((String) o, (String) rootPro.get(o));
			}
		}

		this.metadataNodeToChildNode(rootNode, rootElement);
		content = XMLHelper.formatXML(doc, "utf-8");
		this.content = content;
		return content;
	}

	/**
	 * 
	 * @param file
	 * @param content
	 */
	public void saveXMLFile(File file, String content) {
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
			System.err.println(e.getMessage());
		}
	}

	private void addTechHeader(String serviceId, IMetadataNode base) {
		MetadataNode defaultIn = defaultInterfaceFetcher
				.findDefaultInterfaceIn(serviceId);
		MetadataNode defaultOut = defaultInterfaceFetcher
				.findDefaultInterfaceOut(serviceId);
		defaultInterface = defaultIn != null ? defaultIn : defaultOut;

		if (null != defaultInterface) {
			IMetadataNode defaultReq = defaultInterface.getChild("request");
			IMetadataNode baseRequest = base.getChild("request");
			if (null != defaultReq) {
				if (defaultReq.hasChild()) {
					for (IMetadataNode childNode : defaultReq.getChild()) {
						baseRequest.addChild(0, childNode);
					}
				}
			}
			IMetadataNode defaultRsp = defaultInterface.getChild("response");
			IMetadataNode baseResponse = base.getChild("response");
			if (null != defaultRsp) {
				if (defaultRsp.hasChild()) {
					for (IMetadataNode childNode : defaultRsp.getChild()) {
						baseResponse.addChild(0, childNode);
					}
				}
			}
		} else {
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void metadataNodeToChildNode(IMetadataNode node, Element element) {
		if (node.hasChild()) {
			List<IMetadataNode> childList = node.getChild();
			for (IMetadataNode mNode : childList) {
				if (mNode.hasChild()) {
					String type = null;
					Element structNode = element.addElement(mNode.getNodeID());
					this.setFieldAttribute(mNode, structNode);
					Element processElement = structNode;
					if (mNode.getNodeID().equalsIgnoreCase("request")) {
						processElement = structNode.addElement("sdoroot");

					} else if (mNode.getNodeID().equalsIgnoreCase("response")) {
						processElement = structNode.addElement("sdoroot");
						invokeAddFaultNode(processElement);
					}
					Element ele = processElement;
					if (mNode.hasAttribute() && mNode.hasAttribute("type")) {
						type = mNode.getProperty().getProperty("type");
						if (type.equalsIgnoreCase("array")) {
							ele = structNode.addElement("sdo");
						}
					}
					this.metadataNodeToChildNode((MetadataNode) mNode, ele);
				} else {
					Element fieldNode = element.addElement(mNode.getNodeID());
					this.setFieldAttribute(mNode, fieldNode);
				}
			}
		}
	}

	protected void setFieldAttribute(IMetadataNode node, Element element) {
		if (node.hasAttribute()) {
			Properties pro = node.getProperty().getProperty();
			for (Object objectProperty : pro.keySet()) {
				String key = (String) objectProperty;
				if ("remark".equals(key) || "required".equals(key)) {
					continue;
				}
				if ("exclude".equals(key)) {
					continue;
				}
				if ("expression".equalsIgnoreCase(key)) {
					continue;
				}
				String propertyValue = (String) pro.get(objectProperty);
				if ("type".equals(key)) {

					if (!"array".equals(propertyValue)) {
						continue;
					}
				}
				if ((null == propertyValue) || ("".equals(propertyValue))) {
					continue;
				}
				element.addAttribute(key, propertyValue);
			}
		}
	}

	private List<String> getAllOperationIds(String serviceId) {
		return operationDAO.getOperationIdsbyServiceId(serviceId);
	}

	public void clearXMLFile() {
		this.serviceFile.clear();
	}
	
	/**
	 * @author Vincent Fan
	 *  处理 多只交易共用交易码的情况下，InterfaceId增加了区分码的做法。
	 *  将区分码(由 "-"分隔)去除
	 */
	private String handleInterfaceIdForDupTrade(String interfaceId){
		String tmpId = interfaceId;
		if(interfaceId.indexOf("-") > -1){
			tmpId = interfaceId.substring(0,interfaceId.indexOf("-"));
		}
		return tmpId;
	}


	@Override
	public List<File> generate(InvokeInfo invokeInfo)
			throws Exception {
		log.info("[SERVICE DEF GENERATER]: start to generate service defination file");
		List<File> files = null;
		if (null != invokeInfo) {
			this.csmMsgType = invokeInfo.getConsumeMsgType();
			this.prdMsgType = invokeInfo.getProvideMsgType();
			files = new ArrayList<File>();
			String operationId = invokeInfo.getOperationId();
			String interfaceId = invokeInfo.getEcode();
//			String serviceId = packerUnPackerConfigHelper
//					.getServiceIdByOperationId(operationId);
			String serviceId = invokeInfo.getServiceId();
			//TODO this is added for dup operation id, please remove it when the dup operationid issue be resolved
			String tmpOperationId = handleInterfaceIdForDupTrade(operationId);
			log.info("[Service Def Generater]: start to generate Service Def files for service:["
					+ serviceId
					+ "], operation: ["
					+ operationId
					+ "], interface: [" + interfaceId + "]");

			File inConfigDir = new File(serviceId + tmpOperationId + "(" + invokeInfo.getConsumeMsgType() + "-"
					+ invokeInfo.getProvideMsgType() + ")" +
					 File.separator + IN_CONF_DIR);
			File outConfigDir = new File(serviceId + tmpOperationId + "(" + invokeInfo.getConsumeMsgType() + "-"
					+ invokeInfo.getProvideMsgType() + ")" +
					 File.separator + OUT_CONF_DIR);
			if (!inConfigDir.exists()) {
				inConfigDir.mkdirs();
			}
			if (!outConfigDir.exists()) {
				outConfigDir.mkdirs();
			}
			files.add(inConfigDir);
			files.add(outConfigDir);
			generateByOperation(serviceId, operationId);

		}
		return files;
	}

}
