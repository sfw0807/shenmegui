package com.dc.esb.servicegov.service.impl;

import java.util.*;

import com.dc.esb.servicegov.dao.ServiceDAO;
import com.dc.esb.servicegov.dao.impl.*;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.dao.support.SearchCondition;
import com.dc.esb.servicegov.entity.*;
import com.dc.esb.servicegov.entity.System;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import com.dc.esb.servicegov.util.AuditUtil;
import com.dc.esb.servicegov.util.GlobalImport;
import com.dc.esb.servicegov.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dc.esb.servicegov.service.ExcelImportService;

@Service
public class ExcelImportServiceImpl extends AbstractBaseService implements ExcelImportService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

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

	private String initVersion = "1.0.0";

	@Override
	public boolean executeImport(Map<String, Object> infoMap, Map<String, Object> inputMap, Map<String, Object> outMap,Map<String,String> publicMap) {
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

			//获取调用关系
			ServiceInvoke provider_invoke = serviceInvokeProviderQuery(service, operation, providerSystem);

			//获取消费关系
			//ServiceInvoke cusumer_invoke = serviceInvokeCusumerQuery(service, operation, cusumerSystem);

			//导入接口相关信息
			logger.info("导入接口定义信息...");
			boolean exists = insertInterface(inter,service, operation,provider_invoke,providerSystem);

			insertIDA(exists,inter,idainput,idaoutput);

			insertSDA(existsOper,operation,service,sdainput,sdaoutput);


		}else {

			return false;

		}
		return true;
	}

	private void insertIDA(boolean exists,Interface inter, List<Ida> idainput, List<Ida> idaoutput) {
		//添加报文，自动生成固定报文头<root><request><response>
		//root
		Ida ida = new Ida();
		String rootId="",requestId = "",responseId="";
		if(!exists) {
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

		for(int i=0;i<idainput.size();i++){
			ida = idainput.get(i);
			ida.setInterfaceId(inter.getInterfaceId());
			ida.set_parentId(requestId);
			//判断ida是否存在
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("interfaceId",inter.getInterfaceId());
			paramMap.put("structName",ida.getStructName());
			paramMap.put("metadataId",ida.getMetadataId());
			Ida idadb = idaDao.findUniqureBy(paramMap);
			if(idadb!=null){
				ida.setId(idadb.getId());
				ida.set_parentId(idadb.get_parentId());
//				idaDao.getSession().merge(ida);
				continue;
			}

			idaDao.save(ida);
		}

		for(int i=0;i<idaoutput.size();i++){
			ida = idaoutput.get(i);
			ida.setInterfaceId(inter.getInterfaceId());
			ida.set_parentId(responseId);

			//判断ida是否存在
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("interfaceId",inter.getInterfaceId());
			paramMap.put("structName",ida.getStructName());
			paramMap.put("metadataId",ida.getMetadataId());
			Ida idadb = idaDao.findUniqureBy(paramMap);
			if(idadb!=null){
				ida.setId(idadb.getId());
				ida.set_parentId(idadb.get_parentId());
//				//将session中相同的Object合并一下
				//idaDao.getSession().merge(ida);
				continue;

			}

			idaDao.save(ida);
		}

	}

	private void insertSDA(boolean existsOper,Operation operation,com.dc.esb.servicegov.entity.Service service, List<SDA> sdainput, List<SDA> sdaoutput) {
		SDA sda = new SDA();
		String rootId="",requestId = "",responseId="";
		if(!existsOper){
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
			sdaDAO.save(sda);
			responseId = sda.getSdaId();
		}



		for(int i=0;i<sdainput.size();i++){
			sda = sdainput.get(i);
			sda.setSdaId(UUID.randomUUID().toString());
			sda.setOperationId(operation.getOperationId());
			sda.setParentId(requestId);
			sda.setServiceId(service.getServiceId());

			//判断ida是否存在
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("operationId",sda.getOperationId());
			paramMap.put("structName",sda.getStructName());
			paramMap.put("metadataId",sda.getMetadataId());
			paramMap.put("serviceId",sda.getServiceId());
			SDA sdadb = sdaDAO.findUniqureBy(paramMap);
			if(sdadb!=null){
				sda.setSdaId(sdadb.getSdaId());
				sda.setParentId(sdadb.getParentId());
//				sdaDAO.getSession().merge(sda);
				continue;
			}

			sdaDAO.save(sda);
		}

		for(int i=0;i<sdaoutput.size();i++){
			sda = sdaoutput.get(i);
			sda.setSdaId(UUID.randomUUID().toString());
			sda.setOperationId(operation.getOperationId());
			sda.setParentId(responseId);
			sda.setServiceId(service.getServiceId());

			//判断ida是否存在
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("operationId",sda.getOperationId());
			paramMap.put("structName",sda.getStructName());
			paramMap.put("metadataId",sda.getMetadataId());
			paramMap.put("serviceId",sda.getServiceId());
			SDA sdadb = sdaDAO.findUniqureBy(paramMap);
			if(sdadb!=null){
				sda.setSdaId(sdadb.getSdaId());
				sda.setParentId(sdadb.getParentId());
//				sdaDAO.getSession().merge(sda);
				continue;
			}

			sdaDAO.save(sda);
		}
	}


	private boolean insertInterface(Interface inter, com.dc.esb.servicegov.entity.Service service, Operation operation, ServiceInvoke provider_invoke,String providerSystem) {
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
			provider_invoke.setSystemId(providerSystem);
			provider_invoke.setServiceId(service.getServiceId());
			provider_invoke.setOperationId(operation.getOperationId());
			provider_invoke.setType("0");
			//添加协议==================
			// provider_invoke.setProtocolId("");
			provider_invoke.setInterfaceId(inter.getInterfaceId());
			serviceInvokeDAO.save(provider_invoke);
		}

		return exists;
		/*if(cusumer_invoke!=null){
			String interfaceId = cusumer_invoke.getInterfaceId();
			if(interfaceId!=null && !"".equals(interfaceId)) {
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
			}else{
				inter.setVersion(initVersion);
				interfaceDao.save(inter);
				provider_invoke.setInterfaceId(inter.getInterfaceId());

				serviceInvokeDAO.save(provider_invoke);
			}
		}else{
			inter.setVersion(initVersion);
			//建立消费关系
			interfaceDao.save(inter);
			cusumer_invoke = new ServiceInvoke();
			cusumer_invoke.setSystemId(cusumerSystem);
			cusumer_invoke.setServiceId(service.getServiceId());
			cusumer_invoke.setType("1");
			cusumer_invoke.setOperationId(operation.getOperationId());
			//添加协议==================
			// provider_invoke.setProtocolId("");
			cusumer_invoke.setInterfaceId(inter.getInterfaceId());
			serviceInvokeDAO.save(cusumer_invoke);
		}*/
	}



	//提供系统调用关系
	private ServiceInvoke serviceInvokeProviderQuery(com.dc.esb.servicegov.entity.Service service,Operation operation,String providerSystem){
		Map<String,String> paramMap = new HashMap<String, String>();
		//查询提供系统 关系
		paramMap.put("serviceId",service.getServiceId());
		paramMap.put("operationId",operation.getOperationId());
		paramMap.put("systemId",providerSystem);
		paramMap.put("type","0");

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

	/**
	 * TODO 修改版本
	 * @param service
	 * @param operation
	 * @return
	 */
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

			operation.setServiceId(service.getServiceId());
//			operation.setVersion(initVersion);
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
				return false;
			}
		} else {
			logger.error("服务类别不存在");
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
	public boolean existSystem(String... systemIds) {
	    if(systemIds.length == 0){
			return  false;
		}
		String hql = "SELECT t1 from System t1 where ";
		List<SearchCondition> searchs = new ArrayList<SearchCondition>();
		for (String systemId : systemIds) {
			hql += "t1.systemId = ? or ";
			SearchCondition search = new SearchCondition();
			search.setFieldValue(systemId);
			searchs.add(search);
		}
		hql = hql.substring(0,hql.lastIndexOf("or"));
		List<System> systems = systemDao.findBy(hql, searchs);
		return systems.size() == systemIds.length;

	}
	@Override
	public HibernateDAO getDAO() {
		return null;
	}
}
