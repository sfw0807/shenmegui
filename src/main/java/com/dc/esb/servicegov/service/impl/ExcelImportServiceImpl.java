package com.dc.esb.servicegov.service.impl;

import java.util.*;

import com.dc.esb.servicegov.dao.impl.*;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.dao.support.SearchCondition;
import com.dc.esb.servicegov.entity.*;
import com.dc.esb.servicegov.entity.System;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import com.dc.esb.servicegov.service.support.Constants;
import com.dc.esb.servicegov.util.AuditUtil;
import com.dc.esb.servicegov.util.GlobalImport;
import com.dc.esb.servicegov.util.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dc.esb.servicegov.service.ExcelImportService;

@Service
public class ExcelImportServiceImpl extends AbstractBaseService implements ExcelImportService {

	protected Log logger = LogFactory.getLog(getClass());

	@Autowired
	InterfaceDAOImpl interfaceDao;

	@Autowired
	IdaDAOImpl idaDao;

	@Autowired
	ServiceDAOImpl serviceDao;

	@Autowired
	SDADAOImpl sdaDAO;

	@Autowired
	SystemDAOImpl systemDao;

	@Autowired
	ServiceInvokeDAOImpl serviceInvokeDAO;

	@Autowired
	ServiceCategoryDAOImpl serviceCategoryDAO;

	@Autowired
	OperationDAOImpl operationDAO;

	@Autowired
	InterfaceHeadDAOImpl interfaceHeadDAO;

	@Autowired
	InterfaceHeadRelateDAOImpl interfaceHeadRelateDAO;

	@Autowired
	LogInfoDAOImpl logInfoDAO;

	@Autowired
	private VersionServiceImpl versionService;

	private String initVersion = "1.0.0";

	@Override
	public boolean executeImport(Map<String, Object> infoMap, Map<String, Object> inputMap, Map<String, Object> outMap,Map<String,String> publicMap,Map<String,Object> headMap) {
		com.dc.esb.servicegov.entity.Service service = (com.dc.esb.servicegov.entity.Service) infoMap.get("service");
		Operation operation = (Operation) infoMap.get("operation");
		Interface inter = (Interface) infoMap.get("interface");

		List<Ida> idainput = (List<Ida>)inputMap.get("idas");
		List<Ida> idaoutput = (List<Ida>)outMap.get("idas");
		List<SDA> sdainput = (List<SDA>)inputMap.get("sdas");
		List<SDA> sdaoutput = (List<SDA>)outMap.get("sdas");

		//导入服务定义相关信息
		logger.info("导入服务定义信息...");
		if(insertService(service)){
			//导入服务场景相关信息
			logger.info("导入服务场景信息...");
			boolean existsOper = insertOperation(service,operation);
			//维护调用关系
			//接口提供方 service_invoke 中 type=0
			String providerSystem = publicMap.get("providerSystem");
			//接口消费方 service_invoke 中 type=1
			String cusumerSystem = publicMap.get("cusumerSystem");
			//接口方向
			String interfacepoint = publicMap.get("interfacepoint");
			String systemId = cusumerSystem;
			String type ="1";
			if("Provider".equalsIgnoreCase(interfacepoint)){
				systemId = providerSystem;
				type = "0";
			}
			//获取调用关系
			ServiceInvoke provider_invoke = serviceInvokeProviderQuery(service, operation, systemId,interfacepoint);

			//获取消费关系
			//ServiceInvoke cusumer_invoke = serviceInvokeCusumerQuery(service, operation, cusumerSystem);

			//导入接口相关信息
			logger.info("导入接口定义信息...");
			boolean exists = insertInterface(inter,service, operation,provider_invoke,systemId,type);

			insertIDA(exists,inter,idainput,idaoutput);

			insertSDA(existsOper,operation,service,sdainput,sdaoutput);

			//处理业务报文头
			if(headMap!=null){
				insertInterfaceHead(exists,inter,headMap);
			}


		}else {

			return false;

		}
		return true;
	}

