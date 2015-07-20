package com.dc.esb.servicegov.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.OperationHis;
import com.dc.esb.servicegov.entity.OperationPK;
import com.dc.esb.servicegov.entity.ServiceHead;
import com.dc.esb.servicegov.entity.ServiceInvoke;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import com.dc.esb.servicegov.service.support.Constants;
import com.dc.esb.servicegov.util.DateUtils;

@Service
@Transactional
public class OperationServiceImpl extends AbstractBaseService<Operation, OperationPK>{

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
        return operationDAOImpl.findBy("serviceId", serviceId);
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
        List<Operation> list = findBy(params);
        if(list != null && list.size() > 0){
        	return list.get(0);
        }
        return null;

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

    public boolean addOperation(Operation entity) {
        try {
            String versionId = versionServiceImpl.addVersion(Constants.Version.TARGET_TYPE_OPERATION, entity.getOperationId(),Constants.Version.TYPE_ELSE);
            entity.setVersionId(versionId);
            entity.setDeleted(Constants.DELTED_FALSE);
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
            operationDAOImpl.save(entity);
            //清空
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void deleteOperations(OperationPK[] operationPks) {
        if (operationPks != null && operationPks.length > 0) {
            for (OperationPK operationPK : operationPks) {
                operationDAOImpl.delete(operationPK);
            }
        }
    }

    public ModelAndView detailPage(HttpServletRequest req, String operationId, String serviceId) {

        ModelAndView mv = new ModelAndView("service/operation/detailPage");
        //根据serviceId查询service信息
        com.dc.esb.servicegov.entity.Service service = serviceService.findUniqueBy("serviceId", serviceId);
        if (service != null) {
            mv.addObject("service", service);
        }
        //根据operationId查询operation
        Operation operation = getOperation(serviceId, operationId);
        if (operation != null) {
            mv.addObject("operation", operation);
        }
        return mv;
    }

    /**
     * @param req
     * @param serviceId
     * @param operationId
     * @param consumerStr
     * @param providerStr
     * @return
     */
    public boolean addInvoke(HttpServletRequest req, String serviceId, String operationId, String consumerStr, String providerStr) {
        //清空原有关系
        serviceInvokeService.updateBySO(serviceId, operationId);
        if (!StringUtils.isEmpty(consumerStr)) {
            String[] constrs = consumerStr.split("\\,");
            for (String constr : constrs) {
                if (constr.contains("__invoke__")) { //判断是否是serviceInvokeID
                	constr = constr.replace("__invoke__", "");
                	serviceInvokeService.updateAfterOPAdd(constr,serviceId,operationId,Constants.INVOKE_TYPE_CONSUMER);
                } else {//传入参数为systemId
                    ServiceInvoke si = new ServiceInvoke();
                    si.setSystemId(constr);
                    si.setServiceId(serviceId);
                    si.setOperationId(operationId);
                    si.setType(Constants.INVOKE_TYPE_CONSUMER);
                    serviceInvokeService.save(si);
                }
            }
        }
        if (!StringUtils.isEmpty(providerStr)) {
            String[] prostrs = providerStr.split("\\,");
            for (String prostr : prostrs) {
            	if (prostr.contains("__invoke__")) { //判断是否是serviceInvokeID
            		prostr = prostr.replace("__invoke__", "");
                	serviceInvokeService.updateAfterOPAdd(prostr,serviceId,operationId,Constants.INVOKE_TYPE_PROVIDER);
                }  else {//传入参数为systemId
                    ServiceInvoke si = new ServiceInvoke();
                    si.setInvokeId(UUID.randomUUID().toString());
                    si.setSystemId(prostr);
                    si.setServiceId(serviceId);
                    si.setOperationId(operationId);
                    si.setType(Constants.INVOKE_TYPE_PROVIDER);
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
     */
    public void release(String operationId, String serviceId, String versionDesc) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("serviceId", serviceId);
        params.put("operationId", operationId);
        Operation operation = getOperation(serviceId, operationId);
        if (operation != null) {
            //备份操作基本信息
            OperationHis operationHis = backUpOperation(serviceId, operationId, versionDesc);
            String operationHisAutoId = operationHis.getAutoId();
//            //更新版本信息
//            String versionHisId = versionServiceImpl.releaseVersion(operation.getVersionId(), operationHisAutoId, versionDesc);
//            //更新 operationHis 中的versionId
//            operationHis.setVersionHisId(versionHisId);
//            operationHisService.save(operationHis);
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
        return operationDAOImpl.auditOperation(state, operationIds);
    }
    
    public List<Operation> getReleased(){
    	return operationDAOImpl.getReleased();
    }
}
