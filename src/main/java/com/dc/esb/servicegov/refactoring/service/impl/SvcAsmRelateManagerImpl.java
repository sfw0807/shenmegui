package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.SvcAsmRelateViewDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.SvcAsmRelateView;
import com.dc.esb.servicegov.refactoring.service.SvcAsmRelateManager;
import com.dc.esb.servicegov.refactoring.vo.SvcAsmRelateInfoVO;

@Service
@Transactional
public class SvcAsmRelateManagerImpl implements SvcAsmRelateManager {
	private Log log = LogFactory.getLog(super.getClass());

	@Autowired
	private SvcAsmRelateViewDAOImpl svcAsmRelateViewDAOImpl;

	/**
	 * 获取全部服务接口信息...
	 * 
	 */
	@Override
	public List<SvcAsmRelateInfoVO> getAllSvcAsmRelateInfo() {
		if (this.log.isInfoEnabled())
			this.log.info("获取全部服务接口信息...");

		List<SvcAsmRelateInfoVO> returnList = new ArrayList<SvcAsmRelateInfoVO>();
		List<SvcAsmRelateView> infos = svcAsmRelateViewDAOImpl.getAll();
		if(infos != null && infos.size() > 0){
			for(SvcAsmRelateView rv : infos){
				SvcAsmRelateInfoVO vo = new SvcAsmRelateInfoVO();
				vo.setServiceInfo(rv.getServiceId()+"/"+rv.getServiceName());
				vo.setOperationInfo(rv.getOperationId()+"/"+rv.getOperationName());
				vo.setInterfaceInfo(rv.getInterfaceId()+"/"+rv.getInterfaceName());
				if(rv.getPassbySysAB() != null && !"".equals(rv.getPassbySysAB()) && !"null".equals(rv.getPassbySysAB())){
					vo.setConsumeSysInfo(rv.getCsmSysAB()+"/"+rv.getCsmSysName());
				    vo.setPassbySysInfo(rv.getPassbySysAB()+"/"+rv.getPassbySysName());
				}
				else{
					vo.setPassbySysInfo(rv.getCsmSysAB()+"/"+rv.getCsmSysName());
				}
				vo.setProvideSysInfo(rv.getPrdSysAB()+"/"+rv.getPrdSysName());
				vo.setConsumeMsgType(rv.getConsumeMsgType());
				vo.setProvideMsgType(rv.getProvideMsgType());
				vo.setThrough(rv.getThrough()==null?"":rv.getThrough());
				vo.setOnlineDate(rv.getOnlineDate()==null?"":rv.getOnlineDate());
				vo.setState(rv.getState()==null?"":rv.getState());
				vo.setField(rv.getField()==null?"":rv.getField());
				vo.setOnlineVersion(rv.getOnlineVersion()==null?"":rv.getOnlineVersion());
				if(rv.getOnlineVersion()!=null && !"".equals(rv.getOnlineVersion())){
					vo.setModifyTimes(String.valueOf(Integer.parseInt(rv.getOnlineVersion())-1));
				}else{
					vo.setModifyTimes("");
				}
				returnList.add(vo);
			}
		}
		return returnList;
	}

	/**
	 * 根据查询条件查询出服务接口信息...
	 * 
	 */
	@Override
	public List<SvcAsmRelateInfoVO> getInfosByConditions(Map<String,String> mapConditions) {
		// TODO Auto-generated method stub
		if (this.log.isInfoEnabled())
			this.log.info("根据查询条件查询出服务接口信息...");
		
		List<SvcAsmRelateInfoVO> returnList = new ArrayList<SvcAsmRelateInfoVO>();
		List<SvcAsmRelateView> searchList = svcAsmRelateViewDAOImpl.getInfosByConditions(mapConditions);
		if(searchList != null && searchList.size() > 0){
			for(SvcAsmRelateView rv : searchList){
				SvcAsmRelateInfoVO vo = new SvcAsmRelateInfoVO();
				vo.setServiceInfo(rv.getServiceId()+"/"+rv.getServiceName());
				vo.setOperationInfo(rv.getOperationId()+"/"+rv.getOperationName());
				vo.setInterfaceInfo(rv.getInterfaceId()+"/"+rv.getInterfaceName());
				vo.setConsumeSysInfo(rv.getCsmSysAB()+"/"+rv.getCsmSysName());
				if(rv.getPassbySysAB() != null && !"".equals(rv.getPassbySysAB()) && !"null".equals(rv.getPassbySysAB())){
					vo.setConsumeSysInfo(rv.getCsmSysAB()+"/"+rv.getCsmSysName());
				    vo.setPassbySysInfo(rv.getPassbySysAB()+"/"+rv.getPassbySysName());
				}
				else{
					vo.setPassbySysInfo(rv.getCsmSysAB()+"/"+rv.getCsmSysName());
				}
				vo.setProvideSysInfo(rv.getPrdSysAB()+"/"+rv.getPrdSysName());
				vo.setConsumeMsgType(rv.getConsumeMsgType());
				vo.setProvideMsgType(rv.getProvideMsgType());
				vo.setThrough(rv.getThrough()==null?"":rv.getThrough());
				vo.setOnlineDate(rv.getOnlineDate()==null?"":rv.getOnlineDate());
				vo.setState(rv.getState()==null?"":rv.getState());
				vo.setField(rv.getField()==null?"":rv.getField());
				vo.setOnlineVersion(rv.getOnlineVersion()==null?"":rv.getOnlineVersion());
				if(rv.getOnlineVersion()!=null && !"".equals(rv.getOnlineVersion())){
					vo.setModifyTimes(String.valueOf(Integer.parseInt(rv.getOnlineVersion())-1));
				}else{
					vo.setModifyTimes("");
				}
				returnList.add(vo);
			}
		}
		return returnList;
	}

