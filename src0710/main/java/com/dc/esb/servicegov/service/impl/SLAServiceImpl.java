package com.dc.esb.servicegov.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.dao.impl.SLADAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.SLA;
import com.dc.esb.servicegov.service.support.BaseService;

@Service
@Transactional
public class SLAServiceImpl extends BaseService<SLA, String>{
	
	@Autowired
	private SLADAOImpl slaDAOImpl;

	@Override
	public HibernateDAO<SLA, String> getDAO() {
		return slaDAOImpl;
	}
	
	public List<SLA> getTemplateSLA(Map<String,String> params){
//		return slaDAOImpl.findTemplateBy(params);
		return slaDAOImpl.find(" from SLA where slaTemplateId=?", params.get("slaTemplateId"));
	}
	
	public ModelAndView slaPage(String operationId, String serviceId,
			HttpServletRequest req) {
		try {
			serviceId = URLDecoder.decode(serviceId, "utf-8");
			operationId = URLDecoder.decode(operationId, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView("service/sla/slaPage");
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
				}
			}
		}

		return mv;
	}
	public List<SLA> findByOS(String serviceId, String operationId){
		return slaDAOImpl.find(" from SLA where serviceId=? and operationId=?", serviceId, operationId);
	}
}
