package com.dc.esb.servicegov.vo;

import com.dc.esb.servicegov.vo.ServiceDevProcessInfoBean;

public class ServiceDevProgressVO {

	private String systemName;
	
	private String systemAb;
	
	private int underDefine;
	
	private int dev;
	
	private int unitTest;

	private int sitTest;
	
	private int uatTest;
	
	private int productTest;
	
	private int totalNum;

	public ServiceDevProgressVO(ServiceDevProcessInfoBean bean) {
		this.systemName = bean.getSYS_NAME();
		this.systemAb = bean.getSYS_AB();
		this.underDefine = 0;
		this.dev = 0;
		this.unitTest = 0;
		this.sitTest = 0;
		this.uatTest = 0;
		this.productTest = 0;
		this.totalNum = 0;
	}
	
	public void updateAccount(ServiceDevProcessInfoBean bean) {
		
		if ("服务定义".equals(bean.getVERSIONST())) {
			this.underDefine += bean.getAMOUNT();
		} else if ("sit测试".equals(bean.getVERSIONST())) {
			this.sitTest += bean.getAMOUNT();
		} else if ("uat测试".equals(bean.getVERSIONST())) {
			this.uatTest += bean.getAMOUNT();
		} else if ("联调测试".equals(bean.getVERSIONST())) {
			this.unitTest += bean.getAMOUNT();
		} else if ("投产验证".equals(bean.getVERSIONST())) {
			this.productTest += bean.getAMOUNT();
		} else if ("开发".equals(bean.getVERSIONST())) {
			this.dev += bean.getAMOUNT();
		}
		
		this.totalNum = this.underDefine + this.dev +this.unitTest 
			+ this.sitTest + this.uatTest + this.productTest;
	}
	
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemAb() {
		return systemAb;
	}

	public void setSystemAb(String systemAb) {
		this.systemAb = systemAb;
	}

	public int getUnderDefine() {
		return underDefine;
	}

	public void setUnderDefine(int underDefine) {
		this.underDefine = underDefine;
	}

	public int getDev() {
		return dev;
	}

	public void setDev(int dev) {
		this.dev = dev;
	}

	public int getUnitTest() {
		return unitTest;
	}

	public void setUnitTest(int unitTest) {
		this.unitTest = unitTest;
	}

	public int getSitTest() {
		return sitTest;
	}

	public void setSitTest(int sitTest) {
		this.sitTest = sitTest;
	}

	public int getUatTest() {
		return uatTest;
	}

	public void setUatTest(int uatTest) {
		this.uatTest = uatTest;
	}

	public int getProductTest() {
		return productTest;
	}

	public void setProductTest(int productTest) {
		this.productTest = productTest;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	
}
