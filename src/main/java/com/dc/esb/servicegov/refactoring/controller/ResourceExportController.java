package com.dc.esb.servicegov.refactoring.controller;

import static com.dc.esb.servicegov.refactoring.resource.impl.ConfigFilesExport.merge_configFile_dir;
import static com.dc.esb.servicegov.refactoring.resource.impl.ConfigFilesExport.merge_sqlFile_dir;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.refactoring.dao.impl.SystemDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.resource.ESBConfigSysSqlGenerator;
import com.dc.esb.servicegov.refactoring.resource.impl.ConfigFilesExport;
import com.dc.esb.servicegov.refactoring.resource.impl.MapFileGenerator;
import com.dc.esb.servicegov.refactoring.resource.impl.MetadataXMLGenerater;
import com.dc.esb.servicegov.refactoring.service.LogManager;
import com.dc.esb.servicegov.refactoring.service.impl.InvokeInfoManagerImpl;
import com.dc.esb.servicegov.refactoring.service.impl.OperationManagerImpl;
import com.dc.esb.servicegov.refactoring.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.refactoring.util.FileUtil;
import com.dc.esb.servicegov.refactoring.vo.DuplicateInvokeVO;

@Controller
@RequestMapping("/export")
public class ResourceExportController {

	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(ResourceExportController.class);
	@Autowired
	private ConfigFilesExport cfe;
	@Autowired
	private MapFileGenerator generator;
	@Autowired
	private InvokeInfoManagerImpl invokeInfoManager;
	@Autowired
	private ServiceManagerImpl serviceManager;
	@Autowired
	private OperationManagerImpl operationManager;
	@Autowired
	private SystemDAOImpl systemDAO;
	@Autowired
	private MetadataXMLGenerater metadataXMLGenerate;
	@Autowired 
	private LogManager logManager;	
	private String functionId="15";
	@RequestMapping(method = RequestMethod.GET, value = "/mapfile/{ecode}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportMapFile(HttpServletRequest request,
			HttpServletResponse response, @PathVariable
			String ecode) {
		InputStream in = null;
		OutputStream out = null;
		boolean success = false;
		try {
			File mapFile = null;
			String[] ecodes = null;
			if (ecode.contains(",")) {
				ecode = ecode.substring(0, ecode.length() - 1);
				ecodes = ecode.split(",");
			} else {
				ecodes = new String[]{ecode};
			}
			List<File> files = generator.generate(ecodes);
			
			String zipName = "MapFile"
				+ new SimpleDateFormat("yyyyMMddHHmmss")
						.format(new Date()) + ".zip";
			mapFile = FileUtil.downloadFile(files.toArray(new File[0]), zipName, response);
			if (null == mapFile) {
				try{
					logManager.setLog("生成的映射文件不存在!", functionId, "error");
				}catch(Exception e1){
					log.error(e1, e1);
				}
				String errorMsg = "生成的映射文件不存在！";
				DataException dataException = new DataException(errorMsg);
				throw dataException;

			} else {
				response.setContentType("application/zip");
				response.addHeader("Content-Disposition",
						"attachment;filename="
								+ new String(mapFile.getName().getBytes(
										"gbk"), "iso-8859-1"));
				in = new BufferedInputStream(new FileInputStream(mapFile));
				out = new BufferedOutputStream(response.getOutputStream());
				long fileLength = mapFile.length();
				byte[] cache = null;
				if (fileLength > Integer.MAX_VALUE) {
					cache = new byte[Integer.MAX_VALUE];
				} else {
					cache = new byte[(int) fileLength];
				}
				int i = 0;
				while ((i = in.read(cache)) > 0) {
					out.write(cache, 0, i);
				}
				out.flush();
				success = true;
			}
			// 删除zip文件
            if(mapFile.exists()){
            	mapFile.delete();
            }
		} catch (Exception e) {
			try{
				logManager.setLog("映射文件导出过程中发生异常!", functionId, "error");
			}catch(Exception e1){
				log.error(e1, e1);
			}
			log.error(e, e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					try{
						logManager.setLog("映射文件导出完成,关闭输入流过程发生异常", functionId, "error");
					}catch(Exception e1){
						log.error(e1, e1);
					}
					log.error(e, e);
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					try{
						logManager.setLog("映射文件导出完成,关闭输出流过程发生异常", functionId, "error");
					}catch(Exception e1){
						log.error(e1, e1);
					}
					log.error(e, e);
				}

			}
		}
		return success;
	}
	
	/**
	 * 导出配置
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, value = "/config/{params}", headers = "Accept=application/json")
	public @ResponseBody
	boolean downloadConfig(HttpServletRequest request,
			HttpServletResponse response, @PathVariable
			String params) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		params = new String(params.getBytes("iso-8859-1"),"UTF-8");
		// log.info("....." + request.getRealPath("/"));
		synchronized (ResourceExportController.class) {
			log.info("export params :" + params);
			boolean success = true;
			List<InvokeInfo> list = new ArrayList<InvokeInfo>();
			if(params.indexOf(":") > 0){
				String[] paramArr = params.split(":");
				for(String uniqueStr: paramArr){
					String strArr[] =  uniqueStr.split(",");
					InvokeInfo invoke = new InvokeInfo();
					invoke.setEcode(strArr[0]);
					invoke.setConsumeMsgType(strArr[1]);
					invoke.setProvideMsgType(strArr[2]);
					invoke.setThrough("0".equals(strArr[3])?"是":"否");
					invoke.setServiceId(strArr[4]);
					invoke.setOperationId(strArr[5]);
					invoke.setProvideSysId(strArr[6]);
					invoke.setDirection(strArr[7]);
					list.add(invoke);
				}
			}
			else{
				String strArr[] =  params.split(",");
				InvokeInfo invoke = new InvokeInfo();
				invoke.setEcode(strArr[0]);
				invoke.setConsumeMsgType(strArr[1]);
				invoke.setProvideMsgType(strArr[2]);
				invoke.setThrough("0".equals(strArr[3])?"是":"否");
				invoke.setServiceId(strArr[4]);
				invoke.setOperationId(strArr[5]);
				invoke.setProvideSysId(strArr[6]);
				invoke.setDirection(strArr[7]);
				list.add(invoke);
			}
			// 配置文件导出返回Files
			File[] files = cfe.exportConfigFiles(list);
			String zipName = "";
			if (list.size() > 1) {
				zipName = "ESB_Config_"
						+ new SimpleDateFormat("yyyyMMddHHmmss")
								.format(new Date()) + ".zip";
			} else {
				zipName = "["
						+ list.get(0).getEcode()
						+ "]"
						+ "ESB_Config_"
						+ new SimpleDateFormat("yyyyMMddHHmmss")
								.format(new Date()) + ".zip";
			}
			//FileUtil.downloadFile(files, zipName, response);
            File configZip = FileUtil.downloadFile(files, zipName, response);
            // 删除文件的根目录
			String[] delPathArr = new String[list.size()];
			for(int i =0;i<list.size();i++){
				InvokeInfo tempInvoke = list.get(i);
				delPathArr[i] = tempInvoke.getServiceId() + tempInvoke.getOperationId() + "(" + tempInvoke.getConsumeMsgType()
				                + "-" + tempInvoke.getProvideMsgType() + ")";
				// 删除具有-SOP标志的操作对应的其他路径
				String tempOperationId = tempInvoke.getOperationId();
				if(tempOperationId.contains("-")){
					String tempPath = tempInvoke.getServiceId()
							+ tempOperationId.substring(0, tempOperationId
									.indexOf("-")) + "("
							+ tempInvoke.getConsumeMsgType() + "-"
							+ tempInvoke.getProvideMsgType() + ")";
					File delfile = new File(tempPath);
					FileUtil.deleteFile(delfile);
				}
			}
            if (null == configZip) {
    			try{
    				logManager.setLog("生成的配置文件不存在!", functionId, "error");
    			}catch(Exception e1){
    				log.error(e1, e1);
    			}
				success = false;
				String errorMsg = "生成的配置文件不存在！";
				DataException dataException = new DataException(errorMsg);
				throw dataException;
			}   
		    // 删除zip文件
            if(configZip.exists()){
                	configZip.delete();
            }
			// 删除serviceId + operation文件及所有子文件
			for(String delPath :delPathArr){
				File delfile = new File(delPath);
				FileUtil.deleteFile(delfile);
			}
			// 删除mergeConfigFiles
			File configFileDir = new File(merge_configFile_dir);
			if(null != configFileDir){
				FileUtil.deleteFile(configFileDir);
			}
			// 删除mergeSqlFiles
			File sqlFileDir = new File(merge_sqlFile_dir);
			if(null != sqlFileDir){
				FileUtil.deleteFile(sqlFileDir);
			}
			return success;
		}
	}
	
	/**
	 * 批量导出配置
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, value = "/batchConfig/{params}", headers = "Accept=application/json")
	public @ResponseBody
	boolean batchDownloadConfig(HttpServletRequest request,
			HttpServletResponse response, @PathVariable
			String params) throws Exception {
		params = new String(params.getBytes("iso-8859-1"),"UTF-8");
		// log.info("....." + request.getRealPath("/"));
		synchronized (ResourceExportController.class) {
			log.info("export params :" + params);
			String[] interfaceIds = params.split(",");
			boolean success = true;
			List<InvokeInfo> list = new ArrayList<InvokeInfo>();
			List<InvokeInfo> daoList = invokeInfoManager.getDuplicateInvokeByEcode(interfaceIds);
			for(InvokeInfo ii: daoList){
				InvokeInfo temp = new InvokeInfo();
				temp.setEcode(ii.getEcode());
				temp.setConsumeMsgType(ii.getConsumeMsgType());
				temp.setProvideMsgType(ii.getProvideMsgType());
				temp.setThrough(ii.getThrough());
				temp.setServiceId(ii.getServiceId());
				temp.setOperationId(ii.getOperationId());
				temp.setProvideSysId(systemDAO.getSystemAbById(ii.getProvideSysId()));
				temp.setDirection(ii.getDirection());
				list.add(temp);
			}
			// 配置文件导出返回Files
			File[] files = cfe.exportConfigFiles(list);
			String zipName = "";
			if (list.size() > 1) {
				zipName = "ESB_Config_"
						+ new SimpleDateFormat("yyyyMMddHHmmss")
								.format(new Date()) + ".zip";
			} else {
				zipName = "["
						+ list.get(0).getEcode()
						+ "]"
						+ "ESB_Config_"
						+ new SimpleDateFormat("yyyyMMddHHmmss")
								.format(new Date()) + ".zip";
			}
			//FileUtil.downloadFile(files, zipName, response);
            File configZip = FileUtil.downloadFile(files, zipName, response);
         // 删除文件的根目录
			String[] delPathArr = new String[list.size()];
			for(int i =0;i<list.size();i++){
				InvokeInfo tempInvoke = list.get(i);
				delPathArr[i] = tempInvoke.getServiceId() + tempInvoke.getOperationId() + "(" + tempInvoke.getConsumeMsgType()
				                + "-" + tempInvoke.getProvideMsgType() + ")";
				// 删除具有-SOP标志的操作对应的其他路径
				String tempOperationId = tempInvoke.getOperationId();
				if(tempOperationId.contains("-")){
					String tempPath = tempInvoke.getServiceId()
							+ tempOperationId.substring(0, tempOperationId
									.indexOf("-")) + "("
							+ tempInvoke.getConsumeMsgType() + "-"
							+ tempInvoke.getProvideMsgType() + ")";
					File delfile = new File(tempPath);
					FileUtil.deleteFile(delfile);
				}
			}
            if (null == configZip) {
    			try{
    				logManager.setLog("生成的配置文件不存在!", functionId, "error");
    			}catch(Exception e1){
    				log.error(e1, e1);
    			}
				success = false;
				String errorMsg = "生成的配置文件不存在！";
				DataException dataException = new DataException(errorMsg);
				throw dataException;
			}   
		    // 删除zip文件
            if(configZip.exists()){
                	configZip.delete();
            }
			// 删除serviceId + operation文件及所有子文件
			for(String delPath :delPathArr){
				File delfile = new File(delPath);
				FileUtil.deleteFile(delfile);
			}
			// 删除mergeConfigFiles
			File configFileDir = new File(merge_configFile_dir);
			if(null != configFileDir){
				FileUtil.deleteFile(configFileDir);
			}
			// 删除mergeSqlFiles
			File sqlFileDir = new File(merge_sqlFile_dir);
			if(null != sqlFileDir){
				FileUtil.deleteFile(sqlFileDir);
			}
			return success;
		}
	}
	@RequestMapping(method = RequestMethod.GET, value = "/syssql/{param}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean exportSysSql(HttpServletRequest request,HttpServletResponse response,@PathVariable String param) throws UnsupportedEncodingException {
		param = java.net.URLDecoder.decode(param, "UTF-8");
		String temp = new String(param);
		System.out.println(param);
		boolean success = false;
		try{
			//[0025, BPP, 客服业务处理系统, TCPServiceConnector, other, 0370, PCRM, 个人银行客户关系管理系统]
			String[] ecode = temp.trim().split("\\|");
			String systype = ecode[0];
			String psysId = ecode[1];
			String psysAB = ecode[2];
			String psysName = ecode[3];
			String pAdaptor = ecode[4];
			String pMsg = ecode[5];
			String csysId = ecode[6];
			String csysAB = ecode[7];
			String csysName = ecode[8];
			Map<String,String> paramMap = new HashMap<String,String>();
			if("0".equals(systype)){
				paramMap.put("systype", systype);
				paramMap.put("providerId", psysId);
				paramMap.put("protocolType", pAdaptor);
				paramMap.put("providerName", psysAB);
				paramMap.put("providerName_CN", psysName);
				paramMap.put("providerMsgType", pMsg);
			}else if("1".equals(systype)){
				paramMap.put("systype", systype);
				paramMap.put("consumerId", csysId);
				paramMap.put("consumerName", csysAB);
				paramMap.put("consumerName_CN", csysName);
			}else{
				paramMap.put("systype", systype);
				paramMap.put("providerId", psysId);
				paramMap.put("protocolType", pAdaptor);
				paramMap.put("providerName", psysAB);
				paramMap.put("providerName_CN", psysName);
				paramMap.put("providerMsgType", pMsg);
				paramMap.put("consumerId", csysId);
				paramMap.put("consumerName", csysAB);
				paramMap.put("consumerName_CN", csysName);
			}
			ESBConfigSysSqlGenerator sysSqlGenerator = new ESBConfigSysSqlGenerator();
			List<File> sysSqlFileList = sysSqlGenerator.generate(paramMap);
			String zipName = "SysSql"
				+ new SimpleDateFormat("yyyyMMddHHmmss")
						.format(new Date()) + ".zip";
			File sysSqlFile = FileUtil.downloadFile(sysSqlFileList.get(0).listFiles(), zipName, response);
			// 删除zip文件
	        if(sysSqlFile.exists()){
	        	sysSqlFile.delete();
	        }
	        success = true;
	        paramMap = null;
		}catch(Exception e){
			try{
				logManager.setLog("导出系统级别SQL发生异常!", functionId, "error");
			}catch(Exception e1){
				log.error(e1, e1);
			}
			log.error(e);
		}
		return success;
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, value = "/exportMetadata", headers = "Accept=application/json")
    public
    @ResponseBody
    void exportMetadataExcel(HttpServletRequest request,
			HttpServletResponse response){
		File metadataXML = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			metadataXML = metadataXMLGenerate.generate();
			if (null == metadataXML) {
    			try{
    				logManager.setLog("生成Excel不存在!", functionId, "error");
    			}catch(Exception e1){
    				log.error(e1, e1);
    			}
				String errorMsg = "生成Excel不存在！";
				DataException dataException = new DataException(errorMsg);
				throw dataException;

			} else {
				response.setContentType("application/x-zip-compressed");
				response.addHeader("Content-Disposition",
						"attachment;filename="
								+ new String(metadataXML.getName().getBytes(
										"gbk"), "iso-8859-1"));
				in = new BufferedInputStream(new FileInputStream(metadataXML));
				out = new BufferedOutputStream(response.getOutputStream());
				long fileLength = metadataXML.length();
				byte[] cache = null;
				if (fileLength > Integer.MAX_VALUE) {
					cache = new byte[Integer.MAX_VALUE];
				} else {
					cache = new byte[(int) fileLength];
				}
				int i = 0;
				while ((i = in.read(cache)) > 0) {
					out.write(cache, 0, i);
				}
				out.flush();
			}
		} catch (Exception e) {
			try{
				logManager.setLog("导出metadata.xml文件发生异常!", functionId, "error");
			}catch(Exception e1){
				log.error(e1, e1);
			}
			log.error(e, e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					try{
						logManager.setLog("metadata.xml文件导出完成,关闭输入流过程发生异常", functionId, "error");
					}catch(Exception e1){
						log.error(e1, e1);
					}
					log.error(e, e);
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					try{
						logManager.setLog("metadata.xml文件导出完成,关闭输出流过程发生异常", functionId, "error");
					}catch(Exception e1){
						log.error(e1, e1);
					}
					log.error(e, e);
				}
			}
		}
	}
	
	 /***
     * 资源导出接口的服务操作审核
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/auditEcode/{params}", headers = "Accept=application/json")
    public @ResponseBody String audit(@PathVariable String params){
    	String invalid = null;
    	try {
    		String[] arr = params.split(",");
    		for(String ecode: arr){
    			InvokeInfo invokeInfo = invokeInfoManager.getFirstInvokeByEcode(ecode);
    			String serviceId = invokeInfo.getServiceId();
    			String operationId = invokeInfo.getOperationId();
    			if(invokeInfo != null){
    				// 审核服务
    				if(!serviceManager.checkServicePassed(serviceId)){
    					invalid = serviceId;
    					break;
    				}
                    // 审核操作
    				if(!operationManager.checkOperationPassed(operationId, serviceId)){
    					invalid = operationId;
    					break;
    				}
    			}
    		}
		} catch (Exception e) {
			log.error("审核接口的服务操作出现错误!");
		}
		return invalid;
    }
    
    /***
     * 获取存在多条不同导出配置的invokeInfo信息
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getBatchDuplicateInvoke/{params}", headers = "Accept=application/json")
    public @ResponseBody List<DuplicateInvokeVO> getBatchDuplicateInvoke(@PathVariable String params){
    	List<DuplicateInvokeVO> duplicateList = new ArrayList<DuplicateInvokeVO>();
    	List<InvokeInfo> list = null;
    	try {
    		String[] interfaceIds = params.split(",");
    		list = invokeInfoManager.getBatchDuplicateInvokeByEcode(interfaceIds);
    		if(list != null && list.size()>0){
    			for(InvokeInfo ii: list){
    				DuplicateInvokeVO vo = new DuplicateInvokeVO();
    				vo.setConsumeMsgType(ii.getConsumeMsgType());
    				vo.setInterfaceId(ii.getEcode());
    				vo.setOperationId(ii.getOperationId());
    				vo.setProvideMsgType(ii.getProvideMsgType());
    				vo.setProvideSys(systemDAO.getSystemAbById(ii.getProvideSysId()));
    				vo.setServiceId(ii.getServiceId());
    				vo.setThrough(ii.getThrough());
    				duplicateList.add(vo);
    			}
    		}
    	} catch (Exception e) {
			log.error("审核接口的服务操作出现错误!");
		}
		return duplicateList;
    }
}
