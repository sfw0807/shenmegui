package com.dc.esb.servicegov.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SG_USER")
public class SGUser implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 22222222222L;
	@Id
    @Column(name = "USER_ID")
    private String id;
    @Column(name = "USER_NAME")
    private String name;
    @Column(name="USER_MOBILE")
    private String userMobile;
    @Column(name="USER_TEL")
    private String userTel;
    @Column(name = "USER_PASSWORD")
    private String password;
    @Column(name = "USER_ORG_ID")
    private String orgId;
    @Column(name = "USER_LASTDATE")
    private String lastdate;
    @Column(name = "USER_REMARK")
    private String remark;
    @Column(name = "USER_STARTDATE")
    private String startdate;

    @OneToMany(mappedBy="user",cascade=CascadeType.ALL)
    private List<UserRoleRelation> userRoleRelations;
    

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public List<UserRoleRelation> getUserRoleRelations() {
		return userRoleRelations;
	}

	public void setUserRoleRelations(List<UserRoleRelation> userRoleRelations) {
		this.userRoleRelations = userRoleRelations;
	}

	
    
    
}