package com.dc.esb.servicegov.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.OperationHistory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


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
			operationHistory.setUpdateTime(getNowTime());
			this.save(operationHistory);	
			isSuccess = true;
		}catch(Exception e){
			log.error("error in save saveHistoryVersion", e);
		}
		return isSuccess;
	}
	
	public void delByServiceIdAndOperationId(String serviceId, String operationId){
		String hql= "delete from OperationHistory where serviceId =? and operationId =?";
		Query query = getSession().createQuery(hql);
		query.setString(0, serviceId);
		query.setString(1, operationId);
		query.executeUpdate();
	}

	/***
	 *
	 *获取当前时间
	 *
	 */
	public String getNowTime()
	{

		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updateTime=format.format(date);

		return updateTime;
	}


}
