package com.dc.esb.servicegov.refactoring.service;

import java.util.List;

import com.dc.esb.servicegov.refactoring.entity.MetadataStructs;
import com.dc.esb.servicegov.refactoring.entity.MetadataStructsAttr;



public interface MetadataStructsManager {

	public List<MetadataStructs> getAll();
	public void delById(String structId);
	public void insertOrUpdate(MetadataStructs metadataStructs);
	public MetadataStructs getMetadataStructsById(String structId);
	public boolean isUsed(String structId);
	// 查看元数据结构包含的元数据基本信息
	public List<MetadataStructsAttr> getMdtStructsAttrByStructId(String structId);
	//
}
