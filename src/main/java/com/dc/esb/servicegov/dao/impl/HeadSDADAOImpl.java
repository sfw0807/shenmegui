package com.dc.esb.servicegov.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.entity.HeadSDA;

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
//		String sql = "select RESOURCEID from HEADSDA where SERVICEID='" + sHeadId +
//				"' and PARENTRESOURCEID='/'";
		String hql = "select id from HeadSDA where headId = ? and parentId = '/'";
		Query query = getSession().createQuery(hql);
		Object object = query.setString(0, sHeadId).uniqueResult();
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
//		String sql ="select STRUCTNAME from HEADSDA where RESOURCEID='" + resourceid + "'";
		String hql = "select structName from HeadSDA where id = ?";
		Query query = getSession().createQuery(hql);
		Object obj = query.setString(0, resourceid).uniqueResult();
		return obj.toString();
	}
	
	/**
	 * 根据resourceId获取HEADSDA的child ids list
	 * @param resourceId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getChildIdsByResourceId(String resourceId){
//		String sql = "select RESOURCEID from HEADSDA where PARENTRESOURCEID='" + resourceId + "' order by STRUCTINDEX";
		String hql = "select id from HeadSDA where parentId = ? order by structIndex";
		Query query = getSession().createQuery(hql);
		List list = query.setString(0, resourceId).list();
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
	
//	/**
//	 * 根据resourceid获取节点sda信息 Map形式返回
//	 * @param resourceid
//	 * @return
//	 */
//	public Map<String,String> getHeadSDAMapByResourceid(String resourceid){
//		Map<String,String> returnMap = new HashMap<String,String>();
//		String hql = "from HeadSDA where id = :resourceid";
//		Query query = getSession().createQuery(hql);
//		List list = query.setString("resourceid", resourceid).list();
//		HeadSDA headSda = new HeadSDA();
//		if(list != null && list.size() > 0){
//			headSda = (HeadSDA)list.get(0);
//			returnMap.put("id", headSda.getId());
//			returnMap.put("parentId", headSda.getParentId());
//			returnMap.put("structId", headSda.getStructName());
//			returnMap.put("METADATAID", headSda.getMatadataId());
//			returnMap.put("TYPE", headSda.getType());
//			returnMap.put("REQUIRED", headSda.getRequired());
//			returnMap.put("REMARK", headSda.getRemark());
//		}
//		return returnMap;
//	}
//	
//	/**
//	 * 根据resourceId获取SDA的child ids list
//	 * @param resourceId
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public void getAllChildMap(String resourceid, List<Map<String,String>> listMap){
//		String hql = "select id from HeadSDA where parentId = ? order by structIndex";
//		Query query = getSession().createQuery(hql);
//		List list = query.setString(0, resourceid).list();
//		if(list != null && list.size() > 0){
//			for(Object obj: list){
//				String childId = (String)obj;
//				listMap.add(this.getHeadSDAMapByResourceid(childId));
//				getAllChildMap(childId, listMap);
//			}
//		}
//	}
//	
//	/**
//	 * 获取所有SDA的Map数组
//	 * @return
//	 */
//	public List<Map<String,String>> getAllHeadSDAMapByTopResourceId(String resourceid){
//		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
//		mapList.add(this.getHeadSDAMapByResourceid(resourceid));
//		getAllChildMap(resourceid, mapList);
//		return mapList;
//	}
	
	public List<Map<String, String>> getAllHeadSDAMapBySheadId(
			String headId) {
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		String hql = "from HeadSDA where headId =? order by structIndex";
		Query query = getSession().createQuery(hql);
		query.setString(0, headId);
		List<HeadSDA> list = query.list();
		if(list != null && list.size() > 0){
			for(HeadSDA headSda: list){
				Map<String,String> tempMap = new HashMap<String,String>();
				tempMap.put("id", headSda.getId());
				tempMap.put("parentId", headSda.getParentId());
				tempMap.put("structId", headSda.getStructName());
				tempMap.put("METADATAID", headSda.getMatadataId());
				tempMap.put("TYPE", headSda.getType());
				tempMap.put("REQUIRED", headSda.getRequired());
				tempMap.put("REMARK", headSda.getRemark());
				mapList.add(tempMap);
			}
		}
		return mapList;
	}
}
