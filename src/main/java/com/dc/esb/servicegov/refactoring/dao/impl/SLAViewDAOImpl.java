package com.dc.esb.servicegov.refactoring.dao.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.SLAView;

@Repository
public class SLAViewDAOImpl extends HibernateDAO<SLAView, String>{
	@SuppressWarnings("unchecked")
	public List<SLAView> getInfosByConditions(Map<String,String> conditionsMap){
		String service = conditionsMap.get("service");
		String operation = conditionsMap.get("operation");
		String interfaceInfo = conditionsMap.get("interface");
		String provideSys = conditionsMap.get("provideSys");
		List<SLAView> returnList = new ArrayList<SLAView>();
		Criteria criteria = getSession().createCriteria(SLAView.class);
		if(null!=service && !"".equals(service)){
			criteria.add(Restrictions.or(
					Restrictions.like("serviceId", "%"+service+"%"), 
					Restrictions.like("serviceName", "%"+service+"%"))
					);
		}
		if(null!=operation && !"".equals(operation)){
			criteria.add(Restrictions.or(
					Restrictions.like("operationId", "%"+operation+"%"), 
					Restrictions.like("operationName", "%"+operation+"%"))
					);
		}
		if(null!=interfaceInfo && !"".equals(interfaceInfo)){
			criteria.add(Restrictions.or(
					Restrictions.like("interfaceId", "%"+interfaceInfo+"%"), 
					Restrictions.like("interfaceName", "%"+interfaceInfo+"%"))
					);
		}
		if(null!=provideSys && !"".equals(provideSys)){
			criteria.add(Restrictions.or(
					Restrictions.like("sysName", "%"+provideSys+"%"), 
					Restrictions.like("sysAB", "%"+provideSys+"%"))
					);
		}
		returnList = criteria.list();
		return returnList;
	}
	
}
