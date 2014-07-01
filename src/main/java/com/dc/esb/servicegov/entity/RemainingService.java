package com.dc.esb.servicegov.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-30
 * Time: 下午4:59
 */
@Entity
@Table(name="REMAINING_SERVICE")
@IdClass(RemainingServicePK.class)
public class RemainingService {
    @Id
    private String serviceId;
    @Id
    private String operationId;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }


}
