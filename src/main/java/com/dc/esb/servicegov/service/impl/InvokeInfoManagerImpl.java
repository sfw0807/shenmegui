package com.dc.esb.servicegov.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.IdaDAOImpl;
import com.dc.esb.servicegov.dao.impl.IdaPROPDAOImpl;
import com.dc.esb.servicegov.dao.impl.InterfaceDAOImpl;
import com.dc.esb.servicegov.dao.impl.InterfaceExtendsDAOImpl;
import com.dc.esb.servicegov.dao.impl.InvokeInfoDAOImpl;
import com.dc.esb.servicegov.dao.impl.OLADAOImpl;
import com.dc.esb.servicegov.dao.impl.OLAHistoryDAOImpl;
import com.dc.esb.servicegov.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.dao.impl.OperationHistoryDAOImpl;
import com.dc.esb.servicegov.dao.impl.PublishInfoDAOImpl;
import com.dc.esb.servicegov.dao.impl.SDADAOImpl;
import com.dc.esb.servicegov.dao.impl.SDAHistoryDAOImpl;
import com.dc.esb.servicegov.dao.impl.SLADAOImpl;
import com.dc.esb.servicegov.dao.impl.SLAHistoryDAOImpl;
import com.dc.esb.servicegov.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.dao.impl.ServiceHeadRelateDAOImpl;
import com.dc.esb.servicegov.dao.impl.SvcAsmRelateViewDAOImpl;
import com.dc.esb.servicegov.dao.impl.TransStateDAOImpl;
import com.dc.esb.servicegov.entity.InvokeInfo;
import com.dc.esb.servicegov.entity.PublishInfo;
import com.dc.esb.servicegov.entity.System;
import com.dc.esb.servicegov.entity.TransState;
import com.dc.esb.servicegov.service.InvokeInfoManager;
import com.dc.esb.servicegov.service.SystemManager;
import com.dc.esb.servicegov.vo.InvokeInfoVo;
import com.dc.esb.servicegov.vo.SystemInvokeServiceInfo;

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
	@Autowired
	private IdaDAOImpl idaDAO;
	@Autowired
	private IdaPROPDAOImpl idaPropDAO;
	@Autowired
	private InterfaceDAOImpl interfaceDAO;
	@Autowired
	private InterfaceExtendsDAOImpl interfaceExtendsDAO;
	@Autowired
	private OLADAOImpl olaDAO;
	@Autowired
	private OLAHistoryDAOImpl olaHistoryDAO;
	@Autowired
	private OperationDAOImpl operationDAO;
	@Autowired
	private OperationHistoryDAOImpl operationHistoryDAO;
	@Autowired
	private SDADAOImpl sdaDAO;
	@Autowired
	private SDAHistoryDAOImpl sdaHistoryDAO;
	@Autowired
	private ServiceDAOImpl serviceDAO;
	@Autowired
	private ServiceHeadRelateDAOImpl serviceHeadDAO;
	@Autowired
	private SLADAOImpl slaDAO;
	@Autowired
	private SLAHistoryDAOImpl slaHistoryDAO;
	@Override
	public List<SystemInvokeServiceInfo> getSystemInvokeServiceInfo(Map<String,String> mapConditions) {
		List<SystemInvokeServiceInfo> systemInvokeServiceInfos = new ArrayList<SystemInvokeServiceInfo>();
		List<System> systems = systemManager.getSystemsByConditons(mapConditions);
		if(systems !=null && systems.size() > 0){
			for(System system : systems){
				Integer countServicesOfPID = invokeInfoDAO.getCountOfServicesByPID(system.getSystemId(),mapConditions);
				Integer countServicesOfCID = invokeInfoDAO.getCountOfServicesByCID(system.getSystemId(),mapConditions);
				Integer countOperationsOfPID = invokeInfoDAO.getCountOfOperationsByPID(system.getSystemId(),mapConditions);
				Integer countOperationsOfCID = invokeInfoDAO.getCountOfOperationsByCID(system.getSystemId(),mapConditions);
//				// 去除ESB系统本身
//				if("ESB".equals(system.getSystemAb().toUpperCase())){
//					continue;
//				}
				// 数量都为0的系统不显示
				if(countServicesOfPID == 0 && countServicesOfCID == 0 && countOperationsOfPID ==0 && countOperationsOfCID ==0){
					continue;
				}
				SystemInvokeServiceInfo sysServiceInfo = new SystemInvokeServiceInfo();
				sysServiceInfo.setSystemName(system.getSystemName());
				sysServiceInfo.setSystemAB(system.getSystemAb());
				sysServiceInfo.setFirstPublishDate(system.getFirstPublishDate());
				sysServiceInfo.setSecondPublishDate(system.getSecondPublishDate());
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
	
	/**
	 * get First InvokeInfo by Ecode
	 * @param ecode
	 * @return
	 */
	public InvokeInfo getFirstInvokeByEcode(String ecode) {
		List<InvokeInfo> list = invokeInfoDAO.findBy("ecode", ecode);
		return list.get(0);
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
	@SuppressWarnings("unchecked")
	public int getId(Map param){
		InvokeInfo invokeInfo = invokeInfoDAO.findBy(param).get(0);
		return invokeInfo.getId();
	}
	@SuppressWarnings("unchecked")
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
		String state = params[6];
		String onlineVersion = params[7];
		String onlineDate = params[8];
		String sysId = params[9];
		String provideSysInfo = params[10];
		System system = systemManager.getSystemByAb(provideSysInfo);
		String newConsumerMsgType = params[11];
		String newThrough = "Y".equals(params[12])?"是":"否";
		String newDirection = params[13];
		log.info("newCsmMsg: " +newConsumerMsgType + ", newThrough: " + newThrough + ", newDirection: " + newDirection);
		boolean flag = false;
		try{
			int invokeId = invokeInfoDAO.getMaxId()+1;
			// 获取原始invokeInfo信息
			InvokeInfo invokeInfo = invokeInfoDAO.getFirstByEocdeAndOpeAndServiceId(serviceId, operationId, interfaceId);
			InvokeInfo csmInvokeInfo = new InvokeInfo();
			csmInvokeInfo.setId(invokeId);
			csmInvokeInfo.setConsumeMsgType(newConsumerMsgType);
			csmInvokeInfo.setConsumeSysId(sysId);
			csmInvokeInfo.setDirection(newDirection);
			csmInvokeInfo.setEcode(interfaceId);
			csmInvokeInfo.setFuncType(invokeInfo.getFuncType());
			// session user
			csmInvokeInfo.setModifyUser("");
			csmInvokeInfo.setOperationId(operationId);
			csmInvokeInfo.setPassbySysId(invokeInfo.getPassbySysId());
			csmInvokeInfo.setProvideMsgType(provideMsgType);
			csmInvokeInfo.setProvideSysId(system.getSystemId());
			csmInvokeInfo.setServiceId(serviceId);
			csmInvokeInfo.setSvcHeader(invokeInfo.getSvcHeader());
			csmInvokeInfo.setThrough(newThrough);
			csmInvokeInfo.setUpdateTime(new Timestamp(java.lang.System.currentTimeMillis()));
			invokeInfoDAO.save(csmInvokeInfo);
			
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
			List<InvokeInfo> tempList = null;
			if(interfaceId.indexOf(":") <0){
				tempList = invokeInfoDAO.findBy("ecode", interfaceId);
			}
			else{
				String[] strArr = interfaceId.split(":");
				Map<String,String> properties = new HashMap<String,String>();
				properties.put("serviceId", strArr[0]);
				properties.put("operationId", strArr[1]);
				properties.put("ecode", strArr[2]);
				properties.put("provideMsgType", strArr[3]);
				properties.put("consumeMsgType", strArr[4]);
				tempList = invokeInfoDAO.findBy(properties);
			}
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

	/**
	 * 删除invokeInfo某条记录信息.
	 * 情况1： 该条记录交易码还存在其他调用方，则删除invokeInfo，trans_state,publishInfo
	 * 情况2： 该记录不存在调用方，等同于删除一整条交易
	 * 需要删除IDA SDA history extends 等
	 */
	@Override
	public boolean delInvokebyConditions(String serviceId, String operationId,
			String interfaceId, String prdSysId, String csmSysId,
			String provideMsgType, String consumeMsgType) {
		try {
			InvokeInfo invokeInfo = invokeInfoDAO.getUniqueByConditions(
					serviceId, operationId, interfaceId, prdSysId, csmSysId,
					provideMsgType, consumeMsgType);
			// delete publish_info
			List<PublishInfo> delListPI = publishInfoDAO.findBy("iRid", invokeInfo.getId());
			publishInfoDAO.delete(delListPI);
			// delete trans_state
			TransState ts = tranInfoDAO.findUniqueBy("id", invokeInfo.getId());
			tranInfoDAO.delete(ts);
			// delete invokeInfo
			invokeInfoDAO.delInvokebyConditions(serviceId, operationId,
					interfaceId, prdSysId, csmSysId, provideMsgType,
					consumeMsgType);
			// check is exists invokeInfo by interfaceId; not exist delete other tables
			if(!invokeInfoDAO.checkExistByInterfaceId(interfaceId)){
				// delete interface and so
				idaPropDAO.delIdaPROPByInterfaceId(interfaceId);
				idaDAO.delIDAByInterfaceId(interfaceId);
				interfaceExtendsDAO.delByIntefaceId(interfaceId);
				interfaceDAO.delete(interfaceId);
			}
			// check serviceId is still used 
			if(!invokeInfoDAO.checkExistByServiceId(serviceId)){
				serviceHeadDAO.delByServiceId(serviceId);
				serviceDAO.delete(serviceId);
			}
			// check opeartionId and ServiceId is still used
			if(!invokeInfoDAO.checkExistByOperationIdAndServiceId(operationId, serviceId)){
				olaDAO.delByServiceIdAndOperationId(serviceId, operationId);
				olaHistoryDAO.delByServiceIdAndOperationId(serviceId, operationId);
				sdaDAO.deleteSDAByServiceAndOperationId(serviceId, operationId);
				sdaHistoryDAO.delByServiceIdAndOperationId(serviceId, operationId);
				slaDAO.delSlaByOperationIdAndServiceId(serviceId, operationId);
				slaHistoryDAO.delByOperationIdAndServiceId(serviceId, operationId);
				operationHistoryDAO.delByServiceIdAndOperationId(serviceId, operationId);
				operationDAO.delByServiceIdAndOperationId(serviceId, operationId);
			}
		} catch (Exception e) {
			log.error("删除InvokeInfo信息失败!", e);
			return false;
		}
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkExistByInterfaceId(String interfaceId) {
		// TODO Auto-generated method stub
		return invokeInfoDAO.checkExistByInterfaceId(interfaceId);
	}
	
	/**
	 * 根据多个接口ID获取 具有两条以上导出配置的invokeInfo信息
	 */
	public List<InvokeInfo> getBatchDuplicateInvokeByEcode(String[] interfaceIds) {
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
		returnList = BatchChoiceInvokeList(returnList);
		return returnList;
	}
	
    /**
     * 获取InvokeInfo中serviceId,operationId,ecode相同并存在两条记录的操作
     * @param targetList
     * @param sourceList
     * @return
     */
	public List<InvokeInfo> BatchChoiceInvokeList(List<InvokeInfo> targetList) {
		List<InvokeInfo> batchChoiceList = new ArrayList<InvokeInfo>();
		for(int i=0; i<targetList.size(); i++){
			boolean tmpFlag = false;
			InvokeInfo tmpFirst = targetList.get(i);
			for(int j=i+1; j<targetList.size(); j++){
				InvokeInfo tempSecond = targetList.get(j);
				if(tmpFirst.getServiceId().equals(tempSecond.getServiceId())
						&& tmpFirst.getOperationId().equals(tempSecond.getOperationId())
						&& tmpFirst.getEcode().equals(tempSecond.getEcode())){
					tmpFlag = true;
					batchChoiceList.add(tempSecond);
				}
			}
			if(tmpFlag){
				batchChoiceList.add(tmpFirst);
			}
		}
		return batchChoiceList;
	}
}
