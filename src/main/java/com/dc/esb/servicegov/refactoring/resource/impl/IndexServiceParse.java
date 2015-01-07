package com.dc.esb.servicegov.refactoring.resource.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dc.esb.servicegov.refactoring.dao.impl.OLADAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.OLA;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.resource.IParse;
import com.dc.esb.servicegov.refactoring.util.ExcelTool;
import com.dc.esb.servicegov.refactoring.util.GlobalMap;
import com.dc.esb.servicegov.refactoring.util.ServiceStateUtils;
import com.dc.esb.servicegov.refactoring.util.Utils;

@Service
@Transactional(rollbackFor = Exception.class)
public class IndexServiceParse implements IParse {

	private Log log = LogFactory.getLog(IndexServiceParse.class);

	private ExcelTool excelTool = ExcelTool.getInstance();
	private static final String initVersion = "1.0.0";
	
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

	@Override
	public void parse(Row row, Sheet interfaceSheet) {
		GlobalMap.globaNodePathMap.clear();
		// TODO Auto-generated method stub
		String serviceIdAndName = excelTool
				.getCellContent(interfaceSheet, 0, 7);
		serviceIdAndName = serviceIdAndName.replace("（", "(");
		serviceIdAndName = serviceIdAndName.replace("）", ")");
		serviceId = serviceIdAndName.substring(
				serviceIdAndName.indexOf("(") + 1,
				serviceIdAndName.length() - 1);
		serviceName = serviceIdAndName.substring(0, serviceIdAndName
				.indexOf("("));
		operationId = excelTool.getCellContent(row.getCell(3));
		operationName = excelTool.getCellContent(row.getCell(4));
		// 新文档格式
		if ("".equals(excelTool.getCellContent(interfaceSheet, 2, 0))) {
			serviceRemark = excelTool.getCellContent(interfaceSheet, 2, 7);
			operationRemark = excelTool.getCellContent(interfaceSheet, 3, 7);
		}
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
	}

	public void insertService() {
		com.dc.esb.servicegov.refactoring.entity.Service service = new com.dc.esb.servicegov.refactoring.entity.Service();
		service.setServiceId(serviceId);
		service.setServiceName(serviceName);
		service.setServiceRemark(serviceRemark);
		service.setCategoryId(categoryId);
		service.setVersion(initVersion);
		service.setState(ServiceStateUtils.DEFINITION);
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
		operation.setVersion(initVersion);
		operation.setState(ServiceStateUtils.DEFINITION);
		operation.setModifyUser("");
		operation.setUpdateTime(Utils.getTime());
		operationDAO.TxSaveOperation(operation);
		log.info("insert operation finished!");
	}
	
	public void insertOLA(){
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
