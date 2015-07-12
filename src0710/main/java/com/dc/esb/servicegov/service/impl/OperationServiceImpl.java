package com.dc.esb.servicegov.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.entity.OLA;
import com.dc.esb.servicegov.entity.OLAHis;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.OperationHis;
import com.dc.esb.servicegov.entity.SDA;
import com.dc.esb.servicegov.entity.SDAHis;
import com.dc.esb.servicegov.entity.SLA;
import com.dc.esb.servicegov.entity.SLAHis;
import com.dc.esb.servicegov.entity.ServiceHead;
import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.OperationService;
import com.dc.esb.servicegov.util.DateUtils;

@Service
@Transactional
public class OperationServiceImpl extends BaseServiceImpl<Operation> implements OperationService{
	@Autowired
    private OperationDAOImpl operationDAOImpl;
	@Autowired
	private VersionServiceImpl versionServiceImpl;
	@Autowired
	private ServiceServiceImpl serviceServiceImpl;
	@Autowired
	private ServiceInvokeServiceImpl serviceInvokeServiceImpl;
	public List<Operation> getOperationByServiceId(String serviceId){
		String hql = " from Operation a where deleted != '1' and a.serviceId = ?";
		return operationDAOImpl.find(hql, serviceId);
	}
	//根据operationId获取operation
	public Operation getOperationByOperationIdServiceId(String operationId, String serviceId){
		return operationDAOImpl.findUnique(" from Operation where deleted != '1' and operationId = ? and serviceId = ?", operationId, serviceId);
	}
	//获取待审核的operation
	public List<Operation> getByServiceState(String serviceId, String state){
		String hql = " from Operation a where deleted != '1' and a.serviceId = ? and state = ?";
		return operationDAOImpl.find(hql, serviceId, state);
	}
	
	//查看operationId是否重复
	public boolean uniqueValid(String operationId, String serviceId){
		Operation entity = getOperationByOperationIdServiceId(operationId, serviceId);
		if(entity != null){
    		return false;
    	}
    	return true;
	}
	
