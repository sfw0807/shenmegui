package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.SLADAOImpl;
import com.dc.esb.servicegov.refactoring.entity.SLA;
import com.dc.esb.servicegov.refactoring.service.SLAManager;

@Component
@Transactional(rollbackFor = Exception.class)
public class SLAManagerImpl implements SLAManager {	
	private Log log = LogFactory.getLog(getClass());
	@Autowired
	private SLADAOImpl slaDao;
	
	public boolean deleteSLA(String operationId, String serviceId) {
		boolean isSuccess = false;
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("operationId", operationId);
		paramMap.put("serviceId", serviceId);
		List<SLA> slaList = slaDao.findBy(paramMap);
		if(slaList.size()<=0){
			isSuccess = true;
		}else{
			try{
				for(SLA sda:slaList){
					slaDao.delete(sda);
				}
				//slaDao.getSession().getTransaction().commit();
				isSuccess = true;
			}catch(Exception e){
				log.error("error in delete sla of"+operationId,e);
				//slaDao.getSession().getTransaction().rollback();
			}
		}
		return isSuccess;
	}
	public boolean saveSLA(List<SLA> slaList) {
		boolean isSuccess = false;
		try{	
			for (SLA sla:slaList) {
				slaDao.save(sla);	
			}
			//slaDao.getSession().getTransaction().commit();
			isSuccess = true;
		}catch(Exception e){
			log.error("error in save sla",e);
			//slaDao.getSession().getTransaction().rollback();
		}
		return isSuccess;
	}
}
