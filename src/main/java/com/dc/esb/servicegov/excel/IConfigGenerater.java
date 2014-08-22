package com.dc.esb.servicegov.excel;

public interface IConfigGenerater<I, O> {
	public O generate(I in) throws Exception;
	

}