package com.dc.esb.servicegov.service;

import java.util.List;

import com.dc.esb.servicegov.entity.Organization;

public interface OrgManager {

	public List<Organization> getAll();
	public Organization getOrgById(String id);
	public boolean save(Organization org);
	public boolean delete(Organization org);
}
