package com.dc.esb.servicegov.resource.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.dao.impl.MetadataStructsAttrDAOImpl;
import com.dc.esb.servicegov.dao.impl.MetadataStructsDAOImpl;
import com.dc.esb.servicegov.dao.impl.MetadataStructsExpressionDAOImpl;
import com.dc.esb.servicegov.entity.MetadataStructsAttr;
import com.dc.esb.servicegov.entity.MetadataStructsExpression;
import com.dc.esb.servicegov.resource.metadataNode.IMetadataNode;
import com.dc.esb.servicegov.resource.metadataNode.IMetadataNodeAttribute;
import com.dc.esb.servicegov.resource.metadataNode.MetadataNode;
import com.dc.esb.servicegov.resource.metadataNode.MetadataNodeAttribute;

/**
 * 元数据结构信息解析类
 * @author luof
 *
 */
@Service
public class MetadataStructHelper {

	private static final Log log = LogFactory.getLog(MetadataStructHelper.class);
	
	@Autowired
	private MetadataStructsExpressionDAOImpl mdtStructExpressionDAO;
	@Autowired
	private MetadataStructsAttrDAOImpl mdtStructAttrDAO;
	@Autowired
	private MetadataStructsDAOImpl mdtStructDAO;

	public IMetadataNode parseMetadataStruct(IMetadataNode metadataNode) {
		if (null != metadataNode) {

			if (isMetadataStruct(metadataNode)) {
				metadataNode.getProperty().setProperty("type", "array");
				MetadataNode structNode = getMetadataStructure(metadataNode
						.getNodeID());
				for (IMetadataNode childMetadataNode : structNode.getChild()) {
					metadataNode.addChild(childMetadataNode);
				}
			}
		}
		if (metadataNode.hasChild()) {
			for (IMetadataNode childMetadataNode : metadataNode.getChild()) {
				parseMetadataStruct(childMetadataNode);
			}
		}
		return metadataNode;
	}

	/**
	 * parse a metadata node
	 * 
	 * @param metadataNode
	 * @return
	 */
	public IMetadataNode parseMetadataStructForInterface(
			IMetadataNode metadataNode) {

		if (null != metadataNode) {
			IMetadataNodeAttribute attribute = metadataNode.getProperty();
			if (null != attribute) {
				String metadataId = attribute.getProperty("metadataid");
				if (null != metadataId) {
					if (isMetadataStruct(metadataNode)) {
						metadataNode.getProperty().setProperty("type", "array");
						MetadataNode structNode = getMetadataStructureForInterface(
								metadataNode.getNodeID(), metadataId,metadataNode.getNodeID());

						for (IMetadataNode childMetadataNode : structNode
								.getChild()) {
							metadataNode.addChild(childMetadataNode);
						}
					}
					if (metadataNode.hasChild()) {
						for (IMetadataNode childMetadataNode : metadataNode
								.getChild()) {
							parseMetadataStructForInterface(childMetadataNode);
						}
					}
				}
			}
		}
		return metadataNode;
	}

	/**
	 * 
	 * @param structId
	 * @return
	 */
	public MetadataNode getMetadataStructure(String structId) {
		MetadataNode structNode = new MetadataNode();
		IMetadataNodeAttribute nodeAttr = new MetadataNodeAttribute();
		structNode.setNodeID(structId);
		structNode.setMetadataID(structId);
		nodeAttr.setProperty("metadataid", structId);
		structNode.setProperty(nodeAttr);
		List<MetadataStructsAttr> structsList = mdtStructAttrDAO.getMdtStructsAttrByStructId(structId);
		if ((structsList != null) && (structsList.size() > 0)) {
			for (MetadataStructsAttr childMetadataStruct : structsList) {
				MetadataNode childStructNode = getMetadataStructure(childMetadataStruct.getMetadataId());
				structNode.addChild(childStructNode);
			}
		}
		return structNode;
	}

	public MetadataNode getMetadataStructureForInterface(String structId,
			String metadataId, String rootStructId) {
		MetadataNode structNode = new MetadataNode();
		structNode.setNodeID(structId);
		structNode.setMetadataID(metadataId);
		if(rootStructId.equals("Xperson")&&structId.equalsIgnoreCase("OccupationTpCd")){
			log.info("break");
		}
		MetadataStructsExpression mdtStructExpression = mdtStructExpressionDAO.getByStructIdAndElementId(rootStructId, structId);
		IMetadataNodeAttribute attr = structNode.getProperty();
		if(null == attr){
			attr = new MetadataNodeAttribute();
		}
		attr.setProperty(mdtStructExpression.getAttributeId(), mdtStructExpression.getAttributeName());
	    structNode.setProperty(attr);
		// structNode.setNodeID(metadataId);
		// structNode.setMetadataID(structId);
		IMetadataNodeAttribute mdtAttr = structNode.getProperty();
		if(null == mdtAttr){
			mdtAttr = new MetadataNodeAttribute();
		}
		mdtAttr.setProperty("metadataid", metadataId);
		structNode.setProperty(mdtAttr);
		List<MetadataStructsAttr> structsList = mdtStructAttrDAO.getMdtStructsAttrByStructId(structId);
		if ((structsList != null) && (structsList.size() > 0)) {
			for (MetadataStructsAttr childMetadataStruct : structsList) {
				MetadataNode childStructNode = getMetadataStructureForInterface(
						childMetadataStruct.getElementName(),
						childMetadataStruct.getMetadataId(), structId);
				structNode.addChild(childStructNode);
			}
		}
		return structNode;
	}

	public boolean isMetadataStruct(IMetadataNode metadataNode) {
		if (!metadataNode.hasChild()) {
			IMetadataNodeAttribute attribute = metadataNode.getProperty();
			if (null != attribute) {
				String metadataId = attribute.getProperty("metadataid");
				return mdtStructDAO.isStructs(metadataId);
			}
		}

		return false;

	}

}
