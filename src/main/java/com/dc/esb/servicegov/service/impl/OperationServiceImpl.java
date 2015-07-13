package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.OperationHis;
import com.dc.esb.servicegov.entity.ServiceHead;
import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.OperationService;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import com.dc.esb.servicegov.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional
public class OperationServiceImpl extends AbstractBaseService<Operation, String> implements OperationService {

    private static final String initalVersion = "1.0.0";

    @Autowired
    private OperationDAOImpl operationDAOImpl;
    @Autowired
    private OperationHisServiceImpl operationHisService;
    @Autowired
    private SDAServiceImpl sdaService;
    @Autowired
    private SDAHisServiceImpl sdaHisService;
    @Autowired
    private SLAServiceImpl slaService;
    @Autowired
    private SLAHisServiceImpl slaHisService;
    @Autowired
    private OLAServiceImpl olaService;
    @Autowired
    private OLAHisServiceImpl olaHisService;
    @Autowired
    private ServiceServiceImpl serviceService;
    @Autowired
    private ServiceInvokeServiceImpl serviceInvokeService;
    @Autowired
    private VersionServiceImpl versionServiceImpl;

    public List<Operation> getOperationByServiceId(String serviceId) {
        String hql = " from Operation a where a.serviceId = ?";
        return operationDAOImpl.find(hql, serviceId);
    }

    /**
     * 获取某个服务的未审核场景
     *
     * @param serviceId
     * @return TODO 请把常量"0"统一管理
     */
    public List<Operation> getUnAuditOperationByServiceId(String serviceId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("serviceId", serviceId);
        params.put("state", "0");
        return findBy(params);

    }

    /**
     * 根据 服务ID 场景ID获取场景
     *
     * @param serviceId
     * @param operationId
     * @return TODO 请添加 主键类 OperationPK
     */
    public Operation getOperation(String serviceId, String operationId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("serviceId", serviceId);
        params.put("operationId", operationId);
        return findUniqueBy(params);

    }


    public boolean uniqueValid(String serviceId, String operationId) {
        Operation entity = getOperation(serviceId, operationId);
        if (entity != null) {
            return false;
        }
        return true;
    }

    @Override
    public void save(Operation entity) {
        entity.setOptDate(DateUtils.format(new Date()));
        super.save(entity);
    }

    @Override
    public HibernateDAO getDAO() {
        return operationDAOImpl;
    }

