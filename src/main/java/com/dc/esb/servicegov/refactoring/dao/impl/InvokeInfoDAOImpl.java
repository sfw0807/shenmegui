package com.dc.esb.servicegov.refactoring.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.dao.support.SearchCondition;
import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.entity.InvokeInfoPK;

@Repository
public class InvokeInfoDAOImpl extends HibernateDAO<InvokeInfo, InvokeInfoPK> {

	private Log log = LogFactory.getLog(getClass());

	/**
	 * get count of services by provide system id
	 * 
	 * @return
	 */
	public long getCountOfServicesByPID(String provideSysId) {
		if (log.isInfoEnabled()) {
			log.info("get count of services provided by [" + provideSysId + "] from DB ...");
		}
		SearchCondition sc = new SearchCondition("provideSysId", provideSysId);
		return getCountOfServiceByCondition(sc);
	}
	
	/**
	 * get count of services by consume system id
	 * 
	 * @return
	 */
	public long getCountOfServicesByCID(String consumeSysId) {
		if (log.isInfoEnabled()) {
			log.info("get count of services consume by [" + consumeSysId + "] from DB ...");
		}
		SearchCondition sc = new SearchCondition("consumeSysId", consumeSysId);
		return getCountOfServiceByCondition(sc);
	}
	
	/**
	 * get count of service by condition
	 * @param sc
	 * @return
	 */
	public long getCountOfServiceByCondition(SearchCondition sc){
		if (log.isInfoEnabled()) {
			log.info("get count of services using ["+sc+"] from DB ...");
		}
		long count = 0L;
		StringBuffer hqlStr = new StringBuffer("select count(distinct g.serviceId) from ");
		hqlStr.append(InvokeInfo.class.getName());
		hqlStr.append(" g where g.");
		hqlStr.append(sc.getField());
		hqlStr.append("=:fieldValue");
		try {
			Query query = getSession().createQuery(hqlStr.toString());
			query.setParameter("fieldValue", sc.getFieldValue());
			List countlist = query.list();
			if(countlist.size() > 0){
				count = (Long) countlist.get(0);
			}
		} catch (Exception e) {
			log.error("fail to get count of services using ["+sc+"] from DB ...", e);
		}
		return count;
	}
	
	/**
	 * get count of operations by provide system id
	 * 
	 * @return
	 */
	public long getCountOfOperationsByPID(String provideSysId) {
		if (log.isInfoEnabled()) {
			log.info("get count of services provided by [" + provideSysId + "] from DB ...");
		}
		SearchCondition sc = new SearchCondition("provideSysId", provideSysId);
		return getCountOfOperationByCondition(sc);
	}
	
	/**
	 * get count of services by consume system id
	 * 
	 * @return
	 */
	public long getCountOfOperationsByCID(String consumeSysId) {
		if (log.isInfoEnabled()) {
			log.info("get count of services consume by [" + consumeSysId + "] from DB ...");
		}
		SearchCondition sc = new SearchCondition("consumeSysId", consumeSysId);
		return getCountOfOperationByCondition(sc);
	}
	
	
	/**
	 * get count of service by condition
	 * @param sc
	 * @return
	 */
	public long getCountOfOperationByCondition(SearchCondition sc){
		if (log.isInfoEnabled()) {
			log.info("get count of operations using ["+sc+"] from DB ...");
		}
		long count = 0L;
		StringBuffer hqlStr = new StringBuffer("select count( distinct g.operationId) from ");
		hqlStr.append(InvokeInfo.class.getName());
		hqlStr.append(" g where g.");
		hqlStr.append(sc.getField());
		hqlStr.append("=:fieldValue");
		try {
			Query query = getSession().createQuery(hqlStr.toString());
			query.setParameter("fieldValue", sc.getFieldValue());
			List countlist = query.list();
			count = (Long) countlist.get(0);
		} catch (Exception e) {
			log.error("fail to get count of operations using ["+sc+"] from DB ...",e);
		}
		return count;
	}
}
