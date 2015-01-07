package com.dc.esb.servicegov.refactoring.service;

import java.util.List;

import com.dc.esb.servicegov.refactoring.entity.MetadataStructsAttr;



public interface MetadataStructsAttrManager {

	public List<MetadataStructsAttr> getAll();
	public void delById(String structId,String metadataId);
	public void insertOrUpdate(MetadataStructsAttr metadataStructsAttr);
	public MetadataStructsAttr getMdtStructsAttrById(String structId,String metadataId);
	// 根据structId删除
	public void delByStructId(String structId);
	// 根据structId查找属性列表
	public List<MetadataStructsAttr> getMdtStructsAttrByStructId(String structId);
	// 批量删除List
	@SuppressWarnings("unchecked")
	public boolean batchDelete(MetadataStructsAttr[] msaArr);
	// 批量新增或修改List
	@SuppressWarnings("unchecked")
	public boolean batchInsertOrUpdate(MetadataStructsAttr[] msaArr);
}
