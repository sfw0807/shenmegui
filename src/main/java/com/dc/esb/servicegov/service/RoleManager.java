package com.dc.esb.servicegov.service;

import java.util.List;

import com.dc.esb.servicegov.entity.Role;
import com.dc.esb.servicegov.entity.RoleFunctionRelate;

public interface RoleManager {

	public List<Role> getAll();
	public Role getRoleById(String id);
	public boolean saveRole(Role role);
	public Integer getMaxId();
	public boolean deleteRole(Role role);
	public boolean deleteRoleFunctionRelate(String roleId);
	public boolean saveRoleFunctionRelate(RoleFunctionRelate relat);
}
