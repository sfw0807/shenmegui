package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.dc.esb.servicegov.refactoring.dao.impl.MetadataStructsAttrDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.MetadataStructsDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SDADAOImpl;
import com.dc.esb.servicegov.refactoring.entity.MetadataStructs;
import com.dc.esb.servicegov.refactoring.entity.MetadataStructsAttr;
import com.dc.esb.servicegov.refactoring.service.MetadataStructsManager;

@Component
@Transactional
public class MetadataStructsManagerImpl implements MetadataStructsManager {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private MetadataStructsDAOImpl metadataStructsDAO;
	@Autowired
	private MetadataStructsAttrDAOImpl metadataStructsAttrDAO;
	@Autowired
	private SDADAOImpl sdaDAO;

	@Override
	public List<MetadataStructs> getAll() {
		// TODO Auto-generated method stub\
		if(log.isInfoEnabled()){
			log.info("get all metadataStructs!");
		}
		return metadataStructsDAO.getAll();
	}

	/**
	 * delete metadataStructs and  metadataStructsAttr
	 */
	@Override
	public void delById(String structId) {
		// TODO Auto-generated method stub
		// delete metadataStructsAttr firstly 
		metadataStructsAttrDAO.delByStructId(structId);
		// delete metadataStructsAttr secondly 
		metadataStructsDAO.delete(structId);
	}

	@Override
	public MetadataStructs getMetadataStructsById(String structId) {
		// TODO Auto-generated method stub
		MetadataStructs metadataStructs = metadataStructsDAO.findUniqueBy("structId", structId);
		if(metadataStructs ==  null){
			metadataStructs = new MetadataStructs();
		}
		return metadataStructs;
	}

	@Override
	public void insertOrUpdate(MetadataStructs metadataStructs) {
		// TODO Auto-generated method stub
		metadataStructsDAO.save(metadataStructs);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isUsed(String structId) {
		// TODO Auto-generated method stub
		List list = sdaDAO.findBy("metadataId", structId);
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<MetadataStructsAttr> getMdtStructsAttrByStructId(String structId) {
		// TODO Auto-generated method stub
		List<MetadataStructsAttr> list = metadataStructsAttrDAO.getMdtStructsAttrByStructId(structId);
		if(list == null){
			list = new ArrayList<MetadataStructsAttr>();
		}
		return list;
	}
}
