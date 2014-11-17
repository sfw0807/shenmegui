package com.dc.esb.servicegov.refactoring.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.Interface;
import com.dc.esb.servicegov.refactoring.vo.InterfaceListVO;

@Repository
public class InterfaceDAOImpl extends HibernateDAO<Interface, String>{

	@SuppressWarnings("unchecked")
	public List<InterfaceListVO> getInterfaceManagementInfo_1() {
		String sql="  SELECT"
            			+ " r.ecode,i.interface_name,operation_id,s.sys_ab,p.interface_version,I.version,versionst,p.online_date,i.through"
            		+ " FROM" 
            			+ " invoke_relation r,trans_state t,interface i,publish_info p,system s"
            		+ " WHERE" 
            			+ " r.id = t.id and r.ecode = i.interface_id and p.ir_id = r.id and r.provide_sys_id = s.sys_id"
            		+ " GROUP BY" 
            		+ " r.ecode,i.interface_name,operation_id,s.sys_ab,p.interface_version,I.version,versionst,p.online_date,i.through";
		Query query = super.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(InterfaceListVO.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public InterfaceListVO getInterfaceListVO_1(String ecode) {
		String sql="  SELECT"
            			+ " r.ecode,i.interface_name,operation_id,s.sys_ab,p.interface_version,I.version,versionst,p.online_date,i.through"
            		+ " FROM" 
            			+ " invoke_relation r,trans_state t,interface i,publish_info p,system s"
            		+ " WHERE" 
            			+ " r.id = t.id and r.ecode = i.interface_id and p.ir_id = r.id and r.provide_sys_id = s.sys_id and r.ecode="+"'"+ecode+"'"
            		+ " GROUP BY" 
            		+ " r.ecode,i.interface_name,operation_id,s.sys_ab,p.interface_version,I.version,versionst,p.online_date,i.through";
		Query query = super.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(InterfaceListVO.class));
		List<InterfaceListVO> lst =  query.list();
		if (lst == null) {
			return null;
		} 
		return lst.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<InterfaceListVO> getInterfaceVOList() {
		String sql = "select ir.ECODE,i.INTERFACE_ID,i.INTERFACE_NAME,ir.SERVICE_ID,ir.OPERATION_ID,ir.DIRECTION,"
						+ " ir.CONSUME_MSG_TYPE,ir.PROVIDE_MSG_TYPE,o.OPERATION_NAME,i.REMARK,i.THROUGH,"
						+ " csmSys.SYS_AB as CONSUMER_SYSAB,csmSys.SYS_NAME  as CONSUMER_SYSNAME,"
						+ " prdSys.SYS_AB as PROVIDER_SYSAB,prdSys.SYS_NAME as PROVIDER_SYSNAME"
				 + " from" 
				    	+ " INVOKE_RELATION ir" 
						+ " left join OPERATION o on ir.OPERATION_ID = o. OPERATION_ID"
						+ " left join interface i on ir.ECODE=i.ECODE"
						+ " left join system prdSys on ir.PROVIDE_SYS_ID = prdSys.SYS_ID"
						+ " left join system csmSys on ir.CONSUME_SYS_ID = csmSys.SYS_ID";
		Query query = super.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(InterfaceListVO.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<InterfaceListVO> getInterfaceVOByEcode(String ecode) {
		String sql = "select ir.ECODE,i.INTERFACE_ID,i.INTERFACE_NAME,ir.SERVICE_ID,ir.OPERATION_ID," 
			+ " ir.CONSUME_MSG_TYPE,ir.PROVIDE_MSG_TYPE,o.OPERATION_NAME,i.REMARK,i.THROUGH,"
			+ " csmSys.SYS_AB as CONSUMER_SYSAB,csmSys.SYS_NAME  as CONSUMER_SYSNAME,csmSys.SYS_ID as CONSUMER_SYSID,"
			+ " prdSys.SYS_AB as PROVIDER_SYSAB,prdSys.SYS_NAME as PROVIDER_SYSNAME,prdSys.SYS_ID as PROVIDER_SYSID,"
			+ " psbSys.SYS_AB as PASSBY_SYSAB,psbSys.SYS_NAME as PASSBY_SYSNAME"
			+ " from" 
			+ " INVOKE_RELATION ir" 
			+ " left join OPERATION o on ir.OPERATION_ID = o. OPERATION_ID"
			+ " left join interface i on ir.ECODE=i.ECODE"
			+ " left join system prdSys on ir.PROVIDE_SYS_ID = prdSys.SYS_ID"
			+ " left join system psbSys on ir.PASSBY_SYS_ID = psbSys.SYS_ID"
			+ " left join system csmSys on ir.CONSUME_SYS_ID = csmSys.SYS_ID"
			+ " where ir.ecode=" + "'" + ecode + "'";
		Query query = super.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(InterfaceListVO.class));
		return query.list();
	}
	
	/**
	 * new transaction to save Interface
	 * @param Interface
	 */
	public void TxSaveInterface(Interface i){
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.saveOrUpdate(i);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}
		session.close();
	}
}
