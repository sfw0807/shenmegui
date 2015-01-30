package com.dc.esb.servicegov.refactoring.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.IDA;
import com.dc.esb.servicegov.refactoring.entity.Interface;
import com.dc.esb.servicegov.refactoring.entity.SDA;

/**
 * 
 * @author G
 * 
 */
@Repository
@Transactional
public class IdaDAOImpl extends HibernateDAO<IDA, String> {

	private Log log = LogFactory.getLog(IdaDAOImpl.class);

	/**
	 * 插入node 更新相关seq
	 * 
	 * @param node
	 * @param parent
	 */
	@SuppressWarnings("unchecked")
	public void save_old(IDA node, IDA parent) {
		// Criteria c = createCriteria();
		// Criterion criterion = Restrictions.eq("parentId", parentId);
		// c.add(criterion);
		// c.addOrder(Order.asc("seq"));
		// List<IDA> l = c.list();
		// int seq = Integer.parseInt(l.get(l.size() - 1).getSeq());
		String parentId = parent == null ? "/" : parent.getId();
		if (parent == null) {
			// root
			node.setSeq(1);
		} else {
			Integer seq = (Integer) super.getSession().createSQLQuery(
					"select max(seq) from ida where" + " parent_id = " + "'"
							+ parentId + "'").uniqueResult();
			// if (seq == null) {
			// // 父节点没有其他子节点
			// seq = parent.getSeq();
			// }

			if ("request".equals(node.getStructName())) {
				seq = 1;
			} else if ("response".equals(node.getStructName())) {
				seq = 2;
			}

			node.setSeq(seq + 1);
			Query query = super.getSession()
					.createSQLQuery(
							"update ida set seq=seq+1 where " + "interface_id="
									+ "'" + node.getInterfaceId() + "'"
									+ " and seq > " + seq);
			query.executeUpdate();
		}
		super.save(node);
	}

	public void save(IDA node, IDA parent) {
		if (parent == null) {
			// root
			node.setSeq(1);
		} else {
			Integer seq = Integer.MAX_VALUE;
			if ("request".equals(node.getStructName())) {
				node.setSeq(2);
			} else if ("response".equals(node.getStructName())) {
				node.setSeq(3);
			}
			seq = node.getSeq();
			Query query = super.getSession().createSQLQuery(
					"update ida set seq=seq+1 where " + "interface_id=" + "'"
							+ node.getInterfaceId() + "'" + " and seq >= "
							+ seq);
			query.executeUpdate();
		}
		super.save(node);
	}

	@Override
	public void delete(IDA node) {

		Integer seq = node.getSeq();
		if ("request".equals(node.getStructName())) {
			seq = 2;
		} else if ("response".equals(node.getStructName())) {
			seq = 3;
		}

		Query query = super.getSession().createSQLQuery(
				"update ida set seq=seq-1 where " + "interface_id=" + "'"
						+ node.getInterfaceId() + "'" + " and seq >= " + seq);
		query.executeUpdate();
		super.save(node);
	}

	@Override
	public void delete(String id) {
		IDA node = super.get(id);
		int seq = node.getSeq();
		super.delete(node);
		Query query = super.getSession().createSQLQuery(
				"update ida set seq=seq-1 where " + "interface_id=" + "'"
						+ node.getInterfaceId() + "'" + " and seq > " + seq);
		query.executeUpdate();
	}

	/**
	 * 
	 * @param obj
	 */
	public void deleteInterfaceIDAs(Interface obj) {
		String interfaceId = obj.getInterfaceId();
		// List<IDA> allIDAs = super.findBy("interfaceId", interfaceId);
		// super.delete(allIDAs);
		Query query = super.getSession().createQuery(
				"delete from IDA where interfaceId= :interfaceId");
		query.setString("interfaceId", interfaceId);
		query.executeUpdate();
	}

