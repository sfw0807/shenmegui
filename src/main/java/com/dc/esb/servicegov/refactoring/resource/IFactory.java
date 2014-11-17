package com.dc.esb.servicegov.refactoring.resource;

public interface IFactory<T> {
	public T factory(String type);
	

}
