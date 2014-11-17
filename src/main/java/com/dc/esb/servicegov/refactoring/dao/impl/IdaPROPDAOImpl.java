package com.dc.esb.servicegov.refactoring.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.IdaPROP;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.Attr;

@Repository
@Transactional
public class IdaPROPDAOImpl extends HibernateDAO<IdaPROP, String> {

	private Log log = LogFactory.getLog(IdaPROPDAOImpl.class);

	/**
	 * 根据idaId返回idaPROP的MAP结果
	 * 
	 * @param idaId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Attr> getIdaPropMapByIdaId(String idaId) {
		List<Attr> returnList = new ArrayList<Attr>();
		String hql = "from IdaPROP where idaId = :idaId";
		Query query = getSession().createQuery(hql);
		List list = query.setString("idaId", idaId).list();
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				IdaPROP idaP = (IdaPROP) obj;
				Attr attr = new Attr();
				attr.setResourceid(idaP.getId());
				attr.setStructId(idaP.getIdaId());
				attr.setPropertyName(idaP.getName());
				attr.setPropertyValue(idaP.getValue());
				if (null == idaP.getSeq() || "".equals(idaP.getSeq())) {
					attr.setPropertyIndex(1);
				} else {
					attr.setPropertyIndex(Integer.parseInt(idaP.getSeq()));
				}
				attr.setRemark(idaP.getRemark());
				returnList.add(attr);
			}
		}
		return returnList;
	} 

	/**
	 * 批量出入list
	 * 
	 * @param list
	 */
	public void batchIdaPropList(List<IdaPROP> list) {
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			for (IdaPROP idap : list) {
				session.save(idap);
			}
			tx.commit();
		} catch (Exception e) {
			log.error("批量插入IdaPROP属性信息出错！", e);
			tx.rollback();
		} 
	    session.close();
	}

	/**
	 * 根据接口ID删除
	 * 
	 * @param interfaceId
	 */
	public boolean delIdaPROPByInterfaceId(String interfaceId) {
		String sql = "delete from IDA_PROP where IDA_ID in (select id from IDA where INTERFACE_ID='"
				+ interfaceId + "')";
		try {
			Query query = getSession().createSQLQuery(sql);
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			log.error("删除IDA_PROP节点失败", e);
			return false;
		}
	}
}
