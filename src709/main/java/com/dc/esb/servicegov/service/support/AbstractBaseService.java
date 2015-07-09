package com.dc.esb.servicegov.service.support;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.dao.support.Page;
import com.dc.esb.servicegov.dao.support.SearchCondition;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Transactional
public abstract class AbstractBaseService<T, PK extends Serializable>{

	/**
	 * 获取DAO对象
	 *
	 * @return
	 */
	public abstract HibernateDAO<T, PK> getDAO();

	/**
	 * 返回所有对象
	 *
	 * @return
	 */
	public List<T> getAll() {
		return (List<T>) getDAO().getAll();
	}

	/**
	 * 根据id获取对象
	 *
	 * @param id
	 * @return
	 */
	public T getById(PK id) {
		return (T) getDAO().get(id);
	}

	/**
	 * 根据多个id获取对象List
	 *
	 * @param ids
	 * @return
	 */
	public List<T> getByIds(Collection<PK> ids) {
		return (List<T>) getDAO().get(ids);
	}

	/**
	 * 保存对象
	 *
	 * @param entity
	 */
	@Transactional
	public void save(T entity) {
		getDAO().save(entity);
	}
	
	@Transactional
	public void insert(T entity) {
		getDAO().insert(entity);
	}

	/**
	 * 修改对象
	 *
	 * @param entity
	 */
	public void update(T entity) {
		getDAO().save(entity);
	}

	/**
	 * 删除对象
	 *
	 * @param entity
	 */
	public void delete(T entity) {
		getDAO().delete(entity);
	}

	/**
	 * 根据Id删除
	 *
	 * @param id
	 */
	public void deleteById(PK id) {
		getDAO().delete(id);
	}

	public List<T> findBy(Map<String, String> properties){
		return getDAO().findBy(properties);
	}

	public List<T> findBy(SearchCondition searchCond, Page page){
		return getDAO().findBy(searchCond,page);
	}

	public Page findBy(SearchCondition searchCond, int pageSize) {
		return getDAO().findBy(searchCond, pageSize);
	}

	public T findUniqueBy(Map<String,String> params){
		return getDAO().findUniqureBy(params);
	}

	public T findUniqueBy(String name, Object value){
		return getDAO().findUniqueBy(name, value);
	}

	public List<T> findLike(Map<String, String> params){
		return getDAO().findLike(params);
	}

	public Page findPage(final String hql, int pageSize, List<SearchCondition> searchConds) {
		return getDAO().findPage(hql, pageSize, searchConds);
	}

	public List<T> findBy(String hql, Page page,List<SearchCondition> searchConds){
		return getDAO().findBy(hql, page, searchConds);
	}

	public List<T> findBy(final Map<String, String> properties, String orderByProperties){
		return getDAO().findBy(properties, orderByProperties);
	}

}
