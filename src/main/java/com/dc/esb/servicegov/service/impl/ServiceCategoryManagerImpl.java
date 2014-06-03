package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.ServiceBelongDAOImpl;
import com.dc.esb.servicegov.dao.impl.ServiceCategoryDAOImpl;
import com.dc.esb.servicegov.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.entity.ServiceBelong;
import com.dc.esb.servicegov.entity.ServiceCategory;
import com.dc.esb.servicegov.exception.DataException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-29
 * Time: 下午6:02
 */
@Component
public class ServiceCategoryManagerImpl {

    private static final Log log = LogFactory.getLog(ServiceCategoryManagerImpl.class);

    @Autowired
    private ServiceCategoryDAOImpl serviceCategoryDAO;
    @Autowired
    private ServiceBelongDAOImpl serviceBelongDAO;
    @Autowired
    private ServiceDAOImpl serviceDAO;

    @Transactional
    public List<Service> getServicesByCategory(String categoryId){
        List<Service> services = null;
        if(null != categoryId){
            List<ServiceBelong> serviceBelongs = serviceBelongDAO.findBy("groupId",categoryId);
            if(null != serviceBelongs){
                services = new ArrayList<Service>();
                for(ServiceBelong serviceBelong : serviceBelongs){
                    List<Service> servicesById = serviceDAO.findBy("serviceId", serviceBelong.getServiceId());
                    services.addAll(servicesById);
                }
            }
        }
        return services;
    }

    @Transactional
    public ServiceCategory getCategoryById(String id) throws DataException {
        ServiceCategory category = null;
        if(null != id){
            List<ServiceCategory> categories = serviceCategoryDAO.findBy("groupId", id);
            if(null == categories){
                String errorMsg = "服务分组["+id+"]不存在！";
                log.error(errorMsg);
                throw new DataException(errorMsg);
            }
            if(0 == categories.size()){
                String errorMsg = "服务分组["+id+"]不存在！";
                log.error(errorMsg);
                throw new DataException(errorMsg);
            }
            if(categories.size() > 1){
                String errorMsg = "服务分组["+id+"]重复存在！";
                log.error(errorMsg);
                throw new DataException(errorMsg);
            }
            category = categories.get(0);

        }
        return category;
    }

    @Transactional
    public List<ServiceCategory> getAllCategories(){
        return  serviceCategoryDAO.getAll();
    }

    @Transactional
    public List<ServiceCategory> getTopCategories(){
        return serviceCategoryDAO.findBy("parentId", "MSMGroup");
    }

    @Transactional
    public List<ServiceCategory> getSubCategories(ServiceCategory topServiceCategory){
        String categoryId = topServiceCategory.getGroupId();
        return serviceCategoryDAO.findBy("parentId", categoryId);
    }

}
