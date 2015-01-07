package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.InvokeInfoDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.MetadataDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.MetadataStructsAttrDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SDADAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SystemDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.Metadata;
import com.dc.esb.servicegov.refactoring.entity.MetadataStructsAttr;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.service.MetadataManager;
import com.dc.esb.servicegov.refactoring.vo.MetadataUsedInfo;

@Service
@Transactional
public class MetadataManagerImpl implements MetadataManager{
	private Log log = LogFactory.getLog(MetadataManagerImpl.class);
	
	@Autowired
	private MetadataDAOImpl metadataDAO;
	@Autowired
	private OperationDAOImpl operationDAO;
	@Autowired
	private ServiceDAOImpl serviceDAO;
	@Autowired
	private InvokeInfoDAOImpl invokeInfoDAO;
	@Autowired
	private SystemDAOImpl systemDAO;
	@Autowired
	private SDADAOImpl SdaDAO;
	@Autowired
	private MetadataStructsAttrDAOImpl metaAttrDAO;

	@Override
	public void delByEntity(Metadata metadata) {
		// TODO Auto-generated method stub
		if(metadata != null){
			delById(metadata.getMetadataId());
		}
	}
	
	/**
    * 
    * @param structId
    * @return
    */
   public List<MetadataStructsAttr> getMetaStructById(String structId) {
   	return metaAttrDAO.findBy("structId", structId);
   }

	public Metadata getMetadataByMid(String mid){
    	List<Metadata> list = metadataDAO.findBy("metadataId", mid);
    	if (list != null) {
    		return metadataDAO.findBy("metadataId", mid).get(0);
    	}
    	return null;
    }
	
	@Override
	public void delById(String id) {
		// TODO Auto-generated method stub
		try{
		   metadataDAO.delete(id);
		}
		catch(Exception e){
			log.error("delete metadata ["+id+"] failed!" + e);
		}
		log.info("delete metadata ["+id+"] successed!");
	}

	@Override
	public List<Metadata> getAllMetadata() {
		// TODO Auto-generated method stub
		return metadataDAO.getAll();
	}

	@Override
	public void insert(Metadata metadata) {
		// TODO Auto-generated method stub
		try {
			
			metadataDAO.save(metadata);
		} catch (Exception e) {
			log.error("insert metadata [" + metadata.getMetadataId() + "] failed!" + e);
		}
		log.info("insert metadata [" + metadata.getMetadataId() + "] successed!");
	}

	@Override
	public void update(String id) {
		// TODO Auto-generated method stub
		try {
		Metadata metadata = getMetadataById(id);
		metadataDAO.save(metadata);
		} catch (Exception e) {
			log.error("update metadata [" + id + "] failed!" + e);
		}
		log.info("update metadata [" + id + "] successed!");
	}

	@Override
	public void updateEntity(Metadata metadata) {
		// TODO Auto-generated method stub
		try {
			metadataDAO.save(metadata);
		} catch (Exception e) {
			log.error("update metadata [" + metadata.getMetadataId() + "] failed!" + e);
		}
		log.info("update metadata [" + metadata.getMetadataId() + "] successed!");
	}

	@Override
	public Metadata getMetadataById(String id) {
		// TODO Auto-generated method stub
		Metadata metadata = metadataDAO.findUniqueBy("metadataId", id);
		return metadata;
	}
	
	/**
	 * extend method 元数据调用情况信息查询
	 */
	public List<MetadataUsedInfo> getUsedInfoByMetadataId(String id) {
		List<MetadataUsedInfo> returnList = new ArrayList<MetadataUsedInfo>();
		List<Map<String, String>> sdaList = SdaDAO.getSdaByMetadataId(id);
		for (Map<String, String> tempMap : sdaList) {
			String serviceId = tempMap.get("SERVICE_ID");
			com.dc.esb.servicegov.refactoring.entity.Service service = serviceDAO
					.findUniqueBy("serviceId", serviceId);
			String operationId = tempMap.get("OPERATION_ID");
			Operation operation = operationDAO.findUniqueBy("operationId",
					operationId);
			List<String> prdList = invokeInfoDAO.getPrdSysIdByOidAndSid(
					serviceId, operationId);
			for (String prdSysId : prdList) {
				MetadataUsedInfo metadataUsedInfo = new MetadataUsedInfo();
				com.dc.esb.servicegov.refactoring.entity.System system = systemDAO
						.findUniqueBy("systemId", prdSysId);
				if (system == null) {
					system = new com.dc.esb.servicegov.refactoring.entity.System();
				}
				metadataUsedInfo.setMetadataId(id);
				metadataUsedInfo.setOperationId(operationId);
				metadataUsedInfo.setOperationName(operation.getOperationName());
				metadataUsedInfo.setServiceId(serviceId);
				metadataUsedInfo.setServiceName(service.getServiceName());
				metadataUsedInfo.setPrdSysAB(system.getSystemAb());
				metadataUsedInfo.setPrdSysName(system.getSystemName());
				returnList.add(metadataUsedInfo);
			}
		}
		return returnList;
	}
}
