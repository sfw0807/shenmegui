package com.dc.esb.servicegov.refactoring.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.SDA;

@Repository
public class SDADAOImpl extends HibernateDAO<SDA, String>{

	private Log log = LogFactory.getLog(SDADAOImpl.class);
	
	/**
	 * 根据元数据ID查找不同的operationId和serviceId From SDA
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getSdaByMetadataId(String metadataId){
	      StringBuffer buffer = new StringBuffer();
	      buffer.append("select distinct OPERATION_ID,SERVICE_ID from SDA where METADATA_ID = :metadataId");
	      Query query = getSession().createSQLQuery(buffer.toString());
	      query.setString("metadataId", metadataId);
	      query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	      return query.list();
	}
	
	public boolean deleteSDAByServiceAndOperationId(String serviceId,String operationId){
		String hql = "delete from SDA where serviceId = :serviceId and operationId = :operationId";
		try{
		Query query = getSession().createQuery(hql);
		query.setString("serviceId", serviceId);
		query.setString("operationId", operationId);
		query.executeUpdate();
		}catch(Exception e){
			log.info("删除SDA数据失败！",e);
			return false;
		}
		return true;
	}
	
	/**
	 * batch insert SDAs
	 * @param list
	 */
	public void batchInsertSDAs(List<SDA> list){
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			for(SDA sda : list){
			    session.save(sda);
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			log.error("insert sda infos error!",e);
		}
		session.close();
	}
	
	/**
	 * 根据serviceid、operationid获取sda的根节点resourceId
	 * @param serviceId
	 * @return
	 */
	public String getTopResourceId(String serviceid,String operationid){
		String sql = "select id from sda where OPERATION_ID='" +
				operationid +
				"' and SERVICE_ID='" +
				serviceid +
				"' and PARENT_ID ='/'";
		Query query = getSession().createSQLQuery(sql);
		Object obj = query.uniqueResult();
		return obj.toString();
	}
	
	/**
	 * 根据resourceid获取SDA的structid、
	 * @param resourceid
	 * @return
	 */
	public String getStructIdByResourceId(String resourceid){
		String sql ="select STRUCT_ID from sda where ID='" + resourceid + "'";
		Query query = getSession().createSQLQuery(sql);
		Object obj = query.uniqueResult();
		return obj.toString();
	}
	
	/**
	 * 根据resourceId获取SDA的child ids list
	 * @param resourceId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getChildIdsByResourceId(String resourceId){
		String sql = "select id from sda where PARENT_ID='" + resourceId + "' order by SEQ";
		Query query = getSession().createSQLQuery(sql);
		List list = query.list();
		if(list == null){
			return null;
		}
		else{
			return list;
		}
	}
	
	/**
	 * 根据resourceid获取节点sda信息 Map形式返回
	 * @param resourceid
	 * @return
	 */
	public Map<String,String> getSDAByResourceid(String resourceid){
		Map<String,String> returnMap = new HashMap<String,String>();
		String hql = "from SDA where id = :resourceid";
		Query query = getSession().createQuery(hql);
		List list = query.setString("resourceid", resourceid).list();
		SDA sda = new SDA();
		if(list != null && list.size() > 0){
			sda = (SDA)list.get(0);
			returnMap.put("METADATAID", sda.getMetadataId());
			returnMap.put("TYPE", sda.getType());
			returnMap.put("REQUIRED", sda.getRequired());
			returnMap.put("REMARK", sda.getRemark());
		}
		return returnMap;
	}
	
	
}
