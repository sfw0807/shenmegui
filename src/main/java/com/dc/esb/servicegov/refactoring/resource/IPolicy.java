package com.dc.esb.servicegov.refactoring.resource;

/**
 * R for raw, S for source, T for target
 * read policy from raw content usually a string, then apply the policy to source, after all return as target;
 * @author Administrator
 *
 * @param <R>
 * @param <S>
 * @param <T>
 */
public interface IPolicy<R,S,T> {
	
	public void load(R r) throws Exception;
	
	public void applyPolicy(S source, T target) throws Exception;

}
