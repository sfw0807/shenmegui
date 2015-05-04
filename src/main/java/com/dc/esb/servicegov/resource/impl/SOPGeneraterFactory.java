package com.dc.esb.servicegov.resource.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.entity.InvokeInfo;
import com.dc.esb.servicegov.resource.IConfigGenerater;
import com.dc.esb.servicegov.resource.IFactory;

@Service
public class SOPGeneraterFactory implements
		IFactory<IConfigGenerater<InvokeInfo, List<File>>> {

    @Autowired
    private SOPPackerUnPackerConfigGenerater sopPackerUnPackerConfigGenerater;

	@Override
	public IConfigGenerater<InvokeInfo, List<File>> factory(String type) {
		return sopPackerUnPackerConfigGenerater;
	}

}
