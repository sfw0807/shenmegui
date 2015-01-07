package com.dc.esb.servicegov.refactoring.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.SvcAsmRelateView;

@Repository
public class SvcAsmRelateViewDAOImpl extends
		HibernateDAO<SvcAsmRelateView, String> {

	private Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("unchecked")
	public List<SvcAsmRelateView> getInfosByConditions(
			Map<String, String> mapConditions) {
		if(log.isInfoEnabled()){
			log.info("get infos by different conditions");
		}
		String service = mapConditions.get("service");
		String operation = mapConditions.get("operation");
		String interfaceInfo = mapConditions.get("interfaceInfo");
		String consumeSys = mapConditions.get("consumeSys");
		String passbySys = mapConditions.get("passbySys");
		String provideSys = mapConditions.get("provideSys");
		String prdMsgType = mapConditions.get("prdMsgType");
		String csmMsgType = mapConditions.get("csmMsgType");
		String onlineDate = mapConditions.get("onlineDate");
		String state = mapConditions.get("state");
		String through  = mapConditions.get("through");
		String onlineVersion = mapConditions.get("onlineVersion");
		List<SvcAsmRelateView> list = null ;
		Criteria criteria = getSession().createCriteria(SvcAsmRelateView.class);  
		
		if(service != null && !"".equals(service)){
		    criteria.add(
				Restrictions.or(
						Restrictions.like("serviceId", "%"+ service + "%"), 
						Restrictions.like("serviceName", "%"+ service + "%"))
						);
		}
		if(operation != null && !"".equals(operation)){
			criteria.add(
				Restrictions.or(
						Restrictions.like("operationId", "%"+ operation + "%"),
						Restrictions.like("operationName", "%"+ operation + "%"))
						);
		}
		if(interfaceInfo != null && !"".equals(interfaceInfo)){
			criteria.add(
				Restrictions.or(
						Restrictions.like("interfaceId", "%"+ interfaceInfo + "%"),
						Restrictions.like("interfaceName", "%"+ interfaceInfo + "%"))
						);
		}
		if(consumeSys != null && !"".equals(consumeSys)){
			criteria.add(
				Restrictions.or(
						Restrictions.like("csmSysAB", "%"+ consumeSys + "%"),
						Restrictions.like("csmSysName", "%"+ consumeSys+ "%"))
						);
		}
		if(passbySys != null && !"".equals(passbySys)){
			criteria.add(
				Restrictions.or(
						Restrictions.like("passbySysAB", "%"+ passbySys + "%"),
						Restrictions.like("passbySysName", "%"+ passbySys + "%"))
						);
		}
		if(provideSys != null && !"".equals(provideSys)){
			criteria.add(
				Restrictions.or(
						Restrictions.like("prdSysAB", "%"+ provideSys + "%"),
						Restrictions.like("prdSysName", "%"+ provideSys + "%"))
						);
		}
		if(prdMsgType != null && !"".equals(prdMsgType)){
			criteria.add(
						Restrictions.like("provideMsgType", "%"+ prdMsgType + "%"));
		}
		if(csmMsgType != null && !"".equals(csmMsgType)){
			criteria.add(
						Restrictions.like("consumeMsgType", "%"+ csmMsgType + "%"));
		}
		if(onlineDate != null && !"".equals(onlineDate)){
			criteria.add(
						Restrictions.like("onlineDate", "%"+ onlineDate + "%"));
		}
		if(state != null && !"".equals(state)){
			criteria.add(
						Restrictions.like("state", "%"+ state + "%"));
		}
		if(through != null && !"".equals(through)){
			criteria.add(
						Restrictions.like("through", "%"+ through + "%"));
		}
		if(onlineVersion != null && !"".equals(onlineVersion)){
			criteria.add(
					Restrictions.like("onlineVersion", "%"+ onlineVersion + "%"));
	    }
		list = criteria.list();
		return list;
	}
	/**
	 * 去重复查询SvcAsmRelateView得到InvokeInfo
	 * @return
	 */
	public List<Map<String,Object>> getAllInvokeInfo(){
		String sql = "SELECT DISTINCT T.SERVICE_ID,T.OPERATION_ID,T.ECODE," +
				"T.CONSUME_MSG_TYPE,T.PROVIDE_MSG_TYPE,T.SERVICE_NAME," +
				"T.OPERATION_NAME,T.INTERFACE_NAME,T.PRDSYSAB," +
				"T.PRDSYSNAME,T.THROUGH,T.VERSIONST,T.ONLINE_DATE," +
				"T.ONLINE_VERSION,T.FIELD FROM SVC_ASM_RELATE_VIEW T";
		Query query = getSession().createSQLQuery(sql);
		return query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}
}
