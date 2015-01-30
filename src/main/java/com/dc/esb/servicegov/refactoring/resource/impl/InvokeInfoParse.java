package com.dc.esb.servicegov.refactoring.resource.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.InvokeInfoDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SystemDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.TransStateDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.entity.TransState;
import com.dc.esb.servicegov.refactoring.util.ExcelTool;
import com.dc.esb.servicegov.refactoring.util.GlobalImport;
import com.dc.esb.servicegov.refactoring.util.GlobalMenuId;
import com.dc.esb.servicegov.refactoring.util.ServiceStateUtils;
import com.dc.esb.servicegov.refactoring.util.UserOperationLogUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class InvokeInfoParse {

	private Log log = LogFactory.getLog(InvokeInfoParse.class);
	private ExcelTool excelTool = ExcelTool.getInstance();
	@Autowired
	private SystemDAOImpl systemDAO;
	@Autowired
	private InvokeInfoDAOImpl invokeInfoDAO;
	@Autowired
	private TransStateDAOImpl transStateDAO;

	public boolean parseRow(Row row) {
		String interfaceId = excelTool.getCellContent(row.getCell(0));
		String operationId = excelTool.getCellContent(row.getCell(3));
		String serviceIdAndName = excelTool.getCellContent(row.getCell(2));
		serviceIdAndName = serviceIdAndName.replace("（", "(");
		if("".equals(serviceIdAndName) && "".equals(interfaceId) &&"".equals(operationId)){
			log.info("读取到index页空白行,导入结束!");
			return false;
		}
		String serviceId = serviceIdAndName.substring(serviceIdAndName
				.indexOf("(") + 1, serviceIdAndName.lastIndexOf(")"));
		if ("".equals(serviceId)) {
			log.error("服务" + serviceId + "index页[服务Id]不能为空!");
			UserOperationLogUtil.saveLog("服务" + serviceId + "index页[服务Id]不能为空!", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
			GlobalImport.flag = false;
			return false;
		}
		if ("".equals(operationId)) {
			log.error("服务" + serviceId + operationId + "index页[操作Id]不能为空!");
			UserOperationLogUtil.saveLog("服务" + serviceId + operationId + "index页[操作Id]不能为空!", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
			GlobalImport.flag = false;
			return false;
		}
		String through = excelTool.getCellContent(row.getCell(17));
		String shead = excelTool.getCellContent(row.getCell(19));
		if("".equals(shead)){
			shead = "SHEAD";
		}
		String msgChange = excelTool.getCellContent(row.getCell(12));
		if ("".equals(msgChange)) {
			log.error("服务" + serviceId + operationId + "index页[报文转换方向]不能为空!");
			UserOperationLogUtil.saveLog("服务" + serviceId + operationId + "index页[报文转换方向]不能为空!", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
			GlobalImport.flag = false;
			return false;
		}
		msgChange = msgChange.replace("（", "(");
		msgChange = msgChange.replace("）", ")");
		msgChange = msgChange.replace("非标", "");
		msgChange = msgChange.replace("(", "");
		msgChange = msgChange.replace(")", "");
		msgChange = msgChange.replace("->", "-");
		String funcType = excelTool.getCellContent(row.getCell(16));
		String prdSysId = excelTool.getCellContent(row.getCell(8));
		// 判断提供方是否存在
		if ("".equals(systemDAO.getSystemAbById(prdSysId))) {
			log.error("服务" + serviceId + operationId + "提供方系统["+prdSysId +"]简称不存在，请先添加提供方系统到服务治理平台！");
			UserOperationLogUtil.saveLog("服务" + serviceId + operationId + "提供方系统["+prdSysId+"]简称不存在，请先添加提供方系统到服务治理平台！", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
			GlobalImport.flag = false;
			return false;
		}
		String csmSysAb = excelTool.getCellContent(row.getCell(5));
		// 报文转换方向
		String direction = excelTool.getCellContent(row.getCell(7));
		if ("".equals(direction)
				|| (!"Provider".equals(direction) && !"Consumer"
						.equals(direction))) {
			log.error("服务" + serviceId + operationId + "index页[接口方向]不能为空,并且取值只能是Provider或Consumer!");
			UserOperationLogUtil.saveLog("服务" + serviceId + operationId + "index页[接口方向]不能为空,并且取值只能是Provider或Consumer!", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
			GlobalImport.flag = false;
			return false;
		}
		if ("Provider".equals(direction)) {
			direction = "1";
		} else {
			direction = "0";
		}
		csmSysAb = csmSysAb.replace("（", "(");
		csmSysAb = csmSysAb.replace("）", ")");
		String modifyUser = excelTool.getCellContent(row.getCell(10));
		// String updateDate = excelTool.getCellContent(row.getCell(11));
		// get provide and consume message type
		String provideMsgType = msgChange.substring(msgChange.indexOf("-") + 1);
		String consumeMsgType = msgChange.substring(0, msgChange.indexOf("-"));
		String passedSys = "";
		if (csmSysAb.contains("(")) {
			passedSys = csmSysAb.substring(0, csmSysAb.indexOf("("));
		}
		if ("IPP".equals(passedSys)) {
			passedSys = "ZHIPP";
		}
		if (log.isInfoEnabled()) {
			log.info("parse row contents finished!");
		}
		if (interfaceId.indexOf(".") > 0) {
			interfaceId = interfaceId.substring(0, interfaceId.indexOf("."));
		}
		if (null == interfaceId || "".equals(interfaceId)) {
			interfaceId = serviceId + operationId;
		}
		log.info("接口ID: " + interfaceId);
		Map<String, String> conditions = new HashMap<String, String>();
		// consume as array handle
		String[] csmArr = csmSysAb.split("、");
		for (int i = 0; i < csmArr.length; i++) {
			String consumeAb = this.handleConsumeAb(csmArr[i]);
			try {
				// get consumeSysId
				String csmSysId = systemDAO.getSystemIdByAb(consumeAb);
				if(csmSysId.equals("")){
					log.info("调用方系统 [" + consumeAb + "]未找到系统Id！");
				}
				if(!"填单机".equals(consumeAb) && !"".equals(consumeAb) && "".equals(csmSysId)){
					log.error("调用方系统 [" + consumeAb + "]未找到系统Id,导入失败！");
					UserOperationLogUtil.saveLog("调用方系统 [" + consumeAb + "]未找到系统Id,导入失败！", GlobalMenuId.menuIdMap.get(GlobalMenuId.resourceImportMenuId));
					GlobalImport.flag = false;
					return false;
				}
				conditions.put("serviceId", serviceId);
				conditions.put("operationId", operationId);
				conditions.put("ecode", interfaceId);
				conditions.put("provideSysId", prdSysId);
				conditions.put("consumeSysId", csmSysId);
				InvokeInfo invokeInfo = null;
				// already exists update
				if ((invokeInfo = invokeInfoDAO.checkIsExist(conditions)) != null) {
					// get max id
					invokeInfo.setServiceId(serviceId);
					invokeInfo.setOperationId(operationId);
					invokeInfo.setEcode(interfaceId);
					invokeInfo.setProvideSysId(prdSysId);
					invokeInfo.setConsumeSysId(csmSysId);
					invokeInfo.setPassbySysId(passedSys);
					invokeInfo.setFuncType(funcType);
					invokeInfo.setSvcHeader(shead);
					invokeInfo.setThrough(through);
					invokeInfo.setConsumeMsgType(consumeMsgType);
					invokeInfo.setProvideMsgType(provideMsgType);
					invokeInfo.setModifyUser(modifyUser);
					invokeInfo.setUpdateTime(new Timestamp(System
							.currentTimeMillis()));
					invokeInfo.setDirection(direction);
					invokeInfoDAO.save(invokeInfo);
				} else {
					invokeInfo = new InvokeInfo();
					Integer maxId = invokeInfoDAO.getMaxId();
					invokeInfo.setId(maxId + 1);
					invokeInfo.setServiceId(serviceId);
					invokeInfo.setOperationId(operationId);
					invokeInfo.setEcode(interfaceId);
					invokeInfo.setProvideSysId(prdSysId);
					invokeInfo.setConsumeSysId(csmSysId);
					invokeInfo.setPassbySysId(passedSys);
					invokeInfo.setFuncType(funcType);
					invokeInfo.setSvcHeader(shead);
					invokeInfo.setThrough(through);
					invokeInfo.setConsumeMsgType(consumeMsgType);
					invokeInfo.setProvideMsgType(provideMsgType);
					invokeInfo.setModifyUser(modifyUser);
					invokeInfo.setUpdateTime(new Timestamp(System
							.currentTimeMillis()));
					invokeInfo.setDirection(direction);
					invokeInfoDAO.save(invokeInfo);
					// 更新交易状态 trans_state
					TransState tranState = new TransState();
					tranState.setId(maxId + 1);
					tranState.setVersionState(ServiceStateUtils.DEFINITION);
					transStateDAO.save(tranState);
				}
			} catch (Exception e) {
				log.error("handle db2 failed! import failed!", e);
			}
		}
		log.info("import invoke_info success!");
		return true;
	}

	// 处理调用方英文名称
	public String handleConsumeAb(String tempconsumeAb) {
		String consumeAb = "";
		// three conditions
		// IPP(nbper)
		// IPP(PWM
		// YJT)
		if (tempconsumeAb.contains("(") && tempconsumeAb.contains(")")) {
			consumeAb = tempconsumeAb.substring(tempconsumeAb.indexOf("(") + 1,
					tempconsumeAb.indexOf(")"));
		} else if (tempconsumeAb.contains("(") && !tempconsumeAb.contains(")")) {
			consumeAb = tempconsumeAb.substring(tempconsumeAb.indexOf("(") + 1);
		} else if (!tempconsumeAb.contains("(") && tempconsumeAb.contains(")")) {
			consumeAb = tempconsumeAb.substring(0, tempconsumeAb.indexOf(")"));
		} else {
			consumeAb = tempconsumeAb;
		}
		return consumeAb;
	}
}