	public void insertInterfaceHead(boolean exists,Interface inter,Map<String,Object> headMap){

		//如果接口存在，且不覆盖 直接返回
		if(exists && !GlobalImport.operateFlag){
			return;
		}

		String headName = headMap.get("headName").toString();
		//如果本次导入已导入该报文头，直接建立关系
		InterfaceHead interfaceHead = GlobalImport.headMap.get(headName);
		if(interfaceHead!=null){
			InterfaceHeadRelate relate = new InterfaceHeadRelate();
			relate.setHeadId(interfaceHead.getHeadId());
			relate.setInterfaceId(inter.getInterfaceId());
			interfaceHeadRelateDAO.save(relate);
			return;
		}

		InterfaceHead headDB = interfaceHeadDAO.findUniqueBy("headName", headName);

		if(headDB!=null){
			if(GlobalImport.operateFlag) {
				interfaceHeadDAO.delete(headDB.getHeadId());
			}else {
				if(exists) {
					return;
				}
				//接口不存在，建立关系
				InterfaceHeadRelate relate = new InterfaceHeadRelate();
				relate.setHeadId(headDB.getHeadId());
				relate.setInterfaceId(inter.getInterfaceId());
				interfaceHeadRelateDAO.save(relate);
				return;

			}
		}

		InterfaceHead head = new InterfaceHead();
		head.setHeadName(headName);
		head.setHeadDesc(headName);
		interfaceHeadDAO.save(head);

		InterfaceHeadRelate relate = new InterfaceHeadRelate();
		relate.setHeadId(head.getHeadId());
		relate.setInterfaceId(inter.getInterfaceId());
		interfaceHeadRelateDAO.save(relate);
		String idaheadId = head.getHeadId();


		//添加IDA
		Ida ida = new Ida();
		String rootId="",requestId = "",responseId="";
		ida.setHeadId(idaheadId);
		ida.set_parentId(null);
		ida.setStructName("root");
		ida.setStructAlias("根节点");
		idaDao.save(ida);
		rootId = ida.getId();

		ida = new Ida();
		ida.setHeadId(idaheadId);
		ida.set_parentId(rootId);
		ida.setStructName("request");
		ida.setStructAlias("请求头");
		ida.setSeq(0);
		idaDao.save(ida);
		requestId = ida.getId();

		ida = new Ida();
		ida.setHeadId(idaheadId);
		ida.set_parentId(rootId);
		ida.setSeq(1);
		ida.setStructName("response");
		ida.setStructAlias("响应头");
		idaDao.save(ida);
		responseId = ida.getId();

		List<Ida> input = (List<Ida>)headMap.get("input");
		List<Ida> output =(List<Ida>) headMap.get("output");
		for(int i=0;i<input.size();i++) {
			ida = input.get(i);
			ida.set_parentId(requestId);
			ida.setHeadId(idaheadId);
			//ida.setArgType("headinput");
			idaDao.save(ida);
		}

		for(int i=0;i<output.size();i++) {
			ida = output.get(i);
			ida.set_parentId(responseId);
			ida.setHeadId(idaheadId);
			//ida.setArgType("headoutput");
			idaDao.save(ida);
		}
		//将本次导入的报文头缓存到map,导入有可能是同一个报文头
		GlobalImport.headMap.put(headName,head);
	}