	public ModelAndView addPage(HttpServletRequest req, String serviceId){
		ModelAndView mv = new ModelAndView("service/operation/add");
		//根据serviceId查询service信息注入operation
		com.dc.esb.servicegov.entity.Service service = serviceServiceImpl.getUniqueByServiceId(serviceId);
		if(service != null){
			mv.addObject("service", service);
			
			List<?> systemList = operationDAOImpl.find(" from System ");
			if(systemList != null && systemList.size() > 0){
				mv.addObject("systemList", JSONArray.fromObject(systemList));
			}
		}
		
		return mv;
	}
		
	
	public boolean addOperation(HttpServletRequest req, Operation entity){
		 try{
			 String versionId = versionServiceImpl.addVersion("1", entity.getOperationId());
			 entity.setVersionId(versionId);
			 entity.setDeleted("0");
			 entity.setOptDate(DateUtils.format(new Date()));
			 
			 operationDAOImpl.save(entity);
			 
			 ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
		     SDAServiceImpl sdaServiceImpl = context.getBean(SDAServiceImpl.class);
		     sdaServiceImpl.genderSDAAuto(entity);
		     
		 }catch(Exception e){
			 e.printStackTrace();
			 return false;
		 }
		 return true;
	 }
	//接口关系保存
	public boolean afterAdd(HttpServletRequest req, String serviceId, String operationId, String consumerStr, String providerStr) {
		if(StringUtils.isNotEmpty(consumerStr)){
			String[] constrs = consumerStr.split("\\,");
			for(String constr : constrs){
				if(constr.contains("__invoke__")){ //判断是否是serviceInvokeID
					constr = constr.replace("__invoke__", "");
					ServiceInvoke si = operationDAOImpl.findUnique(" from ServiceInvoke where invokeId = ?", constr);
					if(si != null){
						if(StringUtils.isEmpty(si.getServiceId()) && StringUtils.isEmpty(si.getOperationId())){
							si.setServiceId(serviceId);
							si.setOperationId(operationId);
							si.setType("1"); //1代表消费方
							serviceInvokeServiceImpl.editEntity(si);
						}
						else{
							if(!serviceId.equals(si.getServiceId()) || !operationId.equals(si.getOperationId())){
								ServiceInvoke newsi = new ServiceInvoke();
								newsi.setInvokeId(UUID.randomUUID().toString());
								newsi.setSystemId(si.getSystemId());
								newsi.setServiceId(serviceId);
								newsi.setOperationId(operationId);
								newsi.setType("1");
								newsi.setInterfaceId(si.getInterfaceId());
								serviceInvokeServiceImpl.editEntity(newsi);
							}
						}
					}
				}
				else{//传入参数为systemId
					ServiceInvoke si = new ServiceInvoke();
					si.setInvokeId(UUID.randomUUID().toString());
					si.setSystemId(constr);
					si.setServiceId(serviceId);
					si.setOperationId(operationId);
					si.setType("1");
					serviceInvokeServiceImpl.editEntity(si);
				}
			}
		}
		if(StringUtils.isNotEmpty(providerStr)){
			String[] prostrs = providerStr.split("\\,");
			for(String prostr : prostrs){
				if(prostr.contains("__invoke__")){ //判断是否是serviceInvokeID
					prostr = prostr.replace("__invoke__", "");
					ServiceInvoke si = operationDAOImpl.findUnique(" from ServiceInvoke where invokeId = ?", prostr);
					if(si != null){
						if(StringUtils.isEmpty(si.getServiceId()) && StringUtils.isEmpty(si.getOperationId())){
							si.setServiceId(serviceId);
							si.setOperationId(operationId);
							si.setType("0"); //1代表消费方
							serviceInvokeServiceImpl.editEntity(si);
						}
						else{
							if(!serviceId.equals(si.getServiceId()) || !operationId.equals(si.getOperationId())){
								ServiceInvoke newsi = new ServiceInvoke();
								newsi.setInvokeId(UUID.randomUUID().toString());
								newsi.setSystemId(si.getSystemId());
								newsi.setServiceId(serviceId);
								newsi.setOperationId(operationId);
								newsi.setType("0");
								newsi.setInterfaceId(si.getInterfaceId());
								serviceInvokeServiceImpl.editEntity(newsi);
							}
						}
					}
				}
				else{//传入参数为systemId
					ServiceInvoke si = new ServiceInvoke();
					si.setInvokeId(UUID.randomUUID().toString());
					si.setSystemId(prostr);
					si.setServiceId(serviceId);
					si.setOperationId(operationId);
					si.setType("0");
					serviceInvokeServiceImpl.editEntity(si);
				}
			}
		}
		return true;
	}
		
		
	
