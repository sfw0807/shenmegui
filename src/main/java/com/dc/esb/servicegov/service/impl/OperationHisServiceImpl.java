package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.OperationHisDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.service.OperationService;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.entity.OperationHis;
import com.dc.esb.servicegov.service.OperationHisService;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * TODO 没有dao
 */
@Service
@Transactional
public class OperationHisServiceImpl extends AbstractBaseService<OperationHis,String> implements OperationHisService{
    @Autowired
    private OperationHisDAOImpl operationHisDAOImpl;
    @Autowired
    private OperationServiceImpl operationService;
    @Autowired
    private ServiceServiceImpl serviceService;

    public ModelAndView hisPage(HttpServletRequest req, String operationId, String serviceId){
        if(StringUtils.isNotEmpty(serviceId)){
            try {
                serviceId = URLDecoder.decode(serviceId, "utf-8");
                if(StringUtils.isNotEmpty(operationId)){
                    operationId = URLDecoder.decode(operationId, "utf-8");
                }
                ModelAndView mv = new ModelAndView("service/operationHis/hisPage");
                //根据serviceId查询service信息
                com.dc.esb.servicegov.entity.Service service = serviceService.getById(serviceId);
                if(service != null){
                    mv.addObject("service", service);
                }
                //根据operationId查询operation
                Operation operation = operationService.getOperation(operationId, serviceId);
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
    @Override
    public HibernateDAO<OperationHis, String> getDAO() {
        return null;
    }
}
