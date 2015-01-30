package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.InvokeInfoDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SystemDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.entity.System;
import com.dc.esb.servicegov.refactoring.service.InvokeInfoManager;
import com.dc.esb.servicegov.refactoring.service.SystemManager;
import com.dc.esb.servicegov.refactoring.vo.ServiceDevProcessInfoBean;
import com.dc.esb.servicegov.refactoring.vo.ServiceDevProgressVO;
import com.dc.esb.servicegov.refactoring.vo.SystemLine;
import com.dc.esb.servicegov.refactoring.vo.SystemNode;
import com.dc.esb.servicegov.refactoring.vo.SystemTopology;

@Service
@Transactional
public class SystemManagerImpl implements SystemManager{
	
	private Log log = LogFactory.getLog(SystemManagerImpl.class);
	@Autowired
	private SystemDAOImpl systemDAO;
	@Autowired
	private InvokeInfoDAOImpl invokeInfoDAO;
	@Autowired
	private InvokeInfoManager invokeInfoManager;
	
	@Override
	public List<System> getAllSystems() {
		List<System> systems = new ArrayList<System>();
		systems = systemDAO.getAll();
		return systems;
	}
	
	@SuppressWarnings("unchecked")
	public List<ServiceDevProgressVO> getSeviceDevInfo(String[] sysAbs) {
		StringBuffer sql = new StringBuffer(" select k.* ,s.sys_ab,s.sys_name from (select count(distinct(concat(operation_id,service_id))) as amount,provide_sys_id,versionst  from "
                     +" (select r.service_id ,r.operation_id,provide_sys_id,versionst  from invoke_relation r left join trans_state  t on " 
                     + " r.id =t.id left join Service s on r.service_id = s.service_id where concat(r.operation_id,r.service_id) " +
                     		"in (select concat(operation_id,service_id) from SG_OPERATION where AUDITSTATE='2') and s.AUDITSTATE = '2'" 
                     +" group by r.service_id,r.operation_id,provide_sys_id,versionst) t "
                     +" group by t.provide_sys_id,t.versionst) k left join sg_system s on k.provide_sys_id = s.sys_id");
		
		if (sysAbs != null) {
			if (sysAbs.length == 1) {
				sql.append(" where s.sys_ab = " + "'" + sysAbs[0] + "'");
			} else {
				sql.append(" where s.sys_ab in (");
				for (int i=0; i<sysAbs.length - 1; i++) {
					sql.append("'" + sysAbs[i] + "'" +  ",");
				}
				sql.append("'" + sysAbs[sysAbs.length-1]+ "'" + ")");
			}
		}
		
		sql.append(" order by sys_ab asc");
		
		Session session = systemDAO.getSession();
		Query q = session.createSQLQuery(sql.toString());
		q.setResultTransformer(Transformers.aliasToBean(ServiceDevProcessInfoBean.class));
		List<ServiceDevProcessInfoBean> lst = q.list();
		Map<String, ServiceDevProgressVO> map = new HashMap<String, ServiceDevProgressVO>();
		Iterator<ServiceDevProcessInfoBean> it = lst.iterator();
		while(it.hasNext()) {
			ServiceDevProcessInfoBean bean = it.next();
			String id = bean.getPROVIDE_SYS_ID();
			ServiceDevProgressVO vo = map.get(id);
			if (vo == null) {
				vo = new ServiceDevProgressVO(bean);
				map.put(id, vo);
			}
			vo.updateAccount(bean);
		}
		
		List<ServiceDevProgressVO> result = new ArrayList<ServiceDevProgressVO>();
		
		Iterator<Map.Entry<String, ServiceDevProgressVO>> it2 = map
			.entrySet().iterator();
		while(it2.hasNext()) {
			Map.Entry<String, ServiceDevProgressVO> e = it2.next();
			result.add(e.getValue());
		}
		return result;
	}

	@Override
	public void delSystemById(String id) {
		// TODO Auto-generated method stub
		if(log.isInfoEnabled()){
			log.info("delete system by id !");
		}
		systemDAO.delete(id);
	}
	/**
	 * 批量删除记录
	 * @param idArr
	 */
	@Override
	public void batchDelSystem(String[] idArr){
		List<System> list = new ArrayList<System>();
		for(String id : idArr){
		    System  system = this.getSystemById(id);
		    list.add(system);
		}
		systemDAO.batchDelete(list);
	}

	@Override
	public void insertOrUpdate(System system) {
		// TODO Auto-generated method stub
		if(log.isInfoEnabled()){
			log.info("insert or update  system by id !");
		}
		systemDAO.save(system);
	}