	public ModelAndView editPage(HttpServletRequest req, String operationId, String serviceId){
		try {
			serviceId = URLDecoder.decode(serviceId, "utf-8");
			operationId = URLDecoder.decode(operationId, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView("service/operation/edit");
		//根据operationId查询operation
		Operation operation = getOperationByOperationIdServiceId(operationId, serviceId);
		if(operation != null){
			mv.addObject("operation", operation);
			//根据operation查询service信息
			com.dc.esb.servicegov.entity.Service service = serviceServiceImpl.getUniqueByServiceId(operation.getServiceId());
			if(service != null){
				mv.addObject("service", service);
				
				List<?> systemList = operationDAOImpl.find(" from System ");
				mv.addObject("systemList", JSONArray.fromObject(systemList));
				
				List<?> consumerList = operationDAOImpl.find(" from ServiceInvoke where serviceId=? and operationId=? and type=? ", serviceId, operationId, "1");
				mv.addObject("consumerList", JSONArray.fromObject(consumerList));
				
				List<?> providerList = operationDAOImpl.find(" from ServiceInvoke where serviceId=? and operationId=? and type=? ", serviceId, operationId, "0");
				mv.addObject("providerList", JSONArray.fromObject(providerList));
			}
		}
		
		return mv;
	}
	public boolean editOperation(HttpServletRequest req, Operation entity){
		try{
			versionServiceImpl.editVersion(entity.getVersionId());
			 
			 entity.setOptDate(DateUtils.format(new Date()));
			 operationDAOImpl.save(entity);
		 }catch(Exception e){
			 e.printStackTrace();
			 return false;
		 }
		 return true;
	}
    public void deleteOperations(HttpServletRequest req, String serviceId, String operationIds){
    	String[] ids = operationIds.split("\\,");
    	if(ids != null && ids.length > 0){
    		for(String operationId : ids){
    			Operation operation = getOperationByOperationIdServiceId(operationId, serviceId);
    			if(operation != null){
    				ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
    	    		OperationHisServiceImpl ohs = context.getBean(OperationHisServiceImpl.class);
    	    		
    	    		OperationHis operationHis = new OperationHis(operation);
    	    		ohs.editEntity(operationHis);
    	    		
    	    		versionServiceImpl.deleteVersion(operation.getVersionId(), operationHis.getAutoId());
    	    		
    	    		operationDAOImpl.batchExecute(" delete from Operation where serviceId=? and operationId=?", serviceId, operationId);
    	    		
    			}
    			
    		}
    	}
    }
    
    public ModelAndView detailPage(HttpServletRequest req, String operationId, String serviceId){
    	try {
			serviceId = URLDecoder.decode(serviceId, "utf-8");
			operationId = URLDecoder.decode(operationId, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView("service/operation/detailPage");
		
		//根据serviceId查询service信息
		com.dc.esb.servicegov.entity.Service service = serviceServiceImpl.getUniqueByServiceId(serviceId);
		if(service != null){
			mv.addObject("service", service);
		}
		
		//根据operationId查询operation
		Operation operation = getOperationByOperationIdServiceId(operationId, serviceId);
		
		if(operation != null){
			mv.addObject("operation", operation);
			//查询serviceHead
			String serviceHeadHql = " from ServiceHead where headId = ?	";
			ServiceHead serviceHead = operationDAOImpl.findUnique(serviceHeadHql, operation.getHeadId());
			if(serviceHead != null){
				mv.addObject("serviceHead", serviceHead);
			}
		}
		
		return mv;
	}
    
    public boolean releaseBatch(HttpServletRequest req, @RequestBody Operation[] operations) {
    	if(operations != null && operations.length > 0	){
    		for(Operation operation : operations){
    			release(req, operation.getOperationId(), operation.getServiceId(), operation.getOperationDesc());
    		}
    	}
    	return false;
    }
    
    public ModelAndView release(HttpServletRequest req, String operationId, String serviceId,  String versionDesc){
    	//转移到his
    	try {
			serviceId = URLDecoder.decode(serviceId, "utf-8");
			operationId = URLDecoder.decode(operationId, "utf-8");
			versionDesc = URLDecoder.decode(versionDesc, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Operation operation = getOperationByOperationIdServiceId(operationId, serviceId);
    	
    	if(operation != null){
    		//备份相关
    		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
    		OperationHisServiceImpl ohs = context.getBean(OperationHisServiceImpl.class);
    		
    		OperationHis operationHis = new OperationHis(operation);
    		ohs.editEntity(operationHis);
    		
    		SDAServiceImpl ss = context.getBean(SDAServiceImpl.class);
    		List<SDA> sdaList = ss.getSDAListBySO(operation.getServiceId(), operation.getOperationId());
    		if(sdaList != null && sdaList.size() > 0){
    			SDAHisServiceImpl shs = context.getBean(SDAHisServiceImpl.class);
    			for(SDA sda : sdaList){
    				SDAHis sdaHis = new SDAHis(sda, operationHis.getAutoId());
    				shs.editEntity(sdaHis);
    			}
    		}
    		
    		List<SLA> slaList = operationDAOImpl.find(" from SLA where serviceId = ? and operationId = ?", serviceId, operationId);
    		if(slaList != null && slaList.size() > 0){
    			SLAHisServiceImpl slhs = context.getBean(SLAHisServiceImpl.class);
    			for(SLA sla : slaList){
    				SLAHis slaHis = new SLAHis(sla, operationHis.getAutoId());
    				slhs.editEntity(slaHis);
    			}
    		}
    		
    		List<OLA> olaList = operationDAOImpl.find(" from OLA where serviceId = ? and operationId = ?", serviceId, operationId);
    		if(olaList != null && olaList.size() > 0){
    			OLAHisServiceImpl olhs =  context.getBean(OLAHisServiceImpl.class);
    			for(OLA ola : olaList){
    				OLAHis olaHis = new OLAHis(ola, operationHis.getAutoId());
    				olhs.editEntity(olaHis);
    			}
    		}
    		//修改operationHis 版本
    		String versionHisId = versionServiceImpl.releaseVersion(operation.getVersionId(), operationHis.getAutoId(), versionDesc);
    		operationHis.setVersionHisId(versionHisId);
    		ohs.editEntity(operationHis);
    		
        	operationDAOImpl.save(operation);
    	}
    	
    	return detailPage(req, operationId, serviceId);
    }
    
    public ModelAndView auditPage(HttpServletRequest req, String serviceId){
		ModelAndView mv = new ModelAndView("service/operation/audit");
		
		//根据serviceId查询service信息
		com.dc.esb.servicegov.entity.Service service = serviceServiceImpl.getUniqueByServiceId(serviceId);
		if(service != null){
			mv.addObject("service", service);
		}
		
		
		return mv;
	}
    
    public boolean auditOperation(String state, String[] operationIds){
    	if(operationIds != null && operationIds.length > 0){
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("state", state);
    		params.put("operationIds", operationIds);
    		operationDAOImpl.batchExecute(" update Operation set state =(:state) where operationId in (:operationIds)", params);
    		return true;
    	}
    	return false;
    }
    
    public boolean judgeInterface(String systemId){
    	String hql = " from ServiceInvoke where systemId = ?";
    	List<ServiceInvoke> list =  operationDAOImpl.find(hql, systemId);
    	if(list != null && list.size() > 0){
    		for(ServiceInvoke si : list){
    			if(StringUtils.isNotEmpty(si.getInterfaceId())){
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    
    public ModelAndView interfacePage(String operationId, String serviceId,
			HttpServletRequest req) {
		try {
			serviceId = URLDecoder.decode(serviceId, "utf-8");
			operationId = URLDecoder.decode(operationId, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView("service/operation/interfacePage");
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(req.getSession().getServletContext());
		// 根据serviceId获取service信息
		if (StringUtils.isNotEmpty(serviceId)) {
			ServiceServiceImpl ss = context.getBean(ServiceServiceImpl.class);
			com.dc.esb.servicegov.entity.Service service = ss
					.getUniqueByServiceId(serviceId);
			if (service != null) {
				mv.addObject("service", service);
			}
			if (StringUtils.isNotEmpty(operationId)) {
				// 根据serviceId,operationId获取operation信息
				OperationServiceImpl os = context
						.getBean(OperationServiceImpl.class);
				Operation operation = os.getOperationByOperationIdServiceId(
						operationId, serviceId);
				if (operation != null) {
					mv.addObject("operation", operation);
					
					List<?> systemList = operationDAOImpl.find(" from System ");
					mv.addObject("systemList", JSONArray.fromObject(systemList));
				}
			}
		}

		return mv;
	}
    public List<?> getInterface(String systemId){
    	String hql = " from ServiceInvoke where systemId = ? and interfaceId is not null";
    	List<ServiceInvoke> list =  operationDAOImpl.find(hql, systemId);
    	return list;
    }
    public List<?> getInterfaceByOSS(String serviceId, String operationId, String systemId){
    	String hql = " from ServiceInvoke where serviceId=? and operationId=?";
    	List<ServiceInvoke> list;
    	if(StringUtils.isNotEmpty(systemId)){
    		hql += " and systemId=?";
    		list =  operationDAOImpl.find(hql, serviceId, operationId, systemId);
    	}else{
    		list = operationDAOImpl.find(hql, serviceId, operationId);
    	}
    	
    	return list;
    }
}
