package com.dc.esb.servicegov.refactoring.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.ServiceCategory;
import com.dc.esb.servicegov.refactoring.util.AuditUtil;

@Repository
public class ServiceCategoryDAOImpl extends HibernateDAO<ServiceCategory, String> {
    
	/**
	 * 获取所有分组信息，不包括MSMGroup
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ServiceCategory> getAllCategoryInfo(){
		
		String hql = "FROM ServiceCategory g WHERE g.categoryId not in ('MSMGroup')";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	/**
	 * 获取一级分组信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ServiceCategory> getFirstLevelInfo(){
		String hql = "FROM ServiceCategory g WHERE g.parentId in ('MSMGroup','/')";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	/**
	 * 获取二级分组信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ServiceCategory> getSecondLevelInfo(){
		String hql = "FROM ServiceCategory g WHERE g.parentId not in ('MSMGroup','/')";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	/**
	 * 根据一级分组Id获取二级分组信息
	 * @return
	 */
	public List<ServiceCategory> getSecondCategoryByFirstId(String parentId){
		return this.findBy("parentId", parentId);
	}
	
	/**
	 * 获取二级分组下的服务数量
	 * @param categoryId
	 * @return
	 */
	public Integer countOfServiceBySecondCategoryId(String categoryId){
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(distinct service_id) from SERVICE where CATEGORY_ID = '");
		buffer.append(categoryId);
		buffer.append("' and auditState = '");
		buffer.append(AuditUtil.passed);
		buffer.append("'");
		Query query = getSession().createSQLQuery(buffer.toString());
		Object obj = query.uniqueResult();
        BigDecimal bi = (BigDecimal)obj;

		return Integer.parseInt(bi.toString());
	}
	
	/**
	 * 获取一级分组下的服务数量
	 * @param categoryId
	 * @return
	 */
	public Integer countOfServiceByFirstCategoryId(String categoryId){
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(distinct service_id) from SERVICE where CATEGORY_ID in (");
		buffer.append("select category_id from SERVICE_CATEGORY where PARENT_ID = '");
		buffer.append(categoryId);
		buffer.append("') and auditState = '");
		buffer.append(AuditUtil.passed);
		buffer.append("'");
		Query query = getSession().createSQLQuery(buffer.toString());
        Object obj = query.uniqueResult();
        BigDecimal bi = (BigDecimal)obj;
        return Integer.parseInt(bi.toString());
	}
	
	/**
	 * 获取二级分组下的操作数量
	 * @param categoryId
	 * @return
	 */
	public Integer countOfOperationBySecondCategoryId(String categoryId){
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(operation_id) from sg_operation ");
		buffer.append("where SERVICE_ID in ");
		buffer.append("(select distinct service_id from SERVICE where CATEGORY_ID = '");
		buffer.append(categoryId);
		buffer.append("' and auditState ='");
		buffer.append(AuditUtil.passed);
		buffer.append("') and auditState = '");
		buffer.append(AuditUtil.passed);
		buffer.append("'");
		Query query = getSession().createSQLQuery(buffer.toString());
		Object obj = query.uniqueResult();
        BigDecimal bi = (BigDecimal)obj;
        return Integer.parseInt(bi.toString());
	}
	
	/**
	 * 获取一级分组下的操作数量
	 * @param categoryId
	 * @return
	 */
	public Integer countOfOperationByFirstCategoryId(String categoryId){
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(operation_id) from sg_operation ");
		buffer.append("where SERVICE_ID in ");
		buffer.append("(select distinct service_id from SERVICE where CATEGORY_ID in (");
		buffer.append("select category_id from SERVICE_CATEGORY where PARENT_ID = '");
		buffer.append(categoryId);
		buffer.append("') and auditState = '");
		buffer.append(AuditUtil.passed);
		buffer.append("') and auditState = '");
		buffer.append(AuditUtil.passed);
		buffer.append("'");
		Query query = getSession().createSQLQuery(buffer.toString());
		Object obj = query.uniqueResult();
        BigDecimal bi = (BigDecimal)obj;
        return Integer.parseInt(bi.toString());
	}
}