	private void insertIDA(boolean exists,Interface inter, List<Ida> idainput, List<Ida> idaoutput) {
		//添加报文，自动生成固定报文头<root><request><response>
		//root
		Ida ida = new Ida();
		String rootId="",requestId = "",responseId="";
		//覆盖
		if(GlobalImport.operateFlag){
			//先删除
			String hql = "delete from Ida where interfaceId=?";
			sdaDAO.exeHql(hql,inter.getInterfaceId());
		}else{
			//不覆盖，直接return
			if(exists){
				return;
			}
		}

		if(!exists || ( exists && GlobalImport.operateFlag)) {
			ida.setInterfaceId(inter.getInterfaceId());
			ida.set_parentId(null);
			ida.setStructName("root");
			ida.setStructAlias("根节点");
			idaDao.save(ida);
			rootId = ida.getId();

			ida = new Ida();
			ida.setInterfaceId(inter.getInterfaceId());
			ida.set_parentId(rootId);
			ida.setStructName("request");
			ida.setStructAlias("请求头");
			ida.setSeq(0);
			idaDao.save(ida);
			requestId = ida.getId();

			ida = new Ida();
			ida.setInterfaceId(inter.getInterfaceId());
			ida.set_parentId(rootId);
			ida.setSeq(1);
			ida.setStructName("response");
			ida.setStructAlias("响应头");
			idaDao.save(ida);
			responseId = ida.getId();
		}

		String parentId = null;
		for(int i=0;i<idainput.size();i++){
			ida = idainput.get(i);
			//判断ida是否存在
//			Map<String,String> paramMap = new HashMap<String, String>();
//			paramMap.put("interfaceId",inter.getInterfaceId());
//			paramMap.put("structName",ida.getStructName());
//			if(ida.getMetadataId()!=null && !"".equals(ida.getMetadataId())) {
//				paramMap.put("metadataId", ida.getMetadataId());
//			}
//			paramMap.put("argType","input");//输入参数
//			Ida idadb = idaDao.findUniqureBy(paramMap);
//			if(idadb!=null){
//				ida.setId(idadb.getId());
//				ida.set_parentId(idadb.get_parentId());
//				continue;
//			}
			ida.setInterfaceId(inter.getInterfaceId());
			ida.set_parentId(requestId);
			if(parentId!=null){
				ida.set_parentId(parentId);
			}

			//包含bug，当节点end后，下一节点 不在request 或 response下 就会出现问题，
			if("end".equalsIgnoreCase(ida.getRemark()) || "不映射".equalsIgnoreCase(ida.getRemark()) || ida.getStructName() == null || "".equals(ida.getStructName())){
				if("end".equalsIgnoreCase(ida.getRemark())){
					parentId = null;
				}
				continue;
			}
			//ida.setArgType("input");
			idaDao.save(ida);
			//包含子节点
			if("start".equalsIgnoreCase(ida.getRemark())){
				parentId = ida.getId();
			}
		}

		parentId = null;
		for(int i=0;i<idaoutput.size();i++){
			ida = idaoutput.get(i);
			//判断ida是否存在
//			Map<String,String> paramMap = new HashMap<String, String>();
//			paramMap.put("interfaceId",inter.getInterfaceId());
//			paramMap.put("structName",ida.getStructName());
//			if(ida.getMetadataId()!=null && !"".equals(ida.getMetadataId())) {
//				paramMap.put("metadataId", ida.getMetadataId());
//			}
//			paramMap.put("argType","output");
//			Ida idadb = idaDao.findUniqureBy(paramMap);
//			if(idadb!=null){
//				ida.setId(idadb.getId());
//				ida.set_parentId(idadb.get_parentId());
//				continue;
//
//			}
			ida.setInterfaceId(inter.getInterfaceId());
			ida.set_parentId(responseId);
			if(parentId!=null){
				ida.set_parentId(parentId);
			}

			if("end".equalsIgnoreCase(ida.getRemark())|| "不映射".equalsIgnoreCase(ida.getRemark()) || ida.getStructName() == null || "".equals(ida.getStructName())){
				if("end".equalsIgnoreCase(ida.getRemark())){
					parentId = null;
				}
				continue;
			}
			//ida.setArgType("output");
			idaDao.save(ida);
			//包含子节点
			if("start".equalsIgnoreCase(ida.getRemark())){
				parentId = ida.getId();
			}
		}

	}

