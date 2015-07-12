package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.*;
import com.dc.esb.servicegov.service.OperationService;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import com.dc.esb.servicegov.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
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
            entity.setOptDate(DateUtils.format(new Date()));
            entity.setVersion(saveVersion(entity.getVersion()));
            operationDAOImpl.save(entity);

            ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
            SDAServiceImpl sdaServiceImpl = context.getBean(SDAServiceImpl.class);
            sdaServiceImpl.genderSDAAuto(entity);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean editOperation(HttpServletRequest req, Operation entity) {
        try {
            entity.setOptDate(DateUtils.format(new Date()));
            entity.setVersion(saveVersion(entity.getVersion()));
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

    public ModelAndView release(HttpServletRequest req, String operationId, String serviceId) {

        Operation operation = getOperation(operationId, serviceId);

        if (operation != null) {
            OperationHis operationHis = new OperationHis(operation);
            operationHisService.save(operationHis);

            List<SDA> sdaList = sdaService.getSDAListBySO(serviceId, operationId);
            if (sdaList != null && sdaList.size() > 0) {
                for (SDA sda : sdaList) {
                    SDAHis sdaHis = new SDAHis(sda, operationHis.getAutoId());
                    sdaHisService.save(sdaHis);
                }
            }
            Map<String, String> params = new HashMap<String, String>();
            params.put("operationId", operationId);
            params.put("serviceId", serviceId);
            List<SLA> slaList = slaService.findBy(params);
            if (slaList != null && slaList.size() > 0) {
                for (SLA sla : slaList) {
                    SLAHis slaHis = new SLAHis(sla, operationHis.getAutoId());
                    slaHisService.save(slaHis);
                }
            }

            List<OLA> olaList = olaService.findBy(params);
            if (olaList != null && olaList.size() > 0) {
                for (OLA ola : olaList) {
                    OLAHis olaHis = new OLAHis(ola, operationHis.getAutoId());
                    olaHisService.save(olaHis);
                }
            }

        }
        //修改operation 版本
        operation.setVersion(releaseVersion(operation.getVersion()));
        operationDAOImpl.save(operation);

        return detailPage(req, operationId, serviceId);
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
     * add by liwang
     */
    public boolean releaseBatch(HttpServletRequest req, @RequestBody Operation[] operations) {
        if (operations != null && operations.length > 0) {
            for (Operation operation : operations) {
                release(req, operation.getOperationId(), operation.getServiceId(), operation.getOperationDesc());
            }
        }
        return false;
    }

    /**
     * 备份operation返回autoId
     *
     * @param serviceId
     * @param operationId
     */
    public String backUpOperation(String serviceId, String operationId) {
        Operation operation = getOperation(serviceId, operationId);
        OperationHis operationHis = new OperationHis(operation);
        operationHisService.save(operationHis);
        return operationHis.getAutoId();
    }

    /**
     * add by liwang modify by vincent Fan
     */
    public ModelAndView release(HttpServletRequest req, String operationId, String serviceId, String versionDesc) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("serviceId", serviceId);
        params.put("operationId", operationId);
        Operation operation = getOperation(operationId, serviceId);
        if (operation != null) {
            //备份操作基本信息
            String autoId = backUpOperation(serviceId, operationId);
            //备份SDA
            sdaService.backUpSdaByCondition(params, autoId);
            //备份SLA
            slaService.backUpSLAByCondition(params, autoId);
            //备份OLA
            olaService.backUpByCondition(params, autoId);
            /**
             * 这段代码到底是在做什么
             */
            //修改operationHis 版本
            String versionHisId = versionServiceImpl.releaseVersion(operation.getVersionId(), operationHis.getAutoId(), versionDesc);
            operationHis.setVersionHisId(versionHisId);
            ohs.editEntity(operationHis);

            operationDAOImpl.save(operation);
        }
        return detailPage(req, operationId, serviceId);
    }

}
