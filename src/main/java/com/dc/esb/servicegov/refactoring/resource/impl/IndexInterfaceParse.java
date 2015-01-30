package com.dc.esb.servicegov.refactoring.resource.impl;

import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dc.esb.servicegov.refactoring.dao.impl.InterfaceDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.InterfaceExtendsDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.Interface;
import com.dc.esb.servicegov.refactoring.entity.InterfaceExtends;
import com.dc.esb.servicegov.refactoring.util.ExcelTool;
import com.dc.esb.servicegov.refactoring.util.GenerateUUID;
import com.dc.esb.servicegov.refactoring.util.GlobalImport;
import com.dc.esb.servicegov.refactoring.util.ServiceStateUtils;
import com.dc.esb.servicegov.refactoring.util.Utils;

@Service
@Transactional(rollbackFor = Exception.class)
public class IndexInterfaceParse {

	private Log log = LogFactory.getLog(IndexInterfaceParse.class);

	private ExcelTool excelTool = ExcelTool.getInstance();
	private static final String initVersion = "1.0.0";

	@Autowired
	private InterfaceDAOImpl interfaceDAO;
	@Autowired
	private InterfaceExtendsDAOImpl iExtendsDAO;

	private String interfaceId;
	private String interfaceName;
	private String through;
	// 接口报文类型
	private String interfaceMsgType;
	// 报文转换方向
	private String msgChangeType;
	// 接口方向
	private String direction;

	public void parse(Row row) {
		// TODO Auto-generated method stub
		String serviceIdAndName = excelTool.getCellContent(row.getCell(2));
		serviceIdAndName = serviceIdAndName.replace("（", "(");
		serviceIdAndName = serviceIdAndName.replace("）", ")");
		String serviceId = serviceIdAndName.substring(serviceIdAndName
				.indexOf("(") + 1, serviceIdAndName.length() - 1);
		String operationId = excelTool.getCellContent(row.getCell(3));
		interfaceMsgType = excelTool.getCellContent(row.getCell(9));
		interfaceMsgType = interfaceMsgType.replace("（", "(");
		interfaceMsgType = interfaceMsgType.replace("）", ")");
		interfaceMsgType = interfaceMsgType.replace("非标", "");
		interfaceMsgType = interfaceMsgType.replace("(", "");
		interfaceMsgType = interfaceMsgType.replace(")", "");

		msgChangeType = excelTool.getCellContent(row.getCell(12));
		msgChangeType = msgChangeType.replace("（", "(");
		msgChangeType = msgChangeType.replace("）", ")");
		msgChangeType = msgChangeType.replace("非标", "");
		msgChangeType = msgChangeType.replace("(", "");
		msgChangeType = msgChangeType.replace(")", "");
		msgChangeType = msgChangeType.replace("->", "-");

		// 报文转换方向
		direction = excelTool.getCellContent(row.getCell(7));
		if ("Provider".equals(direction)) {
			direction = "1";
		} else {
			direction = "0";
		}

		interfaceId = excelTool.getCellContent(row.getCell(0));
		interfaceName = excelTool.getCellContent(row.getCell(1));
		if ("".equals(interfaceId)) {
			interfaceId = serviceId + operationId;
		}
		through = excelTool.getCellContent(row.getCell(17));
		try {
			// insert Interface
			insertInterface();
			// 如果是SOP报文，需要配置模板接口
			if (interfaceMsgType.equalsIgnoreCase("sop")
					&& !interfaceId.endsWith("Template")) {
				insertDefaultConfig();
			}
			log.info("import interface infos finished!");
		} catch (Exception e) {
			log.error("import interface Infos error!", e);
		}
	}

	/**
	 * 配置接口模板
	 */
	public void insertDefaultConfig() {
		InterfaceExtends iExtends = new InterfaceExtends();
		iExtends.setId(GenerateUUID.genRandom());
		iExtends.setInterfaceId(interfaceId);
		if ("1".equals(direction)) {
			iExtends.setSuperInterfaceId("OutSOPTemplate");
			iExtends.setSuperInterfaceName("Out端SOP模板接口");
		} else {
			iExtends.setSuperInterfaceId("InSOPTemplate");
			iExtends.setSuperInterfaceName("SOP模板接口");
		}
		if (iExtendsDAO.delByIExtends(iExtends)) {
			iExtendsDAO.TxSaveInterfaceExtends(iExtends);
		}
		log.info("insert interfaceExtends finished!");
	}

	public void insertInterface() {
		Interface i = new Interface();
		i.setInterfaceId(interfaceId);
		i.setEcode(interfaceId);
		i.setInterfaceName(interfaceName);
		if(GlobalImport.operateFlag){
			Interface tmpi = interfaceDAO.findUniqueBy("interfaceId", interfaceId);
			if(tmpi != null){
				i.setVersion(Utils.modifyversionno(tmpi.getVersion()));
				i.setState(tmpi.getState());
			}
			else{
				i.setVersion(initVersion);
				i.setState(ServiceStateUtils.DEFINITION);
			}
		}
		else{
			Interface tmpi = interfaceDAO.findUniqueBy("interfaceId", interfaceId);
			if(tmpi != null){
				i.setVersion(tmpi.getVersion());
				i.setState(tmpi.getState());
			}
			else{
				i.setVersion(initVersion);
				i.setState(ServiceStateUtils.DEFINITION);
			}
		}
		i.setThrough(through);
		i.setModifyUser("");
		i.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		interfaceDAO.TxSaveInterface(i);
		log.info("insert interface finished!");
	}
}
