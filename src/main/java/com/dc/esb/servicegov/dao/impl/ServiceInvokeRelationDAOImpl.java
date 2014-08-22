package com.dc.esb.servicegov.dao.impl;

import com.dc.esb.servicegov.entity.ServiceInvokeRelation;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Administrator Date: 14-6-10 Time: 上午9:24
 */
@Repository
public class ServiceInvokeRelationDAOImpl extends
		HibernateDAO<ServiceInvokeRelation, String> {

	public List<ServiceInvokeRelation> getDupRelation() {
		String hqlStr = "from ServiceInvokeRelation as a where exists (from ServiceInvokeRelation b where a.operationId = b.operationId and a.providerSystemId <> b.providerSystemId)";
		Query query = this.createQuery(hqlStr.toString());
		List<ServiceInvokeRelation> resultList = query.list();
        return resultList;
	}
}
