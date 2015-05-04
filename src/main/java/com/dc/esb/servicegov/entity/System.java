package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SG_SYSTEM")
public class System {

    @Id
    @Column(name = "SYS_ID")
    private String systemId;
    @Column(name = "SYS_AB")
    private String systemAb;
    @Column(name = "SYS_NAME")
    private String systemName;
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "MODIFYUSER")
    private String modifyUser;
    @Column(name = "UPDATETIME")
    private String updateTime;
    @Column(name = "FIRST_PUBLISH_DATE")
    private String firstPublishDate;
    @Column(name = "AVGRESTIME")
    private String avgResTime;
    @Column(name = "MAXRESTIME")
    private String maxResTime;
    @Column(name = "MAXCONNUM")
    private Integer maxConNum;
    @Column(name = "TIMEOUT")
    private String timeOut;
    @Column(name = "SUCCESSRATE")
    private String successRate;
    @Column(name = "OUTMAXCONNUM")
    private Integer outMaxConNum;
    @Column(name = "SECOND_PUBLISH_DATE")
    private String secondPublishDate;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemAb() {
        return systemAb;
    }

    public void setSystemAb(String systemAb) {
        this.systemAb = systemAb;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFirstPublishDate() {
        return firstPublishDate;
    }

    public void setFirstPublishDate(String firstPublishDate) {
        this.firstPublishDate = firstPublishDate;
    }

    public String getAvgResTime() {
        return avgResTime;
    }

    public void setAvgResTime(String avgResTime) {
        this.avgResTime = avgResTime;
    }

    public String getMaxResTime() {
        return maxResTime;
    }

    public void setMaxResTime(String maxResTime) {
        this.maxResTime = maxResTime;
    }

    public Integer getMaxConNum() {
        return maxConNum;
    }

    public void setMaxConNum(Integer maxConNum) {
        this.maxConNum = maxConNum;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }

    public Integer getOutMaxConNum() {
        return outMaxConNum;
    }

    public void setOutMaxConNum(Integer outMaxConNum) {
        this.outMaxConNum = outMaxConNum;
    }

    public String getSecondPublishDate() {
        return secondPublishDate;
    }

    public void setSecondPublishDate(String secondPublishDate) {
        this.secondPublishDate = secondPublishDate;
    }
}
