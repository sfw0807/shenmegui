package com.dc.esb.servicegov.resource.impl;

import com.dc.esb.servicegov.entity.InvokeInfo;
import com.dc.esb.servicegov.resource.IConfigGenerater;
import com.dc.esb.servicegov.resource.IFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * Created by vincentfxz on 15/5/17.
 */

@Component
public class XMLGeneraterFactory implements
        IFactory<IConfigGenerater<InvokeInfo, List<File>>> {
    private static final Log log = LogFactory.getLog(XMLGeneraterFactory.class);

    @Autowired
    @Qualifier("ICSBSXmlConfigGenerator")
    private IConfigGenerater<InvokeInfo, List<File>> icbsXmlConfigGenerater;

    @Autowired
    @Qualifier("StandardXmlConfigGenerator")
    private IConfigGenerater<InvokeInfo, List<File>> standardXmlConfigGenerator;

    @Autowired
    @Qualifier("UDFSXmlConfigGenerator")
    private IConfigGenerater<InvokeInfo,List<File>> udfsXmlConfigGenerator;

    @Override
    public IConfigGenerater<InvokeInfo, List<File>> factory(String type) {
        if("ICBS".equals(type)){
            return icbsXmlConfigGenerater;
        }else if("UDFS".equals(type)){
            return udfsXmlConfigGenerator;
        }else{
            return standardXmlConfigGenerator;
        }
    }
}
