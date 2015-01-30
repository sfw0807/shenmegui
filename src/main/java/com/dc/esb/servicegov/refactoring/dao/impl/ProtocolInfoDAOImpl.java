package com.dc.esb.servicegov.refactoring.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.ProtocolInfo;
import com.dc.esb.servicegov.refactoring.entity.ProtocolInfoPK;

@Repository
public class ProtocolInfoDAOImpl extends
		HibernateDAO<ProtocolInfo, ProtocolInfoPK> {

	private Log log = LogFactory.getLog(ProtocolInfoDAOImpl.class);

	/**
	 * get protocolinfos by sysid
	 * 
	 * @param sysId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProtocolInfo> getProtocolInfobyId(String sysId) {
		if (log.isInfoEnabled()) {
			log.info("get protocolInfo by sysId");
		}
		String hql = "from ProtocolInfo where sysId = :sysId";
		Query query = getSession().createQuery(hql);
		query.setString("sysId", sysId);
		return query.list();
	}

	/**
	 * delete by sysId
	 * 
	 * @param sysId
	 */
	public void delbySysId(String sysId) {
		String hql = "delete from ProtocolInfo where sysId = :sysId";
		Query query = getSession().createQuery(hql);
		query.setString("sysId", sysId);
		query.executeUpdate();
	}

	/**
	 * batch insert or update protocolInfos
	 * @param infoArr
	 */
	public void batchInsertOrUpdate(ProtocolInfo[] infoArr) {
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			for (ProtocolInfo pi : infoArr) {
				session.save(pi);
			}
			tx.commit();
		} catch (Exception e) {
			log.error("batch insert or update protocols infos error!", e);
			tx.rollback();
		} finally {
			session.close();
		}
	}
	
	/**
	 * 获取系统的协议类型
	 * @param sysId
	 * @return
	 */
	public String getConnectTypeBySysId(String sysId){
		   String connectType = "";
	       List<ProtocolInfo> list = this.findBy("sysId", sysId);
	       if(list != null && list.size() > 0){
	           for(ProtocolInfo pi :list){
	        	   if(pi.getConnectMode() != null)
	        	    if(!connectType.contains(pi.getConnectMode())){
	        		   connectType += pi.getConnectMode() + "、";
	        	    }
	              }
	       }
	       if(!"".equals(connectType)){
	    	   connectType = connectType.substring(0,connectType.length() -1);
	       }
	       return connectType;
	}
	
	/**
	 * 获取系统的报文类型
	 * @param sysId
	 * @return
	 */
	public String getMsgTypeBySysId(String sysId, int msgType){
		   String MsgType = "";
		   Map<String,String> params = new HashMap<String,String>();
		   params.put("sysId", sysId);
		   if(msgType == 1){
			   params.put("sysType", "提供方");
		   }
		   else{
			   params.put("sysType", "调用方");
		   }
	       List<ProtocolInfo> list = this.findBy(params);
	       if(list != null && list.size() > 0){
	           for(ProtocolInfo pi :list){
	        	   if(pi.getMsgType()!= null){
		        	   if(!MsgType.contains(pi.getMsgType())){
		        		   MsgType += pi.getMsgType() + "、";
		        	   }
	        	   }
	           }
	       }
	       if(!"".equals(MsgType)){
	    	   MsgType = MsgType.substring(0,MsgType.length() -1);
	       }
	       return MsgType;
	}
}
