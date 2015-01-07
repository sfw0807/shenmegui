package com.dc.esb.servicegov.refactoring.util;

import java.util.HashMap;
import java.util.Map;

import com.dc.esb.servicegov.refactoring.resource.metadataNode.MetadataNode;

public class DefaultTemplate {
	
	public static Map<String, MetadataNode> templateMap = new HashMap<String, MetadataNode>();

	public static void saveTemplate(String templateId, MetadataNode node) {
		if (!templateMap.containsKey(templateId)) {
			templateMap.put(templateId, node);
		}
	}

	public static boolean hasTemplate(String templateId) {
		if (templateMap.containsKey(templateId)) {
			return true;
		} else {
			return false;
		}
	}

	public static MetadataNode getTemplate(String templateId) {
			return templateMap.get(templateId);
	}

	/**
	 * 检查是否是默认接口模板
	 * 
	 * @param templateId
	 * @return
	 */
	public static boolean checkIsIDATemplate(String templateId) {
		if (templateMap.containsKey("default_interface_consumer")
				|| templateMap.containsKey("default_interface_provider")
				|| templateMap.containsKey("default_interface_consumer1")
				|| templateMap.containsKey("default_interface_provider1")
				|| templateMap.containsKey("InSOPTemplate")
				|| templateMap.containsKey("OutSOPTemplate")) {
			return true;
		}
		return false;
	}
}
