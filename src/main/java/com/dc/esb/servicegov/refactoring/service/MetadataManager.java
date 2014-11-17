package com.dc.esb.servicegov.refactoring.service;

import java.util.List;

import com.dc.esb.servicegov.refactoring.entity.Metadata;


public interface MetadataManager {
	
	public List<Metadata> getAllMetadata();
	public void delById(String id);
	public void delByEntity(Metadata metadata);
	public void update(String id);
	public void updateEntity(Metadata metadata);
	public void insert(Metadata metadata);
	public Metadata getMetadataById(String id);
	
}
