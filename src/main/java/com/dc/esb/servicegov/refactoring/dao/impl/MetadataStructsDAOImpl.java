package com.dc.esb.servicegov.refactoring.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.MetadataStructs;

@Repository
public class MetadataStructsDAOImpl extends HibernateDAO<MetadataStructs, String>{

	/**
	 * 判断元数据ID是否是结构体
	 * @param metadataId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isStructs(String metadataId){
		String sql ="select * from METADATA_STRUCTS where STRUCTID='" + metadataId + "'";
		Query query = getSession().createSQLQuery(sql);
		List list = query.list();
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
}
