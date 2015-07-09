package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import com.dc.esb.servicegov.util.EasyUiTreeUtil;
import com.dc.esb.servicegov.util.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ServiceServiceImpl extends AbstractBaseService<com.dc.esb.servicegov.entity.Service, String> {

    @Autowired
    private ServiceDAOImpl serviceDAOImpl;

    @Autowired
    private OperationDAOImpl operationDAOImpl;

    @Override
    public HibernateDAO<com.dc.esb.servicegov.entity.Service, String> getDAO() {
        return serviceDAOImpl;
    }

    public com.dc.esb.servicegov.entity.Service getUniqueByServiceId(String serviceId) {
        return serviceDAOImpl.findUniqueBy("serviceId", serviceId);
    }

    public List<Operation> getOperationByServiceId(String serviceId) {
        String hql = " from Operation a where a.serviceId = ?";
        return operationDAOImpl.find(hql, serviceId);
    }

    public List<TreeNode> genderServiceTree() {
        List<com.dc.esb.servicegov.entity.Service> list = serviceDAOImpl.getAll();
        Map<String, String> fields = new HashMap<String, String>();
        fields.put("id", "serviceId");
        fields.put("text", "serviceName");
        fields.put("append1", "version");
        fields.put("append2", "desc");
        fields.put("append3", "remark");

        EasyUiTreeUtil eUtil = new EasyUiTreeUtil();

        return eUtil.convertTree(list, fields);

    }
}
