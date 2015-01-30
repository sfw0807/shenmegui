package com.dc.esb.servicegov.refactoring.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.refactoring.entity.ProtocolInfo;
import com.dc.esb.servicegov.refactoring.entity.System;
import com.dc.esb.servicegov.refactoring.service.impl.ProtocolInfoManagerImpl;
import com.dc.esb.servicegov.refactoring.service.impl.SystemManagerImpl;
import com.dc.esb.servicegov.refactoring.vo.SystemLine;
import com.dc.esb.servicegov.refactoring.vo.SystemNode;
import com.dc.esb.servicegov.refactoring.vo.SystemTopology;

@Controller
@RequestMapping("/systemInfo")
public class SystemInfoController {
	
	private Log log = LogFactory.getLog(SystemInfoController.class);
	
	@Autowired
	private SystemManagerImpl systemManager;
	@Autowired
	private ProtocolInfoManagerImpl protocolManager;
	
	@RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
	public @ResponseBody List<System> getSystemServiceInvokeInfo(){

		return systemManager.getAllSystems();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/delete/{id}", headers = "Accept=application/json")
	public @ResponseBody boolean delete(@PathVariable String id){
		boolean flag = true;
		try{
			if(id.contains(",")){
				String[] idArr = id.split(",");
//				int i =0;
//				while(i<idArr.length){
//					systemManager.delSystemById(idArr[i]);
//					i++;
//				}
				systemManager.batchDelSystem(idArr);
				// 删除系统协议信息
				protocolManager.batchByIdArr(idArr);
			}
			else{
				systemManager.delSystemById(id);
				protocolManager.delBySysId(id);
			}
		}catch(Exception e){
			log.error("delete system"+id+"failed!", e);
			flag = false;
		}
		return flag;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/insert", headers = "Accept=application/json")
	public @ResponseBody boolean insert(@RequestBody System system){
		boolean flag = true;
		try{
			// 先删除系统下的协议信息
			protocolManager.delBySysId(system.getSystemId());
		    systemManager.insertOrUpdate(system);
		}catch(Exception e){
			log.error("insert system"+system.getSystemId()+"failed!", e);
			flag = false;
		}
		return flag;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/update", headers = "Accept=application/json")
	public @ResponseBody boolean update(@RequestBody System system){
		boolean flag = true;
		try{
			// 先删除系统下的协议信息
			 protocolManager.delBySysId(system.getSystemId());
		     systemManager.insertOrUpdate(system);
		}catch(Exception e){
			log.error("update system"+system.getSystemId()+"failed!", e);
			flag = false;
		}
		return flag;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getSystemById/{id}", headers = "Accept=application/json")
	public @ResponseBody System update(@PathVariable String id){
		System system =  new System();
		try{
			system =  systemManager.getSystemById(id);
		}catch(Exception e){
			log.error("get system"+system.getSystemId()+"failed!", e);
		}
		return system;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/checkSystemUsed/{id}", headers = "Accept=application/json")
	public @ResponseBody String checkSystemUsed(@PathVariable String id){
		if(id.contains(",")){
			String[] idArr = id.split(",");
			int i =0;
			while(i<idArr.length){
				if(systemManager.isSystemUsed(idArr[i])){
					return idArr[i];
				}
				i++;
			}
		}
		else{
			if(systemManager.isSystemUsed(id)){
				return id;
			}
		}
		return null;
	}
	
	/**
	 * insert or update protocolInfos
	 * @param msaArr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST, value = "/saveProtocolInfos", headers = "Accept=application/json")
	public @ResponseBody
	boolean insertOrUpdateProtocolInfos(@RequestBody
	ProtocolInfo[] piArr) {
		boolean flag = true;
		try {
			// for IE 8.0 above
			for(ProtocolInfo pi :piArr){
				pi.setAppScene(("null".equals(pi.getAppScene()) || pi.getAppScene() ==null)?"":pi.getAppScene());
				pi.setAvgResTime(("null".equals(pi.getAvgResTime()) || pi.getAvgResTime() ==null)?"":pi.getAvgResTime());
				pi.setConnectMode(("null".equals(pi.getConnectMode()) || pi.getConnectMode() ==null)?"":pi.getConnectMode());
				pi.setCurrentTimes(("null".equals(pi.getCurrentTimes()) || pi.getCurrentTimes() ==null)?"":pi.getCurrentTimes());
				pi.setMacFlag(("null".equals(pi.getMacFlag()) || pi.getMacFlag() ==null)?"":pi.getMacFlag());
				pi.setSuccessRate(("null".equals(pi.getSuccessRate()) || pi.getSuccessRate() ==null)?"":pi.getSuccessRate());
				pi.setSysAddr(("null".equals(pi.getSysAddr()) || pi.getSysAddr() ==null)?"":pi.getSysAddr());
				pi.setTimeout(("null".equals(pi.getTimeout()) || pi.getTimeout() ==null)?"":pi.getTimeout());
			}
			if (piArr != null && piArr.length > 0) {
				// 批量插入或更新元数据结构属性信息
				protocolManager.batchInsertOrUpdate(piArr);
			}
		} catch (Exception e) {
			log.error("batch insert or update protocolInfos failed!", e);
			flag = false;
		}
		return flag;
	}
	
	/**
	 * get protocolInfos by SysId
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getProtocolInfosBySysId/{id}", headers = "Accept=application/json")
	public @ResponseBody List<ProtocolInfo> getProtocolInfoBySysId(@PathVariable String id){
		List<ProtocolInfo> list = null;
		try{
			list = protocolManager.getProtocolInfosBySysId(id);
			if(list == null){
				list = new ArrayList<ProtocolInfo>();
			}
		}catch(Exception e){
			log.error("get protocolInfos failed!", e);
		}
		return list;
	}
	/**
	 * get protocolInfos by SysId
	 * @param id
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.POST, value = "/getTopology", headers = "Accept=application/json")
	public @ResponseBody boolean getTopology(@RequestBody String[] param,HttpServletRequest request,HttpServletResponse response){
		String sysId = param[0];
		String sysType = param[1];
		SystemTopology sysTopo = systemManager.getSystemTopology(sysId, sysType);
		try {
			File file = new File(request.getRealPath("/")+"/jsp/","config.xml");
			file.createNewFile();
			StringBuffer config = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"); 
			config.append("<system>\n");
			for(SystemNode sysNode : sysTopo.getSystemnodeList()){
				config.append("	<node name=\""+sysNode.getNodeName()+"\" label=\""+sysNode.getNodeLabel()+"\" x=\""+sysNode.getNodeX()+"\" y=\""+sysNode.getNodeY()+"\" des=\""+sysNode.getNodeDesc()+"\"  url=\"#\"/>\n");
			}
			for(SystemLine sysLine : sysTopo.getSystemlineList()){
				config.append("	<line fromName=\""+sysLine.getFromName()+"\" toName=\""+sysLine.getToName()+"\"  des=\""+sysLine.getDesc()+"\" />\n");
			}			
//			config.append("	<line fromName=\"esb\" toName=\"sop\"  des=\"ESB调用SOP\" />\n");
//			config.append("	<line fromName=\"zhipp\" toName=\"esb\" des=\"ZHIPP调用ESB\" />\n");
//			config.append("	<line fromName=\"fes\" toName=\"zhipp\" des=\"FES调用ZHIPP\"  />\n");
//			config.append("	<line fromName=\"bip\" toName=\"zhipp\" des=\"FES调用ZHIPP\"  />\n");
			config.append("</system>");
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter ow = new OutputStreamWriter(fos,"UTF-8");
			ow.write(config.toString());
			ow.flush();
			ow.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
