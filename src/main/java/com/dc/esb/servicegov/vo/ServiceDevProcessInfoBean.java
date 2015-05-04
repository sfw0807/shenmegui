package com.dc.esb.servicegov.vo;


/**
 * 服务开发系统信息VO
 * 自定义接收hibernate sqlQuery list
 * 字段名字和数据库查出结果对应 不可更改
 * @author G
 *
 */
public class ServiceDevProcessInfoBean {

	private String PROVIDE_SYS_ID;
	
	private String SYS_AB;
	
	private String SYS_NAME;
	
	private String VERSIONST;
	
	private int AMOUNT;

	public int getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(int amount) {
		AMOUNT = amount;
	}

	public String getPROVIDE_SYS_ID() {
		return PROVIDE_SYS_ID;
	}

	public void setPROVIDE_SYS_ID(String provide_sys_id) {
		PROVIDE_SYS_ID = provide_sys_id;
	}

	public String getSYS_AB() {
		return SYS_AB;
	}

	public void setSYS_AB(String sys_ab) {
		SYS_AB = sys_ab;
	}

	public String getSYS_NAME() {
		return SYS_NAME;
	}

	public void setSYS_NAME(String sys_name) {
		SYS_NAME = sys_name;
	}

	public String getVERSIONST() {
		return VERSIONST;
	}

	public void setVERSIONST(String versionst) {
		VERSIONST = versionst;
	}

}
