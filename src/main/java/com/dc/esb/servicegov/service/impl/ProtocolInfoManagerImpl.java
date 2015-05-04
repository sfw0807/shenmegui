package com.dc.esb.servicegov.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.ProtocolInfoDAOImpl;
import com.dc.esb.servicegov.entity.ProtocolInfo;
import com.dc.esb.servicegov.service.ProtocolInfoManager;

@Component
@Transactional
public class ProtocolInfoManagerImpl implements ProtocolInfoManager {
	
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private ProtocolInfoDAOImpl protocolInfoDAO;

	@Override
	public boolean batchInsertOrUpdate(ProtocolInfo[] infoArr) {
		// TODO Auto-generated method stub
		try{
			protocolInfoDAO.batchInsertOrUpdate(infoArr);
		}catch(Exception e){
			log.error("insert or update protocol infos error!", e);
			return false;
		}
		return true;
	}

	@Override
	public List<ProtocolInfo> getProtocolInfosBySysId(String sysId) {
		// TODO Auto-generated method stub
		return protocolInfoDAO.getProtocolInfobyId(sysId);
	}

	@Override
	public void delBySysId(String sysId) {
		// TODO Auto-generated method stub
		protocolInfoDAO.delbySysId(sysId);
	}

	@Override
	public void batchByIdArr(String[] idArr) {
		// TODO Auto-generated method stub
		for(String sysId :idArr){
			protocolInfoDAO.delbySysId(sysId);
		}
	}


}
