package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lenovo on 2015/7/13.
 */
@Entity
@Table(name="ROLE_PERMISSION_RELATION")
public class RolePermissionRelation implements Serializable {
    @Id
    @Column(name = "ROLE_ID")
    private String roleId;

    @Id
    @Column(name="PERMISSION_ID")
    private String permissionId;

    @ManyToOne
    @JoinColumn(name="PERMISSION_ID",referencedColumnName="ID",insertable = false,updatable = false)
    private Permission permission;

    @ManyToOne
    @JoinColumn(name="ROLE_ID",referencedColumnName="ROLE_ID",insertable = false,updatable = false)
    private Role role;

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}
