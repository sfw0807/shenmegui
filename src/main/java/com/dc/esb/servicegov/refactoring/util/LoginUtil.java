package com.dc.esb.servicegov.refactoring.util;

import javax.servlet.http.HttpServletRequest;

import com.dc.esb.servicegov.refactoring.entity.User;

public class LoginUtil {

	public static String USER = "user";
	
	private static User currentUser = null;
	
	public static User getUserFromSession(HttpServletRequest request) {
		
		User user = null;
		user = (User)request.getSession().getAttribute(USER);
		return user;
	}
	
	
	public static void setUserOfSession(HttpServletRequest request, User user) {
		
		currentUser = user;
		request.getSession().setAttribute(LoginUtil.USER, user);
	}
	
	public static User getUser() {
		return currentUser;
	}
}
