package com.dc.esb.servicegov.refactoring.service;

import java.util.List;

import com.dc.esb.servicegov.refactoring.entity.OLA;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.SDA;
import com.dc.esb.servicegov.refactoring.entity.SLA;
import com.dc.esb.servicegov.refactoring.vo.OperationInfoVO;
import com.dc.esb.servicegov.refactoring.vo.SDAVO;

public interface OperationManager {
	
	public abstract List<OperationInfoVO> getAllOperations();	
	public abstract Operation getOperation(String operationId, String serviceId);
	public abstract SDAVO getSDAByOperation(String operationId, String serviceId);
	public abstract List<SDA> getSDAByOperationId(String operationId, String serviceId);
	public abstract List<SLA> getSLAByOperationId(String operationId, String serviceId);
	public abstract List<OLA> getOLAByOperationId(String operationId, String serviceId);
	public abstract boolean deleteOperation(Operation operation);
	public abstract boolean deleteOperation(String operationId, String serviceId);
	public abstract boolean saveOperation(Operation operation);
	public abstract boolean deployOperation(String operationId, String serviceId);
	public abstract boolean redefOperation(String operationId, String serviceId);
	public abstract boolean publishOperation(String operationId, String serviceId);
	public List<Operation> getAuditOperation();
	public boolean auditOperation(String operationId, String serviceId, String auditState);
	public boolean checkOperationPassed(String operationId, String serviceId);
	public boolean submitOperation(String operationId, String serviceId);
}
