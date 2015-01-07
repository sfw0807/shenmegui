package com.dc.esb.servicegov.refactoring.resource.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.resource.IConfigGenerater;
import com.dc.esb.servicegov.refactoring.resource.IFactory;

@Service
public class FixGeneraterFactory implements IFactory<IConfigGenerater<InvokeInfo, List<File>>> {
	
	@Autowired
	private FixPackerUnPackerConfigGenerater fixPackerUnPackerConfigGenerater;
	
	@Override
	public IConfigGenerater<InvokeInfo, List<File>> factory(String type) {
		return fixPackerUnPackerConfigGenerater;
	}
}
