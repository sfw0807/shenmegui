package com.dc.esb.servicegov.refactoring.resource.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.resource.IConfigGenerater;
import com.dc.esb.servicegov.refactoring.resource.IExport;
import com.dc.esb.servicegov.refactoring.resource.IFactory;
import com.dc.esb.servicegov.refactoring.util.FileUtil;
import static com.dc.esb.servicegov.refactoring.resource.impl.ESBConfigSqlGenerator.*;

@Service
public class ConfigFilesExport implements IExport {
	private Log log = LogFactory.getLog(ConfigFilesExport.class);

	@Autowired
	private ESBConfigSqlGenerator configSqlGenerator;
	@Autowired
	private InOutXmlConfigFactory inOutXmlConfigFactory;
	@Autowired
	private SpdbServiceXMLGenerater spdbServiceXMLGenerater;
	
	public static final String merge_sqlFile_dir = "mergeSqlFiles";
	public static final String merge_configFile_dir = "mergeConfigFiles";

	@Override
	public File[] exportConfigFiles(List<InvokeInfo> list) {
		// TODO Auto-generated method stub
		File[] files = null;
		Map<String, File> filesMap = new HashMap<String, File>();
		try {
			for (InvokeInfo invokeInfo : list) {
				// export sql config files
				List<File> sqlFiles = configSqlGenerator.generate(invokeInfo);
				FileUtil.mergeFile(filesMap, sqlFiles);
				// 非穿透交易导出in\out配置
				if (!"是".equals(invokeInfo.getThrough())) {
					String interfaceMsgType = "";
					// 根据接口方向获取接口报文类型 1:提供方报文类型 0:调用方报文类型
					if ("1".equals(invokeInfo.getDirection())) {
						interfaceMsgType = invokeInfo.getProvideMsgType();
					} else if ("0".equals(invokeInfo.getDirection())) {
						interfaceMsgType = invokeInfo.getConsumeMsgType();
					}
					// 根据接口报文类型，判断调用哪种报文类型的拆组包类
					IFactory<IConfigGenerater<InvokeInfo, List<File>>> generateFactory = inOutXmlConfigFactory
							.factory(interfaceMsgType);
					// 根据提供方系统简称，对ECIF的拆组包类进行路由
					IConfigGenerater<InvokeInfo, List<File>> configGenerater = generateFactory
							.factory(invokeInfo.getProvideSysId());
					// 相同交易码的特殊处理
					List<File> serviceDefFiles = spdbServiceXMLGenerater
							.generate(invokeInfo);
					FileUtil.mergeFile(filesMap, serviceDefFiles);
					List<File> configFiles = configGenerater
							.generate(invokeInfo);
					FileUtil.mergeFile(filesMap, configFiles);
				}
			}
		} catch (Exception e) {
			log.error("export config files error!", e);
		}
		files = FileUtil.filesFromMap(filesMap);
		//in,out端拆组包文件分别合并到一个文件夹中
		File mergeConfigDir = new File(merge_configFile_dir);
		mergeConfigDir.deleteOnExit();
		mergeConfigDir.mkdirs();
		List<File> mergeConfigFiles = this.mergeConfigFile(files,mergeConfigDir);
		FileUtil.mergeFile(filesMap, mergeConfigFiles);		
		files = FileUtil.filesFromMap(filesMap);
		// 合并ConfigsSQLFiles到一个文件夹中
		File mergeSqlDir = new File(merge_sqlFile_dir);
		mergeSqlDir.deleteOnExit();
		mergeSqlDir.mkdirs();
		List<File> mergeSqlFiles = this.mergeConfigsSql(files, mergeSqlDir);
		FileUtil.mergeFile(filesMap, mergeSqlFiles);
		files = FileUtil.filesFromMap(filesMap);
		return files;
	}

