package com.dc.esb.servicegov.refactoring.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class ESBConfigSysSqlGenerator{
	private static final Log log = LogFactory
			.getLog(ESBConfigSysSqlGenerator.class);


	public static final String SYS_SQL_DIR_PATH = "newsystemsql";
	public static final String PROTOCOL_SQL_DIR_PATH= "01_esbdata_dml_protocol.sql";
	public static final String ADAPTER_FRAME_SQL_DIR_PATH= "03_esbdata_dml_adapteframe.sql";
	public static final String SERVICESYSTEM_SQL_DIR_PATH= "04_esbdata_dml_servicesystem.sql";
	public static final String SYSINFO_SQL_DIR_PATH= "11_esbdata_dml_sysinfo.sql";
	public static final String SYSFLOW_CTRL_SQL_FILE_PATH_NAME = "16_esbdata_dml_esb2_flow_ctrl.sql";	
	public static final String MEMQUEUE_SQL_DIR_PATH= "21_esbdata_dml_memqueue.sql";
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	public File[] exportSysSql(Map<String,String> paramMap) {
		File runtimeFile = null;
		File[] rf = null;
		String tempPath = null;
		try {
			if (null != paramMap) {
				Map<String, File> filesMap = new HashMap<String, File>();
				ESBConfigSysSqlGenerator sqlGenerator = new ESBConfigSysSqlGenerator();
				List<File> sqlFiles = sqlGenerator
						.generate(paramMap);
				merageFile(filesMap, sqlFiles);
				tempPath = "ESB_SYSSQL_"+ new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
				runtimeFile = new File(tempPath+".zip");
				log.info("导出系统级SQL名称[" + runtimeFile.getName() + "]");
				rf = filesFromMap(filesMap);
//				DBTool tool = new DBTool();
//				// TODO
//				tool.compress(rf, runtimeFile.getAbsolutePath(), true);
//				// TODO
//				tool.response(tool.readAndDelFile(runtimeFile),
//						"application/x-zip-compressed", runtimeFile.getName(),
//						response);
				return rf;
			}
		} catch (Exception e) {
			log.error("[SQL Generate Engine]:fail to generate configs", e);
		} finally {
			File fileToDelete = new File(tempPath);
			log.info("开始删除临时文件["+fileToDelete.getAbsolutePath()+"]");
			if(log.isInfoEnabled()){
				log.info("开始删除临时文件["+fileToDelete.getAbsolutePath()+"]");
			}
			boolean deleteResult = deleteFile(fileToDelete);
			if(!deleteResult){
				log.error("删除临时文件["+fileToDelete.getAbsolutePath()+"]失败！");
			}
			if(null != runtimeFile){
				runtimeFile.delete();
			}
		}
		return null;
	}

	public List<File> generate(Map<String,String> paramMap)throws Exception {
		String systype = paramMap.get("systype");
		String providerId = paramMap.get("providerId");
		String protocolType = paramMap.get("protocolType");
		String providerName = paramMap.get("providerName");
		String providerName_CN = paramMap.get("providerName_CN");
		String providerMsgType = paramMap.get("providerMsgType");
		String consumerId = paramMap.get("consumerId");
		String consumerName = paramMap.get("consumerName");
		String consumerName_CN = paramMap.get("consumerName_CN");
		List<File> sqlDirs = new ArrayList<File>();
		String sysdirPath = SYS_SQL_DIR_PATH;
		File syssqlDir = new File(sysdirPath);
		syssqlDir.deleteOnExit();
		syssqlDir.mkdirs();
		if("0".equals(systype)){
			generateProtocolFile(providerName, protocolType);
			generateAdapteframeFile(providerName, providerMsgType);
			generateServiceSystemFile(providerName, protocolType, providerName_CN);
			generateSysInfoFile(providerName, providerName_CN, providerId);
			generateProviderSysFlowCtrlFile(providerId);
			generateMemQueueFile(providerId);
		}else if("1".equals(systype)){
			generateConsumerSysFlowCtrlFile(consumerId);
			generateSysInfoFile(consumerName, consumerName_CN, consumerId);
		}else if("2".equals(systype)){
			generateProtocolFile(providerName, protocolType);
			generateAdapteframeFile(providerName, providerMsgType);
			generateServiceSystemFile(providerName, protocolType, providerName_CN);
			generateSysInfoFile(providerName, providerName_CN, providerId, consumerName, consumerName_CN, consumerId);
			generateSysFlowCtrlFile(providerId, consumerId);
			generateMemQueueFile(providerId);
		}
		sqlDirs.add(syssqlDir);
		paramMap = null;
		return sqlDirs;
	}
	private String generateProtocol(String sysName,String protocolType){
			if("LoanQ".equals(sysName) || "SOP".equals(sysName) || "IDP".equals(sysName)
					|| "NBH".equals(sysName)){
				
			}else{
				sysName = sysName.toLowerCase();
			}
			StringBuilder protocolSqlBuilder = new StringBuilder();
			protocolSqlBuilder.append("insert into PROTOCOLBIND (PROTOCOLID, BINDTYPE, BINDURI, REQUESTADAPTER, RESPONSEADAPTER, THREADPOOL) values('");
			protocolSqlBuilder.append(sysName);
			protocolSqlBuilder.append("_adapter','");
			protocolSqlBuilder.append(protocolType);
			protocolSqlBuilder.append("','");		
			protocolSqlBuilder.append(Protocols.getProtocol(protocolType).replaceAll("sg_"+protocolType.toLowerCase(), sysName+"_adapter"));
			protocolSqlBuilder.append("','null', 'null', 'null')@");
			protocolSqlBuilder.append(LINE_SEPARATOR);
			protocolSqlBuilder.append("insert into BINDMAP (SERVICEID, STYPE, LOCATION, VERSION, PROTOCOLID, MAPTYPE) values ('");
			protocolSqlBuilder.append("local_out', 'SERVICE', 'local_out', '', '");
			protocolSqlBuilder.append(sysName);
			protocolSqlBuilder.append("_adapter', 'LOCATION')@");
			protocolSqlBuilder.append(LINE_SEPARATOR);
			return protocolSqlBuilder.toString();
	}
	private File generateProtocolFile(String sysName,String protocolType){
			File sqlFile = null;
			FileOutputStream fileOutputSream = null;
			String dirPath =SYS_SQL_DIR_PATH;
			String sqlFilePath = dirPath + File.separator
				+ PROTOCOL_SQL_DIR_PATH;

			try {
				sqlFile = new File(sqlFilePath);
				sqlFile.deleteOnExit();		
				sqlFile.createNewFile();		
				fileOutputSream = new FileOutputStream(sqlFile);
				String protocol = generateProtocol(sysName,protocolType);
				log.info("生成sql["+protocol+"]");
				fileOutputSream.write(protocol.getBytes());
				fileOutputSream.flush();
			} catch (FileNotFoundException e) {
				log.error("创建sqlFile["+sqlFilePath+"]出错", e);
				e.printStackTrace();
			}catch (IOException e) {
				log.error("生成sqlFile["+sqlFilePath+"]出错", e);
				e.printStackTrace();
			}finally{
				try {
					fileOutputSream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sqlFile;
	}
	private String generateAdapteframe(String sysName,String providerMsgType){
			if("LoanQ".equals(sysName) || "SOP".equals(sysName) || "IDP".equals(sysName)
					|| "NBH".equals(sysName)){
				
			}else{
				sysName = sysName.toLowerCase();
			}
			StringBuilder adapteframeSqlBuilder = new StringBuilder();
			adapteframeSqlBuilder.append("insert into ESB_ADAPTER_TEMPLATE (NAME, ADAPTERS, TYPE, REMARK, PROTOCOLADAPTER) values ('");
			adapteframeSqlBuilder.append(sysName);
			adapteframeSqlBuilder.append("_service_frame','");
			adapteframeSqlBuilder.append("SystemIdentify|false|SYSTEMIDENTIFY,");
			adapteframeSqlBuilder.append("PackService|false|PACKUNPACK,");
			adapteframeSqlBuilder.append("OutputSpeciHandleService|false|IDENTIFY,");
			adapteframeSqlBuilder.append("JournalLogService|true|JOURNAL,");
			adapteframeSqlBuilder.append("default|false|OTHER,");
			adapteframeSqlBuilder.append("FlowRecycleOutService|true|PACKUNPACK,");
			adapteframeSqlBuilder.append("UnpackService|false|PACKUNPACK,");
			adapteframeSqlBuilder.append("InputSpeciHandleService|false|IDENTIFY,");
			
			if("SOAP".equals(providerMsgType)){
				adapteframeSqlBuilder.append("SoapExceptionCheckService|false|PACKUNPACK,");
			}
			
			adapteframeSqlBuilder.append("JournalLogService|true|JOURNAL'");
			adapteframeSqlBuilder.append(",'OUT");		
			adapteframeSqlBuilder.append("','', '0')@");
			adapteframeSqlBuilder.append(LINE_SEPARATOR);
			return adapteframeSqlBuilder.toString();
	}
	private File generateAdapteframeFile(String sysName,String providerMsgType){
			File sqlFile = null;
			FileOutputStream fileOutputSream = null;
			String dirPath = SYS_SQL_DIR_PATH;
			String sqlFilePath = dirPath + File.separator
				+ ADAPTER_FRAME_SQL_DIR_PATH;

			try {
				sqlFile = new File(sqlFilePath);
				sqlFile.deleteOnExit();		
				sqlFile.createNewFile();		
				fileOutputSream = new FileOutputStream(sqlFile);
				String adapteframe = generateAdapteframe(sysName, providerMsgType);
				log.info("生成sql["+adapteframe+"]");
				fileOutputSream.write(adapteframe.getBytes());
				fileOutputSream.flush();
			} catch (FileNotFoundException e) {
				log.error("创建sqlFile["+sqlFilePath+"]出错", e);
				e.printStackTrace();
			}catch (IOException e) {
				log.error("生成sqlFile["+sqlFilePath+"]出错", e);
				e.printStackTrace();
			}finally{
				try {
					fileOutputSream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sqlFile;
	}
	private String generateServiceSystem(String sysName,String protocolType,String sysName_CN){
			if("LoanQ".equals(sysName) || "SOP".equals(sysName) || "IDP".equals(sysName)
					|| "NBH".equals(sysName)){				
			}else{
				sysName = sysName.toLowerCase();
			}
			StringBuilder servicesystemSqlBuilder = new StringBuilder();
			servicesystemSqlBuilder.append("insert into SERVICESYSTEM (NAME, DESCRIPTION, REALCHANNEL, ADAPTER) values ('");
			servicesystemSqlBuilder.append(sysName);
			servicesystemSqlBuilder.append("System','");
			servicesystemSqlBuilder.append(sysName_CN);
			servicesystemSqlBuilder.append("','false','");
			servicesystemSqlBuilder.append(sysName);
			servicesystemSqlBuilder.append("_adapter')@");
			servicesystemSqlBuilder.append(LINE_SEPARATOR);
			return servicesystemSqlBuilder.toString();
	}
	private File generateServiceSystemFile(String sysName,String protocolType,String sysName_CN){
			File sqlFile = null;
			FileOutputStream fileOutputSream = null;
			String dirPath = SYS_SQL_DIR_PATH;
			String sqlFilePath = dirPath + File.separator
				+ SERVICESYSTEM_SQL_DIR_PATH;

			try {
				sqlFile = new File(sqlFilePath);
				sqlFile.deleteOnExit();		
				sqlFile.createNewFile();		
				fileOutputSream = new FileOutputStream(sqlFile);
				String servicesystem = generateServiceSystem(sysName, protocolType,sysName_CN);
				log.info("生成sql["+servicesystem+"]");
				fileOutputSream.write(servicesystem.getBytes());
				fileOutputSream.flush();
			} catch (FileNotFoundException e) {
				log.error("创建sqlFile["+sqlFilePath+"]出错", e);
				e.printStackTrace();
			}catch (IOException e) {
				log.error("生成sqlFile["+sqlFilePath+"]出错", e);
				e.printStackTrace();
			}finally{
				try {
					fileOutputSream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sqlFile;
	}
	private String generateSysInfo(String sysName,String sysName_CN,String providerId){
			if("LoanQ".equals(sysName) || "SOP".equals(sysName) || "IDP".equals(sysName)
					 || "NBH".equals(sysName)){				
			}else{
				sysName = sysName.toLowerCase();
			}
			StringBuilder sysInfoSqlBuilder = new StringBuilder();
			sysInfoSqlBuilder.append("insert into TBL_ESB_SYS_INFO (SYSTEM_ID, SYSTEM_ENG_NAME, SYSTEM_CHN_NAME, CONSUMER_FLAG, CONSUMER_CTRL,");
			sysInfoSqlBuilder.append("CONSUMER_IP_CTRL, CONSUMER_MAC_CTRL, CONSUMER_IP_LIST, PROVIDER_FLAG, PROVIDER_CTRL, SYSTEM_MAC_CTRL, SYSTEM_SECURITY_INFO) values ('");
			sysInfoSqlBuilder.append(providerId);
			sysInfoSqlBuilder.append("','");
			sysInfoSqlBuilder.append(sysName);
			sysInfoSqlBuilder.append("','");
			sysInfoSqlBuilder.append(sysName_CN);
			sysInfoSqlBuilder.append("','y', 'o', 'y', 'n','127.0.0.1', 'y', 'o', 'n','ok')@");
			sysInfoSqlBuilder.append(LINE_SEPARATOR);
			return sysInfoSqlBuilder.toString();
	}
	private String generateSysInfo(String providerSysName,String providerSysName_CN,String providerId,String consumerSysName,String consumerSysName_CN,String consumerId){
		if("LoanQ".equals(providerSysName) || "SOP".equals(providerSysName) || "IDP".equals(providerSysName)
				|| "NBH".equals(providerSysName)){				
		}else{
			providerSysName = providerSysName.toLowerCase();
		}
		if("LoanQ".equals(consumerSysName) || "SOP".equals(consumerSysName) || "IDP".equals(consumerSysName)
				|| "NBH".equals(consumerSysName)){				
		}else{
			consumerSysName = consumerSysName.toLowerCase();
		}
		StringBuilder sysInfoSqlBuilder = new StringBuilder();
		sysInfoSqlBuilder.append("insert into TBL_ESB_SYS_INFO (SYSTEM_ID, SYSTEM_ENG_NAME, SYSTEM_CHN_NAME, CONSUMER_FLAG, CONSUMER_CTRL,");
		sysInfoSqlBuilder.append("CONSUMER_IP_CTRL, CONSUMER_MAC_CTRL, CONSUMER_IP_LIST, PROVIDER_FLAG, PROVIDER_CTRL, SYSTEM_MAC_CTRL, SYSTEM_SECURITY_INFO) values ('");
		sysInfoSqlBuilder.append(providerId);
		sysInfoSqlBuilder.append("','");
		sysInfoSqlBuilder.append(providerSysName);
		sysInfoSqlBuilder.append("','");
		sysInfoSqlBuilder.append(providerSysName_CN);
		sysInfoSqlBuilder.append("','y', 'o', 'y', 'n','127.0.0.1', 'y', 'o', 'n','ok')@");
		sysInfoSqlBuilder.append(LINE_SEPARATOR);
		sysInfoSqlBuilder.append("insert into TBL_ESB_SYS_INFO (SYSTEM_ID, SYSTEM_ENG_NAME, SYSTEM_CHN_NAME, CONSUMER_FLAG, CONSUMER_CTRL,");
		sysInfoSqlBuilder.append("CONSUMER_IP_CTRL, CONSUMER_MAC_CTRL, CONSUMER_IP_LIST, PROVIDER_FLAG, PROVIDER_CTRL, SYSTEM_MAC_CTRL, SYSTEM_SECURITY_INFO) values ('");
		sysInfoSqlBuilder.append(consumerId);
		sysInfoSqlBuilder.append("','");
		sysInfoSqlBuilder.append(consumerSysName);
		sysInfoSqlBuilder.append("','");
		sysInfoSqlBuilder.append(consumerSysName_CN);
		sysInfoSqlBuilder.append("','y', 'o', 'y', 'n','127.0.0.1', 'y', 'o', 'n','ok')@");
		sysInfoSqlBuilder.append(LINE_SEPARATOR);
		return sysInfoSqlBuilder.toString();
}
	private File generateSysInfoFile(String sysName,String sysName_CN,String providerId){
			File sqlFile = null;
			FileOutputStream fileOutputSream = null;
			String dirPath =SYS_SQL_DIR_PATH;
			String sqlFilePath = dirPath + File.separator
				+ SYSINFO_SQL_DIR_PATH;

			try {
				sqlFile = new File(sqlFilePath);
				sqlFile.deleteOnExit();		
				sqlFile.createNewFile();		
				fileOutputSream = new FileOutputStream(sqlFile);
				String sysinfo = generateSysInfo(sysName,sysName_CN,providerId);
				log.info("生成sql["+sysinfo+"]");
				fileOutputSream.write(sysinfo.getBytes());
				fileOutputSream.flush();
			} catch (FileNotFoundException e) {
				log.error("创建sqlFile["+sqlFilePath+"]出错", e);
				e.printStackTrace();
			}catch (IOException e) {
				log.error("生成sqlFile["+sqlFilePath+"]出错", e);
				e.printStackTrace();
			}finally{
				try {
					fileOutputSream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sqlFile;
	}
	private File generateSysInfoFile(String providerSysName,String providerSysName_CN,String providerId,String consumerSysName,String consumerSysName_CN,String consumerId){
		File sqlFile = null;
		FileOutputStream fileOutputSream = null;
		String dirPath =SYS_SQL_DIR_PATH;
		String sqlFilePath = dirPath + File.separator
			+ SYSINFO_SQL_DIR_PATH;

		try {
			sqlFile = new File(sqlFilePath);
			sqlFile.deleteOnExit();		
			sqlFile.createNewFile();		
			fileOutputSream = new FileOutputStream(sqlFile);
			String sysinfo = generateSysInfo(providerSysName,providerSysName_CN,providerId,consumerSysName,consumerSysName_CN,consumerId);
			log.info("生成sql["+sysinfo+"]");
			fileOutputSream.write(sysinfo.getBytes());
			fileOutputSream.flush();
		} catch (FileNotFoundException e) {
			log.error("创建sqlFile["+sqlFilePath+"]出错", e);
			e.printStackTrace();
		}catch (IOException e) {
			log.error("生成sqlFile["+sqlFilePath+"]出错", e);
			e.printStackTrace();
		}finally{
			try {
				fileOutputSream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sqlFile;
}
	private String generateConsumerSysFlowCtrl(String consumerId){
			StringBuilder consumerSysFlowCtrlSqlBuilder = new StringBuilder();
			consumerSysFlowCtrlSqlBuilder.append("insert into ESB2_FLOW_CTRL (CHANNELID,SERVICEID,PROVIDERID,TOKENTOTALQUANTITY,INMULTIDATE,INDATE,INWEEK,INMONTH,INYEAR,CONTROLID) VALUES ('");
			consumerSysFlowCtrlSqlBuilder.append(consumerId);
			consumerSysFlowCtrlSqlBuilder.append("','all'");
			consumerSysFlowCtrlSqlBuilder.append(",'','50','','','','','','");
			consumerSysFlowCtrlSqlBuilder.append(UUID.randomUUID().toString());
			consumerSysFlowCtrlSqlBuilder.append("')@");
			consumerSysFlowCtrlSqlBuilder.append(LINE_SEPARATOR);
			return consumerSysFlowCtrlSqlBuilder.toString();
	}
	private String generateProviderSysFlowCtrl(String providerId){
		StringBuilder providerSysFlowCtrlSqlBuilder = new StringBuilder();
		providerSysFlowCtrlSqlBuilder.append("insert into ESB2_FLOW_CTRL (CHANNELID,SERVICEID,PROVIDERID,TOKENTOTALQUANTITY,INMULTIDATE,INDATE,INWEEK,INMONTH,INYEAR,CONTROLID) VALUES ('");
		providerSysFlowCtrlSqlBuilder.append("','','");
		providerSysFlowCtrlSqlBuilder.append(providerId);
		providerSysFlowCtrlSqlBuilder.append("','30','','','','','','");
		providerSysFlowCtrlSqlBuilder.append(UUID.randomUUID().toString());
		providerSysFlowCtrlSqlBuilder.append("')@");
		providerSysFlowCtrlSqlBuilder.append(LINE_SEPARATOR);
		return providerSysFlowCtrlSqlBuilder.toString();
	}
	private String generateSysFlowCtrl(String providerId,String consumerId){
		StringBuilder providerSysFlowCtrlSqlBuilder = new StringBuilder();
		providerSysFlowCtrlSqlBuilder.append("insert into ESB2_FLOW_CTRL (CHANNELID,SERVICEID,PROVIDERID,TOKENTOTALQUANTITY,INMULTIDATE,INDATE,INWEEK,INMONTH,INYEAR,CONTROLID) VALUES ('");
		providerSysFlowCtrlSqlBuilder.append("','','");
		providerSysFlowCtrlSqlBuilder.append(providerId);
		providerSysFlowCtrlSqlBuilder.append("','30','','','','','','");
		providerSysFlowCtrlSqlBuilder.append(UUID.randomUUID().toString());
		providerSysFlowCtrlSqlBuilder.append("')@");
		providerSysFlowCtrlSqlBuilder.append(LINE_SEPARATOR);
		providerSysFlowCtrlSqlBuilder.append("insert into ESB2_FLOW_CTRL (CHANNELID,SERVICEID,PROVIDERID,TOKENTOTALQUANTITY,INMULTIDATE,INDATE,INWEEK,INMONTH,INYEAR,CONTROLID) VALUES ('");
		providerSysFlowCtrlSqlBuilder.append(consumerId);
		providerSysFlowCtrlSqlBuilder.append("','all'");
		providerSysFlowCtrlSqlBuilder.append(",'','50','','','','','','");
		providerSysFlowCtrlSqlBuilder.append(UUID.randomUUID().toString());
		providerSysFlowCtrlSqlBuilder.append("')@");
		providerSysFlowCtrlSqlBuilder.append(LINE_SEPARATOR);
		return providerSysFlowCtrlSqlBuilder.toString();
	}
	private File generateProviderSysFlowCtrlFile(String providerId){
			File sqlFile = null;
			FileOutputStream fileOutputSream = null;
			String dirPath = SYS_SQL_DIR_PATH;
			String sqlFilePath = dirPath + File.separator
				+ SYSFLOW_CTRL_SQL_FILE_PATH_NAME;

			try {
				sqlFile = new File(sqlFilePath);
				sqlFile.deleteOnExit();		
				sqlFile.createNewFile();		
				fileOutputSream = new FileOutputStream(sqlFile);
				String sysProviderFlowCtrl = generateProviderSysFlowCtrl(providerId);
				log.info("生成sql["+sysProviderFlowCtrl+"]");
				fileOutputSream.write(sysProviderFlowCtrl.getBytes());
				fileOutputSream.flush();
			} catch (FileNotFoundException e) {
				log.error("创建sqlFile["+sqlFilePath+"]出错", e);
				e.printStackTrace();
			}catch (IOException e) {
				log.error("生成sqlFile["+sqlFilePath+"]出错", e);
				e.printStackTrace();
			}finally{
				try {
					fileOutputSream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sqlFile;
	}
	private File generateConsumerSysFlowCtrlFile(String consumerId){
		File sqlFile = null;
		FileOutputStream fileOutputSream = null;
		String dirPath = SYS_SQL_DIR_PATH;
		String sqlFilePath = dirPath + File.separator
			+ SYSFLOW_CTRL_SQL_FILE_PATH_NAME;

		try {
			sqlFile = new File(sqlFilePath);
			sqlFile.deleteOnExit();		
			sqlFile.createNewFile();		
			fileOutputSream = new FileOutputStream(sqlFile);
			String sysConsumerFlowCtrl = generateConsumerSysFlowCtrl(consumerId);
			log.info("生成sql["+sysConsumerFlowCtrl+"]");
			fileOutputSream.write(sysConsumerFlowCtrl.getBytes());
			fileOutputSream.flush();
		} catch (FileNotFoundException e) {
			log.error("创建sqlFile["+sqlFilePath+"]出错", e);
			e.printStackTrace();
		}catch (IOException e) {
			log.error("生成sqlFile["+sqlFilePath+"]出错", e);
			e.printStackTrace();
		}finally{
			try {
				fileOutputSream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sqlFile;
	}
	private File generateSysFlowCtrlFile(String providerId,String consumerId){
		File sqlFile = null;
		FileOutputStream fileOutputSream = null;
		String dirPath = SYS_SQL_DIR_PATH;
		String sqlFilePath = dirPath + File.separator
			+ SYSFLOW_CTRL_SQL_FILE_PATH_NAME;

		try {
			sqlFile = new File(sqlFilePath);
			sqlFile.deleteOnExit();		
			sqlFile.createNewFile();		
			fileOutputSream = new FileOutputStream(sqlFile);
			String sysFlowCtrl = generateSysFlowCtrl(providerId,consumerId);
			log.info("生成sql["+sysFlowCtrl+"]");
			fileOutputSream.write(sysFlowCtrl.getBytes());
			fileOutputSream.flush();
		} catch (FileNotFoundException e) {
			log.error("创建sqlFile["+sqlFilePath+"]出错", e);
			e.printStackTrace();
		}catch (IOException e) {
			log.error("生成sqlFile["+sqlFilePath+"]出错", e);
			e.printStackTrace();
		}finally{
			try {
				fileOutputSream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sqlFile;
	}
	private String generateMemQueue(String providerId){
			StringBuilder sysInfoSqlBuilder = new StringBuilder();
			sysInfoSqlBuilder.append("insert into esb_memqueue_info(q_id,q_max_deep,q_list_num,q_thd_pool_size,q_timeout,remark) values('REQ_");
			sysInfoSqlBuilder.append(providerId);
			sysInfoSqlBuilder.append("',2000");
			sysInfoSqlBuilder.append(",1,0,120000,''");
			sysInfoSqlBuilder.append(")@");
			sysInfoSqlBuilder.append(LINE_SEPARATOR);
			return sysInfoSqlBuilder.toString();
	}
	private File generateMemQueueFile(String providerId){
			File sqlFile = null;
			FileOutputStream fileOutputSream = null;
			String dirPath = SYS_SQL_DIR_PATH;
			String sqlFilePath = dirPath + File.separator
				+ MEMQUEUE_SQL_DIR_PATH;

			try {
				sqlFile = new File(sqlFilePath);
				sqlFile.deleteOnExit();		
				sqlFile.createNewFile();		
				fileOutputSream = new FileOutputStream(sqlFile);
				String memqueue = generateMemQueue(providerId);
				log.info("生成sql["+memqueue+"]");
				fileOutputSream.write(memqueue.getBytes());
				fileOutputSream.flush();
			} catch (FileNotFoundException e) {
				log.error("创建sqlFile["+sqlFilePath+"]出错", e);
				e.printStackTrace();
			}catch (IOException e) {
				log.error("生成sqlFile["+sqlFilePath+"]出错", e);
				e.printStackTrace();
			}finally{
				try {
					fileOutputSream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sqlFile;
	}
	private File[] filesFromMap(Map<String, File> fileMap) {
		File[] files = null;
		if (null != fileMap) {
			int fileCount = fileMap.size();
			log.info("[SERVICE DEF GENERATER]: [" + fileCount
					+ "] files generated");
			if (fileCount > 0) {
				files = new File[fileCount];
				int i = 0;
				for (Map.Entry<String, File> entry : fileMap.entrySet()) {
					files[i] = entry.getValue();
					i++;
				}
			}
		}
		return files;
	}
	public boolean deleteFile(File file){
		boolean deleteResult = true;
		if(file.isDirectory()){
			File[] subFiles = file.listFiles();
			if(null != subFiles){
				for(File subFile : subFiles){
					deleteResult = deleteFile(subFile);
				}
			}
		}
		log.info("开始删除临时文件["+file.getAbsolutePath()+"]");
		deleteResult = file.delete();
		if(!deleteResult){
			log.error("删除临时文件["+file.getAbsolutePath()+"]失败！");
		}
		return deleteResult;
	}
	private void merageFile(Map<String, File> fileMap, List<File> files) {
		if (null != fileMap && null != files) {
			for (File file : files) {
				if (!fileMap.containsKey(file.getAbsoluteFile())) {
					log.info("[SERVICE DEF GENERATER]: file ["
							+ file.getAbsolutePath()
							+ "] has been put into merge list");
					fileMap.put(file.getAbsolutePath(), file);
				}
			}
		}
	}
}