    public boolean addOperation(HttpServletRequest req, Operation entity) {
        try {
            String versionId = versionServiceImpl.addVersion("1", entity.getOperationId());
            entity.setVersionId(versionId);
            entity.setDeleted("0");
            entity.setOptDate(DateUtils.format(new Date()));
            operationDAOImpl.save(entity);
            sdaService.genderSDAAuto(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean editOperation(HttpServletRequest req, Operation entity) {
        try {
            versionServiceImpl.editVersion(entity.getVersionId());
            entity.setOptDate(DateUtils.format(new Date()));
            operationDAOImpl.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void deleteOperations(String operationIds) {
        String[] ids = operationIds.split("\\,");
        if (ids != null && ids.length > 0) {
            for (String operationId : ids) {
                operationDAOImpl.delete(operationId);
            }
        }
    }

    public ModelAndView detailPage(HttpServletRequest req, String operationId, String serviceId) {

        ModelAndView mv = new ModelAndView("service/operation/detailPage");
        //根据serviceId查询service信息
        com.dc.esb.servicegov.entity.Service service = serviceService.getById(serviceId);
        if (service != null) {
            mv.addObject("service", service);
        }
        //根据operationId查询operation
        Operation operation = getOperation(operationId, serviceId);
        if (operation != null) {
            mv.addObject("operation", operation);
            //查询serviceHead
            String serviceHeadHql = " from ServiceHead where headId = ?	";
            ServiceHead serviceHead = operationDAOImpl.findUnique(serviceHeadHql, operation.getHeadId());
            if (serviceHead != null) {
                mv.addObject("serviceHead", serviceHead);
            }
        }
        return mv;
    }

    public String saveVersion(String version) {
        if (StringUtils.isNotEmpty(version)) {
            String[] s = version.split("\\.");
            int s2 = Integer.parseInt(s[2]);
            s[2] = String.valueOf(s2 + 1);
            return s[0] + "." + s[1] + "." + s[2];
        }
        return initalVersion;
    }

    public String releaseVersion(String version) {
        if (StringUtils.isNotEmpty(version)) {
            String[] s = version.split("\\.");
            int s1 = Integer.parseInt(s[1]);
            s[1] = String.valueOf(s1 + 1);
            return s[0] + "." + s[1] + "." + s[2];
        }
        return initalVersion;
    }

    /**
     * 这个方法也极其混乱，注意一个方法做好一件事情
     * @param req
     * @param serviceId
     * @param operationId
     * @param consumerStr
     * @param providerStr
     * @return
     */
    public boolean addInvoke(HttpServletRequest req, String serviceId, String operationId, String consumerStr, String providerStr) {
        if (StringUtils.isNotEmpty(consumerStr)) {
            String[] constrs = consumerStr.split("\\,");
            for (String constr : constrs) {
                if (constr.contains("__invoke__")) { //判断是否是serviceInvokeID
                    constr = constr.replace("__invoke__", "");
                    ServiceInvoke si = operationDAOImpl.findUnique(" from ServiceInvoke where invokeId = ?", constr);
                    if (si != null) {
                        if (StringUtils.isEmpty(si.getServiceId()) && StringUtils.isEmpty(si.getOperationId())) {
                            si.setServiceId(serviceId);
                            si.setOperationId(operationId);
                            si.setType("1"); //1代表消费方
                            serviceInvokeService.save(si);
                        } else {
                            if (!serviceId.equals(si.getServiceId()) || !operationId.equals(si.getOperationId())) {
                                ServiceInvoke newsi = new ServiceInvoke();
                                newsi.setInvokeId(UUID.randomUUID().toString());
                                newsi.setSystemId(si.getSystemId());
                                newsi.setServiceId(serviceId);
                                newsi.setOperationId(operationId);
                                newsi.setType("1");
                                newsi.setInterfaceId(si.getInterfaceId());
                                serviceInvokeService.save(newsi);
                            }
                        }
                    }
                } else {//传入参数为systemId
                    ServiceInvoke si = new ServiceInvoke();
                    si.setInvokeId(UUID.randomUUID().toString());
                    si.setSystemId(constr);
                    si.setServiceId(serviceId);
                    si.setOperationId(operationId);
                    si.setType("1");
                    serviceInvokeService.save(si);
                }
            }
        }
        if (StringUtils.isNotEmpty(providerStr)) {
            String[] prostrs = providerStr.split("\\,");
            for (String prostr : prostrs) {
                if (prostr.contains("__invoke__")) { //判断是否是serviceInvokeID
                    prostr = prostr.replace("__invoke__", "");
                    ServiceInvoke si = operationDAOImpl.findUnique(" from ServiceInvoke where invokeId = ?", prostr);
                    if (si != null) {
                        if (StringUtils.isEmpty(si.getServiceId()) && StringUtils.isEmpty(si.getOperationId())) {
                            si.setServiceId(serviceId);
                            si.setOperationId(operationId);
                            si.setType("0"); //1代表消费方
                            serviceInvokeService.save(si);
                        } else {
                            if (!serviceId.equals(si.getServiceId()) || !operationId.equals(si.getOperationId())) {
                                ServiceInvoke newsi = new ServiceInvoke();
                                newsi.setInvokeId(UUID.randomUUID().toString());
                                newsi.setSystemId(si.getSystemId());
                                newsi.setServiceId(serviceId);
                                newsi.setOperationId(operationId);
                                newsi.setType("0");
                                newsi.setInterfaceId(si.getInterfaceId());
                                serviceInvokeService.save(newsi);
                            }
                        }
                    }
                } else {//传入参数为systemId
                    ServiceInvoke si = new ServiceInvoke();
                    si.setInvokeId(UUID.randomUUID().toString());
                    si.setSystemId(prostr);
                    si.setServiceId(serviceId);
                    si.setOperationId(operationId);
                    si.setType("0");
                    serviceInvokeService.save(si);
                }
            }
        }
        return true;
    }

    /**
     * 备份operation返回autoId
     *
     * @param serviceId
     * @param operationId
     */
    public OperationHis backUpOperation(String serviceId, String operationId, String versionDesc) {
        Operation operation = getOperation(serviceId, operationId);
        OperationHis operationHis = new OperationHis(operation);
        //修改operationHis 版本
        String versionHisId = versionServiceImpl.releaseVersion(operation.getVersionId(), operationHis.getAutoId(), versionDesc);
        operationHis.setVersionHisId(versionHisId);
        operationHisService.save(operationHis);
        return operationHis;
    }

    /**
     * add by liwang
     * review and modify by Vincent Fan
     */
    public boolean releaseBatch(Operation[] operations) {
        if (operations != null && operations.length > 0) {
            for (Operation operation : operations) {
                release(operation.getOperationId(), operation.getServiceId(), operation.getOperationDesc());
            }
        }
        return false;
    }



    /**
     * add by liwang
     * review and modify by Vincent Fan
     * 这个方法极其混乱。。。
     */
    public void release(String operationId, String serviceId, String versionDesc) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("serviceId", serviceId);
        params.put("operationId", operationId);
        Operation operation = getOperation(operationId, serviceId);
        if (operation != null) {
            //备份操作基本信息
            OperationHis operationHis = backUpOperation(serviceId, operationId, versionDesc);
            String operationHisAutoId = operationHis.getAutoId();
            //更新版本信息
            String versionHisId = versionServiceImpl.releaseVersion(operation.getVersionId(), operationHisAutoId, versionDesc);
            //更新 operationHis 中的versionId
            operationHis.setVersionHisId(versionHisId);
            operationHisService.save(operationHis);
            //备份SDA
            sdaService.backUpSdaByCondition(params, operationHisAutoId);
            //备份SLA
            slaService.backUpSLAByCondition(params, operationHisAutoId);
            //备份OLA
            olaService.backUpByCondition(params, operationHisAutoId);
            /**
             * 这段代码有什么用
             */
            operationDAOImpl.save(operation);
        }
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
}
