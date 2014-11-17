package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.OLADAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.OLAHistoryDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.OperationHistoryDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SDADAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SDAHistoryDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SLADAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SLAHistoryDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.OLA;
import com.dc.esb.servicegov.refactoring.entity.OLAHistory;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.OperationHistory;
import com.dc.esb.servicegov.refactoring.entity.SDA;
import com.dc.esb.servicegov.refactoring.entity.SDAHistory;
import com.dc.esb.servicegov.refactoring.entity.SLA;
import com.dc.esb.servicegov.refactoring.entity.SLAHistory;
import com.dc.esb.servicegov.refactoring.service.OLAManager;
import com.dc.esb.servicegov.refactoring.service.OperationHistoryManager;
import com.dc.esb.servicegov.refactoring.service.OperationManager;
import com.dc.esb.servicegov.refactoring.service.SDAManager;
import com.dc.esb.servicegov.refactoring.service.SLAManager;
import com.dc.esb.servicegov.refactoring.vo.SDAHistoryVO;


@Component
@Transactional(rollbackFor = Exception.class)
public class OperationHistoryManagerImpl implements OperationHistoryManager {
	
	private Log log = LogFactory.getLog(getClass());
	@Autowired
	private OperationHistoryDAOImpl operationHistoryDao;
	@Autowired
	private SDAHistoryDAOImpl sdaHistoryDao;
	@Autowired
	private SLAHistoryDAOImpl slaHistoryDao;
	@Autowired
	private OLAHistoryDAOImpl olaHistoryDao;
	@Autowired
	private OperationDAOImpl operationDao;
	@Autowired
	private SDADAOImpl sdaDao;
	@Autowired
	private SLADAOImpl slaDao;
	@Autowired
	private OLADAOImpl olaDao;
	@Autowired
	private OperationManager operationManager;
	@Autowired
	private SDAManager sdaManager;
	@Autowired
	private SLAManager slaManager;
	@Autowired
	private OLAManager olaManager;
	public List<OperationHistory> getAllHistoryOperation(String operationId,String serviceId) {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("operationId", operationId);
		paramMap.put("serviceId", serviceId);
		return operationHistoryDao.findBy(paramMap);
	}
	public List<OperationHistory> getOperation(String operationId,
			String operationVersion, String serviceId) {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("operationId", operationId);
		paramMap.put("operationVersion", operationVersion);
		paramMap.put("serviceId", serviceId);
		return operationHistoryDao.findBy(paramMap);
	}
	public SDAHistoryVO getSDA(String operationId, String operationVersion,
			String serviceId) {
		SDAHistoryVO root = null;
		if(null!=operationId){	
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("operationId", operationId);
			paramMap.put("operationVersion", operationVersion);
			paramMap.put("serviceId", serviceId);
			List<SDAHistory> nodes = sdaHistoryDao.findBy(paramMap,"seq");
			Map<String, SDAHistoryVO> sdaMap = new HashMap<String, SDAHistoryVO>(nodes.size());
			String tmpPath = "/";
			for(SDAHistory sdaNode : nodes){
				SDAHistoryVO sdaVO = new SDAHistoryVO();
				sdaVO.setValue(sdaNode);
				sdaMap.put(sdaNode.getId(), sdaVO);
				String parentId = sdaNode.getParentId();
				if ("/".equalsIgnoreCase(parentId)) {
					root = sdaVO;
					sdaVO.setXpath("/");
				}
				String metadataId = sdaVO.getValue().getMetadataId();
				String structId = sdaVO.getValue().getStructId();
				SDAHistoryVO parentsdaVO = sdaMap.get(parentId);
				if (null != parentsdaVO) {
					parentsdaVO.addChild(sdaVO);
					sdaVO.setXpath(tmpPath + "/" + metadataId);
					if ("request".equalsIgnoreCase(structId)) {
						tmpPath = "/request";
					}
					if ("response".equalsIgnoreCase(structId)) {
						tmpPath = "/response";
					}
				}
			}
			sdaMap = null;
		}
		return root;
	}
	public List<SLAHistory> getSLA(String operationId, String operationVersion,
			String serviceId) {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("operationId", operationId);
		paramMap.put("operationVersion", operationVersion);
		paramMap.put("serviceId", serviceId);
		return slaHistoryDao.findBy(paramMap);
	}
	public List<OLAHistory> getOLA(String operationId, String operationVersion,
			String serviceId) {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("operationId", operationId);
		paramMap.put("operationVersion", operationVersion);
		paramMap.put("serviceId", serviceId);
		return olaHistoryDao.findBy(paramMap);
	}
	public boolean backOperation(String operationId, String operationVersion,
			String serviceId) {
		boolean isSuccess = false;
		try{
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("operationId", operationId);
			paramMap.put("operationVersion", operationVersion);
			paramMap.put("serviceId", serviceId);
			OperationHistory operationHistory = operationHistoryDao.findBy(paramMap).get(0);
			List<SDAHistory> sdaHistoryList = sdaHistoryDao.findBy(paramMap);
			List<SLAHistory> slaHistoryList = slaHistoryDao.findBy(paramMap);
			List<OLAHistory> olaHistoryList = olaHistoryDao.findBy(paramMap);
			Operation operationNew = new Operation();
			operationNew.setOperationId(operationHistory.getOperationId());
			operationNew.setOperationName(operationHistory.getOperationName());
			operationNew.setState(operationHistory.getOperationState());
			operationNew.setVersion(operationHistory.getOperationVersion());
			operationNew.setServiceId(operationHistory.getServiceId());
			operationNew.setRemark(operationHistory.getRemark());
			operationNew.setModifyUser(operationHistory.getModifyUser());
			operationNew.setUpdateTime(operationHistory.getUpdateTime());
			operationDao.save(operationNew);
			for(SDAHistory sdaHistory : sdaHistoryList){
				SDA sdaNew = new SDA();
				sdaNew.setId(sdaHistory.getId());
				sdaNew.setMetadataId(sdaHistory.getMetadataId());
				sdaNew.setStructId(sdaHistory.getStructId());
				sdaNew.setSeq(sdaHistory.getSeq());
				sdaNew.setType(sdaHistory.getType());
				sdaNew.setRemark(sdaHistory.getRemark());
				sdaNew.setRequired(sdaHistory.getRequired());
				sdaNew.setParentId(sdaHistory.getParentId());
				sdaNew.setOperationId(sdaHistory.getOperationId());
				sdaNew.setServiceId(sdaHistory.getServiceId());
				sdaNew.setModifyUser(sdaHistory.getModifyUser());
				sdaNew.setUpdateTime(sdaHistory.getUpdateTime());
				sdaDao.save(sdaNew);
			}
			for (SLAHistory slaHistory : slaHistoryList) {
				SLA slaNew = new SLA();
				slaNew.setOperationId(slaHistory.getOperationId());
				slaNew.setServiceId(slaHistory.getServiceId());
				slaNew.setSlaName(slaHistory.getSlaName());
				slaNew.setSlaValue(slaHistory.getSlaValue());
				slaNew.setSlaRemark(slaHistory.getSlaRemark());
				slaNew.setModifyUser(slaHistory.getModifyUser());
				slaNew.setUpdateTime(slaHistory.getUpdateTime());
				slaDao.save(slaNew);
			}
			for (OLAHistory olaHistory : olaHistoryList) {
				OLA olaNew = new OLA();
				olaNew.setOperationId(olaHistory.getOperationId());
				olaNew.setServiceId(olaHistory.getServiceId());
				olaNew.setOlaName(olaHistory.getOlaName());
				olaNew.setOlaValue(olaHistory.getOlaValue());
				olaNew.setOlaRemark(olaHistory.getOlaRemark());
				olaNew.setModifyUser(olaHistory.getModifyUser());
				olaNew.setUpdateTime(olaHistory.getUpdateTime());		
				olaDao.save(olaNew);
			}
			isSuccess = true;
		}catch(Exception e){
			log.error(e, e);
		}
		return isSuccess;
	}
}
