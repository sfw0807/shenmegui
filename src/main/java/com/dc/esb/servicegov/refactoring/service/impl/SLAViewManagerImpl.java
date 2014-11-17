package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.SLAViewDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.SLAView;
import com.dc.esb.servicegov.refactoring.service.SLAViewManager;
import com.dc.esb.servicegov.refactoring.vo.SLAViewInfoVO;

@Service
@Transactional
public class SLAViewManagerImpl implements SLAViewManager {
	private Log log = LogFactory.getLog(super.getClass());
	@Autowired
	private SLAViewDAOImpl slaViewDAO;
	
	public List<SLAView> getAllSLAInfo() {
		if(log.isInfoEnabled()){
			log.info("获取全部服务SLA信息...");
		}
		List<SLAView> list = new ArrayList<SLAView>();
		try{
			list = slaViewDAO.getAll();
		}catch(Exception e){
			log.error("获取全部服务SLA信息失败", e);
		}
		return list;
	}

	public List<SLAViewInfoVO> getExportInfo(Map<String, String> conditions) {
		if(log.isInfoEnabled()){
			log.info("获取导出服务SLA信息...");
		}
		List<SLAViewInfoVO> returnList = new ArrayList<SLAViewInfoVO>();
		List<SLAView> slaViewList = new ArrayList<SLAView>();
		try{
			slaViewList = slaViewDAO.getInfosByConditions(conditions);
		}catch(Exception e){
			log.error("获取全部服务SLA信息失败", e);
		}
		
		if(slaViewList.size()>0 && null!=slaViewList){
			for(SLAView slaView : slaViewList){
				SLAViewInfoVO slaViewInfoVO = new SLAViewInfoVO();
				slaViewInfoVO.setServiceInfo(slaView.getServiceId()+"/"+slaView.getServiceName());
				slaViewInfoVO.setOperationInfo(slaView.getOperationId()+"/"+slaView.getOperationName());
				slaViewInfoVO.setInterfaceInfo(slaView.getInterfaceId()+"/"+slaView.getInterfaceName());
				slaViewInfoVO.setProvideSysInfo(slaView.getSysAB()+"/"+slaView.getSysName());
				slaViewInfoVO.setAverageTime(slaView.getAverageTime());
				slaViewInfoVO.setCurrentCount(slaView.getCurrentCount());
				slaViewInfoVO.setSuccessRate(slaView.getSuccessRate());
				slaViewInfoVO.setTimeOut(slaView.getTimeOut());
				slaViewInfoVO.setSlaRemark(slaView.getSlaRemark());
				returnList.add(slaViewInfoVO);
			}
		}
		return returnList;
	}	
}
