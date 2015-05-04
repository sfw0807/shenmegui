package com.dc.esb.servicegov.dao.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.entity.SDA;
import com.dc.esb.servicegov.entity.SDAHistory;

@Repository
public class SDAHistoryDAOImpl extends HibernateDAO<SDAHistory, String> {
	private Log log = LogFactory.getLog(getClass());
	public boolean saveHistoryVersion(List<SDA> sdaList){
		boolean isSuccess = false;
		try{
			for(SDA sda : sdaList){
				SDAHistory sdaHistory = new SDAHistory();
				sdaHistory.setId(sda.getId());
				sdaHistory.setStructId(sda.getStructId());
				sdaHistory.setMetadataId(sda.getMetadataId());
				sdaHistory.setSeq(sda.getSeq());
				sdaHistory.setType(sda.getType());
				sdaHistory.setRequired(sda.getRequired());
				sdaHistory.setRemark(sda.getRemark());
				sdaHistory.setOperationId(sda.getOperationId());
				sdaHistory.setOperationVersion(sda.getOperation().getVersion());
				sdaHistory.setServiceId(sda.getOperation().getServiceId());
				sdaHistory.setServiceVersion(sda.getOperation().getService().getVersion());
				sdaHistory.setParentId(sda.getParentId());
				sdaHistory.setModifyUser(sda.getModifyUser());
				sdaHistory.setUpdateTime(sda.getUpdateTime());
				this.save(sdaHistory);
			}	
			isSuccess = true;
		}catch(Exception e){
			log.error("error in saveHistoryVersion", e);
		}
		return isSuccess;
	}
	
	public void delByServiceIdAndOperationId(String serviceId, String operationId){
		String hql= "delete from SDAHistory where serviceId =? and operationId =?";
		Query query = getSession().createQuery(hql);
		query.setString(0, serviceId);
		query.setString(1, operationId);
		query.executeUpdate();
	}
}