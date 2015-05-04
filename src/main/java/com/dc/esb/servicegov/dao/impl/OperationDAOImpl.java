package com.dc.esb.servicegov.dao.impl;


import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.OperationPK;
import com.dc.esb.servicegov.util.ServiceStateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OperationDAOImpl extends HibernateDAO<Operation, OperationPK> {
    private Log log = LogFactory.getLog(getClass());

    @SuppressWarnings("rawtypes")
    public List getPublishInfo(String operationId) {
        if (log.isInfoEnabled()) {
            log.info("get publishinfo by operationid");
        }
        StringBuffer sql = new StringBuffer("select t1.OPERATION_ID, t2.OPERATION_VERSION ,t2.ONLINE_DATE " +
                "from PUBLISH_INFO t2 left join INVOKE_RELATION t1 on t1.ID=t2.IR_ID where t1.OPERATION_ID='");
        sql.append(operationId);
        sql.append("' order by t2.ONLINE_DATE desc");
        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString());
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List returnlist = query.list();
            return returnlist;
        } catch (Exception e) {
            log.error("error in get publishinfo by operationid", e);
            return null;
        }
    }

    /**
     * 发布操作 状态由服务定义变为开发 版本号末位+1
     *
     * @param operationId
     * @param serviceId
     */
    public boolean deployOperation(String operationId, String serviceId) {
        if (log.isInfoEnabled()) {
            log.info("deploy Operation ...");
        }
        boolean isSuccess = false;
        try {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("operationId", operationId);
            paramMap.put("serviceId", serviceId);
            List<Operation> operationList = this.findBy(paramMap);
            Operation operation = operationList.get(0);
            operation.setState(ServiceStateUtils.DEVELOP);
            isSuccess = true;
        } catch (Exception e) {
            log.error("error in deploy operation[" + operationId + "]", e);
        }
        return isSuccess;
    }

    /**
     * 重定义操作 状态变为服务定义 版本号中间位+1, 保存历史版本
     *
     * @param operationId
     * @param serviceId
     */
    public boolean redefOperation(String operationId, String serviceId) {
        if (log.isInfoEnabled()) {
            log.info("redefine Operation ...");
        }
        boolean isSuccess = false;
        try {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("operationId", operationId);
            paramMap.put("serviceId", serviceId);
            List<Operation> operationList = this.findBy(paramMap);
            Operation operation = operationList.get(0);
            if (ServiceStateUtils.PUBLISH.equals(operation.getState())) {
                String version = operation.getVersion();
                String x1 = version.split("\\.")[0];
                String x2 = version.split("\\.")[1];
                String x3 = version.split("\\.")[2];
                x1 = (Integer.parseInt(x1) + 1) + "";
                x2 = "0";
                x3 = "0";
                operation.setVersion(x1 + "." + x2 + "." + x3);
                operation.setState(ServiceStateUtils.DEFINITION);
            } else if (ServiceStateUtils.DEFINITION.equals(operation.getState())) {
                log.error("操作[" + operationId + "]是服务定义状态,不能进行重定义操作");
            } else {
                String version = operation.getVersion();
                String x1 = version.split("\\.")[0];
                String x2 = version.split("\\.")[1];
                String x3 = version.split("\\.")[2];
                x2 = (Integer.parseInt(x2) + 1) + "";
                x3 = "0";
                operation.setVersion(x1 + "." + x2 + "." + x3);
                operation.setState(ServiceStateUtils.DEFINITION);
            }
            isSuccess = true;
        } catch (Exception e) {
            log.error("error in redef operation[" + operationId + "]", e);
        }
        return isSuccess;
    }

    /**
     * 上线操作 状态变为上线 末位+1
     *
     * @param operationId
     * @param serviceId
     */
    public boolean publishOperation(String operationId, String serviceId) {
        if (log.isInfoEnabled()) {
            log.info("publish Operation ...");
        }
        boolean isSuccess = false;
        try {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("operationId", operationId);
            paramMap.put("serviceId", serviceId);
            List<Operation> operationList = this.findBy(paramMap);
            Operation operation = operationList.get(0);
            String state = operation.getState();
            if (ServiceStateUtils.PREFORPUBLISH.equals(state)) {
                String version = operation.getVersion();
                String x1 = version.split("\\.")[0];
                String x2 = version.split("\\.")[1];
                String x3 = version.split("\\.")[2];
                x3 = (Integer.parseInt(x3) + 1) + "";
                operation.setVersion(x1 + "." + x2 + "." + x3);
                operation.setState(ServiceStateUtils.PUBLISH);
            } else {
                log.error("该操作未经过投产验证,不能上线");
            }
            isSuccess = true;
        } catch (Exception e) {
            log.error(e, e);
        }
        return isSuccess;
    }

    public Operation getOperation(String operationId, String serviceId) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("operationId", operationId);
        paramMap.put("serviceId", serviceId);
        List<Operation> operationList = this.findBy(paramMap);
        if (operationList == null || operationList.size() <= 0) {
            return null;
        }
        return operationList.get(0);
    }

    /**
     * new transaction to save operation
     *
     * @param operation
     */
    public void TxSaveOperation(Operation operation) {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.saveOrUpdate(operation);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            log.error("batch insert or update operation failed!", e);
        }
        session.close();
    }

    /**
     * 获取服务下的所有操作ID
     *
     * @param serviceId
     * @return
     */
    public List<String> getOperationIdsbyServiceId(String serviceId) {
        List<String> list = new ArrayList<String>();
        List<Operation> opeList = this.findBy("serviceId", serviceId);
        for (Operation operation : opeList) {
            list.add(operation.getOperationId());
        }
        return list;
    }

    public void delByServiceIdAndOperationId(String serviceId, String operationId) {
        String hql = "delete from Operation where serviceId =? and operationId =?";
        Query query = getSession().createQuery(hql);
        query.setString(0, serviceId);
        query.setString(1, operationId);
        query.executeUpdate();
    }
}
