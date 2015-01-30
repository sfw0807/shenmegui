package com.dc.esb.servicegov.refactoring.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.OLA;
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
	
	/**
	 * 创建新事务导入serviceHeader数据
	 * 
	 * @param serviceHeader
	 */
	public void txSaveSvcHeader(ServiceHeadRelate serviceHeader) {
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(serviceHeader);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			session.close();
		}
	}
	
	public void delByServiceId(String serviceId){
		String hql= "delete from ServiceHeadRelate where serviceId =?";
		Query query = getSession().createQuery(hql);
		query.setString(0, serviceId);
		query.executeUpdate();
	}
}
