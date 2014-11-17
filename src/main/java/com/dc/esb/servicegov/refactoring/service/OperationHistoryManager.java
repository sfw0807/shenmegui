package com.dc.esb.servicegov.refactoring.service;

import java.util.List;

import com.dc.esb.servicegov.refactoring.entity.OLAHistory;
import com.dc.esb.servicegov.refactoring.entity.OperationHistory;
import com.dc.esb.servicegov.refactoring.entity.SLAHistory;
import com.dc.esb.servicegov.refactoring.vo.SDAHistoryVO;

public interface OperationHistoryManager {
	public abstract List<OperationHistory> getAllHistoryOperation(String operationId,String serviceId);	
	public abstract List<OperationHistory> getOperation(String operationId,String operationVersion,String serviceId);	
	public abstract SDAHistoryVO getSDA(String operationId,String operationVersion,String serviceId);	
	public abstract List<SLAHistory> getSLA(String operationId,String operationVersion,String serviceId);	
	public abstract List<OLAHistory> getOLA(String operationId,String operationVersion,String serviceId);	
	public abstract boolean backOperation(String operationId,String operationVersion,String serviceId);
}
