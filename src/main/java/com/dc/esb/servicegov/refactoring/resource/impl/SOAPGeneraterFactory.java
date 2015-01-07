package com.dc.esb.servicegov.refactoring.resource.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.resource.IConfigGenerater;
import com.dc.esb.servicegov.refactoring.resource.IFactory;

@Service
public class SOAPGeneraterFactory implements
		IFactory<IConfigGenerater<InvokeInfo, List<File>>> {
	
	@Autowired
	private EcifPackUnPackGenerater ecifPackUnPackGenerater;
	@Autowired
	private SpdbSpecDefaultInterfaceGenerater spdbSpecDefaultInterfaceGenerater;
	@Override
	public IConfigGenerater<InvokeInfo, List<File>> factory(String type) {
		if("ecif".equalsIgnoreCase(type)){
			return ecifPackUnPackGenerater;
		}else{
			spdbSpecDefaultInterfaceGenerater.exportBoth();
			return spdbSpecDefaultInterfaceGenerater;
		}
		
	}

}
