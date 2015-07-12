package com.dc.esb.servicegov.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.VersionDAOImpl;
import com.dc.esb.servicegov.dao.impl.VersionHisDAOImpl;
import com.dc.esb.servicegov.entity.Version;
import com.dc.esb.servicegov.entity.VersionHis;
import com.dc.esb.servicegov.service.VersionService;
import com.dc.esb.servicegov.util.DateUtils;
@Service
@Transactional
public class VersionServiceImpl extends BaseServiceImpl<Version> implements	VersionService {
	@Autowired
	VersionDAOImpl daoImpl;
	@Autowired
	VersionHisDAOImpl hisDaoImpl;
	private final String initalVersion = "1.0.0";

	public String addVersion(String targetType, String targetId) {
		Version nv = new Version();
		nv.setId(UUID.randomUUID().toString());
		nv.setCode(initalVersion);
		nv.setType("0");
		nv.setState("0");
		nv.setOptType("0");
		nv.setOptDate(DateUtils.format(new Date()));
		//nv.setOptUser(optUser);
		nv.setTargetId(targetId);
		nv.setTargetType(targetType);
		daoImpl.save(nv);

		return nv.getId();
	}

	public boolean editVersion(String id) {
		Version entity = daoImpl.findUniqueBy("id", id);
		entity.setCode(editVersionCode(entity.getCode()));
		entity.setOptDate(DateUtils.format(new Date()));
		entity.setOptType("1");
//		entity.setOptUser(entity.getOptUser());
		daoImpl.save(entity);
		return true;
	}

	public String editVersionCode(String versionCode) {
		if (StringUtils.isNotEmpty(versionCode)) {
			String[] s = versionCode.split("\\.");
			int s2 = Integer.parseInt(s[2]);
			s[2] = String.valueOf(s2 + 1);
			return s[0] + "." + s[1] + "." + s[2];
		}
		return initalVersion;
	}
	public String deleteVersion(String versionId, String targetId){
		Version ov = daoImpl.findUniqueBy("id", versionId);
		
		VersionHis nv = new VersionHis(ov, targetId);
		nv.setOptType("2");
		hisDaoImpl.save(nv);
		
		return nv.getAutoId();
	}
	
	//发布时产生一个历史版本
	public String releaseVersion(String versionId, String targetId, String versionDesc) {
		Version ov = daoImpl.findUniqueBy("id", versionId);
		ov.setVersionDesc(versionDesc);
		
		VersionHis nv = new VersionHis(ov, targetId);
		hisDaoImpl.save(nv);
		
		ov.setCode(releaseVersionCode(ov.getCode()));
		ov.setOptType("3");
		daoImpl.save(ov);
		
		return nv.getAutoId();
	}

	// 发布时调用方法，每次中间一位+1
	public String releaseVersionCode(String versionCode) {
		if (StringUtils.isNotEmpty(versionCode)) {
			String[] s = versionCode.split("\\.");
			int s1 = Integer.parseInt(s[1]);
			s[1] = String.valueOf(s1 + 1);
			return s[0] + "." + s[1] + "." + s[2];
		}
		return initalVersion;
	}
	
	public List<VersionHis> hisVersionList(String keyValue){
		String hql = " from VersionHis";
		if(StringUtils.isNotEmpty(keyValue)){
			hql += " where code like '%"+keyValue+"%' or versionDesc like '%"+keyValue+"%' or remark like '%"+keyValue+"%'";
		}
		return hisDaoImpl.find(hql);
	}
}
