package com.dc.esb.servicegov.resource.impl;

import com.dc.esb.servicegov.entity.InvokeInfo;
import com.dc.esb.servicegov.resource.IConfigGenerater;
import com.dc.esb.servicegov.resource.IFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2015/5/5.
 */
@Component
public class NB8583GeneraterFactory implements
        IFactory<IConfigGenerater<InvokeInfo, List<File>>> {
    @Autowired
    private NB8583ConfigGenerator nb8583ConfigGenerator;

    @Override
    public IConfigGenerater<InvokeInfo, List<File>> factory(String type) {
        return nb8583ConfigGenerator;
    }
}
