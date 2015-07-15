package com.dc.esb.servicegov.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.service.support.Constants;

@Repository
public class OperationDAOImpl extends HibernateDAO<Operation, String> {
	public boolean auditOperation(String state, String[] operationIds) {
		if (operationIds != null && operationIds.length > 0) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("state", state);
			params.put("operationIds", operationIds);
			batchExecute(
					" update Operation set state =(:state) where operationId in (:operationIds)",
					params);
			return true;
		}
		return false;
	}

	public List<Operation> getReleased() {
		String hql = " from Operation a where a.state=? and a.version.optType !=?";
		List<Operation> list = find(hql, Constants.Operation.OPT_STATE_PASS, Constants.Version.OPT_TYPE_RELEASE);
		return list;
	}
}
