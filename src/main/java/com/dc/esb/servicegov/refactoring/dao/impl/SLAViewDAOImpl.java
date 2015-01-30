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
		String interfaceInfo = conditionsMap.get("interfaces");
		String provideSys = conditionsMap.get("provideSys");
		String successRate = conditionsMap.get("successRate");
		String timeOut = conditionsMap.get("timeOut");
		String currentCount = conditionsMap.get("currentCount");
		String averageTime = conditionsMap.get("averageTime");
		List<SLAView> returnList = new ArrayList<SLAView>();
		Criteria criteria = getSession().createCriteria(SLAView.class);
		if(null!=service && !"".equals(service)){
			criteria.add(Restrictions.or(
					Restrictions.like("serviceId", "%"+service.replaceAll(" ", "")+"%"), 
					Restrictions.like("serviceName", "%"+service.replaceAll(" ", "")+"%"))
					);
		}
		if(null!=operation && !"".equals(operation)){
			criteria.add(Restrictions.or(
					Restrictions.like("operationId", "%"+operation.replaceAll(" ", "")+"%"), 
					Restrictions.like("operationName", "%"+operation.replaceAll(" ", "")+"%"))
					);
		}
		if(null!=interfaceInfo && !"".equals(interfaceInfo)){
			criteria.add(Restrictions.or(
					Restrictions.like("interfaceId", "%"+interfaceInfo.replaceAll(" ", "")+"%"), 
					Restrictions.like("interfaceName", "%"+interfaceInfo.replaceAll(" ", "")+"%"))
					);
		}
		if(null!=provideSys && !"".equals(provideSys)){
			criteria.add(Restrictions.or(
					Restrictions.like("sysName", "%"+provideSys.replaceAll(" ", "")+"%"), 
					Restrictions.like("sysAB", "%"+provideSys.replaceAll(" ", "")+"%"))
					);
		}
		if(null!=successRate && !"".equals(successRate)){
			criteria.add(
					Restrictions.like("successRate", "%"+successRate.replaceAll(" ", "")+"%")
					);
		}
		if(null!=timeOut && !"".equals(timeOut)){
			criteria.add(
					Restrictions.like("timeOut", "%"+timeOut.replaceAll(" ", "")+"%")
					);
		}
		if(null!=currentCount && !"".equals(currentCount)){
			criteria.add(
					Restrictions.like("currentCount", "%"+currentCount.replaceAll(" ", "")+"%")
					);
		}
		if(null!=averageTime && !"".equals(averageTime)){
			criteria.add(
					Restrictions.like("averageTime", "%"+averageTime.replaceAll(" ", "")+"%")
					);
		}
		returnList = criteria.list();
		return returnList;
	}
	
}
