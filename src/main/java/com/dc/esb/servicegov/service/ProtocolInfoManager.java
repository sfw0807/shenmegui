package com.dc.esb.servicegov.service;

import java.util.List;

import com.dc.esb.servicegov.entity.ProtocolInfo;


public interface ProtocolInfoManager {
	
	public List<ProtocolInfo> getProtocolInfosBySysId(String sysId);
	public boolean batchInsertOrUpdate(ProtocolInfo[] pInfoArr);
	public void delBySysId(String sysId);
	public void batchByIdArr(String[] idArr);
}