	// 合并多个SQl配置文件
	private List<File> mergeConfigsSql(File[] files, File mergeSqlDir) {
		List<File> mergeFiles = new ArrayList<File>();
		File buss_service_sql_file = new File(mergeSqlDir + File.separator + BUSS_SERVICE_SQL_FILE_NAME);
		mergeFiles.add(buss_service_sql_file);
		File service_identify_sql_file = new File(mergeSqlDir + File.separator + SERVICE_IDENTIFY_SQL_FILE_NALE);
		mergeFiles.add(service_identify_sql_file);
		File service_info_sql_file = new File(mergeSqlDir + File.separator + SERVICE_INFO_SQL_FILE_NAME);
		mergeFiles.add(service_info_sql_file);
		File service_ctrl_sql_file = new File(mergeSqlDir + File.separator + SERVICE_CTRL_SQL_FILE_NAME);
		mergeFiles.add(service_ctrl_sql_file);
		File flow_ctrl_sql_file = new File(mergeSqlDir + File.separator + FLOW_CTRL_SQL_FILE_PATH_NAME);
		mergeFiles.add(flow_ctrl_sql_file);
		File service_log_cfg_file = new File(mergeSqlDir + File.separator + SERVICE_LOG_CFG_SQL_FILE_NAME);
		mergeFiles.add(service_log_cfg_file);
		File spe_handle_info_file = null;
		for (File file : files) {
			// 只遍历sql目录下的sql文件
			if (file.getAbsolutePath().toLowerCase().contains("sql")) {
				File configSqlDir = new File(file.getAbsolutePath());
				File[] eachFiles = configSqlDir.listFiles();
				for (File eachFile : eachFiles) {
					if (TBL_SPE_HANDLE_INFO.equalsIgnoreCase(eachFile
							.getName())) {
						spe_handle_info_file = new File(mergeSqlDir + File.separator + TBL_SPE_HANDLE_INFO);			
						mergeFiles.add(spe_handle_info_file);
					}					
				}
			}
		}		
		File all_file = new File(mergeSqlDir + File.separator + ALL);
		mergeFiles.add(all_file);
		
		for (File file : files) {
			// 只遍历sql目录下的sql文件
			if (file.getAbsolutePath().toLowerCase().contains("sql")) {
				File configSqlDir = new File(file.getAbsolutePath());
				File[] eachFiles = configSqlDir.listFiles();
				for (File eachFile : eachFiles) {
					if (BUSS_SERVICE_SQL_FILE_NAME.equalsIgnoreCase(eachFile
							.getName())) {
						// 合并文件
						this.mergeTwoFiles(eachFile, buss_service_sql_file);
					}
					if (SERVICE_IDENTIFY_SQL_FILE_NALE
							.equalsIgnoreCase(eachFile.getName())) {
						// 合并文件
						this.mergeTwoFiles(eachFile, service_identify_sql_file);
					}
					if (SERVICE_INFO_SQL_FILE_NAME.equalsIgnoreCase(eachFile
							.getName())) {
						// 合并文件
						this.mergeTwoFiles(eachFile, service_info_sql_file);
					}
					if (SERVICE_CTRL_SQL_FILE_NAME.equalsIgnoreCase(eachFile
							.getName())) {
						// 合并文件
						this.mergeTwoFiles(eachFile, service_ctrl_sql_file);
					}
					if (FLOW_CTRL_SQL_FILE_PATH_NAME.equalsIgnoreCase(eachFile
							.getName())) {
						// 合并文件
						this.mergeTwoFiles(eachFile, flow_ctrl_sql_file);
					}
					if (SERVICE_LOG_CFG_SQL_FILE_NAME.equalsIgnoreCase(eachFile
							.getName())) {
						// 合并文件
						this.mergeTwoFiles(eachFile, service_log_cfg_file);
					}
					if (TBL_SPE_HANDLE_INFO.equalsIgnoreCase(eachFile
							.getName())) {
						// 合并文件
						this.mergeTwoFiles(eachFile, spe_handle_info_file);
					}					
				}
			}
		}
		mergeTwoFiles(buss_service_sql_file,all_file);
		mergeTwoFiles(service_identify_sql_file,all_file);
		mergeTwoFiles(service_info_sql_file,all_file);
		mergeTwoFiles(service_ctrl_sql_file,all_file);
		mergeTwoFiles(flow_ctrl_sql_file,all_file);
		mergeTwoFiles(service_log_cfg_file,all_file);
		if(spe_handle_info_file!=null && spe_handle_info_file.exists()){
			mergeTwoFiles(spe_handle_info_file,all_file);			
		}		
		return mergeFiles;
	}
	// 合并两个文件
	private void mergeTwoFiles(File sourceFile, File targetFile) {
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			bw = new BufferedWriter(new FileWriter(targetFile, true));
			//bw.write(LINE_SEPARATOR);
			br = new BufferedReader(new FileReader(sourceFile));
			String line = null;
			while ((line = br.readLine()) != null) {
				bw.write(line);
				bw.write(LINE_SEPARATOR);
			}
			bw.flush();
		} catch (IOException e) {
			log.error("合并sql文件出错" + e );
		} finally {
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private List<File> mergeConfigFile(File[] files, File mergeConfigDir) {
		List<File> fileList = new ArrayList<File>();
		File intargetFile = new File(mergeConfigDir.getAbsolutePath()+"/in_config/metadata/");
		if(!intargetFile.exists()){
			intargetFile.mkdirs();
		}
		File outtargetFile = new File(mergeConfigDir.getAbsolutePath()+"/out_config/metadata/");
		if(!outtargetFile.exists()){
			outtargetFile.mkdirs();
		}
		for(File file : files){
			if(file.getAbsolutePath().contains("in_config")){
				File sourceFile = new File(file.getAbsolutePath());
				mergeTwoConfigFiles(sourceFile,intargetFile);
			}
			if(file.getAbsolutePath().contains("out_config")){
				File sourceFile = new File(file.getAbsolutePath());
				mergeTwoConfigFiles(sourceFile,outtargetFile);
			}
		}
		for(File file : mergeConfigDir.listFiles()){
			fileList.add(file);
		}
		return fileList;
	}
	private void mergeTwoConfigFiles(File sourceFileDirectory,File targetDirectory){
		File[] sourceFiles = sourceFileDirectory.listFiles();
		File targetFile = null;
		for(File file : sourceFiles){
			targetFile = new File(targetDirectory.getAbsolutePath()+"/"+file.getName());
			if(!targetFile.exists()){
				try {
					targetFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			mergeTwoFiles(file,targetFile);
		}		
	}
}
