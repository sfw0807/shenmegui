package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.service.OperationService;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import com.dc.esb.servicegov.service.support.BaseService;
import com.dc.esb.servicegov.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OperationServiceImpl extends AbstractBaseService<Operation, String> implements OperationService{
	@Autowired
    private OperationDAOImpl operationDAOImpl;
	
	public List<Operation> getOperationByServiceId(String serviceId){
		String hql = " from Operation a where a.serviceId = ?";
		return operationDAOImpl.find(hql, serviceId);
	}
	//根据operationId获取operation
	public Operation getOperation(String serviceId,String operationId){
		Map<String, String> params = new HashMap<String, String>();
		params.put("serviceId", serviceId);
		params.put("operationId", operationId);
		return findUniqueBy(params);

	}

	//查看operationId是否重复
	public boolean uniqueValid(String serviceId,String operationId){
		Operation entity = getOperation(serviceId, operationId);
		if(entity != null){
    		return false;
    	}
    	return true;
	}

    @Override
	public void save(Operation entity){
        entity.setOptDate(DateUtils.format(new Date()));
        super.save(entity);
	 }

    @Override
    public HibernateDAO getDAO() {
        return operationDAOImpl;
    }
}
