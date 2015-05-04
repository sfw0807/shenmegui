package com.dc.esb.servicegov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AUDITSTATE")
public class AuditState implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 831472909992238539L;

    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "STATE")
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
