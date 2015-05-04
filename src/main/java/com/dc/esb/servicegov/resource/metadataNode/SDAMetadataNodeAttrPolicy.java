package com.dc.esb.servicegov.resource.metadataNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.dc.esb.servicegov.resource.IPolicy;

public class SDAMetadataNodeAttrPolicy implements
		IPolicy<String, IMetadataNode, IMetadataNode> {
	private String includeNodeName;
	private List<String> includeAttrNames;

	public String getIncludeNodeName() {
		return includeNodeName;
	}

	public void setIncludeNodeName(String includeNodeName) {
		this.includeNodeName = includeNodeName;
	}

	public List<String> getIncludeAttrName() {
		return includeAttrNames;
	}

	public void setIncludeAttrName(List<String> includeAttrName) {
		this.includeAttrNames = includeAttrName;
	}

	@Override
	public void applyPolicy(IMetadataNode src, IMetadataNode target) {
		if (null != src) {
			IMetadataNode targetNode = target.getNodeByPath(this.includeNodeName);
			IMetadataNode srcNode = src.getNodeByPath(this.includeNodeName);
			addMetadataAttribute(srcNode, targetNode);
		}
	}

	private void addMetadataAttribute(IMetadataNode src, IMetadataNode target) {
		Properties pro = src.getProperty().getProperty();
		IMetadataNodeAttribute targetNodeAttribute = new MetadataNodeAttribute();
		for (Object o : pro.keySet()) {
			if (this.includeAttrNames.contains((String) o)) {
				targetNodeAttribute.setProperty((String) o, (String) pro.get(o));
			}
		}
		target.setProperty(targetNodeAttribute);
		System.out.println(target);
		if(src.hasChild()){
			List<IMetadataNode> srcChildren = src.getChild();
			List<IMetadataNode> targetChildren = target.getChild();
			for(int i =0 ; i< srcChildren.size(); i++){
				addMetadataAttribute(srcChildren.get(i), targetChildren.get(i));
			}
		}
	}

	@Override
	public void load(String attrPolicyStr) throws Exception {
		// TODO Auto-generated method stub
		if (attrPolicyStr.indexOf(":") > 0) {
			setIncludeNodeName(attrPolicyStr.substring(0,
					attrPolicyStr.indexOf(":")));

			String rawPolicyBody = attrPolicyStr.substring(
					attrPolicyStr.indexOf(":") + 1, attrPolicyStr.length());
			if (rawPolicyBody.startsWith("[") && rawPolicyBody.endsWith("]")) {
				setIncludeAttrName(parseBodyAsArray(rawPolicyBody));
			} else {
				setIncludeAttrName(parseBodyAsSingle(rawPolicyBody));
			}
		}
	}

	private List<String> parseBodyAsArray(String rawBody) {
		String rawBodyContent = rawBody.substring(rawBody.indexOf("[") + 1,
				rawBody.indexOf("]"));
		String[] bodyElements = null;
		if (!"".equals(rawBodyContent.trim())) {
			bodyElements = rawBodyContent.split(",");
		}
		return Arrays.asList(bodyElements);
	}

	private List<String> parseBodyAsSingle(String rawBody) {
		List<String> attrNames = null;
		if (null != rawBody) {
			attrNames = new ArrayList<String>();
			attrNames.add(rawBody);
		}
		return attrNames;
	}

}
