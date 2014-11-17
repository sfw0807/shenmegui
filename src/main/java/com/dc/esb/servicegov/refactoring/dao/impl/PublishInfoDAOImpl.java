package com.dc.esb.servicegov.refactoring.dao.impl;


import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.PublishInfo;
import com.dc.esb.servicegov.refactoring.vo.PublishInfoVO;

@Repository
public class PublishInfoDAOImpl extends
		HibernateDAO<PublishInfo, Integer> {

	private Log log = LogFactory.getLog(getClass());
	
	
	/**
	 * get all operations and services  from PublishInfo
	 */
	@SuppressWarnings("unchecked")
	public List<PublishInfoVO> getAllPublishTotalInfos(Map<String,String> mapConditions){
		
		if(log.isInfoEnabled()){
			log.info("get all operations and services  from PublishInfo");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("select ONLINE_DATE as ONLINEDATE ,count(distinct service_id) as countOfServices, count(distinct operation_id) as countOfOperations ");
		buffer.append("FROM ");
		buffer.append("(select p.ONLINE_DATE ,ir.service_id,ir.operation_id from PUBLISH_INFO p join ");
		buffer.append("INVOKE_RELATION ir on p.IR_ID = ir.ID ");
		if(mapConditions.get("prdMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("prdMsgType"))){
			buffer.append(" where ir.provide_msg_type = '");
			buffer.append(mapConditions.get("prdMsgType"));
			buffer.append("'");
		}
		if(mapConditions.get("csmMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("csmMsgType"))){
			buffer.append(" and ir.consume_msg_type = '");
			buffer.append(mapConditions.get("csmMsgType"));
			buffer.append("' ");
		}
		if(mapConditions.get("onlineDate") != null && !"".equalsIgnoreCase(mapConditions.get("onlineDate"))){
			buffer.append(" and p.ONLINE_DATE like '%");
			buffer.append(mapConditions.get("onlineDate"));
			buffer.append("%' ");
		}
		buffer.append("group by p.ONLINE_DATE,ir.service_id,ir.operation_id) t ");
		buffer.append("group by ONLINE_DATE");
		Query query = getSession().createSQLQuery(buffer.toString());
		query.setResultTransformer(Transformers.aliasToBean(PublishInfoVO.class));
		return query.list();
	}
	
	/**
	 * get all AddServicesCount 
	 */
	@SuppressWarnings("unchecked")
	public Integer getCountOfAddServices(String onlinedate, Map<String,String> mapConditions){
		
		if(log.isInfoEnabled()){
			log.info("get all AddServicesCount ");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(distinct ir.SERVICE_ID) from PUBLISH_INFO p left join INVOKE_RELATION ir on p.IR_ID = ir.ID ");
		buffer.append("where p.ONLINE_DATE= :onlineDate and p.IR_ID not in (select distinct ir_id from PUBLISH_INFO t where t.ONLINE_DATE < :onlineDate)");
		if(mapConditions.get("prdMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("prdMsgType"))){
			buffer.append(" and ir.provide_msg_type = '");
			buffer.append(mapConditions.get("prdMsgType"));
			buffer.append("'");
		}
		if(mapConditions.get("csmMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("csmMsgType"))){
			buffer.append(" and ir.consume_msg_type = '");
			buffer.append(mapConditions.get("csmMsgType"));
			buffer.append("'");
		}
		Query query = getSession().createSQLQuery(buffer.toString());
		Object object = query.setString("onlineDate", onlinedate).uniqueResult();
		return Integer.parseInt(object.toString());
	}
	
	/**
	 * get all ModifyServicesCount
	 */
	@SuppressWarnings("unchecked")
	public Integer getCountOfModifyServices(String onlinedate, Map<String,String> mapConditions){
		
		if(log.isInfoEnabled()){
			log.info("get all ModifyServicesCount");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(distinct ir.SERVICE_ID) from PUBLISH_INFO p left join INVOKE_RELATION ir on p.IR_ID = ir.ID ");
		buffer.append("where p.ONLINE_DATE= :onlineDate and p.IR_ID in (select distinct ir_id from PUBLISH_INFO t where t.ONLINE_DATE < :onlineDate)");
		if(mapConditions.get("prdMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("prdMsgType"))){
			buffer.append(" and ir.provide_msg_type = '");
			buffer.append(mapConditions.get("prdMsgType"));
			buffer.append("'");
		}
		if(mapConditions.get("csmMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("csmMsgType"))){
			buffer.append(" and ir.consume_msg_type = '");
			buffer.append(mapConditions.get("csmMsgType"));
			buffer.append("'");
		}
		Query query = getSession().createSQLQuery(buffer.toString());
		Object object = query.setString("onlineDate", onlinedate).uniqueResult();
		return Integer.parseInt(object.toString());
	}
	
	/**
	 * get all AddOperationsCount
	 */
	@SuppressWarnings("unchecked")
	public Integer getCountOfAddOperations(String onlinedate, Map<String,String> mapConditions){
		
		if(log.isInfoEnabled()){
			log.info("get all AddOperationsCount");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(distinct ir.OPERATION_ID) from PUBLISH_INFO p left join INVOKE_RELATION ir on p.IR_ID = ir.ID ");
		buffer.append("where p.ONLINE_DATE= :onlineDate and p.IR_ID not in (select distinct ir_id from PUBLISH_INFO t where t.ONLINE_DATE < :onlineDate)");
		if(mapConditions.get("prdMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("prdMsgType"))){
			buffer.append(" and ir.provide_msg_type = '");
			buffer.append(mapConditions.get("prdMsgType"));
			buffer.append("'");
		}
		if(mapConditions.get("csmMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("csmMsgType"))){
			buffer.append(" and ir.consume_msg_type = '");
			buffer.append(mapConditions.get("csmMsgType"));
			buffer.append("'");
		}
		Query query = getSession().createSQLQuery(buffer.toString());
		Object object = query.setString("onlineDate", onlinedate).uniqueResult();
		log.info("add operations :" +object.toString());
		return Integer.parseInt(object.toString());
	}
	
	/**
	 * get all ModifyOperationCount
	 */
	@SuppressWarnings("unchecked")
	public Integer getCountOfModifyOperations(String onlinedate, Map<String,String> mapConditions){
		
		if(log.isInfoEnabled()){
			log.info("get all ModifyOperationCount");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(distinct ir.OPERATION_ID) from PUBLISH_INFO p left join INVOKE_RELATION ir on p.IR_ID = ir.ID ");
		buffer.append("where p.ONLINE_DATE= :onlineDate and p.IR_ID in (select distinct ir_id from PUBLISH_INFO t where t.ONLINE_DATE < :onlineDate)");
		if(mapConditions.get("prdMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("prdMsgType"))){
			buffer.append(" and ir.provide_msg_type = '");
			buffer.append(mapConditions.get("prdMsgType"));
			buffer.append("'");
		}
		if(mapConditions.get("csmMsgType") != null && !"".equalsIgnoreCase(mapConditions.get("csmMsgType"))){
			buffer.append(" and ir.consume_msg_type = '");
			buffer.append(mapConditions.get("csmMsgType"));
			buffer.append("'");
		}
		Query query = getSession().createSQLQuery(buffer.toString());
		Object object = query.setString("onlineDate", onlinedate).uniqueResult();
		log.info("modify operations :" +object.toString());
		return Integer.parseInt(object.toString());
	}
	
	/**
	 * get All export details by onlineDate
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getAllExportDatasByOnlineDate(String onlineDate){
		
		if(log.isInfoEnabled()){
			log.info("get All export details by onlineDate");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("select t.*,o.operation_name,s.service_name, i.interface_name,i.through, ");
		buffer.append("prdsys.sys_ab as prdsysab,passbysys.sys_ab as passbysysab,csmsys.sys_ab as csmsysab, ");
		buffer.append("prdsys.sys_name as prdsysname,passbysys.sys_name as passbysysname,csmsys.sys_name as csmsysname ");
		buffer.append("FROM ");
		buffer.append("(select p.online_date,p.interface_version,p.field,ir.service_id,ir.operation_id,ir.provide_sys_id,ir.consume_sys_id,ir.passby_sys_id,ir.ecode ");
		buffer.append(",ir.consume_msg_type,ir.provide_msg_type ");
		buffer.append("from ");
		buffer.append("PUBLISH_INFO p left join INVOKE_RELATION ir on p.IR_ID = ir.ID ");
		buffer.append("where p.ONLINE_DATE = :onlineDate ) t ");
		buffer.append("left join operation o on t.operation_id  = o.operation_id ");
		buffer.append("left join service s on t.service_id = s.service_id ");
		buffer.append("left join interface i on t.ecode = i.interface_id ");
		buffer.append("left join system prdsys on t.provide_sys_id = prdsys.sys_id ");
		buffer.append("left join system passbysys on t.passby_sys_id = passbysys.sys_ab ");
		buffer.append("left join system csmsys on t.consume_sys_id = csmsys.sys_id ");
		buffer.append("order by online_date");
		Query query = getSession().createSQLQuery(buffer.toString());
		query.setString("onlineDate", onlineDate);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	public Integer getMaxId(){
		String hql = "SELECT MAX(id) FROM PublishInfo";
		Query query = getSession().createQuery(hql);
		Object obj = query.uniqueResult();
		return (Integer)obj;
	}	
}
