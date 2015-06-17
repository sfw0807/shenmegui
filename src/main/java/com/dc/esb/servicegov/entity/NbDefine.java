package com.dc.esb.servicegov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Administrator on 2015/5/6.
 */
@Entity
@Table(name = "NB_DEFINE")
public class NbDefine implements Serializable {
    @Column(name = "NB_BYTE")
    private String nbByte;
    @Id
    @Column(name = "PARAMETER")
    private String parameter;
    @Column(name = "PARAMETER_NAME")
    private String parameterName;
    @Column(name = "NB_LENGTH")
    private String nbLength;
    @Column(name = "NB_DECIMAL")
    private String nbDecimal;
    @Column(name = "CHANGE_LENGTH")
    private String changeLength;
    @Column(name = "PARAMETER_ANOTHER_NAME")
    private String parameterAnotherName;


    public String getNbByte()
    {
        return nbByte;
    }
    public void setNbByte(String nbByte) {
        this.nbByte = nbByte;
    }

    public String getParameter()
    {
        return parameter;
    }
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameterName()
    {
        return parameterName;
    }
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getNbLength()
    {
        return nbLength;
    }
    public void setNbLength(String nbLength) {
        this.nbLength = nbLength;
    }

    public String getNbDecimal()
    {
        return nbDecimal;
    }
    public void setNbDecimal(String nbDecimal) {
        this.nbDecimal = nbDecimal;
    }

    public String getChangeLength()
    {
        return changeLength;
    }
    public void setChangeLength(String changeLength) {
        this.changeLength = changeLength;
    }

    public String getParameterAnotherName()
    {
        return parameterAnotherName;
    }
    public void setParameterAnotherName(String parameterAnotherName) {
        this.parameterAnotherName = parameterAnotherName;
    }
}
