package com.dc.esb.servicegov.resource.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.entity.InvokeInfo;
import com.dc.esb.servicegov.resource.IConfigGenerater;
import com.dc.esb.servicegov.resource.IFactory;

@Service
public class InOutXmlConfigFactory implements IFactory<IFactory<IConfigGenerater<InvokeInfo, List<File>>>> {
    @Autowired
    private SOPGeneraterFactory sopGeneraterFactory;
    @Autowired
    private FixGeneraterFactory fixGeneraterFactory;
    @Autowired
    private SOAPGeneraterFactory soapGeneraterFactory;
    @Autowired
    private NB8583GeneraterFactory nb8583GeneraterFactory;
    @Autowired
    private XMLGeneraterFactory xmlGeneraterFactory;

    @Override
    public IFactory<IConfigGenerater<InvokeInfo, List<File>>> factory(
            String type) {
        if ("sop".equalsIgnoreCase(type)) {
            return sopGeneraterFactory;
        } else if ("fix".equalsIgnoreCase(type)) {
            return fixGeneraterFactory;
        } else if ("8583".equalsIgnoreCase(type)) {
            return nb8583GeneraterFactory;
        } else if ("xml".equalsIgnoreCase(type)) {
            return xmlGeneraterFactory;
        } else {
            return soapGeneraterFactory;
        }
    }
}