	private void insertSDA(boolean existsOper,Operation operation,com.dc.esb.servicegov.entity.Service service, List<SDA> sdainput, List<SDA> sdaoutput) {
		SDA sda = new SDA();
		String rootId="",requestId = "",responseId="";

		//覆盖
		if(GlobalImport.operateFlag){
			//先删除
			String hql = "delete from SDA where operationId=? and serviceId=?";
			sdaDAO.exeHql(hql,operation.getOperationId(),service.getServiceId());
		}else{
			if(existsOper){
				return;
			}
		}
		if(!existsOper || ( existsOper && GlobalImport.operateFlag)){
			sda.setSdaId(UUID.randomUUID().toString());
			sda.setOperationId(operation.getOperationId());
			sda.setParentId(null);
			sda.setStructName("root");
			sda.setStructAlias("根节点");
			sda.setServiceId(service.getServiceId());
			sdaDAO.save(sda);
			rootId = sda.getSdaId();

			sda = new SDA();
			sda.setSdaId(UUID.randomUUID().toString());
			sda.setOperationId(operation.getOperationId());
			sda.setParentId(rootId);
			sda.setStructName("request");
			sda.setStructAlias("请求头");
			sda.setServiceId(service.getServiceId());
			sda.setSeq(0);
			sdaDAO.save(sda);
			 requestId = sda.getSdaId();

			sda = new SDA();
			sda.setSdaId(UUID.randomUUID().toString());
			sda.setOperationId(operation.getOperationId());
			sda.setParentId(rootId);
			sda.setSeq(1);
			sda.setStructName("response");
			sda.setStructAlias("响应头");
			sda.setServiceId(service.getServiceId());
			sdaDAO.save(sda);
			responseId = sda.getSdaId();
		}


		String parentId = null;
		for(int i=0;i<sdainput.size();i++){
			sda = sdainput.get(i);

			//判断ida是否存在
//			Map<String,String> paramMap = new HashMap<String, String>();
//			paramMap.put("operationId",operation.getOperationId());
//			paramMap.put("structName",sda.getStructName());
//			if(sda.getMetadataId()!=null && !"".equals(sda.getMetadataId())) {
//				paramMap.put("metadataId", sda.getMetadataId());
//			}
//			paramMap.put("serviceId",service.getServiceId());
//			paramMap.put("argType","input");
//			SDA sdadb = sdaDAO.findUniqureBy(paramMap);
//			if(sdadb!=null){
//				sda.setSdaId(sdadb.getSdaId());
//				sda.setParentId(sdadb.getParentId());
//				continue;
//			}
			sda.setSdaId(UUID.randomUUID().toString());
			sda.setOperationId(operation.getOperationId());
			sda.setParentId(requestId);
			sda.setServiceId(service.getServiceId());


			if(parentId!=null){
				sda.setParentId(parentId);
			}
			if("end".equalsIgnoreCase(sda.getRemark())|| "不映射".equalsIgnoreCase(sda.getRemark())|| sda.getStructName()==null || "".equals(sda.getStructName())){
				if("end".equalsIgnoreCase(sda.getRemark())){
					parentId = null;
				}
				continue;
			}
			//sda.setArgType("input");
			sdaDAO.save(sda);
			//包含子节点
			if("start".equalsIgnoreCase(sda.getRemark())){
				parentId = sda.getSdaId();
			}
		}

		parentId = null;
		for(int i=0;i<sdaoutput.size();i++){
			sda = sdaoutput.get(i);
			//判断ida是否存在
//			Map<String,String> paramMap = new HashMap<String, String>();
//			paramMap.put("operationId",operation.getOperationId());
//			paramMap.put("structName",sda.getStructName());
//			if(sda.getMetadataId()!=null && !"".equals(sda.getMetadataId())) {
//				paramMap.put("metadataId", sda.getMetadataId());
//			}
//			paramMap.put("serviceId",service.getServiceId());
//			paramMap.put("argType","output");
//			SDA sdadb = sdaDAO.findUniqureBy(paramMap);
//			if(sdadb!=null){
//				sda.setSdaId(sdadb.getSdaId());
//				sda.setParentId(sdadb.getParentId());
//				continue;
//			}
			sda.setSdaId(UUID.randomUUID().toString());
			sda.setOperationId(operation.getOperationId());
			sda.setParentId(responseId);
			sda.setServiceId(service.getServiceId());

			if(parentId!=null){
				sda.setParentId(parentId);
			}
			if("end".equalsIgnoreCase(sda.getRemark()) || "不映射".equalsIgnoreCase(sda.getRemark()) || sda.getStructName()==null || "".equals(sda.getStructName())){
				if("end".equalsIgnoreCase(sda.getRemark())){
					parentId = null;
				}
				continue;
			}
			//sda.setArgType("output");
			sdaDAO.save(sda);
			//包含子节点
			if("start".equalsIgnoreCase(sda.getRemark())){
				parentId = sda.getSdaId();
			}
		}
	}


