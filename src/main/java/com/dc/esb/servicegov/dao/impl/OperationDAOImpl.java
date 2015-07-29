package com.dc.esb.servicegov.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.OperationPK;
import com.dc.esb.servicegov.entity.ServiceCategory;
import com.dc.esb.servicegov.util.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.service.support.Constants;
import org.springframework.util.Assert;

@Repository
public class OperationDAOImpl extends HibernateDAO<Operation, OperationPK> {
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

	@Override
	public void save(Operation entity){
		entity.setOptDate(DateUtils.format(new Date()));
		String userName = (String) SecurityUtils.getSubject().getPrincipal();
		entity.setOptUser(userName);
		super.save(entity);
	}

	public List<Operation> getByMetadataId(String metadataId){
		String hql = "select o from Operation as o, SDA s where o.operationId = s.operationId and o.serviceId = s.serviceId and s.metadataId = ? ";
		List<Operation> list = this.find(hql, metadataId);
		return list;
	}

	public List<Operation> getByCategoryId(String categoryId){
		String hql = " from " + Operation.class.getName() + " op where op.service.categoryId = ?)";
		List<Operation> opList = this.find(hql, categoryId);
		return opList;
	}

//	@Override
//	public void delete(OperationPK id) {
//		Assert.notNull(id, "id不能为空");
//		String hql = " update "+ Operation.class.getName() + " set state=? , deleted=?" ;
//		super.batchExecute(hql,Constants.Operation.OPT_STATE_UNAUDIT, Constants.DELTED_TRUE);
//	}
}
