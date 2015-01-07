package com.dc.esb.servicegov.refactoring.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.Metadata;

@Repository
public class MetadataDAOImpl extends HibernateDAO<Metadata, String> {

	private Log log = LogFactory.getLog(MetadataDAOImpl.class);

	public void batchSaveMetadatas(List<Metadata> list) {
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			int count = 0;
			for (Metadata metadata : list) {
				session.saveOrUpdate(metadata);
				if (++count % 30 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			log.error("batch insert or update metadata failed!", e);
		}
		session.close();
	}

	/**
	 * 检查元数据是否存在
	 * 
	 * @param metadataId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean checkIsExist(String metadataId){
		List list = this.findBy("metadataId", metadataId);
		if(list != null && list.size()> 0){
			return true;
		}
		return false;
	}
}
