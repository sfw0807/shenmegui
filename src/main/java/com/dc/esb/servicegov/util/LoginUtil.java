package com.dc.esb.servicegov.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dc.esb.servicegov.entity.Function;
import com.dc.esb.servicegov.entity.User;

public class LoginUtil {

	public static String USER = "user";
	public static String PASSWORD = "password";
	public static String LOGIN_MESSAGE = "login_message";
	public static String FUNCTION_LIST = "function_list";
	
	private static User currentUser = null;
	
	public static User getUserFromSession(HttpServletRequest request) {
		
		User user = null;
		user = (User)request.getSession().getAttribute(USER);
		return user;
	}
	
	
	public static void setLoginSuccessOfSession(HttpServletRequest request, User user) {
		
		currentUser = user;
		if(request.getSession().getAttribute(LoginUtil.LOGIN_MESSAGE) != null){
			request.getSession().removeAttribute(LoginUtil.LOGIN_MESSAGE);
		}
		request.getSession().setAttribute(LoginUtil.USER, user);
	}
	
    public static void setLoginFailedOfSession(HttpServletRequest request, User user) {
		
		currentUser = user;
		request.getSession().setAttribute(LoginUtil.LOGIN_MESSAGE, "用户名或密码不正确!");
	}
	
	public static User getUser() {
		return currentUser;
	}
	public static void setFunctionListOfSession(HttpServletRequest request, List<Function> functionList) {
		
		if(request.getSession().getAttribute(LoginUtil.FUNCTION_LIST) != null){
			request.getSession().removeAttribute(LoginUtil.FUNCTION_LIST);
		}
		request.getSession().setAttribute(LoginUtil.FUNCTION_LIST, functionList);
	}
}
