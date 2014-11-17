package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.ArrayList;
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
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.SDA;
import com.dc.esb.servicegov.refactoring.entity.SLA;
import com.dc.esb.servicegov.refactoring.service.OperationManager;
import com.dc.esb.servicegov.refactoring.vo.OperationInfoVO;
import com.dc.esb.servicegov.refactoring.vo.SDAVO;

@Component
@Transactional(rollbackFor = Exception.class)
public class OperationManagerImpl implements OperationManager {
	
	private Log log = LogFactory.getLog(getClass());
	@Autowired
	private OperationDAOImpl operationDao;
	@Autowired
	private SDADAOImpl sdaDao;
	@Autowired
	private SLADAOImpl slaDao;
	@Autowired
	private OLADAOImpl olaDao;
	@Autowired
	private OperationHistoryDAOImpl operationvHistoryDao;
	@Autowired
	private SDAHistoryDAOImpl sdaHistoryDao;
	@Autowired
	private SLAHistoryDAOImpl slaHistoryDao;
	@Autowired
	private OLAHistoryDAOImpl olaHistoryDao;
	
	public List<Operation> getOperationsOfService(String serviceId) {
		return operationDao.findBy("serviceId", serviceId);
	}
	
	@SuppressWarnings("unchecked")
	public List<OperationInfoVO> getAllOperations() {
		List<Operation> allOperations = new ArrayList<Operation>();		
		List<OperationInfoVO> operationInfoVo = new ArrayList<OperationInfoVO>();
		try{
			allOperations = operationDao.getAll();
			for(Operation operation:allOperations){
				OperationInfoVO operationVO = new OperationInfoVO();
				operationVO.setOperationId(operation.getOperationId());
				operationVO.setOperationName(operation.getOperationName());
				operationVO.setServiceId(operation.getService().getServiceId());
				operationVO.setServiceName(operation.getService().getServiceName());
				operationVO.setVersion(operation.getVersion());
				operationVO.setState(operation.getState());
				operationVO.setRemark(operation.getRemark().trim());
				operationVO.setPublishVersion("");
				operationVO.setPublishDate("");	
//				List pubList = operationDao.getPublishInfo(operation.getOperationId());
//				if(null!=pubList && pubList.size()>0){
//					Map map = (Map)pubList.get(0);
//					String onlineDate = (String) map.get("ONLINE_DATE");
//					String operationVersion = (String) map.get("OPERATION_VERSION");
//					operationVO.setPublishVersion(operationVersion);
//					operationVO.setPublishDate(onlineDate);				
//				}else{
//					operationVO.setPublishVersion("");
//					operationVO.setPublishDate("");	
//				}
				operationInfoVo.add(operationVO);
			}
		}catch(Exception e){
			log.error("error in getAllOperations...", e);
		}
		return operationInfoVo;
	}


	public Operation getOperation(String operationId,String serviceId) {	
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("operationId", operationId);
		paramMap.put("serviceId", serviceId);
		Operation operation = operationDao.findBy(paramMap).get(0);
		return operation;
	}
	public SDAVO getSDAByOperation(String operationId,String serviceId){
		SDAVO root = null;
		if(null!=operationId){	
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("operationId", operationId);
			paramMap.put("serviceId", serviceId);
			List<SDA> nodes = sdaDao.findBy(paramMap,"seq");
			Map<String, SDAVO> sdaMap = new HashMap<String, SDAVO>(nodes.size());
			String tmpPath = "/";
			for(SDA sdaNode : nodes){
				SDAVO sdaVO = new SDAVO();
				sdaVO.setValue(sdaNode);
				sdaMap.put(sdaNode.getId(), sdaVO);
				String parentId = sdaNode.getParentId();
				if ("/".equalsIgnoreCase(parentId)) {
					root = sdaVO;
					sdaVO.setXpath("/");
				}
				String metadataId = sdaVO.getValue().getMetadataId();
				String structId = sdaVO.getValue().getStructId();
				SDAVO parentsdaVO = sdaMap.get(parentId);
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
	public List<SLA> getSLAByOperationId(String operationId,String serviceId){
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("operationId", operationId);
		paramMap.put("serviceId", serviceId);
		return slaDao.findBy(paramMap);
	}
	public List<OLA> getOLAByOperationId(String operationId,String serviceId){	
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("operationId", operationId);
		paramMap.put("serviceId", serviceId);		
		return olaDao.findBy(paramMap);
	}
	public boolean deleteOperation(Operation operation) {
	
		return false;
	}

	public boolean deleteOperation(String operationId, String serviceId) {
		boolean isSuccess = false;
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("operationId", operationId);
		paramMap.put("serviceId", serviceId);
		List<Operation> operationList = operationDao.findBy(paramMap);
		try{
			for(Operation operation:operationList){
				operationDao.delete(operation);
			}
			isSuccess = true;
		}catch(Exception e){
			log.error("error in delete operation", e);
		}
		return isSuccess;
	}
	public boolean saveOperation(Operation operation) {
		boolean isSuccess = false;
		try{
			operationDao.save(operation);
			isSuccess = true;
		}catch(Exception e){
			log.error("error in add operation", e);
		}
		return isSuccess;
	}
	public List<SDA> getSDAByOperationId(String operationId, String serviceId) {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("operationId", operationId);
		paramMap.put("serviceId", serviceId);
		List<SDA> sdaList = sdaDao.findBy(paramMap);
		return sdaList;
	}
	public boolean deployOperation(String operationId, String serviceId) {
		return operationDao.deployOperation(operationId, serviceId);
	}
	public boolean redefOperation(String operationId, String serviceId) {
		boolean bakFlag = saveOperationHistoryVersion(operationId, serviceId);
		if(bakFlag){
			log.info("save history version success while redef operation");
			return operationDao.redefOperation(operationId, serviceId);
		}else{
			log.error("error in save history version while redef operation");
			return false;
		}
	}
	public boolean publishOperation(String operationId, String serviceId) {
		boolean bakFlag = saveOperationHistoryVersion(operationId, serviceId);
		if(bakFlag){
			log.info("save history version success while publish operation");
			return operationDao.publishOperation(operationId, serviceId);
		}else{
			log.error("error in save history version while publish operation");
			return false;
		}
	}
	public boolean saveOperationHistoryVersion(String operationId, String serviceId){
		boolean operationFlag = operationvHistoryDao.saveHistoryVersion(getOperation(operationId, serviceId));
		boolean sdaFlag = sdaHistoryDao.saveHistoryVersion(getSDAByOperationId(operationId, serviceId));
		boolean slaFlag = slaHistoryDao.saveHistoryVersion(getSLAByOperationId(operationId, serviceId));
		boolean olaFlag = olaHistoryDao.saveHistoryVersion(getOLAByOperationId(operationId, serviceId));
		return operationFlag&&sdaFlag&&slaFlag&&olaFlag;
	}
}
