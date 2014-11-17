package com.dc.esb.servicegov.refactoring.resource;

import java.util.List;

/**
 * 
 * @author Administrator
 *
 * @param <T>
 * @param <P>
 */

public interface IPolicyEngine<T, P> {
	public void applyPolicies( T source, T target) throws Exception;
	public String loadPolicy(T root) throws Exception ;
	public List<P> parsePolicy(String attrPolicyStr) throws Exception; 
}