	/**
	 * 获取全部服务详细信息...
	 * 
	 */
	@Override
	public List<SvcAsmRelateInfoVO> getAllServiceDetailsInfo() {
		// TODO Auto-generated method stub
		if (this.log.isInfoEnabled())
			this.log.info("获取全部服务详细信息...");

		List<SvcAsmRelateInfoVO> returnList = new ArrayList<SvcAsmRelateInfoVO>();
		List<SvcAsmRelateView> infos = svcAsmRelateViewDAOImpl.getAll();
		if(infos != null && infos.size() > 0){
			for(SvcAsmRelateView rv : infos){
				SvcAsmRelateInfoVO vo = new SvcAsmRelateInfoVO();
				vo.setServiceInfo(rv.getServiceId()+"/"+rv.getServiceName());
				vo.setOperationInfo(rv.getOperationId()+"/"+rv.getOperationName());
				vo.setInterfaceInfo(rv.getInterfaceId()+"/"+rv.getInterfaceName());
				vo.setConsumeSysInfo(rv.getCsmSysAB()+"/"+rv.getCsmSysName());
				if(rv.getPassbySysAB() != null && !"".equals(rv.getPassbySysAB()) && !"null".equals(rv.getPassbySysAB())){
				       vo.setPassbySysInfo(rv.getPassbySysAB()+"/"+rv.getPassbySysName());
				}
				vo.setProvideSysInfo(rv.getPrdSysAB()+"/"+rv.getPrdSysName());
				vo.setConsumeMsgType(rv.getConsumeMsgType());
				vo.setProvideMsgType(rv.getProvideMsgType());
				vo.setThrough(rv.getThrough()==null?"":rv.getThrough());
				vo.setOnlineDate(rv.getOnlineDate()==null?"":rv.getOnlineDate());
				vo.setState(rv.getState()==null?"":rv.getState());
				vo.setField(rv.getField()==null?"":rv.getField());
				vo.setOnlineVersion(rv.getOnlineVersion()==null?"":rv.getOnlineVersion());
				if(rv.getOnlineVersion()!=null && !"".equals(rv.getOnlineVersion())){
					vo.setModifyTimes(String.valueOf(Integer.parseInt(rv.getOnlineVersion())-1));
				}else{
					vo.setModifyTimes("");
				}
				//  merge results,consumerId is different
				boolean flag = true;
				for(SvcAsmRelateInfoVO tempVo : returnList){
					if(tempVo.equals(vo)){
						flag = false;
						break;
					}
				}
				if(flag){
				returnList.add(vo);
				}
			}
		}
		return returnList;
	}

