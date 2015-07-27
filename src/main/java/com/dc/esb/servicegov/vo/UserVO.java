package com.dc.esb.servicegov.vo;


import com.dc.esb.servicegov.entity.SGUser;

/**
 * Created by vincentfxz on 15/7/27.
 */
public class UserVO {

    private String id;
    private String name;
    private String userMobile;
    private String userTel;
    private String password;
    private String orgId;
    private String lastdate;
    private String remark;
    private String startdate;

    public UserVO(String id, String name, String userMobile, String userTel, String password, String orgId, String lastdate, String remark, String startdate){
        this.id = id;
        this.name = name;
        this.userMobile = userMobile;
        this.userTel = userTel;
        this.password = password;
        this.orgId = orgId;
        this.lastdate = lastdate;
        this.remark = remark;
        this.startdate = startdate;
    }

    public UserVO(SGUser user){
        this.id = user.getId();
        this.name = user.getName();
        this.userMobile = user.getUserMobile();
        this.userTel = user.getUserTel();
        this.password = user.getPassword();
        this.orgId = user.getOrgId();
        this.lastdate = user.getLastdate();
        this.remark = user.getRemark();
        this.startdate = user.getStartdate();
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
}
