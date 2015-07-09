package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

/**
 * Created by vincentfxz on 15/7/9.
 */
@Entity
@Table(name="PERMISSION")
public class Permission {
    @Id
    @Column(name="ID")
    private String Id;
    @Column(name="NAME")
    private String name;
    @Column(name="DESCRIPTION")
    private String description;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
