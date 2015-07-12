package com.dc.esb.servicegov.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.dao.impl.OperationHisDAOImpl;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.OperationHis;
import com.dc.esb.servicegov.service.OperationHisService;

@Service
@Transactional
public class OperationHisServiceImpl extends BaseServiceImpl<OperationHis> implements OperationHisService{
	@Autowired
    private OperationHisDAOImpl operationHisDAOImpl;
	
	 public ModelAndView hisPage(HttpServletRequest req, String operationId, String serviceId){
	    	if(StringUtils.isNotEmpty(serviceId)){
	    		try {
	    			serviceId = URLDecoder.decode(serviceId, "utf-8");
	    			if(StringUtils.isNotEmpty(operationId)){
	    				operationId = URLDecoder.decode(operationId, "utf-8");
	    			}
	    			
	    			ModelAndView mv = new ModelAndView("service/operationHis/hisPage");
	    			
	    			//根据serviceId查询service信息
	    			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
	    			ServiceServiceImpl ss = context .getBean(ServiceServiceImpl.class);
	    			com.dc.esb.servicegov.entity.Service service = ss.getUniqueByServiceId(serviceId);
	    			if(service != null){
	    				mv.addObject("service", service);
	    			}
	    			
	    			//根据operationId查询operation
	    			OperationServiceImpl os = context.getBean(OperationServiceImpl.class);
	    			Operation operation = os.getOperationByOperationIdServiceId(operationId, serviceId);
	    			if(operation != null){
	    				mv.addObject("operation", operation);
	    			}
	    			
	    			return mv;
	    		} catch (UnsupportedEncodingException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		
	    	}
	    	return null;
	    }
	
	//根据operationId获取operation
		public List<OperationHis> getByOS(String operationId, String serviceId){
			return operationHisDAOImpl.find(" from OperationHis where operationId = ? and serviceId = ?", operationId, serviceId);
		}
		
		public OperationHis getByAutoId(String autoId){
			OperationHis entity = operationHisDAOImpl.findUnique(" from OperationHis where autoId=?", autoId);
			return entity;
			
		}
}
