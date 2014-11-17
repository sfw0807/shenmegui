package com.dc.esb.servicegov.refactoring.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.OLA;

@Repository
public class OLADAOImpl extends HibernateDAO<OLA, String> {

	private Log log = LogFactory.getLog(OLADAOImpl.class);

	/**
	 * 根据服务ID获取OLA wsdl_host数据
	 * 
	 * @param serviceId
	 * @return
	 */
	public List getWsdlHostByServiceId(String serviceId) {
		String sql = "select OLA_VALUE from OLA where SERVICE_ID='" + serviceId
				+ "'";
		Query query = getSession().createSQLQuery(sql);
		return query.list();
	}

	/**
	 * 根据服务ID和操作ID获取wsdlHost数据 OLA表中存在，则是wsdl_host服务
	 * 
	 * @param operationId
	 * @param serviceId
	 * @return
	 */
	public List getWsdlHostByOperationIdAndServiceId(String operationId,
			String serviceId) {
		String sql = "select OLA_VALUE from OLA where SERVICE_ID='" + serviceId
				+ "' and OPERATION_ID='" + operationId + "'";
		Query query = getSession().createSQLQuery(sql);
		return query.list();
	}

	/**
	 * 根据操作ID获取OLA wsdl_operation数据
	 * 
	 * @param operationId
	 * @return
	 */
	public List getWsdlOperationByServiceId(String operationId) {
		String sql = "select * from OLA where OLA_NAME='wsdl_operation' and OLA_VALUE='true' and OPERATION_ID='"
				+ operationId + "'";
		Query query = getSession().createSQLQuery(sql);
		return query.list();
	}

	/**
	 * 创建新事务导入ola数据
	 * 
	 * @param ola
	 */
	public void txSaveOla(OLA ola) {
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(ola);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			log.error("导入服务OLA数据出现错误!", e);
		} finally {
			session.close();
		}
	}

}
