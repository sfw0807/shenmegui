package com.dc.esb.servicegov.refactoring.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.refactoring.entity.User;
import com.dc.esb.servicegov.refactoring.service.OrgManager;
import com.dc.esb.servicegov.refactoring.service.RoleManager;
import com.dc.esb.servicegov.refactoring.service.UserManager;
import com.dc.esb.servicegov.refactoring.vo.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	private Log log = LogFactory.getLog(UserController.class);
	@Autowired
	private UserManager userManager;
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private OrgManager orgManager;
	@RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
	public @ResponseBody List<UserVO> getAll() {
		List<User> userList = userManager.getAll();
		List<UserVO> userVOList = new ArrayList<UserVO>();
		for (User user:userList) {
			UserVO userVo = new UserVO();
			userVo.setId(user.getId()+"");
			userVo.setName(user.getName());
			userVo.setPassword(user.getPassword());
			userVo.setRole(user.getRole().getName());
			userVo.setOrg(user.getOrg().getOrgAB());
			userVo.setRemark(user.getRemark());
			userVo.setLastdate(user.getLastdate());
			userVo.setStatus(user.getStatus());
			userVOList.add(userVo);
		}
		return userVOList;
		
	}
	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody boolean addUser(@RequestBody String[] param) {
		String username = param[0];
		String initpwd = param[1];
		String role = param[2];
		String org = param[3];
		String status = param[4];
		String remark = param[5];
		User user = new User();
		user.setId(userManager.getMaxId()+1);
		user.setName(username);
		user.setRoleId(Integer.parseInt(role));
		user.setOrgId(org);
		user.setPassword(initpwd);
		user.setStatus(status);
		user.setRemark(remark);
		user.setLastdate("");
		return userManager.save(user);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/delete", headers = "Accept=application/json")
	public @ResponseBody boolean deleteUser(@RequestBody String[] param) {
		return userManager.delete(userManager.getUserById(param[0]));
	}
	@RequestMapping(method = RequestMethod.GET, value = "/getRoleIdByUserId/{userId}", headers = "Accept=application/json")
	public @ResponseBody String deleteUser(@PathVariable String userId) {
		return userManager.getRoleIdByUserId(userId)+"";
	}
	@RequestMapping(method = RequestMethod.POST, value = "/assignRole", headers = "Accept=application/json")
	public @ResponseBody boolean assignRole(@RequestBody String[] param) {
		String userId = param[0];
		String roleId = param[1];
		User user = userManager.getUserById(userId);
		user.setRoleId(Integer.parseInt(roleId));
		return userManager.save(user);
	}
}
