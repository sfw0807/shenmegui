package com.dc.esb.servicegov.refactoring.dao.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.SDA;
import com.dc.esb.servicegov.refactoring.entity.SDAHistory;
import com.dc.esb.servicegov.refactoring.entity.SLA;
import com.dc.esb.servicegov.refactoring.entity.SLAHistory;

@Repository
public class SLAHistoryDAOImpl extends HibernateDAO<SLAHistory, String> {
	private Log log = LogFactory.getLog(getClass());
	public boolean saveHistoryVersion(List<SLA> slaList){
		boolean isSuccess = false;
		try{
			for(SLA sla : slaList){
				SLAHistory slaHistory = new SLAHistory();
				slaHistory.setOperationId(sla.getOperationId());
				slaHistory.setOperationVersion(sla.getOperation().getVersion());
				slaHistory.setServiceId(sla.getServiceId());
				slaHistory.setServiceVersion(sla.getOperation().getVersion());
				slaHistory.setSlaName(sla.getSlaName());
				slaHistory.setSlaValue(sla.getSlaValue());
				slaHistory.setSlaRemark(sla.getSlaRemark());
				slaHistory.setModifyUser(sla.getModifyUser());
				slaHistory.setUpdateTime(sla.getUpdateTime());
				this.save(slaHistory);
			}	
			isSuccess = true;
		}catch(Exception e){
			log.error("error in saveHistoryVersion", e);
		}
		return isSuccess;
	}
}
