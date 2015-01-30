package com.dc.esb.servicegov.refactoring.dao.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.SLA;

@Repository
public class SLADAOImpl extends HibernateDAO<SLA, String> {

	private Log log = LogFactory.getLog(SLADAOImpl.class);

	/**
	 * delete SLA by serviceId and operationId
	 * 
	 * @param serviceId
	 * @param operationId
	 * @return
	 */
	public boolean delSlaByOperationIdAndServiceId(String serviceId,
			String operationId) {
		try {
			String hql = "delete from SLA where serviceId = ? and operationId = ?";
			Query query = getSession().createQuery(hql);
			query.setString(0, serviceId);
			query.setString(1, operationId);
			query.executeUpdate();
		} catch (Exception e) {
			log.error("根据服务ID和操作Id删除SLA数据失败", e);
			return false;
		}
		return true;
	}

	/**
	 * batch insert sla list
	 * 
	 * @param list
	 */
	public void batchInsertSla(List<SLA> list) {
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		log.info("插入的SLA记录数: " + list.size());
		try {
			for (SLA sla: list) {
				session.saveOrUpdate(sla);
			}
			tx.commit();
		} catch (Exception e) {
			log.equals("批量插入SLA数据失败!");
			tx.rollback();
		} finally {
			session.close();
		}
	}
	
	public boolean delSlaList(List<SLA> list) {
		try {
			for (SLA sla : list) {
				this.delSlaByOperationIdAndServiceId(sla.getServiceId(), sla
						.getOperationId());
			}
		} catch (Exception e) {
			log.error("删除sla数据失败!", e);
			return false;
		}
		return true;
	}
}
