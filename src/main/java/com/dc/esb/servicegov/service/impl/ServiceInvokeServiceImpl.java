package com.dc.esb.servicegov.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.dc.esb.servicegov.dao.impl.ServiceInvokeDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.ServiceInvokeService;
import com.dc.esb.servicegov.service.support.AbstractBaseService;

import com.dc.esb.servicegov.service.support.Constants;
import com.dc.esb.servicegov.vo.RelationVO;
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
	public List<?> getBLInvoke(String baseId) {
        return serviceInvokeDAOImpl.getBLInvoke(baseId);
    }
	
	public ServiceInvoke getUniqueSI(String invokeId){
		return serviceInvokeDAOImpl.findUniqueBy("invokeId", invokeId);
	}

	public void updateBySO(String serviceId, String operationId){
		serviceInvokeDAOImpl.updateBySO(serviceId, operationId);
	}
	//场景新建或者修改后关联的方法
	public void updateAfterOPAdd(String invokeId, String serviceId, String operationId, String type){
		ServiceInvoke si = serviceInvokeDAOImpl.findUniqueBy("invokeId", invokeId);
		if(si != null && StringUtils.isEmpty(si.getServiceId()) && StringUtils.isEmpty(si.getOperationId())){
			  si.setServiceId(serviceId);
              si.setOperationId(operationId);
              si.setType(type); 
              serviceInvokeDAOImpl.save(si);
              return;
		}
		//新建条件：serviceId或者operationId为不为空而且serviceId、operationId、type至少有一个不同
		if(si == null || !StringUtils.isEmpty(si.getServiceId()) || !StringUtils.isEmpty(si.getOperationId())){
			if(si == null || !serviceId.equals(si.getServiceId())
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
	public void updateProtocolId(String hql,String ...args ){
		serviceInvokeDAOImpl.exeHql(hql,args);

	}
	public List<?> findJsonBySO(String serviceId, String operationId){
		return serviceInvokeDAOImpl.findJsonBySO(serviceId, operationId);
	}

	public List getInvokerRelationByServiceId(String serviceId) {
		String hql = " select new com.dc.esb.servicegov.vo.RelationVO" +
				"(a.system.systemId, b.system.systemAb, a.system.systemAb, a.serviceId, a.operationId, a.interfaceId)"+
				" from ServiceInvoke as a, ServiceInvoke as b " +
				" where a.type=? and b.type=? and a.serviceId=b.serviceId and a.serviceId=? and a.operationId=b.operationId";

		List<?> list = super.find(hql, Constants.INVOKE_TYPE_PROVIDER, Constants.INVOKE_TYPE_CONSUMER, serviceId);

		return list;

	}

	public List<ServiceInvoke> getDistinctInter(String systemId){
		List<ServiceInvoke> list = this.findBy("systemId", systemId);
		List<ServiceInvoke> tempList = new ArrayList<ServiceInvoke>();

		for(int i = 0; i < list.size(); i++){
			ServiceInvoke si = list.get(i);
			if(si != null){
				for(int j = 0; j < list.size(); j++){
					ServiceInvoke sj = list.get(j);
					if(sj != null){
						if(si.getSystemId().equals(sj.getSystemId()) ){
							if(StringUtils.isNotEmpty(si.getInterfaceId())&& StringUtils.isNotEmpty(sj.getInterfaceId())&& si.getInterfaceId().equals(sj.getInterfaceId())){
								if(i != j){
									sj = null;
									list.set(j, sj);
									tempList.add(sj);
								}
							}
						}
					}
				}
			}

		}
		for(int i = 0; i < tempList.size(); i++){
			list.remove(tempList.get(i));
		}
		return list;
	}

}
