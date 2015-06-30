package com.dc.esb.servicegov.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.service.OperationService;
import com.dc.esb.servicegov.util.DateUtils;

@Service
@Transactional
public class OperationServiceImpl extends BaseServiceImpl<Operation> implements OperationService{
	@Autowired
    private OperationDAOImpl operationDAOImpl;
	
	public List<Operation> getOperationByServiceId(String serviceId){
		String hql = " from Operation a where a.serviceId = ?";
		return operationDAOImpl.find(hql, serviceId);
	}
	//根据operationId获取operation
	public Operation getOperationByOperationId(String operationId){
		return operationDAOImpl.findUnique(" from Operation where operationId = ? ", operationId);
	}
	//查看operationId是否重复
	public boolean uniqueValid(String operationId){
		Operation entity = getOperationByOperationId(operationId);
		if(entity != null){
    		return false;
    	}
    	return true;
	}
	
	public boolean addOperation(Operation entity){
		 try{
			 entity.setOptDate(DateUtils.format(new Date()));
			 operationDAOImpl.save(entity);
		 }catch(Exception e){
			 e.printStackTrace();
			 return false;
		 }
		 return true;
	 }
}
