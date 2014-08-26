package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.InvokeInfoDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.entity.System;
import com.dc.esb.servicegov.refactoring.service.InvokeInfoManager;
import com.dc.esb.servicegov.refactoring.service.SystemManager;
import com.dc.esb.servicegov.refactoring.vo.SystemInvokeServiceInfo;

@Component
@Transactional
public class InvokeInfoManagerImpl implements InvokeInfoManager {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private SystemManager systemManager;
	@Autowired
	private InvokeInfoDAOImpl invokeInfoDAO;

	@Override
	public List<SystemInvokeServiceInfo> getSystemInvokeServiceInfo() {
		List<SystemInvokeServiceInfo> systemInvokeServiceInfos = new ArrayList<SystemInvokeServiceInfo>();
		List<System> systems = systemManager.getAllSystems();
		if(systems.size() > 0){
			for(System system : systems){
				long countServicesOfPID = invokeInfoDAO.getCountOfServicesByPID(system.getSystemId());
				long countServicesOfCID = invokeInfoDAO.getCountOfServicesByCID(system.getSystemId());
				long countOperationsOfPID = invokeInfoDAO.getCountOfOperationsByPID(system.getSystemId());
				long countOperationsOfCID = invokeInfoDAO.getCountOfOperationsByCID(system.getSystemId());
				SystemInvokeServiceInfo sysServiceInfo = new SystemInvokeServiceInfo();
				sysServiceInfo.setSystemName(system.getSystemName());
				sysServiceInfo.setSystemAB(system.getSystemAb());
				sysServiceInfo.setProvideServiceNum(String.valueOf(countServicesOfPID));
				sysServiceInfo.setProvideOperationNum(String.valueOf(countOperationsOfPID));
				sysServiceInfo.setConsumeServiceNum(String.valueOf(countServicesOfCID));
				sysServiceInfo.setConsumeOperationNum(String.valueOf(countOperationsOfCID));
				systemInvokeServiceInfos.add(sysServiceInfo);
			}
		}else{
			log.error("系统中的接入系统数量为[0]");
		}
		return systemInvokeServiceInfos;
	}

	@Override
	public List<InvokeInfo> getInvokeInfos() {
		if(log.isInfoEnabled()){
			log.info("开始获取全量调用关系信息...");
		}
		List<InvokeInfo> invokeInfos = new ArrayList<InvokeInfo>();
		invokeInfos = invokeInfoDAO.getAll();
		return invokeInfos;
	}

}
