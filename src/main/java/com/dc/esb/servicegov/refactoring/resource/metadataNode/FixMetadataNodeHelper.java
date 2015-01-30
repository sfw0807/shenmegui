package com.dc.esb.servicegov.refactoring.resource.metadataNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;

public class FixMetadataNodeHelper {

	private Log log = LogFactory.getLog(FixMetadataNodeHelper.class);
	private final static String ROOTSTRING = "root";
	// 对应response
	private final static String REQUESTSTRING = "request";
	// 信用卡固定头信息元数据
	private final static String TRANCODE = "TranCode";
	private final static String RETURNCODE = "ReturnCode";
	private final static String BANKCODE = "BankCode";
	private final static String TRANSOURCE = "TranSource";
	private final static String BRANCHID = "BranchId";
	private final static String TRANTELLERNO = "TranTellerNo";
	private final static String TRANSEQNO = "TranSeqNo";
	private final static String STATUSCHECK = "STATUS_CHECK";
	private final static String RETURNMSG = "ReturnMsg";
	private final static String CHANNEL = "Channel";
	private final static String CARDNO = "CardNo";
	// 信用卡密码字段特殊处理
	private final static String PWD = "Pwd";

	// private final static String RESPONSESTRING = "response";

	public static FixMetadataNodeHelper fixMetadataNodeHelper = null;

	private FixMetadataNodeHelper() {

	}

	public static FixMetadataNodeHelper getInstance() {
		if (fixMetadataNodeHelper == null) {
			fixMetadataNodeHelper = new FixMetadataNodeHelper();
		}
		return fixMetadataNodeHelper;
	}

