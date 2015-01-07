package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.PublishInfoDAOImpl;
import com.dc.esb.servicegov.refactoring.service.PublishInfoManager;
import com.dc.esb.servicegov.refactoring.vo.PublishInfoVO;
import com.dc.esb.servicegov.refactoring.vo.SvcAsmRelateInfoVO;

@Component
@Transactional
public class PublishInfoManagerImpl implements PublishInfoManager {
	
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private PublishInfoDAOImpl publishInfoDAO;

	@Override
	public List<PublishInfoVO> getAllPublishTotalInfos(Map<String,String> mapConditions) {
		// TODO Auto-generated method stub
		List<PublishInfoVO> infos = publishInfoDAO.getAllPublishTotalInfos(mapConditions);
		String tempDate = "";
		for(PublishInfoVO vo : infos){
			tempDate = vo.getONLINEDATE();
			vo.setCOUNTOFADDSERVICES(publishInfoDAO.getCountOfAddServices(tempDate,mapConditions));
			vo.setCOUNOFMODIFYSERVICES(publishInfoDAO.getCountOfModifyServices(tempDate,mapConditions));
			vo.setCOUNTOFADDOPERATIONS(publishInfoDAO.getCountOfAddOperations(tempDate,mapConditions));
			vo.setCOUNTOFMODIFYOPERATIONS(publishInfoDAO.getCountOfModifyOperations(tempDate,mapConditions));
		}
		return  infos;
	}

	
	public List<SvcAsmRelateInfoVO> getAllExportDatasByOnlineDate(String onlineDate) {
		List<Map<String,String>> mapList = publishInfoDAO.getAllExportDatasByOnlineDate(onlineDate);
		List<SvcAsmRelateInfoVO> results = new ArrayList<SvcAsmRelateInfoVO>();
		for(Map<String,String> map : mapList){
			SvcAsmRelateInfoVO vo = new SvcAsmRelateInfoVO();
			vo.setServiceInfo(map.get("SERVICE_ID") + "/" + map.get("SERVICE_NAME"));
			vo.setOperationInfo(map.get("OPERATION_ID") + "/" + map.get("OPERATION_NAME"));
			vo.setInterfaceInfo(map.get("ECODE") + "/" + map.get("INTERFACE_NAME"));
			vo.setProvideSysInfo(map.get("PRDSYSAB") + "/" + map.get("PRDSYSNAME"));
			if(map.get("PASSBYSYSAB") == null || "".equals(map.get("PASSBYSYSAB"))){
				vo.setPassbySysInfo("");
			}
			else{
			    vo.setPassbySysInfo(map.get("PASSBYSYSAB") + "/" + map.get("PASSBYSYSNAME"));
			}
			vo.setConsumeSysInfo(map.get("CSMSYSAB") + "/" + map.get("CSMSYSNAME"));
			vo.setProvideMsgType(map.get("PROVIDE_MSG_TYPE"));
			vo.setConsumeMsgType(map.get("CONSUME_MSG_TYPE"));
			vo.setOnlineDate(onlineDate);
			vo.setState("上线");
			vo.setOnlineVersion(map.get("INTERFACE_VERSION"));
			vo.setModifyTimes(String.valueOf(Integer.parseInt(map.get("INTERFACE_VERSION"))-1));
			vo.setThrough(map.get("THROUGH"));
			vo.setField(map.get("FIELD"));
			
			// 合并重复VO。consumerID合并成一列
			boolean flag = true ;
			
			for(SvcAsmRelateInfoVO tempVo : results){
				if(tempVo.equals(vo)){
					flag = false;
					if(tempVo.getConsumeSysInfo().indexOf("、") < 0){
					   String sourceConsumer = tempVo.getConsumeSysInfo().substring(0,tempVo.getConsumeSysInfo().indexOf("/"));
					   tempVo.setConsumeSysInfo(sourceConsumer + "、" + map.get("CSMSYSAB"));
					}else{
					   tempVo.setConsumeSysInfo(tempVo.getConsumeSysInfo() + "、" + map.get("CSMSYSAB"));
					}
					break;
				}
			}
			if(flag){
			results.add(vo);
			}
		}
		// TODO Auto-generated method stub
		return results;
	}

	

}
