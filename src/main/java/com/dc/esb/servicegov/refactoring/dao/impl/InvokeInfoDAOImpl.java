package com.dc.esb.servicegov.refactoring.dao.impl;

import java.util.List;
import java.util.Map;

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
	public Integer getCountOfServicesByPID(String provideSysId,Map<String,String> mapConditions) {
		if (log.isInfoEnabled()) {
			log.info("get count of services provided by [" + provideSysId
					+ "] from DB ...");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(distinct SERVICE_ID) from INVOKE_RELATION" +
				" ir left join TRANS_STATE ts on ir.ID = ts.ID ");
		buffer.append("where PROVIDE_SYS_ID=:provideSysId");
		if(mapConditions.get("prdMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("prdMsgType"))){
			buffer.append(" and ir.provide_msg_type = '");
			buffer.append(mapConditions.get("prdMsgType"));
			buffer.append("'");
		}
		if(mapConditions.get("csmMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("csmMsgType"))){
			buffer.append(" and ir.consume_msg_type = '");
			buffer.append(mapConditions.get("csmMsgType"));
			buffer.append("' ");
		}
		if(mapConditions.get("versionSt") != null && !"".equalsIgnoreCase(mapConditions.get("versionSt"))){
			buffer.append(" and ts.VERSIONST ='");
			buffer.append(mapConditions.get("versionSt"));
			buffer.append("' ");
		}
		Query query = getSession().createSQLQuery(buffer.toString());
		query.setString("provideSysId", provideSysId);
		Object obj = query.uniqueResult();
		return (Integer)obj;
	}

	/**
	 * get count of services by consume system id
	 * 
	 * @return
	 */
	public Integer getCountOfServicesByCID(String consumeSysId,Map<String,String> mapConditions) {
		if (log.isInfoEnabled()) {
			log.info("get count of services provided by [" + consumeSysId
					+ "] from DB ...");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(distinct SERVICE_ID) from INVOKE_RELATION" +
				" ir left join TRANS_STATE ts on ir.ID = ts.ID ");
		buffer.append("where consume_SYS_ID=:consumeSysId");
		if(mapConditions.get("prdMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("prdMsgType"))){
			buffer.append(" and ir.provide_msg_type = '");
			buffer.append(mapConditions.get("prdMsgType"));
			buffer.append("'");
		}
		if(mapConditions.get("csmMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("csmMsgType"))){
			buffer.append(" and ir.consume_msg_type = '");
			buffer.append(mapConditions.get("csmMsgType"));
			buffer.append("' ");
		}
		if(mapConditions.get("versionSt") != null && !"".equalsIgnoreCase(mapConditions.get("versionSt"))){
			buffer.append(" and ts.VERSIONST ='");
			buffer.append(mapConditions.get("versionSt"));
			buffer.append("' ");
		}
		Query query = getSession().createSQLQuery(buffer.toString());
		query.setString("consumeSysId", consumeSysId);
		Object obj = query.uniqueResult();
		return (Integer)obj;
	}

	/**
	 * get count of service by condition
	 * 
	 * @param sc
	 * @return
	 */
	public long getCountOfServiceByCondition(SearchCondition sc) {
		if (log.isInfoEnabled()) {
			log.info("get count of services using [" + sc + "] from DB ...");
		}
		long count = 0L;
		StringBuffer hqlStr = new StringBuffer(
				"select count(g.serviceId) from ");
		hqlStr.append(InvokeInfo.class.getName());
		hqlStr.append(" g where g.");
		hqlStr.append(sc.getField());
		hqlStr.append("='");
		hqlStr.append(sc.getFieldValue());
		hqlStr.append("'");
		try {
			Query query = getSession().createQuery(hqlStr.toString());
			// query.setParameter("fieldValue", sc.getFieldValue());
			List countlist = query.list();
			if (countlist.size() > 0) {
				count = (Long) countlist.get(0);
			}
		} catch (Exception e) {
			log.error("fail to get count of services using [" + sc
					+ "] from DB ...", e);
		}
		return count;
	}

	/**
	 * get count of operations by provide system id
	 * 
	 * @return
	 */
	public Integer getCountOfOperationsByPID(String provideSysId,Map<String,String> mapConditions) {
		if (log.isInfoEnabled()) {
			log.info("get count of operations provided by [" + provideSysId
					+ "] from DB ...");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(distinct OPERATION_ID) from INVOKE_RELATION" +
				" ir left join TRANS_STATE ts on ir.ID = ts.ID ");
		buffer.append("where PROVIDE_SYS_ID=:provideSysId");
		if(mapConditions.get("prdMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("prdMsgType"))){
			buffer.append(" and ir.provide_msg_type = '");
			buffer.append(mapConditions.get("prdMsgType"));
			buffer.append("'");
		}
		if(mapConditions.get("csmMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("csmMsgType"))){
			buffer.append(" and ir.consume_msg_type = '");
			buffer.append(mapConditions.get("csmMsgType"));
			buffer.append("' ");
		}
		if(mapConditions.get("versionSt") != null && !"".equalsIgnoreCase(mapConditions.get("versionSt"))){
			buffer.append(" and ts.VERSIONST ='");
			buffer.append(mapConditions.get("versionSt"));
			buffer.append("' ");
		}
		Query query = getSession().createSQLQuery(buffer.toString());
		query.setString("provideSysId", provideSysId);
		Object obj = query.uniqueResult();
		return (Integer)obj;
	}

	/**
	 * get count of services by consume system id
	 * 
	 * @return
	 */
	public Integer getCountOfOperationsByCID(String consumeSysId,Map<String,String> mapConditions) {
		if (log.isInfoEnabled()) {
			log.info("get count of operations provided by [" + consumeSysId
					+ "] from DB ...");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(distinct OPERATION_ID) from INVOKE_RELATION" +
				" ir left join TRANS_STATE ts on ir.ID = ts.ID ");
		buffer.append("where CONSUME_SYS_ID=:consumeSysId");
		if(mapConditions.get("prdMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("prdMsgType"))){
			buffer.append(" and ir.provide_msg_type = '");
			buffer.append(mapConditions.get("prdMsgType"));
			buffer.append("'");
		}
		if(mapConditions.get("csmMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("csmMsgType"))){
			buffer.append(" and ir.consume_msg_type = '");
			buffer.append(mapConditions.get("csmMsgType"));
			buffer.append("' ");
		}
		if(mapConditions.get("versionSt") != null && !"".equalsIgnoreCase(mapConditions.get("versionSt"))){
			buffer.append(" and ts.VERSIONST ='");
			buffer.append(mapConditions.get("versionSt"));
			buffer.append("' ");
		}
		Query query = getSession().createSQLQuery(buffer.toString());
		query.setString("consumeSysId", consumeSysId);
		Object obj = query.uniqueResult();
		return (Integer)obj;
	}

	/**
	 * get count of service by condition
	 * 
	 * @param sc
	 * @return
	 */
	public long getCountOfOperationByCondition(SearchCondition sc) {
		if (log.isInfoEnabled()) {
			log.info("get count of operations using [" + sc + "] from DB ...");
		}
		long count = 0L;
		StringBuffer hqlStr = new StringBuffer(
				"select count( distinct g.operationId) from ");
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
			log.error("fail to get count of operations using [" + sc
					+ "] from DB ...", e);
		}
		return count;
	}

	/**
	 * 根据operationId和serviceId获取提供方系统ID
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<String> getPrdSysIdByOidAndSid(String serviceId,
			String operationId) {

		StringBuffer buffer = new StringBuffer();
		buffer
				.append("select distinct PROVIDE_SYS_ID from INVOKE_RELATION where SERVICE_ID= :serviceId and OPERATION_ID= :operationId");
		Query query = getSession().createSQLQuery(buffer.toString());
		query.setString("serviceId", serviceId);
		query.setString("operationId", operationId);
		return query.list();
	}

	/**
	 * check invoke_relation is or not exists
	 * 
	 * @param conditions
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public InvokeInfo checkIsExist(Map<String, String> conditions) {
		List list = this.findBy(conditions);
		if (list != null && list.size() > 0) {
			log.info("exists count of invoke_infos find by conditions: " + list.size());
			return (InvokeInfo)list.get(0);
		}
		return null;

	}
    
	/**
	 * get max(id)
	 * @return
	 */
	public Integer getMaxId(){
		String hql = "SELECT MAX(id) FROM InvokeInfo";
		Query query = getSession().createQuery(hql);
		Object obj = query.uniqueResult();
		if(obj == null){
			return 0;
		}
		return (Integer)obj;
	}

	/**
	 * 根据ecode获取第一条记录信息
	 * @param ecode
	 * @return
	 */
	public InvokeInfo getInvokeInfoByEcode(String ecode){
		InvokeInfo invokeInfo = null;
		List<InvokeInfo> list = this.findBy("ecode", ecode);
		if(list != null && list.size() > 0){
			invokeInfo = list.get(0);
		}
		return invokeInfo;
	}
	/**
	 * 报文类型是SOP的记录
	 * @param ecode
	 * @param serviceId
	 * @param operationId
	 * @return
	 */
	public List getInvokeByEcodeAndSOP(String ecode,String serviceId,String operationId){
		String sql = "select * from INVOKE_RELATION where SERVICE_ID='" + serviceId+
				"' and OPERATION_ID='" + operationId+
				"' and ECODE='" + ecode+
				"' and CONSUME_MSG_TYPE = 'SOP'";
		Query query = getSession().createSQLQuery(sql);
		return query.list();
	}
}
