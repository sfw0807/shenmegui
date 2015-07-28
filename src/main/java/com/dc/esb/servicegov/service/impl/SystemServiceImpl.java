package com.dc.esb.servicegov.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.*;
import com.dc.esb.servicegov.entity.System;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.SystemDAOImpl;
import com.dc.esb.servicegov.service.SystemService;

@Service
@Transactional
public class SystemServiceImpl extends AbstractBaseService<System, String> implements SystemService{

	@Autowired
	private SystemDAOImpl systemDAOImpl;
	@Autowired
	private ServiceInvokeServiceImpl serviceInvokeService;


	@Override
	public HibernateDAO<System, String> getDAO() {
		return systemDAOImpl;
	}

	@Override
	public void insertProtocol(SystemProtocol systemProtocol) {
		systemDAOImpl.exeHql("insert into SystemProtocol(systemId,protocolId) values(?,?)",systemProtocol.getSystemId(),systemProtocol.getProtocolId());
	}

	public void deleteProtocol(SystemProtocol systemProtocol){
		systemDAOImpl.exeHql("delete from SystemProtocol where systemId = ?",systemProtocol.getSystemId());
	}

	/**
	 * 是否包含接口
	 * @return
	 */
	public boolean containsInterface(String systemId){
		Map<String, String> params = new HashMap<String, String>();
		params.put("systemId", systemId);
		List<ServiceInvoke> list =  serviceInvokeService.findBy(params);
		if(list != null && list.size() > 0){
			for(ServiceInvoke si : list){
				if(StringUtils.isNotEmpty(si.getInterfaceId())){
					return true;
				}
			}
		}
		return false;
	}

}
