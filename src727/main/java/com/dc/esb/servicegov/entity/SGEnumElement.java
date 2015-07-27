package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by vincentfxz on 15/6/24.
 */
@Entity
@Table(name="ENUM_ELEMENT")
public class SGEnumElement {
    @Id
    @Column(name="ELEMENT_ID")
    private String id;
    @Column(name="ELEMENT_NAME")
    private String name;
    @Column(name="BUSS_DEFINE")
    private String bussDefine;
    @Column(name="ENUM_ID")
    private String enumId;
    @Column(name = "OPT_USER")
    private String optUser;
    @Column(name="OPT_DATE")
    private String optDate;
}