	/**
	 * delete IDA by interfaceId
	 * 
	 * @param interfaceId
	 * @return
	 */
	public boolean delIDAByInterfaceId(String interfaceId) {
		try {
			Query query = super.getSession().createQuery(
					"delete from IDA where interfaceId= :interfaceId");
			query.setString("interfaceId", interfaceId);
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * batch insert idas
	 * 
	 * @param list
	 */
	public boolean batchInsertIDAs(List<IDA> list) {
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		try {
			for (IDA ida : list) {
				session.save(ida);
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			log.error("import IDAs error!", e);
			return false;
		}
		session.close();
		return true;
	}

	/**
	 * 获取IDA根节点的resourceid
	 * 
	 * @param interfaceId
	 * @return
	 */
	public String getTopResourceId(String interfaceId) {
//		String sql = "select id from ida where INTERFACE_ID='" + interfaceId
//				+ "' and PARENT_ID='/'";
		String hql = "select id from IDA where interfaceId = ? and parentId = '/'";
		Query query = getSession().createQuery(hql);
		Object obj = query.setString(0, interfaceId).uniqueResult();
		if (obj == null) {
			try {
				throw new Exception("IDA根节点的ID值为空，导出配置文件失败!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return (String) obj;
	}

	/**
	 * 根据resourceid获取IDA的structid、
	 * 
	 * @param resourceid
	 * @return
	 */
	public String getStructIdByResourceId(String resourceid) {
//		String sql = "select STRUCTNAME from ida where ID='" + resourceid + "'";
		String hql = "select structName from IDA where id = ?";
		Query query = getSession().createQuery(hql);
		Object obj = query.setString(0, resourceid).uniqueResult();
		return obj.toString();
	}

	/**
	 * 根据resourceId获取IDA的child ids list
	 * 
	 * @param resourceId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getChildIdsByResourceId(String resourceId) {
//		String sql = "select id from ida where PARENT_ID='" + resourceId
//				+ "' order by SEQ";
		String hql = "select id from IDA where parentId = ? order by seq";
		Query query = getSession().createQuery(hql);
		List list = query.setString(0, resourceId).list();
		if (list == null) {
			return null;
		} else {
			return list;
		}
	}

	/**
	 * 根据resourceid获取节点ida信息 Map形式返回
	 * 
	 * @param resourceid
	 * @return
	 */
	public Map<String, String> getIDAByResourceid(String resourceid) {
		Map<String, String> returnMap = new HashMap<String, String>();
		String hql = "from IDA where id = :resourceid";
		Query query = getSession().createQuery(hql);
		List list = query.setString("resourceid", resourceid).list();
		IDA ida = new IDA();
		if (list != null && list.size() > 0) {
			ida = (IDA) list.get(0);
			returnMap.put("METADATAID", ida.getMetadataId());
			if (ida.getType() != null && !"".equals(ida.getType())) {
				returnMap.put("TYPE", ida.getType());
			}
			if (ida.getLength() != null && !"".equals(ida.getLength())) {
				returnMap.put("LENGTH", ida.getLength());
			}
			if (ida.getScale() != null && !"".equals(ida.getScale())) {
				returnMap.put("SCALE", ida.getScale());
			}
			returnMap.put("REQUIRED", ida.getRequired());
			returnMap.put("REMARK", ida.getRemark());
		}
		return returnMap;
	}
	
//	/**
//	 * 根据resourceid获取节点ida信息 Map形式返回
//	 * @param resourceid
//	 * @return
//	 */
//	public Map<String,String> getIDAMapByResourceid(String resourceid){
//		Map<String,String> returnMap = new HashMap<String,String>();
//		String hql = "from IDA where id = :resourceid";
//		Query query = getSession().createQuery(hql);
//		List list = query.setString("resourceid", resourceid).list();
//		IDA ida = new IDA();
//		if(list != null && list.size() > 0){
//			ida = (IDA)list.get(0);
//			returnMap.put("id", ida.getId());
//			returnMap.put("parentId", ida.getParentId());
//			returnMap.put("structId", ida.getStructName());
//			returnMap.put("METADATAID", ida.getMetadataId());
//			if (ida.getType() != null && !"".equals(ida.getType())) {
//				returnMap.put("TYPE", ida.getType());
//			}
//			if (ida.getLength() != null && !"".equals(ida.getLength())) {
//				returnMap.put("LENGTH", ida.getLength());
//			}
//			if (ida.getScale() != null && !"".equals(ida.getScale())) {
//				returnMap.put("SCALE", ida.getScale());
//			}
//			returnMap.put("REQUIRED", ida.getRequired());
//			returnMap.put("REMARK", ida.getRemark());
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
//		String hql = "select id from IDA where parentId = ? order by seq";
//		Query query = getSession().createQuery(hql);
//		List list = query.setString(0, resourceid).list();
//		if(list != null && list.size() > 0){
//			for(Object obj: list){
//				String childId = (String)obj;
//				listMap.add(this.getIDAMapByResourceid(childId));
//				getAllChildMap(childId, listMap);
//			}
//		}
//	}
//	
//	/**
//	 * 获取所有SDA的Map数组
//	 * @return
//	 */
//	public List<Map<String,String>> getAllIDAMapByTopResourceId(String resourceid){
//		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
//		mapList.add(this.getIDAMapByResourceid(resourceid));
//		getAllChildMap(resourceid, mapList);
//		return mapList;
//	}
	
	public List<Map<String, String>> getAllIDAMapByInterfaceId(
			String interfaceId) {
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		String hql = "from IDA where interfaceId =? order by seq";
		Query query = getSession().createQuery(hql);
		query.setString(0, interfaceId);
		List<IDA> list = query.list();
		if(list != null && list.size() > 0){
			for(IDA ida: list){
				Map<String,String> tempMap = new HashMap<String,String>();
				tempMap.put("id", ida.getId());
				tempMap.put("parentId", ida.getParentId());
				tempMap.put("structId", ida.getStructName());
				tempMap.put("METADATAID", ida.getMetadataId());
				if (ida.getType() != null && !"".equals(ida.getType())) {
					tempMap.put("TYPE", ida.getType());
				}
				if (ida.getLength() != null && !"".equals(ida.getLength())) {
					tempMap.put("LENGTH", ida.getLength());
				}
				if (ida.getScale() != null && !"".equals(ida.getScale())) {
					tempMap.put("SCALE", ida.getScale());
				}
				tempMap.put("REQUIRED", ida.getRequired());
				tempMap.put("REMARK", ida.getRemark());
				mapList.add(tempMap);
			}
		}
		return mapList;
	}
}
