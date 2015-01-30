package com.dc.esb.servicegov.refactoring.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
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
		buffer.append("select count(distinct service_id) from RELATION_PASSED_VIEW");
		buffer.append(" where PROVIDE_SYS_ID=:provideSysId");
		if(mapConditions.get("prdMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("prdMsgType"))){
			buffer.append(" and provide_msg_type = '");
			buffer.append(mapConditions.get("prdMsgType"));
			buffer.append("'");
		}
		if(mapConditions.get("csmMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("csmMsgType"))){
			buffer.append(" and consume_msg_type = '");
			buffer.append(mapConditions.get("csmMsgType"));
			buffer.append("' ");
		}
		if(mapConditions.get("versionSt") != null && !"".equalsIgnoreCase(mapConditions.get("versionSt"))){
			buffer.append(" and VERSIONST ='");
			buffer.append(mapConditions.get("versionSt"));
			buffer.append("' ");
		}
		Query query = getSession().createSQLQuery(buffer.toString());
		query.setString("provideSysId", provideSysId);
		Object obj = query.uniqueResult();
        BigDecimal bi = (BigDecimal)obj;
        return Integer.parseInt(bi.toString());
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
		buffer.append("select count(distinct service_id)  from RELATION_PASSED_VIEW");
		buffer.append(" where consume_SYS_ID=:consumeSysId");
		if(mapConditions.get("prdMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("prdMsgType"))){
			buffer.append(" and provide_msg_type = '");
			buffer.append(mapConditions.get("prdMsgType"));
			buffer.append("'");
		}
		if(mapConditions.get("csmMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("csmMsgType"))){
			buffer.append(" and consume_msg_type = '");
			buffer.append(mapConditions.get("csmMsgType"));
			buffer.append("' ");
		}
		if(mapConditions.get("versionSt") != null && !"".equalsIgnoreCase(mapConditions.get("versionSt"))){
			buffer.append(" and VERSIONST ='");
			buffer.append(mapConditions.get("versionSt"));
			buffer.append("' ");
		}
		Query query = getSession().createSQLQuery(buffer.toString());
		query.setString("consumeSysId", consumeSysId);
		Object obj = query.uniqueResult();
        BigDecimal bi = (BigDecimal)obj;
        return Integer.parseInt(bi.toString());
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
		buffer.append("select count(distinct os)  from RELATION_PASSED_VIEW");
		buffer.append(" where PROVIDE_SYS_ID=:provideSysId");
		if(mapConditions.get("prdMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("prdMsgType"))){
			buffer.append(" and provide_msg_type = '");
			buffer.append(mapConditions.get("prdMsgType"));
			buffer.append("'");
		}
		if(mapConditions.get("csmMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("csmMsgType"))){
			buffer.append(" and consume_msg_type = '");
			buffer.append(mapConditions.get("csmMsgType"));
			buffer.append("' ");
		}
		if(mapConditions.get("versionSt") != null && !"".equalsIgnoreCase(mapConditions.get("versionSt"))){
			buffer.append(" and VERSIONST ='");
			buffer.append(mapConditions.get("versionSt"));
			buffer.append("' ");
		}
		Query query = getSession().createSQLQuery(buffer.toString());
		query.setString("provideSysId", provideSysId);
		Object obj = query.uniqueResult();
	    BigDecimal bi = (BigDecimal)obj;
        return Integer.parseInt(bi.toString());
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
		buffer.append("select count(distinct os) from RELATION_PASSED_VIEW");
		buffer.append(" where CONSUME_SYS_ID=:consumeSysId");
		if(mapConditions.get("prdMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("prdMsgType"))){
			buffer.append(" and provide_msg_type = '");
			buffer.append(mapConditions.get("prdMsgType"));
			buffer.append("'");
		}
		if(mapConditions.get("csmMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("csmMsgType"))){
			buffer.append(" and consume_msg_type = '");
			buffer.append(mapConditions.get("csmMsgType"));
			buffer.append("' ");
		}
		if(mapConditions.get("versionSt") != null && !"".equalsIgnoreCase(mapConditions.get("versionSt"))){
			buffer.append(" and VERSIONST ='");
			buffer.append(mapConditions.get("versionSt"));
			buffer.append("' ");
		}
		Query query = getSession().createSQLQuery(buffer.toString());
		query.setString("consumeSysId", consumeSysId);
		Object obj = query.uniqueResult();
        BigDecimal bi = (BigDecimal)obj;
        return Integer.parseInt(bi.toString());
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
	
	@SuppressWarnings("unchecked")
	public InvokeInfo getFirstByEocdeAndOpeAndServiceId(String serviceId, String operationId,
			String ecode) {
		List list = null;
		try{
			String hql = "from InvokeInfo where serviceId = ? and operationId = ? and ecode =? ";
			Query query = getSession().createQuery(hql);
			query.setString(0, serviceId);
			query.setString(1, operationId);
			query.setString(2, ecode);
			list = query.list();
		}catch(Exception e){
			log.error("InvokeInfo数据出错!");
		}
	    return (InvokeInfo)list.get(0);
	}
	
	public void delInvokebyConditions(String serviceId, String operationId,
			String interfaceId, String prdSysId, String csmSysId,
			String provideMsgType, String consumeMsgType) {
		String hql = "delete from InvokeInfo where serviceId = ? and operationId = ? and ecode = ? "
				+ "and provideSysId = ? and consumeSysId = ? and provideMsgType = ? and consumeMsgType = ?";
		Query query = getSession().createQuery(hql);
		query.setString(0, serviceId);
		query.setString(1, operationId);
		query.setString(2, interfaceId);
		query.setString(3, prdSysId);
		query.setString(4, csmSysId);
		query.setString(5, provideMsgType);
		query.setString(6, consumeMsgType);
		query.executeUpdate();
	}
	
	
	@SuppressWarnings("unchecked")
	public InvokeInfo getUniqueByConditions(String serviceId, String operationId,
			String interfaceId, String prdSysId, String csmSysId,
			String provideMsgType, String consumeMsgType){
		InvokeInfo invokeInfo = new InvokeInfo();
		String hql = "from InvokeInfo where serviceId = ? and operationId = ? and ecode = ? "
			+ "and provideSysId = ? and consumeSysId = ? and provideMsgType = ? and consumeMsgType = ?";
		Query query = getSession().createQuery(hql);
		query.setString(0, serviceId);
		query.setString(1, operationId);
		query.setString(2, interfaceId);
		query.setString(3, prdSysId);
		query.setString(4, csmSysId);
		query.setString(5, provideMsgType);
		query.setString(6, consumeMsgType);
		List list = query.list();
		if(list != null && list.size()>0){
			invokeInfo = (InvokeInfo)list.get(0);
		}
		return invokeInfo;
	}
	
	/**
	 * 根据serviceId判断 invokeInfo是否存在
	 * @param serviceId
	 * @return
	 */
	public boolean checkExistByServiceId(String serviceId){
		List<InvokeInfo> list = this.findBy("serviceId", serviceId);
		if(list != null && list.size() >0){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据interfaceId判断 invokeInfo是否存在
	 * @param interfaceId
	 * @return
	 */
	public boolean checkExistByInterfaceId(String interfaceId){
		List<InvokeInfo> list = this.findBy("ecode", interfaceId);
		if(list != null && list.size() >0){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据serviceId and operationId 判断invokeInfo是否存在
	 * @param operationId
	 * @param serviceId
	 * @return
	 */
	public  boolean checkExistByOperationIdAndServiceId(String operationId,String serviceId){
		Map<String,String> map = new HashMap<String,String>();
		map.put("operationId", operationId);
		map.put("serviceId", serviceId);
		List<InvokeInfo> list = this.findBy(map);
		if(list != null && list.size() >0){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据调用方系统Id获取系统调用报文类型
	 * @param csmSysId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getMsgTypeByCsmSysId(String csmSysId){
		String msgType = "";
		String hql = "select distinct consumeMsgType from InvokeInfo where consumeSysId = ?";
		Query query = getSession().createQuery(hql);
		query.setString(0, csmSysId);
		List<String> list = query.list();
		if(list != null && list.size()> 0){
			for(String ss: list){
				msgType += ss + "、";
			}
			msgType = msgType.substring(0,	msgType.length()-1);
		}
		return msgType;
	}
	
	/**
	 * 根据提供方系统Id获取系统提供报文类型
	 * @param prdSysId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getMsgTypeByPrdSysId(String prdSysId){
		String msgType = "";
		String hql = "select distinct provideMsgType from InvokeInfo where provideSysId = ?";
		Query query = getSession().createQuery(hql);
		query.setString(0, prdSysId);
		List<String> list = query.list();
		if(list != null && list.size()> 0){
			for(String ss: list){
				msgType += ss + "、";
			}
			msgType = msgType.substring(0,	msgType.length()-1);
		}
		return msgType;
	}
}
