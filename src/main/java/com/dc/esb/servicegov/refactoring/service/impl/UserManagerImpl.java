package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.UserDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.User;
import com.dc.esb.servicegov.refactoring.service.UserManager;

@Service
@Transactional
public class UserManagerImpl implements UserManager {
	
	private Log log = LogFactory.getLog(UserManagerImpl.class);
	
	@Autowired
	private UserDAOImpl userDAO;

	public List<User> getAll() {
		return userDAO.getAll();
	}

	public Integer getMaxId() {
		return userDAO.getMaxId();
	}

	public boolean save(User user) {
		boolean flag = false;
		try {
			userDAO.save(user);
			flag = true;
		} catch (Exception e) {
			log.error(e, e);
		}
		return flag;
	}

	public boolean delete(User user) {
		boolean flag = false;
		try {
			userDAO.delete(user);
			flag = true;
		} catch (Exception e) {
			log.error(e, e);
		}
		return flag;
	}

	public User getUserById(String userId) {
		return userDAO.findUniqueBy("id", Integer.parseInt(userId));
	}

	public Integer getRoleIdByUserId(String userId) {
		return userDAO.findUniqueBy("id", Integer.parseInt(userId)).getRoleId();
	}

	@Override
	public User login(String[] params) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", params[0]);
		map.put("password", params[1]);
		List<User> lst = userDAO.findBy(map);
		if (lst.size() > 0 ) {
			return lst.get(0);
		} 
		return null;
	}
}
