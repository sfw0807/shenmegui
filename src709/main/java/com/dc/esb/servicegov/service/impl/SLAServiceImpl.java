package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.SlaDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.SLA;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
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

import java.util.List;
import java.util.Map;
import com.dc.esb.servicegov.dao.impl.SlaDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.SLA;
import com.dc.esb.servicegov.service.support.BaseService;

@Service
@Transactional
public class SLAServiceImpl extends AbstractBaseService<SLA, String>{

    @Autowired
    private SlaDAOImpl slaDAOImpl;

    @Override
    public HibernateDAO<SLA, String> getDAO() {
        return slaDAOImpl;
    }

    public List<SLA> getTemplateSLA(Map<String,String> params){
        return slaDAOImpl.findTemplateBy(params);
	}

	public ModelAndView slaPage(String operationId, String serviceId,
			HttpServletRequest req) {
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
				Operation operation = os.getOperation(
						operationId, serviceId);
				if (operation != null) {
					mv.addObject("operation", operation);
				}
			}
		}

		return mv;
    }
}