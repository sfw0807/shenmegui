package com.dc.esb.servicegov.refactoring.dao.impl;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.PublishView;
import com.dc.esb.servicegov.refactoring.util.ServiceStateUtils;

@Repository
public class PublishViewDAOImpl extends HibernateDAO<PublishView, String> {

	/**
	 * 投产次数
	 * @param onlineDate
	 * @return
	 */
	public Integer countOfPublishTimes(String onlineDate){
		String hql = "select count(distinct onlineDate) from PublishView ";
		if(!"".equals(onlineDate)){
			hql += " where onlineDate <= ?";
		}
		Query query = getSession().createQuery(hql);
		if(!"".equals(onlineDate)){
			query.setString(0, onlineDate);
		}
		Object obj = query.uniqueResult();
		if(obj == null){
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}
	
	/**
	 * 投产提供方数量
	 * @param onlineDate
	 * @return
	 */
	public Integer countOfProviderSys(String onlineDate){
		String hql = "select count(distinct prdSysId) from PublishView where versionSt =?";
		if(!"".equals(onlineDate)){
			hql += " and onlineDate <= ?";
		}
		Query query = getSession().createQuery(hql);
		query.setString(0, ServiceStateUtils.PUBLISH);
		if(!"".equals(onlineDate)){
			query.setString(1, onlineDate);
		}
		Object obj = query.uniqueResult();
		if(obj == null){
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}
	
	/**
	 * 投产调用方数量
	 * @param onlineDate
	 * @return
	 */
	public Integer countOfConsumerSys(String onlineDate){
		String hql = "select count(distinct csmSysId) from PublishView  where versionSt =?";
		if(!"".equals(onlineDate)){
			hql += " and onlineDate <= ?";
		}
		Query query = getSession().createQuery(hql);
		query.setString(0, ServiceStateUtils.PUBLISH);
		if(!"".equals(onlineDate)){
			query.setString(1, onlineDate);
		}
		Object obj = query.uniqueResult();
		if(obj == null){
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}
	
	/**
	 * 投产服务数量
	 * @param onlineDate
	 * @return
	 */
	public Integer countOfService(String onlineDate){
		String hql = "select count(distinct serviceId) from PublishView  where versionSt =?";
		if(!"".equals(onlineDate)){
			hql += " and onlineDate <= ?";
		}
		Query query = getSession().createQuery(hql);
		query.setString(0, ServiceStateUtils.PUBLISH);
		if(!"".equals(onlineDate)){
			query.setString(1, onlineDate);
		}
		Object obj = query.uniqueResult();
		if(obj == null){
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}
	
	/**
	 * 投产操作数量
	 * @param onlineDate
	 * @return
	 */
	public Integer countOfOperation(String onlineDate){
		String sql = "select count(distinct concat(OPERATION_ID,SERVICE_ID)) from PUBLISH_VIEW where VERSIONST =?";
		if(!"".equals(onlineDate)){
			sql += " and ONLINE_DATE <= ?";
		}
		Query query = getSession().createSQLQuery(sql);
		query.setString(0, ServiceStateUtils.PUBLISH);
		if(!"".equals(onlineDate)){
			query.setString(1, onlineDate);
		}
		Object obj = query.uniqueResult();
		if(obj == null){
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}
	
	/**
	 * 服务下线数量
	 * @param onlineDate
	 * @return
	 */
	public Integer countOfOffLine(String onlineDate){
		String sql = "select count(distinct concat(ir.SERVICE_ID,ir.OPERATION_ID)) from INVOKE_RELATION ir left join " +
				"TRANS_STATE  ts on ir.ID = ts.ID where ts.VERSIONST =?";
		Query query = getSession().createSQLQuery(sql);
		query.setString(0, ServiceStateUtils.OFFLINE);
		Object obj = query.uniqueResult();
		if(obj == null){
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}
	
	/**
	 * 服务修订次数
	 * @param onlineDate
	 * @return
	 */
	public Integer countOfModifyTimes(String onlineDate){
		StringBuffer buffer = new StringBuffer();
		buffer.append("select sum(modifyTimes) from (");
		buffer.append("select ");
		buffer.append("(select (count(distinct online_date) - 1) from PUBLISH_VIEW v where v.ECODE = pv.ECODE and v.CONSUME_SYS_ID = pv.CONSUME_SYS_ID)  as modifyTimes ");
		buffer.append("from PUBLISH_VIEW pv where pv.versionst = '");
	    buffer.append(ServiceStateUtils.PUBLISH);
	    buffer.append("'");
		if(!"".equals(onlineDate)){
			buffer.append("and pv.ONLINE_DATE <= ?");
		}
		buffer.append(" group by ECODE,CONSUME_SYS_ID");
		buffer.append(")");
		Query query = getSession().createSQLQuery(buffer.toString());
		if(!"".equals(onlineDate)){
			query.setString(0, onlineDate);
		}
		Object obj = query.uniqueResult();
		if(obj == null){
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}
	
	/**
	 * 服务删除数量
	 * @param onlineDate
	 * @return
	 */
	public Integer countOfDeletedService(String onlineDate){
		// 暂时没有此类数量. 可能需要新建表存储
		return 0;
	}
}
