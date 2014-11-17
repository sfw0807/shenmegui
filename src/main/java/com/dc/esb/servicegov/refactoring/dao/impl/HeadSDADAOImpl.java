package com.dc.esb.servicegov.refactoring.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.HeadSDA;
import com.dc.esb.servicegov.refactoring.entity.SDA;

/**
 * 
 * @author G
 * 
 */
@Repository
public class HeadSDADAOImpl extends HibernateDAO<HeadSDA, String> {
	
	private Log log = LogFactory.getLog(HeadSDADAOImpl.class);
	/**
	 * 根据sHeadId获取top resourceid
	 * @param sHeadId
	 * @return
	 */
	public String getTopResourceIdBySheadId(String sHeadId){
		String sql = "select RESOURCEID from HEADSDA where SERVICEID='" + sHeadId +
				"' and PARENTRESOURCEID='/'";
		Query query = getSession().createSQLQuery(sql);
		Object object = query.uniqueResult();
		if(object == null){
			log.error("找不到对应的业务报文头SDA!");
		}
		return object.toString();
	}
	
	/**
	 * 根据resourceid获取HEADSDA的structid、
	 * @param resourceid
	 * @return
	 */
	public String getStructIdByResourceId(String resourceid){
		String sql ="select STRUCTNAME from HEADSDA where RESOURCEID='" + resourceid + "'";
		Query query = getSession().createSQLQuery(sql);
		Object obj = query.uniqueResult();
		return obj.toString();
	}
	
	/**
	 * 根据resourceId获取HEADSDA的child ids list
	 * @param resourceId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getChildIdsByResourceId(String resourceId){
		String sql = "select RESOURCEID from HEADSDA where PARENTRESOURCEID='" + resourceId + "' order by STRUCTINDEX";
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
	 * 根据resourceid获取节点headsda信息 Map形式返回
	 * @param resourceid
	 * @return
	 */
	public Map<String,String> getHEADSDAByResourceid(String resourceid){
		Map<String,String> returnMap = new HashMap<String,String>();
		String hql = "from HeadSDA where id = :resourceid";
		Query query = getSession().createQuery(hql);
		List list = query.setString("resourceid", resourceid).list();
		HeadSDA headSda = new HeadSDA();
		if(list != null && list.size() > 0){
			headSda = (HeadSDA)list.get(0);
			returnMap.put("METADATAID", headSda.getMatadataId());
			returnMap.put("TYPE", headSda.getType());
			returnMap.put("REQUIRED", headSda.getRequired());
			returnMap.put("REMARK", headSda.getRemark());
		}
		return returnMap;
	}
}
