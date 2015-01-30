package com.dc.esb.servicegov.refactoring.dao.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.OLA;
import com.dc.esb.servicegov.refactoring.entity.OLAHistory;

@Repository
public class OLAHistoryDAOImpl extends HibernateDAO<OLAHistory, String> {
	private Log log = LogFactory.getLog(getClass());
	public boolean saveHistoryVersion(List<OLA> olaList){
		boolean isSuccess = false;
		try{
			for(OLA ola : olaList){
				OLAHistory olaHistory = new OLAHistory();
				olaHistory.setOperationId(ola.getOperationId());
				olaHistory.setOperationVersion(ola.getOperation().getVersion());
				olaHistory.setServiceId(ola.getServiceId());
				olaHistory.setServiceVersion(ola.getOperation().getService().getServiceId());
				olaHistory.setOlaName(ola.getOlaName());
				olaHistory.setOlaValue(ola.getOlaValue());
				olaHistory.setOlaRemark(ola.getOlaRemark());
				olaHistory.setModifyUser(ola.getModifyUser());
				olaHistory.setUpdateTime(ola.getUpdateTime());
				this.save(olaHistory);
			}	
			isSuccess = true;
		}catch(Exception e){
			log.error("error in saveHistoryVersion", e);
		}
		return isSuccess;
	}
	
	public void delByServiceIdAndOperationId(String serviceId, String operationId){
		String hql= "delete from OLAHistory where serviceId =? and operationId =?";
		Query query = getSession().createQuery(hql);
		query.setString(0, serviceId);
		query.setString(1, operationId);
		query.executeUpdate();
	}
}
