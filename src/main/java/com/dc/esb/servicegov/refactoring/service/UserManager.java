package com.dc.esb.servicegov.refactoring.service;

import java.util.List;

import com.dc.esb.servicegov.refactoring.entity.User;

public interface UserManager {
	
	public List<User> getAll();

	public Integer getMaxId();

	public User getUserById(String userId);

	public boolean save(User user);

	public boolean delete(User user);

	public Integer getRoleIdByUserId(String userId);
	
	public User login(String[] params);
}
