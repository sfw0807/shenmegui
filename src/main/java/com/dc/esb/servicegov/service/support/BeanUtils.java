package com.dc.esb.servicegov.service.support;

import com.dc.esb.servicegov.entity.SDANode;
import com.dc.esb.servicegov.entity.SDANode4I;
import com.dc.esb.servicegov.vo.SDANode4IVo;
import com.dc.esb.servicegov.vo.SDANodeVo;

public class BeanUtils {
	
	public static SDANode4IVo sdaNode4IToVO(SDANode4I node){
		SDANode4IVo vo = new SDANode4IVo();
		vo.setActionId(node.getActionId());
		vo.setInterfaceId(node.getInterfaceId());
		vo.setLength(node.getLength());
		vo.setMetadataId(node.getMetadataId());
		vo.setParentResourceId(node.getParentResourceId());
		vo.setRemark(node.getRemark());
		vo.setRequired(node.getRequired());
		vo.setResourceId(node.getResourceId());
		vo.setScale(node.getScale());
		vo.setStructAlias(node.getStructAlias());
		vo.setStructIndex(node.getStructIndex());
		vo.setStructName(node.getStructName());
		vo.setType(node.getType());
		return vo;
		
	}
	
	
	public static SDANodeVo sdaNodeToVO(SDANode node){
		SDANodeVo vo = new SDANodeVo();
		vo.setActionId(node.getActionId());
		vo.setMetadataId(node.getMetadataId());
		vo.setParentResourceId(node.getParentResourceId());
		vo.setRemark(node.getRemark());
		vo.setRequired(node.getRequired());
		vo.setResourceId(node.getResourceId());
		vo.setServiceId(node.getServiceId());
		vo.setStructAlias(node.getStructAlias());
		vo.setStructIndex(node.getStructIndex());
		vo.setStructName(node.getStructName());
		vo.setType(node.getType());
		return vo;
	}

}
