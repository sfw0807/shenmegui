package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.controller.RoleController;
import com.dc.esb.servicegov.refactoring.dao.impl.RoleDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.RoleFunctionRelateDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.Role;
import com.dc.esb.servicegov.refactoring.entity.RoleFunctionRelate;
import com.dc.esb.servicegov.refactoring.service.RoleManager;

@Service
@Transactional
public class RoleManagerImpl implements RoleManager{
	private Log log = LogFactory.getLog(RoleController.class);
	@Autowired
	private RoleDAOImpl roleDAO;
	@Autowired
	private RoleFunctionRelateDAOImpl roleFunctionRelateDAO;
	public List<Role> getAll() {
		return roleDAO.getAll();
	}
	public Role getRoleById(String id) {
		return roleDAO.findUniqueBy("id",  Integer.parseInt(id));
	}
	public boolean saveRole(Role role) {
		boolean flag = false;
		try{
			roleDAO.save(role);
			flag = true;
		}catch(Exception e){
			log.error(e, e);
		}
		return flag;
	}
	public Integer getMaxId() {
		return roleDAO.getMaxId();
	}
	public boolean deleteRole(Role role) {
		boolean flag = false;
		try{
			roleDAO.delete(role);
			flag = true;
		}catch(Exception e){
			log.error(e, e);
		}
		return flag;
	}
	public boolean deleteRoleFunctionRelate(String roleId) {
		boolean flag = false;
		try{
			List<RoleFunctionRelate> relateList = roleFunctionRelateDAO.findBy("roleId",  Integer.parseInt(roleId));
			for(RoleFunctionRelate relat:relateList){
				roleFunctionRelateDAO.delete(relat);
			}
			flag = true;
		}catch(Exception e){
			log.error(e, e);
		}
		return flag;
	}
	public boolean saveRoleFunctionRelate(RoleFunctionRelate relat) {
		boolean flag = false;
		try{
			roleFunctionRelateDAO.save(relat);
			flag = true;
		}catch(Exception e){
			log.error(e, e);
		}
		return flag;
	}

}