	private boolean insertInterface(Interface inter, com.dc.esb.servicegov.entity.Service service, Operation operation, ServiceInvoke provider_invoke,String providerSystem,String type) {
		Map<String,String> paramMap = new HashMap<String, String>();
		boolean exists = false;
		//已存在提供系统关系
		if(provider_invoke!=null){
			String interfaceId = provider_invoke.getInterfaceId();
			if(interfaceId!=null && !"".equals(interfaceId)){
				Interface interfaceDB = interfaceDao.getEntity(interfaceId);
				//覆盖
				if(GlobalImport.operateFlag){
					interfaceDB.setInterfaceName(inter.getInterfaceName());
					interfaceDB.setEcode(inter.getEcode());
					String version = interfaceDB.getVersion();
					if(version==null ||"".equals(version)){
						version = initVersion;
					}else {
						//interfaceDB.setVersion(Utils.modifyversionno(version));
					}
					interfaceDB.setVersion(version);
				}else {

					String version = interfaceDB.getVersion();
					if(version==null ||"".equals(version)){
						version = initVersion;
					}
					interfaceDB.setVersion(version);

				}
				interfaceDao.save(interfaceDB);
				inter.setInterfaceId(interfaceDB.getInterfaceId());
				exists = true;
			}else{
				inter.setVersion(initVersion);
				inter.setInterfaceId(inter.getEcode());
				interfaceDao.save(inter);
				provider_invoke.setInterfaceId(inter.getInterfaceId());

				serviceInvokeDAO.save(provider_invoke);

			}
		}else{
			inter.setVersion(initVersion);
			inter.setInterfaceId(inter.getEcode());
			//建立调用关系
			interfaceDao.save(inter);
			provider_invoke = new ServiceInvoke();
			paramMap.put("systemAb", providerSystem);
			System system = systemDao.findUniqureBy(paramMap);
			provider_invoke.setSystemId(system.getSystemId());
			provider_invoke.setServiceId(service.getServiceId());
			provider_invoke.setOperationId(operation.getOperationId());
			provider_invoke.setType(type);
			//添加协议==================
			// provider_invoke.setProtocolId("");
			provider_invoke.setInterfaceId(inter.getInterfaceId());
			serviceInvokeDAO.save(provider_invoke);
		}

		return exists;
	}



	//提供系统调用关系
	private ServiceInvoke serviceInvokeProviderQuery(com.dc.esb.servicegov.entity.Service service,Operation operation,String systemId,String interfacepoint){
		Map<String,String> paramMap = new HashMap<String, String>();
		//查询提供系统 关系
		paramMap.put("serviceId",service.getServiceId());
		paramMap.put("operationId",operation.getOperationId());
		paramMap.put("systemId",systemId);
		String type = "1";
		if("Provider".equalsIgnoreCase(interfacepoint)){
			type = "0";
		}
		paramMap.put("type",type);

		ServiceInvoke provider_invoke =  serviceInvokeDAO.findUniqureBy(paramMap);
		return provider_invoke;
	}

