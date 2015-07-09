<%@ page contentType="text/json; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
	String mid = request.getParameter("mid");
%>
<%
	if(mid.equals("1.2")){
%>
{"success":true,"url":"/dataTemplate/grid.jsp","title":"版本制作"}
<%
	}
	
%>
<%
	if(mid.equals("1.3")){
%>
{"success":true,"url":"/dataTemplate/grid3.jsp","title":"版本历史"}
<%
	}
	
%>
<%
	if(mid.equals("1.4")){
%>
{"success":true,"url":"/dataTemplate/grid2.jsp","title":"版本公告"}
<%
	}
	
%>
<%
	if(mid.equals("2.3")){
%>
{"success":true,"url":"/dataTemplate/grid4.jsp","title":"任务管理"}
<%
	}
	
%>
<%
	if(mid.equals("2.4")){
%>
{"success":true,"url":"/dataTemplate/task/mytask.html","title":"我的任务"}
<%
	}
	
%>
<%
	if(mid.equals("3.2")){
%>
{"success":true,"url":"/dataTemplate/grid5.jsp","title":"英文单词及缩写管理"}
<%
	}
	
%>
<%
	if(mid.equals("3.3")){
%>
{"success":true,"url":"/dataTemplate/grid6.jsp","title":"类别词管理"}
<%
	}
	
%>
<%
	if(mid.equals("3.4")){
%>
{"success":true,"url":"/dataTemplate/grid7.jsp","title":"元数据管理"}
<%
	}
	
%>
<%
	if(mid.equals("3.6")){
%>
{"success":true,"url":"/dataTemplate/words/gonggong.html","title":"公共代码管理"}
<%
	}
	
%>

<%
	if(mid.equals("4.2")){
%>
{"success":true,"url":"/jsp/user/userMaintain.jsp","title":"用户维护"}
<%
	}
	
%>