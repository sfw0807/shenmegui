package com.dc.esb.servicegov.resource;

public interface IFactory<T> {
	public T factory(String type);
	

}