	public Document MetadataNode2DocumentWithInclude(IMetadataNode node,
			List<Namespace> namespaces, List<String> attrInclude,
			String rqLabel, String interfaceId) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(ROOTSTRING);
		// 添加报文头的命名空间信息
		if (null != namespaces) {
			for (Namespace namespace : namespaces) {
				root.add(namespace);
			}
		}
		// 添加报文头的属性信息和报文头配置信息
		if (REQUESTSTRING.equals(rqLabel)) {
			root.addAttribute("package_type", "fix");
			// 编码格式有待解决，暂定相同
			root.addAttribute("encoding", "GB18030");
			addReqHeadInfo(root, interfaceId);
		} else {
			root.addAttribute("package_type", "fix");
			addRsqHeadInfo(root, interfaceId);
		}
		// 删除卡号CardNo以上的FIX报文头数据
		List<IMetadataNode> deleteNodes = new ArrayList<IMetadataNode>();
		for (IMetadataNode cNode : node.getChild()) {
			if ("CardNo".equals(cNode.getMetadataID())) {
				break;
			} else {
				deleteNodes.add(cNode);
			}
		}
		for (IMetadataNode delNode : deleteNodes) {
			node.remove(delNode);
		}
		setFieldAttributeWithInculde(node, root, attrInclude, rqLabel);
		appendChildNodeToElementWithInclude(node, root, attrInclude, rqLabel);
		return document;
	}

	// 针对信用卡定长报文FIX类型，从CardNo开始生成配置文件
	public void appendChildNodeToElementWithInclude(IMetadataNode metadataNode,
			Element element, List<String> include, String rqLabel) {

		for (IMetadataNode cNode : metadataNode.getChild()) {
			Element cele = element.addElement(cNode.getNodeID());
			setFieldAttributeWithInculde(cNode, cele, include, rqLabel);
			if (cNode.hasChild()) {
				IMetadataNodeAttribute attr = cNode.getProperty();
				if (null != attr) {
					if (cNode.hasAttribute("type")) {
						if (attr.getProperty("type").equalsIgnoreCase("struct")) {
							attr.remove("type");
							attr.setProperty("type", "array");
						}
						if (attr.getProperty("type").equals("array")) {
							String is_struct = cNode.getProperty().containsKey(
									"is_struct") ? cNode.getProperty()
									.getProperty("is_struct") : "false";
							cele.addAttribute("is_struct", is_struct);
						}
						if ("".equals(attr.getProperty("type"))) {
							if (cNode.hasAttribute("remark")) {
								if (attr.getProperty("remark") != null
										&& attr.getProperty("remark")
												.toLowerCase().startsWith(
														"start")) {
									cele.addAttribute("is_struct", "true");
								}
							}
						}
					}
				}
				appendChildNodeToElementWithInclude(cNode, cele, include,
						rqLabel);
			}
		}
	}

	public void setFieldAttributeWithInculde(IMetadataNode node,
			Element element, List<String> include, String rqLabel) {
		boolean arrayFlag = false;
		if (node.hasAttribute()) {
			Properties pro = node.getProperty().getProperty();
			String value = (String) pro.get("type");
			String length = (String) pro.get("length");
			String scale = (String) pro.get("scale");
			if (null != value) {
				if (value.equalsIgnoreCase("struct")) {
					pro.remove("type");
					pro.setProperty("type", "array");
				} else if (value.equalsIgnoreCase("array")) {
					pro.setProperty("type", "array");
					arrayFlag = true;
				} else if ("".equals(value)
						&& !"SvcBody".equals(node.getNodeID())) {
					pro.setProperty("type", "array");
					arrayFlag = true;
				} else {
					pro.setProperty("length", length);
				}
			}
			for (Object objectProperty : pro.keySet()) {
				String key = (String) objectProperty;
				if (include.contains(key + "=" + (String) pro.get(key))) {
					element.addAttribute((String) objectProperty, (String) pro
							.get(objectProperty));
				} else if (include.contains(key)) {
					String propertyValue = (String) pro.get(key);
					if ((propertyValue != null) && (!propertyValue.equals(""))) {
						element.addAttribute((String) objectProperty,
								(String) pro.get(objectProperty));
					}
				}
			}
			// 数组类型添加size属性
			if (arrayFlag) {
				// 获取重复次数
				String remark = (String) pro.get("remark");
				log.info("FIX定长报文数组 [" + node.getNodeID() + "] 是否包含长度描述："
						+ remark);
				if (remark.contains("重复")) {
					String size = remark.substring(remark.indexOf("重复") + 2,
							remark.indexOf("重复") + 4);
					element.addAttribute("size", size);
				} else {
					element.addAttribute("size", "");
				}
			}
			// request请求端数据类型为n的需要添加fill_char和align属性
			if (REQUESTSTRING.equals(rqLabel)) {
				if ("n".equalsIgnoreCase(value)) {
					element.addAttribute("fill_char", "000000");
					element.addAttribute("align", "right");
				}
			}
			// number数据类型添加特殊函数处理表达式
			if (scale != null && !"".equals(scale)) {
				// parentNode
				IMetadataNode parentNode = node.getParentNode();
				// 判断parentId是否是SvcBody、request、response
				if (!"request".equals(parentNode.getNodeID())
						&& !"response".equals(parentNode.getNodeID())
						&& !"SvcBody".equals(parentNode.getNodeID())) {
					String expression = "ff:CreditFormatReturnNumber(" + length
							+ "," + scale + ",${/root/"
							+ parentNode.getNodeID() + "[*]/sdo/"
							+ node.getNodeID() + "})";
					element.addAttribute("expression", expression);
				} else {
					String expression = "ff:CreditFormatReturnNumber(" + length
							+ "," + scale + ",${/root/" + node.getNodeID()
							+ "})";
					element.addAttribute("expression", expression);
				}
			}
			// 信用卡密码字段添加特殊函数表达式处理
			if (PWD.equals(node.getNodeID())) {
				StringBuffer buf = new StringBuffer();
				buf
						.append("com.spdbank.esb.container.service.util.ESBFunction.transPIN(sdo,");
				buf.append("'/sdoroot/SvcBody/PwdCheckFlag',");
				buf.append("'/sdoroot/SvcBody/Pwd',");
				buf.append("'/sdoroot/ReqSvcHeader/KeyVersionNo',");
				buf
						.append("'/sdoroot/ReqHeader/MacOrgId','/sdoroot/SvcBody/CardNo')");
				element.addAttribute("expression", buf.toString());
			}
		}
	}

	// // 找到metadataId对应的Metadata元数据对象
	// public Metadata getMdtById(String metadataId){
	// if(metadataId == null || "".equals(metadataId)){
	// return null;
	// }
	// for(Metadata mdt: mdtList){
	// if(metadataId.equals(mdt.getMetadataId())){
	// return mdt;
	// }
	// }
	// return null;
	// }

	/**
	 * 添加FIX报文请求方的头信息
	 * 
	 * @param element
	 */
	public void addReqHeadInfo(Element root, String interfaceId) {
		// 交易码
		Element tranCodeEle = root.addElement(TRANCODE);
		tranCodeEle.addAttribute("length", "4");
		tranCodeEle.addAttribute("metadataid", TRANCODE);
		tranCodeEle.addAttribute("expression", "'" + interfaceId + "'");
		// 
		Element returnCodeEle = root.addElement(RETURNCODE);
		returnCodeEle.addAttribute("length", "6");
		returnCodeEle.addAttribute("metadataid", RETURNCODE);
		returnCodeEle.addAttribute("expression", "'      '");
		returnCodeEle.addAttribute("isSdoHeader", "true");
		//
		Element bankCodeEle = root.addElement(BANKCODE);
		bankCodeEle.addAttribute("length", "4");
		bankCodeEle.addAttribute("metadataid", BANKCODE);
		//
		Element tranSourceEle = root.addElement(TRANSOURCE);
		tranSourceEle.addAttribute("length", "2");
		tranSourceEle.addAttribute("metadataid", TRANSOURCE);
		//
		Element branchIdEle = root.addElement(BRANCHID);
		branchIdEle.addAttribute("length", "6");
		branchIdEle.addAttribute("metadataid", BRANCHID);
		branchIdEle.addAttribute("fill_char", "000000");
		branchIdEle.addAttribute("align", "right");
		//
		Element tranTellerEle = root.addElement(TRANTELLERNO);
		tranTellerEle.addAttribute("length", "6");
		tranTellerEle.addAttribute("metadataid", TRANTELLERNO);
		tranTellerEle.addAttribute("fill_char", "000000");
		tranTellerEle.addAttribute("align", "right");
		//
		Element tranSeqNoEle = root.addElement(TRANSEQNO);
		tranSeqNoEle.addAttribute("length", "6");
		tranSeqNoEle.addAttribute("metadataid", TRANSEQNO);
		tranSeqNoEle.addAttribute("fill_char", "000000");
		tranSeqNoEle.addAttribute("align", "right");
		tranSeqNoEle.addAttribute("expression",
				"ff:getCreditSerialNo(${/sdoroot/reqsvcheader/TranSeqNo})");
	}

	/**
	 * 添加FIX报文响应方的头信息
	 * 
	 * @param element
	 */
	public void addRsqHeadInfo(Element root, String interfaceId) {
		// 交易码
		Element tranCodeEle = root.addElement(TRANCODE);
		tranCodeEle.addAttribute("length", "4");
		tranCodeEle.addAttribute("metadataid", TRANCODE);
		// 
		Element returnCodeEle = root.addElement(RETURNCODE);
		returnCodeEle.addAttribute("length", "6");
		returnCodeEle.addAttribute("metadataid", RETURNCODE);
		// returnCode下新增固定的一些节点值
		Element statusCheckEle = root.addElement(STATUSCHECK);
		statusCheckEle.addAttribute("successValue", "000000");
		statusCheckEle.addAttribute("searchField",
				"/sdoroot/rspsvcheader/returncode");
		// status下固定添加
		Element bankCodeEle2 = statusCheckEle.addElement(BANKCODE);
		bankCodeEle2.addAttribute("length", "4");
		bankCodeEle2.addAttribute("metadataid", BANKCODE);

		Element returnMsgEle2 = statusCheckEle.addElement(RETURNMSG);
		returnMsgEle2.addAttribute("length", "0");
		returnMsgEle2.addAttribute("metadataid", RETURNMSG);
		returnMsgEle2
				.addAttribute(
						"expression",
						"com.spdbank.esb.container.service.util.ESBFunction.getErrorMsgForBCMS(sdo,'/sdoroot/rspsvcheader/returncode')");

		Element channelEle2 = statusCheckEle.addElement(CHANNEL);
		channelEle2.addAttribute("length", "2");
		channelEle2.addAttribute("metadataid", CHANNEL);

		Element branchIdEle2 = statusCheckEle.addElement(BRANCHID);
		branchIdEle2.addAttribute("length", "6");
		branchIdEle2.addAttribute("metadataid", BRANCHID);

		Element tranTellerNoEle2 = statusCheckEle.addElement(TRANTELLERNO);
		tranTellerNoEle2.addAttribute("length", "6");
		tranTellerNoEle2.addAttribute("metadataid", TRANTELLERNO);

		Element tranSeqEle2 = statusCheckEle.addElement(TRANSEQNO);
		tranSeqEle2.addAttribute("length", "6");
		tranSeqEle2.addAttribute("metadataid", TRANSEQNO);

		Element cardNoEle2 = statusCheckEle.addElement(CARDNO);
		cardNoEle2.addAttribute("length", "19");
		cardNoEle2.addAttribute("metadataid", CARDNO);
		// 固定returnCode
		Element returnCodeEle_2 = root.addElement(RETURNCODE);
		returnCodeEle_2.addAttribute("length", "0");
		returnCodeEle_2.addAttribute("metadataid", RETURNCODE);
		returnCodeEle_2.addAttribute("expression", "'" + "000000000000" + "'");
		// 固定returnMsg
		Element returnMsgEle_2 = root.addElement(RETURNMSG);
		returnMsgEle_2.addAttribute("length", "0");
		returnMsgEle_2.addAttribute("metadataid", RETURNMSG);
		returnMsgEle_2.addAttribute("expression", "'" + "交易成功" + "'");
		//
		Element bankCodeEle = root.addElement(BANKCODE);
		bankCodeEle.addAttribute("length", "4");
		bankCodeEle.addAttribute("metadataid", BANKCODE);
		//
		Element tranSourceEle = root.addElement(TRANSOURCE);
		tranSourceEle.addAttribute("length", "2");
		tranSourceEle.addAttribute("metadataid", TRANSOURCE);
		//
		Element branchIdEle = root.addElement(BRANCHID);
		branchIdEle.addAttribute("length", "6");
		branchIdEle.addAttribute("metadataid", BRANCHID);
		//
		Element tranTellerEle = root.addElement(TRANTELLERNO);
		tranTellerEle.addAttribute("length", "6");
		tranTellerEle.addAttribute("metadataid", TRANTELLERNO);
		//
		Element tranSeqNoEle = root.addElement(TRANSEQNO);
		tranSeqNoEle.addAttribute("length", "6");
		tranSeqNoEle.addAttribute("metadataid", TRANSEQNO);
	}
}
