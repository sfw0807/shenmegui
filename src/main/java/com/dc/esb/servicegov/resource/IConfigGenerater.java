package com.dc.esb.servicegov.resource;

public interface IConfigGenerater<I, O> {
	public O generate(I in) throws Exception;
}
