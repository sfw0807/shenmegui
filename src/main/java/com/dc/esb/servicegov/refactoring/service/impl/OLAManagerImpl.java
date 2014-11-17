package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.OLADAOImpl;
import com.dc.esb.servicegov.refactoring.entity.OLA;
import com.dc.esb.servicegov.refactoring.entity.SLA;
import com.dc.esb.servicegov.refactoring.service.OLAManager;

@Component
@Transactional(rollbackFor = Exception.class)
public class OLAManagerImpl implements OLAManager {	
	private Log log = LogFactory.getLog(getClass());
	@Autowired
	private OLADAOImpl olaDao;
	
	public boolean deleteOLA(String operationId, String serviceId) {
		boolean isSuccess = false;
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("operationId", operationId);
		paramMap.put("serviceId", serviceId);
		List<OLA> olaList = olaDao.findBy(paramMap);
		if(olaList.size()==0){
			isSuccess = true;
		}else{
			try{
				for(OLA ola:olaList){
					olaDao.delete(ola);
				}
				//olaDao.getSession().getTransaction().commit();
				isSuccess = true;
			}catch(Exception e){
				log.error("error in delete ola of"+operationId,e);
				//olaDao.getSession().getTransaction().rollback();
			}			
		}
		return isSuccess;
	}
	public boolean saveOLA(List<OLA> olaList) {
		boolean isSuccess = false;
		try{
			for(OLA ola:olaList){
				olaDao.save(ola);
			}
			//olaDao.getSession().getTransaction().commit();
			isSuccess = true;
		}catch(Exception e){
			log.error("error in save ola",e);
			//olaDao.getSession().getTransaction().rollback();
		}
		return isSuccess;
	}
}
