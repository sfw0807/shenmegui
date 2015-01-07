package com.dc.esb.servicegov.refactoring.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForceNoCacheFilter implements Filter{

	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		System.out.println("destroy cache..."+request.getRemoteAddr());
		((HttpServletResponse)response).setHeader("Cache-Control","no-cache");
		((HttpServletResponse)response).setHeader("Pragma","No-Cache");
		((HttpServletResponse)response).setDateHeader("Expires", 0);
		filterChain.doFilter(request, response);
	}

	
	public void init(FilterConfig arg0) throws ServletException {
				
	}
	
}
