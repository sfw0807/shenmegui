package com.dc.esb.servicegov.refactoring.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.InterfaceExtends;

@Repository
public class InterfaceExtendsDAOImpl extends
		HibernateDAO<InterfaceExtends, String> {

	private Log log = LogFactory.getLog(InterfaceExtendsDAOImpl.class);

	/**
	 * 根据接口ID获取其superInterfaceid
	 * 
	 * @param interfaceId
	 */
	@SuppressWarnings("unchecked")
	public List<String> getSuperInterfaceIdsbyInterfaceid(String interfaceId) {
		List<String> returnList = new ArrayList<String>();
		String hql = "select superInterfaceId from InterfaceExtends where interfaceId = :interfaceId";
		Query query = getSession().createQuery(hql);
		List list = query.setString("interfaceId", interfaceId).list();
		if (list != null && list.size() > 0) {
			returnList = list;
		}
		return returnList;
	}

	/**
	 * new transaction to save interfaceExtends
	 * 
	 * @param Interface
	 */
	public void TxSaveInterfaceExtends(InterfaceExtends iExtends) {
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(iExtends);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}
		session.close();
	}

	/**
	 * 
	 * @param iExtends
	 * @return
	 */
	public boolean delByIExtends(InterfaceExtends iExtends) {
		String sql = "delete from INTERFACE_EXTENDS where INTERFACEID='"
				+ iExtends.getInterfaceId() + "' and SUPERINTERFACEID='"
				+ iExtends.getSuperInterfaceId() + "'";
		try {
			Query query = getSession().createSQLQuery(sql);
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			log.error("删除接口继承关系出错！", e);
			return false;
		}
	}
}
