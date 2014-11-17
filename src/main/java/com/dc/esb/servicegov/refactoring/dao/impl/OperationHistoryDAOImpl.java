package com.dc.esb.servicegov.refactoring.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.OperationHistory;


@Repository
public class OperationHistoryDAOImpl extends HibernateDAO<OperationHistory, String> {
	private Log log = LogFactory.getLog(getClass());
	/**
	 * 保留历史版本
	 * @param operationId
	 * @param serviceId
	 */
	public boolean saveHistoryVersion(Operation operation){
		boolean isSuccess = false;
		try{
			OperationHistory operationHistory = new OperationHistory();
			operationHistory.setOperationId(operation.getOperationId());
			operationHistory.setOperationName(operation.getOperationName());
			operationHistory.setOperationVersion(operation.getVersion());
			operationHistory.setOperationState(operation.getState());
			operationHistory.setServiceId(operation.getServiceId());
			operationHistory.setServiceVersion(operation.getService().getVersion());
			operationHistory.setRemark(operation.getRemark());
			operationHistory.setModifyUser(operation.getModifyUser());
			operationHistory.setUpdateTime(operation.getUpdateTime());
			this.save(operationHistory);	
			isSuccess = true;
		}catch(Exception e){
			log.error("error in save saveHistoryVersion", e);
		}
		return isSuccess;
	}
}
