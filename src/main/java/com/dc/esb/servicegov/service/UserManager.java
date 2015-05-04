package com.dc.esb.servicegov.service;

import java.util.List;

import com.dc.esb.servicegov.entity.User;

public interface UserManager {
	
	public List<User> getAll();

	public Integer getMaxId();

	public User getUserById(String userId);

	public boolean save(User user);

	public boolean delete(User user);

	public Integer getRoleIdByUserId(String userId);
	
	public User login(String[] params);
	
	public boolean checkOldPwd(String userId, String password);
	
	public boolean updatePwd(String userId, String password);
	
	public boolean resetPwd(String userId);
}
