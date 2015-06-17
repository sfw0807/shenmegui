package com.dc.esb.servicegov.resource.impl;

import com.dc.esb.servicegov.entity.InvokeInfo;
import com.dc.esb.servicegov.resource.IConfigGenerater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * Created by vincentfxz on 15/5/17.
 */
@Component(value = "StandardXmlConfigGenerator")
public class StandXmlConfigGenerator implements IConfigGenerater<InvokeInfo, List<File>> {

    @Autowired
    private XMLStandardConfigGenerator xmlStandardConfigGenerator;

    @Override
    public List<File> generate(InvokeInfo in) throws Exception {
        String direction = in.getDirection();
        if("0".equalsIgnoreCase(direction)){
            in.setDirection("1");
        }else{
            in.setDirection("0");
        }
        return xmlStandardConfigGenerator.generate(in);
    }
}
