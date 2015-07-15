package com.dc.esb.servicegov.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.BaseLineVersionHisMapping;
import com.dc.esb.servicegov.entity.OperationHis;
import com.dc.esb.servicegov.entity.ServiceInvoke;

@Repository
public class ServiceInvokeDAOImpl  extends HibernateDAO<ServiceInvoke, String> {
	public List<ServiceInvoke> getBLInvoke(String baseId) {
        String hql = "from " + ServiceInvoke.class.getName() + " as invoke where invoke.invokeId in  (select si.invokeId from " +ServiceInvoke.class.getName() + " as si, "
               +OperationHis.class.getName()+ " as oh where oh.versionHis.autoId in (select bvhm.versionHisId from " + BaseLineVersionHisMapping.class.getName() + " as bvhm where bvhm.baseLineId=?)" +
                "	and si.serviceId = oh.serviceId and si.operationId = oh.operationId)";
        List<ServiceInvoke> list = find(hql, baseId);
        return list;
    }
}