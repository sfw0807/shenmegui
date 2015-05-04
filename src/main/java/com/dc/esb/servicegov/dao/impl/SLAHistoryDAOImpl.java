package com.dc.esb.servicegov.dao.impl;


import com.dc.esb.servicegov.entity.SLA;
import com.dc.esb.servicegov.entity.SLAHistory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SLAHistoryDAOImpl extends HibernateDAO<SLAHistory, String> {
    private Log log = LogFactory.getLog(getClass());

    public boolean saveHistoryVersion(List<SLA> slaList) {
        boolean isSuccess = false;
        try {
            for (SLA sla : slaList) {
                SLAHistory slaHistory = new SLAHistory();
                slaHistory.setOperationId(sla.getOperationId());
                slaHistory.setOperationVersion(sla.getOperation().getVersion());
                slaHistory.setServiceId(sla.getServiceId());
                slaHistory.setServiceVersion(sla.getOperation().getVersion());
                slaHistory.setSlaName(sla.getSlaName());
                slaHistory.setSlaValue(sla.getSlaValue());
                slaHistory.setSlaRemark(sla.getSlaRemark());
                slaHistory.setModifyUser(sla.getModifyUser());
                slaHistory.setUpdateTime(sla.getUpdateTime());
                this.save(slaHistory);
            }
            isSuccess = true;
        } catch (Exception e) {
            log.error("error in saveHistoryVersion", e);
        }
        return isSuccess;
    }

    public boolean delByOperationIdAndServiceId(String serviceId,
                                                String operationId) {
        try {
            String hql = "delete from SLAHistory where serviceId = ? and operationId = ?";
            Query query = getSession().createQuery(hql);
            query.setString(0, serviceId);
            query.setString(1, operationId);
            query.executeUpdate();
        } catch (Exception e) {
            log.error("根据服务ID和操作Id删除SLAHistory数据失败", e);
            return false;
        }
        return true;
    }
}