	/**
	 * 根据查询条件查询出服务详细信息...
	 * 
	 */
	@Override
	public List<SvcAsmRelateInfoVO> getServiceDetailsInfoByConditions(
			Map<String, String> mapConditions) {
		if (this.log.isInfoEnabled())
		this.log.info("根据查询条件查询出服务详细信息...");
	
	List<SvcAsmRelateInfoVO> returnList = new ArrayList<SvcAsmRelateInfoVO>();
	List<SvcAsmRelateView> searchList = svcAsmRelateViewDAOImpl.getInfosByConditions(mapConditions);
	if(searchList != null && searchList.size() > 0){
		for(SvcAsmRelateView rv : searchList){
			SvcAsmRelateInfoVO vo = new SvcAsmRelateInfoVO();
			vo.setServiceInfo(rv.getServiceId()+"/"+rv.getServiceName());
			vo.setOperationInfo(rv.getOperationId()+"/"+rv.getOperationName());
			vo.setInterfaceInfo(rv.getInterfaceId()+"/"+rv.getInterfaceName());
			vo.setConsumeSysInfo(rv.getCsmSysAB()+"/"+rv.getCsmSysName());
			if(rv.getPassbySysAB() != null && !"".equals(rv.getPassbySysAB()) && !"null".equals(rv.getPassbySysAB())){
			       vo.setPassbySysInfo(rv.getPassbySysAB()+"/"+rv.getPassbySysName());
			}
			vo.setProvideSysInfo(rv.getPrdSysAB()+"/"+rv.getPrdSysName());
			vo.setConsumeMsgType(rv.getConsumeMsgType());
			vo.setProvideMsgType(rv.getProvideMsgType());
			vo.setThrough(rv.getThrough()==null?"":rv.getThrough());
			vo.setOnlineDate(rv.getOnlineDate()==null?"":rv.getOnlineDate());
			vo.setState(rv.getState()==null?"":rv.getState());
			vo.setField(rv.getField()==null?"":rv.getField());
			vo.setOnlineVersion(rv.getOnlineVersion()==null?"":rv.getOnlineVersion());
			if(rv.getOnlineVersion()!=null && !"".equals(rv.getOnlineVersion())){
				vo.setModifyTimes(String.valueOf(Integer.parseInt(rv.getOnlineVersion())-1));
			}else{
				vo.setModifyTimes("");
			}
		    //  merge results if only consumerId is different
			boolean flag = true;
			for(SvcAsmRelateInfoVO tempVo : returnList){
				if(tempVo.equals(vo)){
					flag = false;
					break;
				}
			}
			if(flag){
			returnList.add(vo);
			}
		}
	}
	return returnList;
	}

	/**
	 * 获取全部导出配置的接口信息
	 */
	@Override
	public List<SvcAsmRelateInfoVO> getAllExportInvokeInfos() {
		// TODO Auto-generated method stub
		if (this.log.isInfoEnabled())
			this.log.info("获取全部导出配置的接口信息...");

		List<SvcAsmRelateInfoVO> returnList = new ArrayList<SvcAsmRelateInfoVO>();
		List<SvcAsmRelateView> infos = svcAsmRelateViewDAOImpl.getAll();
		if(infos != null && infos.size() > 0){
			for(SvcAsmRelateView rv : infos){
				SvcAsmRelateInfoVO vo = new SvcAsmRelateInfoVO();
				vo.setServiceInfo(rv.getServiceId()+"/"+rv.getServiceName());
				vo.setOperationInfo(rv.getOperationId()+"/"+rv.getOperationName());
				vo.setInterfaceInfo(rv.getInterfaceId()+"/"+rv.getInterfaceName());
				if(rv.getPassbySysAB() != null && !"".equals(rv.getPassbySysAB()) && !"null".equals(rv.getPassbySysAB())){
					vo.setConsumeSysInfo(rv.getCsmSysAB());
				    vo.setPassbySysInfo(rv.getPassbySysAB());
				}
				else{
					vo.setPassbySysInfo(rv.getCsmSysAB());
				}
				vo.setProvideSysInfo(rv.getPrdSysAB()+"/"+rv.getPrdSysName());
				vo.setConsumeMsgType(rv.getConsumeMsgType());
				vo.setProvideMsgType(rv.getProvideMsgType());
				vo.setDirection(rv.getDirection());
				vo.setThrough(rv.getThrough()==null?"":rv.getThrough());
				vo.setOnlineDate(rv.getOnlineDate()==null?"":rv.getOnlineDate());
				vo.setState(rv.getState()==null?"":rv.getState());
				vo.setField(rv.getField()==null?"":rv.getField());
				vo.setOnlineVersion(rv.getOnlineVersion()==null?"":rv.getOnlineVersion());
				if(rv.getOnlineVersion()!=null && !"".equals(rv.getOnlineVersion())){
					vo.setModifyTimes(String.valueOf(Integer.parseInt(rv.getOnlineVersion())-1));
				}else{
					vo.setModifyTimes("");
				}
				//  merge results,consumerId is different
				boolean flag = true;
				for(SvcAsmRelateInfoVO tempVo : returnList){
					if(tempVo.equals(vo)){
						// 合并调用方和经由系统
						if(tempVo.getConsumeSysInfo() != null && vo.getConsumeSysInfo() != null 
								&& !"".equals(vo.getConsumeSysInfo())){
							if(!tempVo.getConsumeSysInfo().contains(vo.getConsumeSysInfo())){
								tempVo.setConsumeSysInfo(tempVo.getConsumeSysInfo()+"、"+vo.getConsumeSysInfo());
							}
						}
						if(tempVo.getPassbySysInfo() != null && vo.getPassbySysInfo() != null
								&& !"".equals(vo.getPassbySysInfo())){
							if(!tempVo.getPassbySysInfo().contains(vo.getPassbySysInfo())){
								tempVo.setPassbySysInfo(tempVo.getPassbySysInfo()+"、"+vo.getPassbySysInfo());
							}
						}
						flag = false;
						break;
					}
				}
				if(flag){
				returnList.add(vo);
				}
			}
		}
		return returnList;
	}
}