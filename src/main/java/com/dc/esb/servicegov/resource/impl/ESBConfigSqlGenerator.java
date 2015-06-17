package com.dc.esb.servicegov.resource.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dc.esb.servicegov.dao.impl.InvokeInfoDAOImpl;
import com.dc.esb.servicegov.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.dao.impl.ProtocolInfoDAOImpl;
import com.dc.esb.servicegov.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.dao.impl.SystemDAOImpl;
import com.dc.esb.servicegov.entity.InvokeInfo;
import com.dc.esb.servicegov.entity.ProtocolInfo;
import com.dc.esb.servicegov.resource.IConfigGenerater;

@Service
public class ESBConfigSqlGenerator implements
		IConfigGenerater<InvokeInfo, List<File>> {

	private static final Log log = LogFactory
			.getLog(ESBConfigSqlGenerator.class);
	@Autowired
	private SystemDAOImpl systemDAO;
	@Autowired
	private OperationDAOImpl operationDAO;
	@Autowired
	private ServiceDAOImpl serviceDAO;
	@Autowired
	private InvokeInfoDAOImpl invokeDAO;
	@Autowired
	private ProtocolInfoDAOImpl protocolDAO;

	public static final String ISTHROUGH = "是";
	public static final String SQL_DIR_PATH = "sql";
	public static final String BUSS_SERVICE_SQL_FILE_NAME = "06_esbdata_dml_bussservice.sql";
	public static final String SERVICE_IDENTIFY_SQL_FILE_NALE = "07_esbdata_dml_serviceidentify.sql";
	public static final String SERVICE_INFO_SQL_FILE_NAME = "12_esbdata_dml_serviceinfo.sql";
	public static final String SERVICE_CTRL_SQL_FILE_NAME = "13_esbdata_dml_serviceCtrl.sql";
	public static final String SYSTEM_IDENTIFY_SQL_FILE_NAME = "14_esbdata_dml_systemIdentity.sql";
	public static final String FLOW_CTRL_SQL_FILE_PATH_NAME = "16_esbdata_dml_esb2_flow_ctrl.sql";
	public static final String SERVICE_LOG_CFG_SQL_FILE_NAME = "19_esbdata_dml_service_log_cfg.sql";
	public static final String TBL_SPE_HANDLE_INFO = "26_esbdata_dml_tbl_spe_handle_info.sql";
	public static final String ALL = "all.sql";
	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	private String operationId;
	private String operationName;
	private String serviceId;
	private String serviceName;
	private String interfaceId;
	private String prdMsgType;
	private String csmMsgType;
	private String prdSysId;
	private String prdSysAb;
	private String through;

	@Override
	public List<File> generate(InvokeInfo invokeInfo) throws Exception {

		File sqlDir = null;
		operationId = invokeInfo.getOperationId();
		serviceId = invokeInfo.getServiceId();
		log.info("operationId:  " + operationId);
		operationName = operationDAO.getOperation(operationId, serviceId)
				.getOperationName();
		log.info("serviceId: " + serviceId);
		serviceName = serviceDAO.findUniqueBy("serviceId", serviceId)
				.getServiceName();
		log.info("interfaceId: " + interfaceId);
		interfaceId = invokeInfo.getEcode();
		prdMsgType = invokeInfo.getProvideMsgType();
		csmMsgType = invokeInfo.getConsumeMsgType();
		prdSysAb = invokeInfo.getProvideSysId();
		prdSysId = systemDAO.getSystemIdByAb(prdSysAb);
		through = invokeInfo.getThrough();
		String dirPath = serviceId + operationId + "(" + csmMsgType + "-"
				+ prdMsgType + ")" + File.separator + SQL_DIR_PATH;
		sqlDir = new File(dirPath);
		sqlDir.deleteOnExit();
		sqlDir.mkdirs();
		synchronized (ESBConfigSqlGenerator.class) {
			generateBussServiceSqlFile();
			generateServiceIdentifySqlFile();
			generateServiceInfoSqlFile();
			generateServiceCtrlSqlFile("o", "");
			generateServiceFlowCtrlFile();
			generateServiceLogCfgSqlFile();
			if (ISTHROUGH.equals(through)) {
				generateSpeHandleSqlFile();
			}
		}
		List<File> sqlDirs = new ArrayList<File>();
		sqlDirs.add(sqlDir);
		return sqlDirs;
	}

	/**
	 * insert into SERVICES (NAME, INADDRESSID, OUTADDRESSID, TYPE,
	 * SESSIONCOUNT, DELIVERYMODE, NODEID, LOCATION, ROUTERABLE) values
	 * ('S120030005AcctOtherSysSignInfoQuery',
	 * '4cc95abd8b9cba4705f290b39dee3d6a', '70022232c41f240289cac75d5b3e3884',
	 * 'SERVICE', 1, '2', '', 'local_out', '1')@
	 */
	private String generateServiceSql() {

		StringBuilder servicesSqlBuilder = new StringBuilder();
		servicesSqlBuilder
				.append("insert into SERVICES (NAME, INADDRESSID, OUTADDRESSID, TYPE, SESSIONCOUNT, DELIVERYMODE, NODEID, LOCATION, ROUTERABLE) values ('");
		servicesSqlBuilder.append(serviceId);
		servicesSqlBuilder.append(operationId);
		servicesSqlBuilder.append("','");
		servicesSqlBuilder.append("4cc95abd8b9cba4705f290b39dee3d6a");
		servicesSqlBuilder.append("','");
		servicesSqlBuilder.append("70022232c41f240289cac75d5b3e3884");
		servicesSqlBuilder
				.append("', 'SERVICE', 1, '2', '', 'local_out', '1')@");
		servicesSqlBuilder.append(LINE_SEPARATOR);
		return servicesSqlBuilder.toString();

	}

	/**
	 * insert into BUSSSERVICES (SERVICEID, CATEGORY, METHODNAME, ISARG,
	 * DESCRIPTION) values ('S120030005AcctOtherSysSignInfoQuery', '', null,
	 * 'false', null)@
	 */
	private String generateBussServiceSql() {
		StringBuilder bussServiceSqlBuilder = new StringBuilder();
		bussServiceSqlBuilder
				.append("insert into BUSSSERVICES (SERVICEID, CATEGORY, METHODNAME, ISARG, DESCRIPTION) values ('");
		bussServiceSqlBuilder.append(serviceId);
		bussServiceSqlBuilder.append(operationId);
		bussServiceSqlBuilder.append("', '', null, 'false', null)@");
		bussServiceSqlBuilder.append(LINE_SEPARATOR);
		return bussServiceSqlBuilder.toString();
	}

	/**
	 * insert into SERVICEINFO (SERVICEID, SERVICETYPE, CONTRIBUTION, PREPARED,
	 * GROUPNAME, LOCATION, DESCRIPTION, MODIFYTIME, ADAPTERTYPE, ISCREATE)
	 * values ('S120030005AcctOtherSysSignInfoQuery', 'BUSS',
	 * 'S120030005AcctOtherSysSignInfoQuery', 'false', null, 'local_out', '',
	 * null, null, 'true')@
	 */
	private String generateServiceInfoSql() {
		StringBuilder serviceInfoSqlBuilder = new StringBuilder();
		serviceInfoSqlBuilder
				.append("insert into SERVICEINFO (SERVICEID, SERVICETYPE, CONTRIBUTION, PREPARED, GROUPNAME, LOCATION, DESCRIPTION, MODIFYTIME, ADAPTERTYPE, ISCREATE) values ('");
		serviceInfoSqlBuilder.append(serviceId + operationId);
		serviceInfoSqlBuilder.append("', 'BUSS', '");
		serviceInfoSqlBuilder.append(serviceId + operationId);
		serviceInfoSqlBuilder
				.append("', 'false', null, 'local_out', '', null, null, 'true')@");
		serviceInfoSqlBuilder.append(LINE_SEPARATOR);
		return serviceInfoSqlBuilder.toString();
	}

	/**
	 * insert into BINDMAP (SERVICEID, STYPE, LOCATION, VERSION, PROTOCOLID,
	 * MAPTYPE) values ('S120030005AcctOtherSysSignInfoQuery', 'SERVICE',
	 * 'local_out', '0', 'sop_adapter', 'request')@
	 * 
	 * 针对代客代理改动 代客代理有三个应用，不同的地址。三个不同接出协议，指向不同的地址。
	 * mmpp_matrix_adapter平台：http://10.112.12.141:7001/matrix/ws/mmpp
	 * mmpp_margin_adapter双向：http://10.112.12.141:7001/margin/ws/mmpp
	 * mmpp_gold_adapter实物：http://10.112.12.141:7001/gold/ws/mmpp
	 * 开发代客代理的交易时，确认交易所属应用类别，使用对应的接出协议
	 * 
	 */
	@SuppressWarnings("unchecked")
	private String generateBindMapSql() {
		boolean generatedFlag = false;
		String sysName = prdSysAb;
		if ("LoanQ".equals(sysName)
				|| "IDP".equals(sysName) || "PWM".equals(sysName)
				|| "YJT".equals(sysName) || "NBH".equals(sysName)) {

		} else {
			sysName = sysName.toLowerCase();
		}
		StringBuilder bindMapSqlBuilder = new StringBuilder();
		Map<String, String> params = new HashMap<String, String>();
		params.put("serviceId", serviceId);
		params.put("operationId", operationId);
		params.put("ecode", interfaceId);
		params.put("provideMsgType", prdMsgType);
		params.put("consumeMsgType", csmMsgType);
		List<InvokeInfo> list = invokeDAO.findBy(params);
		for (InvokeInfo invoke : list) {
			String provideSys = invoke.getProvideSysId();
			String consumeSys = systemDAO.getSystemAbById(invoke
					.getConsumeSysId());
			String funcType = invoke.getFuncType();
			boolean flag = false;
			if ("0204".equals(provideSys)
					|| ("MMPP".equals(consumeSys) && "0001".equals(provideSys))) {
				if ("公共".equals(funcType)) {
					sysName = "mmpp_matrix";
				} else if ("双向".equals(funcType)) {
					sysName = "mmpp_margin";
				} else if ("实物".equals(funcType)) {
					sysName = "mmpp_gold";
				}
				flag = true;
			} else if (generatedFlag) {
				continue;
			}
			bindMapSqlBuilder
					.append("insert into BINDMAP (SERVICEID, STYPE, LOCATION, VERSION, PROTOCOLID, MAPTYPE) values ('");
			bindMapSqlBuilder.append(serviceId + operationId);
			bindMapSqlBuilder.append("', 'SERVICE','local_out', '0', '");
			bindMapSqlBuilder.append(sysName.toLowerCase());
			bindMapSqlBuilder.append("_adapter', 'request')@");
			bindMapSqlBuilder.append(LINE_SEPARATOR);
			generatedFlag = true;
			if(flag){
				break;
			}
		}
		return bindMapSqlBuilder.toString();
	}

	/**
	 * insert into DATAADAPTER (DATAADAPTERID, DATAADAPTER, LOCATION,
	 * ADAPTERTYPE) values ('S120030005AcctOtherSysSignInfoQuery',
	 * 'sop_service_frame', 'local_out', 'OUT')@
	 */
	private String generateDataAdapterSql() {

		String sysName = prdSysAb;
		if ("LoanQ".equals(sysName) 
				|| "IDP".equals(sysName) || "PWM".equals(sysName)
				|| "YJT".equals(sysName) || "NBH".equals(sysName)) {

		} else {
			sysName = sysName.toLowerCase();
		}
		StringBuilder dataAdapterSqlBuilder = new StringBuilder();
		dataAdapterSqlBuilder
				.append("insert into DATAADAPTER (DATAADAPTERID, DATAADAPTER, LOCATION, ADAPTERTYPE) values ('");
		dataAdapterSqlBuilder.append(serviceId + operationId);
		dataAdapterSqlBuilder.append("','");
		dataAdapterSqlBuilder.append(sysName.toLowerCase());
		dataAdapterSqlBuilder.append("_service_frame', 'local_out', 'OUT')@");
		dataAdapterSqlBuilder.append(LINE_SEPARATOR);
		return dataAdapterSqlBuilder.toString();
	}

	/**
	 * insert into DEPLOYMENTS (ID,LOCATION,NAME,VERSION) values
	 * ('3','local_out','S120030005AcctOtherSysSignInfoQuery','0')@
	 * 
	 * insert into DEPLOYMENTS (ID,LOCATION,NAME,VERSION) values
	 * ('0000','local_out','aaaaa','0')@ update DEPLOYMENTS set
	 * id=to_char((select max(int(id))+1 from deployments)) where name='aaaaa'
	 */
	private String generateDeploymensSql() {
		StringBuilder deploymensSqlBuilder = new StringBuilder();
		deploymensSqlBuilder
				.append("insert into DEPLOYMENTS (ID,LOCATION,NAME,VERSION) values ('0000','local_out','");
		deploymensSqlBuilder.append(serviceId + operationId);
		deploymensSqlBuilder.append("','0')@");
		deploymensSqlBuilder.append(LINE_SEPARATOR);
		deploymensSqlBuilder
				.append("update DEPLOYMENTS set id=to_char((select max(int(id))+1 from deployments)) ");
		deploymensSqlBuilder.append("where name='");
		deploymensSqlBuilder.append(serviceId + operationId);
		deploymensSqlBuilder.append("' and id='0000' @");
		// deploymensSqlBuilder.append(helper.newUUID());//不能用uuid
		// deploymensSqlBuilder.append("','local_out','");

		deploymensSqlBuilder.append(LINE_SEPARATOR);
		return deploymensSqlBuilder.toString();
	}

	/**
	 * insert into SERVICESYSTEMMAP (NAME, SERVICEID, ADAPTER) values
	 * ('SOPSystem', 'S120030005AcctOtherSysSignInfoQuery','sop_adapter')@
	 * 
	 * @param serviceId
	 * @param operationId
	 * @param sysName
	 * @param interfaceType
	 * @return
	 * 
	 * 针对代客代理改动 代客代理有三个应用，不同的地址。三个不同接出协议，指向不同的地址。
	 * mmpp_matrix_adapter平台：http://10.112.12.141:7001/matrix/ws/mmpp
	 * mmpp_margin_adapter双向：http://10.112.12.141:7001/margin/ws/mmpp
	 * mmpp_gold_adapter实物：http://10.112.12.141:7001/gold/ws/mmpp
	 * 开发代客代理的交易时，确认交易所属应用类别，使用对应的接出协议
	 * 
	 */
	private String generateServiceSysMapSql() {
		boolean generatedFlag = false;
		String sysName = prdSysAb;
		if ("LoanQ".equals(sysName) || "SOP".equals(sysName)
				|| "IDP".equals(sysName) || "PWM".equals(sysName)
				|| "YJT".equals(sysName) || "NBH".equals(sysName)) {

		} else {
			sysName = sysName.toLowerCase();
		}
		String adapterName = prdSysAb;
		StringBuilder serviceSysMapSqlBuilder = new StringBuilder();
		Map<String, String> params = new HashMap<String, String>();
		params.put("serviceId", serviceId);
		params.put("operationId", operationId);
		params.put("ecode", interfaceId);
		params.put("provideMsgType", prdMsgType);
		params.put("consumeMsgType", csmMsgType);
		List<InvokeInfo> list = invokeDAO.findBy(params);
		for (InvokeInfo invoke : list) {
			String provideSys = invoke.getProvideSysId();
			String consumeSys = systemDAO.getSystemAbById(invoke
					.getConsumeSysId());
			String funcType = invoke.getFuncType();
			boolean flag = false;
			if ("0204".equals(provideSys)
					|| ("MMPP".equals(consumeSys) && "0001".equals(provideSys))) {
				if ("公共".equals(funcType)) {
					adapterName = "mmpp_matrix";
				} else if ("双向".equals(funcType)) {
					adapterName = "mmpp_margin";
				} else if ("实物".equals(funcType)) {
					adapterName = "mmpp_gold";
				}
				flag = true;
			} else if (generatedFlag) {
				continue;
			}
			serviceSysMapSqlBuilder
					.append("insert into SERVICESYSTEMMAP (NAME, SERVICEID, ADAPTER) values ('");
			serviceSysMapSqlBuilder.append(sysName);
			serviceSysMapSqlBuilder.append("System', '");
			serviceSysMapSqlBuilder.append(serviceId + operationId);
			serviceSysMapSqlBuilder.append("','");
			serviceSysMapSqlBuilder.append(adapterName.toLowerCase());
			serviceSysMapSqlBuilder.append("_adapter')@");
			serviceSysMapSqlBuilder.append(LINE_SEPARATOR);
			generatedFlag = true;
			if(flag){
				break;
			}
		}
		return serviceSysMapSqlBuilder.toString();
	}

	private File generateBussServiceSqlFile() throws IOException {
		String dirPath = serviceId + operationId +"(" + csmMsgType + "-"
		+ prdMsgType + ")" + File.separator
				+ SQL_DIR_PATH;
		String sqlFilePath = dirPath + File.separator
				+ BUSS_SERVICE_SQL_FILE_NAME;
		File sqlFile = null;
		FileOutputStream outputStream = null;
		try {
			sqlFile = new File(sqlFilePath);
			sqlFile.deleteOnExit();
			sqlFile.createNewFile();
			String serviceSql = generateServiceSql();
			log.info("生成sql[" + serviceSql + "]");
			outputStream = new FileOutputStream(sqlFile);
			outputStream.write(serviceSql.getBytes());
			String bussServiceSql = generateBussServiceSql();
			log.info("生成sql[" + bussServiceSql + "]");
			outputStream.write(bussServiceSql.getBytes());
			String serviceInfoSql = generateServiceInfoSql();
			log.info("生成sql[" + serviceInfoSql + "]");
			outputStream.write(serviceInfoSql.getBytes());
			String bindMapSql = generateBindMapSql();
			log.info("生成sql[" + bindMapSql + "]");
			outputStream.write(bindMapSql.getBytes());
			String dataAdapterSql = generateDataAdapterSql();
			log.info("生成sql[" + dataAdapterSql + "]");
			outputStream.write(dataAdapterSql.getBytes());
			String serviceSysMapSql = generateServiceSysMapSql();
			log.info("生成sql[" + serviceSysMapSql + "]");
			outputStream.write(serviceSysMapSql.getBytes());

			String deploymentsSql = generateDeploymensSql();
			log.info("生成sql[" + deploymentsSql + "]");
			outputStream.write(deploymentsSql.getBytes());
			// 去掉commit@
			// String commit = generateCommit();
			// outputStream.write(commit.getBytes());

			outputStream.flush();
		} catch (IOException e) {
			log.error("创建文件[" + sqlFilePath + "]失败！");
			throw e;
		} finally {
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("关闭文件流失败", e);
					throw e;
				}
			}
		}
		return sqlFile;
	}

	/**
	 * insert into TBL_SERVICE_IDENTIFY (CHANNELID, TYPE, IDENTIFY_MODE, ENCODE,
	 * IDENTIFY_SWITCH, IDENTIFY_CASE, SERVICENAME) values ('soap', 'dynamic',
	 * 'xml', 'utf-8', 'expression="$ServiceAdr$ServiceAction"',
	 * 'S010010001urn:/BankAcptMrgnOpenAndDep',
	 * 'S010010001BankAcptMrgnOpenAndDep')@
	 * 
	 * @throws Exception
	 */
	private String generateServiceIdentifySql() throws Exception {
		StringBuilder serviceIdentifySqlBuilder = new StringBuilder();
		Map<String, String> params = new HashMap<String, String>();
		params.put("serviceId", serviceId);
		params.put("operationId", operationId);
		params.put("ecode", interfaceId);
		params.put("provideMsgType", prdMsgType);
		params.put("consumeMsgType", csmMsgType);
		List<InvokeInfo> list = invokeDAO.findBy(params);
		String csm_codeType = getSysCodingType(list.get(0).getConsumeSysId(),
				csmMsgType, 0);
		if ("soap".equalsIgnoreCase(csmMsgType.toLowerCase().trim())) {
			serviceIdentifySqlBuilder
					.append("insert into TBL_SERVICE_IDENTIFY (CHANNELID, TYPE, IDENTIFY_MODE, ENCODE, IDENTIFY_SWITCH, IDENTIFY_CASE, SERVICENAME) values ('");
			serviceIdentifySqlBuilder.append(csmMsgType.toLowerCase());
			serviceIdentifySqlBuilder.append("','");
			serviceIdentifySqlBuilder.append("dynamic', 'xml', '");
			serviceIdentifySqlBuilder.append(csm_codeType);
			serviceIdentifySqlBuilder
					.append("', 'expression=\"$ServiceAdr$ServiceAction\"', '");
			serviceIdentifySqlBuilder.append(serviceId);
			serviceIdentifySqlBuilder.append("urn:/");
			serviceIdentifySqlBuilder.append(operationId);
			serviceIdentifySqlBuilder.append("', '");
			serviceIdentifySqlBuilder.append(serviceId);
			serviceIdentifySqlBuilder.append(operationId);
			serviceIdentifySqlBuilder.append("')@");
			serviceIdentifySqlBuilder.append(LINE_SEPARATOR);
		} else if ("sop".equalsIgnoreCase(csmMsgType.toLowerCase().trim())) {
			serviceIdentifySqlBuilder
					.append("insert into TBL_SERVICE_IDENTIFY (CHANNELID, TYPE, IDENTIFY_MODE, ENCODE, IDENTIFY_SWITCH, IDENTIFY_CASE, SERVICENAME) values ('");
			serviceIdentifySqlBuilder.append(csmMsgType.toLowerCase());
			serviceIdentifySqlBuilder.append("','");
			serviceIdentifySqlBuilder.append("dynamic', 'fix', '");
			serviceIdentifySqlBuilder.append(csm_codeType);
			serviceIdentifySqlBuilder
					.append("', 'expression=\"offset=76,length=4\"', '");
			serviceIdentifySqlBuilder.append(interfaceId);
			serviceIdentifySqlBuilder.append("', '");
			serviceIdentifySqlBuilder.append(serviceId);
			serviceIdentifySqlBuilder.append(operationId);
			serviceIdentifySqlBuilder.append("')@");
			serviceIdentifySqlBuilder.append(LINE_SEPARATOR);
		} else {
			throw new Exception("暂时不支持报文类型[" + csmMsgType + "]");
		}
		return serviceIdentifySqlBuilder.toString();
	}

	private File generateServiceIdentifySqlFile() {
		File sqlFile = null;
		FileOutputStream outputStream = null;
		String dirPath = serviceId + operationId +"(" + csmMsgType + "-"
		+ prdMsgType + ")" + File.separator
				+ SQL_DIR_PATH;
		String sqlFilePath = dirPath + File.separator
				+ SERVICE_IDENTIFY_SQL_FILE_NALE;
		try {
			sqlFile = new File(sqlFilePath);
			sqlFile.deleteOnExit();
			sqlFile.createNewFile();
			outputStream = new FileOutputStream(sqlFile);
			String serviceIdentifySql = generateServiceIdentifySql();
			log.info("生成sql[" + serviceIdentifySql + "]");
			outputStream.write(serviceIdentifySql.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			log.error("创建sql文件[" + sqlFilePath + "]失败!", e);
		} catch (Exception e) {
			log.error("生成sql文件[" + sqlFilePath + "]失败！", e);
		} finally {
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("关闭文件[" + sqlFilePath + "]输出流失败！");
				}
			}
		}
		return sqlFile;
	}

	/**
	 * insert into TBL_ESB_SERVICE_BASE_INFO (SERVICE_ID, PROVIDER_ID,
	 * SERVICE_TIMEOUT, SERVICE_STATUS, REMARK) values
	 * ('S120030008AcctCmphSignStatusQuery','0202','10000','0','')@
	 * 
	 * modify by pangzt 超时时间设为60s 2014-04-21
	 * 
	 * @param serviceId
	 * @param operationId
	 * @param sysName
	 * @param interfaceType
	 * @return
	 */
	private String generateServiceBaseInfoSql() {

		StringBuilder serviceBaseInfoSqlBuilder = new StringBuilder();
		serviceBaseInfoSqlBuilder
				.append("insert into TBL_ESB_SERVICE_BASE_INFO (SERVICE_ID, PROVIDER_ID, SERVICE_TIMEOUT, SERVICE_STATUS, REMARK, SERVICE_NAME, LOG_LEVEL) values ('");
		serviceBaseInfoSqlBuilder.append(serviceId);
		serviceBaseInfoSqlBuilder.append(operationId);
		serviceBaseInfoSqlBuilder.append("' ,'");
		serviceBaseInfoSqlBuilder.append(prdSysId);
		serviceBaseInfoSqlBuilder.append("', '60000','o','','" + serviceName
				+ "--" + operationName + "','1')@");
		serviceBaseInfoSqlBuilder.append(LINE_SEPARATOR);
		return serviceBaseInfoSqlBuilder.toString();
	}

	public File generateServiceInfoSqlFile() {
		File sqlFile = null;
		FileOutputStream outputStream = null;
		String dirPath = serviceId + operationId +"(" + csmMsgType + "-"
		+ prdMsgType + ")" + File.separator
				+ SQL_DIR_PATH;
		String sqlFilePath = dirPath + File.separator
				+ SERVICE_INFO_SQL_FILE_NAME;
		try {
			sqlFile = new File(sqlFilePath);
			sqlFile.deleteOnExit();
			sqlFile.createNewFile();
			outputStream = new FileOutputStream(sqlFile);
			String serviceInfoSql = generateServiceBaseInfoSql();
			log.info("生成sql[" + serviceInfoSql + "]");
			outputStream.write(serviceInfoSql.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			log.error("创建sql文件[" + sqlFilePath + "]失败!", e);
		} catch (Exception e) {
			log.error("生成sql文件[" + sqlFilePath + "]失败！", e);
		} finally {
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("关闭文件[" + sqlFilePath + "]输出流失败！");
				}
			}
		}
		return sqlFile;
	}

	private class ConsumerProviderPair {
		private String consumerId;
		private String providerId;

		public String getConsumerId() {
			return consumerId;
		}

		public void setConsumerId(String consumerId) {
			this.consumerId = consumerId;
		}

		public String getProviderId() {
			return providerId;
		}

		public void setProviderId(String providerId) {
			this.providerId = providerId;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ConsumerProviderPair)) {
				return false;
			}
			ConsumerProviderPair another = (ConsumerProviderPair) obj;
			return ((null == this.consumerId) ? (null == another
					.getConsumerId()) : (this.consumerId.equals(another
					.getConsumerId())))
					&& ((null == this.providerId) ? (null == another
							.getProviderId()) : (this.providerId.equals(another
							.getProviderId())));
		}

		@Override
		public int hashCode() {
			int hashCode = 0;
			if (null != consumerId) {
				hashCode ^= consumerId.hashCode();
			}
			if (null != providerId) {
				hashCode ^= providerId.hashCode();
			}
			return hashCode;
		}
	}

	private String generateServiceCtrl(String serviceStatus, String remark) {
		StringBuilder serviceCtrlSqlBuilder = new StringBuilder();
		List<ConsumerProviderPair> cpPairs = new ArrayList<ConsumerProviderPair>();
		// 获取消费方id
		String consumeSys = null;
		String passedSys = null;
		String providerId = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("serviceId", serviceId);
		params.put("operationId", operationId);
		params.put("ecode", interfaceId);
		params.put("provideMsgType", prdMsgType);
		params.put("consumeMsgType", csmMsgType);
		List<InvokeInfo> list = invokeDAO.findBy(params);
		for (InvokeInfo invoke : list) {
			consumeSys = systemDAO.getSystemAbById(invoke.getConsumeSysId());
			passedSys = invoke.getPassbySysId();
			providerId = invoke.getProvideSysId();
			if (consumeSys != null && consumeSys.startsWith("ZHIPP")) {
				consumeSys = "ZHIPP";
			} else if (passedSys != null && !"".equals(passedSys)) {
				consumeSys = passedSys;
			}
			// skip dup
			ConsumerProviderPair cpPair = new ConsumerProviderPair();
			cpPair.setConsumerId(consumeSys);
			cpPair.setProviderId(providerId);
			if (cpPairs.contains(cpPair)) {
				continue;
			} else {
				cpPairs.add(cpPair);
			}
			String orgId = systemDAO.getSystemIdByAb(consumeSys);
			serviceCtrlSqlBuilder
					.append("insert into TBL_ESB_SERVICE_CTRL "
							+ "(SERVICE_ID, CONSUMER_ID, PROVIDER_ID,SERVICE_STATUS, REMARK) values ('");
			serviceCtrlSqlBuilder.append(serviceId);
			serviceCtrlSqlBuilder.append(operationId);
			serviceCtrlSqlBuilder.append("' ,'");
			serviceCtrlSqlBuilder.append(orgId);
			serviceCtrlSqlBuilder.append("' ,'");
			serviceCtrlSqlBuilder.append(providerId);
			serviceCtrlSqlBuilder.append("' ,'");
			serviceCtrlSqlBuilder.append(serviceStatus);
			serviceCtrlSqlBuilder.append("' ,'");
			serviceCtrlSqlBuilder.append(remark);
			serviceCtrlSqlBuilder.append("')@");
			serviceCtrlSqlBuilder.append(LINE_SEPARATOR);
		}
		if (serviceCtrlSqlBuilder.toString() == null
				|| "".equals(serviceCtrlSqlBuilder.toString())) {
			serviceCtrlSqlBuilder.append("调用方系统ID为空！导出sql内容失败!");
		}
		// System.out.println(serviceCtrlSqlBuilder.toString());
		return serviceCtrlSqlBuilder.toString();
	}

	private File generateServiceCtrlSqlFile(String serviceStatus, String remark) {
		File sqlFile = null;
		FileOutputStream outputStream = null;
		String dirPath = serviceId + operationId +"(" + csmMsgType + "-"
		+ prdMsgType + ")" + File.separator
				+ SQL_DIR_PATH;
		String sqlFilePath = dirPath + File.separator
				+ SERVICE_CTRL_SQL_FILE_NAME;
		try {
			sqlFile = new File(sqlFilePath);
			sqlFile.deleteOnExit();
			sqlFile.createNewFile();
			outputStream = new FileOutputStream(sqlFile);
			String serviceCtrlSql = generateServiceCtrl(serviceStatus, remark);
			log.info("生成sql[" + serviceCtrlSql + "]");
			outputStream.write(serviceCtrlSql.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			log.error("创建sql文件[" + sqlFilePath + "]失败!", e);
		} catch (Exception e) {
			log.error("生成sql文件[" + sqlFilePath + "]失败！", e);
		} finally {
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("关闭文件[" + sqlFilePath + "]输出流失败！");
				}
			}
		}
		return sqlFile;
	}

	private String generateServiceFlowCtrl(String serviceId, String operationId) {
		StringBuilder serviceFlowCtrlSqlBuilder = new StringBuilder();
		serviceFlowCtrlSqlBuilder
				.append("insert into ESB2_FLOW_CTRL (CHANNELID,SERVICEID,TOKENTOTALQUANTITY,INMULTIDATE,INDATE,INWEEK,INMONTH,INYEAR,CONTROLID,PROVIDERID) values ('");
		serviceFlowCtrlSqlBuilder.append("all','");
		serviceFlowCtrlSqlBuilder.append(serviceId + operationId);
		serviceFlowCtrlSqlBuilder.append("','20','','','','','','");
		serviceFlowCtrlSqlBuilder.append(UUID.randomUUID().toString());
		serviceFlowCtrlSqlBuilder.append("','')@");
		return serviceFlowCtrlSqlBuilder.toString();
	}

	private File generateServiceFlowCtrlFile() {
		File sqlFile = null;
		FileOutputStream fileOutputSream = null;
		String dirPath = serviceId + operationId +"(" + csmMsgType + "-"
		+ prdMsgType + ")" + File.separator
				+ SQL_DIR_PATH;
		String sqlFilePath = dirPath + File.separator
				+ FLOW_CTRL_SQL_FILE_PATH_NAME;

		try {
			sqlFile = new File(sqlFilePath);
			sqlFile.deleteOnExit();
			sqlFile.createNewFile();
			fileOutputSream = new FileOutputStream(sqlFile);
			String serviceFlowCtrl = generateServiceFlowCtrl(serviceId,
					operationId);
			log.info("生成sql[" + serviceFlowCtrl + "]");
			fileOutputSream.write(serviceFlowCtrl.getBytes());
			fileOutputSream.flush();
		} catch (FileNotFoundException e) {
			log.error("创建sqlFile[" + sqlFilePath + "]出错", e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("生成sqlFile[" + sqlFilePath + "]出错", e);
			e.printStackTrace();
		} finally {
			try {
				fileOutputSream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sqlFile;
	}

	/**
	 * 生成serviceLogCfg语句sql 1、4是调用方报文类型 2、3是提供方报文类型 soap、xml 标识是0 sop、fix 标识是1
	 */
	private String generateServiceLogCfgSql() {
		StringBuilder serviceInfoSqlBuilder = new StringBuilder();
		String csm_hexprt = "";
		String csm_serv_type = "";
		String prd_hexprd = "";
		String prd_serv_type = "";
		// 编码格式
		String csm_codeType = "";
		String prd_codeType = "";
		Map<String, String> params = new HashMap<String, String>();
		params.put("serviceId", serviceId);
		params.put("operationId", operationId);
		params.put("ecode", interfaceId);
		params.put("provideMsgType", prdMsgType);
		params.put("consumeMsgType", csmMsgType);
		List<InvokeInfo> list = invokeDAO.findBy(params);
		if (list != null && list.size() > 0) {
			InvokeInfo invoke = list.get(0);
			// 调用方 、 提供方报文类型
			String prdId = invoke.getProvideSysId();
			String csmId = invoke.getConsumeSysId();
			if (csmId != null && !"".equals(csmId)) {
				csm_codeType = getSysCodingType(csmId, csmMsgType, 0);
			}
			prd_codeType = getSysCodingType(prdId, prdMsgType, 1);
			if ("soap".equalsIgnoreCase(csmMsgType)
					|| "xml".equalsIgnoreCase(csmMsgType)) {
				csm_hexprt = "0";
				csm_serv_type = csmMsgType;
			}
			if ("sop".equalsIgnoreCase(csmMsgType)
					|| "fix".equalsIgnoreCase(csmMsgType)) {
				csm_hexprt = "1";
				csm_serv_type = csmMsgType;
			}
			if ("soap".equalsIgnoreCase(prdMsgType)
					|| "xml".equalsIgnoreCase(prdMsgType)) {
				prd_hexprd = "0";
				prd_serv_type = prdMsgType;
			}
			if ("sop".equalsIgnoreCase(prdMsgType)
					|| "fix".equalsIgnoreCase(prdMsgType)) {
				prd_hexprd = "1";
				prd_serv_type = prdMsgType;
			}

			serviceInfoSqlBuilder
					.append("insert into TBL_ESB_SERVICE_LOG_CFG (SERV_ID,HEXPRT,ENCODING,SERV_TYPE,FLOW_STEP,FILTER_FIELDS,JOURNAL_FIELDS) values ('");
			serviceInfoSqlBuilder.append(serviceId + operationId);
			serviceInfoSqlBuilder.append("','");
			serviceInfoSqlBuilder.append(csm_hexprt);
			serviceInfoSqlBuilder.append("','");
			serviceInfoSqlBuilder.append(csm_codeType);
			serviceInfoSqlBuilder.append("','");
			serviceInfoSqlBuilder.append(csm_serv_type.toLowerCase());
			serviceInfoSqlBuilder
					.append("','1','','/sdoroot/ReqSvcHeader/TranTellerNo;/sdoroot/ReqSvcHeader/BranchId;;;;')@");
			serviceInfoSqlBuilder.append(LINE_SEPARATOR);

			serviceInfoSqlBuilder
					.append("insert into TBL_ESB_SERVICE_LOG_CFG (SERV_ID,HEXPRT,ENCODING,SERV_TYPE,FLOW_STEP,FILTER_FIELDS,JOURNAL_FIELDS) values ('");
			serviceInfoSqlBuilder.append(serviceId + operationId);
			serviceInfoSqlBuilder.append("','");
			serviceInfoSqlBuilder.append(prd_hexprd);
			serviceInfoSqlBuilder.append("','");
			serviceInfoSqlBuilder.append(prd_codeType);
			serviceInfoSqlBuilder.append("','");
			serviceInfoSqlBuilder.append(prd_serv_type.toLowerCase());
			serviceInfoSqlBuilder.append("','2','',';;;;;')@");
			serviceInfoSqlBuilder.append(LINE_SEPARATOR);

			serviceInfoSqlBuilder
					.append("insert into TBL_ESB_SERVICE_LOG_CFG (SERV_ID,HEXPRT,ENCODING,SERV_TYPE,FLOW_STEP,FILTER_FIELDS,JOURNAL_FIELDS) values ('");
			serviceInfoSqlBuilder.append(serviceId + operationId);
			serviceInfoSqlBuilder.append("','");
			serviceInfoSqlBuilder.append(prd_hexprd);
			serviceInfoSqlBuilder.append("','");
			serviceInfoSqlBuilder.append(prd_codeType);
			serviceInfoSqlBuilder.append("','");
			serviceInfoSqlBuilder.append(prd_serv_type.toLowerCase());
			serviceInfoSqlBuilder.append("','3','',';;;;;')@");
			serviceInfoSqlBuilder.append(LINE_SEPARATOR);

			serviceInfoSqlBuilder
					.append("insert into TBL_ESB_SERVICE_LOG_CFG (SERV_ID,HEXPRT,ENCODING,SERV_TYPE,FLOW_STEP,FILTER_FIELDS,JOURNAL_FIELDS) values ('");
			serviceInfoSqlBuilder.append(serviceId + operationId);
			serviceInfoSqlBuilder.append("','");
			serviceInfoSqlBuilder.append(csm_hexprt);
			serviceInfoSqlBuilder.append("','");
			serviceInfoSqlBuilder.append(csm_codeType);
			serviceInfoSqlBuilder.append("','");
			serviceInfoSqlBuilder.append(csm_serv_type.toLowerCase());
			serviceInfoSqlBuilder.append("','4','',';;;;;')@");
			serviceInfoSqlBuilder.append(LINE_SEPARATOR);
		} else {
			serviceInfoSqlBuilder
					.append("Not found service_relat info , Export 19_esbdata_dml_service_log_cfg.sql failed!");
		}
		return serviceInfoSqlBuilder.toString();
	}

	private File generateServiceLogCfgSqlFile() {
		File sqlFile = null;
		FileOutputStream outputStream = null;
		String dirPath = serviceId + operationId +"(" + csmMsgType + "-"
		+ prdMsgType + ")" + File.separator
				+ SQL_DIR_PATH;
		String sqlFilePath = dirPath + File.separator
				+ SERVICE_LOG_CFG_SQL_FILE_NAME;
		try {
			sqlFile = new File(sqlFilePath);
			sqlFile.deleteOnExit();
			sqlFile.createNewFile();
			outputStream = new FileOutputStream(sqlFile);
			String serviceLogCfgSql = generateServiceLogCfgSql();
			log.info("生成sql[" + serviceLogCfgSql + "]");
			outputStream.write(serviceLogCfgSql.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			log.error("创建sql文件[" + sqlFilePath + "]失败!", e);
		} catch (Exception e) {
			log.error("生成sql文件[" + sqlFilePath + "]失败！", e);
		} finally {
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("关闭文件[" + sqlFilePath + "]输出流失败！");
				}
			}
		}
		return sqlFile;
	}

	private String generateSpeHandleInfo() {
		StringBuilder speHandleSqlBuilder = new StringBuilder();

		speHandleSqlBuilder
				.append("insert into tbl_spe_handle_info (SERVICE_ID, CHANNEL_ID, CONSUMER_ID, "
						+ "NODETYPE, CONFIG_PRE, CONFIG_POS, HANDLE_MODE, HANDLE_EXPRESS, MAC_ENABLE, REMARK, CREATE_TIME, MODIFY_TIME) values ('");
		speHandleSqlBuilder.append(serviceId + operationId);
		speHandleSqlBuilder.append("','");
		speHandleSqlBuilder.append(csmMsgType.toLowerCase());
		speHandleSqlBuilder.append("','','1','");
		speHandleSqlBuilder.append(csmMsgType.toLowerCase());
		speHandleSqlBuilder.append("','");
		if ("SOAP".equals(csmMsgType)) {
			speHandleSqlBuilder.append("S000000000Common");
		} else {
			speHandleSqlBuilder.append("S000000000SOPCommon");
		}
		speHandleSqlBuilder.append("','1','',");
		if ("0001".equals(prdSysId)) {
			speHandleSqlBuilder.append("'true',");
		} else {
			speHandleSqlBuilder.append("'',");
		}
		speHandleSqlBuilder.append("'',CURRENT TIMESTAMP, CURRENT TIMESTAMP)@");
		speHandleSqlBuilder.append(LINE_SEPARATOR);

		speHandleSqlBuilder
				.append("insert into tbl_spe_handle_info (SERVICE_ID, CHANNEL_ID, CONSUMER_ID, "
						+ "NODETYPE, CONFIG_PRE, CONFIG_POS, HANDLE_MODE, HANDLE_EXPRESS, MAC_ENABLE, REMARK, CREATE_TIME, MODIFY_TIME) values ('");
		speHandleSqlBuilder.append(serviceId + operationId);
		speHandleSqlBuilder.append("','");
		speHandleSqlBuilder.append(csmMsgType.toLowerCase());
		speHandleSqlBuilder.append("','','2'");
		speHandleSqlBuilder.append(",'");
		if ("SOAP".equals(csmMsgType)) {
			speHandleSqlBuilder.append("Common','");
			speHandleSqlBuilder.append("S000000000Common");
		} else {
			speHandleSqlBuilder.append("SOPCommon','");
			speHandleSqlBuilder.append("S000000000SOPCommon");
		}
		speHandleSqlBuilder.append("','1','',");
		if ("0001".equals(prdSysId)) {
			speHandleSqlBuilder.append("'true',");
		} else {
			speHandleSqlBuilder.append("'',");
		}
		speHandleSqlBuilder.append("'',CURRENT TIMESTAMP, CURRENT TIMESTAMP)@");
		speHandleSqlBuilder.append(LINE_SEPARATOR);

		speHandleSqlBuilder
				.append("insert into tbl_spe_handle_info (SERVICE_ID, CHANNEL_ID, CONSUMER_ID, "
						+ "NODETYPE, CONFIG_PRE, CONFIG_POS, HANDLE_MODE, HANDLE_EXPRESS, MAC_ENABLE, REMARK, CREATE_TIME, MODIFY_TIME) values ('");
		speHandleSqlBuilder.append(serviceId + operationId);
		speHandleSqlBuilder.append("','");
		speHandleSqlBuilder.append(csmMsgType.toLowerCase());
		speHandleSqlBuilder.append("','','3',");
		speHandleSqlBuilder.append("'");
		if ("SOAP".equals(csmMsgType)) {
			speHandleSqlBuilder.append("Common','");
			speHandleSqlBuilder.append("S000000000Common");
		} else {
			speHandleSqlBuilder.append("SOPCommon','");
			speHandleSqlBuilder.append("S000000000SOPCommon");
		}
		speHandleSqlBuilder
				.append("','1','','','',CURRENT TIMESTAMP, CURRENT TIMESTAMP)@");
		speHandleSqlBuilder.append(LINE_SEPARATOR);

		speHandleSqlBuilder
				.append("insert into tbl_spe_handle_info (SERVICE_ID, CHANNEL_ID, CONSUMER_ID, "
						+ "NODETYPE, CONFIG_PRE, CONFIG_POS, HANDLE_MODE, HANDLE_EXPRESS, MAC_ENABLE, REMARK, CREATE_TIME, MODIFY_TIME) values ('");
		speHandleSqlBuilder.append(serviceId + operationId);
		speHandleSqlBuilder.append("','");
		speHandleSqlBuilder.append(csmMsgType.toLowerCase());
		speHandleSqlBuilder.append("','','4','");
		speHandleSqlBuilder.append(csmMsgType.toLowerCase());
		speHandleSqlBuilder.append("','");
		if ("SOAP".equals(csmMsgType)) {
			speHandleSqlBuilder.append("S000000000Common");
		} else {
			speHandleSqlBuilder.append("S000000000SOPCommon");
		}
		speHandleSqlBuilder
				.append("','1','','','',CURRENT TIMESTAMP, CURRENT TIMESTAMP)@");
		speHandleSqlBuilder.append(LINE_SEPARATOR);
		return speHandleSqlBuilder.toString();
	}

	@SuppressWarnings("unused")
	private File generateSpeHandleSqlFile() {
		File sqlFile = null;
		FileOutputStream outputStream = null;
		String dirPath = serviceId + operationId +"(" + csmMsgType + "-"
		+ prdMsgType + ")" + File.separator
				+ SQL_DIR_PATH;
		String sqlFilePath = dirPath + File.separator + TBL_SPE_HANDLE_INFO;
		try {
			sqlFile = new File(sqlFilePath);
			sqlFile.deleteOnExit();
			sqlFile.createNewFile();
			outputStream = new FileOutputStream(sqlFile);
			String speHandleInfoSql = generateSpeHandleInfo();
			log.info("生成sql[" + speHandleInfoSql + "]");
			outputStream.write(speHandleInfoSql.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			log.error("创建sql文件[" + sqlFilePath + "]失败!", e);
		} catch (Exception e) {
			log.error("生成sql文件[" + sqlFilePath + "]失败！", e);
		} finally {
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("关闭文件[" + sqlFilePath + "]输出流失败！");
				}
			}
		}
		return sqlFile;
	}

	// 获取调用方、提供方的编码格式
	private String getSysCodingType(String orgid, String msgtype, int systype) {
		String CodeType = "";
		Map<String, String> vparams = new HashMap<String, String>();
		vparams.put("sysId", orgid);
		if (systype == 0) {
			vparams.put("sysType", "调用方");
		} else {
			vparams.put("sysType", "提供方");
		}
		vparams.put("msgType", msgtype);
		List<ProtocolInfo> list = protocolDAO.findBy(vparams);
		if (list != null && list.size() > 0) {
			CodeType = list.get(0).getCodeType();
		}
		return CodeType;
	}
}
