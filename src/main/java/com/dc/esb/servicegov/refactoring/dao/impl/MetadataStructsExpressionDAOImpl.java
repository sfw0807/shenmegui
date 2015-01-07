package com.dc.esb.servicegov.refactoring.dao.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.MetadataStructsExpression;
import com.dc.esb.servicegov.refactoring.entity.MetadataStructsExpressionPK;

@Repository
public class MetadataStructsExpressionDAOImpl extends
		HibernateDAO<MetadataStructsExpression, MetadataStructsExpressionPK> {

	private Log log = LogFactory.getLog(MetadataStructsExpressionDAOImpl.class);

	/**
	 * get MetadataStructsExpression
	 * @param structId
	 * @param elementId
	 * @return
	 */
	public MetadataStructsExpression getByStructIdAndElementId(String structId,String elementId){
		if(log.isInfoEnabled()){
			log.info("根据structid 和 elementid 获取元数据结构的表达式!");
		}
		MetadataStructsExpressionPK pk = new MetadataStructsExpressionPK();
		pk.setStructId(structId);
		pk.setMetadataId(elementId);
		return this.get(pk);
	}
	
}
