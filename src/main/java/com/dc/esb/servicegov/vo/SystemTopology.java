package com.dc.esb.servicegov.vo;

import com.dc.esb.servicegov.vo.SystemLine;
import com.dc.esb.servicegov.vo.SystemNode;

import java.util.List;

public class SystemTopology {
	private List<SystemNode> systemnodeList;
	private List<SystemLine> systemlineList;
	public List<SystemNode> getSystemnodeList() {
		return systemnodeList;
	}
	public void setSystemnodeList(List<SystemNode> systemnodeList) {
		this.systemnodeList = systemnodeList;
	}
	public List<SystemLine> getSystemlineList() {
		return systemlineList;
	}
	public void setSystemlineList(List<SystemLine> systemlineList) {
		this.systemlineList = systemlineList;
	}
	
}
