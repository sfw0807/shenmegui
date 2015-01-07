package com.dc.esb.servicegov.refactoring.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.MetadataStructsAttr;
import com.dc.esb.servicegov.refactoring.entity.MetadataStructsAttrPK;

@Repository
public class MetadataStructsAttrDAOImpl extends
		HibernateDAO<MetadataStructsAttr, MetadataStructsAttrPK> {

	private Log log = LogFactory.getLog(MetadataStructsAttrDAOImpl.class);

	/**
	 * search MetadataStructsAttr by structId
	 * 
	 * @param structId
	 */
	@SuppressWarnings("unchecked")
	public List<MetadataStructsAttr> getMdtStructsAttrByStructId(String structId) {
		if (log.isInfoEnabled()) {
			log.info("search MetadataStructsAttr by [" + structId + "]");
		}
		String hql = "FROM MetadataStructsAttr WHERE structId = :structId";
		Query query = getSession().createQuery(hql);
		query.setString("structId", structId);
		return query.list();
	}

	/**
	 * delete MetadataStructsAttr by structId
	 * 
	 * @param structId
	 */
	public boolean delByStructId(String structId) {
		if (log.isInfoEnabled()) {
			log.info("delete MetadataStructsAttr by [" + structId + "]");
		}
		try {
			String hql = "DELETE FROM MetadataStructsAttr WHERE structId = :structId";
			Query query = getSession().createQuery(hql);
			query.setString("structId", structId);
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			log.error("delete metadataStructsAttr by structId error!",e);
			return false;
		}
	}

	/**
	 * batch delete MetadataStructsAttr list
	 * 
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public boolean batchDelete(MetadataStructsAttr[] msaArr) {
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			for (MetadataStructsAttr msa : msaArr) {
				session.delete(msa);
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			log.error("batch delete failed!", e);
			return false;
		}
		return true;
	}

	/**
	 * batch insert or update MetadataStructsAttr list
	 * 
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public boolean batchInsertOrUpdate(MetadataStructsAttr[] msaArr) {
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			int count = 0;
			for (MetadataStructsAttr msa : msaArr) {
				session.saveOrUpdate(msa);
				if (++count % 30 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			log.error("batch insert or update failed!", e);
			return false;
		}
		session.close();
		return true;
	}
	
	/**
	 * batch insert or update MetadataStructsAttr list
	 * 
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public boolean batchInsOrUptByList(List<MetadataStructsAttr> list) {
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			int count = 0;
			for (MetadataStructsAttr msa : list) {
				session.saveOrUpdate(msa);
				if (++count % 30 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			log.error("batch insert or update failed!", e);
			return false;
		}
		session.close();
		return true;
	}
}