	@Override
	public System getSystemById(String id) {
		// TODO Auto-generated method stub
		System system = systemDAO.findUniqueBy("systemId", id);
		if(system == null){
			system = new System();
		}
		return system;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isSystemUsed(String id) {
		// TODO Auto-generated method stub
		List<InvokeInfo> prdlist = invokeInfoDAO.findBy("provideSysId", id);
		if(prdlist != null && prdlist.size()>0){
			return true;
		}
		List<InvokeInfo> csmlist = invokeInfoDAO.findBy("consumeSysId", id);
		if(csmlist != null && csmlist.size()>0){
			return true;
		}
		return false;
	}
	public System getSystemByAb(String systemAb) {
		System system = systemDAO.findUniqueBy("systemAb", systemAb);
		if(system == null){
			system = new System();
		}
		return system;
	}
	public SystemTopology getSystemTopology(String sysId,String sysType){
		SystemTopology systemTopology = new SystemTopology();
		List<SystemNode> nodeList = new ArrayList<SystemNode>();
		List<SystemLine> lineList = new ArrayList<SystemLine>();
		int locationX = 700;
		int locationY = 50;	
		SystemNode esbNode = new SystemNode();
		esbNode.setNodeName("ESB");
		esbNode.setNodeLabel("ESB");
		esbNode.setNodeType("ESB");
		esbNode.setNodeX("500");
		esbNode.setNodeDesc("企业服务总线");
		if("1".equals(sysType)){
			System provideSys = this.getSystemById(sysId);
			SystemNode provideNode = new SystemNode();
			provideNode.setNodeName(provideSys.getSystemAb());
			provideNode.setNodeLabel(provideSys.getSystemAb());
			provideNode.setNodeType("provide");
			provideNode.setNodeX("300");
			provideNode.setNodeDesc(provideSys.getSystemName());
			
			SystemLine provideLine = new SystemLine();
			provideLine.setFromName(esbNode.getNodeName());
			provideLine.setToName(provideNode.getNodeName());
			provideLine.setDesc(esbNode.getNodeLabel()+"调用"+provideNode.getNodeLabel());
			lineList.add(provideLine);
			
			List<InvokeInfo> invokeInfoList = invokeInfoManager.getInvokeInfoByPSys(sysId);
			List<String> consumeSysIdList = new ArrayList<String>();
			for(InvokeInfo invokeInfo:invokeInfoList){
				if(!consumeSysIdList.contains(invokeInfo.getConsumeSysId())){
					consumeSysIdList.add(invokeInfo.getConsumeSysId());
				}
			}
			int count = 1;
			int begin = locationY;
			int end = 0;
			for(String consumeId:consumeSysIdList){
				if(consumeId==null||"".equals(consumeId)){
					continue;
				}
				System consumeSys = this.getSystemById(consumeId);
				SystemNode consumeNode = new SystemNode();
				consumeNode.setNodeName(consumeSys.getSystemAb());
				consumeNode.setNodeLabel(consumeSys.getSystemAb());
				consumeNode.setNodeType("consume");
				consumeNode.setNodeX(locationX+"");
				consumeNode.setNodeY((locationY+(count-1)*100)+"");
				consumeNode.setNodeDesc(consumeSys.getSystemName());
				if(!consumeNode.getNodeName().equals(provideNode.getNodeName()) && !consumeNode.getNodeName().equals(esbNode.getNodeName())){
					if(!nodeList.contains(consumeNode)){
						nodeList.add(consumeNode);
					}
				}else{
					consumeNode.setNodeName(consumeNode.getNodeName()+count);
					nodeList.add(consumeNode);
				}
				
				String system = getPassedBySystem(provideNode, consumeNode);
				SystemLine consumeLine = new SystemLine();
				consumeLine.setFromName(consumeNode.getNodeName());
				consumeLine.setToName(esbNode.getNodeName());
				if(null!=system&&!"".equals(system)){
					consumeLine.setDesc(consumeNode.getNodeLabel()+"经过"+system+"调用"+esbNode.getNodeLabel());
				}else{
					consumeLine.setDesc(consumeNode.getNodeLabel()+"调用"+esbNode.getNodeLabel());
				}
				
				lineList.add(consumeLine);
			
				end = (locationY+(count-1)*100);
				count++;
			}
			int middle = (begin + end)/2;
			esbNode.setNodeY(middle+"");
			nodeList.add(esbNode);
			provideNode.setNodeY(middle+"");
			nodeList.add(provideNode);
		}else{
			System consumeSys = this.getSystemById(sysId);
			SystemNode consumeNode = new SystemNode();
			consumeNode.setNodeName(consumeSys.getSystemAb());
			consumeNode.setNodeLabel(consumeSys.getSystemAb());
			consumeNode.setNodeType("consume");
			consumeNode.setNodeX("300");
			consumeNode.setNodeDesc(consumeSys.getSystemName());
			
			SystemLine consumeLine = new SystemLine();
			consumeLine.setFromName(consumeNode.getNodeName());
			consumeLine.setToName(esbNode.getNodeName());
			consumeLine.setDesc(consumeNode.getNodeName()+"调用"+esbNode.getNodeName());
			lineList.add(consumeLine);
			
			List<InvokeInfo> invokeInfoList = invokeInfoManager.getInvokeInfoByCSys(sysId);
			List<String> provideSysIdList = new ArrayList<String>();
			for(InvokeInfo invokeInfo:invokeInfoList){
				if(!provideSysIdList.contains(invokeInfo.getProvideSysId())){
					provideSysIdList.add(invokeInfo.getProvideSysId());
				}
			}
			int count = 1;
			int begin = locationY;
			int end = 0;
			for(String provideId:provideSysIdList){
				if(provideId==null||"".equals(provideId)){
					continue;
				}
				System provideSys = this.getSystemById(provideId);
				SystemNode provideNode = new SystemNode();
				provideNode.setNodeName(provideSys.getSystemAb());
				provideNode.setNodeLabel(provideSys.getSystemAb());
				provideNode.setNodeType("provide");
				provideNode.setNodeX(locationX+"");
				provideNode.setNodeY((locationY+100*(count-1))+"");
				provideNode.setNodeDesc(provideSys.getSystemName());
				if(!provideNode.getNodeName().equals(consumeNode.getNodeName()) && !provideNode.getNodeName().equals(esbNode.getNodeName())){
					if(!nodeList.contains(provideNode)){
						nodeList.add(provideNode);
					}
				}else{
					provideNode.setNodeName(provideNode.getNodeName()+count);
					nodeList.add(provideNode);
				}
				
				String system = getPassedBySystem(provideNode, consumeNode);
				SystemLine provideLine = new SystemLine();
				provideLine.setFromName(esbNode.getNodeName());
				provideLine.setToName(provideNode.getNodeName());
				if(null!=system&&!"".equals(system)){
					provideLine.setDesc(esbNode.getNodeName()+"经过"+system+"调用"+provideNode.getNodeName());
				}else{
					provideLine.setDesc(esbNode.getNodeName()+"调用"+provideNode.getNodeName());
				}
				lineList.add(provideLine);
				end = (locationY+(count-1)*100);
				count++;
			}
			int middle = (begin + end)/2;
			esbNode.setNodeY(middle+"");
			nodeList.add(esbNode);
			consumeNode.setNodeY(middle+"");
			nodeList.add(consumeNode);
		}
		systemTopology.setSystemnodeList(nodeList);
		systemTopology.setSystemlineList(lineList);
		return systemTopology;
	}
	public String getPassedBySystem(SystemNode pnode,SystemNode cnode){
		System psys = this.getSystemByAb(pnode.getNodeName());
		System csys = this.getSystemByAb(cnode.getNodeName());
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("provideSysId", psys.getSystemId());
		paramMap.put("consumeSysId", csys.getSystemId());
		if(invokeInfoDAO.findBy(paramMap).size()>=1){
			InvokeInfo invokeinfo = invokeInfoDAO.findBy(paramMap).get(0);
			return invokeinfo.getPassbySysId();
		}else{
			return null;
		}

	}

	@Override
	public List<System> getSystemsByConditons(Map<String,String> mapConditions) {
		// TODO Auto-generated method stub
		String sysAb = (mapConditions.get("sysAb") == null)?"":mapConditions.get("sysAb");
		String firstPublishDate = (mapConditions.get("publishDate_prd") == null)?"":mapConditions.get("publishDate_prd");
		String secondPublishDate = (mapConditions.get("publishDate_csm") == null)?"":mapConditions.get("publishDate_csm");
		if("".equals(sysAb) && "".equals(firstPublishDate) && "".equals(secondPublishDate)){
			return systemDAO.getAll();
		}
		else{
			Map<String,String> props = new HashMap<String,String>();
			if(!"".equals(sysAb)){
				props.put("systemAb", sysAb);
			}
			if(!"".equals(firstPublishDate)){
				props.put("firstPublishDate", firstPublishDate);
			}
			if(!"".equals(secondPublishDate)){
				props.put("secondPublishDate", secondPublishDate);
			}
			return systemDAO.findBy(props);
		}
	}
}
