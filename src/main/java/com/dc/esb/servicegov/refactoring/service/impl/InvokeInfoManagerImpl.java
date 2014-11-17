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

import com.dc.esb.servicegov.refactoring.dao.impl.InvokeInfoDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.PublishInfoDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SvcAsmRelateViewDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.TransStateDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.entity.PublishInfo;
import com.dc.esb.servicegov.refactoring.entity.System;
import com.dc.esb.servicegov.refactoring.entity.TransState;
import com.dc.esb.servicegov.refactoring.service.InvokeInfoManager;
import com.dc.esb.servicegov.refactoring.service.SystemManager;
import com.dc.esb.servicegov.refactoring.vo.InvokeInfoVo;
import com.dc.esb.servicegov.refactoring.vo.SystemInvokeServiceInfo;

@Component
@Transactional
public class InvokeInfoManagerImpl implements InvokeInfoManager {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private SystemManager systemManager;
	@Autowired
	private InvokeInfoDAOImpl invokeInfoDAO;
	@Autowired
	private PublishInfoDAOImpl publishInfoDAO;
	@Autowired
	private TransStateDAOImpl tranInfoDAO;
	@Autowired
	private SvcAsmRelateViewDAOImpl svcasmrelateviewDAO;
	@Override
	public List<SystemInvokeServiceInfo> getSystemInvokeServiceInfo(Map<String,String> mapConditions) {
		List<SystemInvokeServiceInfo> systemInvokeServiceInfos = new ArrayList<SystemInvokeServiceInfo>();
		List<System> systems = systemManager.getAllSystems();
		if(systems.size() > 0){
			for(System system : systems){
				Integer countServicesOfPID = invokeInfoDAO.getCountOfServicesByPID(system.getSystemId(),mapConditions);
				Integer countServicesOfCID = invokeInfoDAO.getCountOfServicesByCID(system.getSystemId(),mapConditions);
				Integer countOperationsOfPID = invokeInfoDAO.getCountOfOperationsByPID(system.getSystemId(),mapConditions);
				Integer countOperationsOfCID = invokeInfoDAO.getCountOfOperationsByCID(system.getSystemId(),mapConditions);
				// 去除ESB系统本身
				if("ESB".equals(system.getSystemAb().toUpperCase())){
					continue;
				}
				// 数量都为0的系统不显示
				if(countServicesOfPID == 0 && countServicesOfCID == 0 && countOperationsOfPID ==0 && countOperationsOfCID ==0){
					continue;
				}
				SystemInvokeServiceInfo sysServiceInfo = new SystemInvokeServiceInfo();
				sysServiceInfo.setSystemName(system.getSystemName());
				sysServiceInfo.setSystemAB(system.getSystemAb());
				sysServiceInfo.setFirstPublishDate(system.getFirstPublishDate());
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
	
	public InvokeInfo getInvokeByEcode(String ecode) {
		return invokeInfoDAO.findUniqueBy("ecode", ecode);
	}
	public boolean deleteInvokeInfo(String operationId, String serviceId) {
		boolean isSuccess = false;
		try{
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("operationId", operationId);
			paramMap.put("operationId", operationId);
			List<InvokeInfo> invokeList = invokeInfoDAO.findBy(paramMap);
			for(InvokeInfo invokeInfo:invokeList){
				invokeInfoDAO.delete(invokeInfo);
			}	
			isSuccess = true;
		}catch(Exception e){
			log.error("error in delete invokeinfo");
		}
		return isSuccess;
	}
	public List<InvokeInfoVo> getAllInvokeInfo() {
		List<InvokeInfoVo> invokeList = new ArrayList<InvokeInfoVo>();
		List<Map<String,Object>> mapList = svcasmrelateviewDAO.getAllInvokeInfo();
		for (int i=0;i<mapList.size();i++) {	
			Map<String,Object> map = mapList.get(i);
			InvokeInfoVo invokeInfoVo = new InvokeInfoVo();
			if(map.get("SERVICE_ID")==null||map.get("OPERATION_ID")==null||map.get("ECODE")==null
					||map.get("PROVIDE_MSG_TYPE")==null||map.get("CONSUME_MSG_TYPE")==null){
				continue;
			}
			if(map.get("OPERATION_NAME")==null){
				invokeInfoVo.setOperationInfo(map.get("OPERATION_ID").toString()+"/"+"");
			}else{
				invokeInfoVo.setOperationInfo(map.get("OPERATION_ID").toString()+"/"+map.get("OPERATION_NAME").toString());
			}
			if(map.get("SERVICE_NAME")==null){
				invokeInfoVo.setServiceInfo(map.get("SERVICE_ID").toString()+"/"+"");
			}else{
				invokeInfoVo.setServiceInfo(map.get("SERVICE_ID").toString()+"/"+map.get("SERVICE_NAME").toString());
			}
			if(map.get("INTERFACE_NAME")==null){
				invokeInfoVo.setInterfaceInfo(map.get("ECODE").toString()+"/"+"");
			}else{
				invokeInfoVo.setInterfaceInfo(map.get("ECODE").toString()+"/"+map.get("INTERFACE_NAME").toString());
			}
			invokeInfoVo.setProvideMsgType(map.get("PROVIDE_MSG_TYPE").toString());
			invokeInfoVo.setConsumeMsgType(map.get("CONSUME_MSG_TYPE").toString());
			invokeInfoVo.setProvideSysInfo(map.get("PRDSYSAB").toString()+"/"+map.get("PRDSYSNAME").toString());
			invokeInfoVo.setThrough(map.get("THROUGH")==null?"":map.get("THROUGH").toString());
			invokeInfoVo.setState(map.get("VERSIONST")==null?"":map.get("VERSIONST").toString());
			invokeInfoVo.setOnlineDate(map.get("ONLINE_DATE")==null ? "":map.get("ONLINE_DATE").toString());
			invokeInfoVo.setOnlineVersion(map.get("ONLINE_VERSION")==null?"":map.get("ONLINE_VERSION").toString());
			invokeList.add(invokeInfoVo);
		}
		return invokeList;
	}
	public int getId(Map param){
		InvokeInfo invokeInfo = invokeInfoDAO.findBy(param).get(0);
		return invokeInfo.getId();
	}
	public List<System> getConsumer(Map param){
		List<System> consumerList = new ArrayList<System>();
		List<InvokeInfo> invokeList = invokeInfoDAO.findBy(param);
		for(InvokeInfo invokeInfo:invokeList){
			String consumerId = invokeInfo.getConsumeSysId();
			System system = systemManager.getSystemById(consumerId);
			consumerList.add(system);
		}
		return consumerList;
	}
	//add invokeinfo,add transtate,add publishinfo if neccessary 
	public boolean addConsumer(String[] params){
		String operationId = params[0];
		String serviceId = params[1];
		String interfaceId = params[2];
		String provideMsgType = params[3];
		String consumeMsgType = params[4];
		String through = params[5];
		String state = params[6];
		String onlineVersion = params[7];
		String onlineDate = params[8];
		String sysId = params[9];
		String provideSysInfo = params[10];
		System system = systemManager.getSystemByAb(provideSysInfo);
		boolean flag = false;
		try{
			int invokeId = invokeInfoDAO.getMaxId()+1;
			InvokeInfo invokeInfo = new InvokeInfo();
			invokeInfo.setId(invokeId);
			invokeInfo.setOperationId(operationId);
			invokeInfo.setServiceId(serviceId);
			invokeInfo.setEcode(interfaceId);
			invokeInfo.setConsumeMsgType(consumeMsgType);
			invokeInfo.setProvideMsgType(provideMsgType);
			invokeInfo.setThrough(through);
			invokeInfo.setProvideSysId(system.getSystemId());
			invokeInfo.setConsumeSysId(sysId);
			invokeInfoDAO.save(invokeInfo);
			
			TransState transState = new TransState();
			transState.setId(invokeId);
			transState.setVersionState(state);
			tranInfoDAO.save(transState);
			
			if(onlineVersion!=null&&!"".equals(onlineVersion)&&onlineDate!=null&&!"".equals(onlineDate)){
				int publishInfoId = publishInfoDAO.getMaxId()+1;
				PublishInfo publishInfo = new PublishInfo();
				publishInfo.setId(publishInfoId);
				publishInfo.setIRid(invokeId);
				publishInfo.setOnlineDate(onlineDate);
				publishInfo.setOperationVersion(onlineVersion);
				publishInfo.setServiceVersion(onlineVersion);
				publishInfo.setInerfaceVersion(onlineVersion);
				publishInfo.setField("界面新增调用方");
				publishInfoDAO.save(publishInfo);
			}
			flag = true;
		}catch(Exception e){
			log.error(e, e);
		}
		return flag;
	}
	public List<InvokeInfo> getInvokeInfoByPSys(String pid) {
		return invokeInfoDAO.findBy("provideSysId", pid);
	}
	public List<InvokeInfo> getInvokeInfoByCSys(String cid) {
		return invokeInfoDAO.findBy("consumeSysId", cid);
	}

	/**
	 * 根据多个接口ID获取 去重后的invokeInfo信息
	 */
	@Override
	public List<InvokeInfo> getDuplicateInvokeByEcode(String[] interfaceIds) {
		// TODO Auto-generated method stub
		List<InvokeInfo> returnList = new ArrayList<InvokeInfo>();
		for(String interfaceId: interfaceIds){
			List<InvokeInfo> tempList = invokeInfoDAO.findBy("ecode", interfaceId);
			// 去重
			if(tempList == null){
				log.error("接口 [" + interfaceId + "]在invokeInfo中未找到!");
			}
			else{
				returnList = DuplicateInvokeList(returnList, tempList);
			}
		}
		return returnList;
	}
	
	public List<InvokeInfo> DuplicateInvokeList(List<InvokeInfo> targetList,
			List<InvokeInfo> sourceList) {
		for(InvokeInfo source: sourceList){
			boolean flag = false;
			for(InvokeInfo target: targetList){
				if(target.getServiceId().equals(source.getServiceId())
						&&target.getOperationId().equals(source.getOperationId())
						&&target.getEcode().equals(source.getEcode())
						&&target.getProvideMsgType().equals(source.getProvideMsgType())
						&&target.getConsumeMsgType().equals(source.getConsumeMsgType())){
					flag = true;
					break;
				}
			}
			
			if(!flag){
			    targetList.add(source);
			}
		}
		return targetList;
	}
}
