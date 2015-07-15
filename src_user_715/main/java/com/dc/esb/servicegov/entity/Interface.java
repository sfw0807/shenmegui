package com.dc.esb.servicegov.entity;

import javax.persistence.*;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="INTERFACE")
public class Interface {
	@Id
	@Column(name = "INTERFACE_ID")
//	@GeneratedValue(generator="system-uuid")
//    @GenericGenerator(name="system-uuid",strategy="uuid")
	private String interfaceId;
	
	@Column(name = "INTERFACE_NAME")
	private String interfaceName;
	
	@Column(name = "ECODE")
	private String ecode;
	
	@Column(name = "DESCRIPTION")
	private String desc;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "VERSION")
	private String version;
	
	@Column(name = "OPT_USER")
	private String optUser;
	
	@Column(name = "OPT_DATE")
	private String optDate;

	private String headName;

	@OneToMany(mappedBy = "relateInters",cascade = CascadeType.ALL)
	private List<InterfaceHeadRelate> headRelates ;

	@OneToOne(mappedBy="inter",cascade = CascadeType.ALL)
	private ServiceInvoke serviceInvoke;

	@OneToMany(targetEntity = Ida.class,cascade = CascadeType.ALL)
	@JoinColumn(name = "INTERFACE_ID",referencedColumnName = "INTERFACE_ID",insertable = false,updatable = false)
	private List<Ida> idas;

	public List<Ida> getIdas() {
		return idas;
	}

	public void setIdas(List<Ida> idas) {
		this.idas = idas;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOptUser() {
		return optUser;
	}

	public void setOptUser(String optUser) {
		this.optUser = optUser;
	}

	public String getOptDate() {
		return optDate;
	}

	public void setOptDate(String optDate) {
		this.optDate = optDate;
	}

	public ServiceInvoke getServiceInvoke() {
		return serviceInvoke;
	}

	public void setServiceInvoke(ServiceInvoke serviceInvoke) {
		this.serviceInvoke = serviceInvoke;
	}

	public List<InterfaceHeadRelate> getHeadRelates() {
		return headRelates;
	}

	public void setHeadRelates(List<InterfaceHeadRelate> headRelates) {
		this.headRelates = headRelates;
	}

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}
}
