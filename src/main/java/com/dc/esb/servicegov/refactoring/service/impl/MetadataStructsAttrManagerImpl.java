package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.MetadataStructsAttrDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.MetadataStructsAttr;
import com.dc.esb.servicegov.refactoring.entity.MetadataStructsAttrPK;
import com.dc.esb.servicegov.refactoring.service.MetadataStructsAttrManager;

@Component
@Transactional()
public class MetadataStructsAttrManagerImpl implements MetadataStructsAttrManager {
	
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private MetadataStructsAttrDAOImpl metadataStructsAttrDAO;

	@Override
	public void delById(String structId, String metadataId) {
		// TODO Auto-generated method stub
		MetadataStructsAttrPK pk = new MetadataStructsAttrPK();
		pk.setStructId(structId);
		pk.setMetadataId(metadataId);
		metadataStructsAttrDAO.delete(pk);
	}

	@Override
	public List<MetadataStructsAttr> getAll() {
		// TODO Auto-generated method stub
		return metadataStructsAttrDAO.getAll();
	}

	@Override
	public MetadataStructsAttr getMdtStructsAttrById(String structId,
			String metadataId) {
		// TODO Auto-generated method stub
		Map<String,String> properties = new HashMap<String,String>();
		properties.put("structId", structId);
		properties.put("metadataId", metadataId);
		List<MetadataStructsAttr> list = metadataStructsAttrDAO.findBy(properties);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void insertOrUpdate(MetadataStructsAttr metadataStructsAttr) {
		// TODO Auto-generated method stub
		metadataStructsAttrDAO.save(metadataStructsAttr);
	}

	@Override
	public void delByStructId(String structId) {
		// TODO Auto-generated method stub
		metadataStructsAttrDAO.delByStructId(structId);
	}

	@Override
	public List<MetadataStructsAttr> getMdtStructsAttrByStructId(String structId) {
		// TODO Auto-generated method stub
		return metadataStructsAttrDAO.getMdtStructsAttrByStructId(structId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean batchDelete(MetadataStructsAttr[] msaArr) {
		// TODO Auto-generated method stub
		return metadataStructsAttrDAO.batchDelete(msaArr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean batchInsertOrUpdate(MetadataStructsAttr[] msaArr) {
		// TODO Auto-generated method stub
		return metadataStructsAttrDAO.batchInsertOrUpdate(msaArr);
	}

	
}
