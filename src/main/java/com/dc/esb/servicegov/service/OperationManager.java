package com.dc.esb.servicegov.service;

import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.OperationPK;
import com.dc.esb.servicegov.vo.SDA;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Vincent Fan
 * Date: 14-8-22
 * Time: 下午5:05
 */
public interface OperationManager {
    public List<Operation> getAllOperations();
    public List<Operation> getOperationsOfService(String serviceId);
    public Operation getOperationById(OperationPK operationPK);
    public SDA getOperationSDAById(OperationPK operationPK);
}
