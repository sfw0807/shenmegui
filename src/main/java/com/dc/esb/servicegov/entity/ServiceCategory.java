package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-29
 * Time: 下午5:51
 */
@Entity
@Table(name = "SG_COM_GROUP")
public class ServiceCategory {
    @Id
    @Column(name = "GROUPID")
    private String groupId;
    @Column(name = "TYPEID")
    private String typeId;
    @Column(name = "GROUPNAME")
    private String groupName;
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "PARENTID")
    private String parentId;
    @Column(name = "RANK")
    private int rank;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRemark() {
        return remark;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


}
