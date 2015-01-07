package com.dc.esb.servicegov.refactoring.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.ServiceHeadRelate;

/**
 * 
 * @author G
 * 
 */
@Repository
public class ServiceHeadRelateDAOImpl extends HibernateDAO<ServiceHeadRelate, String> {

	/**
	 * 根据服务Id获取业务报文头
	 * @param serviceId
	 * @return
	 */
	public String getHeadByServiceId(String serviceId){
		String hql = "select sheadId from ServiceHeadRelate where serviceId =:serviceId";
		Query query = getSession().createQuery(hql);
		Object obj = query.setString("serviceId", serviceId).uniqueResult();
		if(obj == null){
			return null;
		}
		return obj.toString();
	}
}
