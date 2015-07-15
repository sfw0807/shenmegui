package com.dc.esb.servicegov.service.impl;

import java.util.List;
import java.util.UUID;

import com.dc.esb.servicegov.dao.impl.ServiceInvokeDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.ServiceInvokeService;
import com.dc.esb.servicegov.service.support.AbstractBaseService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class ServiceInvokeServiceImpl extends AbstractBaseService<ServiceInvoke, String> implements ServiceInvokeService{

	@Autowired
	private ServiceInvokeDAOImpl serviceInvokeDAOImpl;

	@Override
	public HibernateDAO<ServiceInvoke, String> getDAO() {
		return serviceInvokeDAOImpl;
	}
	@Override
	public void save(ServiceInvoke entity){
		serviceInvokeDAOImpl.save(entity);
	}
	public List<ServiceInvoke> getBLInvoke(String baseId) {
        return serviceInvokeDAOImpl.getBLInvoke(baseId);
    }
	
	public ServiceInvoke getUniqueSI(String invokeId){
		return serviceInvokeDAOImpl.findUniqueBy("invokeId", invokeId);
	}
	//场景新建或者修改后关联的方法
	public void updateAfterOPAdd(String invokeId, String serviceId, String operationId, String type){
		ServiceInvoke si = serviceInvokeDAOImpl.findUniqueBy("invokeId", invokeId);
		if(StringUtils.isEmpty(si.getServiceId()) && StringUtils.isEmpty(si.getOperationId())){
			  si.setServiceId(serviceId);
              si.setOperationId(operationId);
              si.setType(type); 
              serviceInvokeDAOImpl.save(si);
              return;
		}
		//新建条件：serviceId或者operationId为不为空而且serviceId、operationId、type至少有一个不同
		if(!StringUtils.isEmpty(si.getServiceId()) || !StringUtils.isEmpty(si.getOperationId())){
			if(!serviceId.equals(si.getServiceId()) 
					|| !operationId.equals(si.getOperationId())
					|| !type.equals(si.getType())){
				ServiceInvoke entity = new ServiceInvoke();
				entity.setServiceId(serviceId);
				entity.setOperationId(operationId);
				entity.setType(type);
				entity.setSystemId(si.getSystemId());
				entity.setInterfaceId(si.getInterfaceId());
				
				serviceInvokeDAOImpl.save(entity);
				return;
			}
		}
		
	}
}
