package com.dc.esb.servicegov.refactoring.resource.metadataNode;

import java.util.ArrayList;
import java.util.List;

import com.dc.esb.servicegov.refactoring.resource.IPolicy;
import com.dc.esb.servicegov.refactoring.resource.IPolicyEngine;
import com.dc.esb.servicegov.refactoring.util.SDAConstants;

public class SDAMetadataNodeAttrEngine implements IPolicyEngine<IMetadataNode,IPolicy<String, IMetadataNode, IMetadataNode>> {
	
	private static SDAMetadataNodeAttrEngine sdaMetadataNodeAttrEngine;
	
	private SDAMetadataNodeAttrEngine() {
		
	}
	
	public static SDAMetadataNodeAttrEngine getInstance(){
		if(null == sdaMetadataNodeAttrEngine){
			sdaMetadataNodeAttrEngine = new SDAMetadataNodeAttrEngine();
		}
		return sdaMetadataNodeAttrEngine;
	}

	/**
	 * Metadata Node Attr Engine load Policy from SDA Metadata Root Node
	 * 
	 * @param root
	 */
	@Override
	public String loadPolicy(IMetadataNode root) {
		IMetadataNodeAttribute rootAttr = root.getProperty();
		return rootAttr.getProperty(SDAConstants.SDA_INCLUDE_ATTR);
	}

	@Override
	public List<IPolicy<String, IMetadataNode, IMetadataNode>> parsePolicy(String attrPolicyStr) throws Exception {
		List<IPolicy<String, IMetadataNode, IMetadataNode>> policies = null;
		if (null != attrPolicyStr) {
//			if (validatePolicyStr(attrPolicyStr)) {
				String attrPolicyContentsStr = attrPolicyStr.substring(
						attrPolicyStr.indexOf("{") + 1,
						attrPolicyStr.lastIndexOf("}"));
				if (!"".equals(attrPolicyContentsStr.trim())) {
					policies = new ArrayList<IPolicy<String, IMetadataNode, IMetadataNode>>();
					String[] attrPolicyContents = attrPolicyContentsStr
							.split(";");
					for (String attrPolicyContentStr : attrPolicyContents) {
						SDAMetadataNodeAttrPolicy sdaAttrPolicy = new SDAMetadataNodeAttrPolicy();
						sdaAttrPolicy.load(attrPolicyContentStr.trim());
						policies.add(sdaAttrPolicy);
					}
				}
//			}
		}
		return policies;
	}

	public void applyPolicies(IMetadataNode src, IMetadataNode target) throws Exception {
		List<IPolicy<String, IMetadataNode, IMetadataNode>> policies = parsePolicy(loadPolicy(src));
		if (null != policies) {
			for (IPolicy<String, IMetadataNode, IMetadataNode> policy : policies) {
				policy.applyPolicy(src, target);
			}
		}
	}

//	private boolean validatePolicyStr(String attrPolicyStr) {
//		boolean passed = false;
//		String policyReg = "{.*}";
//		Pattern policyPattern = Pattern.compile(policyReg);
//		Matcher policyMatcher = policyPattern.matcher(attrPolicyStr);
//		passed = policyMatcher.matches();
//		return passed;
//	}

}
