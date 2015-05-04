package com.dc.esb.servicegov.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.entity.Function;
import com.dc.esb.servicegov.entity.User;
import com.dc.esb.servicegov.service.LogManager;
import com.dc.esb.servicegov.service.OrgManager;
import com.dc.esb.servicegov.service.RoleFunctionRelateManager;
import com.dc.esb.servicegov.service.RoleManager;
import com.dc.esb.servicegov.service.UserManager;
import com.dc.esb.servicegov.util.LoginUtil;
import com.dc.esb.servicegov.vo.UserVO;

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
    @Autowired
    private LogManager logManager;
    @Autowired
    private RoleFunctionRelateManager roleFunctionRelatManager;

    @RequestMapping(method = RequestMethod.POST, value = "/login", headers = "Accept=application/json")
    public
    @ResponseBody
    ModelAndView login(HttpServletRequest request, HttpServletResponse response, @RequestParam("userName")
    String userName, @RequestParam("password")
                       String password) throws Exception {
        userName = new String(userName.getBytes("iso-8859-1"), "utf-8");
        password = new String(password.getBytes("iso-8859-1"), "utf-8");
        log.info("userName: " + userName + "; password: " + password);
        User user = userManager.login(new String[]{userName, password});
        //UserOperationLogUtil.saveLog("用户登录");
        if (user != null) {
            // 保存cookie
//			if(savetime != null) { //保存用户在Cookie
//				int savetime_value = savetime != null ? Integer.valueOf(savetime) : 0;
//				int time = 0;
//				if(savetime_value == 1) { //记住一天
//					time = 60 * 60 * 24;
//				} else if(savetime_value == 2) { //记住一月
//					time = 60 * 60 * 24 * 30;
//				} else if(savetime_value == 2) { //记住一年
//					time = 60 * 60 * 24 * 365;
//				}
//				Cookie cid = new Cookie(LoginUtil.USER, userName);
//				cid.setMaxAge(time);
//				Cookie cpwd = new Cookie(LoginUtil.PASSWORD, password);
//				cpwd.setMaxAge(time);
//				response.addCookie(cid);
//				response.addCookie(cpwd);
//			}
            LoginUtil.setLoginSuccessOfSession(request, user);
            int roleId = user.getRoleId();
            List<Function> functionList = roleFunctionRelatManager.getFunctionByRole(roleId + "");
            LoginUtil.setFunctionListOfSession(request, functionList);
            return new ModelAndView("sgHome");
        } else {
            LoginUtil.setLoginFailedOfSession(request, user);
            return new ModelAndView("login");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logOut", headers = "Accept=application/json")
    public
    @ResponseBody
    ModelAndView logOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getSession().getAttribute(LoginUtil.USER) != null) {
            request.getSession().removeAttribute(LoginUtil.USER);
        }
        return new ModelAndView("sgHome");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
    public
    @ResponseBody
    List<UserVO> getAll() {
        List<User> userList = userManager.getAll();
        List<UserVO> userVOList = new ArrayList<UserVO>();
        for (User user : userList) {
            UserVO userVo = new UserVO();
            userVo.setId(user.getId() + "");
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

    @RequestMapping(method = RequestMethod.GET, value = "/getUser/{userId}", headers = "Accept=application/json")
    public
    @ResponseBody
    User getUser(@PathVariable String userId) {
        return userManager.getUserById(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save", headers = "Accept=application/json")
    public
    @ResponseBody
    String insert(@RequestBody User user) {

        userManager.save(user);
        return "success";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean addUser(HttpServletRequest request, @RequestBody String[] param) {
        log.error("当前登录用户:" + ((User) request.getSession().getAttribute(LoginUtil.USER)).getName());
        String username = param[0];
        String initpwd = param[1];
        String role = param[2];
        String org = param[3];
        String status = param[4];
        String remark = param[5];
        User user = new User();
        user.setId(userManager.getMaxId() + 1);
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
    public
    @ResponseBody
    boolean deleteUser(@RequestBody
                       String[] param) {
        return userManager.delete(userManager.getUserById(param[0]));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getRoleIdByUserId/{userId}", headers = "Accept=application/json")
    public
    @ResponseBody
    String deleteUser(@PathVariable
                      String userId) {
        return userManager.getRoleIdByUserId(userId) + "";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/assignRole", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean assignRole(@RequestBody
                       String[] param) {
        String userId = param[0];
        String roleId = param[1];
        User user = userManager.getUserById(userId);
        user.setRoleId(Integer.parseInt(roleId));
        return userManager.save(user);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updatePwd", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean updatePwd(@RequestBody
                      String[] params) {
        String userName = params[0];
        String newPassword = params[1];
        return userManager.updatePwd(userName, newPassword);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/checkOldPwd", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean checkOldPwd(@RequestBody
                        String[] params) {
        String userName = params[0];
        String oldPassword = params[1];
        log.info("oldPassword: " + oldPassword + ", userName: " + userName);
        return userManager.checkOldPwd(userName, oldPassword);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/reset/{params}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean checkOldPwd(@PathVariable String params) {
        String[] userIdArr = params.split(",");
        for (String userId : userIdArr) {
            userManager.resetPwd(userId);
        }
        return true;
    }
}
