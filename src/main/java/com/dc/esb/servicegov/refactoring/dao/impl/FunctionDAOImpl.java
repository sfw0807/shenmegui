package com.dc.esb.servicegov.refactoring.dao.impl;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.Function;
import com.dc.esb.servicegov.refactoring.vo.InterfaceListVO;

/**
 * 
 * @author G
 * 
 */
@Repository
@Transactional
public class FunctionDAOImpl extends HibernateDAO<Function, String> {
//	private Log log = LogFactory.getLog(FunctionDAOImpl.class);
	public Integer getMaxId(){
		String hql = "SELECT MAX(id) FROM Function";
		Query query = getSession().createQuery(hql);
		Object obj = query.uniqueResult();
		return (Integer)obj;
	}
	
	/**
	 * 通过menuName获取MenuId
	 * @param name
	 * @return
	 */
	public String getMenuIdByName(String name){
		String hql = "select id from Function where name =:name";
		Query query = getSession().createQuery(hql);
		Object obj = query.setString("name", name).uniqueResult();
		if(obj == null){
			return "";
		}
		return obj.toString();
	}
	@SuppressWarnings("rawtypes")
	public List getTranLink(){
		String sql="SELECT T1.TRANCODE TRANCODE," +
				"T1.TRANNANE TRANNANE," +
				"T1.CONSUMER CONSUMER," +
				"T1.CONSUMERMSGTYPE CONSUMERMSGTYPE," +
				"T2.PROVIDER PROVIDER " +
				"FROM ESB.TRANCONSUMER T1,TRANPROVIDER T2  " +
				"WHERE  T1.TRANCODE = T2.TRANCODE";
		Query query = super.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	public List getProviderCount(){
		String sql="SELECT PROVIDER,COUNT(TRANCODE) PCOUNT FROM TRANPROVIDER GROUP BY PROVIDER";
		Query query = super.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	public List getConsumerCount(){
		String sql="SELECT CONSUMER ,COUNT(TRANCODE) CCOUNT FROM TRANCONSUMER GROUP BY CONSUMER";
		Query query = super.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}	
	public List getAllTranProvider(){
		String sql="SELECT * FROM TRANPROVIDER";
		Query query = super.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}	
	public List getAllTranConsumer(){
		String sql="SELECT * FROM TRANCONSUMER";
		Query query = super.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}	
	public List getTranProviderByCondition(String trancode,String tranname,String provider,String providerMsgType){
		StringBuffer sql = new StringBuffer("SELECT * FROM TRANPROVIDER WHERE 1=1 ");
		if(trancode!=null && !"".equals(trancode)){
			sql.append(" AND TRANCODE LIKE '%");
			sql.append(trancode);
			sql.append("%'");
		}
		if(tranname!=null && !"".equals(tranname)){
			sql.append(" AND TRANNAME LIKE '%");
			sql.append(tranname);
			sql.append("%'");
		}
		if(provider!=null && !"".equals(provider)){
			sql.append(" AND PROVIDER LIKE '%");
			sql.append(provider);
			sql.append("%'");
		}
		if(providerMsgType!=null && !"".equals(providerMsgType)){
			sql.append(" AND PROVIDERMSGTYPE like '%");
			sql.append(providerMsgType);
			sql.append("%'");
		}
		System.out.println(sql.toString());
		Query query = super.getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	public List getTranConsumerByCondition(String trancode,String tranname,String consumer,
			String passedsys,String provider,String consumerMsgType,String frontcode){
		StringBuffer sql = new StringBuffer("SELECT * FROM TRANCONSUMER WHERE 1=1 ");
		if(trancode!=null && !"".equals(trancode)){
			sql.append(" AND TRANCODE LIKE '%");
			sql.append(trancode);
			sql.append("%'");
		}
		if(trancode!=null && !"".equals(trancode)){
			sql.append(" AND TRANNANE LIKE '%");
			sql.append(tranname);
			sql.append("%'");
		}
		if(consumer!=null && !"".equals(consumer)){
			sql.append(" AND CONSUMER LIKE '%");
			sql.append(consumer);
			sql.append("%'");
		}
		if(passedsys!=null && !"".equals(passedsys)){
			sql.append(" AND PASSEDSYS like '%");
			sql.append(passedsys);
			sql.append("%'");
		}
		if(provider!=null && !"".equals(provider)){
			sql.append(" AND PROVIDER like '%");
			sql.append(provider);
			sql.append("%'");
		}
		if(consumerMsgType!=null && !"".equals(consumerMsgType)){
			sql.append(" AND CONSUMERMSGTYPE like '%");
			sql.append(consumerMsgType);
			sql.append("%'");
		}
		if(frontcode!=null && !"".equals(frontcode)){
			sql.append(" AND FONTTRANCODE like '%");
			sql.append(frontcode);
			sql.append("%'");
		}
		System.out.println(sql.toString());
		Query query = super.getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}	
}
