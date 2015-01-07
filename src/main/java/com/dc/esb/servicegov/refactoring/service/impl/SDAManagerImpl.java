package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SDADAOImpl;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.SDA;
import com.dc.esb.servicegov.refactoring.service.OperationManager;
import com.dc.esb.servicegov.refactoring.service.SDAManager;
import com.dc.esb.servicegov.refactoring.util.ServiceStateUtils;

@Component
@Transactional(rollbackFor = Exception.class)
public class SDAManagerImpl implements SDAManager {	
	private Log log = LogFactory.getLog(getClass());
	@Autowired
	private SDADAOImpl sdaDao;
	@Autowired
	private OperationDAOImpl operationDao;
	@Autowired
	private OperationManagerImpl operationManager;
	public List<SDA> getAllSDAs() {
		return null;
	}
	public SDA getSDAById(String id) {
	
		return null;
	}
	public boolean deleteSDA(String operationId, String serviceId) {
		boolean isSuccess = false;
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("operationId", operationId);
		paramMap.put("serviceId", serviceId);
		List<SDA> sdaList = sdaDao.findBy(paramMap);
		if(sdaList.size()==0){
			isSuccess = true;
		}else{
			try{
				for(SDA sda:sdaList){
					sdaDao.delete(sda);	
				}
				isSuccess = true;
				//sdaDao.getSession().getTransaction().commit();
			}catch(Exception e){
				//sdaDao.getSession().getTransaction().rollback();
				log.error("error in delete sda of "+operationId, e);
			}			
		}
		return isSuccess;
	}
	public boolean saveSDA(List<SDA> sdaList) {
		boolean isSuccess = false;
		try{
			for(SDA sda:sdaList){
				sdaDao.save(sda);
			}
			//sdaDao.getSession().getTransaction().commit();
			isSuccess = true;
		}catch(Exception e){
			//sdaDao.getSession().getTransaction().rollback();
			log.error("error in save sda of sdalist", e);
		}
		return isSuccess;
	}
	public boolean saveSDA(SDA sda) {
		boolean isSuccess = false;
		try{
			sdaDao.save(sda);
			isSuccess = true;
		}catch(Exception e){
			log.error("error in save sda of sdalist", e);
		}
		return isSuccess;
	}
	public boolean updateOperationVersion(String operationId, String serviceId) {
		boolean isSuccess = false;
		try{
			Operation operation = operationDao.getOperation(operationId, serviceId);
			String state = operation.getState();
			if(ServiceStateUtils.DEFINITION.equals(state)){
				String version = operation.getVersion();
				String x1 = version.split("\\.")[0];
				String x2 = version.split("\\.")[1];
				String x3 = version.split("\\.")[2];
				x3 = (Integer.parseInt(x3)+1)+"";
				operation.setVersion(x1+"."+x2+"."+x3);
			}else{
				operationManager.saveOperationHistoryVersion(operationId, serviceId);
				String version = operation.getVersion();
				String x1 = version.split("\\.")[0];
				String x2 = version.split("\\.")[1];
				String x3 = version.split("\\.")[2];
				x3 = (Integer.parseInt(x3)+1)+"";
				operation.setVersion(x1+"."+x2+"."+x3);
				operation.setState(ServiceStateUtils.DEFINITION);
			}	
			isSuccess = true;
		}catch(Exception e){
			log.error(e, e);
		}
		return isSuccess;
	}
}
