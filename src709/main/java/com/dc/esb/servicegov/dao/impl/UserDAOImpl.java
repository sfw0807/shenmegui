package com.dc.esb.servicegov.dao.impl;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.SGUser;
import com.dc.esb.servicegov.entity.UserRoleRelation;

/**
 * Created by vincentfxz on 15/7/2.
 */
@Repository
@Transactional
public class UserDAOImpl extends HibernateDAO<SGUser, String> {
	private final static String ADD_HQL = "insert into SG_USER(USER_ID,USER_NAME,USER_MOBILE,USER_TEL,USER_PASSWORD,USER_ORG_ID,USER_STARTDATE,USER_LASTDATE,USER_REMARK) values (?,?,?,?,?,?,?,?,?)";
	private final static String ADD_HQL_RELATION = "insert into USER_ROLE_RELATION(USER_ID,ROLE_ID) values (?,?)";
	private final static String UPDATE_HQL = "update SG_USER set USER_NAME=?,USER_MOBILE=?,USER_TEL=?,USER_PASSWORD=?,USER_ORG_ID=?,USER_STARTDATE=?,USER_LASTDATE=?,USER_REMARK=?  where USER_ID=?";
	private final static String DEL_HQL = "delete  from SG_USER where USER_ID=?";
	private final static String DEL_HQL_RELATION = "delete  from USER_ROLE_RELATION where USER_ID=?";
	private final static String PW_HQL = "update SG_USER set USER_PASSWORD=? where USER_ID=?";
	
	
	
	@Override
	public void insert(SGUser SGUser){
		org.hibernate.Transaction t= getSession().beginTransaction();
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(ADD_HQL);
		query.setParameter(0, SGUser.getId());
		query.setParameter(1, SGUser.getName());
		query.setParameter(2, SGUser.getUserMobile());
		query.setParameter(3, SGUser.getUserTel());
		query.setParameter(4, SGUser.getPassword());
		query.setParameter(5, SGUser.getOrgId());
		query.setParameter(6, SGUser.getStartdate());
		query.setParameter(7, SGUser.getLastdate());
		query.setParameter(8, SGUser.getRemark());
		query.executeUpdate();
		SQLQuery queryUR = session.createSQLQuery(ADD_HQL_RELATION);
		for(UserRoleRelation u: SGUser.getUserRoleRelations()){
			queryUR.setParameter(0, SGUser.getId());
			queryUR.setParameter(1,u.getRoleId());
			queryUR.executeUpdate();
		}
		t.commit();
	}
	
	public void update(SGUser SGUser){
		org.hibernate.Transaction t= getSession().beginTransaction();
		Session session = getSession();
		//根据userId删除关联表中的相关数据
		SQLQuery delquery = session.createSQLQuery(DEL_HQL_RELATION);
		delquery.setParameter(0, SGUser.getId());
		delquery.executeUpdate();
		//修改SG_USER表的数据
		SQLQuery query = session.createSQLQuery(UPDATE_HQL);
		query.setParameter(0, SGUser.getName());
		query.setParameter(1, SGUser.getUserMobile());
		query.setParameter(2, SGUser.getUserTel());
		query.setParameter(3, SGUser.getPassword());
		query.setParameter(4, SGUser.getOrgId());
		query.setParameter(5, SGUser.getStartdate());
		query.setParameter(6, SGUser.getLastdate());
		query.setParameter(7, SGUser.getRemark());
		query.setParameter(8, SGUser.getId());
		query.executeUpdate();
		//在USER_ROLE_RELATION插入与SG_ROLE的关联
		SQLQuery queryUR = session.createSQLQuery(ADD_HQL_RELATION);
		for(UserRoleRelation u: SGUser.getUserRoleRelations()){
			queryUR.setParameter(0, SGUser.getId());
			queryUR.setParameter(1,u.getRoleId());
			queryUR.executeUpdate();
		}
		t.commit();
	}
	public void deleteById(String id){
		org.hibernate.Transaction t= getSession().beginTransaction();
		Session session = getSession();
		//根据userId删除关联表中的相关数据
		SQLQuery delquery = session.createSQLQuery(DEL_HQL_RELATION);
		delquery.setParameter(0, id);
		delquery.executeUpdate();
		SQLQuery query = session.createSQLQuery(DEL_HQL);
		query.setParameter(0,id);
		query.executeUpdate();
		t.commit();
		}
	
	public void passWord(SGUser SGUser){
		org.hibernate.Transaction t= getSession().beginTransaction();
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(PW_HQL);
		query.setParameter(0, SGUser.getPassword());
		query.setParameter(1, SGUser.getId());
		query.executeUpdate();
		t.commit();
	}
}
