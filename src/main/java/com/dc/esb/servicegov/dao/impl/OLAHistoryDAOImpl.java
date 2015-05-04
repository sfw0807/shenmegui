package com.dc.esb.servicegov.dao.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.entity.OLA;
import com.dc.esb.servicegov.entity.OLAHistory;

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
				olaHistory.setUpdateTime(getNowTime());
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
