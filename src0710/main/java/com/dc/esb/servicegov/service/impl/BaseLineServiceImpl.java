package com.dc.esb.servicegov.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.BaseLineDAOImpl;
import com.dc.esb.servicegov.entity.BaseLine;
import com.dc.esb.servicegov.entity.BaseLineVersionHisMapping;
import com.dc.esb.servicegov.util.DateUtils;

@Service
@Transactional
public class BaseLineServiceImpl extends BaseServiceImpl<BaseLine>{
	@Autowired
	private BaseLineDAOImpl daoImpl;
	@Autowired
	private VersionServiceImpl versionServiceImpl;
	@Autowired
	private BVServiceImpl bvServiceImpl;
	// 查询已通过审核但未发布的场景列表
		public List<?> operationList() {
			String hql = " from Operation a where a.state=? and a.version.optType !=?";
			List<?> list = daoImpl.find(hql, "1", "3");
			return list;
		}
	// 查询已发布的场景列表
	public List<?> operationHisList() {
		String hql = " from OperationHis a where a.state=?";
		List<?> list = daoImpl.find(hql, "1");
		return list;
	}
	
	public boolean release(HttpServletRequest req, String code, String blDesc, String[] versionHisIds){
		//新建基线
		BaseLine bl = new BaseLine();
		bl.setBaseId(UUID.randomUUID().toString());
		bl.setCode(code);
		bl.setBlDesc(blDesc);
		bl.setOptDate(DateUtils.format(new Date()));
		String versionId = versionServiceImpl.addVersion("0", bl.getBaseId());
		bl.setVersionId(versionId);
		daoImpl.save(bl);
		
		//保存基线版本关系
		for(String versionHisId : versionHisIds){
			BaseLineVersionHisMapping bvm = new BaseLineVersionHisMapping();
			bvm.setBaseLineId(bl.getBaseId());
			bvm.setVersionHisId(versionHisId);
			bvServiceImpl.editEntity(bvm);
		}
		
		//更新版本
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "1");
		params.put("versionHisIds", versionHisIds);
		
		daoImpl.batchExecute(" update VersionHis set type=(:type) where autoId in(:versionHisIds)", params);
//		
//		for(String versionId : versionIds){
//			Operation operation = daoImpl.findUnique(" from Operation o where o.versionId = ?", versionId);
//			//保存历史
//			if(operation != null){
//				operationServiceImpl.his(req, operation);
//			}
//		}
		return true;
	}
	
	public List<BaseLine> getBaseLine(String code, String blDesc) {
		String hql = " from BaseLine where 1=1 ";
		if(StringUtils.isNotEmpty(code)){
			hql += "and code like '%"+code+"%' ";
		}
		if(StringUtils.isNotEmpty(blDesc)){
			hql += "and blDesc like '%"+blDesc+"%' ";
		}
		List<BaseLine >list = daoImpl.find(hql);
		return list;
	}
	
	public List<?> getBLOperationHiss(String baseId){
		String hql = " from OperationHis oh where oh.versionHis.autoId in (select bvhm.versionHisId from BaseLineVersionHisMapping bvhm where bvhm.baseLineId=?)";
		List<?> list = daoImpl.find(hql, baseId);
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public List<?> getBLInvoke(String baseId){
		String hql = "from ServiceInvoke as invoke where invoke.invokeId in  (select si.invokeId from ServiceInvoke as si, " +
				" OperationHis as oh where oh.versionHis.autoId in (select bvhm.versionHisId from BaseLineVersionHisMapping as bvhm where bvhm.baseLineId=?)" +
				"	and si.serviceId = oh.serviceId and si.operationId = oh.operationId)";
		List<?> list = daoImpl.find(hql, baseId);
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
}