	//消费系统调用关系
	private ServiceInvoke serviceInvokeCusumerQuery(com.dc.esb.servicegov.entity.Service service,Operation operation,String cusumerSystem){
		Map<String,String> paramMap = new HashMap<String, String>();
		//查询提供系统 关系
		paramMap.put("serviceId",service.getServiceId());
		paramMap.put("operationId",operation.getOperationId());
		paramMap.put("systemId",cusumerSystem);
		paramMap.put("type","1");

		ServiceInvoke provider_invoke =  serviceInvokeDAO.findUniqureBy(paramMap);
		return provider_invoke;
	}

	private boolean insertOperation(com.dc.esb.servicegov.entity.Service service, Operation operation) {

		boolean exists = false;
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("operationId",operation.getOperationId());
		paramMap.put("serviceId",service.getServiceId());
		Operation operationDB = operationDAO.findUniqureBy(paramMap);
		if(operationDB!=null){
			//覆盖
			if(GlobalImport.operateFlag){
				operationDB.setOperationName(operation.getOperationName());
				operationDB.setOperationDesc(operation.getOperationDesc());
				/**
				 * TODO 版本等李旺完成后进行
				 */
//				String version = operationDB.getVersion();
//				if(version==null ||"".equals(version)){
//					version = initVersion;
//				}
//				if (AuditUtil.passed.equals(operationDB.getState())) {
//					//operationDB.setVersion(Utils.modifyversionno(version));
//				} else {
//					operationDB.setVersion(version);
//				}
				operationDAO.save(operationDB);
			}
			exists = true;
		}else{
			String versionId = versionService.addVersion(Constants.Version.TARGET_TYPE_OPERATION,operation.getOperationId(),Constants.Version.TYPE_ELSE);
			operation.setServiceId(service.getServiceId());
			operation.setVersionId(versionId);
			operation.setState(AuditUtil.submit);
			operationDAO.save(operation);
		}
		return exists;

	}
	private boolean insertService(com.dc.esb.servicegov.entity.Service service) {
		String serviceId = service.getServiceId();
		String categoryId = serviceId.substring(0, 5);
		String parentId = serviceId.substring(0, 4);

		//检查服务类别是否存在
		ServiceCategory serviceCategory = serviceCategoryDAO.getEntity(categoryId);
		if (serviceCategory != null) {
			if (!parentId.equals(serviceCategory.getParentId())) {
				logger.error("服务小类别不存在");
				LogInfo logInfo = new LogInfo();
				logInfo.setDetail("服务小类别不存在");
				logInfo.setType("导入");

				;
				logInfo.setTime(Utils.getTime());
				logInfoDAO.save(logInfo);

				return false;
			}
		} else {
			logger.error("服务类别不存在");
			LogInfo logInfo = new LogInfo();
			logInfo.setDetail("服务类别不存在");
			logInfo.setType("导入");

			;
			logInfo.setTime(Utils.getTime());
			logInfoDAO.save(logInfo);
			return false;
		}

		//检查服务是否存在
		com.dc.esb.servicegov.entity.Service serviceDB = serviceDao.getEntity(serviceId);
		if(serviceDB!=null){
			//覆盖
			if(GlobalImport.operateFlag){
				serviceDB.setServiceName(service.getServiceName());
				serviceDB.setDesc(service.getDesc());

				String version = serviceDB.getVersion();
				if(version==null ||"".equals(version)){
					version = initVersion;
				}
				if (AuditUtil.passed.equals(serviceDB.getState())) {
					//serviceDB.setVersion(Utils.modifyversionno(version));
				} else {
					serviceDB.setVersion(version);
				}
			}
			serviceDao.save(serviceDB);
		}else{
			//不存在 新增服务
			service.setCategoryId(categoryId);
			service.setVersion(initVersion);
			service.setState(AuditUtil.submit);

			serviceDao.save(service);
		}
		return true;
	}

	@Override
	public boolean existSystem(String  systemId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("systemAb", systemId);

	    System system = systemDao.findUniqureBy(paramMap);//systemDao.getEntity(systemId);
		if(system!=null){
			return true;
		}
		return false;

	}

	@Override
	public HibernateDAO getDAO() {
		return null;
	}
}
