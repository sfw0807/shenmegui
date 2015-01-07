package com.dc.esb.servicegov.refactoring.resource.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.resource.IConfigGenerater;
import com.dc.esb.servicegov.refactoring.resource.IFactory;

@Service
public class InOutXmlConfigFactory implements IFactory<IFactory<IConfigGenerater<InvokeInfo, List<File>>>>{
	
	@Autowired
	private SOPGeneraterFactory sopGeneraterFactory;
	@Autowired
	private FixGeneraterFactory fixGeneraterFactory;
	@Autowired
	private SOAPGeneraterFactory soapGeneraterFactory;

	@Override
	public IFactory<IConfigGenerater<InvokeInfo, List<File>>> factory(
			String type) {
		if("sop".equalsIgnoreCase(type)){
			return sopGeneraterFactory;
		}else if("fix".equalsIgnoreCase(type)){
			return fixGeneraterFactory;
		}else{
			return soapGeneraterFactory;
		}
		
		
	}

	


}
