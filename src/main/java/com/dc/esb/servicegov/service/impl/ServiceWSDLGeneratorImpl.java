package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.wsdl.WSDLGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-16
 * Time: 下午3:39
 */
@Component
@Qualifier("serviceWSDLGenerator")
public class ServiceWSDLGeneratorImpl implements WSDLGenerator<List<String>> {
    @Override
    public boolean generate(List<String> services) throws Exception {
        File workSpace = createWorkSpace();

        return false;

    }


    private File createWorkSpace() {
        StringBuilder sb = new StringBuilder();
        sb.append("wsdl_");
        long currentThreadId = Thread.currentThread().getId();
        sb.append(currentThreadId);
        long currentTime = System.currentTimeMillis();
        sb.append(currentThreadId);
        String workSpacePath = sb.toString();
        File workSpace = new File(workSpacePath);
        if (!workSpace.exists()) {
            workSpace.mkdirs();
        }
        return workSpace;
    }

}
