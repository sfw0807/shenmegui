package com.dc.esb.servicegov.refactoring.resource.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dc.esb.servicegov.refactoring.dao.impl.OLADAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.OLA;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.util.AuditUtil;
import com.dc.esb.servicegov.refactoring.util.ExcelTool;
import com.dc.esb.servicegov.refactoring.util.GlobalImport;
import com.dc.esb.servicegov.refactoring.util.GlobalMenuId;
import com.dc.esb.servicegov.refactoring.util.ServiceStateUtils;
import com.dc.esb.servicegov.refactoring.util.UserOperationLogUtil;
import com.dc.esb.servicegov.refactoring.util.Utils;

@Service
@Transactional(rollbackFor = Exception.class)
public class IndexServiceParse {

	private Log log = LogFactory.getLog(IndexServiceParse.class);

	private ExcelTool excelTool = ExcelTool.getInstance();
	private String initVersion;
	private String initOperationVersion;
	private String initOperationState;
	@Autowired
	private ServiceDAOImpl serviceDAO;
	@Autowired
	private OperationDAOImpl operationDAO;
	@Autowired
	private OLADAOImpl olaDAO;

	private String serviceId;
	private String serviceName;
	private String serviceRemark;
	private String operationId;
	private String operationName;
	private String operationRemark;
	private String categoryId;

	public boolean parse(Row row) {
		this.initVersion = "1.0.0";
		this.initOperationVersion = "1.0.0";
		this.initOperationState = ServiceStateUtils.DEFINITION;
		// TODO Auto-generated method stub
		String serviceIdAndName = excelTool.getCellContent(row.getCell(2));
		serviceIdAndName = serviceIdAndName.replace("（", "(");
		serviceIdAndName = serviceIdAndName.replace("）", ")");
		serviceId = serviceIdAndName.substring(
				serviceIdAndName.indexOf("(") + 1,
				serviceIdAndName.length() - 1);
		if ("".equals(serviceId)) {
			log.error("服务" + serviceId + operationId + "[服务Id]不能为空!");
			UserOperationLogUtil.saveLog("服务" + serviceId + operationId
					+ "Index页[服务Id]不能为空!", GlobalMenuId.menuIdMap
					.get(GlobalMenuId.resourceImportMenuId));
			GlobalImport.flag = false;
			return false;
		}
		serviceName = serviceIdAndName.substring(0, serviceIdAndName
				.indexOf("("));
		operationId = excelTool.getCellContent(row.getCell(3));
		if ("".equals(operationId)) {
			log.error("服务" + serviceId + operationId + "[操作Id]不能为空!");
			UserOperationLogUtil.saveLog("服务" + serviceId + operationId
					+ "Index页[操作Id]不能为空!", GlobalMenuId.menuIdMap
					.get(GlobalMenuId.resourceImportMenuId));
			GlobalImport.flag = false;
			return false;
		}
		operationName = excelTool.getCellContent(row.getCell(4));
		// 获取服务分组
		categoryId = serviceId.substring(1, 6);
		try {
			// insert service
			insertService();
			// insert operation
			insertOperation();
			// insert ola
			insertOLA();
			log.info("import service infos finished!");
		} catch (Exception e) {
			log.error("import service Infos error!", e);
		}
		return true;
	}

	public void insertService() {
		com.dc.esb.servicegov.refactoring.entity.Service service = new com.dc.esb.servicegov.refactoring.entity.Service();
		service.setServiceId(serviceId);
		service.setServiceName(serviceName);
		service.setServiceRemark(serviceRemark);
		service.setCategoryId(categoryId);
		if(GlobalImport.operateFlag){
			com.dc.esb.servicegov.refactoring.entity.Service tmpService = serviceDAO.findUniqueBy("serviceId", serviceId);
			if(tmpService != null){
			    if(AuditUtil.passed.equals(tmpService.getAuditState())){
			    	service.setVersion(Utils.modifyversionno(tmpService.getVersion()));
			    }
			    else{
			    	service.setVersion(tmpService.getVersion());
			    }
				service.setState(tmpService.getState());
			}
			else{
				service.setVersion(initVersion);
			    service.setState(ServiceStateUtils.DEFINITION);
			}
		}
		else{
			com.dc.esb.servicegov.refactoring.entity.Service tmpService = serviceDAO.findUniqueBy("serviceId", serviceId);
			if(tmpService != null){
				service.setVersion(tmpService.getVersion());
				service.setState(tmpService.getState());
			}
			else{
				service.setVersion(initVersion);
			    service.setState(ServiceStateUtils.DEFINITION);
			}
		}
		service.setAuditState(AuditUtil.submit);
		service.setModifyUser("");
		service.setUpdateTime(Utils.getTime());
		serviceDAO.TxSaveService(service);
		log.info("insert service finished!");
	}

	public void insertOperation() {
		Operation operation = new Operation();
		operation.setOperationId(operationId);
		operation.setOperationName(operationName);
		operation.setRemark(operationRemark);
		operation.setServiceId(serviceId);
		if (GlobalImport.operateFlag) {
			Operation tmpOperation = operationDAO.getOperation(operationId,
					serviceId);
			if (tmpOperation != null) {
				if (AuditUtil.passed.equals(tmpOperation.getAuditState())) {
					operation.setVersion(Utils.modifyversionno(tmpOperation
							.getVersion()));
					this.initOperationVersion = Utils
							.modifyversionno(tmpOperation.getVersion());
				} else {
					operation.setVersion(tmpOperation.getVersion());
					this.initOperationVersion = tmpOperation.getVersion();
				}
				operation.setState(tmpOperation.getState());
				this.initOperationState = tmpOperation.getState();
			} else {
				operation.setVersion(initOperationVersion);
				operation.setState(initOperationState);
			}
		} else {
			Operation tmpOperation = operationDAO.getOperation(operationId,
					serviceId);
			if (tmpOperation != null) {
				operation.setVersion(tmpOperation.getVersion());
				operation.setState(tmpOperation.getState());
				this.initOperationVersion = tmpOperation.getVersion();
				this.initOperationState = tmpOperation.getState();
			} else {
				operation.setVersion(initOperationVersion);
				operation.setState(initOperationState);
			}
		}
		operation.setAuditState(AuditUtil.submit);
		operation.setModifyUser("");
		operation.setUpdateTime(Utils.getTime());
		operationDAO.TxSaveOperation(operation);
		log.info("insert operation finished!");
	}

	public void insertOLA() {
		OLA ola = new OLA();
		ola.setOperationId(operationId);
		ola.setServiceId(serviceId);
		ola.setOlaName("wsdl_operation");
		ola.setOlaValue("true");
		ola.setOlaRemark("wsdl_operation");
		ola.setUpdateTime(Utils.getTime());
		ola.setModifyUser("");
		olaDAO.txSaveOla(ola);
		log.info("insert OLA finished!");
	}
}
